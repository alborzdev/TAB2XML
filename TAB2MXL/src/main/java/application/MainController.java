package application;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import xmlClasses.Chain;

public class MainController implements Initializable {
	
	private Stage stage;
	private File file;

	@FXML
	//private TextArea textarea;
	private static JFXTextArea textarea;

	private static String tab;
	Chain chain;
	/**
	 * This method allows Open/Upload button to select a .txt file and display it in text area
	 * @param event
	 * @throws FileNotFoundException
	 * 
	 */
	public void doOpen(ActionEvent event) throws FileNotFoundException {
		//file chooser
		FileChooser filechooser= new FileChooser(); 
		filechooser.setTitle("Open text file"); 
		filechooser.getExtensionFilters().addAll( new FileChooser.ExtensionFilter(".txt files", "*.txt") );
		file = filechooser.showOpenDialog(stage); 
		
		if(file!=null) {
			//Sends Textarea to Backend to anaylize/parse
			textarea.appendText(tab2mxl.txtAnalyzing.analyze(file.toString()) );
			
		}
		tab=new String(textarea.getText());
		
	}
	
	/**
	 * Lets user specify name and path of xml file
	 * @param event
	 * @throws Exception 
	 */
	File loc;
	public void convertFile(ActionEvent event) throws Exception {
		//xmlClasses.ObjectToMxl.mxlMaker();
		FileChooser saver = new FileChooser();
		
		FileChooser.ExtensionFilter extFilter = 
                new FileChooser.ExtensionFilter("xml files (*.xml)", "*.xml");
           saver.getExtensionFilters().add(extFilter);
        	loc = saver.showSaveDialog(stage);	//get file path specified by user
        FileWriter write;
        chain = new Chain(file, getTitle(), getName(), getLyricist(), getComposer(), loc.getAbsolutePath());
//		try {
//			write = new FileWriter(loc);
//			//SHOULD RECIEVE XML FROM BACKEND
//			write.write(xmlClasses.ObjectToMxl.mxlMaker());
//       	  	write.close();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		
	}
  
	/*
	 * @description: changes scene to adding additional info 
	 */
	public void SceneChange(ActionEvent event) throws IOException {
		Parent Scene2root = FXMLLoader.load(getClass().getResource("AdditionalInformation.fxml"));
		Scene AddInfoScene = new Scene(Scene2root, 700, 500);
		
		//this gets scene information
		Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
		window.setScene(AddInfoScene);
		window.show();
	}
	
	public void backButton(ActionEvent event) throws IOException {
		Parent Scene2root = FXMLLoader.load(getClass().getResource("Main.fxml"));
		Scene AddInfoScene = new Scene(Scene2root, 700, 700);
		
		//this gets scene information
		Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
		window.setScene(AddInfoScene);
		window.show();
	}
	/*
	 * Exit button - exit app
	 * */
	public void doExit(ActionEvent event) {
		Platform.exit();
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
	//initializer
	}

	public void init(Stage primaryStage) {
		this.stage = primaryStage;
		
	}
	
	public String getText() {
		return tab;
	}
	
	/**
	 * GETTERS FOR ADDITIONAL INFORMATION
	 */
	@FXML
	private static TextField name=new TextField("");
	@FXML
	private static TextField composer = new TextField("");
	@FXML
	private static TextField lyricist=new TextField("");
	@FXML
	private static TextField title=new TextField("");
	public String getName() throws IOException {
		String s=new String(name.toString());
		return s;
	}
	public String getComposer() throws IOException {
		String s=new String(composer.toString());
		return s;
	}
	public String getLyricist() throws IOException {
		String s=new String(lyricist.toString());
		return s;
	}
	public String getTitle() throws IOException {
		String s=new String(title.toString());
		return s;
	}
	@FXML
	private TextArea previewXML;
	//method that displays preview of xml file
	public void preview(ActionEvent event) throws Exception {
		previewXML.appendText(chain.toString());
	}
	
}
