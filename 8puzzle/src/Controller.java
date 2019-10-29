//Course: 			CS4242
//Student Name:	Alex Helfrich
//Student ID:		000740491
//Assignment #:	#2
//Due Date:		9/24/18
//Signature:		
//Score:	

import java.util.ArrayList;

//Controller class includes the A* algorithm and manipulates PuzzleNode objects
public class Controller {
	private PuzzleNode startNode;
	private PuzzleNode endNode; //Should end up being the same as nodeGoal
	
	public Controller() {
		//Randomly create a new puzzle, set as starting node
		startNode = new PuzzleNode();
		startNode.initRandom();
		//Initialize the endGoal Node
	}
	public String getMetrics() {
		return "Number of Moves: " + endNode.getG();
	}

	public String startToString() {
		String result = "";
		for (int i = 0; i < 3; ++i) {
			for (int j = 0; j < 3; ++j) {
				result = result + startNode.getPuzzleMap()[i][j] + " ";
			}
			result = result + "\n";
		}
		return result;
	}

	public String finalToString() {
		if (endNode == null) {
			return "Error: Could not solve.";
		}
		String result = "";
		for (int i = 0; i < 3; ++i) {
			for (int j = 0; j < 3; ++j) {
				result = result + endNode.getPuzzleMap()[i][j] + " ";
			}
			result = result + "\n";
		}
		return result;
	}
	
	public boolean algorithm() {
		int iterations = 0;
		
		ArrayList<PuzzleNode> openList = new ArrayList<PuzzleNode>();
		openList.add(startNode);
		ArrayList<PuzzleNode> closedList = new ArrayList<PuzzleNode>();
		while(!openList.isEmpty()) {
			++iterations;
			//Find node with lowest f(n)
			//Take lowest off of the open list
			PuzzleNode lowest = openList.get(0);
			boolean remove = true;
			for (int i = 1; i < openList.size(); i++) {
				if(openList.get(i).getF() < lowest.getF()) {
					lowest = openList.get(i);
					openList.remove(i);
					remove = false;
				} 
			}
			if (remove) { openList.remove(0); }
			//Add to the closed list
			closedList.add(lowest);
			System.out.println(openList.size()); // TODO
			//If current node is the goal, set endNode to that and end the method
			if (lowest.isEnd()) {
				endNode = lowest;
				return true;
			}
			if (iterations > 1000) {
				return false;
			}
			//Generate possible successor nodes
			ArrayList<PuzzleNode> successorList = new ArrayList<PuzzleNode>();
			successorList = lowest.generateSuccessors();
			//for each successor of lowest
			for (int i = 0; i < successorList.size(); ++i) {
				//int successor_current_cost = successorList.get(i).getF(); TODO
				//System.out.println(i); //TODO debugging
				//find current successor on the open list
				PuzzleNode current = successorList.get(i);
				System.out.println(current.toString()); // TODO
				System.out.println(current.getG() + " " + current.getF());
				
				
				//check for current successor on open list
				//if node exists on open list, and that node is better, 
				//skip this successor (by deleting from successor list
				boolean addToList = true;
				if (current.getG() > 35) {
					addToList = false;
				}
				if (addToList) {
					for (int j = 0; j < openList.size(); ++j) {
						if (current.isEqual(openList.get(j)) ) {
							System.out.println("on open");
							//having found a node equal...
							if (openList.get(j).getF() < current.getF()) {
								//skip this successor
								addToList = false;
							} else {
								openList.remove(j);
							}
						}
					}
				}
				//else check for current successor on closed list, 
				//assuming it hasn't already been found on the open list
				//and that node has a lower f(n)
				if (addToList) {
					for (int j = 0; j < closedList.size(); ++j) {
						if (current.isEqual(closedList.get(j))) {
							System.out.println("on closed");
							//having found a node equal...
							if (closedList.get(j).getF() < current.getF()) {
								addToList = false;
							} else {
								closedList.remove(j);
							}
						}
					}
				}
				//assuming no nodes were found on the open or closed list with a lower f,
				// add current successor to the open list
				if (addToList) {
					openList.add(current);
				} else { 
					closedList.add(current);
				}
				
				
				/*
				//If successor is on the closed list, ignore
				boolean ignore = false;
				for (int j = 0; j < closedList.size(); ++j) {
					if (current.isEqual(closedList.get(j))) {
						System.out.println("on closed");
						//having found a node equal...
						ignore = true;
					}
				}
				if (!ignore) {
					for (int j = 0; j < openList.size(); ++j) {
						if (current.isEqual(openList.get(j)) ) {
							System.out.println("on open");
							//having found a node equal...
							if (current.getG() > openList.get(j).getG()) {
								ignore = true;
							}
						}
					}
				}
				//if successor isn't on either open or closed list
				if (!ignore) {
					openList.add(current);
				}
				*/
				
			}
			
		}
		return false;
	}
}
