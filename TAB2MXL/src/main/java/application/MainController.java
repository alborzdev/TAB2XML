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
import xmlClasses.Chain;

public class MainController implements Initializable {

	private Stage stage;
	private File file;

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

	int []timesigs;
	int size;
	String tab;
	Chain chain;
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

		}
		//updateTimeSigsArray();

	}
	/**
	 * 
	 * @throws Exception
	 */
	public void updateTimeSigsArray() throws Exception {
		//NEW TIME SIGNATURE ARRAYS
		File f = new File("TempD.txt");
		 tab = tab2mxl.txtAnalyzing.analyze(f.toString());

		System.out.println("measuresTEXTAREA "+tab);
				List<String[]> TAB = new TabReaderV4( Chain.stringToFile( tab ), 6).listMeasures();
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
				
	}
	@FXML
	public void changeMeasure(ActionEvent event) {
		
		String s = MeasureTimeSig.getSelectionModel().getSelectedItem();
		System.out.println(s);
		if(!to.getText().isEmpty() && !from.getText().isEmpty()) {
			System.out.println("here 167");
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
		else if(measures.getSelectionModel().getSelectedItem()!=null){
			int m = measures.getSelectionModel().getSelectedIndex();
			if(s.compareTo("3/4")==0)
				timesigs[m]=34;
			else timesigs[m]=44;

			System.out.println("m="+m+" changing to "+timesigs[m]);
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
		
		
//		if(to.getText()!=null && from.getText()!=null) {
//			Integer TO = Integer.parseInt(to.getText()), FROM = Integer.parseInt(from.getText());
//			for(int i = TO-1; i<FROM;i++) {
//				timesigs[i]=MeasureTimeSig.getSelectionModel().getSelectedItem();
//			}
//		}

		System.out.println("Measures");
		for(int i=1;i<size;i++) {
			System.out.println(timesigs[i]);
		}
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
		System.out.println("KEY EVENT TRIGGERED");
		chain = new Chain(textarea.getText(), getTitle(), getLyricist(),getComposer(), getTimeSig(), getKey(), getType(),getConversionType());     	
		try{chain.TABtoPART();

		} 
		catch(LineErrorException e) {
			System.out.println("LINE ERROR EXCEPTION");
			ERRORStextarea.setStyle("-fx-text-fill: red ;") ;
			ERRORStextarea.clear();
			ERRORStextarea.appendText(e.getMessage());
			ErrorHandling.errorEventHighlight(
					"Conversion was unsuccessful :(",
					e, textarea, "|"+e.getString()+"|");
		}

		try{chain.INFOtoPARTWISE();} 
		catch(Exception e) {
			System.out.println("Exception");
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
		//measuresTEXTAREA = new JFXTextArea();
		//textarea = new JFXTextArea();
		//measuresTEXTAREA.clear();
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
		TimeSig.getSelectionModel().select(1);
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
					textarea.clear();
					loader("database.txt","attributes.txt");
				}
				if(response==NO) { loader("database.txt","attributes.txt");}
			});;
		}
		else loader("database.txt","attributes.txt");

	}

	public void loader(String database, String attributes) {
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
				//,"+getComposer()+","+getTitle()+","+getLyricist());
				//					//if(parts.length>3)
				//					if(!parts[4].isEmpty()) composer.setText(parts[4]);
				//					if(!parts[5].isEmpty()) title.setText(parts[5]);
				//					if(!parts[6].isEmpty()) lyricist.setText(parts[6]);

				;		
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@FXML
	public void printSelection(ActionEvent event) throws Exception {
		System.out.println("print selection:");
		List<String[]> TAB = new TabReaderV4( Chain.stringToFile( tab ), 6).listMeasures();
		int i = measures.getSelectionModel().getSelectedIndex();
			String [] t=TAB.get(i);
			measuresTEXTAREA.clear();
			for(int j=0;j<t.length;j++)	{
				System.out.println(t[j]);
				measuresTEXTAREA.appendText("|");
				measuresTEXTAREA.appendText(t[j]);
				measuresTEXTAREA.appendText("|\n");
			}
		
	}
	
	@FXML
	public void saveChanges(ActionEvent event) {
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
	@FXML
	public void advancedOptions(ActionEvent event) throws Exception {
		//save text area
		save();
		
		Parent root;
        try {
            root = FXMLLoader.load(getClass().getResource("/fxml-files/advanced.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Advanced Options");
            stage.setScene(new Scene(root, 450, 450));
            stage.show();
            // Hide this current window (if this is what you want)
           // ((Node)(event.getSource())).getScene().getWindow().hide();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        textarea.clear();
        loader("tempD.txt","tempA.txt");
        System.out.println("613");
        updateTimeSigsArray();
	}
	
	@FXML
	public void disableSingle(ActionEvent event) {
		boolean b = false;
		if(measures.isDisabled()==b) {
			b = !b;
			measures.setDisable(b);
		}
	}
	@FXML private Button close;
	@FXML
	public void Close(ActionEvent event) {
	    Stage stage = (Stage) close.getScene().getWindow();
	    stage.close();
	}
}
