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
//	private File file;

	@FXML
	private TextArea textarea;

	
	public void doOpen(ActionEvent event) throws FileNotFoundException {
		//file chooser
		FileChooser filechooser= new FileChooser(); 
		filechooser.setTitle("Open text file"); 
		filechooser.getExtensionFilters().addAll( new FileChooser.ExtensionFilter(".txt files", "*.txt") );
		file = filechooser.showOpenDialog(stage); 

		if(file!=null) { 
			System.out.println("Chosen file: " + file);
			Scanner scan = new Scanner(file); 
			while(scan.hasNextLine()) {
				textarea.appendText(scan.nextLine());
				
			}
			scan.close();
		}
		
	}
	
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
  
	
	public void doExit(ActionEvent event) {
		Platform.exit();
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
	
		
	}

	public void init(Stage primaryStage) {
		this.stage = primaryStage;
		
	}
}
