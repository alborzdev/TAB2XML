package application;

import org.junit.jupiter.api.AfterEach;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.api.FxAssert;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.matcher.control.LabeledMatchers;

import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import xmlClasses.Attributes;
import xmlClasses.PartWriter;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextArea;
import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

	
class MainControllerTest extends ApplicationTest{
	
	Button button;
	ComboBox<String> combobox;
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
    }
    
    @Test
    public void Attributes() {
    	TextField comp =find("#composer");
    	clickOn(comp);
    	comp.setText("Lian Attily");
    	assertEquals("Lian Attily", comp.getText());
    }
   
    @Test
    public void Exit() {
    	press(KeyCode.CONTROL).press(KeyCode.X).release(KeyCode.CONTROL).release(KeyCode.X);
    	
    }
    
    @Test
    public void ReqAtt() throws InterruptedException {
    	Button uploadbtn = find("#upload");
    	Button export = find("#export");
    	clickOn(uploadbtn);
    	File inputFile = new File("/testTab.txt");
    	System.out.println("PRINTING IN CONSOLE "+inputFile.getAbsolutePath());
       // when(fileChooser.showOpenDialog(this.targetWindow())).thenReturn(inputFile);
    	//clickOn(export);
    //	FxAssert.verifyThat(export, LabeledMatchers.hasText("export"));
    }

}
