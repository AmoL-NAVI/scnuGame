package com.scnu.manager;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import com.scnu.element.Player;

public class SaveManager {

	private static final String SAVE_DIR = "save/";
	private static final int MAX_SLOTS = 3;
	private static final SimpleDateFormat FMT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	public static class SaveData {
		public int attack = 1;
		public int maxHp = 5;
		public int speed = 1;
		public int speedCharge = 0;
		public long shootInterval = 20;
		public int attackSpeedCharge = 0;
		public List<Integer> clearedLevels = new ArrayList<>();
		public String saveTime;

		public int getClearedCount() { return clearedLevels.size(); }
		public boolean isLevelCleared(int lv) { return clearedLevels.contains(lv); }
		public boolean isLevelUnlocked(int lv) {
			return lv <= 1 || clearedLevels.contains(lv - 1);
		}
	}

	public static int createNewSave() {
		List<Integer> ex = listSlots();
		for (int i = 1; i <= MAX_SLOTS; i++) {
			if (!ex.contains(i)) {
				Properties p = new Properties();
				p.setProperty("player.attack","1");
				p.setProperty("player.maxHp","5");
				p.setProperty("player.speed","1");
				p.setProperty("player.speedCharge","0");
				p.setProperty("player.shootInterval","20");
				p.setProperty("player.attackSpeedCharge","0");
				p.setProperty("clearedLevels","");
				p.setProperty("saveTime",FMT.format(new Date()));
				saveProperties(i, p);
				return i;
			}
		}
		return -1;
	}

	public static void save(int slot, Player player, List<Integer> clearedLevels) {
		Properties p = new Properties();
		p.setProperty("player.attack", String.valueOf(player.getAttack()));
		p.setProperty("player.maxHp", String.valueOf(player.getMaxHp()));
		p.setProperty("player.speed", String.valueOf(player.getSpeed()));
		p.setProperty("player.speedCharge", String.valueOf(player.getSpeedCharge()));
		p.setProperty("player.shootInterval", String.valueOf(player.getShootInterval()));
		p.setProperty("player.attackSpeedCharge", String.valueOf(player.getAttackSpeedCharge()));
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < clearedLevels.size(); i++) {
			if (i > 0) sb.append(",");
			sb.append(clearedLevels.get(i));
		}
		p.setProperty("clearedLevels", sb.toString());
		p.setProperty("saveTime", FMT.format(new Date()));
		saveProperties(slot, p);
	}

	private static void saveProperties(int slot, Properties p) {
		new File(SAVE_DIR).mkdirs();
		try (FileOutputStream out = new FileOutputStream(SAVE_DIR + "slot_" + slot + ".dat")) {
			p.store(out, "Tank Game Save Slot " + slot);
		} catch (IOException e) {
			System.err.println("保存存档失败: " + e.getMessage());
		}
	}

	public static SaveData load(int slot) {
		File f = new File(SAVE_DIR + "slot_" + slot + ".dat");
		if (!f.exists()) return null;
		SaveData d = new SaveData();
		Properties p = new Properties();
		try (FileInputStream in = new FileInputStream(f)) { p.load(in); }
		catch (IOException e) { System.err.println("读取存档失败: " + e.getMessage()); return null; }

		d.attack = gi(p,"player.attack",1);
		d.maxHp = gi(p,"player.maxHp",5);
		d.speed = gi(p,"player.speed",1);
		d.speedCharge = gi(p,"player.speedCharge",0);
		d.shootInterval = gl(p,"player.shootInterval",20);
		d.attackSpeedCharge = gi(p,"player.attackSpeedCharge",0);
		d.saveTime = p.getProperty("saveTime","未知");
		String lv = p.getProperty("clearedLevels","");
		if (!lv.isEmpty()) {
			for (String s : lv.split(","))
				try { d.clearedLevels.add(Integer.parseInt(s.trim())); } catch (NumberFormatException ignored) {}
		}
		return d;
	}

	public static void applyToPlayer(SaveData d, Player player) {
		if (d == null || player == null) return;
		player.setAttack(d.attack);
		player.setMaxHp(d.maxHp); player.setHp(d.maxHp);
		player.setSpeed(d.speed);
		player.setSpeedCharge(d.speedCharge);
		player.setShootInterval(d.shootInterval);
		player.setAttackSpeedCharge(d.attackSpeedCharge);
	}

	public static boolean slotExists(int slot) { return new File(SAVE_DIR + "slot_" + slot + ".dat").exists(); }
	public static void delete(int slot) { new File(SAVE_DIR + "slot_" + slot + ".dat").delete(); }

	public static List<Integer> listSlots() {
		List<Integer> r = new ArrayList<>();
		File dir = new File(SAVE_DIR);
		if (!dir.exists()) return r;
		File[] fs = dir.listFiles((d,n) -> n.startsWith("slot_") && n.endsWith(".dat"));
		if (fs != null) for (File f : fs)
			try { r.add(Integer.parseInt(f.getName().replace("slot_","").replace(".dat",""))); } catch (NumberFormatException ignored) {}
		return r;
	}

	public static boolean hasEmptySlot() { return listSlots().size() < MAX_SLOTS; }

	private static int gi(Properties p, String k, int d) {
		try { return Integer.parseInt(p.getProperty(k)); } catch (Exception e) { return d; }
	}
	private static long gl(Properties p, String k, long d) {
		try { return Long.parseLong(p.getProperty(k)); } catch (Exception e) { return d; }
	}
}
