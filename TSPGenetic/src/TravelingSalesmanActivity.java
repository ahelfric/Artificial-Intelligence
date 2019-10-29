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

public class TravelingSalesmanActivity extends Application implements EventHandler<ActionEvent>{
	private final Button runApp = new Button(); 
    private Text initialDistance = new Text();
    private Text finalDistance = new Text();
    
    private ArrayList<Travel> travelList = new ArrayList<>();
    private Travel travel = new Travel(10);//10 CITIES
    private ArrayList<Circle> cityList = new ArrayList<>();
    private ArrayList<Line> lineList = new ArrayList<>();
    private Travel currentSolution;
    private int firstDistance, secondDistance, lastDistance; 

    public int algorithm(int generationsIn) {
        //travelList should already be initialized with pop = 50;
    	
    	//Can't check for "solution", as there's no defined solution
    	//Therefore, we will run this program for 500 generations,
    	//Checking the distance at 50, 250, and 500 generations 
    	//in order to compare the results
    	
    	int generations = generationsIn;
    	int currentGen = 0;
		while (currentGen < generations) {
    		//mutate all travels in the list
    		mutate();
    		//cull the worst half in the list
    		cull();
    		currentSolution = travelList.get(0);
    		++currentGen;
    		if (currentGen == 50) {
    			firstDistance = currentSolution.getDistance();
    		}
    		if (currentGen == 250) {
    			secondDistance = currentSolution.getDistance();
    		}
    		System.out.println(currentGen);
    	}
    	//The cull() method should already have organized the travels from
    	//best distance to worst, so grab the travel at the front of the list
        return travelList.get(0).getDistance(); 
    }

	private void mutate() {
		for (int i = 0; i < travelList.size(); ++i) {
			travelList.get(i).swapCities();
		}
	}

	private void cull() {
		// TODO Auto-generated method stub
		ArrayList<Travel> temp = new ArrayList<>();
		temp.add(travelList.get(0));
		for (int i = 0; i < travelList.size(); ++i) {
			for (int j = 0; j < temp.size(); ++j) {
				if (travelList.get(i).getDistance() < temp.get(j).getDistance()) {
					temp.add(j,  travelList.get(i));
					break;
				}
			}
			if (travelList.get(i).getDistance() >= temp.get(temp.size()-1).getDistance()) {
				temp.add(travelList.get(i));
			}
		}
		//replace the worst half with newly generated random travels
		for (int i = temp.size()/2; i < temp.size(); ++i) {
			travel.shuffleCities(); //should already be initialized in initTravelList
			temp.set(i, travel);
		}
		travelList = temp;
	}

	private void initTravelList(int listPopSize) {
		travel.generateInitialTravel();
		for (int i = 0; i < listPopSize; ++i) {
			travel.shuffleCities();
			travelList.add(travel);
		}
	}

	@Override
	public void handle(ActionEvent event) {
		if (runApp == event.getSource()) {
			//Initialize travelList with population = 50
			lastDistance = algorithm(500);
			//TODO run for 100, 1000, and 5000 generations, compare results
			finalDistance.setText("After 50 generations, Final Distance: " + firstDistance +
								"\nAfter 250 generations, final distance: " + secondDistance +
								"\nAfter 500 generations, final distance: " + lastDistance);
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
	    
	    //Initialize travelList
		initTravelList(50);
	    
		for (int i = 0; i < 10; ++i) {
			cityList.add(new Circle(travelList.get(0).getCity(i).getX(), 
					travelList.get(0).getCity(i).getY(), 3, Color.BLACK));
		}
		for (int i = 0; i < 9; ++i) {
			lineList.add(new Line(travelList.get(0).getCity(i).getX(), travelList.get(0).getCity(i).getY(), 
					travelList.get(0).getCity(i+1).getX(), travelList.get(0).getCity(i+1).getY()));
		}
		
		//Add elements to panes
		cityList.forEach(center.getChildren()::add);
		lineList.forEach(center.getChildren()::add);
		center.getChildren().addAll(runApp, initialDistance, finalDistance);
		
		runApp.setText("Run");
		runApp.setOnAction(this);
		initialDistance.setText("Initial Distance: " + travelList.get(0).getDistance()
								+ "\nPopulation: 50");

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