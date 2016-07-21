package org.fczm.common.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * Mengular Document for Java Template Engine
 * @version 2.1
 * @author 浮尘追梦
 */
public class MengularDocument {
	//定义模板文件默认文字编码为UTF-8
	public final static String DEFAULT_TEMPLATE_CHARACTER_ENCODING = "UTF-8";
	//定义输出文件默认文字编码为UTF-8
	public final static String DEFAULT_OUTPUT_CHARACTER_ENCODING = "UTF-8";
	
	private String rootPath;
	private int depth;
	private String outputPath;
	
	//是否允许设置loop，一旦设置占位符后会生成整体文档，不允许生成loop
	private boolean setLoopEnable = true;
	private String document;
	//模板根据loop部分拆分后的段落
	private List < String >  paragraphs;
	//模板列表，包括id和模板内容
	private SortedMap < String, String >  templates;
	//循环输出列表，包括id和输出内容
	private SortedMap < String, String >  loops;
	
	public String getRootPath() {
		return rootPath;
	}
	
	/**
	 * 使用默认模板文件文字编码的构造方法
	 * @param rootPath Web项目根目录
	 * @param depth 相对深度
	 * @param templatePath 模板文件路径
	 * @param outputPath 输出文件路径
	 */
	public MengularDocument(String rootPath, int depth, String templatePath, String outputPath) {
		super();
		this.rootPath = rootPath;
		this.outputPath = outputPath;
		init(templatePath, depth, DEFAULT_TEMPLATE_CHARACTER_ENCODING);
	}

	/**
	 * 自定义模板文件文字编码的构造方法
	 * @param rootPath Web项目根目录
	 * @param depth 相对深度
	 * @param templatePath 模板文件路径
	 * @param outputPath 输出文件路径
	 * @param templateCharacterEncoding 自定义文字编码
	 */
	public MengularDocument(String rootPath, int depth, String templatePath, String outputPath, String templateCharacterEncoding) {
		super();
		this.rootPath  =  rootPath;
		this.outputPath = outputPath;
		init(templatePath,  depth, templateCharacterEncoding);
	}
	
	/**
	 * 初始化方法
	 * @param templatePath 模板文件路径
	 * @param templateCharacterEncoding 自定义文字编码
	 */
	@SuppressWarnings("resource")
	private void init(String templatePath, int depth, String templateCharacterEncoding) {
		this.depth = depth;
		File templateFile = new File(rootPath + templatePath);
		if(!templateFile.isFile()||!templateFile.exists()) {
			try {
				throw new Exception("Mengular Document: Template file not found.");
			} catch (Exception e) {
				e.printStackTrace();
			}
			return;
		}
		try {
			InputStreamReader streamReader = new InputStreamReader(new FileInputStream(templateFile), templateCharacterEncoding);
			BufferedReader bufferedReader = new BufferedReader(streamReader);
			String line = null;
			paragraphs = new ArrayList<>();
			templates = new TreeMap<>();
			String paragraph = "", template = "", key = null;
			boolean isTemplate = false;
			while((line = bufferedReader.readLine()) != null) {
				if(line.contains("<!--mengular-start")) {
					paragraphs.add(paragraph);
					paragraph = "";
					isTemplate = true;
					key = line.split("mengular-start=\"")[1].split("\"")[0];
					continue;
				}
				if(line.contains("<!--mengular-end-->")) {
					if(key == null) {
						throw new Exception("No mengular start tag found!");
					}
					templates.put(key, template);
					template = "";
					isTemplate = false;
					key = null;
					continue;
				}
				if(isTemplate) {
					template += line + "\n";
				} else {
					paragraph += line + "\n";
				}
			}
			paragraphs.add(paragraph);
			bufferedReader.close();
			
			loops = new TreeMap<>();
			for(String k: templates.keySet()) {
				loops.put(k, "");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 填充占位符
	 * @param key 占位符名称
	 * @param value 占位符值
	 */
	public void setValue(String key, String value) {
		//如果设置可以循环，则关闭设置循环并生成整体文档
		if(setLoopEnable) {
			setLoopEnable = false;
			document = "";
			for(String paragraph: paragraphs) {
				document += paragraph;
				if(loops.size() > 0) {
					document += loops.get(loops.firstKey());
					loops.remove(loops.firstKey());
				}
			}
			String back = "";
			for(int i = 0; i < depth; i++) {
				back += "../";
			}
			document = document.replace("href=\"", "href=\"" + back).replace("src=\"", "src=\"" + back);
		}
		document = document.replace("#{" + key + "}", value);
	}
	
	/**
	 * 填充循环
	 * @param id 循环id
	 * @param items 循环值
	 */
	public void setLoop(String id, List<Map<String, String>>  items) {
		if(!setLoopEnable) {
			try {
				throw new Exception("Mengular Document: Cannot set loop after setting placeholder value.");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		String template = templates.get(id);
		if(template == null) {
			try {
				throw new Exception("Mengular Document: Cannot found template where id=" + id);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		String loop, document = "";
		for(Map<String, String>  item: items) {
			loop = template;
			for(String key: item.keySet()) {
				loop = loop.replace("#{" + key + "}#", item.get(key));
			}
			document += loop;
		}
		loops.put(id, loops.get(id) + "\n" + document);
	}
	
	/**
	 * 使用默认文字编码输出
	 * @param outputPath 输出文件路径
	 */
	public void output() {
		output(DEFAULT_OUTPUT_CHARACTER_ENCODING);
	}
	
	/**
	 * 自定义文字编码输出
	 * @param outputCharacterEncoding 自定义输出文件文字编码
	 */
	public void output(String outputCharacterEncoding) {
		File output = new File(rootPath + outputPath);
		try {
			if (!output.exists()) {
				output.createNewFile();
			} 
			OutputStreamWriter streamWriter = new OutputStreamWriter(new FileOutputStream(output), outputCharacterEncoding);
			BufferedWriter bufferedWriter = new BufferedWriter(streamWriter);
			bufferedWriter.write(document);
			bufferedWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}