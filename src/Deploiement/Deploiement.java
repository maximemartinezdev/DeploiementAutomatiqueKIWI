package Deploiement;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import org.apache.commons.io.FileUtils;

/**
 * 
 * @author MMARTINEZ
 *
 */
public class Deploiement {

	private static final String CHEMIN_DEPLOIEMENT = "";
	private static final String CHEMIN_DEPLOIEMENT_TMP = Deploiement.CHEMIN_DEPLOIEMENT + File.separator + "tmp";
	private static final String CHEMIN_SAUVEGARDE = "";
	private static final String LIGNE_COMMANDE = "cd " + Deploiement.CHEMIN_DEPLOIEMENT + " -exec npm install; -exec exec";
	

	private File deploiement;
	private File sauvegarde;
	private File sauvegardeDeploiement = new File("dateDeploiemnt.txt");
	private Date dateDernierDeploiement;


	public Deploiement() {
		this.deploiement = new File(Deploiement.CHEMIN_DEPLOIEMENT);
		this.sauvegarde = new File(Deploiement.CHEMIN_SAUVEGARDE);
		try {
			this.recupererDateDernierDeploiement();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Fonction qui permet de récupérer la dernière date de déploiement
	 * @throws IOException
	 */
	private void recupererDateDernierDeploiement() throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(this.sauvegardeDeploiement));
	    String sCurrentLine;
		String lastLine = "";
		while ((sCurrentLine = br.readLine()) != null) 
	    {
	        lastLine = sCurrentLine;
	    }
		if(!lastLine.isEmpty()) {
			this.dateDernierDeploiement = new Date(lastLine);
		}
	}

	/**
	 * Fonction qui permet de sauvegarde l'ancienne solution déployé
	 * @return
	 * @throws IOException
	 */
	public boolean sauvegarderAncienneSolution() throws IOException {
		boolean result = false;
		if (this.deploiement.isDirectory() && this.deploiement.exists() && this.sauvegarde.isDirectory()
				&& this.sauvegarde.exists()) {
			File fileNameCopy = new File(
					this.sauvegarde + File.separator + this.deploiement.getName() + " - " + System.currentTimeMillis());
			if (fileNameCopy.mkdirs()) {
				FileUtils.copyDirectoryToDirectory(this.deploiement, fileNameCopy);
				result = true;
			}
		}
		return result;
	}

	/**
	 * Fonction qui permet de supprimer l'ancienne solution
	 * @throws IOException
	 */
	public void supprimerAncienneSolution() throws IOException {
		FileUtils.deleteDirectory(this.deploiement);
	}

	/**
	 * Fonction qui permet de copier les fichiers avant de les déployer
	 * @param files
	 * @throws IOException
	 */
	public void copieFichier(List<File> files) throws IOException {
		Objects.requireNonNull(files, "files cannot be null");
		this.deploiement.mkdirs();
		if (!files.isEmpty()) {
			for (File file : files) {
				File tmp = new File(CHEMIN_DEPLOIEMENT_TMP);
				tmp.mkdirs();
				FileUtils.copyFileToDirectory(file, tmp);
				this.enregistrerResultat();
			}			
		}
	}
	
	/**
	 * Fonction qui permet de deployer les fichiers
	 * @throws Exception 
	 */
	public void deployer() throws Exception {
		this.build();
		File fichierDeploiement = new File(this.CHEMIN_DEPLOIEMENT_TMP + File.separator + "dist");
		if(!fichierDeploiement.exists() || fichierDeploiement.isFile()) {
			throw new Exception("le deploiement n'a pas fonctionné");
		}
		FileUtils.copyDirectoryToDirectory(fichierDeploiement, this.deploiement);
		FileUtils.deleteDirectory(new File(CHEMIN_DEPLOIEMENT_TMP));
	}
	
	/**
	 * Fonction qui a pour but d'executer les commandes de deploiement et generer le dossier dist
	 * @throws IOException
	 */
	private void build() throws IOException {
		Process p = Runtime.getRuntime().exec(new String[]{"bash","-c",LIGNE_COMMANDE});
	}

	/**
	 * Fonction qui permet d'enregister la date de déploiement dans le fichier
	 * @throws IOException
	 */
	private void enregistrerResultat () throws IOException {
	    Files.write(Paths.get(this.sauvegardeDeploiement.getAbsolutePath()), (new Date().toString() + "\n\r").getBytes(), StandardOpenOption.APPEND);
	}
	
	public Date getDateDernierDeploiement() {
		return dateDernierDeploiement;
	}

	public void setDateDernierDeploiement(Date dateDernierDeploiement) {
		this.dateDernierDeploiement = dateDernierDeploiement;
	}
}
