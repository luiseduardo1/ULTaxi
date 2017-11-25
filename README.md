# ULTaxi

## Dépendances

Le projet nécessite *Java 8* et *Maven*.

## Compilation

Dans une invite de commande, écrivez la ligne suivante:

```sh
mvn clean && mvn compile
```

## Exécution

### Serveur

Pour rouler le projet, écrivez dans une invite de commande la ligne suivante:

```sh
mvn exec:java
```

### Tests

Pour exécuter les tests, vous pouvez utilisez la commande suivante:

```sh
mvn test
```

### Scripts `bash`

Il y a aussi une gamme de scripts `bash` qui permettent de rouler des requêtes
sur le serveur et de tester des appels. Voir le _README_
[suivant](scripts/README.md) pour plus de détails.
