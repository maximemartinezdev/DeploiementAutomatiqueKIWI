package git;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang.NotImplementedException;
import org.eclipse.egit.github.core.Commit;
import org.eclipse.egit.github.core.Repository;
import org.eclipse.egit.github.core.RepositoryCommit;
import org.eclipse.egit.github.core.client.GitHubClient;
import org.eclipse.egit.github.core.service.CommitService;
import org.eclipse.egit.github.core.service.RepositoryService;


/**
 * 
 * @author MMARTINEZ
 *
 */
public class Git {

	private static final String REPO_NAME = "";
	private static final String BRANCHE = "";
	private static final String USER = "";
	private static final String PASSWORD = "";
	
	private GitHubClient client;
	
	public Git () {
		 this.client = new GitHubClient();
		 this.client.setCredentials(Git.USER, Git.PASSWORD);
	}
	
	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public Repository getRepository() throws Exception {
        RepositoryService service = new RepositoryService(client);
        List<Repository> listRepo = service.getRepositories().stream().filter(r -> r.getName().equals(Git.REPO_NAME)).collect(Collectors.toList());  
        if(listRepo.isEmpty()) {
        	throw new Exception ("Le répertoire " + Git.REPO_NAME + " n'existe pas !");
        }
        return listRepo.get(0);
	}
	
	/**
	 * Fonction qui permt de savoir si un commit a été effectué arpès le dernier deploiement
	 * @param dateDerniereMiseEnProd
	 * @return
	 * @throws IOException
	 * @throws Exception
	 */
	public boolean hasCommit(Date dateDerniereMiseEnProd) throws IOException, Exception {
		return this.hasCommit(dateDerniereMiseEnProd, this.getRepository());
	}
	
	/**
	 * Fonction qui permt de savoir si un commit a été effectué arpès le dernier deploiement
	 * @param dateDerniereMiseEnProd
	 * @param repo
	 * @return
	 * @throws IOException
	 */
	private boolean hasCommit (Date dateDerniereMiseEnProd, Repository repo) throws IOException {
		CommitService commitService = new CommitService(this.client);
		List<RepositoryCommit> listCommit = commitService.getCommits(repo).stream().filter(c -> c.getCommit().getAuthor().getDate().after(dateDerniereMiseEnProd)).collect(Collectors.toList());  
		return !listCommit.isEmpty();
	}
	
	/**
	 * Fonction qui de récupérer le dernier commit après le dernier déploiement 
	 * @param dateDerniereMiseEnProd
	 * @return
	 * @throws Exception
	 */
	public RepositoryCommit getDernierCommit(Date dateDerniereMiseEnProd) throws Exception {
		return this.getDernierCommit(dateDerniereMiseEnProd, this.getRepository());
	}
	
	/**
	 * 
	 * @param dateDerniereMiseEnProd
	 * @param repo
	 * @return
	 * @throws IOException
	 */
	private RepositoryCommit getDernierCommit(Date dateDerniereMiseEnProd, Repository repo) throws IOException {
		RepositoryCommit result = null;
		if(this.hasCommit(dateDerniereMiseEnProd, repo)) {
			CommitService commitService = new CommitService(this.client);
			List<RepositoryCommit> listCommit = commitService.getCommits(repo).stream().filter(c -> c.getCommit().getAuthor().getDate().after(dateDerniereMiseEnProd)).collect(Collectors.toList());
			for (RepositoryCommit repositoryCommit : listCommit) {
				if(result == null || repositoryCommit.getCommit().getAuthor().getDate().after(result.getCommit().getAuthor().getDate())) {
					result = repositoryCommit;
				}
			}
		}		
		return result;
	}
	
	/**
	 * Fonction qui peret de récupérer les fichiers de la branche de déploiement
	 * @param dateDerniereMiseEnProd
	 * @return
	 * @throws Exception
	 */
	public List<File> getFichierDernierCommit (Date dateDerniereMiseEnProd) throws Exception {
		return this.getFichierDernierCommit(dateDerniereMiseEnProd, this.getRepository());
	}
	
	/**
	 * Fonction qui peret de récupérer les fichiers de la branche de déploiement
	 * @param dateDerniereMiseEnProd
	 * @param repo
	 * @return
	 * @throws IOException
	 */
	private List<File> getFichierDernierCommit (Date dateDerniereMiseEnProd, Repository repo) throws IOException {
		throw new NotImplementedException();
	}
}
