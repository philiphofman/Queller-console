package br.wotr.chart;

import br.wotr.bot.Main;
import br.wotr.util.ConstantUtil;
import br.wotr.util.DiceUtil;

public class BattleChartMilitary extends DieChart {
	int battleAction;
	int roundOfBattle;

	/**
	 * Start the flow in the chart
	 * 
	 * @param entry The entry point in the chart flow.
	 * @return True if a possible battleAction was executed with the die.
	 */
	public String runChart(String die, int entry) {
		super.runChart(die, entry);
		boolean battleOver = false;
		battleAction = 1;
		roundOfBattle = 1;

		while (!battleOver) {
			mu.a("Nations not At War form Rearguard");
			if (trunk == 1) {
				mu.q("SP defending Stronghold region not under siege?");
				if (Main.yes()) {
					mu.q("Army VALUE <= enemy VALUE AND Army size < 8?");
					if (Main.yes()) {
						mu.a(ConstantUtil.getRetreatintostronghold());
						battleOver = true;
						continue;
					}
				}
				trunk = 2;
			}
			if (trunk == 2) {
				mu.q("SP army AGGRESSIVE?");
				if (Main.yes()) {
					branch = 1;
					if (branch == 1) {
						mu.q("Army is a sortie?");
						if (Main.yes()) {
							branch = 2;
						} else {
							branch = 3;
						}
					}
					if (branch == 2) {
						mu.q("Holding a playable Character Card?");
						if (Main.yes()) {
							mu.printPriorities(ConstantUtil.getFbct2b2());
							battleAction = 1;
							branch = 0;
						} else {
							battleAction = 2;
							branch = 0;
						}
					}
					if (branch == 3) {
						mu.q("Army has Witch-king AND It's 1st Round?");
						if (Main.yes()) {
							mu.printPriorities(ConstantUtil.getFbct2b3());
							battleAction = 1;
							branch = 0;
						} else {
							branch = 4;
						}
					}
					if (branch == 4) {
						mu.q("SP is laying siege?");
						if (Main.yes()) {
							mu.printPriorities(ConstantUtil.getFbct2b456());
							battleAction = 1;
							branch = 0;
						} else {
							branch = 5;
						}
					}
					if (branch == 5) {
						mu.q("Over 4 Event cards?");
						if (Main.yes()) {
							mu.printPriorities(ConstantUtil.getFbct2b456());
							battleAction = 1;
							branch = 0;
						} else {
							branch = 6;
						}
					}
					if (branch == 6) {
						mu.q("Holding playable Call to Battle card?");
						if (Main.yes()) {
							mu.printPriorities(ConstantUtil.getFbct2b456());
							battleAction = 1;
							branch = 0;
						} else {
							battleAction = 2;
							branch = 0;
						}
					}
				} else {
					trunk = 3;
				}
			}
			if (trunk == 3) {
				mu.q("Besieged OR Battling in Stronghold?");
				if (Main.yes()) {
					mu.printPriorities(ConstantUtil.getFbct34y());
					battleAction = 1;
				} else {
					trunk = 4;
				}
			}
			if (trunk == 4) {
				mu.q("Round 1 of combat?");
				if (Main.yes()) {
					mu.printPriorities(ConstantUtil.getFbct34y());
					battleAction = 1;
				} else {
					mu.printPriorities(ConstantUtil.getFbct4n());
					mu.a(ConstantUtil.getRetreat());
					battleOver = true;
					continue;
				}
			}
			// Play Card
			if (battleAction == 1) {
				mu.a(ConstantUtil.getPlaycard());
				battleAction = 2;
			}
			// Combat roll, re-roll and casualties
			if (battleAction == 2) {
				mu.a("Combat roll");
				mu.a("Leader re-roll");
				mu.a("Remove casualties");
				mu.printPriorities(ConstantUtil.getFbcremovecasualties());
				trunk = 5;
			}
			if (trunk == 5) {
				mu.q("FP remain?");
				if (Main.yes()) {
					branch = 6;
					if (branch == 6) {
						mu.q("SP army AGGRESSIVE?");
						if (Main.yes()) {
							branch = 7;
						} else {
							mu.a(ConstantUtil.getEnd());
							battleOver = true;
							continue;
						}
					}
					if (branch == 7) {
						mu.q("Siege AND SP have an Elite AND Remains AGGRESSIVE if it downgrades the Elite?");
						if (Main.yes()) {
							mu.a(ConstantUtil.getDowngradeeliteandfight());
							roundOfBattle++;
						} else {
							mu.q("Is it a Field battle?");
							if (Main.yes()) {
								mu.a(ConstantUtil.getFightanotherround());
								roundOfBattle++;
							} else {
								mu.a(ConstantUtil.getEnd());
								battleOver = true;
							}
						}
						continue;
					}
				} else {
					trunk = 6;
				}
			}
			if (trunk == 6) {
				mu.printPriorities(ConstantUtil.getFbct6());
				mu.q("Any of these are true?");
				if (Main.yes()) {
					mu.a(ConstantUtil.getMoveintoregion());
				} else {
					mu.a(ConstantUtil.getEnd());
				}
				battleOver = true;
				continue;
			}
		}
		// if in battle always consume the die
		chartResult = DiceUtil.useDie(dieToUse);
		return chartResult;
	}
}
