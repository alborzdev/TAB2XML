package application;

import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.LinkedList;
import java.util.ResourceBundle;
import java.util.prefs.Preferences;

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
import javafx.scene.input.KeyEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import test.LineErrorException;
import xmlClasses.Chain;

public class MainController implements Initializable {
	
	private Stage stage;
	private File file;


	@FXML
	private JFXTextArea ERRORStextarea;
	@FXML 
	private JFXComboBox<String> KeySig = new JFXComboBox<String>();
	@FXML 
	private JFXComboBox<String> TimeSig= new JFXComboBox<String>() ;
	@FXML 
	private JFXComboBox<String> InstrumentType= new JFXComboBox<String>();
	
	@FXML 
	private JFXComboBox<String> conversionType= new JFXComboBox<String>();
	
	@FXML 
	private JFXComboBox<Integer> measures= new JFXComboBox<Integer>();
	
	@FXML 
	private JFXComboBox<String> MeasureTimeSig= new JFXComboBox<String>();
	
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
		FileChooser saver = new FileChooser();
		
		FileChooser.ExtensionFilter extFilter = 
                new FileChooser.ExtensionFilter("xml files (*.xml)", "*.xml");
           saver.getExtensionFilters().add(extFilter);
        	loc = saver.showSaveDialog(stage);	//get file path specified by user
        FileWriter write;

        chain = new Chain(textarea.getText(), getTitle(), getLyricist(),getComposer(), getTimeSig(), getKey(), getType(),getConversionType());     	
        
        //CHAIN CALLS w/ ERROR HANDLING
        boolean errorEvent = false;

        //T2P
        try { chain.TABtoPART(); } 
        catch ( LineErrorException e ) {
			errorEvent = ErrorHandling.errorEventHighlight(
					"Conversion was unsuccessful :(",
					e, textarea, "|"+e.getString()+"|");
        }
    
        try { chain.INFOtoPARTWISE(); } 
        catch( Exception e ) {
        	errorEvent = ErrorHandling.errorEvent(
					"Conversion was unsuccessful :(",
					"Some attributes are incorrect", e);
        }
        //M2X
		try { chain.MARSHtoXML(); }
		catch ( Exception e ) {
			errorEvent = ErrorHandling.errorEvent(
					"Conversion was unsuccessful :(",
					"Your tab format is correct, something went wrong on our end! Please try again.", e);
		}
		
        if(loc==null) {
        	System.out.println("Exporting has been cancelled");
        }
        if(errorEvent) {
           	AlertType type = AlertType.ERROR; 
			Alert alert = new Alert(type, "Conversion was unsuccessful :("); 
			alert.getDialogPane().setContentText("Exporting was cancelled due to the errors shows on the right.");
			alert.showAndWait();
        }
        else {
	        try {
				write = new FileWriter(loc);
				//SHOULD RECIEVE XML FROM BACKEND
				write.write(chain.getXML());
	       	  	write.close();
	       	  	Alert conf = new Alert(AlertType.CONFIRMATION,  
	                 "Conversion was successful!"); 
	       	  	conf.setContentText("A MusicXML file has been exported.");
	       	 	conf.showAndWait(); 
			} catch (IOException e) { 
				AlertType type = AlertType.ERROR; 
				Alert alert = new Alert(type, "Saving file was unsuccessful :("); 
				alert.getDialogPane().setContentText("Exporting was cancelled. Please try again."); 
				alert.showAndWait();
				e.printStackTrace();	}
        }
	}

	
	@FXML
	public void updateTextArea(KeyEvent event) throws Exception {
		chain = new Chain(textarea.getText(), getTitle(), getLyricist(),getComposer(), getTimeSig(), getKey(), getType(),getConversionType());     	
        try{chain.TABtoPART();
        
        } 
        catch(LineErrorException e) {
        	ERRORStextarea.setStyle("-fx-text-fill: red ;") ;
        	ERRORStextarea.clear();
        	ERRORStextarea.appendText(e.getMessage());
        	ErrorHandling.errorEventHighlight(
					"Conversion was unsuccessful :(",
					e, textarea, "|"+e.getString()+"|");
            }
         
        try{chain.INFOtoPARTWISE();} 
        catch(Exception e) {
        	ERRORStextarea.setStyle("-fx-text-fill: red ;") ;
        	ERRORStextarea.clear();
        	ERRORStextarea.appendText(e.getMessage());
        	//errorEvent=ErrorHandling.errorEventHighlight("Conversion was unsuccessful :(",	e, textarea, "|"+e.getString()+"|");
        }
	}
	
	@FXML
	private MenuItem help;
	public void UserManual(ActionEvent event) {
		File usermanual = new File("Documentation/User Manual.pdf");
		if (Desktop.isDesktopSupported()) {
	        new Thread(() -> {
	            try {
	                Desktop.getDesktop().open(usermanual);
	            } catch (IOException e) {
	                e.printStackTrace();
	            }
	        }).start();
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
		KeySig.getSelectionModel().select(0);
//		KeySig.getItems().add("G Major");
//		KeySig.getItems().add("D Major");
//		KeySig.getItems().add("A Major");
//		KeySig.getItems().add("E Major");
//		KeySig.getItems().add("B Major");
//		KeySig.getItems().add("F# Major");
//		KeySig.getItems().add("C# Major");
		
		TimeSig.getItems().add("3/4");
		TimeSig.getItems().add("4/4");
//		TimeSig.getItems().add("5/4");
//		TimeSig.getItems().add("6/8");
//		TimeSig.getItems().add("7/8");
//		TimeSig.getItems().add("12/8");
		
		InstrumentType.getItems().add("Guitar");
		InstrumentType.getItems().add("Drums - not completely implemented");
		InstrumentType.getItems().add("Bass - not completely implemented");
		InstrumentType.getSelectionModel().select(0);
		
		conversionType.getItems().add("Tab");
		conversionType.getItems().add("Sheet Music");
		
		MeasureTimeSig.getItems().add("3/4");
		MeasureTimeSig.getItems().add("4/4");
		
		for(int i=0;i<11;i++)
			measures.getItems().add(i);
		measures.setStyle("-fx-text-fill: white;");
	}

	public void init(Stage primaryStage) {
		this.stage = primaryStage;
		
	}
		
	
	/**
	 * GETTERS FOR ADDITIONAL INFORMATION
	 */
	public String getConversionType() {
		String s;
		if(conversionType.getSelectionModel().isEmpty()==false) {
			s = conversionType.getSelectionModel().getSelectedItem().toString();
			switch(s) {
			case "Sheet Music": return"G";
			case "Tab":
			default: return "TAB";
			}
		}
			else {
				s="TAB";
//				AlertType type = AlertType.WARNING; 
//				Alert alert = new Alert(type, "Required Information missing"); 
//				alert.getDialogPane().setContentText("Conversion type left empty (default = TAB)"); 
//				alert.showAndWait();
			}
		return s;
	}
	
	//default = Guitar
	public String getType() {
		String s;
		if(InstrumentType.getSelectionModel().isEmpty()==false) {
			s =new String(InstrumentType.getSelectionModel().getSelectedItem().toString());
		}
		else {s="Guitar";
//		AlertType type = AlertType.WARNING; 
//		Alert alert = new Alert(type, "Required Information missing"); 
//		alert.getDialogPane().setContentText("Instrument type left empty (default = Guitar)"); 
//		alert.showAndWait();
		
		}
		
		return s;
	}
	//default = CMajor
	public String getKey() {
		String s;
		if(KeySig.getSelectionModel().isEmpty()==false) {
			s =new String(KeySig.getSelectionModel().getSelectedItem().toString());
		System.out.println("selected key sig "+s);
		}
		else {s= "C Major";
		//AlertType type = AlertType.WARNING; 
		//Alert alert = new Alert(type, "Required Information missing"); 
		//alert.getDialogPane().setContentText("Key Signature empty (default = C Major)"); 
		//alert.showAndWait();
		}
		
		return s;
	}
	//default = 4/4
	public int getTimeSig() {
		int indx = TimeSig.getSelectionModel().getSelectedIndex();
		switch(indx) {
		case 0: return 34;
		case 1: return 44;
		//case 2: return 54;
		//case 3: return 68;
		//case 4: return 78;
		//case 5: return 128;
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
	
	private LinkedList<String> RECENTFILES= new LinkedList<String>();
	Preferences pref;
	@FXML
	public void LOADRECENT(ActionEvent event)  {
		//textarea.setText(RECENTFILES.get(0).toString());
		conversionType.getSelectionModel().select(pref.get("type", getConversionType()));
		KeySig.getSelectionModel().select(pref.get("key", getKey()));
		TimeSig.getSelectionModel().select(pref.getInt("time", getTimeSig()));
		InstrumentType.getSelectionModel().select(pref.get("instrument", getType()));
		textarea.clear();
		textarea.setText(pref.get("Recent", textarea.getText()));
		BufferedReader br;
		try {
			br = new BufferedReader(new FileReader("database.txt"));
			String line;
			textarea.clear();
			try {
				while ((line = br.readLine()) != null) {
					 System.out.println(line);
					 textarea.appendText(line);
					 textarea.appendText("\n");
				 }
			} catch (IOException e) {Alert conf = new Alert(AlertType.ERROR,  
	                 "Could not save changes"); 
	       	  	conf.setContentText("br.readLine wrong");
	       	 	conf.showAndWait(); 
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			Alert conf = new Alert(AlertType.ERROR,  
	                 "Could not save changes"); 
	       	  	conf.setContentText("file reader error");
	       	 	conf.showAndWait(); 
			e.printStackTrace();
		}
		
	}
	
	@FXML
	public void saveChanges(ActionEvent event) {
		pref  =Preferences.userNodeForPackage(MainController.class); 
		pref.put("Recent", textarea.getText());
		pref.put("key", getKey());
		pref.putInt("time", getTimeSig());
		pref.put("type", getConversionType());
		pref.put("instrument", getType());
		FileWriter fw;
		try {
			fw = new FileWriter("database.txt",false);

			BufferedWriter bw = new BufferedWriter(fw); 
			PrintWriter pw = new PrintWriter(bw); 
			pw.println(textarea.getText()); 
			pw.flush(); 
			pw.close();
			RECENTFILES.add(textarea.getText());
			if(RECENTFILES.get(0)!=null) {
			 	Alert conf = new Alert(AlertType.CONFIRMATION,  
		                 ""); 
		       	  	conf.setContentText("CHANGES SAVED");
		       	 	conf.showAndWait(); 
		}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			if(RECENTFILES.get(0)!=null) {
			 	Alert conf = new Alert(AlertType.ERROR,  
		                 "Could not save changes"); 
		       	  	conf.setContentText("SOMETHING WENT WRONG");
		       	 	conf.showAndWait(); 
		}
		} 
		
		
	
	}
}
