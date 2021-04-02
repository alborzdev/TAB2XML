package application;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextArea;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import test.TabReaderV4;
import xmlClasses.Chain;

public class advancedController implements Initializable {
	@FXML 
	private JFXComboBox<Integer> measures= new JFXComboBox<Integer>();

	@FXML 
	private JFXComboBox<String> MeasureTimeSig= new JFXComboBox<String>();
	
	@FXML
	private JFXTextArea measuresTEXTAREA = new JFXTextArea();

	int []timesigs;
	int size;
	String tab;
	Chain chain;
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub

		MeasureTimeSig.getItems().add("3/4");
		MeasureTimeSig.getItems().add("4/4");
		try {
			updateTimeSigsArray();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void updateTimeSigsArray() throws Exception {
		//NEW TIME SIGNATURE ARRAYS
		File f = new File("TempD.txt");
		tab = tab2mxl.txtAnalyzing.analyze(f.toString());
		System.out.println("TEXT AREA 148");
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
		int m = measures.getSelectionModel().getSelectedIndex();
		String s = MeasureTimeSig.getSelectionModel().getSelectedItem();
		System.out.println(s);
		if(s.compareTo("3/4")==0)
			timesigs[m]=34;
		else timesigs[m]=44;
		System.out.println("m="+m+" changing to "+timesigs[m]);
	}
	private void save() {
		FileWriter fw;
		try {
			{fw = new FileWriter("tempD.txt",false);

			BufferedWriter bw = new BufferedWriter(fw); 
			PrintWriter pw = new PrintWriter(bw); 
			pw.println(tab);
			pw.flush(); 
			pw.close();}

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
        System.out.println("611");
        loader("tempD.txt","tempA.txt");
        System.out.println("613");
        updateTimeSigsArray();
	}
	
	@FXML
	public void goBack(ActionEvent event) throws IOException {
		Parent Scene2root = FXMLLoader.load(getClass().getResource("/fxml-files/Main.fxml"));
		Scene AddInfoScene = new Scene(Scene2root);

		//this gets scene information
		Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
		window.setScene(AddInfoScene);
		window.show();
	}
	public void loader(String database, String attributes) {
		
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
	@FXML private Toggle toggle;
	ToggleGroup group = new ToggleGroup();
	
	@FXML
	public void disableSingle(ActionEvent event) {
		toggle.setToggleGroup(group);
		Toggle temp = group.getSelectedToggle();
		if(!temp.equals(null))
			measures.setDisable(true);
		else measures.setDisable(false);
		System.out.println("disabling toggle function");
	}

}
