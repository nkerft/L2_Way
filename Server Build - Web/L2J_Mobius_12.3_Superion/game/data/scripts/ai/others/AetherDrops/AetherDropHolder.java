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

/**
 * @author Mobius
 */
public class AetherDropHolder
{
	private final int _npcId;
	private final double _chance;
	private final int _minDropAmount;
	private final int _maxDropAmount;
	private final int _minPlayerLevel;
	private final int _maxPlayerLevel;
	
	public AetherDropHolder(int npcId, double chance, int minDropAmount, int maxDropAmount, int minPlayerLevel, int maxPlayerLevel)
	{
		_npcId = npcId;
		_chance = chance;
		_minDropAmount = minDropAmount;
		_maxDropAmount = maxDropAmount;
		_minPlayerLevel = minPlayerLevel;
		_maxPlayerLevel = maxPlayerLevel;
	}
	
	public int getNpcId()
	{
		return _npcId;
	}
	
	public double getChance()
	{
		return _chance;
	}
	
	public int getMinDropAmount()
	{
		return _minDropAmount;
	}
	
	public int getMaxDropAmount()
	{
		return _maxDropAmount;
	}
	
	public int getMinPlayerLevel()
	{
		return _minPlayerLevel;
	}
	
	public int getMaxPlayerLevel()
	{
		return _maxPlayerLevel;
	}
}
