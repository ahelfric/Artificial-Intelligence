

// Course: 			CS4242
// Student Name:	Alex Helfrich
// Student ID:		000740491
// Assignment #:	#1
// Due Date:		9/10/18
// Signature:		
// Score:			

//This class holds the information for a floor object
//i.e. dirt placement, floor array size (length, width)
public class Floor {
	public int[][] floorMap;
	private int length;
	private int width;
	private int locationX;
	private int locationY;
	
	public Floor(int lengthIn, int widthIn) {
		length = lengthIn;
		width = widthIn;
		floorMap = new int[length][width];
		initDirt();
		
		locationX = initLocationX();
		locationY = initLocationY();
	}
	
	private void initDirt() {
		//Generate a random amount of dirt
		Random rand = new Random();
		for (int i = 0; i < length; ++i) {
			for (int j = 0; j < width; ++j) {
				floorMap[i][j] = rand.nextInt(2);
			}
		}
	}
	
	private int initLocationY() {
		Random rand = new Random();
		return rand.nextInt(width-1);
	}

	private int initLocationX() {
		Random rand = new Random();
		return rand.nextInt(length-1);
	}

	public int getLength() {
		return length;
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getLocationX() {
		return locationX;
	}
	
	public int getLocationY() {
		return locationY;
	}
}
