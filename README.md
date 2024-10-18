# RMM (Remote Monitoring and Management)


### Objectif

Création d’un RMM (Remote Monitoring and Management) client / serveur permettant la gestion à distance de machines Linux.


### Features

- Serveur permettant d'aggréger les données des machines supervisées
- Client de gestion exécuté sur la machine à gérer, se connecte automatiquement au serveur
- Client d’Interface Utilisateur desktop (et mobile si possible) permettant la gestion de la flotte de machines et la visualisation des données
- Accès console
- Remontée des données de fonctionnement de la machine vers le serveur
- Création de scripts (bash) et exécution de ces scripts sur un ensemble de machines
- (Sauvegardes automatiques de fichiers)
- Pouvoir organiser les machines (tri par nom/@ip, filtrer par nom/@ip, créer des dossiers, déplacer des machines)


### Utilisation

Un JAR compilé contient l'ensemble de l'application Client/Serveur. Le choix du type de d'application se fait donc au lancement, via des arguments dans la commande d'éxécution :
- "-s" ou "--server" : Lancer l'application en mode serveur (aggrège les données)
- "-c" ou "--client" : Lancer l'application en mode client (à placer sur les machines à superviser)
- "-u" ou "--userinterface" : Lancer l'application en mode interface utilisateur, permettant de visualiser les données du serveur et intérragir avec les machines supervisées (non fonctionnel pour le moment)
- "--no-debug" : Désactiver les messages de debug en console
- "-n" ou "--name" : Permet de spécifier un nom au client (principalement pour identifier les clients de supervision)
- "-h" ou "--host" : Permet de spécifier l'adresse de connexion (adresse du serveur)
- "-p" ou "--port" : Permet de spécifier le port de connexion (Client vers Serveur) ou le port d'écoute (Serveur)


**Voici donc un exemple d'utilisation :**

Lancer un serveur :
java --enable-preview -jar RMM.jar -s -p 15000

Lancer un client de supervision :
java --enable-preview -jar RMM.jar -c -n Client_A -h 127.0.0.1 -p 15000


**JARs de test :**

A la racine du dossier du projet se trouvent deux JARs permettant d'observer le comportement lors de l'autentification entre le Client et le Serveur.

RMM_auth_ok.jar est le comportement attendu, avec une autentification réussie par le client.

Commandes recommandée :
- Serveur : java --enable-preview -jar RMM_auth_ok.jar -s -p 15000
- Client : java --enable-preview -jar RMM_auth_ok.jar -c -n Client_A -h 127.0.0.1 -p 15000

RMM_auth_nok.jar est un exemple d'échec d'autentification, le serveur mettant fin à la liaison suite à un mot de passe incorrect.

Commandes recommandée :
- Serveur : java --enable-preview -jar RMM_auth_nok.jar -s -p 15000
- Client : java --enable-preview -jar RMM_auth_nok.jar -c -n Client_A -h 127.0.0.1 -p 15000


**Lecture des commandes reçues**

Avec le débug activé, il est possible d'observer les commandes reçues par le client et le serveur, et les paramètres transmis à travers ces commandes (chaque ligne représentant un paramètre suivi de sa valeur).

Exemple d'une commande d'autentification :

AUTH
login user
password bonjour
version 0.1