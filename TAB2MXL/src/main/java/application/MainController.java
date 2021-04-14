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
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.prefs.Preferences;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXToggleButton;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.KeyEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import test.LineErrorException;
import test.TabReaderV4;
import test.TabReaderV5;
import xmlClasses.Chain;

public class MainController implements Initializable {

	private Stage stage;
	private File file;
	private static boolean CHANGE = false;

	@FXML
	private JFXTextField from, to;
	@FXML 
	private JFXToggleButton toggle = new JFXToggleButton();
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

	@FXML
	private JFXTextArea measuresTEXTAREA = new JFXTextArea();

	static int []timesigs;
	private static int size;
	String tab;
	Chain chain;

	/**
	 * Detects the tab type (instrument type)
	 * @returns the index of the detected instrument 
	 */
	private int detector() {
		int DetectedIntrument = (ErrorHandling.detectInstrument( textarea.getText() )/10);
		if(DetectedIntrument == 1) InstrumentType.getSelectionModel().select(0);//set guitar
		else if(DetectedIntrument == 2 || DetectedIntrument == 3 ) InstrumentType.getSelectionModel().select(2);  //set bass
		else if(DetectedIntrument == 4) InstrumentType.getSelectionModel().select(1);//set drums
		//else return -1; //cannot detect intrument. Tell user to pick instrument and line count
		return DetectedIntrument;
	}

	/**
	 * This method allows Open/Upload button to select a .txt file and display it in text area
	 * @param event
	 * @throws Exception 
	 * 
	 */
	public void doOpen(ActionEvent event) throws Exception {
		//file chooser
		FileChooser filechooser= new FileChooser(); 
		filechooser.setTitle("Open text file"); 
		filechooser.getExtensionFilters().addAll( new FileChooser.ExtensionFilter(".txt files", "*.txt") );
		file = filechooser.showOpenDialog(stage); 
		if(file==null) {
			System.out.println("No file has been selected");
		}
		if(file!=null) {
			//check if textarea is empty
			if(textarea.getText().trim().length() != 0) {
				ButtonType YES = new ButtonType("Yes");
				ButtonType NO = new ButtonType("No");
				ButtonType Cancel = new ButtonType("Cancel upload");
				AlertType type = AlertType.WARNING; 
				Alert alert = new Alert(type, "Would you like to overwrite the current text area?", YES, NO, Cancel); 
				alert.getDialogPane();
				alert.showAndWait().ifPresent(response ->{
					if(response == YES) {
						textarea.clear();
						try {
							textarea.appendText(tab2mxl.txtAnalyzing.analyze(file.toString()));
						} catch (FileNotFoundException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					if(response == NO) {
						try {
							textarea.appendText(tab2mxl.txtAnalyzing.analyze(file.toString()));
						} catch (FileNotFoundException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}

				});;
			}
			else textarea.appendText(tab2mxl.txtAnalyzing.analyze(file.toString()));
			ERRORStextarea.clear();
			detector();
		}
		//if(CHANGE)loadArray();
		if(size==0){
			List<String[]> TAB2 = new TabReaderV5( Chain.stringToFile( textarea.getText() ), ErrorHandling.detectInstrument(textarea.getText())%10).listMeasures();
			for(int i=0;i<TAB2.size();i++) {
				String [] t=TAB2.get(i);
				System.out.println("printing t String[]");
				for(int j=0;j<t.length;j++)	
					System.out.println(t[j]);
			}
			timesigs = new int[TAB2.size()];
			size = TAB2.size();
			for(int i=0;i<size;i++) {
				measures.getItems().add(i+1);
				timesigs[i]=getTimeSig();
			}
		}
		//updateTimeSigsArray();

	}

	/**
	 * Updates the array of time signatures for each measure. This is called when the tab input is provided to initialize
	 * the time signature to 4/4 and set the size of the array to the number of measures
	 * @throws Exception
	 */
	public void updateTimeSigsArray() throws Exception {
		//NEW TIME SIGNATURE ARRAYS
		File f = new File("TempD.txt");
		tab = tab2mxl.txtAnalyzing.analyze(f.toString());

		//if we are in the advanced options FXML file do this:
		List<String[]> TAB = new TabReaderV5( Chain.stringToFile( tab ), ErrorHandling.detectInstrument(tab)%10).listMeasures();
		System.out.println("size = "+TAB.size());
		for(int i=0;i<TAB.size();i++) {
			String [] t=TAB.get(i);
			System.out.println("printing t String[]");
			for(int j=0;j<t.length;j++)	
				System.out.println(t[j]);
		}
		timesigs = new int[TAB.size()];
		size = TAB.size();
		for(int i=0;i<size;i++) {
			measures.getItems().add(i+1);
			timesigs[i]=44;
		}

		//If we are in the main fxml, initialize like this:
		if(!textarea.getText().isEmpty()) {
			List<String[]> TAB2 = new TabReaderV5( Chain.stringToFile( textarea.getText() ), ErrorHandling.detectInstrument(textarea.getText())%10).listMeasures();
			System.out.println("size = "+TAB2.size());
			for(int i=0;i<TAB2.size();i++) {
				String [] t=TAB2.get(i);
				System.out.println("printing t String[]");
				for(int j=0;j<t.length;j++)	
					System.out.println(t[j]);
			}
			timesigs = new int[TAB2.size()];
			size = TAB2.size();
			for(int i=0;i<size;i++) {
				measures.getItems().add(i+1);
				timesigs[i]=44;
			}
		}
	}
	/**
	 * This method updates the time signature of a measure or more when its chosen from the combo box
	 * @param event
	 */
	@FXML
	public void changeMeasure(ActionEvent event) {
		String s = MeasureTimeSig.getSelectionModel().getSelectedItem();
		System.out.println(s);
		//If there is an interval of measures to change do this:
		if(!to.getText().isEmpty() && !from.getText().isEmpty()) {		
			Integer TO = Integer.parseInt(to.getText()), FROM = Integer.parseInt(from.getText());
			if(TO>timesigs.length || FROM>timesigs.length || TO<=0 || FROM<=0 || TO<FROM) {
				Alert conf = new Alert(AlertType.ERROR,  
						"Could not change time signatures"); 
				conf.setContentText("Please enter a valid range.");
				conf.showAndWait(); 
			}else {
				for(int i = FROM-1; i<TO;i++) {
					if(s.compareTo("3/4")==0)
						timesigs[i]=34;
					else timesigs[i]=44;
					System.out.println("m="+i+" changing to "+timesigs[i]);
				}
			}

		}
		//If we are only changing the time signature of one measure do this:
		else if(measures.getSelectionModel().getSelectedItem()!=null){
			int m = measures.getSelectionModel().getSelectedIndex();
			if(s.compareTo("3/4")==0)
				timesigs[m]=34;
			else timesigs[m]=44;

			System.out.println("m="+m+" changing to "+timesigs[m]);
		}
		//flag so that we do NOT reset the time signatures when exporting
		CHANGE = true;
		System.out.println("CHANGE 209 = "+CHANGE);
	}


	/**
	 * Lets user specify name and path of xml file and exports an xml file
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
		System.out.println("change = "+CHANGE);
		System.out.println("gettimesigs = "+getTimeSig());

		//this indicates that certain measures' time signature changed so load from the database txt that holds the info
		if(CHANGE)loadArray();

		else {
			List<String[]> TAB2 = new TabReaderV5( Chain.stringToFile( textarea.getText() ), ErrorHandling.detectInstrument(textarea.getText())%10).listMeasures();
			for(int i=0;i<TAB2.size();i++) {
				String [] t=TAB2.get(i);
				System.out.println("printing t String[]");
				for(int j=0;j<t.length;j++)	
					System.out.println(t[j]);
			}
			timesigs = new int[TAB2.size()];
			size = TAB2.size();
			for(int i=0;i<size;i++) {
				measures.getItems().add(i+1);
				timesigs[i]=getTimeSig();
			}
		}
		System.out.println("SIZE = "+size);

		System.out.println("Measures");
		for(int i=0;i<size;i++) {
			System.out.println(timesigs[i]);
		}
		int stafflines = ErrorHandling.detectInstrument(textarea.getText());

		chain = new Chain(textarea.getText(), getTitle(), getLyricist(),getComposer(), timesigs, getKey(), getType(),getConversionType(), stafflines);     	

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
	public void resetTime() {
		CHANGE = false;
	}

	/**
	 * updates the errors and warnings text area every time there is a change
	 * Mainly catches exception from Chain
	 * @param event
	 * @throws Exception
	 */
	@FXML
	public void updateTextArea(KeyEvent event) throws Exception {
		System.out.println("KEY EVENT TRIGGERED");
		boolean flag = false;
		detector();
		if(CHANGE)loadArray();
		else if(size==0){
			List<String[]> TAB2 = new TabReaderV5( Chain.stringToFile( textarea.getText() ), ErrorHandling.detectInstrument(textarea.getText())%10).listMeasures();
			for(int i=0;i<TAB2.size();i++) {
				String [] t=TAB2.get(i);
				System.out.println("printing t String[]");
				for(int j=0;j<t.length;j++)	
					System.out.println(t[j]);
			}
			timesigs = new int[TAB2.size()];
			size = TAB2.size();
			for(int i=0;i<size;i++) {
				measures.getItems().add(i+1);
				timesigs[i]=getTimeSig();
			}
		}
		int stafflines = ErrorHandling.detectInstrument(textarea.getText());
		chain = new Chain(textarea.getText(), getTitle(), getLyricist(),getComposer(), timesigs, getKey(), getType(),getConversionType(), stafflines);     	

		System.out.println("FLAG before = "+flag);
		try{chain.TABtoPART();} 
		catch(LineErrorException e) {
			flag = true;
			System.out.println("LINE ERROR EXCEPTION");
			ERRORStextarea.setStyle("-fx-text-fill: red ;") ;
			ERRORStextarea.clear();
			ERRORStextarea.appendText(e.getMessage());
			//			ErrorHandling.errorEventHighlight(
			//					"Conversion was unsuccessful :(",
			//					e, textarea, "|"+e.getString()+"|");
		}

		try{chain.INFOtoPARTWISE();} 
		catch(Exception e) {
			flag = true;
			System.out.println("Exception");
			ERRORStextarea.setStyle("-fx-text-fill: red ;") ;
			ERRORStextarea.clear();
			ERRORStextarea.appendText(e.getMessage());

		}
		System.out.println("FLAG after = "+flag);
		if(!flag) ERRORStextarea.clear();

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

		KeySig.setStyle("-fx-text-fill:  #e1dddd ;") ;
		KeySig.getSelectionModel().select(0);
		TimeSig.getItems().add("3/4");
		TimeSig.getItems().add("4/4");

		TimeSig.getSelectionModel().select(1);
		TimeSig.setStyle("-fx-text-fill:  white ;");

		InstrumentType.getItems().add("Guitar");
		InstrumentType.getItems().add("Drums");
		InstrumentType.getItems().add("Bass");
		InstrumentType.setStyle("-fx-text-fill: white ;");
		InstrumentType.getSelectionModel().select(0);

		conversionType.getItems().add("Tab");
		conversionType.getItems().add("Sheet Music");
		conversionType.setStyle("-fx-text-fill:  #e1dddd ;") ;
		conversionType.getSelectionModel().select(0);

		MeasureTimeSig.getItems().add("3/4");
		MeasureTimeSig.getItems().add("4/4");

		try {
			updateTimeSigsArray();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
		}

		return s;
	}
	//default = 4/4
	public int getTimeSig() {
		int indx = TimeSig.getSelectionModel().getSelectedIndex();
		System.out.println("indx "+indx);
		switch(indx) {
		case 0: return 34; 
		case 1: return 44; 
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
	/**
	 * Loads the most recently saved tab and its attributes from the database.txt and attributes.txt files
	 * @param event
	 * @throws IOException
	 */
	@FXML
	public void LOADRECENT(ActionEvent event) throws IOException  {
		//check if textarea is empty
		if(textarea.getText().trim().length() != 0) {
			ButtonType YES = new ButtonType("YES");
			ButtonType NO = new ButtonType("NO");
			ButtonType Cancel = new ButtonType("Cancel");
			AlertType type = AlertType.WARNING; 
			Alert alert = new Alert(type, "Would you like to overwrite the current text area?", YES, NO, Cancel); 
			alert.getDialogPane();
			alert.showAndWait().ifPresent(response -> {
				if(response == YES) {
					try {
						loadArray();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					textarea.clear();
					try {
						loader("database.txt","attributes.txt");
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				if(response==NO) { try {
					loader("database.txt","attributes.txt");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}}
				if(response==Cancel) {
					return ;
				}
			});;
		}
		else loader("database.txt","attributes.txt");
		detector();
		loadArray();
	}

	/**
	 * Method that does the actual loading to the main GUI given the file paths
	 * @param database
	 * @param attributes
	 * @throws IOException
	 */
	private void loader(String database, String attributes) throws IOException {
		loadArray();
		BufferedReader br;
		try {
			br = new BufferedReader(new FileReader(database));
			String line;
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
		//reload attributes
		String text;
		String [] parts;
		try {
			br = new BufferedReader(new FileReader(attributes));
			while((text = br.readLine()) != null) {
				parts = text.split(",");
				// now `parts` array will contain your data
				//LOAD ATTRIBUTES

				//getKey()+","+getTimeSig()+","+getConversionType()+","+getType());
				KeySig.getSelectionModel().select(parts[0]);
				if(parts[1].compareTo("44")==0) {TimeSig.getSelectionModel().select(1);}
				else TimeSig.getSelectionModel().select(0);
				if(parts[2].compareTo("TAB")==0) {conversionType.getSelectionModel().select(0);}
				else conversionType.getSelectionModel().select(1);
				InstrumentType.getSelectionModel().select(parts[3]);
				if(parts.length>3) {
					if(parts.length>4 && !parts[4].isEmpty()) composer.setText(parts[4]);
					if(parts.length>5 && !parts[5].isEmpty()) title.setText(parts[5]);
					if(parts.length>6 && !parts[6].isEmpty()) lyricist.setText(parts[6]);
				}

			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Prints a single selected measure from the combo box in the advanced options controller
	 * @param event
	 * @throws Exception
	 */
	@FXML
	public void printSelection(ActionEvent event) throws Exception {
		List<String[]> TAB = new TabReaderV5( Chain.stringToFile( tab ), ErrorHandling.detectInstrument(tab)%10 ).listMeasures();

		if(measures.getSelectionModel().getSelectedItem()!=null) {
			System.out.println("print selection:");
			System.out.println("!!!!!!!!!!!!"+ErrorHandling.detectInstrument(tab)%10);
			int i = measures.getSelectionModel().getSelectedIndex();
			String [] t=TAB.get(i);
			measuresTEXTAREA.clear();
			for(int j=0;j<t.length;j++)	{
				System.out.println("HEREEEEE"+t[j]);
				measuresTEXTAREA.appendText("|");
				measuresTEXTAREA.appendText(t[j]);
				measuresTEXTAREA.appendText("|\n");
			}
		}

	}

	/**
	 * Prints a range of selected measure from the combo box in the advanced options controller
	 * @param event
	 * @throws Exception
	 */
	@FXML
	public void printIntervalSelection(KeyEvent event) throws Exception {
		List<String[]> TAB = new TabReaderV5( Chain.stringToFile( tab ), ErrorHandling.detectInstrument(tab)%10 ).listMeasures();
		if(to.getText()!=null && from.getText()!=null) {
			System.out.println("PRINTING SELECTION\n");
			int fr = Integer.parseInt(from.getText()), TO = Integer.parseInt(to.getText());
			measuresTEXTAREA.clear();
			for(int i = fr-1; i<=TO-1; i++) {
				String [] t=TAB.get(i);
				for(int j=0;j<t.length;j++)	{
					System.out.println("FROM = "+fr+"   TO: "+TO);
					System.out.println(t[j]);
					measuresTEXTAREA.appendText("|");
					measuresTEXTAREA.appendText(t[j]);
					measuresTEXTAREA.appendText("|\n");
				}
				System.out.println("\n");
			}
		}
	}

	/**
	 * Saves the current tab file and all of its attributes into *.txt files
	 * @param event
	 * @throws IOException
	 */
	@FXML
	public void saveChanges(ActionEvent event) throws IOException {
		saveArray();
		if(textarea.getText().isBlank() || textarea.getText().isEmpty()) {
			Alert conf = new Alert(AlertType.ERROR,  
					"Could not save changes"); 
			conf.setContentText("There is nothing to save");
			conf.showAndWait(); 
		}
		else {
			FileWriter fw;
			try {
				{fw = new FileWriter("database.txt",false);

				BufferedWriter bw = new BufferedWriter(fw); 
				PrintWriter pw = new PrintWriter(bw); 
				pw.println(textarea.getText());
				pw.flush(); 
				pw.close();}

				//save attributes
				fw = new FileWriter("attributes.txt",false);

				BufferedWriter bw = new BufferedWriter(fw); 
				PrintWriter pw = new PrintWriter(bw); 
				pw.println(getKey()+","+getTimeSig()+","+getConversionType()+","+getType()+","+getComposer()+","+getTitle()+","+getLyricist());

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
	/*
	 * Saves the text area into a temporaty txt file before opening the advanced options window
	 */
	private void save() {
		FileWriter fw;
		try {
			{fw = new FileWriter("tempD.txt",false);

			BufferedWriter bw = new BufferedWriter(fw); 
			PrintWriter pw = new PrintWriter(bw); 
			pw.println(textarea.getText());
			pw.flush(); 
			pw.close();}

			//save attributes
			fw = new FileWriter("tempA.txt",false);

			BufferedWriter bw = new BufferedWriter(fw); 
			PrintWriter pw = new PrintWriter(bw); 
			pw.println(getKey()+","+getTimeSig()+","+getConversionType()+","+getType()+","+getComposer()+","+getTitle()+","+getLyricist());

			pw.flush(); 
			pw.close();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}

	/*
	 * Displays the advanced options window
	 */
	@FXML
	public void advancedOptions(ActionEvent event) throws Exception {
		save();
		Parent root;
		try {
			root = FXMLLoader.load(getClass().getResource("/fxml-files/advanced.fxml"));
			Stage stage = new Stage();
			stage.setTitle("Advanced Options");
			stage.getIcons().add(new Image("https://icons-for-free.com/iconfiles/png/512/music+icon-1320184414432119131.png"));
			stage.setScene(new Scene(root, 450, 450));
			stage.show();
			// Hide this current window (if this is what you want)
			// ((Node)(event.getSource())).getScene().getWindow().hide();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		//textarea.clear();
		//loader("tempD.txt","tempA.txt");
		System.out.println("613");
		updateTimeSigsArray();
	}

	/**
	 * Closes the advanced options window
	 */
	@FXML private Button close;
	@FXML
	public void Close(ActionEvent event) throws IOException {
		Stage stage = (Stage) close.getScene().getWindow();
		stage.close();
		saveArray();
	}
	/*
	 *Saves the individual measures' time signatures into a txt file
	 */
	private void saveArray() throws IOException {
		FileWriter fw;
		fw = new FileWriter("timesigs.txt",false);

		BufferedWriter bw = new BufferedWriter(fw); 
		PrintWriter pw = new PrintWriter(bw); 
		for(int i =0;i<size;i++) {
			pw.println(timesigs[i]);
			System.out.println("saving into file -> "+timesigs[i]);	
		}
		pw.flush(); 
		pw.close();
	}
	/*
	 * Loads the individual measures' time signatures from a txt file
	 */
	private void loadArray() throws IOException {
		BufferedReader br;

		br = new BufferedReader(new FileReader("timesigs.txt"));
		String line;
		int i=0;
		while ((line = br.readLine()) != null) {
			System.out.println("loading - > "+line);
			if(line.equals("34"))
				timesigs[i]=34; 
			else timesigs[i]=44;
			i++;
		}
		br.close();
		CHANGE = true;
	}

	//text area getter
	public String getTextArea() {
		return textarea.getText();
	}

}
