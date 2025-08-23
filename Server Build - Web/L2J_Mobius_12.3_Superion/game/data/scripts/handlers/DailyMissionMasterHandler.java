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
package handlers;

import java.util.logging.Logger;

import org.l2jmobius.gameserver.handler.DailyMissionHandler;

import handlers.dailymissionhandlers.AuctionDailyMissionHandler;
import handlers.dailymissionhandlers.AugmentationDailyMissionHandler;
import handlers.dailymissionhandlers.BossDailyMissionHandler;
import handlers.dailymissionhandlers.CeremonyOfChaosDailyMissionHandler;
import handlers.dailymissionhandlers.CombinationDailyMissionHandler;
import handlers.dailymissionhandlers.CompoundDailyMissionHandler;
import handlers.dailymissionhandlers.ConquestCollectFlowersMissionHandler;
import handlers.dailymissionhandlers.EnchantDailyMissionHandler;
import handlers.dailymissionhandlers.EnsoulDailyMissionHandler;
import handlers.dailymissionhandlers.ExaltedDailyMissionHandler;
import handlers.dailymissionhandlers.FishingDailyMissionHandler;
import handlers.dailymissionhandlers.JoinClanDailyMissionHandler;
import handlers.dailymissionhandlers.LevelDailyMissionHandler;
import handlers.dailymissionhandlers.LoginMonthDailyMissionHandler;
import handlers.dailymissionhandlers.LoginWeekendDailyMissionHandler;
import handlers.dailymissionhandlers.MentorDailyMissionHandler;
import handlers.dailymissionhandlers.MonsterDailyMissionHandler;
import handlers.dailymissionhandlers.NoblesseDailyMissionHandler;
import handlers.dailymissionhandlers.OlympiadDailyMissionHandler;
import handlers.dailymissionhandlers.OlympiadHeroDailyMissionHandler;
import handlers.dailymissionhandlers.QuestDailyMissionHandler;
import handlers.dailymissionhandlers.SiegeDailyMissionHandler;
import handlers.dailymissionhandlers.UseItemDailyMissionHandler;

/**
 * @author UnAfraid, Mobius
 */
public class DailyMissionMasterHandler
{
	private static final Logger LOGGER = Logger.getLogger(DailyMissionMasterHandler.class.getName());
	
	public static void main(String[] args)
	{
		DailyMissionHandler.getInstance().registerHandler("auction", AuctionDailyMissionHandler::new);
		DailyMissionHandler.getInstance().registerHandler("augment", AugmentationDailyMissionHandler::new);
		DailyMissionHandler.getInstance().registerHandler("boss", BossDailyMissionHandler::new);
		DailyMissionHandler.getInstance().registerHandler("ceremonyofchaos", CeremonyOfChaosDailyMissionHandler::new);
		DailyMissionHandler.getInstance().registerHandler("combine", CombinationDailyMissionHandler::new);
		DailyMissionHandler.getInstance().registerHandler("compound", CompoundDailyMissionHandler::new);
		DailyMissionHandler.getInstance().registerHandler("conquestflowers", ConquestCollectFlowersMissionHandler::new);
		DailyMissionHandler.getInstance().registerHandler("enchant", EnchantDailyMissionHandler::new);
		DailyMissionHandler.getInstance().registerHandler("ensoul", EnsoulDailyMissionHandler::new);
		DailyMissionHandler.getInstance().registerHandler("exalted", ExaltedDailyMissionHandler::new);
		DailyMissionHandler.getInstance().registerHandler("fishing", FishingDailyMissionHandler::new);
		DailyMissionHandler.getInstance().registerHandler("joinclan", JoinClanDailyMissionHandler::new);
		DailyMissionHandler.getInstance().registerHandler("level", LevelDailyMissionHandler::new);
		DailyMissionHandler.getInstance().registerHandler("loginmonth", LoginMonthDailyMissionHandler::new);
		DailyMissionHandler.getInstance().registerHandler("loginweekend", LoginWeekendDailyMissionHandler::new);
		DailyMissionHandler.getInstance().registerHandler("mentor", MentorDailyMissionHandler::new);
		DailyMissionHandler.getInstance().registerHandler("monster", MonsterDailyMissionHandler::new);
		DailyMissionHandler.getInstance().registerHandler("noblesse", NoblesseDailyMissionHandler::new);
		DailyMissionHandler.getInstance().registerHandler("olympiad", OlympiadDailyMissionHandler::new);
		DailyMissionHandler.getInstance().registerHandler("olympiadhero", OlympiadHeroDailyMissionHandler::new);
		DailyMissionHandler.getInstance().registerHandler("quest", QuestDailyMissionHandler::new);
		DailyMissionHandler.getInstance().registerHandler("siege", SiegeDailyMissionHandler::new);
		DailyMissionHandler.getInstance().registerHandler("useitem", UseItemDailyMissionHandler::new);
		LOGGER.info(DailyMissionMasterHandler.class.getSimpleName() + ": Loaded " + DailyMissionHandler.getInstance().size() + " handlers.");
	}
}
