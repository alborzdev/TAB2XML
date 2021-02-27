package xmlClasses;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

public class ScoreInstrument {
	
public class ScorePartDrum {
	
	private String id;
	private String instName;

	public ScorePartDrum() {}  
	
	public ScorePartDrum(String id, String instName) { 
	    this.id = id;  
	    this.instName = instName;
	} 
	
	@XmlAttribute  
	public String getId() {  
	    return id;  
	}  
	public void setId(String id) {  
	    this.id = id;  
	} 
	
	@XmlElement(name="instrument-name")
		public String getInstName() {  
		    return instName;  
		}  
		public void setInstName(String instName) {  
		    this.instName = instName;  
		}  
	}

}
