// Course: 			CS4242
// Student Name:	Alex Helfrich
// Student ID:		000740491
// Assignment #:	#3 NQueen
// Due Date:		10/9/18
// Signature:		
// Score:	

public class NQueenController {
	NQueenBoard initArray, solution;
	double temperature, probability, randomProbability;
	int iterations = 0;
	int delta, n;
	
	public NQueenController(int size) {
		initArray = new NQueenBoard(size);
		n = size;
	}
	
	public void algorithm() {
		//initialize board, set as current
		solution = initArray;
		//let t=100 (or other)
		double t = ((n*1.0) / 2.0);
		temperature = t;
		//return if cost == 0;
		if (solution.getCost() == 0) {
			return;
		}
		//(while t!=0)
		while (temperature > 0.0) {
			//generate random neighbor
			NQueenBoard neighbor = solution.generateNeighbor();
			delta = solution.getCost() - neighbor.getCost();
			//TODO
			if (neighbor.getCost() < solution.getCost()) {
				solution = neighbor;
				++iterations;
				if (solution.getCost() == 0) {
					return;
				}
			} else {
				probability = Math.exp((delta*1.0)/(temperature*1.0)); //delta is expected to be negative
				randomProbability = Math.random();
				if (probability >= randomProbability) {
					solution = neighbor;
					++iterations;
				}
			}
			temperature = (t/(iterations*1.0));
		}
	}

	public String getInitArray() {
		return initArray.toString();
	}
	
	public String getSolution() {
		return solution.toString();
	}
	
	public int getIterations() {
		return iterations;
	}
}
