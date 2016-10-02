package ch.hepia.algo.puzzle.game;

import ch.hepia.algo.puzzle.utils.State;

import java.util.ArrayList;

public class Solver {

	public static void main (String[] args) {
		String st = "123450876";

		State state = new State(st,3,0);
		System.out.println("Current state");
		System.out.println(state);
		System.out.println("State as string");
		System.out.println(state.getState());

		ArrayList<State> succ = state.successors();
		System.out.println("Successors");
		for (State successor :
				succ) {
			System.out.println();
			System.out.println(successor);
		}
	}

}
