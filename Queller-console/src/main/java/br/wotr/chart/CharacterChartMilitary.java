package br.wotr.chart;

import br.wotr.bot.Main;
import br.wotr.util.ConstantUtil;
import br.wotr.util.DiceUtil;

public class CharacterChartMilitary extends DieChart {

	public String runChart(String die, int entry) {
		super.runChart(die, entry);

		// Entry points
		if (entryPoint == 1) {
			trunk = 1;
		}

		// Decide action
		while (chartResult == null) {
			if (trunk == 1) {
				mu.q("Nazgûl OR Witch-king in play?");
				if (Main.yes()) {
					branch = 1;
					if (branch == 1) {
						mu.q("Witch-king not in AGGRESSIVE army?");
						if (Main.yes()) {
							fu.printPriorities("Chart-C-C-WKplacement");
						}
						branch = 2;
					}
					if (branch == 2) {
						mu.q("Nazgûl not in an AGGRESSIVE army and can join/create one?");
						if (Main.yes()) {
							fu.printPriorities("Chart-C-C-Nazgulplacement");
						}
						branch = 3;
					}
					if (branch == 3) {
						mu.q("Mouth of Sauron not in AGGRESSIVE OR MOBILE army?");
						if (Main.yes()) {
							fu.printPriorities("Chart-C-C-MouthofSauronplacement");
						}
					}
					if (branch == 4) {
						mu.q("Were you able to move any Minion or Nazgûl?");
						if (Main.yes()) {
							chartResult = mu.a(ConstantUtil.getMoveminionsandnazgul());
							continue;
						}
					}
				}
				trunk = 2;
			}
			if (trunk == 2) {
				mu.q("Army AGGRESSIVE AND Has Witch-king or Max. possible leadership?");
				if (Main.yes()) {
					chartResult = mu.a(ConstantUtil.getAttack());
					continue;
				}
				trunk = 3;
			}
			if (trunk == 3) {
				mu.q("MOBILE army with leadership AND A valid Move/Attack towards nearest available TARGET?");
				if (Main.yes()) {
					chartResult = mu.a(ConstantUtil.getArmy3());
					continue;
				}
				chartResult = mu.a(ConstantUtil.getEventcdie());
			}

		}

		// Military - Verify which actions consume the die
		if (chartResult.equals(ConstantUtil.getMoveminionsandnazgul())) {
			chartResult = DiceUtil.useDie(dieToUse);
		}
		return chartResult;
	}
}
