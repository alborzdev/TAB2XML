package xmlClasses;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

public class Key {
	private int fifths;
	
	public Key() {}  
	
	public Key(int fifths) {  
	    this.fifths = fifths;  
	} 
	
	@XmlElement
	public int getFifths() {  
	    return fifths;  
	}  
	public void setFifths(int fifths) {  
	    this.fifths = fifths;  
	}
}
