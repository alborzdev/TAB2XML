package xmlClasses;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlType(propOrder={"stafflines", "staffTuning"})
public class StaffDetails {
	private int staffLines;
	private ArrayList<StaffTuning> st;

	public StaffDetails() {}  
	
	public StaffDetails(int sl, ArrayList<StaffTuning> st) { 
	    this.staffLines = sl;  
	    this.st = st;
	} 
	
	@XmlElement(name="staff-lines")
	public int getStafflines() {  
	    return staffLines;  
	}  
	public void setStaffLines(int sl) {  
	    this.staffLines = sl;  
	} 
	
	@XmlElement(name="staff-tuning")
	public ArrayList<StaffTuning> getStaffTuning() {  
	    return st;  
	}  
	public void setStaffTuning(ArrayList<StaffTuning> st) {  
	    this.st = st;  
	}  
}
