package br.wotr.chart;

import br.wotr.bot.Main;
import br.wotr.util.ConstantUtil;
import br.wotr.util.DiceUtil;

public class ArmyChartMilitary extends DieChart {

	static int numOfMoves = 0;

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
			trunk = 3;
		}
		if (entryPoint == 3) {
			trunk = 5;
		}
		if (entryPoint == 4) {
			trunk = 4;
		}

		// Decide action
		while (chartResult == null) {
			if (trunk == 1) {
				mu.q("THREAT exists?");
				if (Main.yes()) {
					branch = 1;
					if (branch == 1) {
						mu.q("AGGRESSIVE army adjacent to THREAT?");
						if (Main.yes()) {
							chartResult = mu.a(ConstantUtil.getAttack());
							continue;
						}
						branch = 2;
					}
					if (branch == 2) {
						mu.q("Can form AGGRESSIVE army adjacent to THREAT?");
						if (Main.yes()) {
							numOfMoves++;
							chartResult = mu.a(ConstantUtil.getMove());
							continue;
						}
						branch = 3;
					}
					if (branch == 3) {
						mu.q("Can move army to increase VALUE at THREATend Stronghold?");
						if (Main.yes()) {
							numOfMoves++;
							chartResult = mu.a(ConstantUtil.getMove());
							continue;
						}
						branch = 4;
					}
					if (branch == 4) {
						mu.q("MOBILE army's closest TARGET takes it towards cause of THREAT?");
						if (Main.yes()) {
							numOfMoves++;
							chartResult = mu.a(ConstantUtil.getMove());
							continue;
						}
					}
				}
				trunk = 2;
			}
			if (trunk == 2) {
				mu.q("EXPOSED TARGET exists?");
				if (Main.yes()) {
					mu.a("Move nearest TARGET towards EXPOSED");
					numOfMoves++;
					chartResult = ConstantUtil.getMove();
					continue;
				}
				trunk = 3;
			}
			if (trunk == 3) {
				mu.q("Army can move into FSP region?");
				if (Main.yes()) {
					mu.q("Decrease distance to TARGET?");
					if (Main.yes()) {
						mu.a("Move into region");
						numOfMoves++;
						chartResult = ConstantUtil.getMove();
						continue;
					}
				}
				trunk = 4;
			}
			if (trunk == 4) {
				mu.q("Woodland Realm has never been under siege?");
				if (Main.yes()) {
					mu.q("At least one FP Nation is PASSIVE AND There is an SP army on the route from Barad-dûr to Woodland Realm?");
					if (Main.yes()) {
						fu.printPriorities("ArmyChart-Military-T4");
						mu.a(ConstantUtil.getMovetowardswoodlandrealm());
						chartResult = ConstantUtil.getMove();
					}
				}
				trunk = 5;
			}
			if (trunk == 5) {
				mu.q("MOBILE army can Move or Attack towards closest TARGET?");
				if (Main.yes()) {
					mu.q("TARGET is in active Nation?");
					if (Main.yes()) {
						fu.printPriorities("ArmyChart-Military-T5");
						mu.q("Will it Move?");
						if (Main.yes()) {
							numOfMoves++;
							chartResult = mu.a(ConstantUtil.getMove());
						} else {
							chartResult = mu.a(ConstantUtil.getAttack());
						}
						continue;
					}
				}
				trunk = 6;
			}
			if (trunk == 6) {
				mu.q("2 SP armies adjacent to one another?");
				if (Main.yes()) {
					branch = 1;
					if (branch == 1) {
						mu.q("Move would make PASSIVE siege AGGRESSIVE?");
						if (Main.yes()) {
							mu.q("Will it Move?");
							if (Main.yes()) {
								numOfMoves++;
								chartResult = mu.a(ConstantUtil.getMove());
							} else {
								chartResult = mu.a(ConstantUtil.getAttack());
							}
							continue;
						}
						branch = 2;
					}
					if (branch == 2) {
						mu.q("Move would create a new MOBILE army OR increase the troop count of a MOBILE army?");
						if (Main.yes()) {
							fu.printPriorities("ArmyChart-Military-T6");
							mu.q("Will it Move?");
							if (Main.yes()) {
								numOfMoves++;
								chartResult = mu.a(ConstantUtil.getMove());
							} else {
								chartResult = mu.a(ConstantUtil.getAttack());
							}
							continue;
						}
					}
				}
				trunk = 7;
			}
			if (trunk == 7) {
				mu.q("Have an AGGRESSIVE army?");
				if (Main.yes()) {
					fu.printPriorities("ArmyChart-Military-T7");
					numOfMoves++;
					chartResult = mu.a(ConstantUtil.getAttack());
					continue;
				}
				trunk = 8;
			}
			if (trunk == 8) {
				mu.q("Have an army on the board?");
				if (Main.yes()) {
					fu.printPriorities("ArmyChart-Military-T8");
				}
				chartResult = mu.a(ConstantUtil.getContinuedieselection());
			}
		}

		// Verify which actions consume the die
		if (chartResult.equals(ConstantUtil.getMove()) && numOfMoves > 1) {
			numOfMoves = 0;
			chartResult = DiceUtil.useDie(dieToUse);
		}
		return chartResult;
	}
}
