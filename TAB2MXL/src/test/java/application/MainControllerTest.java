package application;

import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.api.FxAssert;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.matcher.base.WindowMatchers;
import org.testfx.matcher.control.LabeledMatchers;

import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import static org.junit.jupiter.api.Assertions.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

	
class MainControllerTest extends ApplicationTest{
	
	Button Uploadbutton;
	Button export;
	ComboBox<String> combobox;
	JFXTextArea textarea;
	 /* Just a shortcut to retrieve widgets in the GUI. */
    public <T extends Node> T find(final String query) {
        /** TestFX provides many operations to retrieve elements from the loaded GUI. */
        return lookup(query).query();
    }
   
	/**
     * Will be called with {@code @Before} semantics, i. e. before each test method.
	 * @throws IOException 
     */
    @Override
    public void start(Stage primaryStage) throws IOException {
    	System.out.println(getClass().getResource("/fxml-files/Main.fxml"));
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml-files/Main.fxml"));
		AnchorPane root = loader.load();
		MainController controller = loader.getController();
		controller.init(primaryStage);

		Scene scene = new Scene(root,  1263, 517);

		primaryStage.getIcons().add(new Image("https://icons-for-free.com/iconfiles/png/512/music+icon-1320184414432119131.png"));
		primaryStage.setScene(scene);
		primaryStage.setTitle("Tab 2 .xml");
		primaryStage.setResizable(false);
		primaryStage.show();
    }
    
    @BeforeEach
    public void setUp() throws Exception {
    	Uploadbutton = find("#upload");
    	export = find("#export");
    	textarea = find("#textarea");
    }

    @Test
    public void when_Uploadbutton_is_clicked_FileChooser() {
        // when:
        clickOn(".button");

        // then:
        FxAssert.verifyThat(".button", LabeledMatchers.hasText("Upload file"));
        
    }
    
    @Test
    public void InstumentTypeheck() {
    	ComboBox<String> combobox= find("#InstrumentType");
    	clickOn("#InstrumentType");
    	assertEquals(3, combobox.getItems().size());
    }
    @Test
    public void ConversionTypeCheck() {
    	ComboBox<String> combobox= find("#conversionType");
    	clickOn("#conversionType");
    	assertEquals(2, combobox.getItems().size());
    	
    	combobox.getSelectionModel().select(0);
    	
    	
    }
    
    @Test
    public void Attributes() {
    	TextField comp =find("#composer");
    	clickOn(comp);
    	comp.setText("Lian Attily");
    	assertEquals("Lian Attily", comp.getText());
    }
   

    
    @Test
    public void export() throws InterruptedException {
    	clickOn(textarea);
    	textarea.appendText("e|-------5-7-----7-|-8-----8-2-----2-|-0---------0-----|-----------------|\r\n"
    			+ "B|-----5-----5-----|---5-------3-----|---1---1-----1---|-0-1-1-----------|\r\n"
    			+ "G|---5---------5---|-----5-------2---|-----2---------2-|-0-2-2---2-------|\r\n"
    			+ "D|-7-------6-------|-5-------5-------|-3---------------|-----------------|\r\n"
    			+ "A|-----------------|-----------------|-----------------|-2-0-0---0---8-7-|\r\n"
    			+ "E|-----------------|-----------------|-----------------|-----------------|\r\n"
    			+ "");
    	clickOn(export);
    	Thread.sleep(10000);
    	press(KeyCode.ENTER).release(KeyCode.ENTER);
    	
    }
    
    @Test
    public void testTextArea() throws InterruptedException {
    	BufferedReader br;
		try {
			br = new BufferedReader(new FileReader("testTab.txt"));
			String line;
			try {
				while ((line = br.readLine()) != null) {
					 System.out.println(line);
					 textarea.appendText(line);
					 textarea.appendText("\n");
				 }
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		Thread.sleep(100);
		textarea.deleteText(4, 8);
		Thread.sleep(100);
		clickOn(export);
		Thread.sleep(1000);
    }
    
    @Test
    public void testEmptySaveChanges() {
    	Button sc = find("#savechanges");
    	clickOn(sc);
    	
    	FxAssert.verifyThat(sc, LabeledMatchers.hasText("Save current changes"));
    }
    
    @Test
    public void testSaveChanges() {
    	clickOn(textarea);
    	textarea.appendText("e|-------5-7-----7-|-8-----8-2-----2-|-0---------0-----|-----------------|\r\n"
    			+ "B|-----5-----5-----|---5-------3-----|---1---1-----1---|-0-1-1-----------|\r\n"
    			+ "G|---5---------5---|-----5-------2---|-----2---------2-|-0-2-2---2-------|\r\n"
    			+ "D|-7-------6-------|-5-------5-------|-3---------------|-----------------|\r\n"
    			+ "A|-----------------|-----------------|-----------------|-2-0-0---0---8-7-|\r\n"
    			+ "E|-----------------|-----------------|-----------------|-----------------|\r\n"
    			+ "");
    	Button sc = find("#savechanges");
    	clickOn(sc);
    	
    	FxAssert.verifyThat(sc, LabeledMatchers.hasText("Save current changes"));
    }
    
    @Test
    public void testMenu() {
    	clickOn("#menu");
    	FxAssert.verifyThat("#menu", LabeledMatchers.hasText("File"));
    }
    
    @Test
    public void advancedSettings() {
    	clickOn("#advanced");
    	FxAssert.verifyThat(window("Advanced Options"), WindowMatchers.isShowing());
    }
    
    @Test
    public void AdvancedWindowStuff() {
    	clickOn(textarea);
    	textarea.appendText("e|-------5-7-----7-|-8-----8-2-----2-|-0---------0-----|-----------------|\r\n"
    			+ "B|-----5-----5-----|---5-------3-----|---1---1-----1---|-0-1-1-----------|\r\n"
    			+ "G|---5---------5---|-----5-------2---|-----2---------2-|-0-2-2---2-------|\r\n"
    			+ "D|-7-------6-------|-5-------5-------|-3---------------|-----------------|\r\n"
    			+ "A|-----------------|-----------------|-----------------|-2-0-0---0---8-7-|\r\n"
    			+ "E|-----------------|-----------------|-----------------|-----------------|\r\n"
    			+ "");
    	clickOn("#advanced");
    	TextField f = find("#from");
    	clickOn("#from");
    	f.setText("1");
    	TextField t = find("#to");
    	clickOn("#to");
    	t.setText("2");
    	clickOn("#MeasureTimeSig").clickOn("3/4");
    	clickOn("#close");
    	//FxAssert.verifyThat("#", null);
    }
    
    @Test
    public void testPrintSelection() {
    	clickOn(textarea);
    	textarea.appendText("e|-------5-7-----7-|-8-----8-2-----2-|-0---------0-----|-----------------|\r\n"
    			+ "B|-----5-----5-----|---5-------3-----|---1---1-----1---|-0-1-1-----------|\r\n"
    			+ "G|---5---------5---|-----5-------2---|-----2---------2-|-0-2-2---2-------|\r\n"
    			+ "D|-7-------6-------|-5-------5-------|-3---------------|-----------------|\r\n"
    			+ "A|-----------------|-----------------|-----------------|-2-0-0---0---8-7-|\r\n"
    			+ "E|-----------------|-----------------|-----------------|-----------------|\r\n"
    			+ "");
    	clickOn("#advanced");
    	clickOn("#measures").clickOn("1");
    	clickOn("#MeasureTimeSig").clickOn("3/4");
    }
    
    @Test
    public void testMenuItems() {
    	clickOn("#menu").clickOn("#LOADRECENT");
    	clickOn("#helpMENU").clickOn("#UserManual");
    	clickOn("#menu").clickOn("#uploadFile");
    	
    	//clickOn("#menu").clickOn("#exit");
    	
    }

}
