package com.mygdx.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mygdx.game.Main;
import com.mygdx.game.configuration.ConfigurationBuilder;
import com.mygdx.game.configuration.Configuration;

import java.awt.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();

		// Base configuration
		config.title = "RoboRaider";
		config.width = 640;
		config.height = 480;
		config.resizable = false;
		config.vSyncEnabled = true;

		ConfigurationBuilder configurationBuilder = new ConfigurationBuilder();

		try {
			configurationBuilder.load(new FileInputStream("Configuration.json"));
		} catch(Exception e) {
			try {
				InputStream is = DesktopLauncher.class.getClassLoader().getResourceAsStream("Configuration.json");
				configurationBuilder.load(is);
			} catch(Exception fe) {
				System.out.println(fe);
			}
		}
		Configuration configurationData = configurationBuilder.config();

		new LwjglApplication(new Main(configurationData), config);
	}
}
