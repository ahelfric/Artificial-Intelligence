// Course: 			CS4242
// Student Name:	Alex Helfrich
// Student ID:		000740491
// Assignment #:	#1
// Due Date:		9/10/18
// Signature:		
// Score:			



public class VacuumActivity extends Application implements EventHandler<ActionEvent>{
	private final Text title = new Text("Lab 1: Vacuum AI");
	private final Text instructions = new Text("Enter size of floor, then run application. Dirt generation is random.");
	private final Text lengthTitle = new Text("Length:");
	private final Text widthTitle = new Text("Width:");
	private final TextField length = new TextField();
	private final TextField width = new TextField();
	private final Button runApp = new Button(); 
	
	private final Text initArray = new Text("");
	private final Text finalArray = new Text("");
	private final Text perfMetrics = new Text("");
	
	public Vacuum vacuum;
	public Floor floor;
	
	
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void handle(ActionEvent event) {
		if(runApp == event.getSource()) {
			try {
				floor = new Floor(Integer.parseInt(length.getText()), 
								  Integer.parseInt(width.getText()));
			} catch (NumberFormatException e) {
				instructions.setText("Integers only, please");
			} 
			vacuum = new Vacuum(floor);
			initArray.setText(vacuum.startFloorToString());
			vacuum.algorithm();
			finalArray.setText(vacuum.finalFloorToString());
			perfMetrics.setText(vacuum.metricsToString());
		}
	}

	@Override
	public void start(Stage stage) throws Exception {
		BorderPane border = new BorderPane();
		Pane center = new Pane();
		
		//adding elements to panes
		center.getChildren().addAll(title, instructions, lengthTitle, widthTitle, length, 
									width, runApp, initArray, finalArray, perfMetrics);
		
		//editing button titles and setting actions
		runApp.setText("Run");
		runApp.setOnAction(this);
		
		//editing center pane
		instructions.setLayoutY(18);
		lengthTitle.setLayoutY(39);
		length.setLayoutY(45);
		widthTitle.setLayoutY(85);
		width.setLayoutY(90);
		runApp.setLayoutY(130);
		
		initArray.setLayoutY(180);
		finalArray.setLayoutY(360);
		perfMetrics.setLayoutX(120);
		perfMetrics.setLayoutY(300);
		
		//organizing border pane
		border.setPadding(new Insets(10,20,10,20));
		border.setCenter(center);
		
		//setting scene and stage
		Scene scene = new Scene(border, 1000, 550);
		stage.setTitle("Lab 1: Vacuum AI");
		stage.setScene(scene);
		stage.show();
	}

}

