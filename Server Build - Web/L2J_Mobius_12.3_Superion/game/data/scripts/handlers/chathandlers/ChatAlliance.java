/*
 * Copyright (c) 2013 L2jMobius
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
 * WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR
 * IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package handlers.chathandlers;

import org.l2jmobius.Config;
import org.l2jmobius.gameserver.enums.ChatType;
import org.l2jmobius.gameserver.handler.IChatHandler;
import org.l2jmobius.gameserver.model.actor.Player;
import org.l2jmobius.gameserver.model.actor.enums.player.PlayerCondOverride;
import org.l2jmobius.gameserver.network.SystemMessageId;
import org.l2jmobius.gameserver.network.serverpackets.CreatureSay;

/**
 * Alliance Chat Handler.
 */
public class ChatAlliance implements IChatHandler
{
	private static final ChatType[] CHAT_TYPES =
	{
		ChatType.ALLIANCE,
	};
	
	@Override
	public void handleChat(ChatType type, Player activeChar, String target, String text)
	{
		if ((activeChar.getClan() == null) || ((activeChar.getClan() != null) && (activeChar.getClan().getAllyId() == 0)))
		{
			activeChar.sendPacket(SystemMessageId.YOU_ARE_NOT_IN_AN_ALLIANCE);
			return;
		}
		
		if (activeChar.isChatBanned() && Config.BAN_CHAT_CHANNELS.contains(type))
		{
			activeChar.sendPacket(SystemMessageId.CHATTING_IS_CURRENTLY_PROHIBITED_IF_YOU_TRY_TO_CHAT_BEFORE_THE_PROHIBITION_IS_REMOVED_THE_PROHIBITION_TIME_WILL_INCREASE_EVEN_FURTHER_CHATTING_BAN_TIME_REMAINING_S1_SECONDS);
			return;
		}
		if (Config.JAIL_DISABLE_CHAT && activeChar.isJailed() && !activeChar.canOverrideCond(PlayerCondOverride.CHAT_CONDITIONS))
		{
			activeChar.sendPacket(SystemMessageId.CHATTING_IS_CURRENTLY_PROHIBITED);
			return;
		}
		activeChar.getClan().broadcastToOnlineAllyMembers(new CreatureSay(activeChar, type, activeChar.getName(), text));
	}
	
	@Override
	public ChatType[] getChatTypeList()
	{
		return CHAT_TYPES;
	}
}