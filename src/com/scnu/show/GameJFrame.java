package com.scnu.show;

import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * @说明:游戏窗体 实现的主要功能:关闭 显示，最大最小化
 * @功能说明:需要嵌入面板，启动主线程等等
 * @窗体说明 swing awt 窗体大小(记录用户上次使用的软件窗体样式)
 * 
 * @分析 	1.面板绑定到窗体
 * 	    2.监听绑定
 * 		3.游戏主线程启动
 * 		4.显示窗体
 */
public class GameJFrame extends JFrame{
	public static int GameX = 800;
	public static int GameY = 600;
	private JPanel jPanel = null;
	private KeyListener keyListener = null;	//监听
	private MouseMotionListener mouseMotionListener = null;
	private MouseListener mouseListener = null;
	private Thread thread = null;				//游戏主线程
	
	public GameJFrame() {
		init();
	}
	
	public void init() {
		this.setSize(GameX,GameY);
		this.setTitle("测试游戏-坦克世界");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
	}
	
	// 拓展-窗体布局: 可以添加存档，读档等按钮布局
//	public void addButton() {
//		this.setLayout(manager);
//	}
	
	// 启动方法
	public void start() {
		if (jPanel != null) {
			this.add(jPanel);
		}
		if (keyListener != null) {
			this.addKeyListener(keyListener);
		}
		if (thread != null) {
			thread.start();
		}
		
		// 显示界面
		this.setVisible(true);
		if(this.jPanel instanceof Runnable) {
			new Thread((Runnable)this.jPanel).start();
		}
	}
	

	// set注入
	public void setjPanel(JPanel jPanel) {
		this.jPanel = jPanel;
	}

	public void setKeyListener(KeyListener keyListener) {
		this.keyListener = keyListener;
	}

	public void setMouseMotionListener(MouseMotionListener mouseMotionListener) {
		this.mouseMotionListener = mouseMotionListener;
	}

	public void setMouseListener(MouseListener mouseListener) {
		this.mouseListener = mouseListener;
	}

	public void setThread(Thread thread) {
		this.thread = thread;
	}
	
	
	
}
