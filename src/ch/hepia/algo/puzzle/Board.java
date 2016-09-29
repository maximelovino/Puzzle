package ch.hepia.algo.puzzle;

/**
 * Created by maximelovino on 29.09.16.
 */
public class Board {
	private int[][] board;
	private int size;

	public Board (int[][] board, int size) {
		this.board = board;
		this.size = size;
	}

	@Override
	public int hashCode () {
		String hash = "";

		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[i].length; j++) {
				hash += board[i][j];
			}
		}

		return Integer.valueOf(hash);
	}

	public int state(){
		String state = "";

		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[i].length; j++) {
				state += board[i][j];
			}
		}

		return Integer.valueOf(state);
	}

	@Override
	public String toString () {
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[i].length; j++) {
				System.out.print(board[i][j]+"\t");
			}
			System.out.println();
		}
	}

	public int[][] getBoard () {
		return board;
	}

	public void setBoard (int[][] board) {
		this.board = board;
	}

	public int getSize () {
		return size;
	}

	public void setSize (int size) {
		this.size = size;
	}
}
