package xmlClasses;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

public class Beam {
	private int number;
	private String begin;
	
	public Beam() {}  
	
	public Beam(int number, String begin) {  
	    this.number = number;
	    this.begin = begin;
	} 
	
	@XmlAttribute
	public int getNumber() {  
	    return number;  
	}  
	
	public void setNumber(int number) {  
	    this.number = number;  
	}
	
	@XmlElement
	public String getBegin() {  
	    return begin;  
	}  
	
	public void setBegin(String begin) {  
	    this.begin = begin;  
	}
}
