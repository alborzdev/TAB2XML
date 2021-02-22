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

//	@Test
//	void TestFileConversion() throws InterruptedException {
//		Thread.sleep(100);
//		System.out.println("Testing\n");
//	}
//	
//	@Test
//	void TestAppClose() {
//		Thread thread = new Thread() { // Wrapper thread.
//            @Override
//            public void run() {
//                try {
//                    Application.launch(MainControllerTest.class.getName()); // Run JavaFX application.
//                    success = true;
//                } catch(Throwable t) {
//                    if(t.getCause() != null && t.getCause().getClass().equals(InterruptedException.class)) {
//                        // We expect to get this exception since we interrupted
//                        // the JavaFX application.
//                        success = true;
//                        return;
//                    }
//                    // This is not the exception we are looking for so log it.
//                    Logger.getLogger(MainControllerTest.class.getName()).log(Level.SEVERE, null, t);
//                }
//            }
//        };
//        thread.setDaemon(true);
//        thread.start();
//        try {
//            Thread.sleep(3000);  // Wait for 3 seconds before interrupting JavaFX application
//        } catch(InterruptedException ex) {
//            // We don't care if we wake up early.
//        }
//        thread.interrupt();
//        try {
//            thread.join(1); // Wait 1 second for our wrapper thread to finish.
//        } catch(InterruptedException ex) {
//            // We don't care if we wake up early.
//        }
//        assertTrue(success);
//    }
//
//	
//	@Test
//	void TestTextAreaGetter() throws InterruptedException, IOException {
//		MainController test=new MainController();
//		Thread.sleep(1000);
//		Assert.assertEquals(test.getText(), null);
//		
//	}
//	
//	@Test
//	void TestPastingTAB() {
//		
//	}
//	
//	@Test
//	void TestingXML() throws Exception {
//		Thread.sleep(1000);
//
//	}
	
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
