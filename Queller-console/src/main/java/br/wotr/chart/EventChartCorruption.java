package br.wotr.chart;

import br.wotr.bot.Main;
import br.wotr.util.ConstantUtil;
import br.wotr.util.DiceUtil;

public class EventChartCorruption extends DieChart {
	/**
	 * Start the flow in the chart
	 * 
	 * @param entry The entry point in the chart flow.
	 * @return True if a possible action was executed with the die.
	 */
	public String runChart(String die, int entry) {
		super.runChart(die, entry);

		// Entry points
		if (entryPoint == 1) {
			trunk = 1;
		}
		if (entryPoint == 2) {
			trunk = 4;
		}

		// Decide action
		while (chartResult == null) {
			if (trunk == 1) {
				if (Main.isBaseGame()) {
					mu.q("Holding a playable Character Card?");
				} else {
					mu.q("Holding a playable Character Card OR Faction Card with Character symbol?");
				}
				if (Main.yes()) {
					if (Main.isBaseGame()) {
						fu.printPriorities("EventChart-Corruption-T1-Yes-Base");
					} else {
						fu.printPriorities("EventChart-Corruption-T1-Yes");
					}
					chartResult = mu.a(ConstantUtil.getPlaycard());
					continue;
				}
				trunk = 2;
			}
			if (trunk == 2) {
				mu.a("Die being used: " + dieToUse);
				mu.q("Using P die?");
				if (!Main.yes()) {
					chartResult = mu.a(ConstantUtil.getContinuedieselection());
					continue;
				} else {
					branch = 1;
					if (branch == 1) {
						mu.q("Less than 5 Event cards?");
						if (Main.yes()) {
							chartResult = mu.a(ConstantUtil.getDrawcharactercard());
							continue;
						}
						branch = 2;
					}
					if (branch == 2) {
						mu.q("Holding a playable Strategy Card?");
						if (Main.yes()) {
							fu.printPriorities("EventChart-Corruption-T2-B2");
							chartResult = mu.a(ConstantUtil.getPlaycard());
							continue;
						}
						branch = 3;
					}
					if (branch == 3) {
						if (!Main.isBaseGame()) {
							mu.q("Holding less than 3 Faction Cards?");
							if (Main.yes()) {
								chartResult = mu.a(ConstantUtil.getDrawfactioncard());
								continue;
							}
						}
						chartResult = mu.a(ConstantUtil.getDrawstrategycard());
						continue;
					}
				}
			}
			if (trunk == 4) {
				mu.q("Holding \"FSP revealed\" card?");
				if (Main.yes()) {
					chartResult = mu.a(ConstantUtil.getPlaycard());
					continue;
				}
				trunk = 5;
			}
			if (trunk == 5) {
				mu.q("Less than 6 cards?");
				if (Main.yes()) {
					chartResult = mu.a(ConstantUtil.getDrawcharactercard());
				} else {
					chartResult = mu.a(ConstantUtil.getContinuedieselection());
				}
				continue;
			}
		}

		// Corruption - Verify which actions consume the die
		if (chartResult.equals(ConstantUtil.getDrawstrategycard())) {
			mu.q("Over 6 Event cards?");
			if (Main.yes()) {
				mu.q("All 6 are Character Cards?");
				if (Main.yes()) {
					fu.printPriorities("EventChart-Corruption-T3-B1-Yes");
				} else {
					fu.printPriorities("EventChart-Corruption-T3-B1-No");
				}
				mu.a(ConstantUtil.getDiscard());
			} else {
				mu.a(ConstantUtil.getEnd());
			}
		}
		if (!chartResult.equals(ConstantUtil.getContinuedieselection())) {
			chartResult = DiceUtil.useDie(dieToUse);
		}
		return chartResult;
	}
}
