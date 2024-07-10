# Projet Tron Multijoueur

## Étude des performances des algorithmes Maxn et Paranoid pour la prise de décision dans le jeu de Tron

### Équipe de développement :
- A. Catherine ATTY
- Sékou DOUMBOUYA
- Manne Emile KITSOUKOU
- Amirath Fara OROU-GUIDOU

### Parcours :
Licence 3 Informatique, Groupe : 2B

### Supervision :
LEHEMBRE Etienne

### Date :
7 avril 2023

## Table des matières
1. [Introduction](#introduction)
2. [Jeu de Tron](#jeu-de-tron)
3. [Problématique et hypothèses de recherche](#problématique-et-hypothèses-de-recherche)
4. [État de l'art : théorie des jeux et Recherche adversarial](#état-de-lart-théorie-des-jeux-et-recherche-adversarial)
5. [Organisation et planification](#organisation-et-planification)
6. [Analyse et Démonstration des algorithmes](#analyse-et-démonstration-des-algorithmes)
7. [Architecture du projet](#architecture-du-projet)
8. [Expérimentations et Évaluation](#expérimentations-et-évaluation)
9. [Conclusion et perspectives](#conclusion-et-perspectives)
10. [Annexes](#annexes)
11. [Bibliographie](#bibliographie)

## Introduction
La théorie des jeux et l'intelligence artificielle sont deux domaines interconnectés qui ont connu une évolution fulgurante ces dernières années. Le mariage entre ces deux domaines a conduit à de nombreuses applications, notamment les systèmes multi-agents et l'apprentissage par renforcement.

Dans ce projet, nous avons utilisé la théorie des jeux et l'intelligence artificielle pour mettre en œuvre et étudier le jeu de Tron avec plusieurs joueurs et différentes stratégies. Le but du jeu est d'être le dernier joueur debout en évitant les collisions avec les murs, les bords et les trajectoires des adversaires.

## Jeu de Tron
### Description du jeu
Le jeu de Tron se joue sur une grille de taille N x M. Chaque joueur contrôle un point qui se déplace sur la grille et laisse derrière lui un mur infranchissable. Le but est d'être le dernier joueur en évitant les collisions.

![Jeu de Tron](rapport/Tmages/jeu-tron.png)

### Analyse mathématique du jeu
Le jeu se joue sur une grille où chaque cellule peut être vide ou occupée par un joueur. Chaque joueur a trois choix de mouvement : continuer tout droit, tourner à gauche ou tourner à droite. Le jeu se termine soit par la victoire d'un joueur, soit par une égalité si tous les joueurs sont éliminés.

## Problématique et hypothèses de recherche
### Problématique de recherche
Le principal défi du jeu de Tron est d'anticiper les mouvements des adversaires et de concevoir des stratégies efficaces. Ce projet vise à étudier l'efficacité de différents algorithmes de jeu en fonction de divers facteurs tels que la taille de la grille et le nombre d'adversaires.

### Hypothèses de recherche
- La performance des stratégies s'améliore avec la profondeur de recherche.
- L'efficacité des algorithmes diminue avec l'augmentation du nombre d'adversaires.
- Il existe une taille maximale de grille au-delà de laquelle les performances des stratégies diminuent.

## État de l'art : théorie des jeux et Recherche adversarial
La théorie des jeux et l'intelligence artificielle ont été appliquées au jeu de Tron pour développer des stratégies de jeu optimales. Les recherches se sont concentrées sur des algorithmes tels que MCTS, minimax, alpha-beta et negamax, ainsi que sur l'utilisation d'heuristiques pour améliorer l'efficacité des algorithmes.

## Organisation et planification
### Étapes du projet
1. **Planification** : Définition des questions de recherche, formulation des hypothèses, recherche bibliographique, choix de l'approche méthodologique et modélisation du jeu.
2. **Implémentation et Développement** : Développement du jeu, implémentation des algorithmes et préparation des données et des expériences.
3. **Évaluation et Analyse** : Analyse des résultats et interprétation des résultats.

### Gestion du projet
Chaque membre de l'équipe a été assigné à une tâche spécifique :
- **Manne** : Implémentation du modèle de jeu et des heuristiques.
- **Amirath** : Implémentation des algorithmes de recherche adversarial (Maxn et Paranoid).
- **Catherine** : Implémentation des algorithmes de recherche et mise en place de la plateforme de test.
- **Sékou** : Réalisation de l'interface graphique.

### Planification du travail
![Diagramme de Gantt](images/gantt_chart.png)

## Analyse et Démonstration des algorithmes
### OpenSpace
L'heuristique OpenSpace est basée sur le nombre de cases vides autour d'un joueur. Elle évalue la qualité de la position d'un joueur en fonction des cases vides adjacentes.

![Algorithme OpenSpace](images/openspace_algorithm.png)

### GASLAP
GASLAP (Go As Long As Possible) calcule la distance maximale qu'un joueur peut parcourir dans une direction donnée sans rencontrer d'obstacles.

![Algorithme GASLAP](images/gaslap_algorithm.png)

### Voronoï
Le diagramme de Voronoï divise l'espace en régions distinctes en fonction de la proximité de points spécifiques. Nous l'utilisons pour déterminer les zones d'influence des joueurs.

![Algorithme Voronoï](images/voronoi_algorithm.png)

### Checker
L'algorithme Checker utilise une grille de cases blanches et noires pour optimiser la zone d'influence des joueurs en réduisant le surplus de cases.

## Architecture du projet
Notre projet est basé sur l'architecture logicielle MVC (Modèle-Vue-Contrôleur). Il est divisé en quatre modules principaux :
- **Modèle** : Contient les classes représentant les éléments du jeu.
- **Vue** : Responsable de l'affichage graphique et des interactions utilisateur.
- **Core** : Contient les classes exécutables pour lancer le jeu.
- **Algorithms** : Contient les algorithmes de recherche adversarial et les heuristiques.

![Architecture du projet](images/project_architecture.png)

## Expérimentations et Évaluation
### Cas d'utilisation testés
Nous avons testé différentes configurations d'algorithmes pour évaluer leurs performances dans un jeu à trois joueurs.

![Tableau des cas d'utilisation testés](images/test_cases.png)

### Méthodologie
Nous avons répété les expériences au moins 10 fois pour chaque configuration d'algorithme afin d'obtenir une valeur moyenne précise des performances.

### Résultats des expérimentations et analyse
Les résultats montrent que l'augmentation de la profondeur de recherche améliore les performances jusqu'à un certain point, au-delà duquel les performances diminuent en raison de la complexité informatique.

![Courbe de performance](images/performance_curve.png)

## Conclusion et perspectives
Nos expériences ont montré que l'algorithme Paranoid est un concurrent de taille pour les jeux multijoueurs, surpassant l'algorithme Maxn dans certains cas. Des recherches futures pourraient étudier les performances de ces algorithmes dans d'autres jeux multijoueurs.

## Annexes
### Table des figures
1. Jeu de Tron
2. Modèle d'interaction entre 3 joueurs
3. Diagramme de Gantt
4. Architecture du projet
5. Diagramme de classe du modèle
6. Diagramme de classe de la classe Move
7. Diagramme de classe de la classe State
8. Popup de fin de partie
9. Fenêtre contextuelle
10. Fenêtre principale du jeu
11. Diagramme de classe du package algorithms
12. Courbe de performance de l'algorithme Maxn en fonction de la profondeur

### Bibliographie
- Hans Leo Bodlaender, Antonius Jacobus Johannes Kloks, et al. Fast algorithms for the tron game on trees. 1990.
- Jean-Baptiste Boin. CS221 project progress-light cycle racing in tron.
- Niek GP Den Teuling and Mark HM Winands. Monte-carlo tree adversarialsearch for the simultaneous move game tron. Univ. Maastricht, Netherlands, Tech. Rep, 2011.
- Sturtevant Nathan. A comparison of algorithms for multi-player games - university of alberta.
- Pierre Perick, David L St-Pierre, Francis Maes, and Damien Ernst. Comparison of different selection strategies in monte-carlo tree search for the game of tron. In 2012 IEEE Conference on Computational Intelligence and Games (CIG), pages 242–249. IEEE, 2012.
- Andy Sloane. Google ai challenge post-mortem, Mar 2010.
