package com.scnu.show;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.scnu.manager.SaveManager;

/**
 * 主菜单 — 三个按钮:开始新游戏 / 选择关卡 / 选择存档
 */
public class MainMenuJPanel extends JPanel {

	private GameJFrame frame;
	private ImageIcon background;
	private String[] menuItems = { "开始新游戏", "选择关卡", "选择存档" };
	private int selectedIndex = -1;

	private final int BTN_X = 280;
	private final int BTN_W = 240;
	private final int BTN_H = 45;
	private final int BTN_START_Y = 280;
	private final int BTN_GAP = 15;

	public MainMenuJPanel(GameJFrame frame) {
		this.frame = frame;
		this.background = new ImageIcon("image/login_background.png");
		setLayout(null);

		addMouseMotionListener(new MouseAdapter() {
			@Override
			public void mouseMoved(MouseEvent e) {
				int old = selectedIndex;
				selectedIndex = -1;
				for (int i = 0; i < menuItems.length; i++) {
					int y = BTN_START_Y + i * (BTN_H + BTN_GAP);
					if (hitTest(e.getX(), e.getY(), BTN_X, y, BTN_W, BTN_H)) {
						selectedIndex = i;
						break;
					}
				}
				if (old != selectedIndex) repaint();
			}
		});

		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				for (int i = 0; i < menuItems.length; i++) {
					int y = BTN_START_Y + i * (BTN_H + BTN_GAP);
					if (hitTest(e.getX(), e.getY(), BTN_X, y, BTN_W, BTN_H)) {
						onClick(i);
						return;
					}
				}
			}
		});
	}

	private boolean hitTest(int mx, int my, int x, int y, int w, int h) {
		return mx >= x && mx <= x + w && my >= y && my <= y + h;
	}

	private void onClick(int index) {
		switch (index) {
		case 0: // 开始新游戏
			int slot = SaveManager.createNewSave();
			if (slot < 0) {
				JOptionPane.showMessageDialog(this, "存档位已满（最多3个），请先删除旧存档。");
				return;
			}
			frame.setCurrentSaveSlot(slot);
			frame.switchToGame(1, slot);
			break;
		case 1: // 选择关卡
			if (frame.getCurrentSaveSlot() <= 0) {
				JOptionPane.showMessageDialog(this, "请先选择一个存档（「选择存档」按钮）。");
				return;
			}
			frame.switchToLevelSelect();
			break;
		case 2: // 选择存档
			frame.switchToSaveSelect();
			break;
		}
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		if (background != null) {
			g.drawImage(background.getImage(), 0, 0, getWidth(), getHeight(), null);
		} else {
			g.setColor(new Color(0x2B, 0x2B, 0x2B));
			g.fillRect(0, 0, getWidth(), getHeight());
		}

		g.setFont(new Font("微软雅黑", Font.PLAIN, 20));
		for (int i = 0; i < menuItems.length; i++) {
			int y = BTN_START_Y + i * (BTN_H + BTN_GAP);

			// 选择关卡无存档时显示灰色
			boolean disabled = (i == 1 && frame.getCurrentSaveSlot() <= 0);

			if (i == selectedIndex && !disabled) {
				g.setColor(new Color(0xFF, 0xD7, 0x00));
				g.fillRoundRect(BTN_X, y, BTN_W, BTN_H, 10, 10);
				g.setColor(Color.BLACK);
			} else if (disabled) {
				g.setColor(new Color(0x44, 0x44, 0x44, 180));
				g.fillRoundRect(BTN_X, y, BTN_W, BTN_H, 10, 10);
				g.setColor(Color.GRAY);
			} else {
				g.setColor(new Color(0x55, 0x55, 0x55, 180));
				g.fillRoundRect(BTN_X, y, BTN_W, BTN_H, 10, 10);
				g.setColor(Color.WHITE);
			}
			int sw = g.getFontMetrics().stringWidth(menuItems[i]);
			g.drawString(menuItems[i], BTN_X + (BTN_W - sw) / 2, y + BTN_H - 12);
		}

		// 底部存档提示
		if (frame.getCurrentSaveSlot() > 0) {
			g.setFont(new Font("微软雅黑", Font.PLAIN, 14));
			g.setColor(Color.GREEN);
			g.drawString("当前存档: 存档位 " + frame.getCurrentSaveSlot(),
					10, getHeight() - 10);
		}
	}
}