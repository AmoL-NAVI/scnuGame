package com.scnu.element;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.ImageIcon;

import com.scnu.manager.ElementManager;
import com.scnu.manager.GameElement;

/**
 * @说明-本类是玩家子弹类，本类的实体对象是由玩家对象的调用和创建
 * @开发步骤:
 * 	1.继承基类，重写show
 * 	2.按需重写其他方法
 * 	3.思考并定义子类特有的属性
 */
public class PlayFile extends ElementObj{
	private int attack = 1;	// 攻击力
	private int moveNum = 3;	//移速
	private String fx;
	
	
	
	
	public PlayFile() {};
	
	@Override
	public ElementObj createElement(String str) {
		String[] split = str.split(",");
		for (String str1 : split) {
			String[] split2 = str1.split(":");
			switch(split2[0]) {
			case "x":
				this.setX(Integer.parseInt(split2[1]));
				break;
			case "y":
				this.setY(Integer.parseInt(split2[1]));
				break;
			case "f":
				this.fx = split2[1];
				break;
			}
		}
		changeLocate();
		this.setW(10);
		this.setH(10);
		
		return this;
	}

	@Override
	public void showElement(Graphics g) {
		// TODO 自动生成的方法存根

		g.setColor(Color.red);
		g.fillOval(this.getX(), this.getY(), this.getW(), this.getH());
	}
	
	@Override
	protected void move() {
		if(this.getX()<0 || this.getY()<0 || this.getX()>900 ||this.getY()>600) {
			this.setLive(false);
			return;
		}
		
		switch(this.fx) {
		case "up":
			this.setY(this.getY() - moveNum); 
			break;
		case "down":
			this.setY(this.getY() + moveNum);
			break;
		case "right":
			this.setX(this.getX() + moveNum);
			break;
		case "left":
			this.setX(this.getX() - moveNum);
			break;
		}
	}
	/**
	 * 对子弹来说:1.出边界 2.碰撞 3.
	 * 处理方式时-当达到死亡的条件时，只进行修改死亡状态的操作
	 */
	@Override
	public void pkByOther(ElementObj other) {
		// TODO 自动生成的方法存根
		if (other instanceof MapObj) {
			if (((MapObj) other).getType() == "river" || ((MapObj) other).getType() == "grass") {
				return;
			}else {
				this.die();
			}
		}else {
			this.die();
		}
	}
	
	

	private void changeLocate() {
		switch(this.fx) {
		case "up":
			this.setX(this.getX() + 13); 
			break;
		case "down":
			this.setX(this.getX() + 13); 
			this.setY(this.getY() + 40);
			break;
		case "right":
			this.setX(this.getX() + 40);
			this.setY(this.getY() +13);
			break;
		case "left":
			this.setY(this.getY() +13);
			break;
		}
	}
	
	@Override
	public void die() {
		// TODO 自动生成的方法存根
		this.setLive(false);
	}
	
	
	
	
	
	
}
