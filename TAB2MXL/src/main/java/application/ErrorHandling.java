package application;

public class ErrorHandling {

//	public static void main(String[] args) {
//		String test1 = "e|-------5-7-----7-|-8-----8-2-----2-|-0---------0-----|-----------------|\r\n"
//				+ "B|-----5-----5-----|---5-------3-----|---1---1-----1---|-0-1-1-----------|\r\n"
//				+ "G|---5---------5---|-----5-------2---|-----2---------2-|-0-2-2---2-------|\r\n"
//				+ "D|-7-------6-------|-5-------5-------|-3---------------|-----------------|\r\n"
//				+ "A|-----------------|------------|-----------------|-2-0-0---0---8-7-|\r\n"
//				+ "E|-----------------|-----------------|-----------------|-----------------|";
//		String test2 = "------------";
//		
//		//String[] str = test1.split("|", 0);
//		
//		int[] ans = findNeedle(test1,"|"+test2+"|");
//		System.out.println("Start at:"+ans[0]+"\nEnd at:"+ans[1]);
//		
//	}
	
	
	public static int[] findNeedle(String hay, String needle) {
		//if no answer
		if( !hay.contains(needle) ) return null;
		//output
		int[] indices = {0,0};
		
		for(int i = 0; i < hay.length()-needle.length(); i++) {
			//System.out.println(i);
			if(hay.charAt(i)==needle.charAt(0)) {
				//System.out.println("firstchar");
				for(int j = 0; j < needle.length(); j++) {
					//System.out.println(i+" "+hay.substring(i,j+i+1)+" "+(i+j));
					if( needle.equals( hay.substring(i,j+i+1) ) ) {
						System.out.println("FOUND");
						indices[0]=i;
						indices[1]=j+i;
					}
				}
			}
		}
		
		//return
		return indices;
	}
	
}
