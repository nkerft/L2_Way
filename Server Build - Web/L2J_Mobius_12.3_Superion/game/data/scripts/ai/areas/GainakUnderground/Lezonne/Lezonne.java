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
package ai.areas.GainakUnderground.Lezonne;

import org.l2jmobius.gameserver.model.actor.Npc;
import org.l2jmobius.gameserver.model.actor.Player;
import org.l2jmobius.gameserver.model.skill.SkillCaster;
import org.l2jmobius.gameserver.model.skill.holders.SkillHolder;

import ai.AbstractNpcAI;

/**
 * @author Notorion
 */
public class Lezonne extends AbstractNpcAI
{
	// NPC
	private static final int LEZONNE = 33834;
	// Items
	private static final int ADENA = 57;
	// Skills
	private static final SkillHolder[] DONATE_BUFFS =
	{
		new SkillHolder(11517, 1), // Horn Melody
		new SkillHolder(11518, 1), // Drum Melody
		new SkillHolder(11519, 1), // Pipe Organ Melody
		new SkillHolder(11520, 1), // Guitar Melody
		new SkillHolder(30812, 1), // Fantasia Harmony
	};
	
	private Lezonne()
	{
		addStartNpc(LEZONNE);
		addTalkId(LEZONNE);
		addFirstTalkId(LEZONNE);
	}
	
	@Override
	public String onEvent(String event, Npc npc, Player player)
	{
		String htmltext = null;
		switch (event)
		{
			case "index":
			{
				htmltext = npc.getId() + ".html";
				break;
			}
			
			case "donate_adena":
			{
				if (getQuestItemsCount(player, ADENA) >= 200000)
				{
					takeItems(player, ADENA, 200000);
					for (SkillHolder holder : DONATE_BUFFS)
					{
						SkillCaster.triggerCast(npc, player, holder.getSkill());
					}
				}
				else
				{
					htmltext = "noItems.html";
				}
				break;
			}
			
		}
		return htmltext;
	}
	
	public static void main(String[] args)
	{
		new Lezonne();
	}
}
