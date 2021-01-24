package test;
import java.util.Scanner;
import java.util.regex.*;
import java.io.*;

import config.ConfigReader;

public class TabReader {
	ConfigReader cfg = ConfigReader.getConfig();
	File file;
	int string_count, curr_line, curr_measure;
	int measures_in_current_line;
	String[] strings, measure;
	String measureDelimiterRegex="\\|";
	
	public TabReader() {
		string_count = Integer.parseInt(cfg.getAttr("string_count"));
		file = new File(cfg.getAttr("test_path")+cfg.getAttr("test_file"));
		strings = new String[string_count];
		measure = new String[string_count];
		curr_line = 0;
		curr_measure = 0;
		evaluateLine();
    
		
	}
	
	//read one measure at a time
	public void readMeasure() {
		System.out.println("DEBUG: curr_measure/measures_in_current_line (note that there is no measure at =): "+curr_measure + "/" + measures_in_current_line);
		if(curr_measure >= measures_in_current_line) {
			curr_measure = 0;
			System.out.println("DEBUG: end of line, reading next line");
			evaluateLine();
			readMeasure();
		}else {
			System.out.println("DEBUG: measure read:");
			for(int i=0; i<string_count; i++) {
				measure[i] = strings[i].split(measureDelimiterRegex)[curr_measure + 1]; //+1 to skip the tuning data
				System.out.println(measure[i]);
			}
			curr_measure ++;
			
		}
		
	}
	
	//testing method to print one measure at a time
		public void printMeasure() {
			for(String str : measure) {
				System.out.println(str);
			}
			
		}

	//rough method to grab an entire row of measures at a time
	private void evaluateLine() {
		//assume format is the same as in test_file?
		try{
			Scanner sc = new Scanner(file);
			String temp = "";
			int line = (curr_line * (string_count+1));
			
			//move "cursor" to first string in line
			for(int i=0; i<line; i++){
				temp = sc.nextLine();
			}
			
			for(int i=0; i<string_count; i++) {
				strings[i] = sc.nextLine();
			}

			
			//really really rough bit giving me bad feelings, but it seems to work
			//count number of measures start at -1 becuase there is one bar on each side
			int count = -1;
			for(int i=0; i<strings[0].length(); i++){
				if(strings[0].charAt(i) == '|'){
					count++;
				}
			}
			measures_in_current_line = count;
			//end of bad feeling
			
			
			sc.close();
			curr_line++;
		}catch(Exception e){
			System.out.println("DEBUG: something went wrong trying to evaluate line " +curr_line);
			System.out.println(e.toString());
		}

	}
	
	
}
