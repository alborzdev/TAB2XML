package xmlClasses;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

public class Clef {
	private String sign;
	private int line;
	
	public Clef() {}  
	
	public Clef(String sign, int line) {  
	    super();  
	    this.sign = sign;  
	    this.line = line; 
	} 
	
	@XmlElement
	public String getSign() {  
	    return sign;  
	}  
	public void setSign(String sign) {  
	    this.sign = sign;  
	}
	
	@XmlElement
	public int getTime() {  
	    return line;  
	}  
	public void setTime(int line) {  
	    this.line = line;  
	}
}
