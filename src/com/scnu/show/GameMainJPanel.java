package com.scnu.show;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import com.scnu.element.ElementObj;
import com.scnu.element.Player;
import com.scnu.manager.ElementManager;
import com.scnu.manager.GameElement;

/**
 * @说明:游戏的主要面板
 * @功能说明:主要进行元素的显示，同时进行界面的刷新(多线程)
 * 
 * @多线程刷新方法 	1.本类中实现线程接口
 * 				2.本类中定义一个内部类来实现
 */
public class GameMainJPanel extends JPanel implements Runnable{
	// 联动管理器
	private ElementManager em;
	
	public GameMainJPanel() {
		init();
	}

	private void init() {
		// TODO 自动生成的方法存根
		em = ElementManager.getManager();
	}
	
	// 用于绘画
	@Override
	public void paint(Graphics g) {
		// TODO 自动生成的方法存根
		super.paint(g);
		
		Map<GameElement, List<ElementObj>> all = em.getGameElements();

		// 显示所有实体
		for(GameElement ge:GameElement.values()) {
			List<ElementObj> list = all.get(ge);
			for(int i = 0; i <list.size(); i++) {
				list.get(i).showElement(g);
			}
		}
		
//		Set<GameElement> set = all.keySet();
//		for (GameElement ge:set) {
//			List<ElementObj> list = all.get(ge);
//			for(int i = 0; i <list.size(); i++) {
//				list.get(i).showElement(g);
//			}
//		}
		
	}

	@Override
	public void run() {
		// TODO 自动生成的方法存根
		while(true) {
			this.repaint();
			// 控制速度加入休眠
			try {
				Thread.sleep(10);// 100帧
			} catch (InterruptedException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
			
			
		}
	}
}
