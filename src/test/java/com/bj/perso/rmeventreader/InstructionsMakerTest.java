package com.bj.perso.rmeventreader;

import static org.junit.Assert.*;

import org.junit.Test;

import com.bj.perso.rmeventreader.reconnaisseur.DataType;
import com.bj.perso.rmeventreader.reconnaisseur.InstructionsMaker;

import utility.PairList;

public class InstructionsMakerTest {

	@Test
	public void test() {
		InstructionsMaker aa = InstructionsMaker.VOID;
		
		String couple1 = "John et Marie";
		String couple2 = "Poney et Chasse-neige";
		
		PairList<DataType, String> pair = aa.filtrer(couple1);
		
		assertEquals(pair.get(DataType.IGNORE), "John");
		assertEquals(pair.get(DataType.IGNORETWO), "Marie");

		pair = aa.filtrer(couple2);
		
		assertEquals(pair.get(DataType.IGNORE), "Poney");
		assertEquals(pair.get(DataType.IGNORETWO), "Chasse-neige");
		
	}

}
