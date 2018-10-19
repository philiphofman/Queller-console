package br.wotr.util;

public class MessageUtil {

	public String getStrategyAsString(char strategy) {
		return (strategy == 'c' ? "Corruption" : "Military");
	}

	public char switchStrategy(char strategy) {
		if (strategy == 'c') {
			strategy = 'm';
		} else {
			strategy = 'c';
		}
		a("Quller switched to " + getStrategyAsString(strategy) + " Strategy");
		return strategy;
	}

	public String a(String s) {
		System.out.println(">>> " + s + " <<<");
//		FileUtil.toLog(">>> " + s + " <<<\n");
		return s;
	}

	public String m(String s) {
		System.out.println(s);
//		FileUtil.toLog(s + "\n");
		return s;
	}

	public String q(String s) {
		System.out.print(s);
//		FileUtil.toLog(s);
		return s;
	}

	public void printPhaseHeader(int phaseNumber) {
		if (phaseNumber == 1) {
			m("-------------------------------------------------------");
			m("| Phase " + phaseNumber + ") - Recover Action Dice and Draw Event Cards |");
			m("-------------------------------------------------------");

		}
		if (phaseNumber == 2) {
			m("-------------------------------");
			m("| Phase " + phaseNumber + ") - Fellowship Phase |");
			m("-------------------------------");
		}
		if (phaseNumber == 3) {
			m("------------------------------");
			m("| Phase " + phaseNumber + ") - Hunt Allocation |");
			m("------------------------------");
		}
		if (phaseNumber == 4) {
			m("--------------------------");
			m("| Phase " + phaseNumber + ") - Action Roll |");
			m("--------------------------");
		}
		if (phaseNumber == 5) {
			m("--------------------------------");
			m("| Phase " + phaseNumber + ") - Action Resolution |");
			m("--------------------------------");
		}
		if (phaseNumber == 6) {
			m("----------------------------");
			m("| Phase " + phaseNumber + ") - Victory Check |");
			m("----------------------------");
		}
	}

	public void printPriorities(String filename) {
		m(FileUtil.getContents(filename));
	}

	public void printQuellerPool() {
		m("Pool:  " + DiceUtil.quellerDicePool);
	}

	public void printQuellerStrategy(char s) {
		m("Strategy: " + getStrategyAsString(s));
	}

	public void printSavedAndDiscardedDice() {
		m("Saved: " + DiceUtil.quellerDiceSaved);
		m("Discarded: " + DiceUtil.quellerDiceDiscarded);
	}

	public void printTurnNumber(int turnNumber) {
		m("----------");
		m("| Turn " + turnNumber + " |");
		m("----------");
	}

	public void printRollNotOk() {
		a("Wrong format. Format: \"X X X X X\", where X is a die result");
		q("What did Queller get? ");
	}
	
	public void startingStrategy(char c) {
		m("Starting Strategy: " + getStrategyAsString(c));
	}

	public void welcomeMessage() {
		m("Queller - A Shadow Player bot for War of the Ring by Quitch");
	}
}
