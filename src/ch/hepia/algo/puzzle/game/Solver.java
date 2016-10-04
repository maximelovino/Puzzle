package ch.hepia.algo.puzzle.game;

import ch.hepia.algo.puzzle.utils.State;

import java.util.HashSet;
import java.util.concurrent.LinkedBlockingQueue;

public class Solver {

	public static void main (String[] args) {
		String st = "820347516";
		int size = 3;
		State initialState = new State(st,size,0);
		System.out.println("initial state");
		System.out.println(initialState);

		State finishedState = blindSolve(initialState,size,false);

		System.out.println();
		if (finishedState!=null) {
			System.out.println(finishedState);
			System.out.println("Cost "+finishedState.getCost());
		}else{
			System.out.println("no solution was found");
		}

	}


	public static State blindSolve(State initialState, int size, boolean optimize){
		State goalState = State.getGoalState(size);

//		if (initialState.equals(goalState))
//			return initialState;

		LinkedBlockingQueue<State> queue = new LinkedBlockingQueue<>();

		State currentState;

		HashSet<State> visited = new HashSet<>();

		queue.add(initialState);

		while (!queue.isEmpty()){
			currentState = queue.poll();

			if (!visited.contains(currentState)){
				visited.add(currentState);
				if (currentState.equals(goalState))
					return currentState;
				else
					queue.addAll(currentState.successors());
			}

		}

		return null;
	}



}
