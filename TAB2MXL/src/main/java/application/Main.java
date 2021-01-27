package application;
import java.io.File;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;


public class Main extends Application {

	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("Main.fxml"));
		Parent root = loader.load();
		MainController controller = loader.getController();
		controller.init(primaryStage);

		Scene scene = new Scene(root);
		System.out.println("Test");
		System.out.println("Test2");
		primaryStage.getIcons().add(new Image("https://icons-for-free.com/iconfiles/png/512/music+icon-1320184414432119131.png"));
		primaryStage.setScene(scene);
		primaryStage.setTitle("Tab 2 XML");
		primaryStage.show();		
		
	}
	
}



















/*
		Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("Main.fxml"));

//			Scene scene = new Scene(root);
			 primaryStage.setTitle("JavaFX App");

		        FileChooser fileChooser = new FileChooser();

		        Button button = new Button("Select File");
		        button.setOnAction(e -> {
		            File selectedFile = fileChooser.showOpenDialog(primaryStage);
		        });

		        VBox vBox = new VBox(button);
		        Scene scene = new Scene(vBox, 960, 600);

		        primaryStage.setScene(scene);
		        primaryStage.show();
		        fileChooser.getExtensionFilters().addAll(
		        	     new FileChooser.ExtensionFilter("Text Files", "*.txt")
		        	);
			primaryStage.setTitle("T2X");
			primaryStage.setScene(scene);
			primaryStage.show();

*/