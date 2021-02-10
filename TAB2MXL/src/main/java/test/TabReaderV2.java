package test;
import java.util.Scanner;
import java.util.regex.*;
import java.io.*;

import config.ConfigReader;

public class TabReaderV2 {
	ConfigReader cfg = ConfigReader.getConfig();
	private File file;
	private int string_count, next_line, curr_measure;
	private String[][] line;
	private String[] measure; //legacy measure format
	private String measureDelimiterRegex="\\|";
	private boolean eof;
	
	public TabReaderV2() {
		string_count = Integer.parseInt(cfg.getAttr("string_count"));
		file = new File(cfg.getAttr("test_path")+cfg.getAttr("test_file"));
		eof = false;
		// measures start at 1, index 0 exists, but is reserved for tuning data
		next_line = 0;
		curr_measure = 1;
		evaluateLine();
	}
	
	public TabReaderV2(String filepath) {
		string_count = Integer.parseInt(cfg.getAttr("string_count"));
		file = new File(filepath);
		eof = false;
		// measures start at 1, index 0 exists, but is reserved for tuning data
		next_line = 0;
		curr_measure = 1;
		evaluateLine();
	}
	
	//read one measure at a time
	public void readMeasure() {
		//System.out.println("DEBUG: curr_measure: "+curr_measure);
		if(curr_measure >= line[0].length) {
			curr_measure = 1;
			System.out.println("DEBUG: end of line, reading next line");
			evaluateLine();
			readMeasure();
		}else if(eof){
			System.out.println("DEBUG: end of file!");
		}else{
			this.measure = new String[string_count];
			for(int i=0; i<string_count; i++) {
				measure[i] = line[i][curr_measure];
			}
			System.out.println("DEBUG: measure read: ");
			this.printMeasure();
			curr_measure ++;
		}
	}
	
	//testing method to print one measure at a time
		public void printMeasure() {
			for(int i=0; i<string_count; i++) {
				System.out.println(measure[i]);
			}
		}
		
		public String[] getMeasure() {
			return this.measure;
		}

	//rough method to grab an entire row of measures at a time
	private void evaluateLine() {
		//assume format is the same as in test_file?
		System.out.println("DEBUG: evaluating line: "+next_line);
		try{
			Scanner sc = new Scanner(file);
			String temp = "";
			int line = (next_line * (string_count+1));
			
			//move "cursor" to first string in line
			for(int i=0; i<line; i++){
				temp = sc.nextLine();
			}
			
			String[] strings = new String[string_count];
			for(int i=0; i<string_count; i++) {
				strings[i] = sc.nextLine();
			}

			
			//really really rough bit giving me bad feelings, but it seems to work
			//count number of measures start at -1 becuase there is one bar on each side
			int measures_in_current_line = 0;
			for(int i=0; i<strings[0].length(); i++){
				if(strings[0].charAt(i) == '|'){
					measures_in_current_line++;
				}
			}
			//end of bad feeling
			
			//System.out.println("DEBUG: found measures_in_current_line: "+(measures_in_current_line-1));
			sc.close();
			this.line = new String[string_count][measures_in_current_line];
			for(int i=0; i<string_count; i++) {
				this.line[i] = strings[i].split(measureDelimiterRegex);
				//System.out.println(this.line[i][1]);
			}
			
			next_line++;
		}catch(Exception e){
			System.out.println("DEBUG: something went wrong trying to evaluate line "+next_line);
			System.out.println(e.toString());
			this.eof = true;
		}

	}
	
	/* used for drum tabs to get drumkit */
	public void resetMeasure() {
		this.curr_measure = 0;
	}
	
	
}
