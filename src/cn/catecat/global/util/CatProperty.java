package cn.catecat.global.util;

import java.io.IOException;
import java.util.Properties;

import cn.catecat.image.dao.impl.ImageImpl;

public class CatProperty {
	private static Properties path=new Properties();
	private static Properties scanRule=new Properties();
	static{
		try {
			//配置文件读取
			path.load(ImageImpl.class.getClassLoader().getResourceAsStream("path.properties"));
			scanRule.load(ImageImpl.class.getClassLoader().getResourceAsStream("scanRule.properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			if(path.isEmpty()){
				throw new RuntimeException("路径配置文件不存在！path.propeties");
			}
			if(scanRule.isEmpty()){
				throw new RuntimeException("路径配置文件不存在！scanRule.propeties");
			}
		}
	}
	/**
	 * 获取配置文件值
	 * @param type	配置文件类型
	 * @param key	属性Key
	 * @return
	 */
	public static String getPropertyValue(String type, String key){
		if(type==null)return null;
		if(type.equalsIgnoreCase("path")){
			return path.getProperty(key);
		}else if(type.equalsIgnoreCase("scanRule")){
			return scanRule.getProperty(key);
		}
		return null;
	}
}
