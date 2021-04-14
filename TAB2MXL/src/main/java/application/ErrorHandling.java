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
	
	public static int detectInstrument(String textArea) {
		int lines = 0;
		String[] tempArray;
		String delimiter = "\n";
		
		textArea = textArea + "\nZ\n" + "X\n" + "Y\n" + "W\n" + "V\n" + "U\n";
		tempArray = textArea.split(delimiter);
		for (int i = 0; i < tempArray.length; i++) {
			System.out.println(tempArray[i] + " LINE " + i);
		}
		int j = 0;
		while(tempArray[j].trim() == "") {
			j++;
		}
		for (int i = j; i < tempArray.length - 6; i++) {
			if((tempArray[i].toUpperCase().charAt(0) != '|') && (tempArray[i].charAt(1) != '|')) {
				return 46;
			}
			if(tempArray[i+4].trim().isEmpty() || tempArray[i+4].charAt(0) == ('Z')) {
				lines = 4;
				return 34;
			}
			else if(tempArray[i+5].trim().isEmpty()|| tempArray[i+5].charAt(0) == ('Z')) {
				lines = 5;
				return 25;
			} //test
			else if(tempArray[i+6].trim().isEmpty()|| tempArray[i+6].charAt(0) == ('Z')) {
				lines = 6;
				return 16;
			}
			else if (tempArray[i].toUpperCase().charAt(0) == tempArray[i+5].toUpperCase().charAt(0)
					&& tempArray[i+1].toUpperCase().charAt(0) == tempArray[i+6].toUpperCase().charAt(0)) 
			{
				lines = 5;
				return 25;
			}
			
			else if (tempArray[i].toUpperCase().charAt(0) == tempArray[i+4].toUpperCase().charAt(0)
					&& tempArray[i+1].toUpperCase().charAt(0) == tempArray[i+5].toUpperCase().charAt(0)) 
			{
				lines = 4;
				return 34;
			}
			
			else if (tempArray[i].toUpperCase().charAt(0) == tempArray[i+6].toUpperCase().charAt(0)
					&& tempArray[i+1].toUpperCase().charAt(0) == tempArray[i+7].toUpperCase().charAt(0)) 
			{
				lines = 6;
				return 16;
			}
			
			
			else if (tempArray[i].toUpperCase().charAt(0) == (tempArray[i+1].toUpperCase().charAt(0)) 
					|| (tempArray[i+1].trim().isEmpty())) 
			{
				lines++;
				break;
			} 
			
			lines++;
	
			//System.out.println(tempArray[i].toUpperCase().charAt(0) + "\n");
		}
		if(lines == 6) {
			return 16;
		} 
		else if(lines == 5) {
			return 25;
		}
		else if(lines == 4) {
			return 34;
		} else {
			return 00;
		}
		
	}
	
}
