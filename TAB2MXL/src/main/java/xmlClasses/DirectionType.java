package xmlClasses;

import com.sun.xml.txw2.annotation.XmlElement;

public class DirectionType {
	private String words;
	
	public DirectionType() {
		
	}
	
	public DirectionType(String words) {
		this.words = words;
	}
	
	@XmlElement
	public String getWords() {  
	    return words;  
	}  
	public void setWords(String loc) {  
	    this.words = words;  
	}
	
}
