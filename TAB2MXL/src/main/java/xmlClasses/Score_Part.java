package xmlClasses;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

public class Score_Part {
	
private String id;
private String part_name;

	public Score_Part() {}  
	
	public Score_Part(String id, String part_name) { 
	    this.id = id;  
	    this.part_name = part_name;
	} 
	
	@XmlAttribute  
	public String getId() {  
	    return id;  
	}  
	public void setId(String id) {  
	    this.id = id;  
	} 
	
	@XmlElement(name="part-name")
	public String getPartName() {  
	    return part_name;  
	}  
	public void setPartName(String part_name) {  
	    this.part_name = part_name;  
	}  
	
	
}
