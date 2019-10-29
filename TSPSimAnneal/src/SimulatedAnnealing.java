// Course: 			CS4242
// Student Name:	Alex Helfrich
// Student ID:		000740491
// Assignment #:	#3 TSP Simulated Annealing
// Due Date:		10/9/18
// Signature:		
// Score:	

import java.util.ArrayList;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class SimulatedAnnealing extends Application implements EventHandler<ActionEvent>{
	private final Button runApp = new Button(); 
    private Text initialDistance = new Text();
    private Text finalDistance = new Text();
    
    private Travel travel = new Travel(10);//10 CITIES
    private ArrayList<Circle> cityList = new ArrayList<>();
    private ArrayList<Line> lineList = new ArrayList<>();
    private Travel currentSolution;

    public double simulatedAnnealing(double tempIn, int numberOfIterations, double coolingRate) {
        double t = tempIn;
        travel.generateInitialTravel();
        double bestDistance = travel.getDistance();
        Travel bestSolution = travel;
        currentSolution = bestSolution;

        for (int i = 0; i < numberOfIterations; i++) {
            if (t > 0.1) {
                currentSolution.swapCities();
                double currentDistance = currentSolution.getDistance();
                if (currentDistance < bestDistance) {
                    bestDistance = currentDistance;
                } else if (Math.exp((bestDistance - currentDistance) / t) < Math.random()) {
                    currentSolution.revertSwap();
                }
                t *= coolingRate;
            } else {
                continue;
            }
        }
        return bestDistance;
    }
    

	@Override
	public void handle(ActionEvent event) {
		if (runApp == event.getSource()) {
			finalDistance.setText("Final Distance: " + simulatedAnnealing(300, 100000, .7));
			for (int i = 0; i < 9; ++i) {
				lineList.get(i).setStartX(currentSolution.getCity(i).getX());
				lineList.get(i).setStartY(currentSolution.getCity(i).getY());
				lineList.get(i).setEndX(currentSolution.getCity(i+1).getX());
				lineList.get(i).setEndY(currentSolution.getCity(i+1).getY());
			}
		}
	}

	@Override
	public void start(Stage stage) throws Exception {
		BorderPane border = new BorderPane();
	    Pane center = new Pane();
	    
		for (int i = 0; i < 10; ++i) {
			cityList.add(new Circle(travel.getCity(i).getX(), travel.getCity(i).getY(), 3, Color.BLACK));
		}
		for (int i = 0; i < 9; ++i) {
			lineList.add(new Line(travel.getCity(i).getX(), travel.getCity(i).getY(), 
									travel.getCity(i+1).getX(), travel.getCity(i+1).getY()));
		}
		
		//Add elements to panes
		cityList.forEach(center.getChildren()::add);
		lineList.forEach(center.getChildren()::add);
		center.getChildren().addAll(runApp, initialDistance, finalDistance);
		
		runApp.setText("Run");
		runApp.setOnAction(this);
		initialDistance.setText("Initial Distance: " + travel.getDistance());

		runApp.setLayoutY(10);
		initialDistance.setLayoutY(10);
		initialDistance.setLayoutX(80);
		finalDistance.setLayoutY(10);
		finalDistance.setLayoutX(300);
		
		//organizing border pane
		border.setPadding(new Insets(10, 20, 10, 20));
		border.setCenter(center);
		
		//setting scene and stage
		Scene scene = new Scene(border, 600, 600);
		stage.setTitle("TSP");
		stage.setScene(scene);
		stage.show();
	}

    public static void main(String args[]) {
    	launch(args);
    }
}