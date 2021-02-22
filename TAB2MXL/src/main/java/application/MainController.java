package application;

import java.io.File;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextArea;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import xmlClasses.Chain;

public class MainController implements Initializable {
	
	private Stage stage;
	private File file;


	@FXML 
	private JFXComboBox<String> KeySig;
	@FXML 
	private JFXComboBox<String> TimeSig;
	@FXML 
	private JFXComboBox<String> InstrumentType;
	
	@FXML
	private JFXTextArea textarea;

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
		textarea.clear();
		if(file==null) {
			System.out.println("No file has been selected");
		}
		if(file!=null) {
			//Sends Textarea to Backend to anaylize/parse
			textarea.appendText(tab2mxl.txtAnalyzing.analyze(file.toString()));
		}
	}
	
	/**
	 * Lets user specify name and path of xml file
	 * @param event
	 * @throws Exception 
	 */
	private File loc;
	public void convertFile(ActionEvent event) throws Exception {
		//xmlClasses.ObjectToMxl.mxlMaker();
		FileChooser saver = new FileChooser();
		
		FileChooser.ExtensionFilter extFilter = 
                new FileChooser.ExtensionFilter("xml files (*.xml)", "*.xml");
           saver.getExtensionFilters().add(extFilter);
        	loc = saver.showSaveDialog(stage);	//get file path specified by user
        FileWriter write;
        if(file!=null)chain = new Chain(file, getTitle(), getLyricist(),getComposer(), loc.getAbsolutePath(), getTimeSig(), getKey());
        else { System.out.println(textarea.getText());
        	chain = new Chain(textarea.getText(), getTitle(), getLyricist(),getComposer(), loc.getAbsolutePath(), getTimeSig(), getKey());     	
        }
        if(loc==null) {
        	System.out.println("Exporting has been cancelled");
        }
        try {
			write = new FileWriter(loc);
			//SHOULD RECIEVE XML FROM BACKEND
			write.write(chain.getXML());
       	  	write.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	/**
	 * 
	 *
	 */
	@FXML
	private MenuItem help;
	public void UserManual(ActionEvent event) {
		 FileWriter write;
			try {
				write = new FileWriter("user.home");
				write.write("TAB2MXL_UserManual.pdf");
	       	  	write.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
	}
	
	
	/* Exit button - exit app
	 * */
	public void doExit(ActionEvent event) {
		Platform.exit();
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		KeySig.getItems().add("C Major");
		KeySig.getItems().add("G Major");
		KeySig.getItems().add("D Major");
		KeySig.getItems().add("A Major");
		KeySig.getItems().add("E Major");
		KeySig.getItems().add("B Major");
		KeySig.getItems().add("F# Major");
		KeySig.getItems().add("C# Major");
		
		TimeSig.getItems().add("3/4");
		TimeSig.getItems().add("4/4");
		TimeSig.getItems().add("5/4");
		TimeSig.getItems().add("6/8");
		TimeSig.getItems().add("7/8");
		TimeSig.getItems().add("12/8");
		
		InstrumentType.getItems().add("Guitar");
		InstrumentType.getItems().add("Drums");
		InstrumentType.getItems().add("Bass");
	}

	public void init(Stage primaryStage) {
		this.stage = primaryStage;
		
	}
	
	
	
	/**
	 * GETTERS FOR ADDITIONAL INFORMATION
	 */
	
	public String getType() {
		String s=new String(InstrumentType.getSelectionModel().getSelectedItem().toString());
		System.out.println("selected type "+s);
		return s;
	}
	
	public String getKey() {
		String s=new String(KeySig.getSelectionModel().getSelectedItem().toString());
		System.out.println("selected key sig "+s);
		return s;
	}
	
	public int getTimeSig() {
		int indx = TimeSig.getSelectionModel().getSelectedIndex();
		switch(indx) {
		case 0: return 34;
		case 1: return 44;
		case 2: return 54;
		case 3: return 68;
		case 4: return 78;
		case 5: return 128;
		default: return 44;
		}
	}
	
	@FXML
	private TextField composer = new TextField("");
	@FXML
	private TextField lyricist=new TextField("");
	@FXML
	private TextField title=new TextField("");

	public String getComposer() throws IOException {
		String s=new String(composer.getText());
		return s;
	}
	public String getLyricist() throws IOException {
		String s=new String(lyricist.getText());
		return s;
	}
	public String getTitle() throws IOException {
		String s=new String(title.getText());
		return s;
	}
	@FXML
	public void errorMessage() {
		AlertType type = AlertType.ERROR; 
		Alert alert = new Alert(type, ""); 
		alert.getDialogPane().setContentText("This tab format is not supported"); 
		alert.showAndWait();
	}
	
}
