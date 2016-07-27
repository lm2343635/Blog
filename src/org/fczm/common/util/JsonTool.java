package org.fczm.common.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * @author 浮尘追梦
 * @version 1.0
 */
public class JsonTool {
	String pathname;
	String content;
	
	public JsonTool() {
		super();
		File file = new File(pathname);
		BufferedReader bufferedReader = null;
		content = "";
		try {
			bufferedReader = new BufferedReader(new FileReader(file));
			String tmp = null;
			while( (tmp = bufferedReader.readLine()) != null) {
				content += tmp;
			}
			bufferedReader.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				bufferedReader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}
}
