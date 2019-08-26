package test;

import java.util.StringTokenizer;

public class TokenizerTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub	
		String str = "안녕 하세 요";
		StringTokenizer stn = new StringTokenizer(str, " ");
		
		String token;
		
		while(stn.hasMoreTokens()) {
			token = stn.nextToken();
			System.out.println(token);
		}
	}

}
