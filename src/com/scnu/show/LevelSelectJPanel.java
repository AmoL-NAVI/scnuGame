package com.scnu.show;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import com.scnu.manager.SaveManager;
import com.scnu.manager.SaveManager.SaveData;

/**
 * 关卡选择 — 仅显示已解锁的关卡
 */
public class LevelSelectJPanel extends JPanel {

	private GameJFrame frame;
	private int saveSlot;
	private SaveData saveData;

	private final int COLS = 5;
	private final int CELL_W = 100;
	private final int CELL_H = 80;
	private final int GRID_GAP = 20;
	private final int GRID_X = (800 - (COLS * CELL_W + (COLS - 1) * GRID_GAP)) / 2;
	private final int GRID_Y = 180;
	private final int TOTAL_LEVELS = 10;

	private int hoverIndex = -1;

	public LevelSelectJPanel(GameJFrame frame, int saveSlot) {
		this.frame = frame;
		this.saveSlot = saveSlot;
		this.saveData = (saveSlot > 0) ? SaveManager.load(saveSlot) : null;
		if (this.saveData == null) this.saveData = new SaveData();

		setLayout(null);
		addMouseMotionListener(new MouseAdapter() {
			@Override
			public void mouseMoved(MouseEvent e) {
				int old = hoverIndex;
				hoverIndex = -1;
				for (int i = 0; i < TOTAL_LEVELS; i++) {
					int col = i % COLS, row = i / COLS;
					int x = GRID_X + col * (CELL_W + GRID_GAP);
					int y = GRID_Y + row * (CELL_H + GRID_GAP);
					if (e.getX() >= x && e.getX() <= x + CELL_W
							&& e.getY() >= y && e.getY() <= y + CELL_H
							&& saveData.isLevelUnlocked(i + 1)) {
						hoverIndex = i;
						break;
					}
				}
				if (old != hoverIndex) repaint();
			}
		});

		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getY() > getHeight() - 40 && e.getX() < 160) {
					frame.switchToMainMenu();
					return;
				}
				for (int i = 0; i < TOTAL_LEVELS; i++) {
					int col = i % COLS, row = i / COLS;
					int x = GRID_X + col * (CELL_W + GRID_GAP);
					int y = GRID_Y + row * (CELL_H + GRID_GAP);
					if (e.getX() >= x && e.getX() <= x + CELL_W
							&& e.getY() >= y && e.getY() <= y + CELL_H
							&& saveData.isLevelUnlocked(i + 1)) {
						frame.switchToGame(i + 1, saveSlot);
						return;
					}
				}
			}

			@Override
			public void mouseExited(MouseEvent e) {
				hoverIndex = -1;
				repaint();
			}
		});
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		g.setColor(new Color(0x2B, 0x2B, 0x2B));
		g.fillRect(0, 0, getWidth(), getHeight());

		g.setFont(new Font("微软雅黑", Font.BOLD, 32));
		g.setColor(Color.WHITE);
		String title = "选择关卡";
		int tx = (getWidth() - g.getFontMetrics().stringWidth(title)) / 2;
		g.drawString(title, tx, 100);

		g.setFont(new Font("微软雅黑", Font.PLAIN, 14));
		g.setColor(Color.GREEN);
		g.drawString("存档位 " + saveSlot + " | 已通关: " + saveData.getClearedCount() + " 关", 20, 30);

		g.setFont(new Font("微软雅黑", Font.PLAIN, 18));
		for (int i = 0; i < TOTAL_LEVELS; i++) {
			int col = i % COLS, row = i / COLS;
			int x = GRID_X + col * (CELL_W + GRID_GAP);
			int y = GRID_Y + row * (CELL_H + GRID_GAP);

			boolean unlocked = saveData.isLevelUnlocked(i + 1);
			boolean cleared = saveData.isLevelCleared(i + 1);

			if (i == hoverIndex) {
				g.setColor(new Color(0xFF, 0xD7, 0x00));
			} else if (cleared) {
				g.setColor(new Color(0x28, 0xA7, 0x45));
			} else if (unlocked) {
				g.setColor(new Color(0x55, 0x55, 0x55, 200));
			} else {
				g.setColor(new Color(0x33, 0x33, 0x33, 150));
			}
			g.fillRoundRect(x, y, CELL_W, CELL_H, 12, 12);

			g.setColor(unlocked ? Color.WHITE : Color.GRAY);
			String num = String.valueOf(i + 1);
			int nx = x + (CELL_W - g.getFontMetrics().stringWidth(num)) / 2;
			g.drawString(num, nx, y + CELL_H / 2 + 6);

//			if (cleared) {
//				g.setFont(new Font("微软雅黑", Font.BOLD, 24));
//				g.setColor(Color.GREEN);
//				g.drawString("✓", x + CELL_W - 30, y + 28);
//				g.setFont(new Font("微软雅黑", Font.PLAIN, 18));
//			} else if (!unlocked) {
//				g.setFont(new Font("微软雅黑", Font.PLAIN, 18));
//				g.setColor(Color.DARK_GRAY);
//				g.drawString("🔒", x + CELL_W - 28, y + CELL_H - 10);
//				g.setFont(new Font("微软雅黑", Font.PLAIN, 18));
//			}
		}

		g.setFont(new Font("微软雅黑", Font.PLAIN, 16));
		g.setColor(Color.LIGHT_GRAY);
		g.drawString("< 返回主菜单", 20, getHeight() - 20);
	}
}
