package ch.hepia.algo.puzzle.game;

import ch.hepia.algo.puzzle.utils.State;

public class StateTester {

	public static void main (String[] args) {
		State randSt = State.getRandomState(3);
		State goal = State.getGoalState(3);
		String stateString = "1-2-3-4-5-6-8-7-0";
		State testString = new State(stateString,3,0,null);
		System.out.println(testString);
		System.out.println("Misplaced tiles here: "+testString.getMisplacedTiles());

		System.out.println("Random");
		System.out.println(randSt);
		System.out.println("Misplaced tiles here: "+randSt.getMisplacedTiles());
		System.out.println();
		System.out.println("Goal");
		System.out.println(goal);
		System.out.println("Misplaced tiles here: "+goal.getMisplacedTiles());

		for (State next :
				randSt.successors()) {
			System.out.println();
			System.out.println(next);
		}
	}
}
