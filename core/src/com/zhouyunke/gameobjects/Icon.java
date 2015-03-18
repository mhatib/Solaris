package com.zhouyunke.gameobjects;


public class Icon {
	private final int type;
	private int special;
	private int specialState;
	
	public Icon(int t){
		this.type = t;
		this.special = 0;
		this.specialState = 0;
	}
	
	public int getType() {
		  return type;
	}
	
	public int getSpecial(){
		return special;
	}
	
	public void setSpecial(int t){
		this.special = t;
		this.specialState = 2;
	}
	
	public void reduceSpecialStateByOne(){
		this.specialState--;
	}
	
	public int getSpecialState(){
		return specialState;
	}
	
	public boolean equals(Icon other){
	    if (other== null)
	    	return false;
	    return type == other.type;
	}
}
