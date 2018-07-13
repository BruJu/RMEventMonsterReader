package imagereader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import monsterlist.metier.Monstre;

public class BuildingMotifs {
	List<Monstre> monstres;
	
	public BuildingMotifs(List<Monstre> monstres) {
		this.monstres = monstres;
		
		ImageReader.remplirMotif();
	}
	
	public void lancer() {
		List<String> dejaVu = new ArrayList<>();
		
		for (Monstre monstre : monstres) {
			if (dejaVu.contains(monstre.name))
				continue;
			
			if (!monstre.name.equals("UNKNOWN_NAME")) {
				String filename = "ressources\\Picture\\" + monstre.name + ".PNG";
						
						
				ImageReader ir = new ImageReader(filename);
				
				try {
					ir.lireImage();
				} catch (IOException e) {
					System.out.println(filename);
					e.printStackTrace();
				}
				
				
				String s = ir.reconnaitre();
				
				ir.afficher();
				System.out.println(monstre.name + " " + s);
				dejaVu.add(monstre.name);
				
				try {
					Thread.sleep(1500);
				} catch (InterruptedException e) {

				}
				
			}
			
		}
		
		
	}
	
}
