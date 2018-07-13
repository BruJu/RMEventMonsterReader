package monsterlist;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import decrypter.Recognizer;

public class Correspondeur {
	private Map<String, String> map = new HashMap<>();
	
	public boolean lireFichier(File file) throws IOException {
		FileReader fileReader = new FileReader(file);
		BufferedReader buffer = new BufferedReader(fileReader);
		String pattern = "_ _";
		
		while (true) {
			String line = buffer.readLine();
			
			if (line == null) {
				break;
			}
			
			
			List<String> chaines = Recognizer.tryPattern(pattern, line);
			
			if (chaines == null) {
				buffer.close();
				return false;
			}
			
			map.put(chaines.get(0), chaines.get(1));
		}
		
		buffer.close();
		
		return true;
	}
	
	public String get(String cle) {
		return map.get(cle);
	}
	
	public String get(int cle) {
		return map.get(Integer.toString(cle));
	}
	
}
