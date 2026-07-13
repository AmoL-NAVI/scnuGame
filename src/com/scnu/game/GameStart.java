package com.scnu.game;

import com.scnu.controller.GameListener;
import com.scnu.controller.GameThread;
import com.scnu.show.GameJFrame;
import com.scnu.show.GameMainJPanel;

public class GameStart {
	/**
	 * 程序唯一入口
	 */
	public static void main(String[] args) {
		// TODO 自动生成的方法存根
		GameMainJPanel jp = new GameMainJPanel();
		
		// 实例化面板
		GameJFrame gj = new GameJFrame();
		// 实例化监听
		GameListener listener = new GameListener();
		// 实例化主线程
		GameThread th = new GameThread();
		
		// 注入
		gj.setjPanel(jp);
		gj.setKeyListener(listener);
		gj.setThread(th);
		
		gj.start();
	}

}
