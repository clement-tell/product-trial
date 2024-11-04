# Backend
- Base de données : MongoDB (nécessite un serveur MongoDB pour executer en local l'application).
- Les contrôleurs, services et entités sont testés à 100%.
- Tout est documenté avec des Javadocs.

# Frontend
- La partie admin n'est pas sécurisée, il aurait fallu par exemple utiliser Spring Security mais je n'ai pas eu le temps.
- Uniquement la partie Contact est testée.
- <span style="color: red;">Après réflexion il aurait fallu mettre le filtre et la page des produits affichés en argument d'URL car pour l'instant à chaque mise à jour (création, modification, suppression) la page se recharge sans filtre et revient à la page 0</span>

# Comportement des APIs
|                   | Consultation | Création | Modification |
|-------------------|--------------|----------|--------------|
| `id`                | ☑            | ☐       | ☐            |
| `code`              | ☑            | ☐       | ☐            |
| `name`              | ☑            | ☑        | ☑             |
| `description`       | ☑            | ☑        | ☑            |
| `image`             | ☑            | ☑        | ☑            |
| `category`          | ☑            | ☑        | ☑            |
| `price`             | ☑            | ☑        | ☑             |
| `quantity`          | ☑            | ☐        | ☐            |
| `internalReference`  | ☑           | ☑        | ☐             |
| `shellId`          | ☑           | ☑        | ☑            |
| `inventoryStatus`   | ☑            | ☑        | ☑             |
| `rating`            | ☑            | ☐        | ☐            |
| `createdAt`        | ☑            | ☐        | ☐            |
| `updatedAt`        | ☑            | ☐        | ☐            |

# Réflexions

- `id` est défini comment étant incrémentable lors de la création.
- `code` est généré aléatoirement lors de la création.
- `internalReference` est renseignée lors de la création et n'est plus modifiable ensuite. (Après réflexion il aurait fallu le rendre obligatoire à la création).
- `name` et `price` sont obligatoires à la création.
- `inventoryStatus` doit être soit "INSTOCK", "LOWSTOCK" ou "OUTOFSTOCK".
- `createdAt` est généré lors de la création.
- `updatedAt` est généré lors de la modification.
- `rating` n'est pas renseignable lors de la création ou de la modification. Lorsque des avis arriverait, l'attribut pourrait se mettre à jour avec les nouvelles notes.
- Je n'ai pas compris `shellId` donc il n'y a pas de restriction dessus.

# Déploiement
- L'application est déployée avec App Engine sur Google Cloud Platform via GitHub Actions.
- L'application est accessible depuis https://clement-tell-product-trial.com.
- <span style="color: red;">Le backend peut mettre un peu de temps à démarrer (auto scaling) donc les produits peuvent mettre du temps à charger la première fois.</span>

# Liens utiles
- [Sonar Cloud](https://sonarcloud.io/organizations/candidat-clement-tell/projects)
- [GitHub](https://github.com/clement-tell/product-trial) avec les dépôts du Backend et du Frontend, un zip des sources, les tests postman
- [Lien de l'application](https://clement-tell-product-trial.com/)
