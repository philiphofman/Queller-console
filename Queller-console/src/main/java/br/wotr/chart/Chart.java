package br.wotr.chart;

import br.wotr.util.FileUtil;
import br.wotr.util.MessageUtil;

//@formatter:off
	/* *******************
	 *      DIECHARTS	 *
	 *   C: Character	 *
	 *   A: Army		 *
	 *   M: Muster		 *
	 *   E: Event		 *
	 *   B: Battle		 *
	 *	 F: Faction		 *
	 * *******************/
	//@formatter:on
public abstract class Chart {
	// TODO Z - Utilities in main AND here?
	FileUtil fu = new FileUtil();
	MessageUtil mu = new MessageUtil();
	char strategy;
	int entryPoint;
	int trunk;
	int branch;
	String dieToUse;
	String chartResult;

	public String runChart(char strat, String die, int entry) {
		trunk = 1;
		branch = 1;
		strategy = strat;
		dieToUse = die;
		entryPoint = entry;
		chartResult = null;
		return "";
	}
}
