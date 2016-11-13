package ch.hepia.algo.puzzle.game;

import ch.hepia.algo.puzzle.utils.State;

import java.util.PriorityQueue;

public class StateTester {

	public static void main (String[] args) {
		State randSt = State.getRandomState(3);
		State goal = State.getGoalState(3);
		String stateString = "1-2-3-4-5-6-8-7-0";
		State testString = new State(stateString,3,0,null);
		State testString2 = new State(stateString, 3, 2, goal);
		System.out.println(testString);
		System.out.println("Misplaced tiles here: "+testString.getMisplacedTiles());

		System.out.println("Random");
		System.out.println(randSt);
		System.out.println("Misplaced tiles here: "+randSt.getMisplacedTiles());
		boolean manhattan = false;
		PriorityQueue<State> queue = new PriorityQueue<>((o1, o2) -> {
			Integer heuristic1 = manhattan ? o1.getManhattanDistance() : o1.getMisplacedTiles();
			Integer heuristic2 = manhattan ? o2.getManhattanDistance() : o2.getMisplacedTiles();
			return heuristic1.compareTo(heuristic2);
		});

		queue.add(testString);
		queue.add(goal);
		queue.add(randSt);


		System.out.println(queue.contains(testString2));
		System.out.println(testString2.equals(testString));
		System.out.println(testString.hashCode());
		System.out.println(testString2.hashCode());
		while (!queue.isEmpty())
			System.out.println(queue.poll() + "\n");
	}
}
