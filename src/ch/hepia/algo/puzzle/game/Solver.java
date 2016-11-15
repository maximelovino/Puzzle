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
			System.out.println("<Search type> <Initial State> <Size> <Goal State>");
			System.out.println();
			System.out.print("\t");
			System.out.println("<Search type>: blind, cachedBlind, manhattan, misplaced (last two are heuristics)");
			System.out.print("\t");
			System.out.println("<Initial State>: Write the cases from top to bottom, left to right, first one top left, " +
					"last one bottom right, 0 for the empty, separated by '-' or use 'RANDOM'");
			System.out.print("\t");
			System.out.println("<Goal State>: Same as <Initial State> but you can use GOAL " +
					"to set the goal state as the perfect goal state for that size");
			return;
		}

		State initialState = null;
		State goalState = null;
		State finishedState = null;
		int size = 0;
		try {
			size = Integer.valueOf(args[2]);
		} catch (NumberFormatException e) {
			System.out.println("The size param was not valid");
			e.printStackTrace();
			System.exit(1);
		}

		// Switch for initial State, if it's random, we generate it, otherwise we read from input
		switch (args[1]){
			case "RANDOM":
				initialState = State.getRandomState(size);
				break;
			default:
				try {
					initialState = new State(args[1], size, 0, null);
				} catch (IllegalArgumentException e) {
					System.out.println("The input for initialState was not conform to the size");
					e.printStackTrace();
					System.exit(1);
				}
				break;
		}

		// Switch for goal State
		switch (args[3]){
			case "RANDOM":
				goalState = State.getRandomState(size);
				break;
			case "GOAL":
				goalState = State.getPerfectState(size);
				break;
			default:
				try {
					goalState = new State(args[3], size, 0, null);
				} catch (IllegalArgumentException e) {
					System.out.println("The input for initialState was not conform to the size");
					e.printStackTrace();
					System.exit(1);
				}
				break;
		}
		initialState.setGoalState(goalState);

		// Switch for how to solve
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
				System.out.println("This type of search is invalid");
				System.exit(1);
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


	/**
	 * Method that implements a blindSearch to solve
	 *
	 * @param initialState The initialstate of our puzzle
	 * @param goalState    The state we want to achieve
	 * @param size         The size of our puzzle
	 * @param optimize     If we use a cache to store already visited states
	 * @return The goalState created by the path we used, or null if there is no solution
	 */
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

	/**
	 * Method to display the path of the result, from initialState to goalState
	 *
	 * @param result	The result state
	 */
	private static void displayFullPathOfResult (State result) {

		//We stack from result to initialState using parentState and then we unstack and print

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

		System.out.println("Cost of path: " + result.getCost());
	}

	/**
	 * Heuristic search method to solve puzzle
	 *
	 * @param initialState    The initial state of our puzzle
	 * @param goalState        The goal state of our puzzle
	 * @param size            The size of the puzzle
	 * @param manhattan        Boolean to tell if we use manhattan or misplaced tiles heuristics
	 * @return The result state, obtained from the search
	 */
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
