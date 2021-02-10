package xmlClasses;

import javax.xml.bind.annotation.XmlElement;

public class Work {
	private static String work_title;
	
	public Work() {}  
	
	public Work(String work_title) {  
	    Work.work_title = work_title;  
	} 
	
	@XmlElement(name="work-title")
	public static String getWorkTitle() {  
	    return work_title;  
	}  
	public void setWorkTitle(String work_title) {  
	    Work.work_title = work_title;  
	}
	
}
