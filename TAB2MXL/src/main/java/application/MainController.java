package application;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Scanner;

import javax.xml.bind.JAXBException;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class MainController implements Initializable {
	
	private Stage stage;
	private File file;

	@FXML
	private TextArea textarea;

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
			textarea.appendText( tab2mxl.txtAnalyzing.analyze(file.toString()) );
			
		}
		
		
	}
	
	/**
	 * Lets user specify name and path
	 * Need to connect backend & frontend so we can convert to .xml
	 * @param event
	 * @throws Exception 
	 */
	public void convertFile(ActionEvent event) throws Exception {
		xmlClasses.ObjectToMxl.mxlMaker();
//		FileChooser saver = new FileChooser();
//	
//		FileChooser.ExtensionFilter extFilter = 
//                new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
//           saver.getExtensionFilters().add(extFilter);
//        	File loc = saver.showSaveDialog(stage);	//get file path specified by user
//        FileWriter write;
//     
//		try {
//			write = new FileWriter(loc);
//			//SHOULD RECIEVE XML FROM BACKEND
//			write.write(getText());
//       	  	write.close();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
		
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
		return textarea.getText();
	}
	
	/**
	 * GETTERS FOR ADDITIONAL INFORMATION
	 */
	@FXML
	private TextField name;
	private TextField composer;
	private TextArea lyricist;
	private TextField title;
	public String getName(ActionEvent event) throws IOException {
		return name.getText();
	}
	public String getComposer(ActionEvent event) throws IOException {
		return composer.getText();
	}
	public String getLyricist(ActionEvent event) throws IOException {
		return lyricist.getText();
	}
	public String getTitle(ActionEvent event) throws IOException {
		return title.getText();
	}
	@FXML
	private TextArea previewXML;
	//method that displays preview of xml file
	public void preview(ActionEvent event) throws IOException {
		File prev = new File("musicTest2.xml");
	//	Sytem.out.println()
		previewXML.appendText(tab2mxl.txtAnalyzing.analyze(prev.toString()));
		//previewXML.appendText(xmlClasses.ObjectToMxl.getText());
	}
	
}
