# README

## Front-end

La version de Node.js utilisée est **22.9.0**.  
Pour démarrer ce projet, vous pouvez  utiliser le `Dockerfile` :

```bash
cd front
docker build -t my-angular-app .
docker run -p 80:80 my-angular-app
```

---

## Back-end

Le back-end suit une **structure basée sur les fonctionnalités**, organisant les composants liés tels que les contrôleurs, les services, les dépôts et les entités dans des packages spécifiques à chaque fonctionnalité.

Pour démarrer le Back-end, vous pouvez  utiliser le `Dockerfile` :

```bash
docker build -t shop-app .
```

Exécutez le conteneur :

```bash
docker run -p 8080:8080 shop-app
```

Vous pouvez utiliser la collection Postman (`alten.postman_collection.json`) pour effectuer des tests.

---

