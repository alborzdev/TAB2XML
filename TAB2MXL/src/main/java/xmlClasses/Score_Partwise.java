package xmlClasses;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "score-partwise")
@XmlType(propOrder={"partList", "part"})
public class Score_Partwise {
	private double version;
	private Part_List part_list;
	private Part part;
	
	public Score_Partwise() {}  
	
	public Score_Partwise(double version, Part_List part_list, Part part) {  
	    super();  
	    this.version = version; 
	    this.part_list = part_list;
	    this.part = part;
	    
	} 
	
	@XmlAttribute  
	public double getVersion() {  
	    return version;  
	}  
	public void setVersion(int version) {  
	    this.version = version;  
	}  
	
	@XmlElement(name="part-list")
	public Part_List getPartList() {  
	    return part_list;  
	}  
	public void setPartList(Part_List part_list) {  
	    this.part_list = part_list;  
	}  
	
	@XmlElement
	public Part getPart() {  
	    return part;  
	}  
	public void setPart(Part part) {  
	    this.part = part;  
	}  
}
