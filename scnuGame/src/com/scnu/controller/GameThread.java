package com.scnu.controller;

import java.util.List;
import java.util.Map;

import javax.swing.ImageIcon;

import com.scnu.element.ElementObj;
import com.scnu.element.Enemy;
import com.scnu.element.Player;
import com.scnu.manager.ElementManager;
import com.scnu.manager.GameElement;
import com.scnu.manager.GameLoad;

/**
 * @说明:游戏的主线程，用于控制游戏加载，游戏关卡，游戏运行时自动化和游戏判断...
 */
public class GameThread extends Thread{
	private ElementManager em;
	
	public GameThread() {
		em = ElementManager.getManager();
	}
	
	@Override
	public void run() {
		while (true) { // 拓展-可以将true变为一个变量用于控制结束
			// 游戏开始前-加载资源
			gameLoad();
			// 游戏进行时
			gameRun();
			// 游戏场景结束-资源回收
			gameOver();
			
			try {
				sleep(10);
			} catch (InterruptedException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
		}
	}

	/**
	 * 游戏的加载在这里完成
	 */
	private void gameLoad() {
		// TODO 自动生成的方法存根
//		System.out.println("调用加载器");
		// 加载图片
		GameLoad.loadImg();
		// 加载地图
		GameLoad.MapLoad(5);
		// 加载主角
		GameLoad.loadPlayer();
		//加载敌人/NPC
	}
	
	/**
	 * 游戏进行时
	 * @任务说明-游戏过程中需要做的事情:	1.自动化玩家的移动，碰撞，死亡
	 * 							2.新元素的增加(NPC死亡后出现道具...)
	 * 							3.暂停...
	 */
	
	private void gameRun() {
		// TODO 自动生成的方法存根
		long gameTime=0L;
		while (true) { // 拓展-true变为变量，用于控制关卡结束
			Map<GameElement, List<ElementObj>> all = em.getGameElements();
			List<ElementObj> enemy = em.getElementByKey(GameElement.ENEMY);
			List<ElementObj> file = em.getElementByKey(GameElement.PLAYFILE);
			List<ElementObj> maps = em.getElementByKey(GameElement.MAPS);
			
			// 游戏元素自动化方法
			moveUpdate(all, gameTime);
			// 碰撞方法
			ElementPK(enemy,file);
			ElementPK(file, maps);
			
			gameTime++;		
			try {
				sleep(10);
			} catch (InterruptedException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
		}
	}
	private void ElementPK(List<ElementObj> listA, List<ElementObj> listB) {
		// TODO 自动生成的方法存根
		for (int i = 0; i < listA.size(); i++) {
			for (int j = 0; j < listB.size(); j++) {
				if(listA.get(i).pk(listB.get(j))) {
					// 拓展-不直接die改为受攻击方法
					listA.get(i).pkByOther(listB.get(j));
					listB.get(j).pkByOther(listA.get(i));
					break;
				}
			}
		}
	}

	// 游戏元素自动化方法
	public void moveUpdate(Map<GameElement, List<ElementObj>> all,Long gameTime) {
		for(GameElement ge:GameElement.values()) {
			List<ElementObj> list = all.get(ge);
			for(int i = 0; i <list.size(); i++) {
				ElementObj obj = list.get(i);
				if (!obj.isLive()) {
					// 死亡之前启动死亡方法
					obj.die();
					list.remove(i--);
					continue;
				}
				obj.model(gameTime);
			}
		}
	}

	/**
	 * 游戏切换关卡
	 */
	private void gameOver() {
		// TODO 自动生成的方法存根
		
	}
	
	
	
	
	
	
	
	
	
}
