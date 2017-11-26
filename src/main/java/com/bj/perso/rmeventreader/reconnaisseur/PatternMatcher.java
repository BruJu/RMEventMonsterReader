package com.bj.perso.rmeventreader.reconnaisseur;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PatternMatcher {
	
	
	public String[] recognize(String ligne, List<Pattern> patterns) {
		String[] result = null;
		
		for (Pattern pattern : patterns) {
			result = recognize(ligne, pattern);
			
			if (result != null)
				return result;
		}
		
		return null;
	}
	

	private String[] recognize(String ligne, Pattern p) { 
		Matcher matcher = p.matcher(ligne);
		
		if (!matcher.find())
			return null;
		
		ArrayList<String> strings = new ArrayList<>();
		
		for (int i = 0 ; i <= matcher.groupCount() ; i++) {
			strings.add(matcher.group(i));
		}
		
		return (String[]) strings.toArray();
	}
}

