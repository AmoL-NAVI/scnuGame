package com.scnu.element;

import java.awt.Graphics;
import java.util.Random;

import javax.swing.ImageIcon;

public class Enemy extends ElementObj{

	
	
	
	@Override
	public void showElement(Graphics g) {
		// TODO 自动生成的方法存根
		g.drawImage(this.getIcon().getImage(),
				getX(), getY(), 
				getW(), getH(), null);
	}
	
	@Override
	public ElementObj createElement(String str) {
		// TODO 自动生成的方法存根
		Random random = new Random();
		int x = random.nextInt(800);
		int y = random.nextInt(500);
		this.setX(x);
		this.setY(y);
		this.setW(50);
		this.setH(50);
		this.setIcon(new ImageIcon("image/tank/bot/bot_up.png"));
		
		return this;
	}
	
	@Override
	public void pkByOther(ElementObj other) {
		// TODO 自动生成的方法存根
		if (other instanceof PlayFile) {
			this.die();
		}
	}
	
	@Override
	public void die() {
		// TODO 自动生成的方法存根
		this.setLive(false);
	}
	
	
	
	
}
