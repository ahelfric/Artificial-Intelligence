// Course: 			CS4242
// Student Name:	Alex Helfrich
// Student ID:		000740491
// Assignment #:	#4 Traveling Salesman Genetic Algorithm
// Due Date:		10/9/18
// Signature:		
// Score:	

public class City {
	private int x, y;
	
	public City() {
		x = (int) (Math.random() * 500);
		y = (int) (Math.random() * 500);
	}
	
	public double distanceToCity(City city) {
		int x = Math.abs(getX() - city.getX());
		int y = Math.abs(getY() - city.getY());
		
		return Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
	}

	public int getY() {
		return y;
	}

	public int getX() {
		return x;
	}
}
