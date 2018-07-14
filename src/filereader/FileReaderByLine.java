package filereader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class FileReaderByLine {
	public static void lireLeFichier(File file, ActionOnLine actionOnLine) throws IOException {
		FileReader fileReader = new FileReader(file);
		BufferedReader buffer = new BufferedReader(fileReader);
		String line;
		
		while (true) {
			line = buffer.readLine();
			
			if (line == null) {
				break;
			}
			
			actionOnLine.read(line);
		}
		
		buffer.close();
	}
}
