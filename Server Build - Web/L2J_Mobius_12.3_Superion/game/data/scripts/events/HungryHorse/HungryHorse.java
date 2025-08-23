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
package events.HungryHorse;

import org.l2jmobius.gameserver.model.actor.Npc;
import org.l2jmobius.gameserver.model.actor.Player;
import org.l2jmobius.gameserver.model.quest.LongTimeEvent;
import org.l2jmobius.gameserver.model.skill.SkillCaster;
import org.l2jmobius.gameserver.model.skill.holders.SkillHolder;

/**
 * Hungry Horse
 * @URL http://www.lineage2.com/en/news/events/hungry-horse-event-09192017.php
 * @author Mobius
 */
public class HungryHorse extends LongTimeEvent
{
	// NPC
	private static final int GALUP = 34010;
	// Items
	private static final int CARROT = 40363;
	private static final int POUCH = 40365;
	// Skills
	private static final SkillHolder[] GROUP_BUFFS =
	{
		new SkillHolder(15642, 1), // Horn Melody
		new SkillHolder(15643, 1), // Drum Melody
		new SkillHolder(15644, 1), // Pipe Organ Melody
		new SkillHolder(15645, 1), // Guitar Melody
		new SkillHolder(15651, 1), // Prevailing Sonata
		new SkillHolder(15652, 1), // Daring Sonata
		new SkillHolder(15653, 1), // Refreshing Sonata
	};
	private static final SkillHolder FANTASIA = new SkillHolder(32840, 1); // Fantasia Harmony - Adventurer
	private static final SkillHolder XP_BUFF = new SkillHolder(19036, 1); // Blessing of Light
	
	private HungryHorse()
	{
		addStartNpc(GALUP);
		addFirstTalkId(GALUP);
		addTalkId(GALUP);
	}
	
	@Override
	public String onEvent(String event, Npc npc, Player player)
	{
		String htmltext = null;
		switch (event)
		{
			case "34010-01.htm":
			case "34010-02.htm":
			{
				htmltext = event;
				break;
			}
			case "knight":
			{
				if (getQuestItemsCount(player, CARROT) >= 7)
				{
					takeItems(player, CARROT, 7);
					for (SkillHolder holder : GROUP_BUFFS)
					{
						SkillCaster.triggerCast(npc, player, holder.getSkill());
					}
					SkillCaster.triggerCast(npc, player, FANTASIA.getSkill()); // TODO: Merge events.
					SkillCaster.triggerCast(npc, player, XP_BUFF.getSkill());
				}
				else
				{
					htmltext = "34010-03.htm";
				}
				break;
			}
			case "warrior":
			{
				if (getQuestItemsCount(player, CARROT) >= 7)
				{
					takeItems(player, CARROT, 7);
					for (SkillHolder holder : GROUP_BUFFS)
					{
						SkillCaster.triggerCast(npc, player, holder.getSkill());
					}
					SkillCaster.triggerCast(npc, player, FANTASIA.getSkill()); // TODO: Merge events.
					SkillCaster.triggerCast(npc, player, XP_BUFF.getSkill());
				}
				else
				{
					htmltext = "34010-03.htm";
				}
				break;
			}
			case "wizard":
			{
				if (getQuestItemsCount(player, CARROT) >= 7)
				{
					takeItems(player, CARROT, 7);
					for (SkillHolder holder : GROUP_BUFFS)
					{
						SkillCaster.triggerCast(npc, player, holder.getSkill());
					}
					SkillCaster.triggerCast(npc, player, FANTASIA.getSkill()); // TODO: Merge events.
					SkillCaster.triggerCast(npc, player, XP_BUFF.getSkill());
				}
				else
				{
					htmltext = "34010-03.htm";
				}
				break;
			}
			case "giveCarrots40":
			{
				if (getQuestItemsCount(player, CARROT) >= 40)
				{
					takeItems(player, CARROT, 40);
					giveItems(player, POUCH, 1);
					htmltext = "34010-04.htm";
				}
				else
				{
					htmltext = "34010-03.htm";
				}
				break;
			}
			case "giveCarrots4000":
			{
				if (getQuestItemsCount(player, CARROT) >= 4000)
				{
					takeItems(player, CARROT, 4000);
					giveItems(player, POUCH, 100);
					htmltext = "34010-04.htm";
				}
				else
				{
					htmltext = "34010-03.htm";
				}
				break;
			}
			case "giveCarrots40000":
			{
				if (getQuestItemsCount(player, CARROT) >= 40000)
				{
					takeItems(player, CARROT, 40000);
					giveItems(player, POUCH, 1000);
					htmltext = "34010-04.htm";
				}
				else
				{
					htmltext = "34010-03.htm";
				}
				break;
			}
		}
		return htmltext;
	}
	
	@Override
	public String onFirstTalk(Npc npc, Player player)
	{
		return npc.getId() + "-01.htm";
	}
	
	public static void main(String[] args)
	{
		new HungryHorse();
	}
}
