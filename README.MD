# check-common-lib

`check-common-lib` est une librairie utilitaire Java Spring Boot fournissant des outils transverses pour les projets internes :

- 🔍 Audit automatique des appels de méthodes
- ⏱️ Log du temps d'exécution
- ♻️ Retry léger par annotation

## 🧩 Fonctionnalités

### 1. Audit automatique

- Interception des méthodes publiques (hors librairie)
- Log des paramètres d'entrée, du résultat et des exceptions
- Activation via la propriété : `check-common.audit-enabled=true`

### 2. Log du temps d'exécution

- Mesure du temps d'exécution de chaque méthode interceptée
- Activation via : `check-common.log-execution-time=true`

### 3. Retry par annotation

- Réexécution automatique d'une méthode en cas d'échec
- Personnalisable : nombre de tentatives, délai, exceptions incluses/exclues
- Nécessite l’annotation `@RetryOnFailure`

---

## ⚙️ Installation

Ajoutez la dépendance Maven :

```xml
<dependency>
    <groupId>com.tonorganisation</groupId>
    <artifactId>check-common-lib</artifactId>
    <version>1.0.0</version>
</dependency>
```

## Sonatype verification

[sonatype-nexus-verification: jfnepce5jy]
