package com.scnu.show;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;

import com.scnu.manager.SaveManager;
import com.scnu.manager.SaveManager.SaveData;

/**
 * 存档选择 — 最多 3 个存档位
 */
public class SaveSelectJPanel extends JPanel {

	private GameJFrame frame;
	private int hoverIndex = -1;

	private final int SLOT_X = 180;
	private final int SLOT_W = 440;
	private final int SLOT_H = 90;
	private final int SLOT_START_Y = 150;
	private final int SLOT_GAP = 20;
	private final int MAX_SLOTS = 3;

	public SaveSelectJPanel(GameJFrame frame) {
		this.frame = frame;
		setLayout(null);

		addMouseMotionListener(new MouseAdapter() {
			@Override
			public void mouseMoved(MouseEvent e) {
				int old = hoverIndex;
				hoverIndex = -1;
				for (int i = 0; i < MAX_SLOTS; i++) {
					int y = SLOT_START_Y + i * (SLOT_H + SLOT_GAP);
					if (e.getX() >= SLOT_X && e.getX() <= SLOT_X + SLOT_W
							&& e.getY() >= y && e.getY() <= y + SLOT_H) {
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
				for (int i = 0; i < MAX_SLOTS; i++) {
					int y = SLOT_START_Y + i * (SLOT_H + SLOT_GAP);
					if (e.getX() >= SLOT_X && e.getX() <= SLOT_X + SLOT_W
							&& e.getY() >= y && e.getY() <= y + SLOT_H) {
						onSlotClick(i);
						return;
					}
				}
				for (int i = 0; i < MAX_SLOTS; i++) {
					int y = SLOT_START_Y + i * (SLOT_H + SLOT_GAP);
					int delX = SLOT_X + SLOT_W + 10;
					if (e.getX() >= delX && e.getX() <= delX + 50
							&& e.getY() >= y && e.getY() <= y + SLOT_H) {
						int slotNum = i + 1;
						if (SaveManager.slotExists(slotNum)) {
							SaveManager.delete(slotNum);
							if (frame.getCurrentSaveSlot() == slotNum) {
								frame.setCurrentSaveSlot(0);
							}
							repaint();
						}
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

	private void onSlotClick(int slotIndex) {
		int slotNum = slotIndex + 1;
		if (SaveManager.slotExists(slotNum)) {
			frame.setCurrentSaveSlot(slotNum);
			frame.switchToMainMenu();
		}
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		g.setColor(new Color(0x2B, 0x2B, 0x2B));
		g.fillRect(0, 0, getWidth(), getHeight());

		g.setFont(new Font("微软雅黑", Font.BOLD, 32));
		g.setColor(Color.WHITE);
		String title = "选择存档";
		int tx = (getWidth() - g.getFontMetrics().stringWidth(title)) / 2;
		g.drawString(title, tx, 100);

		for (int i = 0; i < MAX_SLOTS; i++) {
			int slotNum = i + 1;
			int y = SLOT_START_Y + i * (SLOT_H + SLOT_GAP);
			boolean exists = SaveManager.slotExists(slotNum);
			boolean active = (slotNum == frame.getCurrentSaveSlot());

			if (i == hoverIndex && exists) {
				g.setColor(new Color(0xFF, 0xD7, 0x00, 100));
			} else if (exists) {
				g.setColor(new Color(0x55, 0x55, 0x55, 200));
			} else {
				g.setColor(new Color(0x33, 0x33, 0x33, 150));
			}
			g.fillRoundRect(SLOT_X, y, SLOT_W, SLOT_H, 10, 10);

			if (active) {
				g.setColor(Color.GREEN);
				g.drawRoundRect(SLOT_X, y, SLOT_W, SLOT_H, 10, 10);
			}

			g.setFont(new Font("微软雅黑", Font.BOLD, 18));
			g.setColor(exists ? Color.WHITE : Color.GRAY);
			g.drawString("存档位 " + slotNum, SLOT_X + 20, y + 30);

			if (exists) {
				SaveData data = SaveManager.load(slotNum);
				if (data != null) {
					g.setFont(new Font("微软雅黑", Font.PLAIN, 13));
					g.setColor(Color.LIGHT_GRAY);
					g.drawString("ATK:" + data.attack + "  HP:" + data.maxHp
							+ "  SPD:" + data.speed,
							SLOT_X + 20, y + 55);
					g.drawString("已通关 " + data.getClearedCount() + " 关  |  "
							+ data.saveTime, SLOT_X + 20, y + 75);

					g.setColor(Color.RED);
					g.setFont(new Font("微软雅黑", Font.PLAIN, 14));
					g.drawString("删除", SLOT_X + SLOT_W + 15, y + 30);
				}
			} else {
				g.setFont(new Font("微软雅黑", Font.PLAIN, 14));
				g.setColor(Color.GRAY);
				g.drawString("空存档位", SLOT_X + 20, y + 52);
			}
		}

		g.setFont(new Font("微软雅黑", Font.PLAIN, 16));
		g.setColor(Color.LIGHT_GRAY);
		g.drawString("< 返回主菜单", 20, getHeight() - 20);
	}
}
