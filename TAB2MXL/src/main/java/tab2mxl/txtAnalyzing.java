package tab2mxl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Scanner;

public class txtAnalyzing {

	public static void main(String[] args) throws FileNotFoundException {
		//JUST IMPLEMENTED FOR TESTING
		//finds path of txt file
		String path = System.getProperty("user.dir")+"/src/main/java/tab.txt";
		
		//creates a file object with the path
		File file = new File(path);
		
		//initializes new scanner of the file object
		Scanner scan = new Scanner(file);
		
		//String to hold lines of one measure
		String lines = "";
		
		//goes through each line the file has
		while(scan.hasNextLine()) {
			
			//stores the current line in lines
			lines += "\n" + scan.nextLine();

		}
		
		//outputs the final string
		System.out.println(lines);
		
	}

}
