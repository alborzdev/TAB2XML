package xmlClasses;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlType(propOrder={"line","sign"})
public class Clef {
	private String sign;
	private int line;
	
	public Clef() {}  
	
	public Clef(String sign, int line) {    
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
	
	@XmlElement(name="line")
	public int getLine() {  
	    return line;  
	}  
	public void setLine(int line) {  
	    this.line = line;  
	}
}
