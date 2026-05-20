<div align="center">

# 🎓 Portail de Suivi du Doctorat

[![Java](https://img.shields.io/badge/Java-17-ED8B00?style=for-the-badge&logo=java&logoColor=white)](https://www.java.com)
[![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.x-6DB33F?style=for-the-badge&logo=spring-boot&logoColor=white)](https://spring.io/projects/spring-boot)
[![Thymeleaf](https://img.shields.io/badge/Thymeleaf-3.x-005F0F?style=for-the-badge&logo=thymeleaf&logoColor=white)](https://www.thymeleaf.org)
[![License](https://img.shields.io/badge/License-MIT-blue.svg?style=for-the-badge)](LICENSE)

**Application Web Java EE pour la gestion centralisée du suivi doctoral**

*Inscriptions · Soutenances · Publications · Formations*

</div>

---

## 📋 Table des matières

- [Contexte](#-contexte)
- [Objectifs](#-objectifs)
- [Équipe](#-équipe)
- [Stack Technique](#️-stack-technique)
- [Architecture](#-architecture)
- [Fonctionnalités](#-fonctionnalités)
- [Installation](#-installation)
- [Usage](#-usage)
- [Screenshots](#-screenshots)
- [Perspectives](#-perspectives)

---

## 🎯 Contexte

Le suivi doctoral dans les établissements universitaires repose encore largement sur des **procédures manuelles** (emails, documents papier), entraînant :

| Problème | Impact |
|----------|--------|
| ❌ Erreurs et retards dans les validations | Perte de temps et ressources |
| ❌ Manque de visibilité sur l'avancement | Dossiers bloqués ou oubliés |
| ❌ Difficulté à vérifier les prérequis de soutenance | Soutenances non conformes |
| ❌ Aucun circuit de validation centralisé | Communication fragmentée |

> **Solution proposée** : Un portail web **dématérialisé** avec workflow structuré et multi-rôles.

---

## 🚀 Objectifs

### Objectif Général

> Concevoir et implémenter un portail web de suivi doctoral permettant la gestion centralisée des inscriptions, soutenances, publications et formations avec un circuit de validation structuré.

### Objectifs Spécifiques

| | Objectif | Description |
|---|----------|-------------|
| 📝 | **Dématérialiser** | Remplacer les procédures papier par un workflow numérique fluide et traçable |
| 🔐 | **Sécuriser** | Authentification multi-rôles avec Spring Security (Doctorant, Directeur, Admin) |
| ⚡ | **Automatiser** | Vérification automatique des prérequis de soutenance et règles métier |
| 📊 | **Visualiser** | Tableaux de bord en temps réel avec jauges de progression et alertes |
| 🔄 | **Valider** | Circuit de validation multi-niveaux : Doctorant → Directeur → Admin |
| 📚 | **Centraliser** | Regroupement de toutes les données doctorales en un seul point d'accès |

### 📏 Contraintes Métier

```
┌─────────────────────────────────────────┐
│  • Durée max 3/6 ans                    │
│  • 2 articles Q1/Q2 minimum             │
│  • 2 conférences minimum                │
│  • 200h de formation doctorante         │
└─────────────────────────────────────────┘
```

---

## 👥 Équipe

<div align="center">

| <img src="https://img.shields.io/badge/👤-Assia_Abouzraa-blue?style=flat-square" /> | <img src="https://img.shields.io/badge/👤-Hiba_EL_Khayat-teal?style=flat-square" /> | <img src="https://img.shields.io/badge/👤-Youssef_El_Aissioui-purple?style=flat-square" /> |
|:---:|:---:|:---:|
| **Assia Abouzraa** | **Hiba EL Khayat** | **Youssef El Aissioui** |
| *Backend & Sécurité* | *Données & Base de données* | *Frontend & UX* |
| Spring Security | Modélisation JPA | Thymeleaf |
| Services & Controllers | Repositories | Bootstrap & CSS |
| Entités JPA | DataInitializer | JavaScript |

</div>

---

## 🛠️ Stack Technique

### Backend

```
Java 17 ────────────────┐
Spring Boot 3.x ────────┼──► Framework principal
Spring MVC ─────────────┤
Spring Security ────────┼──► Authentification & Autorisation
Spring Data JPA ────────┤
Hibernate ORM ──────────┼──► Persistance des données
Lombok ─────────────────┘
```

### Frontend

```
Thymeleaf ──────────────► Moteur de templates
HTML5 / CSS3 ───────────► Structure & Style
Bootstrap 5 ──────────────► Composants responsive
JavaScript (Vanilla) ───► Interactivité client
```

### Base de Données

| Environnement | Technologie | Usage |
|---------------|-------------|-------|
| 🧪 Développement | H2 | Tests et prototypage |
| 🚀 Production | PostgreSQL | Données réelles |
| 🔧 Génération | JPA / Hibernate DDL-auto | Schéma automatique |
| 📥 Initialisation | Scripts SQL | Données de test |

### Outils & Déploiement

<p align="center">
  <img src="https://img.shields.io/badge/Maven-C71A36?style=for-the-badge&logo=apache-maven&logoColor=white" />
  <img src="https://img.shields.io/badge/Git-F05032?style=for-the-badge&logo=git&logoColor=white" />
  <img src="https://img.shields.io/badge/IntelliJ_IDEA-000000?style=for-the-badge&logo=intellij-idea&logoColor=white" />
  <img src="https://img.shields.io/badge/Docker-2496ED?style=for-the-badge&logo=docker&logoColor=white" />
</p>

---

## 🏗 Architecture

### Architecture MVC 3 Couches

```
┌─────────────────────────────────────────────────────────────┐
│                    COUCHE PRÉSENTATION                        │
│  ┌─────────────────────────────────────────────────────┐    │
│  │  Thymeleaf  │  Bootstrap 5  │  CSS3  │  JavaScript   │    │
│  └─────────────────────────────────────────────────────┘    │
│                         ▲                                   │
│                         │ Requêtes HTTP / Réponses HTML     │
├─────────────────────────┼───────────────────────────────────┤
│                         ▼                                   │
│                     COUCHE MÉTIER                           │
│  ┌─────────────────────────────────────────────────────┐    │
│  │  Spring Boot  │  Spring MVC  │  Spring Security     │    │
│  │  Services     │  DTO         │  Validators          │    │
│  └─────────────────────────────────────────────────────┘    │
│                         ▲                                   │
│                         │ Appels Repository                 │
├─────────────────────────┼───────────────────────────────────┤
│                         ▼                                   │
│                     COUCHE DONNÉES                          │
│  ┌─────────────────────────────────────────────────────┐    │
│  │  Spring Data JPA  │  Hibernate  │  H2 / PostgreSQL   │    │
│  └─────────────────────────────────────────────────────┘    │
└─────────────────────────────────────────────────────────────┘
```

### Patterns Utilisés

| Pattern | Description | Implémentation |
|---------|-------------|----------------|
| 🧩 **MVC** | Model-View-Controller | Spring MVC + Thymeleaf |
| 🧩 **DAO** | Data Access Object | Spring Data Repositories |
| 🧩 **IoC** | Inversion of Control | Injection de dépendances Spring |
| 🧩 **SRP** | Single Responsibility | Une classe = une responsabilité |

---

## ✨ Fonctionnalités

### 🔐 1. Authentification & Comptes
- Inscription / Connexion sécurisée
- Rôles : **Doctorant** · **Directeur** · **Admin**
- Chiffrement BCrypt + Spring Security

### 📋 2. Module Inscription
- Campagnes par année universitaire
- Circuit de validation : `Doctorant → Directeur → Admin`
- Règles de durée (3/6 ans) avec gestion des dérogations

### 🎓 3. Module Soutenance
- Vérification **automatique** des prérequis
- Proposition du jury par le directeur de thèse
- Autorisation et planification par l'administration

### 📚 4. Publications & Formations
- Suivi des publications (Q1/Q2)
- Gestion des conférences
- Validation des 200h de formation

### 📊 5. Tableau de Bord
- Suivi en **temps réel**
- Jauges de progression visuelles
- Alertes de dépassement de durée

### 🔧 6. Administration
- Gestion des utilisateurs et rôles
- Création des campagnes d'inscription
- Statistiques globales du système

---

## 📥 Installation

### ✅ Prérequis

- [ ] Java 17+
- [ ] Maven 3.8+
- [ ] (Optionnel) PostgreSQL 14+

### 📦 Cloner le projet

```bash
# Cloner le dépôt
git clone https://github.com/votre-org/portail-doctorat.git

# Accéder au répertoire
cd portail-doctorat
```

### ⚙️ Configuration

```bash
# 1. Copier le fichier de configuration
cp src/main/resources/application.properties.example    src/main/resources/application.properties

# 2. Éditer application.properties (optionnel)
# Changer le port (défaut: 8084)
# Configurer PostgreSQL au lieu de H2
```

### 🚀 Lancer l'application

```bash
# Compilation
mvn clean install

# Exécution
mvn spring-boot:run
```

> 🌐 L'application est accessible sur : **http://localhost:8084**

---

## 🖥 Usage

### Parcours Doctorant 👨‍🎓

```
┌─────────────────────────────────────────┐
│  1. Connexion → Tableau de bord         │
│  2. Déposer dossier d'inscription       │
│  3. Ajouter publications / formations     │
│  4. Créer demande de soutenance         │
│  5. Suivre l'état en temps réel         │
└─────────────────────────────────────────┘
```

### Parcours Directeur de Thèse 👨‍🏫

```
┌─────────────────────────────────────────┐
│  1. Consulter dossiers à valider        │
│  2. Donner avis sur inscription         │
│  3. Proposer membres du jury            │
│  4. Soumettre rapport favorable          │
│  5. Suivre ses doctorants               │
└─────────────────────────────────────────┘
```

### Parcours Administrateur ⚙️

```
┌─────────────────────────────────────────┐
│  1. Dashboard statistiques globales     │
│  2. Gérer campagnes d'inscription       │
│  3. Valider / rejeter dossiers          │
│  4. Autoriser la soutenance             │
│  5. Planifier date, heure & lieu        │
└─────────────────────────────────────────┘
```

---

## 📸 Screenshots

> 🖼️ *Captures d'écran à ajouter dans le dossier `docs/screenshots/`*

<div align="center">

| Dashboard | Inscription | Soutenance |
|:---------:|:-----------:|:----------:|
| ![Dashboard](docs/screenshots/dashboard.png) | ![Inscription](docs/screenshots/inscription.png) | ![Soutenance](docs/screenshots/soutenance.png) |
| *Vue d'ensemble* | *Dépôt de dossier* | *Planification* |

</div>

---

## 📊 Statistiques du Projet

<div align="center">

| Fichiers Java | Templates HTML | Fichiers CSS/JS | Lignes de Code |
|:-------------:|:--------------:|:---------------:|:--------------:|
| **58** | **42** | **1** | **2K+** |

</div>

---

## 🔮 Perspectives

### Améliorations Futures

- [ ] 📧 **Notifications email** automatiques (Spring Mail)
- [ ] 📱 **Application mobile** (React Native / Flutter)
- [ ] 📊 **Tableaux de bord analytiques** avancés
- [ ] 🔒 **Authentification OAuth2** / SSO institutionnel
- [ ] 🐳 **Conteneurisation Docker** complète
- [ ] ☁️ **Déploiement cloud** (AWS / Azure)

---

## 📝 License

<div align="center">

Ce projet est sous licence **MIT**.

Voir le fichier [LICENSE](LICENSE) pour plus de détails.

</div>

---

<div align="center">

### 🎓 Projet réalisé dans le cadre du module Java EE

**Année universitaire 2025-2026**

<br>

| Assia Abouzraa | Hiba EL Khayat | Youssef El Aissioui |
|:--------------:|:--------------:|:-------------------:|
| 💻 Backend | 🗄️ Données | 🎨 Frontend |

<br>

⭐ *N'hésitez pas à star le projet si vous le trouvez utile !* ⭐

</div>
