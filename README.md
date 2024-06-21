# Meeting Planner API

## Objectif du Projet

- Ce projet vise à fournir une solution complète de la gestion des salles qui devient trop complexe durant le COVID.
- Facilitant la mis en place d'un utilitaire aux clients dans le suivi et la gestion de leurs réunion.
- En mettant l'accent sur la gestion des locaux qui contiennent au total 4 salles et servent à 100 employés sur site,
- L'application vise à offrir une API conviviale pour améliorer l'efficacité de réservation des salles afin de faire les
  réunion pour chaque matinée

## Aperçu de la Solution

- La solution est développée en Java et met en œuvre une API pour gérer les salles de réunion sur la base des
  spécifications données.
- Vous trouverez ci-dessous les principaux composants et technologies utilisés pour mettre en œuvre la solution.

## Conception

- Diagrammes de classe
  ![img.png](img.png)
  ![img_1.png](img_1.png)

## Spécifications:

- Réservation de salle par créneau de 1 heure, de 8h-20h, tous les week-days.
- Libration des salles d'une 1H avant sa prochaine réservation pour permettre aux agents de nettoyage de désinfecter les
  locaux a cause des restrictions du COVID.

## Fonctionnalités implémentées:

1. les operations CRUD pour les deferentes entites
2. Gestion des Equipements
3. Gestion des Salles
3. Gestion des Reservation
4. Proposition de la meilleure salle pour faire les réunions par ordre d’arrivée pour chaque matinée en fonction du :
    - Nombre de personnes avec optimisation de l'occupation des salles en fonction de la capacité (70%)
    - Type de réunion : VC, SPEC, RS, RC.
    - Ressources (Equipements + Creneau) disponibles du planning.

## Processus:

- L'intersection des specifications donne une resultat qui doit etre triée et retourner le 1er salle disponible afin d'
  eviter le gaspillage des ressources.
- La mise a jour des information de réservation de salle pour eviter la reutilisation avant l'heure de nettoyage

## Architecture

**Modèle MVC (Modèle-Vue-Contrôleur):**

- L’approche en couches permet une meilleure évolution et une meilleure maintenabilité du code pour assurer une
  séparation nette des préoccupations et la modularité.
  ![img_8.png](img_8.png)
    1. **Model:**
        - Les Entités sont souvent utilisés pour implémenter des objets métiers qui seront manipulés par les autres
          couches.
    2. **Repositories:**
        - Les répertoires sont souvent utilisés pour centraliser la logique d'accès à la base de données.
    3. **Services:**
        - Les services sont souvent utilisés pour abstraire la logique métier.
    4. **Controllers:**
        - Les contrôleurs sont souvent utilisés pour contrôler le flux de l'application et des interactions avec
          l’utilisateur.

## Technologies Utilisées

### Backend: Outils de Developement

![img_9.png](img_9.png)

- [ ] **[Spring boot](https://spring.io/projects/spring-boot)**
    - Composant très particulier de Spring Framework qui encourage l'utilisation de bonnes pratiques de programmation.
        - Autoconfiguration automatique de Spring;
        - Starters de dépendances;
        - Endpoints Actuator pour fournir des données sur l’application;

- [ ] **[Spring Data JPA](https://spring.io/projects/spring-data-jpa/)**
    - Partie de la famille Spring Data, facile d’implémenter des dépôts basés sur JPA (Java Persistence API).
        - Facilite la création d’applications alimentées par Spring qui utilisent des technologies d’accès aux données.

- [ ] **[Spring REST Docs](https://spring.io/projects/spring-restdocs)**
    - Documentation des services RESTful.
        - Documentation écrite à la main avec Asciidoctor et des extraits générés automatiquement avec Spring MVC Test.

- [ ] **[Lombok](https://projectlombok.org/)**
    - Bibliothèque Java qui se branche automatiquement dans votre éditeur et construit des outils, pimentant votre Java.
       - N’écrivez plus jamais une autre méthode getter ou equals, 
       - Annotation de la classe pour constructeur complet ou vide, 
       - Automatisation des variables de logging.

- [ ] **[Spring Dev Tools](https://docs.spring.io/spring-boot/reference/using/devtools.html)**
    - Expérience de développement d’applications plus agréable
        - Module peut être inclus dans tout projet pour fournir des fonctionnalités de développement supplémentaires.

- [ ] **[MySQL DB](https://www.mysql.com/)**
    - Système de gestion de base de données relationnelle
        - Base de données open source la plus populaire au monde, pris en charge avec tel application ou langage de
          programmation préféré
      
- [ ] **[H2 Database](https://h2database.com/)**
    - la base de données Java SQL
        - Très rapide, open source, API JDBC 
        - Modes serveur et embarqué; bases de données en mémoire 
        - Application console basée sur navigateur 
        - Faible encombrement : environ 2,5 Mo taille du fichier jar
      
- [ ] **[Apache Maven](https://maven.apache.org/)**
  - Outil de gestion et de compréhension de projets logiciels.
    - Basé sur le concept d’un modèle d’objet de projet (POM);
    - Gestion des dépendances et la construction du projet, les rapports et la documentation à partir d’une information centrale.

- [ ] **[Swagger](https://swagger.io/)**
    - Outil open source et professionnels, conçus pour simplifier le développement des API, concevoir et documenter
      efficacement les API à grande échelle.
        - *OpenAPI* utilisé pour les tests et la validation des Endpoints afin de s'assurer que l'API fonctionne comme
          prévu.
        - Vous pouvez accéder à la console de Swagger en visitant l'URL http://localhost:8088/api/swagger-ui/index.html après le déploiement de l'API.
          ![img_7.png](img_7.png)

  #### **Videos démonstratif des tests d'API:**
  ![Demo APIs](Demo%20APIs.mp4)

https://github.com/BrightProgrammer7/Meeting-Planner/assets/77416814/49a03b32-ff1b-4349-9e43-9aae4cf4605e


### IDE: Environnement de Developement

- [ ] **[Intellij IDEA](https://www.jetbrains.com/idea/)**
    - L’IDE leader de Java et Kotlin.
        - IDE qui rend le développement plus productif et agréable
        - Compréhension approfondie du code
        - Renforcer les workflows de développement d’entreprise
        - Expérience transparente dès la sortie de la boîte
          ![img_10.png](img_10.png)

### Test

- [ ] [JUnit 5](http://junit.org/junit5/)
    - La 5ème version majeure du framework de test convivial pour Java et la JVM
        - La génération actuelle du framework de test JUnit est l’un des frameworks de test unitaires les plus
          populaires de l’écosystème Java
        - Une base moderne pour les tests côté développeur sur la JVM afin de valider les méthodes individuelles et les
          fonctionnalités des classes spécifiques.
- [ ] [Mockito](https://site.mockito.org/)
    - Savoureux cadre de simulation pour les tests unitaires en Java
        - Cadre moqueur qui a vraiment bon goût pour écrire de beaux tests avec une API propre et simple.
        - Pas de Hangover car les tests sont très lisibles et ils produisent des erreurs de vérification propres.

#### **Videos démonstratif des Tests Unitaires et d’Intégrations.:**

![Demo Tests](Demo%20Tests.mp4)

https://github.com/BrightProgrammer7/Meeting-Planner/assets/77416814/bddab752-f910-4e44-b90a-9ca0b17d0ca9


## Resultats des Tables
Les informations de connexion sont dans le fichier application.properties.
![img_2.png](img_2.png)
![img_3.png](img_3.png)
![img_6.png](img_6.png)

#### © 2024 By **Akhmim Abdelilah** For **Zenika**
