package com.adserving.sitevars;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Scanner;

public class ExpressionTest {
	public static void main(String[] args) {
		HashMap<Integer, CampaignDao> m = new HashMap<>();

		/*----------valid operators : 
		 *  1. >
		 *  2. <
		 *  3. =
		 *  4. !=
		 *  5. .contains
		 *  6. regex
		 *  7. sw -- starts with 
		 *  8. ew -- ends with 
		 *  
		 *  -------valid Expression seperator 
		 *  1. and
		 *  2. or
		 */
	
		try (BufferedReader br = new BufferedReader(new FileReader(args[0]))) {
		    String line;
		    while ((line = br.readLine()) != null) {
		      String s[]=line.split("&&");
		      m.put(Integer.parseInt(s[0]), new CampaignDao(s[1],Integer.parseInt(s[0] )));
		    }
		}catch (Exception e) {
			System.out.println("file not found");
			System.exit(0);
		}
		  String cont="";
		do{
		
			Scanner sc=new Scanner(System.in);
		   System.out.println("Enter request data");  
		   String data=sc.nextLine();  
		   long startTime = System.currentTimeMillis();
		ExpressionData requestData = new ExpressionData(data);
		
		
		for(Entry<Integer,CampaignDao> entry:m.entrySet()){                                                                                                                        
		try {
			System.out.println(entry.getKey()+":"+ m.get(entry.getKey()).checkAvailability(requestData));
		} catch (Exception e) {
			e.printStackTrace();
		}
		}
		long endTime   = System.currentTimeMillis();
		long totalTime = endTime - startTime;
		System.out.println("Total time : "+ totalTime + " ms");
		Scanner sc1=new Scanner(System.in);
		 System.out.println("continue(Y/N)?");  
		 cont=sc1.next();   
	}while("y".equalsIgnoreCase(cont));
		System.out.println("Bye..! ");
	}
}
