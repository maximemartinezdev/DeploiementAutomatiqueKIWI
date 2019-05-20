# DeploiementAutomatiqueKIWI

# Solution 1

## Explication

  Attention le projet n'est pas fonctionnel ! et va être sujet à des modifications/améliorations !
  
  Le but de ce projet était d'utiliser l'api JGIT en Java afin d'automatiser le déploiement.
  
  Le projet s'éxécute sur le serveur de qualif (via la création d'un JAR ) récursivement (une fois par jour) avec la commande : 
    
    0 0 * * * java -jar "le nom mon jar"
    
   Le projet permet d'éffectuer chaque soir les tâches suivantes :
   - Vérifier si une commit a été réalisé depuis le dernier déploiement (branche + répo)
   - Si besoin récupérer le nouveau code
   - Sauvegarder l'ancienne solution
   - Supprimer l'ancien projet déployé
   - Copié le nouveau projet dans le dossier de déploiement 
   - "Compiler" le projet grunt
   - Supprimer tous les fichiers/dossiers sauf le dist
   - Sauvegarder la date de déploiement
  
  La JavaDOC est générée dans le dossier /doc
 
## Point negatif
  
  Ce projet est codé de A-Z. Il est donc très dépendant de la personne qui la développée. Il nécéssite certains prérequis :
   - Avoir une jre sur le serveur
   - Avoir node et npm installés
   - Avoir des connaissances en JAVA
   
## Axes d'amélioration

  Le projet n'est que en phase d'alpha voici quelques modifications à apporter :
  - Externaliser le fichier d'historique des déploiements et le rendre configurable
  - Créer des logs dans un fichier afin d'avoir de tracer les actions du programme
  - Commenter le code
  - Contrôler les paramètres d'entrées
  - Créer une architecture plus maintenanble en séparant les fonctionnalités
  - Créer une tâche plannifiée à la place d'un JAR éxécutable et déployer le projet sur un serveur d'application (glassfish...)
  
   
 # Solution 2
 
 AWS permet de créer un pipeline de déploiement continu. Elle récupére le projet sur une branche spécifique d'un projet git
 (example prod) et permet de le déployer sur un serveur.
 
 Cette solution comporte que des points positifs et ne nécéssite pas de développement spécifique. Cependant, il  faudrait téléverser que le dossier "dist" sur la branche Git. 

 Certaines actions comme le grunt build serait encore a réalser manuellement et les commits seraient compliqués.
