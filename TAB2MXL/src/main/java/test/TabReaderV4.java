package test;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.io.File;
import java.io.FileWriter;
import java.util.regex.*;
import config.ConfigReader;

public class TabReaderV4{
	//formal vocabulary for reference
	//line - actual line in the file
	//tabLine - line of tab, all strings can be separated into measures
	
	static ConfigReader cfg = ConfigReader.getConfig();
	private File file;
	private int string_count, scanLine, next_tabLine, curr_measure;
	private String[][] tabLine;
	private String[] measure, tuning;
	private String measureDelimiterRegex="\\|";
	private String tabRegex="([a-g]|[A-G])?\\|(-|[0-9])+\\|((-|[0-9])+\\|)*";
	private Pattern tabPat = Pattern.compile(tabRegex);
	//regex explaination / tab format restrictions
	//must start with tuning data
	//each tabline must contain at least one measure
	//for now can only contain numbers and dashes
	//each measure must contain at least one valid character
	// - just rules
	//should not contain trailing whitespace? might be ok
	//every measure must be the same length
	private boolean eof;
	
	protected TabReaderV4() throws Exception { //basic, config-driven constructor for easy testing - replace with parameter driven version in future
		this.string_count = Integer.parseInt(cfg.getAttr("string_count"));
		this.file = new File(cfg.getAttr("test_path")+cfg.getAttr("test_file"));
		this.eof = false;
		// measures start at 1, index 0 exists - but is reserved for tuning data
		this.next_tabLine = 0;
		this.curr_measure = 1;
		this.evaluateLine();
	}
	
	public TabReaderV4(File file, int string_count) throws Exception { //basic, parameter-driven constructor
		this.string_count = string_count;
		this.file = file;
		this.eof = false;
		// measures start at 1, index 0 exists - but is reserved for tuning data
		this.next_tabLine = 0;
		this.curr_measure = 1;
		this.evaluateLine();
	}
	
	public TabReaderV4(String contents, int string_count) throws Exception { //basic, parameter-driven constructor
		this.string_count = string_count;
		this.eof = false;
		// measures start at 1, index 0 exists - but is reserved for tuning data
		this.next_tabLine = 0;
		this.curr_measure = 1;
		this.evaluateLine();
		//autosave tab
		try {
			FileWriter wr = new FileWriter(cfg.getAttr("autosave_path")+cfg.getAttr("autosave_file"));
			wr.write(contents);
			wr.close();
			this.file = new File(cfg.getAttr("autosave_path")+cfg.getAttr("autosave_file"));
		}catch(Exception e) {
			System.out.println("DEBUG: autosave failure!");
			System.out.println(e.toString());
			throw new Exception("Problem autosaving tab");
		}
		
	}
	
	
	public void readMeasure() throws Exception {
		//System.out.println("DEBUG: curr_measure: "+curr_measure);
		if(curr_measure >= tabLine[0].length) {
			curr_measure = 1;
			System.out.println("DEBUG: end of line, reading next line");
			evaluateLine();
			readMeasure();
		}else if(eof){
			System.out.println("DEBUG: end of file!");
		}else{
			this.measure = new String[string_count];
			for(int i=0; i<string_count; i++) {
				measure[i] = tabLine[i][curr_measure];
			}
			System.out.println("DEBUG: measure read: ");
			this.stringArrayDump("measure",this.measure);
			curr_measure ++;
		}
	}
	
	public String[] getMeasure() {
		return this.measure;
	}
	
	public String[] getTuning() {
		return this.tuning;
	}
	
	
	public void evaluateLine() throws Exception {
		//new and improved, allows for inconsistent lengths + spacing + starting points
		System.out.println("DEBUG: evaluating line: "+next_tabLine);
		try {
			Scanner sc = new Scanner(this.file);
			boolean eof = this.eof;
			String[] uTab = new String[this.string_count]; //unbroken tab
			String temp;
			//runs the scanner until it reaches where it left off last time + string count
			for (int i=0; i<this.scanLine; i++) {
				temp = sc.nextLine();
			}
			//check every subsequent line line for valid tabness
			while(sc.hasNext()) {
				this.scanLine ++;
				temp = sc.nextLine();
				temp = temp.trim();
				Matcher tabMat = tabPat.matcher(temp);
				if(tabMat.matches()) {
					uTab[0] = temp; //save first line of tab
					break; //move on to read + sanity check rest of tabLine and parse if correct
				}else if(countPipeChars(temp) > 1) { //contains 2 or more | characters -> we read that as intended as part of tablature, and throw bad formatting exceptions
					throw new LineErrorException("Bad formatting: detected at line " + this.scanLine, this.scanLine, temp);
				}
			}
			
			if(sc.hasNext()) {
				//no issues, continue to parse next lines
			}else {
				//legitamate end of file?
				eof = true;
				throw new NoSuchElementException();
			}
			
			//parse rest of tabLine + check if valid (formatting sanity check)
			for(int i=1; i<this.string_count; i++) {
				if(sc.hasNext()) {
					temp = sc.nextLine();
					temp = temp.trim();
				}else { //missing line as opposed to badly formatted line
					throw new LineErrorException("Bad Formatting: Missing line " + (this.scanLine + i), this.scanLine + i);
				}
				Matcher tabMat = tabPat.matcher(temp);
				if(tabMat.matches()) {
					uTab[i] = temp;
				}
				else {
					throw new LineErrorException("Bad formatting: detected at line " + (this.scanLine + i), this.scanLine + i, temp);
				}
			}
			
			
			
			//dump what is read
			stringArrayDump("unbroken-tabLine(uTab) "+this.next_tabLine, uTab);
			
			
			//break up into measures + check if valid (content sanity check)
			String[][] bTab = new String[this.string_count][];
			for(int i=0; i<this.string_count; i++) {
				bTab[i] = uTab[i].split(this.measureDelimiterRegex);
			}
			//sanity checks
			// same number of measures in each line
			int measures = bTab[0].length;
			for(int i=1; i<this.string_count; i++) {
				if(measures != bTab[i].length) {
					throw new LineErrorException("Bad content: number of measures in line " + (this.scanLine + i) + " is different from the line above", this.scanLine + i, uTab[i]);
				}
			}
			// same lengths for each simultameous measure
			for(int i=0; i<bTab[0].length; i++) {
				int measureLength = bTab[0][i].length();
				for(int j=1; j<bTab.length; j++) {
					if(measureLength != bTab[j][i].length()) {
						throw new LineErrorException("Bad content: the length of measure " + i + " in line " + (this.scanLine + j) + "is different from the line above", this.scanLine + j, bTab[j][i]);
					}
				}
			}
			
			//all sanity checks passed - may need more in future
			//save broken-up tabLine
			this.tabLine = bTab;
			//save tuning info for quick access
			String[] defaultTuning = {"E","B","G","D","A","E"};
			this.tuning = new String[this.string_count];
			for(int i=0; i<this.string_count; i++) {
				if(bTab[i][0].length() > 0) {  //check if tuning data is missing, replace with defaukt EADGBE
					this.tuning[i] = bTab[i][0];
				}else {
					this.tuning = defaultTuning;
					break;
				}
			}
			
			this.scanLine += this.string_count - 1;
			this.next_tabLine ++;
			sc.close();
			
		}catch(NoSuchElementException e) {
			//suppressed
			//System.out.println("DEBUG: something went wrong trying to evaluate tabLine "+ this.next_tabLine);
			//System.out.println("DEBUG: issue occured at line "+ this.scanLine +" of tab file");
			//System.out.println(e.toString());
			System.out.println("DEBUG: reached end of file with no issues");
			this.eof = true;
		}catch(Exception e) {
			System.out.println("DEBUG: something went wrong trying to evaluate tabLine "+ this.next_tabLine);
			//System.out.println("DEBUG: issue occured at line "+ this.scanLine +" of tab file");
			System.out.println(e.toString());
			this.eof = true;
			throw e;
		}
		

	}
	
	private int countPipeChars(String in) {
		int out = 0;
		for(char i: in.toCharArray()) {
			if(i == '|') {
				out ++;
			}
		}
		return out;
	}
	
	public boolean hasNext() {
		return !eof;
	}
	
	public void stringArrayDump(String arrayName, String[] in) {
		System.out.println("DEBUG: dumping contents of: " +arrayName);
		for(int i=0; i<in.length; i++) {
			System.out.println(in[i]);
		}
	}
}
