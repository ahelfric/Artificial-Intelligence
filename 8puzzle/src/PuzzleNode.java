//Course: 			CS4242
//Student Name:	Alex Helfrich
//Student ID:		000740491
//Assignment #:	#2
//Due Date:		9/24/18
//Signature:		
//Score:	

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PuzzleNode {
	private int[][] puzzleMap;
	//follows the format [x][y] with x rows and y columns
	//so that the top right is [0][2], bottom left is [2][0], top left is [0][0], etc.
	private int xIndex;
	private int yIndex;
	private int f; //f(n)
	private int g; //g(n)
	private PuzzleNode predecessor;
	
	public PuzzleNode() {
		//Creates an empty map - only used for creating the random starting node
		puzzleMap = new int[3][3];
		g = 0;
		f = 0;
		xIndex = 2;
		yIndex = 2;
		predecessor = null;
	}

	public PuzzleNode(int i, int j, int k, int l, int m, int n, int o, int p, int q, int x, int y, 
						PuzzleNode predecessorIn) {
		//Creates a specific map as a node (i.e. endGoal)
		puzzleMap = new int[3][3];
		//row 0
		puzzleMap[0][0] = i;
		puzzleMap[0][1] = j;
		puzzleMap[0][2] = k;
		
		//row 1
		puzzleMap[1][0] = l;
		puzzleMap[1][1] = m;
		puzzleMap[1][2] = n;
		
		//row 2
		puzzleMap[2][0] = o;
		puzzleMap[2][1] = p;
		puzzleMap[2][2] = q;
		
		//Set where 0, the 'empty' tile, is located
		xIndex = x;
		yIndex = y;
		
		predecessor = predecessorIn;
		
		g = predecessor.getG() + 1;
		
		f = calculateF();
	}

	public void initRandom() {
		//Randomly generates a puzzle layout for the starting node
		List<Integer> list = new ArrayList<Integer>();
		for (int i = 0; i < 9; ++i) {
			list.add(i);
		}
		Collections.shuffle(list);
		List<Integer> check = new ArrayList<Integer>();
		check.addAll(list);
		if (solvable(check)) {
			int index = 0;
			for (int i = 0; i < 3; ++i) {
				for (int j = 0; j < 3; ++j) {
					puzzleMap[i][j] = list.get(index);
					if (list.get(index) == 0) {
						xIndex = i;
						yIndex = j;
					}
					++index;
				}
			}
		} else {System.out.println("Unsolvable, starting over");this.initRandom();} //TODO
	}
	
	private boolean solvable(List<Integer> list) {
		int inversions = 0;
		//remove the 0 from the list for accuracy
		for (int i = 0; i < list.size(); ++i) {
			if (list.get(i) == 0) {
				list.remove(i);
			}
		}
		
		for (int i = 0; i < list.size(); ++i) {
			for (int j = i + 1; j < list.size(); ++j) {
				if (list.get(j)>list.get(i) ) {
					++inversions;
				}
			}
		}
		
		//8 puzzles with an odd number of inversions are impossible
		if (inversions%2 == 1) {
			System.out.println(inversions); // TODO
			return false;
		}
		return true;
	}

	public int calculateF() {
		//Calculates f(n) = g(n) + h(n)
		int result = 0;
		//Calculate Manhattan distance (h(n))
		//for every spot on the grid
		for (int i = 0; i < 3; ++i) {
			for (int j = 0; j < 3; ++j) {
				switch (puzzleMap[i][j]) {
				case 1: result += (Math.abs(0-i) + Math.abs(0-j)); break;
				case 2: result += (Math.abs(0-i) + Math.abs(1-j)); break;
				case 3: result += (Math.abs(0-i) + Math.abs(2-j)); break;
				case 4: result += (Math.abs(1-i) + Math.abs(0-j)); break;
				case 5: result += (Math.abs(1-i) + Math.abs(1-j)); break;
				case 6: result += (Math.abs(1-i) + Math.abs(2-j)); break;
				case 7: result += (Math.abs(2-i) + Math.abs(0-j)); break;
				case 8: result += (Math.abs(2-i) + Math.abs(1-j)); break;
				case 0: result += (Math.abs(2-i) + Math.abs(2-j)); break;
				}
			}
		}
		
		return result + g;
	}

	public boolean isEnd() {
		if (this.puzzleMap[0][0] == 1 && 
				this.puzzleMap[0][1] == 2 &&
				this.puzzleMap[0][2] == 3 &&
				this.puzzleMap[1][0] == 4 &&
				this.puzzleMap[1][1] == 5 &&
				this.puzzleMap[1][2] == 6 &&
				this.puzzleMap[2][0] == 7 &&
				this.puzzleMap[2][1] == 8 &&
				this.puzzleMap[2][2] == 0) {
			return true;
		}
		return false;
	}

	public ArrayList<PuzzleNode> generateSuccessors() {
		ArrayList<Integer> directions = new ArrayList<Integer>();
		ArrayList<PuzzleNode> successorList = new ArrayList<PuzzleNode>();
		// Use the index of the empty tile to determine what directions it can move
		// Where 0 is north, 1 is east, 2 is south, and 3 is west
		// Put the possible directions in the directions arrayList
		switch (yIndex) {
		case 0: directions.add(1); break;
		case 1: directions.add(1); directions.add(3); break;
		case 2: directions.add(3); break;
		}
		
		switch (xIndex) {
		case 0: directions.add(2); break;
		case 1: directions.add(2); directions.add(0); break;
		case 2: directions.add(0); break;
		}
		
		//For each direction, generate a successor and add it to the list
		for (int i = 0; i < directions.size(); ++i) {
			//create temporary array
			int[][] temp = new int[3][3];
			//add all elements of the current node into array
			for (int j = 0; j < 3; ++j) {
				for (int k = 0; k < 3; ++k) {
					temp[j][k] = puzzleMap[j][k];
				}
			}
			//Create node based on movement, add to list
			if (directions.get(i) == 1) {
				temp[xIndex][yIndex] = temp [xIndex][yIndex + 1];
				temp [xIndex][yIndex + 1] = 0;
				successorList.add(new PuzzleNode(temp[0][0], temp[0][1], temp[0][2], 
						temp[1][0], temp[1][1], temp[1][2], 
						temp[2][0], temp[2][1], temp[2][2], xIndex, yIndex+1, this));
			}
			if (directions.get(i) == 2) {
				temp[xIndex][yIndex] = temp [xIndex + 1][yIndex];
				temp [xIndex + 1][yIndex] = 0;
				successorList.add(new PuzzleNode(temp[0][0], temp[0][1], temp[0][2], 
						temp[1][0], temp[1][1], temp[1][2], 
						temp[2][0], temp[2][1], temp[2][2], xIndex + 1, yIndex, this));
			}
			if (directions.get(i) == 3) {
				temp[xIndex][yIndex] = temp [xIndex][yIndex - 1];
				temp [xIndex][yIndex - 1] = 0;
				successorList.add(new PuzzleNode(temp[0][0], temp[0][1], temp[0][2], 
						temp[1][0], temp[1][1], temp[1][2], 
						temp[2][0], temp[2][1], temp[2][2], xIndex, yIndex - 1, this));
			}
			if (directions.get(i) == 0) {
				temp[xIndex][yIndex] = temp [xIndex - 1][yIndex];
				temp [xIndex - 1][yIndex] = 0;
				successorList.add(new PuzzleNode(temp[0][0], temp[0][1], temp[0][2], 
						temp[1][0], temp[1][1], temp[1][2], 
						temp[2][0], temp[2][1], temp[2][2], xIndex - 1, yIndex, this));
			}
		}
		return successorList;
	}


	public boolean isEqual(PuzzleNode puzzleNode) {
		//Compare two nodes, return true if equal, false otherwise
		if (puzzleMap[0][0] == puzzleNode.getPuzzleMap()[0][0] &&
				puzzleMap[0][1] == puzzleNode.getPuzzleMap()[0][1] &&
				puzzleMap[0][2] == puzzleNode.getPuzzleMap()[0][2] &&
				puzzleMap[1][0] == puzzleNode.getPuzzleMap()[1][0] &&
				puzzleMap[1][1] == puzzleNode.getPuzzleMap()[1][1] &&
				puzzleMap[1][2] == puzzleNode.getPuzzleMap()[1][2] &&
				puzzleMap[2][0] == puzzleNode.getPuzzleMap()[2][0] &&
				puzzleMap[2][1] == puzzleNode.getPuzzleMap()[2][1] &&
				puzzleMap[2][2] == puzzleNode.getPuzzleMap()[2][2]) {
			return true;
		}
		return false;
	}

	public int getF() {
		//Returns the f(n) of the node 
		return f;
	}

	public int getG() {
		return g;
	}

	public int[][] getPuzzleMap() {
		return puzzleMap;
	}
	
	public String toString() {
		String result = "";
		for (int i = 0; i < 3; ++i) {
			for (int j = 0; j < 3; ++j) {
				result = result + puzzleMap[i][j];
			}
			result = result + "\n";
		}
		
		return result;
	}
}
