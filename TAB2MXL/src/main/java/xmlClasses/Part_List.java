package xmlClasses;

import javax.xml.bind.annotation.XmlAttribute;

import javax.xml.bind.annotation.XmlRootElement;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "part-list")
public class Part_List {
private Score_Part score_part;
	
	public Part_List() {}  
	
	public Part_List(Score_Part score_part) {  
	    super();  
	    this.score_part = score_part;  
	} 
	
	@XmlElement(name="score-part")
	public Score_Part getScorePart() {  
	    return score_part;  
	}  
	public void setScorePart(Score_Part score_part) {  
	    this.score_part = score_part;  
	}  
}
