package xmlClasses;

import java.io.FileOutputStream;  
import java.util.ArrayList;  
  
import javax.xml.bind.JAXBContext;  
import javax.xml.bind.Marshaller;  
  
 
public class ObjectToMxl {  
public static void main(String[] args) throws Exception{  
    JAXBContext contextObj = JAXBContext.newInstance(Score_Partwise.class);  
  
    Marshaller marshallerObj = contextObj.createMarshaller();  
    marshallerObj.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);  
    
    Score_Part sp = new Score_Part("P1", "Music");
    
    Part_List pl = new Part_List(sp);  
    
    Key k = new Key(0);
    Time t = new Time(4, 4);
    Clef c = new Clef("G", 2);
    Attributes att = new Attributes(1, k, t, c);
    
    Pitch pi = new Pitch("C", 4);
    Note n = new Note(4, "whole", pi);
    
    Measure m = new Measure(1, att, n);
    ArrayList <Measure> measures = new ArrayList<Measure>();
    measures.add(m);
    Part p = new Part("P1", measures);
    		
    Score_Partwise spw = new Score_Partwise(3.1, pl, p);  
    marshallerObj.marshal(spw, new FileOutputStream("musicTest.xml"));  
       
}  
}  