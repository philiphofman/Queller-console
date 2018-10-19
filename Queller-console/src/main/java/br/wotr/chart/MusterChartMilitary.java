package br.wotr.chart;

import br.wotr.bot.Main;
import br.wotr.util.ConstantUtil;
import br.wotr.util.DiceUtil;

public class MusterChartMilitary extends DieChart {
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
				mu.q("A Stronghold is under THREAT AND Can Muster?");
				if (Main.yes()) {
					mu.printPriorities(ConstantUtil.getFmcprisecer());
					chartResult = mu.a(ConstantUtil.getMuster());
					continue;
				}
				trunk = 2;
			}
			if (trunk == 2) {
				if (!DiceUtil.savedDiceIsEmpty()) {
					chartResult = ConstantUtil.getHassaveddie();
					mu.m("*** " + ConstantUtil.getHassaveddie() + "***");
				} else {
					mu.q("Can muster Minion?");
					if (Main.yes()) {
						mu.q("FP has Will of the West die AND Gandalf The White not recruited AND No Minions recruited?");
						if (Main.yes()) {
							chartResult = mu.a(ConstantUtil.getSavemusterdieforlast());
							continue;
						} else {
							mu.printPriorities(ConstantUtil.getFmcminions());
							mu.printPriorities(ConstantUtil.getFccwitchking());
							mu.printPriorities(ConstantUtil.getFccmouth());
							chartResult = mu.a(ConstantUtil.getMusterminion());
							continue;
						}
					}
				}
				trunk = 3;
			}
			if (trunk == 3) {
				if (Main.isBaseGame()) {
					mu.q("SP Nation not At War??");
				} else {
					mu.q("SP Nation not At War OR No Faction is in play?");
				}
				if (Main.yes()) {
					if (Main.isBaseGame()) {
						mu.printPriorities(ConstantUtil.getFmcnationsbase());
					} else {
						mu.printPriorities(ConstantUtil.getFmcnations());
					}
					if (Main.isBaseGame()) {
						chartResult = mu.a(ConstantUtil.getAdvancenation());
					} else {
						mu.q("Faction was highest priority?");
						if (Main.yes()) {
							chartResult = mu.a(ConstantUtil.getRecruitfaction());
						} else {
							chartResult = mu.a(ConstantUtil.getAdvancenation());
						}
					}
					continue;
				}
				trunk = 4;
			}
			if (trunk == 4) {
				mu.q("Holding playable Muster card?");
				if (Main.yes()) {
					mu.q("Card allows choice of Muster location?");
					if (!Main.yes()) {
						chartResult = mu.a(ConstantUtil.getMuster());
						continue;
					} else {
						branch = 2;
					}
				} else {
					trunk = 5;
				}
			}
			if (trunk == 5) {
				mu.q("SP can Muster?");
				if (Main.yes()) {
					branch = 2;
				} else {
					chartResult = mu.a(ConstantUtil.getContinuedieselection());
					continue;
				}
			}
			if (branch == 2) {
				mu.q("Can create an EXPOSED TARGET?");
				if (Main.yes()) {
					mu.printPriorities(ConstantUtil.getFmcprisecer());
					chartResult = mu.a(ConstantUtil.getMuster());
					continue;
				}
				branch = 3;
			}
			if (branch == 3) {
				mu.q("Muster possible in region with SP army?");
				if (Main.yes()) {
					mu.printPriorities(ConstantUtil.getFmcmusterinsparmy());
					mu.printPriorities(ConstantUtil.getFmcprisecer());
					chartResult = mu.a(ConstantUtil.getMuster());
					continue;
				}
				mu.printPriorities(ConstantUtil.getFmcsettlementclosest());
				branch = 4;
			}
			if (branch == 4) {
				mu.q("Have less than 6 Nazgûl?");
				if (Main.yes()) {
					mu.printPriorities(ConstantUtil.getFmcprisecen());
				} else {
					mu.printPriorities(ConstantUtil.getFmcprisecnn());
				}
				chartResult = mu.a(ConstantUtil.getMuster());
				continue;
			}
		}

		// Military - Verify which actions consume the die
		if (chartResult.equals(ConstantUtil.getSavemusterdieforlast())
				|| chartResult.equals(ConstantUtil.getHassaveddie())) {
			if (chartResult.equals(ConstantUtil.getSavemusterdieforlast())) {
				DiceUtil.saveDie(dieToUse);
			}
			chartResult = ConstantUtil.getUsed();
		}

		if (chartResult.equals(ConstantUtil.getMuster()) || chartResult.equals(ConstantUtil.getMusterminion())
				|| chartResult.equals(ConstantUtil.getAdvancenation())) {
			chartResult = DiceUtil.useDie(dieToUse);
		}
		return chartResult;
	}
}
