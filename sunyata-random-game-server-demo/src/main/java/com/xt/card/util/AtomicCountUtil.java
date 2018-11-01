package com.xt.card.util;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class AtomicCountUtil {
	private Map<String, AtomicInteger> cAtomicCount = new HashMap<String, AtomicInteger>();
	private Map<String, AtomicInteger> fAtomicCount = new HashMap<String, AtomicInteger>();
	private static AtomicCountUtil atomicCountUtil = new AtomicCountUtil();
	private AtomicCountUtil(){
		cAtomicCount.put("loss", new AtomicInteger());
		cAtomicCount.put("win0", new AtomicInteger());
		cAtomicCount.put("win1", new AtomicInteger());
		cAtomicCount.put("win2", new AtomicInteger());
		cAtomicCount.put("win3", new AtomicInteger());
		cAtomicCount.put("win4", new AtomicInteger());
		cAtomicCount.put("win5", new AtomicInteger());
		cAtomicCount.put("win6", new AtomicInteger());
		
		fAtomicCount.put("win0", new AtomicInteger());
		fAtomicCount.put("win1", new AtomicInteger());
		fAtomicCount.put("win2", new AtomicInteger());
		fAtomicCount.put("win3", new AtomicInteger());
		fAtomicCount.put("win4", new AtomicInteger());
		fAtomicCount.put("win5", new AtomicInteger());
		fAtomicCount.put("win6", new AtomicInteger());
		fAtomicCount.put("win7", new AtomicInteger());
		fAtomicCount.put("win8", new AtomicInteger());
		
	}
	public static AtomicCountUtil getInstance(){
		if(atomicCountUtil == null){
			atomicCountUtil = new AtomicCountUtil();
		}
		return atomicCountUtil;
	}
	
	public AtomicInteger getCCount(String type){
		return cAtomicCount.get(type);
	}

	public AtomicInteger getFCount(String type){
		return fAtomicCount.get(type);
	}
	
}
