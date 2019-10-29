// Course: 			CS4242
// Student Name:	Alex Helfrich
// Student ID:		000740491
// Assignment #:	#4 NQueen
// Due Date:		10/22/18
// Signature:		
// Score:	

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class NQueenActivity extends Application implements EventHandler<ActionEvent>{
	private final Button runApp = new Button();
	
	//private final Text initArray = new Text("");
	private final Text finalArray = new Text("");
	private final Text iterations = new Text("");
	private final TextField enterN = new TextField("");
	
	private int n;
	
	@Override
	public void handle(ActionEvent event) {
		if (runApp == event.getSource()) {
			try {
				n = Integer.parseInt(enterN.getText());
			} catch (NumberFormatException e) {
				//
			}
			NQueenController controller = new NQueenController(n);
			//initArray.setText(controller.getInitArray());
			controller.algorithm();
			finalArray.setText(controller.getSolution());
			iterations.setText("Generations: " + controller.getGenerations());
		}
		
	}

	@Override
	public void start(Stage stage) throws Exception {
		BorderPane border = new BorderPane();
		Pane center = new Pane();
		
		//Add elements to panes
		center.getChildren().addAll(runApp, finalArray, enterN, iterations);
		
		//editing button titles and setting actions;
		runApp.setText("Run");
		runApp.setOnAction(this);
		
		//editing center pane
		finalArray.setLayoutY(50);
		runApp.setLayoutY(10);
		iterations.setLayoutY(30);
		iterations.setLayoutX(200);
		enterN.setLayoutX(50);
		enterN.setLayoutY(10);
		
		//organizing border pane
		border.setPadding(new Insets(10, 20, 10, 20));
		border.setCenter(center);
		
		//setting scene and stage
		Scene scene = new Scene(border, 500, 500);
		stage.setTitle("Homework 4");
		stage.setScene(scene);
		stage.show();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
