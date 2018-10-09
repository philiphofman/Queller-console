package br.wotr.util;

public class MessageUtil {

	// Map<String,String>

	public void welcomeMessage() {
		System.out.println(
				"Queller - A Shadow Player bot for War of the Ring by Quitch");
	}

	public void startingStrategy(char c) {
		System.out.println("Starting Strategy: " + getStrategyAsString(c));
	}

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
		return s;
	}

	public void m(String s) {
		System.out.println(s);
	}

	public void q(String s) {
		System.out.print(s);
	}

	public void printPhaseHeader(int phaseNumber) {
		if (phaseNumber == 1) {
			System.out.println(
					"-------------------------------------------------------");
			System.out.println("| Phase " + phaseNumber
					+ ") - Recover Action Dice and Draw Event Cards |");
			System.out.println(
					"-------------------------------------------------------");

		}
		if (phaseNumber == 2) {
			System.out.println("-------------------------------");
			System.out.println(
					"| Phase " + phaseNumber + ") - Fellowship Phase |");
			System.out.println("-------------------------------");
		}
		if (phaseNumber == 3) {
			System.out.println("------------------------------");
			System.out.println(
					"| Phase " + phaseNumber + ") - Hunt Allocation |");
			System.out.println("------------------------------");
		}
		if (phaseNumber == 4) {
			System.out.println("--------------------------");
			System.out.println("| Phase " + phaseNumber + ") - Action Roll |");
			System.out.println("--------------------------");
		}
		if (phaseNumber == 5) {
			System.out.println("--------------------------------");
			System.out.println(
					"| Phase " + phaseNumber + ") - Action Resolution |");
			System.out.println("--------------------------------");
		}
		if (phaseNumber == 6) {
			System.out.println("----------------------------");
			System.out
					.println("| Phase " + phaseNumber + ") - Victory Check |");
			System.out.println("----------------------------");
		}
	}

	public void printTurnNumber(int turnNumber) {
		System.out.println("----------");
		System.out.println("| Turn " + turnNumber + " |");
		System.out.println("----------");
	}

	public void printQuellerPool() {
		System.out.println("Queller pool: " + DiceUtil.quellerDicePool);
	}

	public void printSavedAndDiscardedDice() {
		System.out.println("Saved:      " + DiceUtil.quellerDiceSaved);
		System.out.println("Discarded:  " + DiceUtil.quellerDiceDiscarded);
	}

	public void printQuellerStrategy(char s) {
		System.out.println("Current strategy: " + getStrategyAsString(s));
	}

}
