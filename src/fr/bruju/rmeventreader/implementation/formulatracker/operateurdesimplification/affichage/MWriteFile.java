package fr.bruju.rmeventreader.implementation.formulatracker.operateurdesimplification.affichage;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

import fr.bruju.rmeventreader.implementation.formulatracker.contexte.Attaques;
import fr.bruju.rmeventreader.implementation.formulatracker.exploitation.Maillon;

public class MWriteFile implements Maillon {

	@Override
	public void traiter(Attaques attaques) {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());

		
		File f = new File("sorties/sortie" + sdf.format(timestamp) + ".txt");
		
		try {
			f.createNewFile();
			FileWriter ff = new FileWriter(f);
			attaques.liste.forEach(a -> {
				try {
					ff.write(a.chaineAAfficher + "\n");
				} catch (IOException e) {
					e.printStackTrace();
				}
			});
			
			ff.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
	}


}
