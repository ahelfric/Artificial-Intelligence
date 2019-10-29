// Course: 			CS4242
// Student Name:	Alex Helfrich
// Student ID:		000740491
// Assignment #:	#4 NQueen
// Due Date:		10/22/18
// Signature:		
// Score:	

import java.util.ArrayList;

public class NQueenController {
	NQueenBoard initArray, solution;
	ArrayList<NQueenBoard> populationArray;
	double probability, randomProbability;
	int generations = 0;
	int population, n;
	
	public NQueenController(int size) {
		//initArray = new NQueenBoard(size);
		n = size;
		population = n*n;
		populationArray = new ArrayList<NQueenBoard>();
	}
	
	public NQueenBoard algorithm() {
		generateInitial(); //population array with random boards
		//Check for solution
		if (checkSolutions()) {
			return solution;
		}
		while (generations < population*1000000) {
			//Mutate all boards (start of loop)
			mutate();
			//Check for solution
			if (checkSolutions()) {
				return solution;
			}
			//Select best half of the boards, regenerate the others
			cull();
			//(end of loop)
			++generations;
		}
		return null; // this means the method has failed TODO should make it restart
	}
		

	private void cull() {
		//sort all items in the array in order from best to worst
		ArrayList<NQueenBoard> temp = new ArrayList<NQueenBoard>();
		temp.add(populationArray.get(0));
		for (int i = 1; i < population; ++i) {
			for (int j = 0; j < temp.size(); ++j) {
				if (populationArray.get(i).getFitness() > temp.get(j).getFitness()) {
					temp.add(j, populationArray.get(i));
					break;
				}
			}
			if (populationArray.get(i).getFitness() <= temp.get(temp.size()-1).getFitness()) {
				temp.add(populationArray.get(i));
			}
		}
		//replace the worst half with newly generated random boards
		for (int i = temp.size()/2; i < temp.size(); ++i) {
			temp.set(i, new NQueenBoard(n));
		}
		populationArray = temp;
	}

	private void mutate() {
		//for each item in the array, mutate by changing a queen's position
		for (int i = 0; i < population; ++i) {
			populationArray.set(i, populationArray.get(i).generateNeighbor());
		}
	}

	private boolean checkSolutions() {
		//Check all items in array for correctness
		//if solution is found set solution=checkSolutions.get(i), return true
		for (int i = 0; i < population; ++i) {
			if (populationArray.get(i).getFitness() == 0) {
				solution = populationArray.get(i);
				return true;
			}
		}
		return false;
	}

	private void generateInitial() {
		for (int i = 0; i < population; ++i) {
			populationArray.add(new NQueenBoard(n));
		}
	}

	public String getInitArray() {
		return initArray.toString();
	}
	
	public String getSolution() {
		return solution.toString();
	}
	
	public int getGenerations() {
		return generations;
	}
}