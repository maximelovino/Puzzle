package ch.hepia.algo.puzzle.game;

import ch.hepia.algo.puzzle.utils.State;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Stack;
import java.util.concurrent.LinkedBlockingQueue;

public class Solver {
	private static final int MAX_SIZE_QUEUE = 1000000;
	private static int count = 0;

	public static void main (String[] args) {
		if (args.length < 3){
			System.out.println("Please use these programs with the following arguments");
			System.out.println("<Search type> <Initial State> <Goal State>");
			System.out.println();
			System.out.print("\t");
			System.out.println("<Search type>: blind, cachedBlind, manhattan, misplaced (last two are heuristics)");
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
				size = Integer.valueOf(args[2]);
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
		initialState.setGoalState(goalState);

		switch (args[0]){
			case "blind":
				finishedState = blindSolve(initialState,goalState,size,false);
				break;
			case "cachedBlind":
				finishedState = blindSolve(initialState,goalState,size,true);
				break;
			case "manhattan":
				finishedState = heuristicsSolve(initialState,goalState,size,true);
				break;
			case "misplaced":
				finishedState = heuristicsSolve(initialState,goalState,size,false);
				break;
			default:
				throw new IllegalArgumentException("Your type of search doesn't exist");
		}
		if (finishedState != null) {

			if (!finishedState.equals(goalState)) {
				System.out.println("we couldn't do better than this");
				System.out.println(finishedState);
			} else {
				displayFullPathOfResult(finishedState);
			}
		}else{
			System.out.println(initialState);
			System.out.println("no solution was found");
		}
		System.out.println("We went through " + count + " states");



	}


	private static State blindSolve(State initialState, State goalState, int size, boolean optimize){

		LinkedBlockingQueue<State> queue = new LinkedBlockingQueue<>();

		State currentState;

		HashSet<State> visited = new HashSet<>();

		queue.add(initialState);

		while (!queue.isEmpty()){
			currentState = queue.poll();
			count++;


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
			State tempState = path.pop();
			System.out.println(tempState);
			System.out.println();
		}

		System.out.println("Cost of path: "+result.getCost());
	}

	private static State heuristicsSolve(State initialState, State goalState, int size, boolean manhattan){
		PriorityQueue<State> priority = new PriorityQueue<>((o1, o2) -> {
			Integer heuristic1 = manhattan ? o1.getManhattanDistance() : o1.getMisplacedTiles();
			Integer heuristic2 = manhattan ? o2.getManhattanDistance() : o2.getMisplacedTiles();
			return heuristic1.compareTo(heuristic2);
		});

		State currentState = null;

		HashSet<State> visited = new HashSet<>();

		priority.add(initialState);

		while (!priority.isEmpty()) {
			currentState = priority.poll();
			count++;
			if (currentState.equals(goalState))
				return currentState;
			ArrayList<State> succ = currentState.successors();

			for (State successor : succ) {
				if (!visited.contains(successor) && !priority.contains(successor))
					priority.add(successor);
			}
			visited.add(currentState);
		}

		return currentState;
	}
}
