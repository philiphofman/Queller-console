package br.wotr.util;

public class ConstantUtil {

	// action text constants
	private final static String advancenation = "Advance Nation on Political Track";
	private final static String army = "Army";
	private final static String army2 = "Army 2";
	private final static String army3 = "Army 3";
	private final static String army4 = "Army 4";
	private final static String attack = "Attack";
	private final static String attackcdie = "Attack using C die";
	private final static String attackadie = "Attack using A die";
	private final static String bringfactionintoplay = "Bring Faction into play";
	private final static String continuedieselection = "Continue die selection";
	private final static String character = "Character";
	private final static String character2 = "Character 2";
	private final static String discard = "Discard";
	private final static String discardunplayable = "Discard unplayable die";
	private final static String downgradeeliteandfight = "Downgrade Elite and fight another round";
	private final static String drawcards = "Draw Cards";
	private final static String drawcharactercard = "Draw Character Card";
	private final static String drawfactioncard = "Draw Faction Card";
	private final static String drawfactionevent = "Draw Faction Event";
	private final static String drawstrategycard = "Draw Strategy Card";
	private final static String end = "End";
	private final static String event = "Event";
	private final static String event2 = "Event 2";
	private final static String eventadie = "Event using A die";
	private final static String eventcdie = "Event using C die";
	private final static String eventmdie = "Event using M die";
	private final static String fightanotherround = "Fight another round";
	private final static String hassaveddie = "Already saved a Muster die for last. Continuing.";
	private final static String move = "Move";
	private final static String moveattack = "Move/Attack";
	private final static String moveintoregion = "Move into Region";
	private final static String moveminionsandnazgul = "Move Minions and Nazgûl";
	private final static String movenearestarmytowardsexposed = "Move nearest army towards exposed";
	private final static String moveoneunitintoregion = "Move 1 unit into Region";
	private final static String movetowardswoodlandrealm = "Move towards Woodland Realm";
	private final static String muster = "Muster";
	private final static String musterfactionfigures = "Muster Faction Figures";
	private final static String musterminion = "Muster Minion";
	private final static String pass = "Pass";
	private final static String placeaccordingcpageminioncard = "Place according to Character page and Minion card";
	private final static String playarmymustercard = "Play Army Card/Play Muster Card";
	private final static String playarmycard = "Play Army Card";
	private final static String playcard = "Play Card";
	private final static String playcardcdie = "Play Card using C die";
	private final static String playcardpdie = "Play Card using P die";
	private final static String playfactionevent = "Play Faction Event";
	private final static String playmustercard = "Play Muster Card";
	private final static String recruitfaction = "Recruit Faction";
	private final static String retreat = "Retreat";
	private final static String retreatintostronghold = "Retreat into Stronghold";
	private final static String savemusterdieforlast = "Saving Muster for use as last die and returning to die selection";
	private final static String troopsfromnationsnotatwar = "Troops from Nations Not At War form rearguard";
	private final static String used = "used";
	private final static String usemustersetaside = "Use Muster die set aside for Minion";

	// file name constants. follow formula fxcytz(bw)r for charts
	// where x is the chart name, y the strategy and z the trunk number, w is the
	// branch number, if present. r is the response to the question 'y' or 'n',
	// where there is difference. for phases fpxytzbw, x is the phase number
	private final static String facct10 = "ArmyChart-Corruption-T10";
	private final static String facct3 = "ArmyChart-Corruption-T3";
	private final static String facct7 = "ArmyChart-Corruption-T7";
	private final static String facct8 = "ArmyChart-Corruption-T8";
	private final static String facct9 = "ArmyChart-Corruption-T9";
	private final static String facmt4 = "ArmyChart-Military-T4";
	private final static String facmt5 = "ArmyChart-Military-T5";
	private final static String facmt6 = "ArmyChart-Military-T6";
	private final static String facmt7 = "ArmyChart-Military-T7";
	private final static String facmt8 = "ArmyChart-Military-T8";
	private final static String fbct2b2 = "BattleChart-T2B2";
	private final static String fbct2b3 = "BattleChart-T2B3";
	private final static String fbct2b456 = "BattleChart-T2B456";
	private final static String fbct34y = "BattleChart-T3T4-Yes";
	private final static String fbct4n = "BattleChart-T4-No";
	private final static String fbct6 = "BattleChart-T6";
	private final static String fbcremovecasualties = "BattleChart-RemoveCasualties";
	private final static String fccMouth = "CharacterChart-MouthOfSauron";
	private final static String fccNazgul = "CharacterChart-Nazgul";
	private final static String fccWitchking = "CharacterChart-Witch-king";
	private final static String fecct1ybase = "EventChart-Corruption-T1-Yes-Base";
	private final static String fecct1y = "EventChart-Corruption-T1-Yes";
	private final static String fecct2b2 = "EventChart-Corruption-T2B2";
	private final static String fecct3b1n = "EventChart-Corruption-T3B1-No";
	private final static String fecct3b1y = "EventChart-Corruption-T3B1-Yes";
	private final static String fecmeventdisc = "EventChart-Military-EventDiscard";
	private final static String fecmfactiondisc = "EventChart-Military-FactionDiscard";
	private final static String fecmt1ybase = "EventChart-Military-T1-Yes-Base";
	private final static String fecmt1y = "EventChart-Military-T1-Yes";
	private final static String fecmt2b2ybase = "EventChart-Military-T2B2-Yes-Base";
	private final static String fecmt2b2y = "EventChart-Military-T2B2-Yes";
	// TODO file names for faction charts priorities
	private final static String fmcminions = "MusterChart-Minions";
	private final static String fmcmusterinsparmy = "MusterChart-MusterInSPArmy";
	private final static String fmcnationsbase = "MusterChart-Nations-Base";
	private final static String fmcnations = "MusterChart-Nations";
	private final static String fmcprisecEN = "MusterChart-PriSecEN";
	private final static String fmcprisecER = "MusterChart-PriSecER";
	private final static String fmcprisecNN = "MusterChart-PriSecNN";
	private final static String fmcprisecRN = "MusterChart-PriSecRN";
	private final static String fmcsettlementclosest = "MusterChart-SettlementClosestTo";
	private final static String fp1ct1b1n = "Phase1-Corruption-T1B1-No";
	private final static String fp1ct1b1y = "Phase1-Corruption-T1B1-Yes";
	private final static String fp1ct2 = "Phase1-Corruption-T2";
	private final static String fp1mt1 = "Phase1-Military-T1";
	private final static String fp1mt2 = "Phase1-Military-T2";
	private final static String fp5mt5any = "Phase5-Military-T5-AnyConditionTrue";

	public static String getAdvancenation() {
		return advancenation;
	}

	public static String getArmy() {
		return army;
	}

	public static String getArmy2() {
		return army2;
	}

	public static String getArmy3() {
		return army3;
	}

	public static String getArmy4() {
		return army4;
	}

	public static String getAttack() {
		return attack;
	}

	public static String getAttackcdie() {
		return attackcdie;
	}

	public static String getAttackadie() {
		return attackadie;
	}

	public static String getBringfactionintoplay() {
		return bringfactionintoplay;
	}

	public static String getContinuedieselection() {
		return continuedieselection;
	}

	public static String getCharacter() {
		return character;
	}

	public static String getCharacter2() {
		return character2;
	}

	public static String getDiscard() {
		return discard;
	}

	public static String getDiscardunplayable() {
		return discardunplayable;
	}

	public static String getDowngradeeliteandfight() {
		return downgradeeliteandfight;
	}

	public static String getDrawcards() {
		return drawcards;
	}

	public static String getDrawcharactercard() {
		return drawcharactercard;
	}

	public static String getDrawfactioncard() {
		return drawfactioncard;
	}

	public static String getDrawfactionevent() {
		return drawfactionevent;
	}

	public static String getDrawstrategycard() {
		return drawstrategycard;
	}

	public static String getEnd() {
		return end;
	}

	public static String getEvent() {
		return event;
	}

	public static String getEvent2() {
		return event2;
	}

	public static String getEventadie() {
		return eventadie;
	}

	public static String getEventcdie() {
		return eventcdie;
	}

	public static String getEventmdie() {
		return eventmdie;
	}

	public static String getFightanotherround() {
		return fightanotherround;
	}

	public static String getHassaveddie() {
		return hassaveddie;
	}

	public static String getMove() {
		return move;
	}

	public static String getMoveattack() {
		return moveattack;
	}

	public static String getMoveintoregion() {
		return moveintoregion;
	}

	public static String getMoveminionsandnazgul() {
		return moveminionsandnazgul;
	}

	public static String getMovenearestarmytowardsexposed() {
		return movenearestarmytowardsexposed;
	}

	public static String getMoveoneunitintoregion() {
		return moveoneunitintoregion;
	}

	public static String getMovetowardswoodlandrealm() {
		return movetowardswoodlandrealm;
	}

	public static String getMuster() {
		return muster;
	}

	public static String getMusterfactionfigures() {
		return musterfactionfigures;
	}

	public static String getMusterminion() {
		return musterminion;
	}

	public static String getPass() {
		return pass;
	}

	public static String getPlaceaccordingcpageminioncard() {
		return placeaccordingcpageminioncard;
	}

	public static String getPlayarmymustercard() {
		return playarmymustercard;
	}

	public static String getPlayarmycard() {
		return playarmycard;
	}

	public static String getPlaycard() {
		return playcard;
	}

	public static String getPlaycardcdie() {
		return playcardcdie;
	}

	public static String getPlaycardpdie() {
		return playcardpdie;
	}

	public static String getPlayfactionevent() {
		return playfactionevent;
	}

	public static String getPlaymustercard() {
		return playmustercard;
	}

	public static String getRecruitfaction() {
		return recruitfaction;
	}

	public static String getRetreat() {
		return retreat;
	}

	public static String getRetreatintostronghold() {
		return retreatintostronghold;
	}

	public static String getSavemusterdieforlast() {
		return savemusterdieforlast;
	}

	public static String getTroopsfromnationsnotatwar() {
		return troopsfromnationsnotatwar;
	}

	public static String getUsed() {
		return used;
	}

	public static String getUsemustersetaside() {
		return usemustersetaside;
	}

	public static String getFacct10() {
		return facct10;
	}

	public static String getFacct3() {
		return facct3;
	}

	public static String getFacct7() {
		return facct7;
	}

	public static String getFacct8() {
		return facct8;
	}

	public static String getFacct9() {
		return facct9;
	}

	public static String getFacmt4() {
		return facmt4;
	}

	public static String getFacmt5() {
		return facmt5;
	}

	public static String getFacmt6() {
		return facmt6;
	}

	public static String getFacmt7() {
		return facmt7;
	}

	public static String getFacmt8() {
		return facmt8;
	}

	public static String getFbct2b2() {
		return fbct2b2;
	}

	public static String getFbct2b3() {
		return fbct2b3;
	}

	public static String getFbct2b456() {
		return fbct2b456;
	}

	public static String getFbct34y() {
		return fbct34y;
	}

	public static String getFbct4n() {
		return fbct4n;
	}

	public static String getFbct6() {
		return fbct6;
	}

	public static String getFbcremovecasualties() {
		return fbcremovecasualties;
	}

	public static String getFccmouth() {
		return fccMouth;
	}

	public static String getFccnazgul() {
		return fccNazgul;
	}

	public static String getFccwitchking() {
		return fccWitchking;
	}

	public static String getFecct1ybase() {
		return fecct1ybase;
	}

	public static String getFecct1y() {
		return fecct1y;
	}

	public static String getFecct2b2() {
		return fecct2b2;
	}

	public static String getFecct3b1n() {
		return fecct3b1n;
	}

	public static String getFecct3b1y() {
		return fecct3b1y;
	}

	public static String getFecmeventdisc() {
		return fecmeventdisc;
	}

	public static String getFecmfactiondisc() {
		return fecmfactiondisc;
	}

	public static String getFecmt1ybase() {
		return fecmt1ybase;
	}

	public static String getFecmt1y() {
		return fecmt1y;
	}

	public static String getFecmt2b2ybase() {
		return fecmt2b2ybase;
	}

	public static String getFecmt2b2y() {
		return fecmt2b2y;
	}

	public static String getFmcminions() {
		return fmcminions;
	}

	public static String getFmcmusterinsparmy() {
		return fmcmusterinsparmy;
	}

	public static String getFmcnationsbase() {
		return fmcnationsbase;
	}

	public static String getFmcnations() {
		return fmcnations;
	}

	public static String getFmcprisecen() {
		return fmcprisecEN;
	}

	public static String getFmcprisecer() {
		return fmcprisecER;
	}

	public static String getFmcprisecnn() {
		return fmcprisecNN;
	}

	public static String getFmcprisecrn() {
		return fmcprisecRN;
	}

	public static String getFmcsettlementclosest() {
		return fmcsettlementclosest;
	}

	public static String getFp1ct1b1n() {
		return fp1ct1b1n;
	}

	public static String getFp1ct1b1y() {
		return fp1ct1b1y;
	}

	public static String getFp1ct2() {
		return fp1ct2;
	}

	public static String getFp1mt1() {
		return fp1mt1;
	}

	public static String getFp1mt2() {
		return fp1mt2;
	}

	public static String getFp5mt5any() {
		return fp5mt5any;
	}

}
