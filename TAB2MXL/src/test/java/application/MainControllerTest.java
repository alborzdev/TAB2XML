package application;

import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.api.FxAssert;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.matcher.control.LabeledMatchers;

import com.jfoenix.controls.JFXTextArea;

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
    public void TestUploadButton() {
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
    	assertEquals(combobox.getSelectionModel().getSelectedItem().equals("Tab"),true);
    	
    }
    
    @Test
    public void Attributes() {
    	TextField comp =find("#composer");
    	clickOn(comp);
    	comp.setText("Lian Attily");
    	assertEquals("Lian Attily", comp.getText());
    }
    
    @Test
    public void TestHelpMenuItems(){
    	clickOn("#Help");
    	clickOn("#UserManual");
    	
    }
    
    @Test
    public void TestExport() throws InterruptedException {
    	clickOn(textarea);
    	textarea.appendText("e|-------5-7-----7-|-8-----8-2-----2-|-0---------0-----|-----------------|\r\n"
    			+ "B|-----5-----5-----|---5-------3-----|---1---1-----1---|-0-1-1-----------|\r\n"
    			+ "G|---5---------5---|-----5-------2---|-----2---------2-|-0-2-2---2-------|\r\n"
    			+ "D|-7-------6-------|-5-------5-------|-3---------------|-----------------|\r\n"
    			+ "A|-----------------|-----------------|-----------------|-2-0-0---0---8-7-|\r\n"
    			+ "E|-----------------|-----------------|-----------------|-----------------|\r\n"
    			+ "");
    	clickOn(export).type(KeyCode.C).press(KeyCode.ENTER).release(KeyCode.ENTER);
    	
    }
    
    @Test
    public void TestTextArea() throws InterruptedException {
    	clickOn(textarea);
    	String s = "e|-------5-7-----7-|-8-----8-2-----2-|-0---------0-----|-----------------|\r\n"
    			+ "B|-----5-----5-----|---5-------3-----|---1---1-----1---|-0-1-1-----------|\r\n"
    			+ "G|---5---------5---|-----5-------2---|-----2---------2-|-0-2-2---2-------|\r\n"
    			+ "D|-7-------6-------|-5-------5-------|-3---------------|-----------------|\r\n"
    			+ "A|-----------------|-----------------|-----------------|-2-0-0---0---8-7-|\r\n"
    			+ "E|-----------------|-----------------|-----------------|-----------------|\r\n"
    			+ "";
    	textarea.appendText(s);
		Thread.sleep(100);
		textarea.deleteText(4, 8);
		//Thread.sleep(100);
		clickOn(export);
		//Thread.sleep(1000);
    }
    
    @Test
    public void TestEmptySaveChangesButton() {
    	Button sc = find("#savechanges");
    	clickOn(sc);
    	
    	FxAssert.verifyThat(sc, LabeledMatchers.hasText("Save current changes"));
    }
    
    @Test
    public void TestMenu() {
    	clickOn("#menu");
    	FxAssert.verifyThat("#menu", LabeledMatchers.hasText("File"));

    	clickOn("#openrecent");
    	clickOn("#uploadMenu");
    	clickOn("#exit");
    }
    
    @Test
    public void TestSaveChanges() {
    	clickOn(textarea);
    	String s = "e|-------5-7-----7-|-8-----8-2-----2-|-0---------0-----|-----------------|\r\n"
    			+ "B|-----5-----5-----|---5-------3-----|---1---1-----1---|-0-1-1-----------|\r\n"
    			+ "G|---5---------5---|-----5-------2---|-----2---------2-|-0-2-2---2-------|\r\n"
    			+ "D|-7-------6-------|-5-------5-------|-3---------------|-----------------|\r\n"
    			+ "A|-----------------|-----------------|-----------------|-2-0-0---0---8-7-|\r\n"
    			+ "E|-----------------|-----------------|-----------------|-----------------|\r\n"
    			+ "";
    	textarea.appendText(s);
    	clickOn("#savechanges");
    	clickOn("#menu");
    	clickOn("#openrecent").clickOn("YES");
    	assertEquals(textarea.getText().equals(s), true);
    }
    
    @Test
    public void TestAdvancedSettings() {
    	clickOn(textarea);
    	
    	String s = "e|-------5-7-----7-|-8-----8-2-----2-|-0---------0-----|-----------------|\r\n"
    			+ "B|-----5-----5-----|---5-------3-----|---1---1-----1---|-0-1-1-----------|\r\n"
    			+ "G|---5---------5---|-----5-------2---|-----2---------2-|-0-2-2---2-------|\r\n"
    			+ "D|-7-------6-------|-5-------5-------|-3---------------|-----------------|\r\n"
    			+ "A|-----------------|-----------------|-----------------|-2-0-0---0---8-7-|\r\n"
    			+ "E|-----------------|-----------------|-----------------|-----------------|\r\n"
    			+ "";
    	textarea.appendText(s);
    	clickOn("#advanced");
    	
    	clickOn("#measures").clickOn("1").clickOn("#MeasureTimeSig").clickOn("3/4").clickOn("#close");
    	clickOn(export);
    	
    }
    
    @Test
    public void TestAdvancedSettingsInterval() {
    	clickOn(textarea);
    	
    	String s = "e|-------5-7-----7-|-8-----8-2-----2-|-0---------0-----|-----------------|\r\n"
    			+ "B|-----5-----5-----|---5-------3-----|---1---1-----1---|-0-1-1-----------|\r\n"
    			+ "G|---5---------5---|-----5-------2---|-----2---------2-|-0-2-2---2-------|\r\n"
    			+ "D|-7-------6-------|-5-------5-------|-3---------------|-----------------|\r\n"
    			+ "A|-----------------|-----------------|-----------------|-2-0-0---0---8-7-|\r\n"
    			+ "E|-----------------|-----------------|-----------------|-----------------|\r\n"
    			+ "";
    	textarea.appendText(s);
    	clickOn("#advanced");
    	clickOn("#from").type(KeyCode.DIGIT2);
    	clickOn("#to").type(KeyCode.DIGIT3).clickOn("#MeasureTimeSig").clickOn("4/4").clickOn("#close");
    	clickOn(export);
    }

}
