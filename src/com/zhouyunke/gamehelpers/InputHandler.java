package com.zhouyunke.gamehelpers;



import com.badlogic.gdx.InputProcessor;
import com.zhouyunke.gameobjects.*;
import com.zhouyunke.gameworld.GameWorld;

public class InputHandler implements InputProcessor {
	
	GameWorld myWorld;

    public InputHandler(GameWorld world) {
        myWorld = world;
    }

	@Override
	public boolean keyDown(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		if(myWorld.isRunning()){
			int row = (int) ((screenY-(myWorld.getscreenHeight()-myWorld.SIZE*myWorld.getHeight())/2)/myWorld.SIZE);
			int col = (int) ((screenX-(myWorld.getScreenWidth()-myWorld.SIZE*myWorld.getWidth())/2)/myWorld.SIZE);
			if(row>-1&&col>-1&&row<myWorld.getHeight()&&col<myWorld.getWidth()&&myWorld.getLife()>0){
				if(myWorld.getCurrentDiamond()==null)
					myWorld.setCurrentDiamond(row, col, myWorld.getIcon(row, col));
				else
					if(myWorld.getNextDiamond()==null&&isAdjacent(myWorld.getCurrentDiamond(),row,col)){
						myWorld.setNextDiamond(row, col, myWorld.getIcon(row, col));
						if(myWorld.getCurrentDiamond().getIcon() != myWorld.getNextDiamond().getIcon()){
							swap(myWorld.getCurrentDiamond(), myWorld.getNextDiamond());
							myWorld.setLife(myWorld.getLife()-1);
							for(int i=0;i<myWorld.getHeight();i++){
								for(int j=0;j<myWorld.getWidth();j++){
									if(myWorld.getIcon(i, j).getSpecial()==1){
										if(myWorld.getIcon(i,j).getSpecialState()>0)
											myWorld.getIcon(i,j).reduceSpecialStateByOne();
										else
											myWorld.getIcon(i, j).setSpecial(0);
									}
								}
							}
						}
/*				if(myWorld.findMatch(false).isEmpty()){ 
					swap(myWorld.getCurrentDiamond(), myWorld.getNextDiamond());
				}*/
				 		myWorld.setCurrentDiamondToNull();
						myWorld.setNextDiamondToNull();
					}else{
						myWorld.setCurrentDiamond(row, col, myWorld.getIcon(row, col));
						myWorld.setNextDiamondToNull();
					}
				}
		
		} else {
			if(screenY<myWorld.getscreenHeight()/4){
				myWorld.restart();}
		}
		return true;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}
	
	private boolean isAdjacent(Diamond a, int row, int col){
		if(a.row()==row||Math.abs(a.row()-row)==1){
			if(a.col()==col||Math.abs(a.col()-col)==1)
				return true;
		}
		return false;
	}
	
	private void swap(Diamond current, Diamond next){
		Icon tmpIcon = myWorld.getIcon(current.row(),current.col());
		myWorld.setIcon(current.row(),current.col(),myWorld.getIcon(next.row(), next.col()));
		myWorld.setIcon(next.row(), next.col(), tmpIcon);
	}

}
