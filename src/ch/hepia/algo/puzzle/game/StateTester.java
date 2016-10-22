package ch.hepia.algo.puzzle.game;

import ch.hepia.algo.puzzle.utils.State;

public class StateTester {

	public static void main (String[] args) {
		State randSt = State.getRandomState(4);
		State goal = State.getGoalState(4);
		String stateString = "1-2-3-4-5-6-7-8-0";
		State testString = new State(stateString,3,0,null);
		System.out.println(testString);

		System.out.println("Random");
		System.out.println(randSt);
		System.out.println();
		System.out.println("Goal");
		System.out.println(goal);

		for (State next :
				randSt.successors()) {
			System.out.println();
			System.out.println(next);
		}
	}
}
