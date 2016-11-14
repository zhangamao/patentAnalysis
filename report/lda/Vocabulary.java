package com.report.lda;

import java.util.Map;
import java.util.TreeMap;

/**
 * a word set
 * @author M.Line
 *
 */
public class Vocabulary {

	Map<String, Integer> word2idMap;  //单词键值对 word--idMap
	String[] id2wordMap;  //单词数组 
	
	public Vocabulary()
	{
		word2idMap = new TreeMap<String, Integer>();
		id2wordMap = new String[1024];
		
	}
	
	public Integer getId(String word){  //取得单词的id
		return getId(word, false);
	}
	
	public String getWord(int id){
		return id2wordMap[id];
	}
	public Integer getId(String word, boolean create)  
	{
		Integer id = word2idMap.get(word);
		if(!create)  //如果单词为非新创建的，则直接返回取得的id
			return id;
		if(id == null)
		{
			id = word2idMap.size();
		}
		word2idMap.put(word, id);
		if(id2wordMap.length - 1 < id)
		{
			resize(word2idMap.size()*2); //相当于扩容
		}
		id2wordMap[id] = word;
		
		return id;
	}
	
	private void resize(int n)
	{
		String[] nArray = new String[n];
		System.arraycopy(id2wordMap, 0, nArray, 0, id2wordMap.length);
		id2wordMap = nArray;
	}
	
	private void loseWeight()
	{
		if(size() == id2wordMap.length)
			return;
		resize(word2idMap.size());
	}
	
	public int size()
	{
		return word2idMap.size();
	}
	
	@Override
	public String toString()
	{
		final StringBuilder sb = new StringBuilder();
		for(int i = 0; i < id2wordMap.length; i++)
		{
			if(id2wordMap[i] == null)
				break;
			sb.append(i).append("=").append(id2wordMap[i]).append("\n");
		}
		return sb.toString();
	}
}
