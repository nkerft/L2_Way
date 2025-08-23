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
package instances.DimensionMakkum;

import org.l2jmobius.gameserver.model.actor.Npc;
import org.l2jmobius.gameserver.model.actor.Player;
import org.l2jmobius.gameserver.model.instancezone.Instance;

import instances.AbstractInstance;

/**
 * Dimension Makkum instance
 * @author Gigi
 */
public class DimensionMakkum extends AbstractInstance
{
	// NPCs
	private static final int PIORE = 34290;
	private static final int DIMENSIONAL_MAKKUM = 26195;
	// Misc
	private static final int TEMPLATE_ID = 10658;
	
	public DimensionMakkum()
	{
		super(TEMPLATE_ID);
		addFirstTalkId(PIORE);
		addKillId(DIMENSIONAL_MAKKUM);
		addInstanceCreatedId(TEMPLATE_ID);
	}
	
	@Override
	public String onEvent(String event, Npc npc, Player player)
	{
		final Instance world = getPlayerInstance(player);
		switch (event)
		{
			case "enter_instance":
			{
				enterInstance(player, npc, TEMPLATE_ID);
				break;
			}
			case "spawn_piore":
			{
				addSpawn(PIORE, npc.getX(), npc.getY(), npc.getZ(), npc.getHeading(), false, 300000, false, world.getId());
				break;
			}
			case "exitInstance":
			{
				if (world != null)
				{
					teleportPlayerOut(player, world);
				}
				break;
			}
		}
		return super.onEvent(event, npc, player);
	}
	
	@Override
	public void onInstanceCreated(Instance instance, Player player)
	{
		addSpawn(DIMENSIONAL_MAKKUM, 185064, -9610, -5488, 19610, false, 430000, true, instance.getId());
	}
	
	@Override
	public String onKill(Npc npc, Player killer, boolean isSummon)
	{
		final Instance world = npc.getInstanceWorld();
		if (world == null)
		{
			return super.onKill(npc, killer, isSummon);
		}
		
		startQuestTimer("spawn_piore", 4000, npc, killer);
		world.finishInstance();
		
		return super.onKill(npc, killer, isSummon);
	}
	
	@Override
	public String onFirstTalk(Npc npc, Player player)
	{
		return "34290.html";
	}
	
	public static void main(String[] args)
	{
		new DimensionMakkum();
	}
}