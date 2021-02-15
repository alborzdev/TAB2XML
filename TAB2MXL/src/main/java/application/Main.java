package application;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;


public class Main extends Application {

	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		System.out.println(getClass().getResource("/fxml-files/Main.fxml"));
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml-files/Main.fxml"));
		AnchorPane root = loader.load();
		MainController controller = loader.getController();
		controller.init(primaryStage);

		Scene scene = new Scene(root,  1263, 517);

		primaryStage.getIcons().add(new Image("https://icons-for-free.com/iconfiles/png/512/music+icon-1320184414432119131.png"));
		primaryStage.setScene(scene);
		primaryStage.setTitle("Tab 2 .xml");
		primaryStage.show();		
		
	}
	
}