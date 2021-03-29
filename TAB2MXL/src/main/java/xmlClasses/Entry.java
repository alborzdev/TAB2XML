package xmlClasses;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementRef;

/* This class is used to hold the object and name of a measure entry, 
 * allowing the measure to hold notes, backups, and forwards */
@XmlJavaTypeAdapter(EntryAdapter.class)
public class Entry {
	private String name;
    private Object value;
    
	public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public Object getValue() {
        return value;
    }
    public void setValue(Object value) {
        this.value = value;
    }
}
