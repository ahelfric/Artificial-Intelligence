// Course: 			CS4242
// Student Name:	Alex Helfrich
// Student ID:		000740491
// Assignment #:	#1
// Due Date:		9/10/18
// Signature:		
// Score:			



//This class includes the algorithm for the Vacuum AI
//A vacuum object knows nothing about the environment it is in
//It will attempt to find the northwest corner first, then move down from there
//The class also handles outputting performance metrics to a file, vacuumMetrics.text
public class Vacuum {
	private boolean right;
	private int startLocationX;
	private int startLocationY;
	private int locationX;
	private int locationY;
	private Floor floor;
	private int moves = 0;
	private String moveset = "";
	
	public Vacuum(Floor floorIn) {
		floor = floorIn;
		
		startLocationX = floor.getLocationX();
		startLocationY = floor.getLocationY();
		locationX = floor.getLocationX();
		locationY = floor.getLocationY();
	}
	
	public void algorithm() {
		checkFloor();
		
		//move all the way to the northwest corner, cleaning along the way
		while (checkWest()) {
			moveWest();
			checkFloor();
		}
		while (checkNorth()) {
			moveNorth();
			checkFloor();
		}
		
		// We know we must move right, so set out direction as that
		right = true;
		//Move either left or right, on any row that isn't the bottom row
		while (checkSouth()) {
			// once we find the end of the row, move down, and change directions
			if (!checkSideways()) {
				moveSouth();
				checkFloor();
				right = !right;
			} else {
				moveSideways();
				checkFloor();
			}
		}
		//On the bottom row, move all the way until the end. 
		while (checkSideways()) {
			moveSideways();
			checkFloor();
		}
	}

	private void moveSideways() {
		if (right) {
			++locationX;
			moveset = moveset + "E";
		} else {
			--locationX;
			moveset = moveset + "W";
		}
		++moves;
	}

	private void moveSouth() {
		++locationY;
		++moves;
		moveset = moveset + "S";
	}

	private void moveNorth() {
		--locationY;
		++moves;
		moveset = moveset + "N";
	}

	private void moveWest() {
		--locationX;
		++moves;
		moveset = moveset + "W";
	}

	private boolean checkSideways() {
		if (right) {
			if ((locationX + 1) == floor.getLength()) {
				return false;
			} else return true;
		} else {
			if (locationX == 0) {
				return false;
			} else return true;
		}
	}

	private boolean checkSouth() {
		if ((locationY + 1) == floor.getWidth()) {
			return false;
		}
		return true;
	}

	private boolean checkNorth() {
		if (locationY == 0) return false;
		return true;
	}

	private boolean checkWest() {
		if (locationX == 0) return false;
		return true;
	}

	//Check if there's dirt, clean if so
	private void checkFloor() {
		if (floor.floorMap[locationX][locationY] != 0) {
			floor.floorMap[locationX][locationY] = 0;
			++moves;
			moveset = moveset + "C";
		}
	}
	
	//To string methods
	public String startFloorToString() {
		String result = "";
		for (int i = 0; i < floor.getWidth(); ++i) {
			for (int j = 0; j < floor.getLength(); ++j) {
				result = result + (floor.floorMap[j][i] + " ");
			}
			result = result + ("\n");
		}
		return result;
	}

	public String finalFloorToString() {
		String result = "";
		for (int i = 0; i < floor.getWidth(); ++i) {
			for (int j = 0; j < floor.getLength(); ++j) {
				result = result + (floor.floorMap[j][i] + " ");
			}
			result = result + ("\n");
		}
		return result;
	}

	public String metricsToString() {
		String result = "";
		result = result + ("Start Location: X=" + startLocationX + " Y=" + startLocationY + "\n");
		result = result + ("# of moves: " + moves + ". Final Position: X=" + locationX + " Y=" + locationY);
		result = result + ("\n" + moveset);
		return result;
	}
	
	//TESTING **********************************
		public void printStartFloor() {
			try {
			PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(
					"initialArray.txt")));
			for (int i = 0; i < floor.getWidth(); ++i) {
				for (int j = 0; j < floor.getLength(); ++j) {
					out.print(floor.floorMap[j][i] + " ");
				}
				out.print("\n");
			}
			out.close();
			} catch (Exception e) {}
		}
		
		public void printFloor() {
			try {
			PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(
					"finalArray.txt")));
			for (int i = 0; i < floor.getWidth(); ++i) {
				for (int j = 0; j < floor.getLength(); ++j) {
					out.print(floor.floorMap[j][i] + " ");
				}
				out.print("\n");
			}
			out.close();
			} catch (Exception e) { e.printStackTrace();}
		}
		
		public void printMetrics() {
			try {
				PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(
						"vacuumMetrics.txt")));
				out.println("Start Location: X=" + startLocationX + " Y=" + startLocationY + "\n");
				out.println("# of moves: " + moves + ". Final Position: X=" + locationX + " Y=" + locationY);
				out.println("\n" + moveset);
				out.close();
				} catch (Exception e) {}
		}
}
