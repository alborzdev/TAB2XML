package xmlClasses;

import javax.xml.bind.annotation.XmlAttribute;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

public class Creator {
	
	private String type;
	private String name;
	
	public Creator() {}  
	
	public Creator(String type, String name) {  
	    super();  
	    this.type = type;  
	    this.name = name; 
	} 
	
	@XmlAttribute
	public String getType() {  
	    return type;  
	}  
	public void setType(String type) {  
	    this.type = type;  
	}
	
	@XmlElement
	public String getName() {  
	    return name;  
	}  
	public void setName(String name) {  
	    this.name = name;  
	}
}
