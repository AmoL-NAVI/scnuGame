package com.scnu.controller;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.scnu.element.ElementObj;
import com.scnu.manager.ElementManager;
import com.scnu.manager.GameElement;

/**
 * @说明:监听类 用于监听用户的操作KeyListener
 */
public class GameListener implements KeyListener{
	private ElementManager em = ElementManager.getManager();
	private Set<Integer> set = new HashSet<Integer>();
	
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO 自动生成的方法存根
		
	}
	
	/**
	 * 上w87 下s88 左a65 右d68
	 */
	@Override
	public void keyPressed(KeyEvent e) {
		// TODO 自动生成的方法存根
		System.out.println("KeyPressed: " + e.getKeyCode());
		int key = e.getKeyCode();
		if (set.contains(key)) {
			// 包含则结束方法
			return;
		} else {
			// 不包含则加入set
			set.add(key);
		}
		
		List<ElementObj> playerList = em.getElementByKey(GameElement.PLAYER);
		for (ElementObj obj:playerList) {
			obj.keyClick(true,e.getKeyCode());
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO 自动生成的方法存根
		System.out.println("KeyReleased: " + e.getKeyCode());
		int key = e.getKeyCode();
		if (set.contains(key)) {
			// 包含则移除set
			set.remove(key);
		} else {
			// 不包含则结束方法
			return;
		}
		
		List<ElementObj> playerList = em.getElementByKey(GameElement.PLAYER);
		for (ElementObj obj:playerList) {
			obj.keyClick(false,e.getKeyCode());
		}
	}

	
	
	
	
	
	
	
	
	
}
