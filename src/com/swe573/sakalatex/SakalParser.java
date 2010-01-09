package com.swe573.sakalatex;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.swe573.sakalatex.outline.SakalOutlineNode;

public class SakalParser {

	public static int getRowNumber(String pattern, String content){
		
		String[] strArr = content.split("\n");
		
		for(int i=0;i<strArr.length;i++){
			if(strArr[i].contains(pattern)){
				return i;
			}
		}
		return -1;
	}
	
	public static List<SakalOutlineNode> parse(String filename){
		FileInputStream fi = null;
		List<SakalOutlineNode> ll = null;
		SakalOutlineNode son = null;
		
		String groupStr;
		int rowNumber;
		
		try{
			ll = new ArrayList<SakalOutlineNode>();
			String fileContent = Activator.readFile(filename);
			
			
			//Patter
			CharSequence inputStr = fileContent;
			String sectionPatternStr = "\\\\section\\{(.*)\\}|\\\\subsection\\{(.*)\\}|\\\\subsubsection\\{(.*)\\}";
			
			// Compile and use regular expression
			Pattern pattern = Pattern.compile(sectionPatternStr);
			Matcher matcher = pattern.matcher(inputStr);
			boolean matchFound;
			while(matchFound=matcher.find()){
				
				if(!matchFound) break;
				for(int i=1;i<=3;i++){
					
					if(matcher.group(i) == null) continue;
					
					rowNumber = getRowNumber(matcher.group(0),fileContent);
					groupStr = matcher.group(i);
					
					son = new SakalOutlineNode(groupStr, 1, rowNumber);
					ll.add(son);
					
				}
			}
		}		
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		finally
		{
			if(fi != null)
				try {
					fi.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
		
		return ll;
	}
}
