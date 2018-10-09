package br.wotr.bot;

import java.util.Scanner;

import br.wotr.chart.ArmyChart;
import br.wotr.chart.BattleChart;
import br.wotr.chart.CharacterChart;
import br.wotr.chart.EventChart;
import br.wotr.chart.FactionChart;
import br.wotr.chart.MusterChart;
import br.wotr.util.ConstantUtil;
import br.wotr.util.DiceUtil;
import br.wotr.util.FileUtil;
import br.wotr.util.MessageUtil;

/* other variables
 FP strongholds
 Elves: Rivendell, Lórien, Woodland Realm, Grey Havens
 Dwarves: Erebor
 Rohan: Helm's Deep
 Gondor: Minas Tirith, Dol Amroth
 
 SP Strongholds
 Sauron: Minas Morgul, Morannon, Barad-Dûr, Dol Guldur, Mount Gundabad
 Isengard: Orthanc
 S&E: Umbar

 Glossary of terms: AGGRESSIVE, EXPOSED, MOBILE, PASSIVE, PRIMARY, SECONDARY, TARGET, THREAT, VALUE

 variables: numOfDice, FSP start, FSP mordor, FSPOver5, MOBILE */

/**
 * Main class of the application. It contains Utilities (for File, Dice and
 * Messages manipulation) and several variables to control the loops and
 * questions.
 */
public class Main {
	// utilities
	static FileUtil fu = new FileUtil();
	static MessageUtil mu = new MessageUtil();
	static Scanner sc = new Scanner(System.in);
	// chart objects
	static ArmyChart armyChart = new ArmyChart();
	static BattleChart battleChart = new BattleChart();
	static CharacterChart characterChart = new CharacterChart();
	static EventChart eventChart = new EventChart();
	static FactionChart factionChart = new FactionChart();
	static MusterChart musterChart = new MusterChart();
	// queller and game attributes
	static boolean baseGame = true;
	static boolean usedARing = false;
	static char strategy = 'c';
	static int quellerRings = 0;
	static String mostCommonDie = "";
	// constants for Chart names
	final static String aChart = "Army";
	final static String bChart = "Battle";
	final static String cChart = "Character";
	final static String eChart = "Event";
	final static String fChart = "Faction";
	final static String mChart = "Muster";

	/**
	 * Main method of the application, which contains the main loop of the game.
	 * It Will go through the phases 1 till 6 until the game is over.
	 */
	public static void main(String[] argv) throws Exception {
		boolean gameover = false;
		int turnCount = 0;
		mu.welcomeMessage();
		System.out.println("Playing the base game");
		// mu.q("PLaying with the Warriors of Middle Earth expansion?");
		// if (yes()) {
		// baseGame = false;
		// }

		strategy = DiceUtil.initialStrategy();
		mu.startingStrategy(strategy);
		do {
			mu.printTurnNumber(++turnCount);
			phase1();
			phase2();
			phase3();
			phase4();
			do {
				phase5();
			} while (DiceUtil.hasDie());
			DiceUtil.resetDice();
			gameover = phase6();
		} while (!gameover);
		mu.a("Game over.");
		sc.close();
	}

	/**
	 * Phase 1) - Recover Action Dice and Draw Event Cards<br>
	 * The objective of this phase is to draw cards and discard cards if needed.
	 */
	private static void phase1() {
		mu.printPhaseHeader(1);
		int trunk = 1;
		switch (strategy) {
		case 'c':
			mu.a("Draw cards");
			if (trunk == 1) {
				mu.q("Over 6 Event cards?");
				if (yes()) {
					mu.q("More than 1 strategy card?");
					if (yes()) {
						fu.printPriorities("Phase1-Corruption-T1B1-Yes");
					} else {
						fu.printPriorities("Phase1-Corruption-T1B1-No");
					}
				}
				if (!baseGame) {
					trunk = 2;
				}
			}
			// Warriors of the Middle Earth expansion
			if (trunk == 2) {
				mu.q("Over 4 Faction cards?");
				if (yes()) {
					fu.printPriorities("Phase1-Corruption-T2-Yes");
				}
			}
			break;
		case 'm':
			mu.a("Draw cards");
			if (trunk == 1) {
				mu.q("Over 6 cards?");
				if (yes()) {
					fu.printPriorities("Phase1-Military-T1-Yes");
				}
				if (!baseGame) {
					trunk = 2;
				}
			}
			// Warriors of the Middle Earth expansion
			if (trunk == 2) {
				mu.q("Over 4 faction cards?");
				if (yes()) {
					fu.printPriorities("Phase1-Military-T2-Yes");
				}
			}
			break;
		default:
			break;
		}
	}

	/**
	 * Phase 2) - Fellowship Phase<br>
	 * The objective of this phase is to check Corruption and VPs to decide if
	 * Queller changes his strategy.
	 */
	private static void phase2() {
		mu.printPhaseHeader(2);
		int trunk = 1;
		switch (strategy) {
		case 'c':
			if (trunk == 1) {
				mu.q("Corruption lower than either side's VPs?");
				if (yes()) {
					strategy = mu.switchStrategy(strategy);
				}
			}
			break;
		case 'm':
			if (trunk == 1) {
				mu.q("Shadow VPs lower than corruption?");
				if (yes()) {
					strategy = mu.switchStrategy(strategy);
				}
			}
			break;
		default:
			break;
		}
	}

	/**
	 * Phase 3) - Hunt Allocation<br>
	 * The objective of this phase is to determine the number of eyes to put in
	 * the Hunt Box
	 */
	private static void phase3() {
		mu.printPhaseHeader(3);
		int trunk = 1;
		int eyeAmount = 0;

		switch (strategy) {
		//@formatter:off
		/* ********************
		 * Phase 3 Corruption |
		 *********************/
		//@formatter:on
		case 'c':
			if (trunk == 1) {
				mu.q("FSP at Rivendell AND Progress = 0?");
				if (yes()) {
					if (DiceUtil.betweenOneAndThree(DiceUtil.rollD6())) {
						eyeAmount = 0;
					} else {
						eyeAmount = 1;
					}
					break;
				} else {
					trunk = 2;
				}
			}
			if (trunk == 2) {
				mu.q("FSP in Mordor?");
				if (yes()) {
					eyeAmount = 8;
					break;
				} else {
					trunk = 3;
				}
			}
			if (trunk == 3) {
				mu.q("Queller has 7 dice OR AGGRESSIVE army adjacent to TARGET which would win the game?");
				if (yes()) {
					eyeAmount = 1;
					break;
				} else {
					trunk = 4;
				}
			}
			if (trunk == 4) {
				mu.q("FSP progress > 5?");
				if (yes()) {
					eyeAmount = 2;
					break;
				} else {
					trunk = 5;
				}
			}
			if (trunk == 5) {
				mu.q("FSP shortest route leads via a SP Stronghold AND They are within 2 progress of it or have passed it?");
				if (yes()) {
					eyeAmount = 2;
				} else {
					eyeAmount = 1;
				}
				break;
			}
			break;
		//@formatter:off
		/* ******************
		 * Phase 3 Military |
		 *******************/
		//@formatter:on
		case 'm':
			if (trunk == 1) {
				mu.q("FSP in Mordor?");
				if (yes()) {
					eyeAmount = 8;
					break;
				} else {
					trunk = 2;
				}
			}
			if (trunk == 2) {
				mu.q("FSP progress > 5?");
				if (yes()) {
					eyeAmount = 2;
					break;
				} else {
					trunk = 3;
				}
			}
			if (trunk == 3) {
				mu.q("FSP in Rivendell AND Progress = 0?");
				if (yes()) {
					eyeAmount = 0;
					break;
				} else {
					eyeAmount = 1;
				}
			}
			break;
		default:
			break;
		}
		mu.a("Assign " + (eyeAmount < 8 ? eyeAmount : "maximum") + " eye(s)");
	}

	/**
	 * Phase 4) - Action Roll<br>
	 * The objective of this phase is to roll the remaining Action Dice and form
	 * the Queller pool for this turn
	 */
	private static void phase4() {
		mu.printPhaseHeader(4);
		mu.a("Roll action dice");
		mu.q("What did Queller get? ");
		String spRoll = sc.nextLine();
		spRoll = spRoll.trim().toUpperCase();
		while (!rollIsOk(spRoll)) {
			mu.a("Wrong format. Make sure it's something like X X X X X");
			mu.a("One die result followed by a space, followed by result, etc.");
			mu.q("What did Queller get? ");
			spRoll = sc.nextLine();
			spRoll = spRoll.trim().toUpperCase();
		}
		DiceUtil.setQuellerRoll(spRoll);
		mostCommonDie = DiceUtil.mostCommonDie();
	}

	/**
	 * Phase 5) - Action Resolution<br>
	 * The objective of this phase is to perform actions until there is no dice
	 * in the pool or in the saved die for last. For every iteration, it will
	 * either consume a die or save a die for last or discard a die, in the case
	 * of impossibility to perform an action.
	 */
	private static void phase5() {
		int trunk = 1;
		int branch = 1;
		int minionNumber = 0;
		String dieToDiscard = "";
		String result = "";

		mu.printPhaseHeader(5);
		mu.printQuellerStrategy(strategy);
		mu.printQuellerPool();

		// TODO T - Saved and Discarded dice
		mu.printSavedAndDiscardedDice();

		switch (strategy) {
		/*
		 **********************
		 * Phase 5 Corruption *
		 **********************
		 */
		case 'c':
			while (!wasUsed(result)) {
				if (DiceUtil.poolIsEmpty()) {
					mu.a("Use Muster die set aside for Minion");
					do {
						mu.m("Which one?");
						fu.printPriorities("MusterChart-Minions");
						mu.q("Number: ");
						minionNumber = sc.nextInt();
					} while (!DiceUtil.betweenOneAndThree(minionNumber));
					switch (minionNumber) {
					case 1:
						mu.m("Saruman can only go to Orthanc");
						break;
					case 2:
						fu.printPriorities("CharacterChart-Witch-king");
						break;
					case 3:
						fu.printPriorities("CharacterChart-MouthOfSauron");
						break;
					default:
						break;
					}
					mu.a("Muster Minion");
					mu.a("Using " + DiceUtil.getDieFromSaved() + " die");
					result = ConstantUtil.getUsed();
					continue;
				}

				// Phase 5 Corruption
				if (trunk == 1) {
					mu.q("SP under THREAT or FP EXPOSED?");
					if (yes()) {
						branch = 1;
						// Phase 5 Corruption
						if (branch == 1) {
							mu.q("AGGRESSIVE army adjacent to THREAT?");
							if (yes()) {
								result = performAction(
										ConstantUtil.getAttackcdie(), "C",
										bChart, 1);
								if (wasUsed(result)) {
									continue;
								}
								result = performAction(ConstantUtil.getArmy(),
										"A", aChart, 1);
								if (wasUsed(result)) {
									continue;
								} else {
									branch = 6;
								}
							} else {
								branch = 2;
							}
						}
						// Phase 5 Corruption
						if (branch == 2) {
							mu.q("Move will create AGGRESSIVE army adjacent to THREAT?");
							if (yes()) {
								result = performAction(ConstantUtil.getArmy(),
										"A", aChart, 1);
								if (wasUsed(result)) {
									continue;
								} else {
									branch = 6;
								}
							} else {
								branch = 3;
							}
						}
						// Phase 5 Corruption
						if (branch == 3) {
							mu.q("Move will increase army in Stronghold under THREAT?");
							if (yes()) {
								result = performAction(ConstantUtil.getArmy(),
										"A", aChart, 1);
								if (wasUsed(result)) {
									continue;
								} else {
									branch = 6;
								}
							} else {
								branch = 4;
							}
						}
						// Phase 5 Corruption
						if (branch == 4) {
							mu.q("MOBILE army's route to closest TARGET takes it towards THREAT?");
							if (yes()) {
								result = performAction(ConstantUtil.getArmy(),
										"A", aChart, 1);
								if (wasUsed(result)) {
									continue;
								} else {
									branch = 6;
								}
							} else {
								branch = 5;
							}
						}
						// Phase 5 Corruption
						if (branch == 5) {
							mu.q("Can move towards EXPOSED?");
							if (yes()) {
								result = performAction(ConstantUtil.getArmy(),
										"A", aChart, 1);
								if (wasUsed(result)) {
									continue;
								} else {
									branch = 6;
								}
							} else {
								branch = 6;
							}
						}
						// Phase 5 Corruption
						if (branch == 6) {
							mu.q("Can muster in Stronghold under THREAT?");
							if (yes()) {
								result = performAction(ConstantUtil.getMuster(),
										"M", mChart, 1);
								if (wasUsed(result)) {
									continue;
								}
							}
							branch = 7;
						}
						// Phase 5 Corruption
						if (branch == 7) {
							mu.q("THREAT is sieging an SP Stronghold which can use more leadership?");
							if (yes()) {
								result = performAction(
										ConstantUtil.getCharacter2(), "C",
										cChart, 2);
								if (wasUsed(result)) {
									continue;
								}
							}
						}
					}
					trunk = 2;
				}
				// Phase 5 Corruption
				if (trunk == 2) {
					mu.q("A MOBILE Minion is in a PASSIVE army against all adjacent At War armies?");
					if (yes()) {
						result = performAction(ConstantUtil.getCharacter2(),
								"C", cChart, 2);
						if (wasUsed(result)) {
							continue;
						}
					}
					trunk = 3;
				}
				// Phase 5 Corruption
				if (trunk == 3) {
					mu.q("FSP in Mordor or revealed AND Character cards > 0?");
					if (yes()) {
						branch = 1;
						if (branch == 1) {
							mu.q("Holding \"FSP revealed\" Character card?");
							if (yes()) {
								mu.a("Play Card using C die");
								result = performAction(
										ConstantUtil.getPlaycardcdie(), "C",
										eChart, 1);
								if (wasUsed(result)) {
									continue;
								}
							}
							result = performAction(ConstantUtil.getEvent2(),
									"P", eChart, 2);
							if (wasUsed(result)) {
								continue;
							}
						}
					}
					trunk = 4;
				}
				// Phase 5 Corruption
				if (trunk == 4) {
					mu.q("FSP in region free for Nazgûl with 0 Nazgûl?");
					if (yes()) {
						result = performAction(ConstantUtil.getCharacter2(),
								"C", cChart, 2);
						if (wasUsed(result)) {
							continue;
						}
					}
					trunk = 5;
				}
				// Phase 5 Corruption
				if (trunk == 5) {
					if (baseGame) {
						mu.q("Can muster Minion OR S&E not At War?");
					} else {
						mu.q("Can muster Minion OR S&E not At War OR No Faction recruited?");
					}
					if (yes()) {
						result = performAction(ConstantUtil.getMuster(), "M",
								mChart, 1);
						if (wasUsed(result)) {
							continue;
						}
					}
					trunk = 6;
				}
				// Phase 5 Corruption
				if (trunk == 6) {
					mu.q("AGGRESSIVE army adjacent to TARGET?");
					if (yes()) {
						mu.q("FSP in Mordor OR TARGET would win the game OR TARGET not under siege and in a nation At War?");
						if (yes()) {
							result = performAction(ConstantUtil.getCharacter(),
									"C", cChart, 1);
							if (wasUsed(result)) {
								continue;
							}
							result = performAction(ConstantUtil.getArmy2(), "A",
									aChart, 2);
							if (wasUsed(result)) {
								continue;
							}
						}
					} else {
						mu.q("Can Queller pass?");
						if (yes()) {
							mu.a("Pass");
							result = ConstantUtil.getUsed();
							continue;
						}
					}
					trunk = 7;
				}
				// Phase 5 Corruption
				if (trunk == 7) {
					mu.q("Playable Character cards?");
					if (yes()) {
						result = performAction(ConstantUtil.getEventcdie(), "C",
								eChart, 1);
						if (wasUsed(result)) {
							continue;
						}
						result = performAction(ConstantUtil.getEvent(), "P",
								eChart, 1);
						if (wasUsed(result)) {
							continue;
						}
					}
					trunk = 8;
				}
				// Phase 5 Corruption
				if (trunk == 8) {
					mu.q("AGGRESSIVE army adjacent to TARGET not under siege?");
					if (yes()) {
						result = performAction(ConstantUtil.getAttackcdie(),
								"C", bChart, 1);
						if (wasUsed(result)) {
							continue;
						}
						result = performAction(ConstantUtil.getArmy2(), "A",
								aChart, 2);
						if (wasUsed(result)) {
							continue;
						}
					}
					trunk = 9;
				}
				// Phase 5 Corruption
				if (trunk == 9) {
					if (!baseGame) {
						mu.q("All factions in play?");
						if (!yes()) {
							result = performAction(
									ConstantUtil.getRecruitfaction(), "M",
									fChart, 4);
							if (wasUsed(result)) {
								continue;
							}
						}
						result = performAction(
								ConstantUtil.getPlayfactionevent(), "P", fChart,
								1);
						if (wasUsed(result)) {
							continue;
						}
					}
					// Phase 5 Corruption
					result = performAction(ConstantUtil.getEvent(), "P", eChart,
							1);
					if (wasUsed(result)) {
						continue;
					}
					result = performAction(ConstantUtil.getArmy3(), "A", aChart,
							3);
					if (wasUsed(result)) {
						continue;
					}
					result = performAction(ConstantUtil.getCharacter(), "C",
							cChart, 1);
					if (wasUsed(result)) {
						continue;
					}
					if (!baseGame) {
						result = performAction(ConstantUtil.getRecruitfaction(),
								"M", fChart, 4);
						if (wasUsed(result)) {
							continue;
						}
					}
					// Phase 5 Corruption
					result = performAction(ConstantUtil.getMuster(), "M",
							mChart, 1);
					if (wasUsed(result)) {
						continue;
					}
					if (!baseGame) {
						result = performAction(
								ConstantUtil.getDrawfactionevent(), "P", fChart,
								2);
						if (wasUsed(result)) {
							continue;
						}
					}
					// Phase 5 Corruption
					mu.q("Has unplayable die?");
					if (yes()) {
						mu.a(ConstantUtil.getDiscardunplayable());
						mu.printQuellerPool();
						mu.q("Discarded die: ");
						dieToDiscard = sc.nextLine();
						dieToDiscard = dieToDiscard.trim().toUpperCase();
						while (!DiceUtil.isInPool(dieToDiscard)) {
							mu.m("Die (" + dieToDiscard
									+ ") is not in the pool.");
							mu.printQuellerPool();
							mu.q("Discarded die: ");
							dieToDiscard = sc.nextLine();
							dieToDiscard = dieToDiscard.trim().toUpperCase();
						}
						DiceUtil.removeDie(dieToDiscard);
						result = ConstantUtil.getUsed();
						continue;
					}
					mu.q("End of Phase 5 without any action? Queller might have a problem... let's investigate");
				}
			}
			break;
		/*
		 ********************
		 * Phase 5 Military *
		 ********************
		 */
		case 'm':
			while (!wasUsed(result)) {
				if (DiceUtil.poolIsEmpty()) {
					mu.a("Use Muster die set aside for Minion");
					do {
						mu.m("Which one?");
						fu.printPriorities("MusterChart-Minions");
						mu.q("Number: ");
						minionNumber = sc.nextInt();
					} while (!DiceUtil.betweenOneAndThree(minionNumber));
					switch (minionNumber) {
					case 1:
						mu.m("Saruman can only go to Orthanc");
						break;
					case 2:
						fu.printPriorities("CharacterChart-Witch-king");
						break;
					case 3:
						fu.printPriorities("CharacterChart-MouthOfSauron");
						break;
					default:
						break;
					}
					mu.a("Muster Minion");
					mu.a("Using " + DiceUtil.getDieFromSaved() + " die");
					result = ConstantUtil.getUsed();
					continue;
				}

				// Phase 5 Military
				if (trunk == 1) {
					mu.q("SP under THREAT or FP EXPOSED?");
					if (yes()) {
						branch = 1;
						// Phase 5 Military
						if (branch == 1) {
							mu.q("AGGRESSIVE army adjacent to THREAT?");
							if (yes()) {
								result = performAction(
										ConstantUtil.getAttackcdie(), "C",
										bChart, 1);
								if (wasUsed(result)) {
									continue;
								}
								result = performAction(ConstantUtil.getArmy(),
										"A", aChart, 1);
								if (wasUsed(result)) {
									continue;
								} else {
									branch = 6;
								}
							} else {
								branch = 2;
							}
						}
						// Phase 5 Military
						if (branch == 2) {
							mu.q("Move will create AGGRESSIVE army adjacent to THREAT?");
							if (yes()) {
								result = performAction(ConstantUtil.getArmy(),
										"A", aChart, 1);
								if (wasUsed(result)) {
									continue;
								} else {
									branch = 6;
								}
							} else {
								branch = 3;
							}
						}
						// Phase 5 Military
						if (branch == 3) {
							mu.q("Move will increase army in Stronghold under THREAT?");
							if (yes()) {
								result = performAction(ConstantUtil.getArmy(),
										"A", aChart, 1);
								if (wasUsed(result)) {
									continue;
								} else {
									branch = 6;
								}
							} else {
								branch = 4;
							}
						}
						// Phase 5 Military
						if (branch == 4) {
							mu.q("MOBILE army's route to closest TARGET takes it towards THREAT?");
							if (yes()) {
								result = performAction(ConstantUtil.getArmy(),
										"A", aChart, 1);
								if (wasUsed(result)) {
									continue;
								} else {
									branch = 6;
								}
							} else {
								branch = 5;
							}
						}
						// Phase 5 Military
						if (branch == 5) {
							mu.q("Can move towards EXPOSED?");
							if (yes()) {
								result = performAction(ConstantUtil.getArmy(),
										"A", aChart, 1);
								if (wasUsed(result)) {
									continue;
								} else {
									branch = 6;
								}
							} else {
								branch = 6;
							}
						}
						// Phase 5 Military
						if (branch == 6) {
							mu.q("Can muster in Stronghold under THREAT?");
							if (yes()) {
								result = performAction(ConstantUtil.getMuster(),
										"M", mChart, 1);
								if (wasUsed(result)) {
									continue;
								}
							}
							branch = 7;
						}
						// Phase 5 Military
						if (branch == 7) {
							mu.q("THREAT is sieging an SP Stronghold which can use more leadership?");
							if (yes()) {
								result = performAction("Character", "C", cChart,
										1);
								if (wasUsed(result)) {
									continue;
								}
							}
						}
					}
					trunk = 2;
				}
				// Phase 5 Military
				if (trunk == 2) {
					mu.q("A MOBILE minion is in a non-AGGRESSIVE army against all adjacent At War armies?");
					if (yes()) {
						result = performAction(ConstantUtil.getCharacter2(),
								"C", cChart, 2);
						if (wasUsed(result)) {
							continue;
						}
					}
					trunk = 3;
				}
				// Phase 5 Military
				if (trunk == 3) {
					if (baseGame) {
						mu.q("Can muster Minion OR S&E not At War?");
					} else {
						mu.q("Can muster Minion OR S&E not At War OR No Faction recruited?");
					}
					if (yes()) {
						result = performAction(ConstantUtil.getMuster(), "M",
								mChart, 1);
						if (wasUsed(result)) {
							continue;
						}
					}
					trunk = 4;
				}
				// Phase 5 Military
				if (trunk == 4) {
					mu.q("FSP in Mordor or revealed AND Character cards > 0?");
					if (yes()) {
						branch = 1;
						if (branch == 1) {
							mu.q("Holding \"FSP revealed\" Character card?");
							if (yes()) {
								result = performAction(
										ConstantUtil.getPlaycardcdie(), "C",
										eChart, 1);
								if (wasUsed(result)) {
									continue;
								}
								result = performAction(
										ConstantUtil.getPlaycardpdie(), "P",
										eChart, 1);
								if (wasUsed(result)) {
									continue;
								}
							}
						}
					}
					trunk = 5;
				}
				// Phase 5 Military
				if (trunk == 5) {
					mu.q("AGGRESSIVE army adjacent to TARGET? OR A MOBILE army adjacent to army which is blocking the route to TARGET?");
					if (yes()) {
						fu.printPriorities(
								"Phase5-Military-T5-AnyConditionTrue");
						mu.q("Are any of these true?");
						if (yes()) {
							result = performAction(ConstantUtil.getCharacter(),
									"C", cChart, 1);
							if (wasUsed(result)) {
								continue;
							}
							result = performAction(ConstantUtil.getAttackadie(),
									"A", aChart, 1);
							if (wasUsed(result)) {
								continue;
							}
						}
					}
					trunk = 6;
				}
				// Phase 5 Military
				if (trunk == 6) {
					mu.q("Playable Strategy Cards??");
					if (yes()) {
						result = performAction(ConstantUtil.getEventmdie(), "M",
								eChart, 1);
						if (wasUsed(result)) {
							continue;
						}
						result = performAction(ConstantUtil.getEventadie(), "A",
								eChart, 1);
						if (wasUsed(result)) {
							continue;
						}
					}
					mu.q("Can Queller pass?");
					if (yes()) {
						mu.a(ConstantUtil.getPass());
						result = ConstantUtil.getUsed();
						continue;
					}
					if (!baseGame) {
						result = performAction(
								ConstantUtil.getPlayfactionevent(), "P", fChart,
								1);
						if (wasUsed(result)) {
							continue;
						}
					}
					result = performAction(ConstantUtil.getEvent(), "P", eChart,
							1);
					if (wasUsed(result)) {
						continue;
					}
					trunk = 7;
				}
				// Phase 5 Military
				if (trunk == 7) {
					mu.q("AGGRESSIVE army adjacent to TARGET?");
					if (yes()) {
						result = performAction(ConstantUtil.getCharacter(), "C",
								cChart, 1);
						if (wasUsed(result)) {
							continue;
						}
					}
					result = performAction(ConstantUtil.getArmy2(), "A", aChart,
							2);
					if (wasUsed(result)) {
						continue;
					}
					result = performAction(ConstantUtil.getCharacter(), "C",
							cChart, 1);
					if (wasUsed(result)) {
						continue;
					}
					// Phase 5 Military
					if (!baseGame) {
						result = performAction(ConstantUtil.getRecruitfaction(),
								"M", fChart, 4);
						if (wasUsed(result)) {
							continue;
						}
					}
					result = performAction(ConstantUtil.getMuster(), "M",
							mChart, 1);
					if (wasUsed(result)) {
						continue;
					}
					if (!baseGame) {
						result = performAction(
								ConstantUtil.getDrawfactionevent(), "P", fChart,
								2);
						if (wasUsed(result)) {
							continue;
						}
					}
					// Phase 5 Military
					mu.q("Has unplayable die?");
					if (yes()) {
						mu.a(ConstantUtil.getDiscardunplayable());
						mu.printQuellerPool();
						mu.q("Discarded die: ");
						dieToDiscard = sc.nextLine();
						dieToDiscard = dieToDiscard.trim().toUpperCase();
						while (!DiceUtil.isInPool(dieToDiscard)) {
							mu.m("Die (" + dieToDiscard
									+ ") is not in the pool.");
							mu.printQuellerPool();
							mu.q("Discarded die: ");
							dieToDiscard = sc.nextLine();
							dieToDiscard = dieToDiscard.trim().toUpperCase();
						}
						DiceUtil.removeDie(dieToDiscard);
						result = ConstantUtil.getUsed();
						continue;
					}
					mu.q("End of Phase 5 without any action? Queller might have a problem... let's investigate");
				}
			}
			break;
		default:
			break;
		}
	}

	/**
	 * Phase 6) - Victory Check<br>
	 * The objective of this phase is to determine if a side won by Military
	 * Victory. If it happened, the game is over.
	 * 
	 * @return True if there was a Military victory, false if not.
	 */
	private static boolean phase6() {
		mu.printPhaseHeader(6);
		System.out.println("Military Victory for any side?");
		if (yes()) {
			return true;
		}
		return false;
	}

	/**
	 * @return True if Queller has a ring. False if not.
	 */
	public static boolean hasRing() {
		if (quellerRings > 0) {
			return true;
		}
		return false;
	}

	/**
	 * @return True if player is playing the base game. False if playing with an
	 *         expansion (WoME).
	 */
	public static boolean isBaseGame() {
		return baseGame;
	}

	/**
	 * Input validation for the Action Roll.
	 * 
	 * @return True if the roll is correct (every die face corresponds to one
	 *         that exists). False if not.
	 * 
	 */
	public static boolean rollIsOk(String roll) {
		String[] split = roll.split(" ");
		for (String d : split) {
			if (!d.equals("A") && !d.equals("C") && !d.equals("E")
					&& !d.equals("H") && !d.equals("M") && !d.equals("P")) {
				mu.a("Die result (" + d + ") is not valid");
				return false;
			}
		}
		return true;
	}

	/**
	 * @return True if die was "used" in the chart (or saved for last, in case
	 *         of a Muster special case). False, if not.
	 */
	public static boolean wasUsed(String r) {
		return r.equals(ConstantUtil.getUsed());
	}

	/**
	 * Asks yes or no and only accepts inputs 'y' or 'n' (or uppercase).
	 * 
	 * @return True if yes. False if no.
	 * 
	 */
	public static boolean yes() {
		String s;
		boolean response = false;
		do {
			System.out.print(" (y/n): ");
			s = sc.nextLine();
			s = s.toLowerCase();
		} while (!s.equals("y") && !s.equals("n"));
		if (s.equals("y")) {
			response = true;
		}
		return response;
	}

	/**
	 * Choose M or A die as first priority before trying to use H die.
	 * 
	 * @return A String with the die chosen (A/M or H). If none is available in
	 *         the pool, returns null.
	 */
	public static String chooseDie(String d1, String d2) {
		if (DiceUtil.isInPool(d1)) {
			return d1;
		} else {
			if (DiceUtil.isInPool(d2)) {
				return d2;
			}
		}
		return null;
	}

	/**
	 * Tries to perform an action using a die.
	 * 
	 * @param action
	 *            A String with the intended. In the future, will try to extract
	 *            the chart AND die to be used, just from the action text.
	 * @param die
	 *            The preferred die to be consumed in this action. Will check
	 *            availability of the die inside this method.
	 * @param chart
	 *            The chart to be traversed
	 * @param entry
	 *            The entry point of the chart
	 * @return A String telling if the die was used or not. If true, it will
	 *         return "used" and stop the execution of Phase 5. If not true,
	 *         will continue to die selection at the point where it came in
	 *         Phase 5.
	 * 
	 */
	public static String performAction(String action, String die, String chart,
			int entry) {
		String chartResult = "not used";
		String dieToUse = null;

		if (DiceUtil.hasDie()) {
			if (DiceUtil.poolIsEmpty()) {
				dieToUse = DiceUtil.getDieFromSaved();
			} else {
				// Pool has a die
				if (die.equals("A") || die.equals("M")) {
					dieToUse = chooseDie(die, "H");
				} else {
					if (DiceUtil.isInPool(die)) {
						dieToUse = die;
					}
				}
			}
		}

		// TODO T - Print action before chart call
		// mu.a("Trying to perform: " + action);
		// mu.a("Using this die: " + dieToUse);
		if (dieToUse != null) {
			switch (strategy) {
			//@formatter:off
			/* *******************
			 * CORRUPTION CHARTS *
			 * *******************/
			//@formatter:on
			case 'c':
				switch (chart) {
				case "Army":
					chartResult = armyChart.runChart(strategy, dieToUse, entry);
					if (!wasUsed(chartResult)) {
						if (chartResult.equals(ConstantUtil.getAttack())) {
							chartResult = performAction(
									ConstantUtil.getAttack(), dieToUse, bChart,
									1);
						}
						if (chartResult.equals(ConstantUtil.getMove())) {
							mu.a("Move 2nd time");
							chartResult = performAction(ConstantUtil.getMove(),
									dieToUse, aChart, 1);
						}
					}
					break;
				case "Battle":
					chartResult = battleChart.runChart(strategy, dieToUse,
							entry);
					break;
				case "Character":
					chartResult = characterChart.runChart(strategy, dieToUse,
							entry);
					if (!wasUsed(chartResult)) {
						if (chartResult.equals(ConstantUtil.getAttack())) {
							chartResult = performAction(
									ConstantUtil.getAttack(), dieToUse, bChart,
									1);
						}
						if (chartResult.equals(ConstantUtil.getArmy4())) {
							chartResult = performAction(ConstantUtil.getArmy4(),
									dieToUse, aChart, 4);
						}
						if (chartResult.equals(ConstantUtil.getEventcdie())) {
							chartResult = performAction(
									ConstantUtil.getEventcdie(), dieToUse,
									eChart, 1);
						}
					}
					break;
				case "Event":
					chartResult = eventChart.runChart(strategy, dieToUse,
							entry);
					break;
				case "Faction":
					chartResult = factionChart.runChart(strategy, dieToUse,
							entry);
					break;
				case "Muster":
					chartResult = musterChart.runChart(strategy, dieToUse,
							entry);
					if (!wasUsed(chartResult)) {
						if (chartResult
								.equals(ConstantUtil.getRecruitfaction())) {
							chartResult = performAction(
									ConstantUtil.getRecruitfaction(), dieToUse,
									fChart, 4);
						}
					}
					break;
				default:
					break;
				}
				break;
			//@formatter:off
			/* *****************
			 * MILITARY CHARTS *
			 * *****************/
			//@formatter:on
			case 'm':
				switch (chart) {
				case "Army":
					chartResult = armyChart.runChart(strategy, dieToUse, entry);
					if (!wasUsed(chartResult)) {
						if (chartResult.equals(ConstantUtil.getAttack())) {
							chartResult = performAction(
									ConstantUtil.getAttack(), dieToUse, bChart,
									1);
						}
						if (chartResult.equals(ConstantUtil.getMove())) {
							mu.a("Move 2nd time");
							chartResult = performAction(ConstantUtil.getMove(),
									dieToUse, aChart, 1);
						}
					}
					break;
				case "Battle":
					chartResult = battleChart.runChart(strategy, dieToUse,
							entry);
					break;
				case "Character":
					chartResult = characterChart.runChart(strategy, dieToUse,
							entry);
					if (!wasUsed(chartResult)) {
						if (chartResult.equals(ConstantUtil.getAttack())) {
							chartResult = performAction(
									ConstantUtil.getAttack(), dieToUse, bChart,
									1);
						}
						if (chartResult.equals(ConstantUtil.getArmy3())) {
							chartResult = performAction(ConstantUtil.getArmy3(),
									dieToUse, aChart, 3);
						}
						if (chartResult.equals(ConstantUtil.getEventcdie())) {
							chartResult = performAction(
									ConstantUtil.getEventcdie(), dieToUse,
									eChart, 1);
						}
					}
					break;
				case "Event":
					chartResult = eventChart.runChart(strategy, dieToUse,
							entry);
					break;
				case "Faction":
					chartResult = factionChart.runChart(strategy, dieToUse,
							entry);
					break;
				case "Muster":
					chartResult = musterChart.runChart(strategy, dieToUse,
							entry);
					if (!wasUsed(chartResult)) {
						if (chartResult
								.equals(ConstantUtil.getRecruitfaction())) {
							chartResult = performAction(
									ConstantUtil.getRecruitfaction(), dieToUse,
									fChart, 4);
						}
					}
					break;
				default:
					break;
				} // switch military charts
			} // switch strategy
		} else {
			// TODO T - Die not available
			// mu.m("*** Die not available ***");
		}
		return chartResult;
	}

}
