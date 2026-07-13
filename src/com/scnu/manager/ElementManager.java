package com.scnu.manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.scnu.element.ElementObj;

/**
 * @说明 本类是元素管理器，用于存储所有的元素，同时提供方法给与视图
 * 		和控制获取数据
 */
public class ElementManager {
	/*
	 * String作为Key匹配所有的元素
	 * player -> List<Object> listPlayer
	 */
	private Map<GameElement, List<ElementObj>> gameElements;

	public Map<GameElement, List<ElementObj>> getGameElements() {
		return gameElements;
	}
	// 添加元素->多半给加载器调用
	public void addElement(ElementObj obj, GameElement ge) {
		gameElements.get(ge).add(obj);
	}
	// 读取单个元素->根据Key返回list集合，返回指定类元素
	public List<ElementObj> getElementByKey(GameElement ge){
		return gameElements.get(ge);
	}
	
	/*
	 * 单例模式:内存中有且只有一个实例
	 * 饿汉模式-启动自动加载实例
	 * 饱汉模式-需要使用再加载实例
	 * 
	 * 编写方式:
	 * 1.需要一个静态的属性(定义一个常量) 单例的引用
	 * 2.提供一个静态的方法(返回这个实例) return单例的引用
	 * 3.一般为了防止其他人使用(类可实例化),所以会私有构造方法
	 */
	private static ElementManager EM=null; //引用
	
	public static synchronized ElementManager getManager() {
		if (EM == null) {
			EM = new ElementManager();
		}
		
		return EM;
	}
	
	private ElementManager() {
		 init();
	}

	/*
	 * 本方法是为了将来可能出现的拓展，重写init方法准备的
	 */
	public void init() {
		// TODO 自动生成的方法存根
		gameElements = new HashMap<GameElement,List<ElementObj>>();
		
//		gameElements.put(GameElement.PLAYER, new ArrayList<ElementObj>());
//		gameElements.put(GameElement.MAPS, new ArrayList<ElementObj>());
//		gameElements.put(GameElement.ENEMY, new ArrayList<ElementObj>());
//		gameElements.put(GameElement.BOSS, new ArrayList<ElementObj>());
		for(GameElement ge: GameElement.values()) {
			gameElements.put(ge, new ArrayList<ElementObj>());
		}
		
	}
}
