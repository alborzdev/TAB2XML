package tab2mxl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Scanner;

public class txtAnalyzing {

	public static void main(String[] args) {
		//JUST IMPLEMENTED FOR TESTING
		//finds path of txt file
		String path = System.getProperty("user.dir")+"/src/main/java/tab.txt";
		String song = "";
		
		try {
			song = analyze(path);
			
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
		
		System.out.println(song);
		
	}
	
	public static String analyze(String path) throws FileNotFoundException {
		
		//creates a file object with the path
		File file = new File(path);
		
		//initializes new scanner of the file object
		Scanner scan = new Scanner(file);
		
		//String to hold lines of one measure
		String lines = "";
		
		
		//goes through each line the file has in it
		while(scan.hasNextLine()) {
			
			//stores the current line in lines
			lines += "\n" + scan.nextLine();
		}
		
		//outputs the final string 
		return lines;
		
	}

}
