package br.wotr.chart;

import br.wotr.bot.Main;
import br.wotr.util.ConstantUtil;
import br.wotr.util.DiceUtil;

public class CharacterChartCorruption extends DieChart {

	public String runChart(String die, int entry) {
		super.runChart(die, entry);

		// Entry points
		if (entryPoint == 1) {
			trunk = 1;
		}
		if (entryPoint == 2) {
			trunk = 3;
		}

		// Decide action
		while (chartResult == null) {
			if (trunk == 1) {
				mu.q("AGGRESSIVE army against adjacent TARGET AND Has Witch-king or max. possible leadership?");
				if (Main.yes()) {
					chartResult = mu.a(ConstantUtil.getAttack());
					continue;
				}
				trunk = 2;
			}
			if (trunk == 2) {
				mu.q("MOBILE army with leadership AND A valid move/attack towards nearest available TARGET?");
				if (Main.yes()) {
					chartResult = mu.a(ConstantUtil.getArmy4());
					continue;
				}
				trunk = 3;
			}
			if (trunk == 3) {
				mu.q("Nazgûl OR Witch-king in play?");
				if (!Main.yes()) {
					chartResult = mu.a(ConstantUtil.getEventcdie());
					continue;
				} else {
					branch = 1;
				}
				if (branch == 1) {
					mu.q("Witch-king not in an AGGRESSIVE army and can join/create one?");
					if (Main.yes()) {
						mu.printPriorities(ConstantUtil.getFccwitchking());
					}
					branch = 2;
				}
				if (branch == 2) {
					mu.q("Nazgûl not in FSP region and can move there?");
					if (Main.yes()) {
						mu.printPriorities(ConstantUtil.getFccnazgul());
					}
					branch = 3;
				}
				if (branch == 3) {
					mu.q("Nazgûl not in an AGGRESSIVE army and can join/create one?");
					if (Main.yes()) {
						mu.printPriorities(ConstantUtil.getFccnazgul());
					}
					branch = 4;
				}
				if (branch == 4) {
					mu.q("Mouth of Sauron not in an AGGRESSIVE OR MOBILE army?");
					if (Main.yes()) {
						mu.printPriorities(ConstantUtil.getFccmouth());
					}
					branch = 5;
				}
				if (branch == 5) {
					mu.q("Were you able to move any Minion or Nazgûl?");
					if (Main.yes()) {
						chartResult = mu.a(ConstantUtil.getMoveminionsandnazgul());
					} else {
						chartResult = mu.a(ConstantUtil.getEventcdie());
					}
					continue;
				}
			}
		}

		// Corruption - Verify which actions consume the die
		if (chartResult.equals(ConstantUtil.getMoveminionsandnazgul())) {
			chartResult = DiceUtil.useDie(dieToUse);
		}
		return chartResult;
	}
}
