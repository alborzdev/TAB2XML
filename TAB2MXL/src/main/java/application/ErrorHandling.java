package application;

public class ErrorHandling {

	public static void main(String[] args) {
		String test1 = "qwertyuio";
		String test2 = "rty";
		
		int[] ans = findNeedle(test1,test2);
		System.out.println("Start at:"+ans[0]+"\nEnd at:"+ans[1]);
		
	}
	
	
	
	public static int[] findNeedle(String hay, String needle) {
		//if no answer
		if( !hay.contains(needle) ) return null;
		//output
		int[] indices = new int[2];
		
		for (int i = 0; i < hay.length; i++) {
			if () {
				
			}
		}
		
		//return
		return indices;
	}
	
}
