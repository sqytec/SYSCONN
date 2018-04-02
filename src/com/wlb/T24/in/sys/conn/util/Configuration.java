package com.wlb.T24.in.sys.conn.util;

import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

public class Configuration implements Constants {
	public static Properties getProperites() {
		/*String path = System.getProperty("user.dir");
		path += "/MQSelection";
		path += "/MQSeletion.properties";*/
		String path = "F:\\workspace_git\\SYSCONN\\MQSeletion\\MQSeletion.properties";
		System.out.println(path);
		File file = new File(path);
		Properties properties = new Properties();
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(file);
			properties.load(fis);
		} catch (Exception e) {
			System.out.println("Get [field.properties] failed!");
			Closer.close(new Closeable[] { fis });
		} finally {
			Closer.close(new Closeable[] { fis });
		}
		return properties;
	}
}
