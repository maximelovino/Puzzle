package ch.hepia.algo.puzzle.utils;

/**
 * Simple class to implement a Position tuple
 */
public class Position {
	private int i;
	private int j;

	/**
	 * Main constructor for Position
	 *
	 * @param i A i coordinate
	 * @param j A j coordinate
	 */
	public Position (int i, int j) {
		this.i = i;
		this.j = j;
	}

	/**
	 *
	 * @return i    The i coordinate
	 */
	public int getI () {
		return i;
	}

	/**
	 *
	 * @return j    The j coordinate
	 */
	public int getJ () {
		return j;
	}
}
