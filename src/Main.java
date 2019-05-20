import java.io.IOException;
import java.util.Date;

import Deploiement.Deploiement;
import git.Git;

public class Main {

	public static void main(String[] args) throws IOException, Exception {
		// TODO Auto-generated method stub
		Git git = new Git();
		Deploiement deploiement = new Deploiement();
		if(deploiement.sauvegarderAncienneSolution()) {
			deploiement.supprimerAncienneSolution();
			deploiement.copieFichier(git.getFichierDernierCommit(deploiement.getDateDernierDeploiement()));
			deploiement.deployer();
		}
	}

}
