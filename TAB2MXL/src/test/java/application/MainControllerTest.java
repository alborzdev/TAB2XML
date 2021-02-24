package application;

import org.junit.jupiter.api.AfterEach;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import xmlClasses.Attributes;
import xmlClasses.PartWriter;

import com.jfoenix.controls.JFXTextArea;
import static org.junit.jupiter.api.Assertions.*;

import java.io.FileNotFoundException;
import java.io.IOException;

	
class MainControllerTest {
	

	private volatile boolean success = false;

	@BeforeEach
	void setUp() {
		//test=new MainController();
	}

	@AfterEach
	void clear() throws IOException {
		
	}

	@Test
	void test1(){
		assertThrows(FileNotFoundException.class,()-> tab2mxl.txtAnalyzing.analyze("somehwere"));
	}
	
//	@Test
//	void test2(){
//		PartWriter p=new PartWriter();
//		
//		Assert.assertNotEquals(p.getPart(), null);
//	}
	
	@Test
	void test3() {
		Attributes a=new Attributes(4,null,null,null);
		assertEquals(a.getDivisions(),4);
		assertEquals(a.getClef(),null);
		assertEquals(a.getKey(),null);
		assertEquals(a.getTime(),null);
	}

}
