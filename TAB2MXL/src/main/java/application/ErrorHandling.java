package application;

public class ErrorHandling {

//	public static void main(String[] args) {
//		String test1 = "helloasdhello";
//		String test2 = "hello";
//		
//		int[] ans = findNeedle(test1,test2);
//		System.out.println("Start at:"+ans[0]+"\nEnd at:"+ans[1]);
//		
//	}
	
	
	
	public static int[] findNeedle(String hay, String needle) {
		//if no answer
		if( !hay.contains(needle) ) return null;
		//output
		int[] indices = {0,0};
		
		for(int i = 0; i < hay.length(); i++) {
			if(hay.charAt(i)==needle.charAt(0)) {
				for(int j = i; j < needle.length()+i; j++) {
					//System.out.println(i+" "+hay.substring(i,j+1)+" "+j);
					if( needle.equals( hay.substring(i,j+1) ) ) {
						indices[0]=i;
						indices[1]=j;
					}
				}
			}
		}
		
		//return
		return indices;
	}
	
}
