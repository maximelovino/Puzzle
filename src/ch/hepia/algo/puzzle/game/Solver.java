package ch.hepia.algo.puzzle.game;

import ch.hepia.algo.puzzle.utils.State;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Stack;
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
				finishedState = blindSolve(initialState,size,false);
				break;
			case "cachedBlind":
				finishedState = blindSolve(initialState,size,true);
				break;
			case "heuristics":
				break;
			default:
				throw new IllegalArgumentException("Your type of search doesn't exist");
		}


		displayFullPathOfResult(finishedState);



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

	public static void displayFullPathOfResult(State result){

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

}
