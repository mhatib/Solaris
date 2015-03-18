package com.zhouyunke.diamondcrash.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.zhouyunke.diamondcrash.DiamondCrash;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.title = "Diamond Crush";
        config.width = 272;
        config.height = 408;
        new LwjglApplication(new DiamondCrash(), config);
	}
}
