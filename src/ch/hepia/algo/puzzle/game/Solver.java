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
		/*
		String st = "820347516";
		int size = 3;
		State initialState = new State(st,size,0,null);
		*/

		State initialState = State.getRandomState(3);

		State finishedState = blindSolve(initialState,3,false);
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
