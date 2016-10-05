package ch.hepia.algo.puzzle.game;

import ch.hepia.algo.puzzle.utils.State;

import java.util.HashSet;
import java.util.concurrent.LinkedBlockingQueue;

public class Solver {

	public static void main (String[] args) {
		if (args.length < 3){
			System.out.println("Please use these programs with the following arguments");
			System.out.println("<Search type> <Initial State> <Goal State>");
			System.out.println();
			System.out.print("\t");
			System.out.println("<Search type>: blind, cachedBlind, heuristics");
			System.out.print("\t");
			System.out.println("<Initial State>: Write the cases from top to bottom, left to right, first one top left, last one bottom right, 0 for the empty, or use 'RANDOM n' where n is the size of nxn puzzle");
			System.out.print("\t");
			System.out.println("<Goal State>: Same as <Initial State> but you can use GOAL to set the goal state as the logical goal state for that size");
			return;
		}


		State st = State.getRandomState(3);
		System.out.println(st);

//		String st = "820347516";
//		int size = 3;
//		State initialState = new State(st,size,0,null);
//		System.out.println("initial state");
//		System.out.println(initialState);
//
//		State finishedState = blindSolve(initialState,size,false);
//
//		System.out.println();
//		if (finishedState!=null) {
//			System.out.println(finishedState);
//			System.out.println("Cost "+finishedState.getCost());
//		}else{
//			System.out.println("no solution was found");
//		}

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
