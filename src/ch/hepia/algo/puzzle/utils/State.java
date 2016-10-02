package ch.hepia.algo.puzzle.utils;

import java.util.ArrayList;

public class State {
	private String state;
	private int n;
	private int indexOfEmpty;
	private int cost;

	public State (String state, int n, int cost) {
		if (state.length()!= Math.pow(n,2))
			throw new IllegalArgumentException("invalid state, size not compatible");

		this.state = state;
		this.n = n;
		this.cost = cost;
		this.indexOfEmpty = this.state.indexOf('0');
	}

	public ArrayList<State> successors(){
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

	private State moveLeft(){
		if (this.indexOfEmpty % n ==0)
			return null;

		return swapEmptyWith(indexOfEmpty-1);
	}

	private State moveRight(){
		if(this.indexOfEmpty % n == n-1)
			return null;

		return swapEmptyWith(indexOfEmpty+1);
	}

	private State moveUp(){
		if (this.indexOfEmpty-n < 0)
			return null;

		return swapEmptyWith(indexOfEmpty-n);
	}

	private State moveDown(){
		if (this.indexOfEmpty+n > this.state.length()-1)
			return null;

		return swapEmptyWith(indexOfEmpty+n);
	}

	private State swapEmptyWith(int newIndex){
		char toSwapWith = this.state.charAt(newIndex);

		String s1 = this.state.substring(0,Math.min(newIndex,this.indexOfEmpty));
		String s2 = this.state.substring(Math.min(newIndex,this.indexOfEmpty)+1,Math.max(newIndex,this.indexOfEmpty));
		String s3 = this.state.substring(Math.max(newIndex,this.indexOfEmpty)+1);

		String newString;

		if (this.indexOfEmpty<newIndex){
			newString = s1+toSwapWith+s2+'0'+s3;
		}else{
			newString = s1+'0'+s2+toSwapWith+s3;
		}

		return new State(newString,this.n, this.cost+1);
	}

	public String getState () {
		return state;
	}

	public int getN () {
		return n;
	}

	public int getCost () {
		return cost;
	}

	public int getIndexOfEmpty () {
		return indexOfEmpty;
	}

	@Override
	public int hashCode () {
		return this.state.hashCode();
	}

	@Override
	public String toString () {
		String str = "";

		for (int i = 0; i < this.state.length(); i++) {
			if (i % n == 0 && i != 0)
				str += "\n";

			str = this.state.charAt(i) == '0' ? str + "-" : str + this.state.charAt(i);

			if (i != this.state.length()-1)
				str += "\t";
		}

		return str;
	}
}
