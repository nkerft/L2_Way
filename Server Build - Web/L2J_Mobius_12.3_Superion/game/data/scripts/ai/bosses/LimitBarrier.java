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
package ai.bosses;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.l2jmobius.gameserver.model.actor.Npc;
import org.l2jmobius.gameserver.model.actor.Player;
import org.l2jmobius.gameserver.model.skill.Skill;
import org.l2jmobius.gameserver.model.skill.enums.SkillFinishType;
import org.l2jmobius.gameserver.model.skill.holders.SkillHolder;
import org.l2jmobius.gameserver.network.NpcStringId;
import org.l2jmobius.gameserver.network.serverpackets.ExShowScreenMessage;
import org.l2jmobius.gameserver.scripting.annotations.Disabled;

import ai.AbstractNpcAI;

/**
 * Limit Barrier AI<br>
 *         OK - Raid Bosses level 100 and higher from now on use "Limit Barrier" skill when their HP reaches 90%, 60% and 30%.<br>
 *         OK - 600 hits in 15 seconds are required to destroy the barrier. Amount of damage does not matter.<br>
 *         OK - If barrier destruction is failed, Boss restores full HP.<br>
 *         OK - Epic Bosses Orfen, Queen Ant and Core also use Limit Barrier.<br>
 *         OK - Epic Bosses Antharas, Zaken and Baium and their analogues in instance zones do not use Limit Barrier.<br>
 *         OK - Raid Bosses in instances do not use Limit Barrier.<br>
 *         OK - All Raid Bosses who use Limit Barrier are listed below:
 * @author RobikBobik
 */
@Disabled // Behaviour does not go well with low population servers.
public class LimitBarrier extends AbstractNpcAI
{
	// NPCs
	private static final int[] RAID_BOSSES =
	{
		29325, // Orfen
		29001, // Queen Ant
		29006, // Core
		26131, // Isabella
		26137, // Mimir
		26277, // Ashen Shadow Expeditionary Force Leaders Kantu
		26278, // Ashen Shadow Expeditionary Force Leaders Kai
		26279, // Ashen Shadow Expeditionary Force Leaders Heine
		26280, // Ashen Shadow Expeditionary Force Leaders Xenon
		26312, // Lithra
		29374, // Cyrax
		26431, // Avenger Alusion
		26432, // Avenger Graff
		26433, // Demon Venoma
		26434, // Fiend Sarboth
		26435, // Watcher Tristan
		26436, // Watcher Setheth
		26437, // Berserker Zetahl
		26438, // Berserker Tabris
		26439, // Ferocious Valac
		26440, // Arrogant Lebruum
		26441, // Witch Moira
		26442, // Mad Cullan
		26443, // Akrikhin Charon
		26444, // Scorpion King Votan
	};
	// Skill
	private static final SkillHolder LIMIT_BARRIER = new SkillHolder(32203, 1);
	// Misc
	private static final int HIT_COUNT = 600;
	private static final Map<Npc, Integer> RAIDBOSS_HITS = new ConcurrentHashMap<>();
	
	private LimitBarrier()
	{
		addAttackId(RAID_BOSSES);
		addKillId(RAID_BOSSES);
	}
	
	@Override
	public String onEvent(String event, Npc npc, Player player)
	{
		switch (event)
		{
			case "RESTORE_FULL_HP":
			{
				final int hits = RAIDBOSS_HITS.getOrDefault(npc, 0);
				if (hits < HIT_COUNT)
				{
					if (player != null)
					{
						npc.broadcastPacket(new ExShowScreenMessage(NpcStringId.YOU_HAVE_FAILED_TO_DESTROY_THE_LIMIT_BARRIER_THE_RAID_BOSS_HAS_FULLY_RECOVERED_ITS_HEALTH, 2, 5000, true));
					}
					npc.setCurrentHp(npc.getStat().getMaxHp(), true);
					npc.stopSkillEffects(SkillFinishType.REMOVED, LIMIT_BARRIER.getSkillId());
					RAIDBOSS_HITS.put(npc, 0);
				}
				else if (hits > HIT_COUNT)
				{
					if (player != null)
					{
						npc.broadcastPacket(new ExShowScreenMessage(NpcStringId.YOU_HAVE_DESTROYED_THE_LIMIT_BARRIER, 2, 5000, true));
					}
					npc.stopSkillEffects(SkillFinishType.REMOVED, LIMIT_BARRIER.getSkillId());
					RAIDBOSS_HITS.put(npc, 0);
				}
				break;
			}
		}
		return super.onEvent(event, npc, player);
	}
	
	@Override
	public String onAttack(Npc npc, Player attacker, int damage, boolean isSummon, Skill skill)
	{
		if (npc.isAffectedBySkill(LIMIT_BARRIER.getSkillId()))
		{
			final int hits = RAIDBOSS_HITS.getOrDefault(npc, 0);
			RAIDBOSS_HITS.put(npc, hits + 1);
		}
		
		if ((npc.getCurrentHp() < (npc.getMaxHp() * 0.9)) && (npc.getCurrentHp() > (npc.getMaxHp() * 0.87)))
		{
			if (!npc.isAffectedBySkill(LIMIT_BARRIER.getSkillId()))
			{
				npc.setTarget(npc);
				npc.abortAttack();
				npc.abortCast();
				npc.doCast(LIMIT_BARRIER.getSkill());
				npc.broadcastPacket(new ExShowScreenMessage(NpcStringId.THE_RAID_BOSS_USES_THE_LIMIT_BARRIER_FOCUS_YOUR_ATTACKS_TO_DESTROY_THE_BARRIER_IN_15_SEC, 2, 5000, true));
				startQuestTimer("RESTORE_FULL_HP", 15000, npc, attacker);
			}
		}
		else if ((npc.getCurrentHp() < (npc.getMaxHp() * 0.6)) && (npc.getCurrentHp() > (npc.getMaxHp() * 0.58)))
		{
			if (!npc.isAffectedBySkill(LIMIT_BARRIER.getSkillId()))
			{
				npc.setTarget(npc);
				npc.abortAttack();
				npc.abortCast();
				npc.doCast(LIMIT_BARRIER.getSkill());
				npc.broadcastPacket(new ExShowScreenMessage(NpcStringId.THE_RAID_BOSS_USES_THE_LIMIT_BARRIER_FOCUS_YOUR_ATTACKS_TO_DESTROY_THE_BARRIER_IN_15_SEC, 2, 5000, true));
				startQuestTimer("RESTORE_FULL_HP", 15000, npc, attacker);
			}
		}
		else if ((npc.getCurrentHp() < (npc.getMaxHp() * 0.3)) && (npc.getCurrentHp() > (npc.getMaxHp() * 0.28)))
		{
			if (!npc.isAffectedBySkill(LIMIT_BARRIER.getSkillId()))
			{
				npc.setTarget(npc);
				npc.abortAttack();
				npc.abortCast();
				npc.doCast(LIMIT_BARRIER.getSkill());
				npc.broadcastPacket(new ExShowScreenMessage(NpcStringId.THE_RAID_BOSS_USES_THE_LIMIT_BARRIER_FOCUS_YOUR_ATTACKS_TO_DESTROY_THE_BARRIER_IN_15_SEC, 2, 5000, true));
				startQuestTimer("RESTORE_FULL_HP", 15000, npc, attacker);
			}
		}
		return super.onAttack(npc, attacker, damage, isSummon, skill);
	}
	
	@Override
	public String onKill(Npc npc, Player killer, boolean isSummon)
	{
		RAIDBOSS_HITS.remove(npc);
		return super.onKill(npc, killer, isSummon);
	}
	
	public static void main(String[] args)
	{
		new LimitBarrier();
	}
}
