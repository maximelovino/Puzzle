package ch.hepia.algo.puzzle.game;

import ch.hepia.algo.puzzle.utils.State;

import java.util.ArrayList;

public class Solver {

	public static void main (String[] args) {
		String st = "123450876";

		State state = new State(st,3);

		System.out.println(state);
		System.out.println(state.getState());

		ArrayList<State> succ = state.successors();

		for (State successor :
				succ) {
			System.out.println();
			System.out.println(successor);
		}
	}

}
