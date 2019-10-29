// Course: 			CS4242
// Student Name:	Alex Helfrich
// Student ID:		000740491
// Assignment #:	#2
// Due Date:		9/24/18
// Signature:		
// Score:	

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

//JavaFX activity class controls the GUI only
public class PuzzleActivity extends Application implements EventHandler<ActionEvent>{
	private final Button runApp = new Button();
	
	private final Text initArray = new Text("");
	private final Text finalArray = new Text("");
	private final Text metrics = new Text("");
	
	@Override
	public void handle(ActionEvent event) {
		if(runApp == event.getSource()) {
			boolean pass = false;
			//Create a controller object that handles the A* algorithm and nodes
			Controller controller = null;
			//Display the initial, random state
			/////initArray.setText(controller.startToString());
			//Run the A* algorithm
			while (!pass) {
				controller = new Controller();
				pass = controller.algorithm();
			}

			initArray.setText(controller.startToString());
			//Display the final state, which should be
			// 123
			// 456
			// 780
			finalArray.setText(controller.finalToString());
			metrics.setText(controller.getMetrics());
		}
	}

	@Override
	public void start(Stage stage) throws Exception {
		BorderPane border = new BorderPane();
		Pane center = new Pane();
		
		//Add elements to panes
		center.getChildren().addAll(runApp, finalArray, initArray, metrics);
		
		//editing button titles and setting actions;
		runApp.setText("Run");
		runApp.setOnAction(this);
		
		//editing center pane
		finalArray.setLayoutY(200);
		initArray.setLayoutY(50);
		runApp.setLayoutY(10);
		metrics.setLayoutY(250);
		
		//organizing border pane
		border.setPadding(new Insets(10, 20, 10, 20));
		border.setCenter(center);
		
		//setting scene and stage
		Scene scene = new Scene(border, 1000, 500);
		stage.setTitle("Homework 2");
		stage.setScene(scene);
		stage.show();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
