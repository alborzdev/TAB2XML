package application;

import java.io.FileWriter;
import java.io.IOException;

import com.jfoenix.controls.JFXTextArea;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import test.LineErrorException;

public class ErrorHandling {

    public static boolean errorEvent(String Title, String Subtitle, Exception e) {
    	
//    	AlertType type = AlertType.ERROR; 
//		Alert alert = new Alert(type, Title); 
//		alert.getDialogPane().setContentText(Subtitle); 
//		alert.showAndWait();

    	return true;
    }
    public static boolean errorEventHighlight(String Title, Exception e, JFXTextArea textarea, String problem) {
    	
    	int[] location = ErrorHandling.findNeedle(textarea.getText(), problem);
    	textarea.selectRange(location[0], location[1]);
    	
		
    	return errorEvent(Title, e.getMessage(), e);
    }
	
	public static int[] findNeedle(String hay, String needle) {
		//if no answer
		if( !hay.contains(needle) ) return null;
		//output
		int[] indices = {0,0};
		
		for(int i = 0; i < hay.length()-needle.length(); i++) {
			//System.out.println(i);
			if(hay.charAt(i)==needle.charAt(0)) {
				//System.out.println("firstchar");
				for(int j = 0; j < needle.length(); j++) {
					//System.out.println(i+" "+hay.substring(i,j+i+1)+" "+(i+j));
					if( needle.equals( hay.substring(i,j+i+1) ) ) {
						System.out.println("FOUND");
						indices[0]=i;
						indices[1]=j+i;
					}
				}
			}
		}
		
		//return
		return indices;
	}
	
}
