// Course: 			CS4242
// Student Name:	Alex Helfrich
// Student ID:		000740491
// Assignment #:	#3 NQueen
// Due Date:		10/9/18
// Signature:		
// Score:	


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class NQueenBoard {
	private int cost;
	private int size; //n = ?
	private int[] positions;//and array of size n, each item is the queen position
	
	public NQueenBoard(int sizeIn) {
		//Creates initial, randomized board
		size = sizeIn;
		positions = new int[size];
		List<Integer> list = new ArrayList<Integer>();
		for (int i = 0; i < size; i++) {
			list.add(i);
		}
		Collections.shuffle(list);
		for (int i = 0; i < size; i++) {
			positions[i] = list.get(i);
		}
		cost = this.calculateCost();
	}
	
	public NQueenBoard(int[] positionsIn) {
		//Creates specified board
		size = positionsIn.length;
		positions = new int[size];
		for (int i = 0; i < positionsIn.length; ++i) {
			positions[i] = positionsIn[i];
		}
		cost = this.calculateCost();
	}
	
	private int calculateCost() {
		//called in each constructor;
		int c = 0;
		boolean[] attackedQueens = new boolean[size];
		//check columns
		for (int i = 0; i < size; ++i) {
			for (int j = 0; j < size; ++j) {
				if (i != j) {
					if (positions[i] == positions[j]) {
						attackedQueens[i] = true;
					}
				}
			}
		}
		//check diagonals
		for (int i = 0; i < size; ++i) {
			for (int j = 0; j < size; ++j) {
				if (i != j) {
					int deltaRow = Math.abs(i - j);
					int deltaCol = Math.abs(positions[i] - positions[j]);
					if (deltaRow == deltaCol) {
						attackedQueens[i] = true;
					}
				}
			}
		}
		//check how many queens are attacked
		for (int i = 0; i < size; ++i) {
			if (attackedQueens[i]) {
				++c;
			}
		}
		return c;
	}
	
	public int getCost() {
		return cost;
	}
	
	public String toString() {
		String result = "";
		
		for (int i = 0; i < size; ++i) {
			for (int j = 0; j < size; ++j) {
				if (positions[i] == j) {
					result += "Q ";
				} else {
					result += "X ";
				}
				
			}
			result += "\n";
		}
		
		return result;
	}

	public NQueenBoard generateNeighbor() {
		//Generates a random neighbor
		//Select random row:
		Random rand = new Random();
		int randRow = rand.nextInt(size);
		int randColumn = rand.nextInt(size);
		int [] returnArray = new int[size];
		for (int i = 0; i < size; ++i) {
			returnArray[i] = this.positions[i];
		}
		returnArray[randRow] = randColumn;
		NQueenBoard neighbor = new NQueenBoard(returnArray);
		return neighbor;
	}
}
