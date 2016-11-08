package org.fczm.common.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * @author 浮尘追梦
 * @version 1.1
 */
public class JsonTool {
	String pathname = null;
	JSONObject object = null;
	
	/**
	 * 使用文件路径初始化
	 * @param pathname
	 */
	public JsonTool(String pathname) {
		super();
		File file = new File(pathname);
		if(!file.exists()) {
			try {
				throw new Exception("Json File is not exsit in file system.");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		BufferedReader bufferedReader = null;
		String content = "";
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
		object = JSONObject.fromObject(content);
		this.pathname = pathname;
	}
	
	/**
	 * 使用json对象初始化
	 * @param jsonObject
	 */
	public JsonTool(JSONObject jsonObject) {
		object = jsonObject;
	}
	 
	/**
	 * 获取json设置对象
	 * @return
	 */
	public JSONObject getJSONConfig() {
		return this.object;
	}
	
	/**
	 * 设置json设置对象
	 * @param object
	 */
	public void setJSONConfig(JSONObject object) {
		this.object = object;
	}

    /**
     * 获取json字符串
     * @return
     */
    public String getJSONString() {
    	return object.toString();
    }
    
    /**
     * 获取格式化json字符串
     * @return
     */
    public String getFormatJSONString() {
    	return formatJson(object.toString());
    }
    
	/**
	 * 获取对象属性
	 * @param key
	 * @return
	 */
	public Object getObject(String key) {
		return object.get(key);
	}
	
	/**
	 * 获取json对象属性
	 * @param key
	 * @return
	 */
	public JSONObject getJSONObject(String key) {
		return object.getJSONObject(key);
	}
	
	/**
	 * 获取json数组属性
	 * @param key
	 * @return
	 */
	public JSONArray getJSONArray(String key) {
		return object.getJSONArray(key);
	}
	
	/**
	 * 获取字符串属性
	 * @param key
	 * @return
	 */
	public String getString(String key) {
		return object.getString(key);
	}
	
	/**
	 * 获取整数属性
	 * @param key
	 * @return
	 */
	public int getInt(String key) {
		return object.getInt(key);
	}
	
	/**
	 * 获取浮点数属性
	 * @param key
	 * @return
	 */
	public double getDouble(String key) {
		return object.getDouble(key);
	}
	
	/**
	 * 写入属性
	 * @param key
	 * @param value
	 */
	public void put(String key, Object value) {
		object.put(key, value);
	}
	
    /**
     * 写入json字符串到指定路径文件
     * @param pathname
     * @return
     */
    public boolean writeObject(String pathname) {
    	if(pathname == null || object == null) {
    		return false;
    	}
    	File file = new File(pathname);
    	if(!file.exists()) {
    		return false;
    	}
    	try {
			BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file));
			bufferedWriter.write(getFormatJSONString());
			bufferedWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
    	return true;
    }
    
    /**
     * 写入json字符串到默认路径
     * @return
     */
    public boolean writeObject() {
    	return writeObject(pathname);
    }
    

    /**
     * 返回指定次数的缩进字符串。每一次缩进三个空格，即SPACE。
     * @param number 缩进次数。
     * @return 指定缩进次数的字符串。
     */
    private String indent(int number) {
        StringBuffer result = new StringBuffer();
        for(int i = 0; i < number; i++) {
            result.append("    ");
        }
        return result.toString();
    }
    
    /**
     * 返回格式化JSON字符串。
     * @param json 未格式化的JSON字符串。
     * @return 格式化的JSON字符串。
     */
    public String formatJson(String json) {
        StringBuffer result = new StringBuffer();
        int length = json.length();
        int number = 0;
        char key = 0;
        for (int i = 0; i < length; i++) {
            key = json.charAt(i);
            if((key == '[') || (key == '{') ) {
                if((i - 1 > 0) && (json.charAt(i - 1) == ':')) {
                    result.append('\n');
                    result.append(indent(number));
                }
                result.append(key);
                result.append('\n');
                number++;
                result.append(indent(number));
                continue;
            }
            if((key == ']') || (key == '}') ) {
                result.append('\n');
                number--;
                result.append(indent(number));
                result.append(key);
                if(((i + 1) < length) && (json.charAt(i + 1) != ',')) {
                    result.append('\n');
                }
                continue;
            }
            if((key == ',')) {
                result.append(key);
                result.append('\n');
                result.append(indent(number));
                continue;
            }
            result.append(key);
        }
        return result.toString();
    }
    
}
