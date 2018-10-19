package br.wotr.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

public class DiceUtil {
	static List<String> quellerDicePool = new ArrayList<String>();
	static List<String> quellerDiceSaved = new ArrayList<String>();
	static List<String> quellerDiceDiscarded = new ArrayList<String>();
	static MessageUtil mu = new MessageUtil();

	public static void addDie(String die) {
		quellerDicePool.add(die);
	}

	public static void removeDie(String die) {
		if (!poolIsEmpty()) {
			quellerDicePool.remove(die);
		} else {
			quellerDiceSaved.remove(die);
		}
		quellerDiceDiscarded.add(die);
	}

	public static void resetDice() {
		quellerDiceDiscarded.clear();
	}

	public static int rollD6() {
		Random random = new Random();
		return random.nextInt(6) + 1;
	}

	public static void saveDie(String die) {
		quellerDicePool.remove(die);
		quellerDiceSaved.add(die);
	}

	public static void setQuellerRoll(String s) {
		String[] roll = s.split(" ");
		for (String dieFace : roll) {
			if (!dieFace.equals("E")) {
				quellerDicePool.add(dieFace);
			}
		}
	}

	public static boolean betweenOneAndThree(int number) {
		if (number >= 1 && number <= 3) {
			return true;
		}
		return false;
	}

	public static boolean poolIsEmpty() {
		return quellerDicePool.isEmpty();
	}

	public static boolean savedDiceIsEmpty() {
		return quellerDiceSaved.isEmpty();
	}

	public static boolean hasDie() {
		if (!(quellerDicePool.isEmpty() && quellerDiceSaved.isEmpty())) {
			return true;
		}
		return false;
	}

	public static boolean isInPool(String die) {
		return quellerDicePool.contains(die);
	}

	public static boolean isInSaved(String dieFace) {
		return quellerDiceSaved.contains(dieFace);
	}

	public static char initialStrategy() {
		int die = rollD6();
		if (betweenOneAndThree(die)) {
			return 'c';
		} else {
			return 'm';
		}
	}

	public static String useDie(String die) {
		mu.m("Using " + die + " die");
		removeDie(die);
		return ConstantUtil.getUsed();
	}

	// TODO K - Saved Dice will always have only 1 die?
	public static String getDieFromSaved() {
		String savedDie = quellerDiceSaved.get(0);
		removeDie(quellerDiceSaved.get(0));
		return savedDie;
	}

	public static String mostCommonDie() {
		Map<String, Integer> map = new HashMap<>();
		for (String die : quellerDicePool) {
			Integer val = map.get(die);
			map.put(die, val == null ? 1 : val + 1);
		}
		Entry<String, Integer> max = null;
		for (Entry<String, Integer> e : map.entrySet()) {
			if (max == null || e.getValue() > max.getValue())
				max = e;
		}
		return max.getKey();
	}

}
