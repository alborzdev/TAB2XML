package xmlClasses;

import javax.xml.bind.annotation.XmlAccessorType;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlValue;
import javax.xml.bind.annotation.*;

@XmlAccessorType(XmlAccessType.FIELD)
public class Beam {
	@XmlAttribute
	private int number;
	@XmlValue
	private String beam;
	
	public Beam() {}  
	
	public Beam(int number, String begin) {  
	    this.number = number;
	    this.beam = begin;
	} 
	
	
	public int getNumber() {  
	    return number;  
	}  
	
	public void setNumber(int number) {  
	    this.number = number; 
	}

	public String getBegin() {  
	    return beam;  
	}  
	
	public void setBegin(String begin) {  
	    this.beam = begin;  
	}
}
