package ch.hepia.algo.puzzle.game;

import ch.hepia.algo.puzzle.utils.State;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Stack;
import java.util.concurrent.LinkedBlockingQueue;

public class Solver {
	private static final int MAX_SIZE_QUEUE = 1000000;

	public static void main (String[] args) {
		if (args.length < 3){
			System.out.println("Please use these programs with the following arguments");
			System.out.println("<Search type> <Initial State> <Goal State>");
			System.out.println();
			System.out.print("\t");
			System.out.println("<Search type>: blind, cachedBlind, heuristics");
			System.out.print("\t");
			System.out.println("<Initial State>: Write the cases from top to bottom, left to right, first one top left, " +
					"last one bottom right, 0 for the empty, separated by '-' or use 'RANDOM n' where n is the size of nxn puzzle");
			System.out.print("\t");
			System.out.println("<Goal State>: Same as <Initial State> but you can use GOAL " +
					"to set the goal state as the logical goal state for that size");
			return;
		}

		State initialState;
		State goalState;
		State finishedState = null;
		int size;

		switch (args[1]){
			case "RANDOM":
				size = Integer.valueOf(args[2]);
				initialState = State.getRandomState(size);
				break;
			default:
				size = (int)Math.sqrt(args[1].length());
				initialState = new State(args[1],size,0,null);
				break;
		}


		switch (args[3]){
			case "RANDOM":
				goalState = State.getRandomState(size);
				break;
			case "GOAL":
				goalState = State.getGoalState(size);
				break;
			default:
				goalState = new State(args[3],size,0,null);
				break;
		}

		switch (args[0]){
			case "blind":
				finishedState = blindSolve(initialState,goalState,size,false);
				break;
			case "cachedBlind":
				finishedState = blindSolve(initialState,goalState,size,true);
				break;
			case "heuristics":
				break;
			default:
				throw new IllegalArgumentException("Your type of search doesn't exist");
		}

		if (size > 3 && finishedState!=goalState){
			System.out.println("we couldn't do better than this");
			System.out.println(finishedState);
		}else{
			displayFullPathOfResult(finishedState);
		}



	}


	private static State blindSolve(State initialState, State goalState, int size, boolean optimize){

		LinkedBlockingQueue<State> queue = new LinkedBlockingQueue<>();

		State currentState;

		HashSet<State> visited = new HashSet<>();

		queue.add(initialState);

		while (!queue.isEmpty()){
			currentState = queue.poll();

			//TODO check if we do like that
			if (size > 3 && queue.size()> MAX_SIZE_QUEUE) {
				return currentState;
			}

			if (optimize) {
				if (!visited.contains(currentState)){
					visited.add(currentState);
					if (currentState.equals(goalState))
						return currentState;
					else
						queue.addAll(currentState.successors());
				}
			}else{
				if (currentState.equals(goalState))
					return currentState;
				else
					queue.addAll(currentState.successors());
			}

		}

		return null;
	}

	private static void displayFullPathOfResult(State result){

		if (result == null){
			System.out.println("No path was found");
			return;
		}

		Stack<State> path = new Stack<>();
		State temp = result;

		while (temp!=null){
			path.push(temp);
			temp = temp.getParentState();
		}

		while (!path.isEmpty()){
			System.out.println();
			System.out.println(path.pop());
			System.out.println();
		}

		System.out.println("Cost of path: "+result.getCost());
	}

	private static State heuristicsSolve(State initialState, int size){
		return null;
	}

}
