package br.wotr.chart;

import br.wotr.bot.Main;
import br.wotr.util.ConstantUtil;
import br.wotr.util.DiceUtil;

public class MusterChartCorruption extends DieChart {
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
				mu.q("A Stronghold is under THREAT AND Can muster?");
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
					mu.q("An SP Nation not At War?");
				} else {
					mu.q("An SP Nation not At War OR No Faction in play?");
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
				mu.q("Holding playable Muster Card?");
				if (Main.yes()) {
					branch = 1;
				}
				trunk = 5;
			}
			if (trunk == 5) {
				mu.q("SP can muster?");
				if (!Main.yes()) {
					chartResult = mu.a(ConstantUtil.getContinuedieselection());
				} else {
					branch = 2;
				}
			}

			if (branch == 1) {
				mu.q("Card allows choosing Muster location?");
				if (!Main.yes()) {
					chartResult = mu.a(ConstantUtil.getMuster());
					continue;
				}
				branch = 2;
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
				mu.q("FSP adjacent to SP Settlement AND Progress puts it outside Mordor AND No army is in FSP region?");
				if (Main.yes()) {
					branch = 4;
				} else {
					branch = 5;
				}
			}
			if (branch == 4) {
				mu.q("Settlement has army without Nazgûl AND There's no Nazgûl with FSP?");
				if (Main.yes()) {
					mu.printPriorities(ConstantUtil.getFmcprisecnn());
				} else {
					mu.printPriorities(ConstantUtil.getFmcprisecrn());
				}
				chartResult = mu.a(ConstantUtil.getMuster());
				continue;
			}
			if (branch == 5) {
				mu.q("Muster possible in region with SP army?");
				if (Main.yes()) {
					mu.printPriorities(ConstantUtil.getFmcmusterinsparmy());
					mu.printPriorities(ConstantUtil.getFmcprisecer());
					chartResult = mu.a(ConstantUtil.getMuster());
					continue;
				} else {
					mu.printPriorities(ConstantUtil.getFmcsettlementclosest());
					branch = 6;
				}
			}
			if (branch == 6) {
				mu.q("Have less than 6 Nazgûl?");
				if (Main.yes()) {
					mu.printPriorities(ConstantUtil.getFmcprisecnn());
				} else {
					mu.printPriorities(ConstantUtil.getFmcprisecen());
				}
				chartResult = mu.a(ConstantUtil.getMuster());
				continue;
			}
		}

		// Verify which actions consume the die
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
