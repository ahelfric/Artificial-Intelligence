import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class BrightnessActivity extends Application implements EventHandler<ActionEvent>{
	private final Button runApp = new Button();
	private final Button testNet = new Button();
	
	private Text outputText = new Text();
	
	private int[][] trainingSet = new int[12][4];
	private int[][] testingSet = new int[4][4];
	
	private BrightnessNeuralNetwork ann;
	
	@Override
	public void handle(ActionEvent event) {
		if (runApp == event.getSource()) {
			for (int i = 0; i < 1500; ++i) {
				ann.train();
			}
		}
		if (testNet == event.getSource()) {
			String temp = "";
			for (int i = 0; i < 4; ++i) {
				temp = temp + ann.calculateOutput(testingSet[i]);
			}
			outputText.setText(temp);
		}
	}

	@Override
	public void start(Stage stage) throws Exception {
		BorderPane border = new BorderPane();
	    Pane center = new Pane();
	    
	    //create testing set
	    trainingSet[0][0] = 1;
	    trainingSet[0][1] = 0;
	    trainingSet[0][2] = 0;
	    trainingSet[0][3] = 1;
	    
	    trainingSet[1][0] = 0;
	    trainingSet[1][1] = 1;
	    trainingSet[1][2] = 0;
	    trainingSet[1][3] = 0;

	    trainingSet[2][0] = 1;
	    trainingSet[2][1] = 1;
	    trainingSet[2][2] = 0;
	    trainingSet[2][3] = 1;
	    
	    trainingSet[3][0] = 0;
	    trainingSet[3][1] = 0;
	    trainingSet[3][2] = 1;
	    trainingSet[3][3] = 0;
	    
	    trainingSet[4][0] = 0;
	    trainingSet[4][1] = 1;
	    trainingSet[4][2] = 1;
	    trainingSet[4][3] = 0;
	    
	    trainingSet[5][0] = 0;
	    trainingSet[5][1] = 0;
	    trainingSet[5][2] = 1;
	    trainingSet[5][3] = 1;
	    
	    trainingSet[6][0] = 0;
	    trainingSet[6][1] = 1;
	    trainingSet[6][2] = 0;
	    trainingSet[6][3] = 1;
	    
	    trainingSet[7][0] = 1;
	    trainingSet[7][1] = 1;
	    trainingSet[7][2] = 1;
	    trainingSet[7][3] = 0;
	    
	    trainingSet[8][0] = 0;
	    trainingSet[8][1] = 0;
	    trainingSet[8][2] = 0;
	    trainingSet[8][3] = 1;
	    
	    trainingSet[9][0] = 1;
	    trainingSet[9][1] = 0;
	    trainingSet[9][2] = 1;
	    trainingSet[9][3] = 0;
	    
	    trainingSet[10][0] = 1;
	    trainingSet[10][1] = 0;
	    trainingSet[10][2] = 1;
	    trainingSet[10][3] = 1;
	    
	    trainingSet[11][0] = 1;
	    trainingSet[11][1] = 0;
	    trainingSet[11][2] = 0;
	    trainingSet[11][3] = 0;

	    //create testing set
	    testingSet[0][0] = 1;
	    testingSet[0][1] = 1;
	    testingSet[0][2] = 1;
	    testingSet[0][3] = 1;
	    
	    testingSet[1][0] = 0;
	    testingSet[1][1] = 1;
	    testingSet[1][2] = 1;
	    testingSet[1][3] = 1;
	    
	    testingSet[2][0] = 1;
	    testingSet[2][1] = 1;
	    testingSet[2][2] = 0;
	    testingSet[2][3] = 0;
	    
	    testingSet[3][0] = 0;
	    testingSet[3][1] = 0;
	    testingSet[3][2] = 0;
	    testingSet[3][3] = 0;
	    
	    
	    
	    //Create initial ANN
		ann = new BrightnessNeuralNetwork(trainingSet, 0.6);
	    
	    //Add elements to panes
	    center.getChildren().addAll(runApp, testNet, outputText);
	    
	  	runApp.setText("Train");
	  	runApp.setOnAction(this);
	  	testNet.setText("Test");
	  	testNet.setOnAction(this);
	  	
	  	testNet.setLayoutY(50);
	  	outputText.setLayoutY(100);
	  	
	  	//organizing border pane
	  	border.setPadding(new Insets(10, 20, 10, 20));
	  	border.setCenter(center);
	  		
	  	//setting scene and stage
	  	Scene scene = new Scene(border, 600, 600);
	  	stage.setTitle("Brightness NN");
	  	stage.setScene(scene);
		stage.show();
	}
	
	public static void main(String args[]) {
		launch(args);
	}
}
