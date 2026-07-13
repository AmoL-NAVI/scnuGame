package com.scnu.manager;

import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import javax.swing.ImageIcon;

import com.scnu.element.ElementObj;
import com.scnu.element.MapObj;
import com.scnu.element.Player;

/**
 * @说明-加载器(工具类 用于读取配置文件)
 * 		大多提供static方法
 */
public class GameLoad {
	private static ElementManager em = ElementManager.getManager();
	public static Map<String, ImageIcon> imgMap = new HashMap<String, ImageIcon>();
	
	
	public static Properties pro = new Properties();
	
	/**
	 * @说明 传入地图ID由加载方法依据文件规则自动产生地图文件名称
	 * @param mapId
	 */
	public static void MapLoad(int mapId) {
		// 构建文件路径
		String mapName = "com/scnu/text/" + mapId + ".map";
		// 使用IO流读取文件对象
		ClassLoader classLoader = GameLoad.class.getClassLoader();
		InputStream maps = classLoader.getResourceAsStream(mapName);
		System.out.println(maps);
		if (maps == null) {
			System.out.println("配置文件读取异常");
			return;
		}
		
		try {
			// 动态获取所有key,有key就有value
			pro.clear();
			pro.load(maps);
			Enumeration<?> names = pro.propertyNames();
			while (names.hasMoreElements()) {
				String key = names.nextElement().toString();
				System.out.println(pro.getProperty(key));
				String [] arrs = pro.getProperty(key).split(";");
				for (int i = 0; i < arrs.length; i++) {
					ElementObj element = new MapObj().createElement(key + "," + arrs[i]);
					em.addElement(element, GameElement.MAPS);
				}
			}
			
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
	}
	
	/**
	 * @说明-加载图片方法
	 * 
	 */
	public static void loadImg() {
		String texturl="com/scnu/text/GameData.pro";
		ClassLoader classLoader = GameLoad.class.getClassLoader();
		InputStream texts = classLoader.getResourceAsStream(texturl);
		
		try {
			pro.clear();
			pro.load(texts);
			Set<Object> set = pro.keySet();
			for(Object object : set) {
				String url = pro.getProperty(object.toString());
				imgMap.put(object.toString(), new ImageIcon(url));
			}
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}

	}
	
	/**
	 * 加载玩家
	 */
	public static void loadPlayer() {
		loadObj();
		// TODO 自动生成的方法存根
		String playString = "500,500,up";
//		ElementObj play = new Player().createElement(playString);
		
		ElementObj obj = getObj("player");
		
		ElementObj play = obj.createElement(playString);
		em.addElement(play, GameElement.PLAYER);
	}
	
	public static ElementObj getObj(String str) {
		try {
			Class<?> class1 = objMap.get(str);
			Object newInstance = class1.newInstance();
			if (newInstance instanceof ElementObj) {
				return (ElementObj)newInstance;  // 此时return等价于new Player();
			}
		} catch (InstantiationException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 拓展:使用配置文件实例化对象
	 * 
	 */
	private static Map<String, Class<?>> objMap = new HashMap<String, Class<?>>();
	public static void loadObj() {
		String texturl="com/scnu/text/obj.pro";
		ClassLoader classLoader = GameLoad.class.getClassLoader();
		InputStream texts = classLoader.getResourceAsStream(texturl);
		
		try {
			pro.clear();
			pro.load(texts);
			Set<Object> set = pro.keySet();
			for(Object object : set) {
				String classUrl = pro.getProperty(object.toString());
				// 使用反射的方式直接获取类
				Class<?> forName = Class.forName(classUrl);
				objMap.put(object.toString(), forName);
			}
		} catch (IOException | ClassNotFoundException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
	}
	
	// 用于测试
	public static void main(String[] args) {
	}


}
