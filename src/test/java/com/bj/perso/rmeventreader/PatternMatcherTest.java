package com.bj.perso.rmeventreader;

import static org.junit.Assert.*;

import org.junit.Test;

import com.bj.perso.rmeventreader.reconnaisseur.DataType;
import com.bj.perso.rmeventreader.reconnaisseur.PatternMatcher;

public class PatternMatcherTest {

	//@Test
	public void testLitteral() {
		PatternMatcher patternMatcher = new PatternMatcher();
		
		String pattern = "chameau";
		DataType[] dataTypes = new DataType[] {};
		
		assertNotNull(patternMatcher.filtrer(pattern, dataTypes, "chameau"));
		assertNull(patternMatcher.filtrer(pattern, dataTypes, "poney"));
	}

	@Test
	public void testJoker() {
		PatternMatcher patternMatcher = new PatternMatcher();
		
		String pattern = "_ et";
		DataType[] dataTypes = new DataType[] {DataType.IGNORE};
		
		assertEquals(patternMatcher.filtrer(pattern, dataTypes, "hamster et").get(DataType.IGNORE), "hamster");
		assertEquals(patternMatcher.filtrer(pattern, dataTypes, "poney et").get(DataType.IGNORE), "poney");
	}
}
