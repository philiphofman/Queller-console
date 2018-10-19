package br.wotr.chart;

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
public abstract class DieChart {
	MessageUtil mu = new MessageUtil();
	int entryPoint;
	int trunk;
	int branch;
	String dieToUse;
	String chartResult;

	public String runChart(String die, int entry) {
		entryPoint = entry;
		trunk = 1;
		branch = 1;
		dieToUse = die;
		chartResult = null;
		return "";
	}
}
