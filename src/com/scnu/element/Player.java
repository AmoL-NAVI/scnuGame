package com.scnu.element;

import java.awt.Graphics;
import java.security.PublicKey;
import java.util.HashMap;
import java.util.Map;

import javax.swing.ImageIcon;

import com.scnu.manager.ElementManager;
import com.scnu.manager.GameElement;
import com.scnu.manager.GameLoad;

public class Player extends ElementObj {
	/**
	 * 移动属性: 1.单属性 配合方向枚举类型使用;一次只能移动一个方向 2.双属性 上下和左右 3.四属性
	 */
	private boolean left = false;
	private boolean up = false;
	private boolean right = false;
	private boolean down = false;

	// 需要变量记录主角面向的方向，默认up
	private String fx = "up";
	// 攻击状态false不攻击 true攻击
	private boolean pkType = false;
	private ElementObj element;
	
	
	public Player() {};
	
	public Player(int x, int y, int w, int h, ImageIcon icon) {
		super(x, y, w, h, icon);
		// TODO 自动生成的构造函数存根
	}
	
	@Override
	public ElementObj createElement(String str) {
		String[] split = str.split(",");
		this.setX(Integer.parseInt(split[0]));
		this.setY(Integer.parseInt(split[1]));
		ImageIcon imageIcon = GameLoad.imgMap.get(split[2]);
		this.setW(imageIcon.getIconWidth());
		this.setH(imageIcon.getIconHeight());
		this.setIcon(imageIcon);
		
		return this;
	}

	@Override
	public void showElement(Graphics g) {
		// TODO 自动生成的方法存根
		g.drawImage(this.getIcon().getImage(), this.getX(), this.getY(), this.getW(), this.getH(), null);
	}

	@Override
	public void keyClick(boolean bl, int key) {
		// TODO 自动生成的方法存根
		if (bl) {
			switch (key) {
			case 65:
				this.up = false;
				this.down = false;
				this.right = false;
				this.left = true;
				this.fx="left";
				break;
			case 87:
				this.up = true;
				this.down = false;
				this.right = false;
				this.left = false;
				this.fx = "up";
				break;
			case 68:
				this.up = false;
				this.down = false;
				this.right = true;
				this.left = false;
				this.fx = "right";
				break;
			case 83:
				this.up = false;
				this.down = true;
				this.right = false;
				this.left = false;
				this.fx = "down";
				break;
			case 32:
				this.pkType = true;
				break;
			}
		} else {
			switch (key) {
			case 65:
				this.left = false;
				break;
			case 87:
				this.up = false;
				break;
			case 68:
				this.right = false;
				break;
			case 83:
				this.down = false;
				break;
			case 32:
				this.pkType = false;
			}
		}

	}

	@Override
	protected void move() {
		if (this.left && this.getX() > 0) {
			this.setX(this.getX() - 1);
		}
		if (this.up && this.getY() > 0) {
			this.setY(this.getY() - 1);
		}
		if (this.right && this.getX() < 800 - 2*this.getW()) {
			this.setX(this.getX() + 1);
		}
		if (this.down && this.getY() < 600 - 2*this.getH()) {
			this.setY(this.getY() + 1);
		}
	}
	
	@Override
	protected void updateImage(long gameTime) {
		// TODO 自动生成的方法存根
		this.setIcon(GameLoad.imgMap.get(fx));
	}
	
	private long fileTime=0;
	/**
	 * 子弹的添加，需要:发射者坐标，发射者方向
	 */
	@Override
	protected void add(long gameTime) {
		if(!this.pkType) {
			return;
		}
		if(gameTime - fileTime <= 20) {			
			return;
		}
		fileTime = gameTime;
		// TODO 自动生成的方法存根
		// 传递一个固定格式 {X:3,Y:5,f:up}
		ElementObj element = new PlayFile().createElement(this.toString());
		ElementManager.getManager().addElement(element, GameElement.PLAYFILE);
		
	}

	@Override
	public String toString() {
		// TODO 自动生成的方法存根
		// {X:3,Y:5,f:up} json格式
		return "x:" + this.getX() + ",y:" + this.getY() + ",f:" + this.fx;
	}
	
	
	
	
}
