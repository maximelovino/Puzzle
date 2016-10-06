package ch.hepia.algo.puzzle.utils;

import java.util.ArrayList;
import java.util.Random;

/**
 * Class representing a state of the game
 * <p>
 * State is represented as a string, cost is the number of moves to get to the state
 */
public class State {
	private int[][] state;
	private int n;
	private int cost;
	private Position indexOfEmpty;
	private State parentState;

	//TODO Problem with 4x4 with State class, number > 9 take two digits, fucks up everything


	/**
	 * Default constructor for State
	 *
	 * @param state The state, as a string
	 * @param n     The size of the square (nxn)
	 * @param cost  The number of moves to get to the state
	 */
	public State (int[][] state, int n, int cost, State parentState) {
		if (state.length != n || state[0].length != n)
			throw new IllegalArgumentException("invalid state, size not compatible");

		this.state = state;
		this.n = n;
		this.cost = cost;
		this.indexOfEmpty = findEmpty(state);
		this.parentState = parentState;
	}

	public State (String state, int n, int cost, State parentState){
		if (state.length() > Math.pow(n,2))
			throw new IllegalArgumentException("invalid state, size not compatible");

		int[][] tab = new int[n][n];

		for (int i = 0; i < state.length(); i++) {
			tab[i / n][i % n] = Integer.valueOf(state.charAt(i));
		}

		this.state = tab;
		this.n = n;
		this.cost = cost;
		this.indexOfEmpty = findEmpty(tab);
		this.parentState = parentState;
	}

	private static Position findEmpty (int[][] tab) {
		for (int i = 0; i < tab.length; i++) {
			for (int j = 0; j < tab[i].length; j++) {
				if (tab[i][j] == 0) {
					return new Position(i, j);
				}
			}
		}
		return null;
	}


	public ArrayList<State> successors () {
		ArrayList<State> successors = new ArrayList<>();

		State temp;

		if ((temp = this.moveLeft()) != null)
			successors.add(temp);

		if ((temp = this.moveUp()) != null)
			successors.add(temp);

		if ((temp = this.moveRight()) != null)
			successors.add(temp);

		if ((temp = this.moveDown()) != null)
			successors.add(temp);


		return successors;
	}

	private State moveLeft () {
		if (this.indexOfEmpty.getJ() == 0)
			return null;

		return swapEmptyWith(new Position(this.indexOfEmpty.getI(), this.indexOfEmpty.getJ() - 1));
	}

	private State moveRight () {
		if (this.indexOfEmpty.getJ() == n - 1)
			return null;

		return swapEmptyWith(new Position(this.indexOfEmpty.getI(), this.indexOfEmpty.getJ() + 1));
	}

	private State moveUp () {
		if (this.indexOfEmpty.getI() == 0)
			return null;

		return swapEmptyWith(new Position(this.indexOfEmpty.getI() - 1, this.indexOfEmpty.getJ()));
	}

	private State moveDown () {
		if (this.indexOfEmpty.getI() == n - 1)
			return null;

		return swapEmptyWith(new Position(this.indexOfEmpty.getI() + 1, this.indexOfEmpty.getJ()));
	}

	@Override
	public boolean equals (Object obj) {
		return this.hashCode() == obj.hashCode();
	}

	private State swapEmptyWith (Position newIndex) {
		int[][] newState = this.state.clone();

		newState[this.indexOfEmpty.getI()][this.indexOfEmpty.getJ()] = newState[newIndex.getI()][newIndex.getJ()];
		newState[newIndex.getI()][newIndex.getJ()] = 0;

		return new State(newState, this.n, this.cost + 1, this);
	}

	public int[][] getState () {
		return state;
	}

	public int getN () {
		return n;
	}

	public int getCost () {
		return cost;
	}

	@Override
	public int hashCode () {
		String str ="";
		for (int i = 0; i < this.state.length; i++) {
			for (int j = 0; j < this.state[i].length; j++) {
				str += String.valueOf(this.state[i][j]);
			}
		}
		return str.hashCode();
	}

	@Override
	public String toString () {
		String str = "";

		for (int i = 0; i < this.state.length; i++) {
			for (int j = 0; j < this.state[i].length; j++) {
				if (this.state[i][j] == 0)
					str += "-";
				else
					str += this.state[i][j];

				if (j != n - 1)
					str += "\t";

			}
			str += "\n";
		}

		return str;
	}

	public State getParentState () {
		return parentState;
	}

	public static State getGoalState (int n) {
		int[][] goal = new int[n][n];

		for (int i = 0; i < Math.pow(n, 2) - 1; i++) {
			goal[i / n][i % n] = i + 1;
		}
		goal[n - 1][n - 1] = 0;

		return new State(goal, n, 0, null);
	}

	public static State getRandomState (int n) {
		int[][] newState = new int[n][n];
		Random rand = new Random();
		ArrayList<Integer> values = new ArrayList<>();

		for (int i = 0; i < Math.pow(n, 2); i++) {
			values.add(i);
		}

		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				int index = rand.nextInt(values.size());
				int temp = values.get(index);
				values.remove(index);
				newState[i][j] = temp;
			}
		}

		return new State(newState, n, 0, null);
	}
}
