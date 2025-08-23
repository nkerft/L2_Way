/*
 * This file is part of the L2J Mobius project.
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package handlers.admincommandhandlers;

import java.util.StringTokenizer;

import org.l2jmobius.commons.util.StringUtil;
import org.l2jmobius.gameserver.cache.HtmCache;
import org.l2jmobius.gameserver.data.sql.ClanTable;
import org.l2jmobius.gameserver.handler.IAdminCommandHandler;
import org.l2jmobius.gameserver.managers.CastleManager;
import org.l2jmobius.gameserver.model.actor.Player;
import org.l2jmobius.gameserver.model.clan.Clan;
import org.l2jmobius.gameserver.model.siege.Castle;
import org.l2jmobius.gameserver.model.siege.CastleSide;
import org.l2jmobius.gameserver.network.SystemMessageId;
import org.l2jmobius.gameserver.network.serverpackets.NpcHtmlMessage;

/**
 * Admin Castle manage admin commands.
 * @author St3eT
 */
public class AdminCastle implements IAdminCommandHandler
{
	private static final String[] ADMIN_COMMANDS =
	{
		"admin_castlemanage",
	};
	
	@Override
	public boolean useAdminCommand(String command, Player activeChar)
	{
		final StringTokenizer st = new StringTokenizer(command, " ");
		final String actualCommand = st.nextToken();
		if (actualCommand.equals("admin_castlemanage"))
		{
			if (st.hasMoreTokens())
			{
				final String param = st.nextToken();
				final Castle castle;
				if (StringUtil.isNumeric(param))
				{
					castle = CastleManager.getInstance().getCastleById(Integer.parseInt(param));
				}
				else
				{
					castle = CastleManager.getInstance().getCastle(param);
				}
				
				if (castle == null)
				{
					activeChar.sendSysMessage("Invalid parameters! Usage: //castlemanage <castleId[1-9] / castleName>");
					return false;
				}
				
				if (!st.hasMoreTokens())
				{
					showCastleMenu(activeChar, castle.getResidenceId());
				}
				else
				{
					final String action = st.nextToken();
					final Player target = checkTarget(activeChar) ? activeChar.getTarget().asPlayer() : null;
					switch (action)
					{
						case "showRegWindow":
						{
							castle.getSiege().listRegisterClan(activeChar);
							break;
						}
						case "addAttacker":
						{
							if (checkTarget(activeChar))
							{
								castle.getSiege().registerAttacker(target, true);
							}
							else
							{
								activeChar.sendPacket(SystemMessageId.INVALID_TARGET);
							}
							break;
						}
						case "removeAttacker":
						{
							if (checkTarget(activeChar))
							{
								castle.getSiege().removeSiegeClan(activeChar);
							}
							else
							{
								activeChar.sendPacket(SystemMessageId.INVALID_TARGET);
							}
							break;
						}
						case "addDeffender":
						{
							if (checkTarget(activeChar))
							{
								castle.getSiege().registerDefender(target, true);
							}
							else
							{
								activeChar.sendPacket(SystemMessageId.INVALID_TARGET);
							}
							break;
						}
						case "removeDeffender":
						{
							if (checkTarget(activeChar))
							{
								castle.getSiege().removeSiegeClan(target);
							}
							else
							{
								activeChar.sendPacket(SystemMessageId.INVALID_TARGET);
							}
							break;
						}
						case "startSiege":
						{
							if (!castle.getSiege().getAttackerClans().isEmpty())
							{
								castle.getSiege().startSiege();
							}
							else
							{
								activeChar.sendSysMessage("There is currently not registered any clan for castle siege!");
							}
							break;
						}
						case "stopSiege":
						{
							if (castle.getSiege().isInProgress())
							{
								castle.getSiege().endSiege();
							}
							else
							{
								activeChar.sendSysMessage("Castle siege is not currently in progress!");
							}
							showCastleMenu(activeChar, castle.getResidenceId());
							break;
						}
						case "setOwner":
						{
							if ((target == null) || !checkTarget(activeChar))
							{
								activeChar.sendPacket(SystemMessageId.INVALID_TARGET);
							}
							else if (target.getClan().getCastleId() > 0)
							{
								activeChar.sendSysMessage("This clan already have castle!");
							}
							else if (castle.getOwner() != null)
							{
								activeChar.sendSysMessage("This castle is already taken by another clan!");
							}
							else if (!st.hasMoreTokens())
							{
								activeChar.sendSysMessage("Invalid parameters!!");
							}
							else
							{
								final CastleSide side = Enum.valueOf(CastleSide.class, st.nextToken().toUpperCase());
								if (side != null)
								{
									castle.setSide(side);
									castle.setOwner(target.getClan());
								}
							}
							showCastleMenu(activeChar, castle.getResidenceId());
							break;
						}
						case "takeCastle":
						{
							final Clan clan = ClanTable.getInstance().getClan(castle.getOwnerId());
							if (clan != null)
							{
								castle.removeOwner(clan);
							}
							else
							{
								activeChar.sendSysMessage("Error during removing castle!");
							}
							showCastleMenu(activeChar, castle.getResidenceId());
							break;
						}
						case "switchSide":
						{
							if (castle.getSide() == CastleSide.DARK)
							{
								castle.setSide(CastleSide.LIGHT);
							}
							else if (castle.getSide() == CastleSide.LIGHT)
							{
								castle.setSide(CastleSide.DARK);
							}
							else
							{
								activeChar.sendSysMessage("You can't switch sides when is castle neutral!");
							}
							showCastleMenu(activeChar, castle.getResidenceId());
							break;
						}
					}
				}
			}
			else
			{
				final NpcHtmlMessage html = new NpcHtmlMessage(0, 1);
				html.setHtml(HtmCache.getInstance().getHtm(activeChar, "data/html/admin/castlemanage.htm"));
				activeChar.sendPacket(html);
			}
		}
		return true;
	}
	
	private void showCastleMenu(Player player, int castleId)
	{
		final Castle castle = CastleManager.getInstance().getCastleById(castleId);
		if (castle != null)
		{
			final Clan ownerClan = castle.getOwner();
			final NpcHtmlMessage html = new NpcHtmlMessage(0, 1);
			html.setHtml(HtmCache.getInstance().getHtm(player, "data/html/admin/castlemanage_selected.htm"));
			html.replace("%castleId%", castle.getResidenceId());
			html.replace("%castleName%", castle.getName());
			html.replace("%ownerName%", ownerClan != null ? ownerClan.getLeaderName() : "NPC");
			html.replace("%ownerClan%", ownerClan != null ? ownerClan.getName() : "NPC");
			html.replace("%castleSide%", StringUtil.capitalizeFirst(castle.getSide().toString().toLowerCase()));
			player.sendPacket(html);
		}
	}
	
	private boolean checkTarget(Player player)
	{
		return ((player.getTarget() != null) && player.getTarget().isPlayer() && (player.getTarget().asPlayer().getClan() != null));
	}
	
	@Override
	public String[] getAdminCommandList()
	{
		return ADMIN_COMMANDS;
	}
}