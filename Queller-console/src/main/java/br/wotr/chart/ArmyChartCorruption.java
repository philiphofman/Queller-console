package br.wotr.chart;

import br.wotr.bot.Main;
import br.wotr.util.ConstantUtil;
import br.wotr.util.DiceUtil;

public class ArmyChartCorruption extends DieChart {

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
			trunk = 4;
		}
		if (entryPoint == 4) {
			trunk = 8;
		}
		if (entryPoint == 5) {
			trunk = 6;
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
							chartResult = ConstantUtil.getAttack();
							continue;
						}
					}
					branch = 2;
					if (branch == 2) {
						mu.q("Can form AGGRESSIVE army adjacent to THREAT?");
						if (Main.yes()) {
							numOfMoves++;
							chartResult = mu.a(ConstantUtil.getMove());
							continue;
						} else {
							branch = 3;
						}
					}
					if (branch == 3) {
						mu.q("Can move an army to increase VALUE at THREATened Stronghold?");
						if (Main.yes()) {
							numOfMoves++;
							chartResult = mu.a(ConstantUtil.getMove());
							continue;
						} else {
							branch = 4;
						}
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
					mu.a("Move nearest army towards EXPOSED");
					numOfMoves++;
					chartResult = mu.a(ConstantUtil.getMove());
					continue;
				}
				trunk = 3;
			}
			if (trunk == 3) {
				mu.q("AGGRESSIVE army adjacent to TARGET?");
				if (Main.yes()) {
					branch = 1;
					if (branch == 1) {
						mu.q("TARGET not under siege?");
						if (Main.yes()) {
							mu.printPriorities(ConstantUtil.getFacct3());
							chartResult = ConstantUtil.getAttack();
							continue;
						}
					}
				}
				trunk = 4;
			}
			if (trunk == 4) {
				mu.q("Army can move to FSP region without increasing distance to TARGET?");
				if (Main.yes()) {
					branch = 1;
					if (branch == 1) {
						mu.q("Dice in Hunt Box AND FSP progress puts it outside Mordor AND No army in region?");
						if (Main.yes()) {
							mu.a("Move into region");
							numOfMoves++;
							chartResult = mu.a(ConstantUtil.getMove());
							continue;
						}
					}
				}
				trunk = 5;
			}
			if (trunk == 5) {
				mu.q("Holding playable Army Strategy Card?");
				if (Main.yes()) {
					mu.q("Moves more armies than die allows OR creates MOBILE army die could not?");
					if (Main.yes()) {
						chartResult = mu.a(ConstantUtil.getPlaycard());
						continue;
					}
				}
				trunk = 6;
			}
			if (trunk == 6) {
				mu.q("Army can move into an empty Settlement of a Nation At War?");
				if (Main.yes()) {
					mu.q("Increases distance to TARGET?");
					if (Main.yes()) {
						mu.a("Move 1 unit into region");
					} else {
						mu.a("Move into region");
					}
					numOfMoves++;
					chartResult = ConstantUtil.getMove();
					continue;
				}
				trunk = 7;
			}
			if (trunk == 7) {
				mu.q("2 SP armies adjacent to one another?");
				if (Main.yes()) {
					branch = 1;
					if (branch == 1) {
						mu.q("Move would make PASSIVE siege AGGRESSIVE?");
						if (Main.yes()) {
							numOfMoves++;
							chartResult = mu.a(ConstantUtil.getMove());
							continue;
						} else {
							branch = 2;
						}
					}
					if (branch == 2) {
						mu.q("Move would create a new MOBILE army OR increase the troop count of a MOBILE army?");
						if (Main.yes()) {
							mu.printPriorities(ConstantUtil.getFacct7());
							numOfMoves++;
							chartResult = mu.a(ConstantUtil.getMove());
							continue;
						}
					}

				}
				trunk = 8;
			}
			if (trunk == 8) {
				mu.q("MOBILE army can move OR attack towards closest TARGET?");
				if (Main.yes()) {
					mu.printPriorities(ConstantUtil.getFacct8());
					mu.a("Move OR Attack");
					mu.q("Will it Attack?");
					if (Main.yes()) {
						chartResult = ConstantUtil.getAttack();
					} else {
						numOfMoves++;
						chartResult = mu.a(ConstantUtil.getMove());
					}
					continue;
				}
				trunk = 9;
			}
			if (trunk == 9) {
				mu.q("Have an AGGRESSIVE army?");
				if (Main.yes()) {
					mu.printPriorities(ConstantUtil.getFacct9());
					chartResult = ConstantUtil.getAttack();
					continue;
				}
				trunk = 10;
			}
			if (trunk == 10) {
				mu.q("Have an army on the board?");
				if (Main.yes()) {
					mu.printPriorities(ConstantUtil.getFacct10());
					numOfMoves++;
					chartResult = mu.a(ConstantUtil.getMove());
					continue;
				}
				chartResult = mu.a(ConstantUtil.getContinuedieselection());
			}
		}

		// Verify which actions consume the die
		if (chartResult.equals(ConstantUtil.getMove()) && numOfMoves > 1) {
			numOfMoves = 0;
			chartResult = DiceUtil.useDie(dieToUse);
		}

		if (chartResult.equals(ConstantUtil.getPlaycard())) {
			chartResult = DiceUtil.useDie(dieToUse);
		}
		return chartResult;
	}
}
