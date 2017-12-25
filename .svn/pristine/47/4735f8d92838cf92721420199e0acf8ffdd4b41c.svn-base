package com.pengyue.bbsTools.conf;

import java.io.IOException;
import java.util.Properties;
import java.util.logging.LogManager;

import com.pengyue.bbsTools.bbscore.util.IOUtils;

public abstract class Constants {
	public static final Properties FILE_CONSTANTS = new Properties();
	static {
	
		IOUtils.loadFile2Prop("config.txt", FILE_CONSTANTS);
		System.getProperties().putAll(FILE_CONSTANTS);
	}
}
