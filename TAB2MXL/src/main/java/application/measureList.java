//package application;
//
//import java.io.File;
//import java.io.FileNotFoundException;
//import java.net.URL;
//import java.util.List;
//import java.util.ResourceBundle;
//
//import javafx.fxml.FXML;
//import javafx.fxml.Initializable;
//import javafx.scene.control.TableColumn;
//import javafx.scene.control.TableView;
//import javafx.scene.control.cell.PropertyValueFactory;
//import test.TabReaderV4;
//import xmlClasses.Chain;
//
//public class measureList implements Initializable {
//
//	@FXML
//	private TableView measures;
//	
//	@FXML
//	private TableColumn<measuresList, String> measueNum;
//	@FXML
//	private TableColumn<measuresList, String> time;
//	@Override
//	public void initialize(URL location, ResourceBundle resources) {
//		measueNum.setCellValueFactory(new PropertyValueFactory<>("NUM"));
//		time.setCellValueFactory(new PropertyValueFactory<>("TIME"));
//		fill();
//	}
//
//	private void fill() {
//		measures.getItems().clear();
//		List<measuresList> bookings; 
//
//		for(ParkingSpot ps: s) {
//			if(!tableView.getItems().contains(ps)) {
//				if(bookings.contains(ps)) {
//					
//				}
//				tableView.getItems().add(ps);
//			}
//		}
//		
//	}
//	
//	
//	private class measuresList {
//		List measures;
//		private String num, time;
//		private int size;
//		
//		public measuresList() throws Exception{
//			File f = new File("TempD.txt");
//			String tab = tab2mxl.txtAnalyzing.analyze(f.toString());
//
//			System.out.println("measuresTEXTAREA "+tab);
//					List<String[]> TAB = new TabReaderV4( Chain.stringToFile( tab ), 6).listMeasures();
//					size= TAB.size();
//		}
//		
//		public String getNUM() {
//			return num;
//		}
//		public String getTIME() {
//			return time;
//		}
//		
//		public getList() {
//			return measures;
//		}
//	}
//}
//
//	