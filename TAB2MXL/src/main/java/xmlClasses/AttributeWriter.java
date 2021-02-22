package xmlClasses;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlType;

public class AttributeWriter {
	
	private Attributes att;
	private int divisions;
	private Key key;
	private Time time;
	private Clef c;
	private StaffDetails sd;
	ArrayList<StaffTuning> st = new ArrayList<StaffTuning>();
    
	
	AttributeWriter(int fifths, int divisions, int beats, int beat_type, String clef, int line, int staffLines){
		key = new Key(fifths); //HARD CODED
		time = new Time(beats, beat_type);
		c = new Clef(clef, line);
		this.divisions = divisions;
	    sd = new StaffDetails(staffLines, st);
		att = new Attributes(this.divisions, key, time, c, sd);
	}
	
	//done
	public Attributes getAttributes() {
		return att;
	}
	
	public void setTuning(String[][] tunings) {
		for (int i = 0; i < tunings.length; i++) {
            st.add(new StaffTuning( i+1, tunings[i][0], Integer.parseInt(tunings[i][1])));
        }
	}
}
