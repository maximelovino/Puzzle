package ch.hepia.algo.puzzle.game;

import ch.hepia.algo.puzzle.utils.State;

import java.util.ArrayList;

public class StateTester {

	public static void main (String[] args) {
		State randSt = State.getRandomState(3);

		State st = new State("123456780",3,0,null);

		System.out.println(st);
		System.out.println();
		System.out.println();
		System.out.println(randSt);

		ArrayList<State> next = randSt.successors();

		for (State n :
				next) {
			System.out.println();
			System.out.println(n);
		}
	}
}
