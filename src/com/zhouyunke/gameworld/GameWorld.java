package com.zhouyunke.gameworld;

import java.util.ArrayList;


import com.zhouyunke.gamehelpers.Generator;
import com.zhouyunke.gameobjects.Diamond;
import com.zhouyunke.gameobjects.Icon;

public class GameWorld{

	public static final int SIZE = 140;
	private static final int BASE_SCORE = 10;
	private Generator gen;
	private int score;
	private int life;
	private int magic;
	private Icon[][] grid;
	Diamond currentDiamond;
	Diamond nextDiamond;
	private int scoreCounter = 0;
	private int flashingState = 0;
	private boolean collapsing = false;
    private boolean filling = false;
    private ArrayList<Diamond> diamondsToCollapse = null;
    private int numberOfFlashes = 3;
    private ArrayList<AnimatedDiamond> movingDiamonds = null;
    private int fallPerFrame = 40; 
    private ArrayList<AnimatedDiamond> fillingDiamonds = null;
    private static float screenHeight;
    private static float screenWidth;
    public enum GameState {
        READY, RUNNING, GAMEOVER, HIGHSCORE
    }
    public GameState currentState;

    	
	public GameWorld(int height, int width, Generator generator,float w,float h){    
	    grid = new Icon[height][width];
	    gen = generator;
	    do{gen.initialize(grid);}while(!findMatch(false).isEmpty());
	    score = 0;
	    life = 5;
	    magic = 0;
	    currentDiamond = null;
	    nextDiamond = null;
	    screenHeight = h;
	    screenWidth = w;
	    currentState = GameState.RUNNING;
	}
	
	public float getScreenWidth(){
		return screenWidth;
	}

	public float getscreenHeight(){
		return screenHeight;
	}

	public Icon getIcon(int row, int col) {
		return grid[row][col];
	}


	public void setIcon(int row, int col, Icon icon) {
		grid[row][col] = icon;		
	}


	public int getWidth() {
		return grid[0].length;
	}


	public int getHeight() {
		return grid.length;
	}


	public int getScore() {
		return score;
	}
	
	public int getMagic(){
		return magic;
	}
	
	public boolean getCollapsing(){
		return collapsing;
	}
	
	public boolean getFilling(){
		return filling;
	}
	
	public int getFlashingState(){
		return flashingState;
	}
	public ArrayList<Diamond> getDiamondsToCollapse(){
		return diamondsToCollapse;
	}
	
	public ArrayList<AnimatedDiamond> getMovingDiamonds(){
		return movingDiamonds;
	}
	
	public ArrayList<AnimatedDiamond> getFillingDiamonds(){
		return fillingDiamonds;
	}
	
	public void setCurrentDiamond(int row, int col, Icon icon){
		currentDiamond = new Diamond(row, col, icon);
	}
	
	public Diamond getCurrentDiamond(){
		return currentDiamond;
	}
	
	public void setCurrentDiamondToNull(){
		currentDiamond = null;
	}
	
	public void setNextDiamond(int row, int col, Icon icon){
		nextDiamond = new Diamond(row, col, icon);
	}
	
	public void setNextDiamondToNull(){
		nextDiamond = null;
	}
	
	public Diamond getNextDiamond(){
		return nextDiamond;
	}
	
	public void setLife(int n){
		life = n;
	}
	
	public int getLife(){
		return life;
	}
	
	public GameState getCurrentState(){
		return currentState;
	}
	
	public void setCurrentState(GameState s){
		currentState = s;
	}
	
	public boolean isRunning(){
		return (currentState==GameState.RUNNING);
	}
	
	public void restart(){
		life = 5;
		score = 0;
		magic = 0;
	    do{gen.initialize(grid);}while(!findMatch(false).isEmpty());
	    currentDiamond = null;
	    nextDiamond = null;
	    currentState = GameState.RUNNING;
	}
	

	public ArrayList<Diamond> findMatch(boolean doMarkAndUpdateScore) {

		ArrayList<Diamond> aListTemp = new ArrayList<Diamond>(); 
		int counter = 0; 
		scoreCounter = 0;
		
		//vertical
	    for(int i = 0; i < grid[0].length; i++){
	    	for(int j = 0; j < grid.length; j++){ 
	    		if(j < grid.length - 3 && (grid[j][i].equals(grid[j+1][i])) && (grid[j][i].equals(grid[j+2][i])) && (grid[j][i].equals(grid[j+3][i]))){ 
	    			aListTemp.add(new Diamond(j, i, grid[j][i])); 
	    			counter++; 
	    		}

	    		else if(j > 0 && j < grid.length - 2 && grid[j-1][i].equals(grid[j][i]) && grid[j+1][i].equals(grid[j][i]) && grid[j+2][i].equals(grid[j][i])){ 
	    			aListTemp.add(new Diamond(j, i, grid[j][i])); 
	    			counter++;
	    		}

	    		else if(j > 1 && j<grid.length-1 && grid[j][i].equals(grid[j-1][i]) && grid[j-2][i].equals(grid[j][i]) && grid[j][i].equals(grid[j+1][i])){ 
	    			aListTemp.add(new Diamond(j, i, grid[j][i])); 
	    			counter++;
	    		}
	    		
	    		else if(j > 2 && grid[j][i].equals(grid[j-1][i]) && grid[j-2][i].equals(grid[j][i]) && grid[j][i].equals(grid[j-3][i])){ 
	    			aListTemp.add(new Diamond(j, i, grid[j][i])); 
	    			counter++;
	    		}
	    	}

	    	if(counter == 4 && doMarkAndUpdateScore){ 
	    		score = score + BASE_SCORE; 
	    		life ++;
	    		scoreCounter ++;
	    	}

	    	else if(counter > 4 && doMarkAndUpdateScore){
	    		score = score + (BASE_SCORE * (int)Math.pow((double)2, (double)(counter - 4))); 	
	    		life += (int)Math.pow((double)2, (double)(counter - 4));
	    		scoreCounter += counter-3;
	    	}

	    	counter = 0;
	    }

	    counter = 0;
	    //horizontol
	    for(int i = 0; i < grid.length; i++){
	    	for(int j = 0; j < grid[0].length; j++){
	    		if(j < grid[j].length - 3 && grid[i][j].equals(grid[i][j+1]) && grid[i][j].equals(grid[i][j+2]) && grid[i][j].equals(grid[i][j+3])){ 
	    			aListTemp.add(new Diamond(i, j, grid[i][j])); 
	    			counter++;
	    		}
	    		else if(j > 0 && j < grid[j].length - 2 && grid[i][j-1].equals(grid[i][j]) && grid[i][j+1].equals(grid[i][j]) && grid[i][j+2].equals(grid[i][j])){ 
	    			aListTemp.add(new Diamond(i, j, grid[i][j])); 
	    			counter++;
	    		}
	    		else if(j > 1 && j<grid[j].length - 1 && grid[i][j].equals(grid[i][j-1]) && grid[i][j-2].equals(grid[i][j]) && grid[i][j+1].equals(grid[i][j])){ 
	    			aListTemp.add(new Diamond(i, j, grid[i][j]));
	    			counter++;
	    		}
	    		else if(j > 2 && grid[i][j].equals(grid[i][j-1]) && grid[i][j-2].equals(grid[i][j]) && grid[i][j-3].equals(grid[i][j])){ 
	    			aListTemp.add(new Diamond(i, j, grid[i][j]));
	    			counter++;
	    		}
	    	}

	    	if(counter == 4 && doMarkAndUpdateScore){
	    		score = score + BASE_SCORE;
	    		life++;
	    		scoreCounter++;
	    	}
	    	else if(counter > 4 && doMarkAndUpdateScore){
	    		score = score + (BASE_SCORE * (int)Math.pow((double)2, (double)(counter - 4)));		
	    		life += (int)Math.pow((double)2, (double)(counter - 4));
	    		scoreCounter+=counter-3;
	    	}
	    	counter = 0;
	    }
	    
	    counter = 0;
	    
	    //going down
	    boolean isContinue = false;
	    for(int i = 0; i < grid.length; i++){
	    	for(int j = 0; j < grid[0].length; j++){
	    		if(j < grid[j].length - 3 && i < grid[j].length -3 && grid[i][j].equals(grid[i+1][j+1]) && grid[i][j].equals(grid[i+2][j+2]) && grid[i][j].equals(grid[i+3][j+3])){ 
	    			aListTemp.add(new Diamond(i, j, grid[i][j])); 
	    			counter++;
	    		}
	    		else if(j > 0 && i > 0 && j < grid[j].length - 2 && i < grid[j].length -2 && grid[i-1][j-1].equals(grid[i][j]) && grid[i+1][j+1].equals(grid[i][j]) && grid[i+2][j+2].equals(grid[i][j])){ 
	    			aListTemp.add(new Diamond(i, j, grid[i][j])); 
	    			counter++;
	    		}
	    		else if(j > 1 && i >1 && j < grid[j].length - 1 && i < grid[j].length -1 && grid[i][j].equals(grid[i-1][j-1]) && grid[i-2][j-2].equals(grid[i][j]) && grid[i+1][j+1].equals(grid[i][j])){ 
	    			aListTemp.add(new Diamond(i, j, grid[i][j]));
	    			counter++;
	    		}
	    		else if(j > 2 && i>2 && grid[i][j].equals(grid[i-1][j-1]) && grid[i-2][j-2].equals(grid[i][j]) && grid[i-3][j-3].equals(grid[i][j])){ 
	    			aListTemp.add(new Diamond(i, j, grid[i][j]));
	    			counter++;
	    		}
	    	}
	    	if(counter>0 && i<grid.length-1)
	    		isContinue = true;
	    	else 
	    		isContinue = false;
	    	if(counter == 4 && doMarkAndUpdateScore && !isContinue){
	    		score = score + BASE_SCORE;
	    		life++;
	    		scoreCounter++;
	    	}
	    	else if(counter > 4 && doMarkAndUpdateScore && !isContinue){
	    		score = score + (BASE_SCORE * (int)Math.pow((double)2, (double)(counter - 4)));		
	    		life += (int)Math.pow((double)2, (double)(counter - 4));
	    		scoreCounter+=counter-3;
	    	}
	    	if(!isContinue)
	    		counter = 0;
	    }
	    
	    counter = 0;
	    isContinue = false;
	    //going up
	    for(int i = 0; i < grid.length; i++){
	    	for(int j = 0; j < grid[0].length; j++){
	    		if(j < grid[j].length - 3 && i > 2 && grid[i][j].equals(grid[i-1][j+1]) && grid[i][j].equals(grid[i-2][j+2]) && grid[i][j].equals(grid[i-3][j+3])){ 
	    			aListTemp.add(new Diamond(i, j, grid[i][j])); 
	    			counter++;
	    		}
	    		else if(j > 0 && i > 1 && j < grid[j].length - 2 && i < grid[j].length -1 && grid[i+1][j-1].equals(grid[i][j]) && grid[i-1][j+1].equals(grid[i][j]) && grid[i-2][j+2].equals(grid[i][j])){ 
	    			aListTemp.add(new Diamond(i, j, grid[i][j])); 
	    			counter++;
	    		}
	    		else if(j > 1 && i <grid[j].length-2 && j < grid[j].length - 1 && i > 0 && grid[i][j].equals(grid[i-1][j+1]) && grid[i+1][j-1].equals(grid[i][j]) && grid[i+2][j-2].equals(grid[i][j])){ 
	    			aListTemp.add(new Diamond(i, j, grid[i][j]));
	    			counter++;
	    		}
	    		else if(j > 2 && i<grid[j].length-3 && grid[i][j].equals(grid[i+1][j-1]) && grid[i+2][j-2].equals(grid[i][j]) && grid[i+3][j-3].equals(grid[i][j])){ 
	    			aListTemp.add(new Diamond(i, j, grid[i][j]));
	    			counter++;
	    		}
	    	}
	    	if(counter>0 && i<grid.length-1)
	    		isContinue = true;
	    	else 
	    		isContinue = false;
	    	if(counter == 4 && doMarkAndUpdateScore && !isContinue){
	    		score = score + BASE_SCORE;
	    		life++;
	    		scoreCounter++;
	    	}
	    	else if(counter > 4 && doMarkAndUpdateScore && !isContinue){
	    		score = score + (BASE_SCORE * (int)Math.pow((double)2, (double)(counter - 4)));		
	    		life += (int)Math.pow((double)2, (double)(counter - 4));
	    		scoreCounter+=counter-3;
	    	}
	    	if(!isContinue)
	    		counter = 0;
	    }
	    
	    if(doMarkAndUpdateScore){
	    	for(int i = 0; i < aListTemp.size(); i++){
	    		setIcon(aListTemp.get(i).row(), aListTemp.get(i).col(), null);
	    	}
	    }
	    if(aListTemp.size()>0){	    
	    	for(int i=0;i<aListTemp.size();i++){
	    		if(aListTemp.get(i).getIcon().getSpecial()==1){
	    			System.out.println("aa");
	    			magic++;
	    		}
	    	}
	    }
	    return aListTemp;
	}


	public ArrayList<Diamond> collapseColumn(int col) {
		ArrayList<Diamond> aList = new ArrayList<Diamond>();
		
		for(int i = grid.length-1; i > 0; i--){
			int switchRow = i;
			while(grid[switchRow][col] == null && switchRow > 0){ 
				switchRow--; 
			}
			if(switchRow != i && !(grid[i][col] == null && grid[switchRow][col] == null)){
				grid[i][col] = grid[switchRow][col];
				grid[switchRow][col] = null;
				Diamond temp = new Diamond(i, col, grid[i][col]);
				temp.setPreviousRow(switchRow);
				aList.add(temp);
			}
		}
	    return aList;
	}


	public ArrayList<Diamond> fillColumn(int col) {
		ArrayList<Diamond> aList = new ArrayList<Diamond>();
		  
	    for(int i = 0; i < grid.length; i++){ 
	    	if(grid[i][col] != null){ 
	    		break;
	    	}
	    	else if(grid[i][col] == null){
	    		setIcon(i, col, gen.generate());
	    		aList.add(new Diamond(i, col, grid[i][col])); 
	    	}
	    }
	    
	    return aList;
	}
	
	public void update(){
		switch (currentState) {
		case RUNNING:
			updateRunning();
			break;

		default:
			break;
		}
	}
	
	public void updateRunning(){
        if (flashingState == 0 && !collapsing && !filling){
        	diamondsToCollapse = findMatch(true);
            if (diamondsToCollapse.size() != 0){
            	flashingState = numberOfFlashes * 10;
            } else {
				diamondsToCollapse = null;
			}
        }
        
        if (flashingState > 0){
        	flashingState--;
        	if (flashingState == 0){
        		ArrayList<Diamond> currentMovedDiamonds = new ArrayList<Diamond>();
        		for (int col = 0; col <getWidth(); ++col){
        			currentMovedDiamonds.addAll(collapseColumn(col));
        		}
        		collapsing = true;
        		movingDiamonds = new ArrayList<AnimatedDiamond>();
        		for (Diamond d : currentMovedDiamonds){
        			movingDiamonds.add(new AnimatedDiamond(d,screenHeight,getHeight()));
        		}         
        	}
        }
        
        if (collapsing){
        	boolean found = false;
        	for (AnimatedDiamond diamond : movingDiamonds){
        		if (!diamond.done()){
        			found = true;
        			diamond.animate(fallPerFrame);
        		}
        	}
        	if (!found){
        		collapsing = false;
        		movingDiamonds = null;
                initializeCellsToFill();
                filling = true;
                if (fillingDiamonds.size() == 0){
                	System.out.println("WARNING: game returned collapsing cells but failed to return cells to fill columns");
                	filling = false;
                	fillingDiamonds = null;
                }
        	}
       }
        
       if (filling){
          boolean found = false;
          for (AnimatedDiamond diamond : fillingDiamonds){
        	  if (!diamond.done()){
        		  found = true;
        		  diamond.animate(fallPerFrame);
        	  }
          }
          if (!found){
        	  filling = false;
        	  fillingDiamonds = null;
        	  for(int i=0;i<scoreCounter;i++)
        		  gen.setOneSpecial(grid);
        	  scoreCounter = 0;
          }
       }
       
       if(life==0)
    	   currentState = GameState.GAMEOVER;
	}
	
	private void initializeCellsToFill(){
	    fillingDiamonds = new ArrayList<AnimatedDiamond>();
	    for (int col = 0; col < getWidth(); ++col){
	    	ArrayList<Diamond> currentNewDiamonds = fillColumn(col);
	    	for (Diamond d : currentNewDiamonds){
	    		fillingDiamonds.add(new AnimatedDiamond(d,-1,screenHeight,getHeight()));
	    	}
	    }
	  }

}



