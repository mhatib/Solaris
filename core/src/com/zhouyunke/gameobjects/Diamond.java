package com.zhouyunke.gameobjects;

public class Diamond {
	private int row;
	private int col;
	private Icon icon;
	private int previousRow;
	
	public Diamond(int row, int col,Icon icon){
	    this.row = row;
	    this.col = col;
	    this.icon = icon;
	    this.previousRow = row;
	}
	
	public void setPreviousRow(int row){
	    previousRow = row;
	}
	
	public int getPreviousRow(){
	    return previousRow;
	}
	
	public Icon getIcon(){
	    return icon;
	}  
	
	public int row(){
	    return row;
	}
	
	public int col(){
	    return col;
	}
	
	public boolean samePosition(Diamond other){
	    return row == other.row && col == other.col;
	}
	
}
