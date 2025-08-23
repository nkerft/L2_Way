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
package ai.others.AetherDrops;

import java.io.File;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;

import org.l2jmobius.commons.util.IXmlReader;

/**
 * Aether drops data parser.
 * @author CostyKiller
 */
public class AetherDropData implements IXmlReader
{
	private final Map<Integer, AetherDropHolder> _dropsInfo = new HashMap<>();
	
	@Override
	public void load()
	{
		_dropsInfo.clear();
		parseDatapackFile("data/scripts/ai/others/AetherDrops/AetherDropData.xml");
		LOGGER.info(getClass().getSimpleName() + ": Loaded " + _dropsInfo.size() + " aether drop data.");
	}
	
	@Override
	public void parseDocument(Document document, File file)
	{
		forEach(document, "list", listNode -> forEach(listNode, "npc", npcNode ->
		{
			final NamedNodeMap attrs = npcNode.getAttributes();
			
			// Parse the NPC ID.
			final Integer npcId = parseInteger(attrs, "id");
			if (npcId == null)
			{
				LOGGER.severe(getClass().getSimpleName() + ": Missing NPC ID, skipping record!");
				return;
			}
			
			// Parse the drop chance.
			final Double chance = parseDouble(attrs, "chance");
			if (chance == null)
			{
				LOGGER.severe(getClass().getSimpleName() + ": Missing drop chance info for NPC ID " + npcId + ", skipping record!");
				return;
			}
			
			// Parse other attributes with default values.
			final int min = parseInteger(attrs, "min", 1);
			final int max = parseInteger(attrs, "max", 1);
			final int minLevel = parseInteger(attrs, "minLevel", 85);
			final int maxLevel = parseInteger(attrs, "maxLevel", Integer.MAX_VALUE);
			
			// Store the parsed data.
			_dropsInfo.put(npcId, new AetherDropHolder(npcId, chance, min, max, minLevel, maxLevel));
		}));
	}
	
	/**
	 * Gets all the NPC IDs with Aether drops.
	 * @return the Collection of all NPC IDs
	 */
	public Collection<Integer> getNpcIds()
	{
		return _dropsInfo.keySet();
	}
	
	/**
	 * Gets the AetherDropHolder for a specific NPC ID.
	 * @param npcId the NPC ID
	 * @return the AetherDropHolder for the NPC ID, or null if not found
	 */
	public AetherDropHolder getAetherDropHolder(int npcId)
	{
		return _dropsInfo.get(npcId);
	}
	
	public static AetherDropData getInstance()
	{
		return SingletonHolder.INSTANCE;
	}
	
	private static class SingletonHolder
	{
		protected static final AetherDropData INSTANCE = new AetherDropData();
	}
}
