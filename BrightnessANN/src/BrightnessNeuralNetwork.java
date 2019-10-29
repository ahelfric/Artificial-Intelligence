import java.util.Random;

public class BrightnessNeuralNetwork {
	//three layers: input, hidden, and output
	//4 inputs
	//2 hidden layer nodes
	//1 output
	
	private int iteration;
	private int[][] inputs; //should either be 0 or 1, for dark or light
	private double[] w1, w2, hiddenOutputs, v1;
	//where w1 is an array of each weight between the inputs and hidden node 1
	//where w2 is an array of each weight between the inputs and hidden node 2
	//v1 is an array of each weight between the hidden nodes and the output
	private double output, tk, learningRate;	
	
	public BrightnessNeuralNetwork(int[][] trainingSetIn, double learningIn) {
		inputs = trainingSetIn; //training set
		//set arbitrary weights between -0.5 and 0.5 TODO
		iteration = 0;
		w1 = new double[4];
		w2 = new double[4];
		v1 = new double[2];
		for (int i = 0; i < 4; ++i) {
			Random r = new Random();
			w1[i] = -0.5 + (0.5 - (-0.5)) * r.nextDouble();
			w2[i] = -0.5 + (0.5 - (-0.5)) * r.nextDouble();
			//System.out.printf(w1[i] + ", ");
			//System.out.printf(w2[i] + ", ");
		}
		Random r = new Random();
		v1[0] =  -0.5 + (0.5 - (-0.5)) * r.nextDouble();
		v1[1] =  -0.5 + (0.5 - (-0.5)) * r.nextDouble();
		//initialize hiddenOutputs to 0.0 for both outputs
		hiddenOutputs = new double[2];
		hiddenOutputs[0] = 0.0;
		hiddenOutputs[1] = 0.0;

		learningRate = learningIn;
	}
	
	public void train() {
		
		for (int i = 0; i < 12; ++i) {
			String empty = calculateOutput(inputs[i]);
			
			//determine what the training output should be (tk)
			int train = 0;
			for (int j = 0; j < 4; ++j) {
				train += inputs[i][j];
			}
			if (train > 1) { tk = 1; } else { tk = 0; }
			
			System.out.println("tk= " + tk);//TODO
			
			//calculate error term for output unit 
			//Ok(E)(1-Ok(E))(Tk(E) - Ok(E))
			double outputErrorTerm = output * (1 - output) * (tk - output);
		
			//calculate error term for hidden unit
			double h1ErrorTerm = hiddenOutputs[0] * (1 - hiddenOutputs[0]) * (v1[0] * outputErrorTerm);
			double h2ErrorTerm = hiddenOutputs[1] * (1 - hiddenOutputs[1]) * (v1[1] * outputErrorTerm);
			
			//System.out.println(" " + h1ErrorTerm + ", " + h2ErrorTerm); TODO
			
			//calculate input-hidden weights
			for (int j = 0; j < 4; ++j) {
				w1[j] += (learningRate * h1ErrorTerm * inputs[i][j]);
				w2[j] += (learningRate * h2ErrorTerm * inputs[i][j]);
				//System.out.println(w1[j] + " " + w2[j]); 	TODO
			}
			
			//calculate hidden-output weights
			v1[0] += learningRate * outputErrorTerm * hiddenOutputs[0];
			v1[1] += learningRate * outputErrorTerm * hiddenOutputs[1];
		}
		
		++iteration;
		System.out.println("Iteration: " + iteration);
		
	}
	
	public String calculateOutput(int[] input) {
		String out = "";
		
		//calculate hidden outputs
		System.out.println();
		for (int i = 0; i < input.length; ++i) {
			hiddenOutputs[0] += input[i] * w1[i];
			System.out.print(input[i]);
			out = out + input[i];
		}
		hiddenOutputs[0] = 1.0/(1.0 + Math.exp(-1.0 * hiddenOutputs[0]));
		for (int i = 0; i < input.length; ++i) {
			hiddenOutputs[1] += input[i] * w2[i];
		}
		hiddenOutputs[1] = 1.0/(1.0 + Math.exp(-1.0 * hiddenOutputs[1]));
		
		//calculate output
		output = hiddenOutputs[0] * v1[0] + hiddenOutputs[1] * v1[1];
		output = 1.0/(1.0 + Math.exp(-1 * output));
		
		//return output;
		
		System.out.printf("\nObserved output: " + output + "\n"); //TODO
		out = out + "\nObserved output: " + output + "\n\n";
		return out;
	}
	
	public int getIteration() {
		return iteration;
	}
}
