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

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
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
	 * ISSUE: When importing a .txt file the text wraps weirdly in the text area. 
	 * This might cause an issue when parsing tab
	 */
	public void doOpen(ActionEvent event) throws FileNotFoundException {
		//file chooser
		FileChooser filechooser= new FileChooser(); 
		filechooser.setTitle("Open text file"); 
		filechooser.getExtensionFilters().addAll( new FileChooser.ExtensionFilter(".txt files", "*.txt") );
		file = filechooser.showOpenDialog(stage); 
		
		if(file!=null) { 
			textarea.appendText( tab2mxl.txtAnalyzing.analyze(file.toString()) );
			System.out.println("Conflict");
		}
		
		
	}
	
	/**
	 * Currently converts .txt back to a .txt file. Lets user specify name and path
	 * @param event
	 * @throws IOException
	 */
	public void convertFile(ActionEvent event) throws IOException {
		FileChooser saver = new FileChooser();
	
		FileChooser.ExtensionFilter extFilter = 
                new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
           saver.getExtensionFilters().add(extFilter);
        	//saver.showSaveDialog(stage);
        	File loc = saver.showSaveDialog(stage);	//get file path specified by user
        FileWriter write;
		try {
			write = new FileWriter(loc);
			write.write(textarea.getText());
       	  	write.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
        	 
		
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
}
