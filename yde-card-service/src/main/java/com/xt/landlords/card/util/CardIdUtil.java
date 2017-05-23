package com.xt.landlords.card.util;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class CardIdUtil {
	private AtomicInteger atomicInteger;
	private Integer len;
	private List<String> idList;
	
	public CardIdUtil(){
		atomicInteger = new AtomicInteger(0);
		idList = new ArrayList<String>();
	}

	public AtomicInteger getAtomicInteger() {
		return atomicInteger;
	}

	public void setAtomicInteger(AtomicInteger atomicInteger) {
		this.atomicInteger = atomicInteger;
	}

	public Integer getLen() {
		return len;
	}

	public void setLen(Integer len) {
		this.len = len;
	}

	public List<String> getIdList() {
		return idList;
	}

	public void setIdList(List<MyId> mids) {
		System.out.println(mids.get(0));
		for(int i = 0; i < mids.size(); i++){
			this.idList.add(mids.get(i).getId());
		}
	}

	@Override
	public String toString() {
		return "CardIdUtil [atomicInteger=" + atomicInteger + ", len=" + len
				+ ", idList=" + idList.get(0) + "......]";
	}
	
	
}
