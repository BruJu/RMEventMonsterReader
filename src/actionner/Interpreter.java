package actionner;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import decrypter.Decrypter;

public class Interpreter {
	private Decrypter decrypter;
	
	public Interpreter (ActionMaker actionMaker) {
		decrypter = new Decrypter(actionMaker);
	}
	
	public void inputFile(File file) throws IOException {
		FileReader fileReader = new FileReader(file);
		BufferedReader buffer = new BufferedReader(fileReader);
		
		while (true) {
			String line = buffer.readLine();
			
			if (line == null) {
				break;
			}
			
			interpreter(line);
		}
		
		buffer.close();
	}
	
	public void inputLines(List<String> lines) {
		for (String line : lines) {
			interpreter(line);
		}
	}

	private void interpreter(String line) {
		decrypter.decript(line);
	}
}
