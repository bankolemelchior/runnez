L'architecture à trois couches (ou **Three-Layer Architecture**) est un modèle classique utilisé dans le développement d'applications pour structurer le code de manière claire et maintenable. Elle est souvent utilisée dans les applications Spring Boot, car elle permet de séparer les différentes responsabilités de l'application en couches distinctes, facilitant la maintenance, l'évolutivité et les tests.

### Les trois couches principales sont :

1. **Couche de présentation (Presentation Layer)** - ou couche **controller**
2. **Couche de service (Service Layer)** - ou couche **business**
3. **Couche de persistance (Persistence Layer)** - ou couche **data access**

#### 1. Couche de présentation (Presentation Layer)

La **couche de présentation** est responsable de la gestion des requêtes HTTP entrantes et de la réponse HTTP sortante. C'est ici que se trouvent les **contrôleurs Spring MVC** (annotés avec `@Controller` ou `@RestController`).

- Elle reçoit les requêtes des utilisateurs (généralement via une interface web ou une API REST).
- Elle valide les entrées et délègue la logique métier à la couche de service.
- Elle retourne les résultats au client sous forme de réponse HTTP.

**Exemple :**

```java
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        User user = userService.getUserById(id);
        return ResponseEntity.ok(user);
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User createdUser = userService.createUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }
}
```

#### 2. Couche de service (Service Layer)

La **couche de service** contient la logique métier. Elle sert de pont entre la couche de présentation et la couche de persistance. Toutes les règles métier de l'application sont centralisées ici.

- Elle reçoit les données de la couche de présentation, applique la logique métier et interagit avec la couche de persistance pour obtenir ou modifier les données.
- Elle peut également gérer les transactions.

**Exemple :**

```java
@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    public User createUser(User user) {
        return userRepository.save(user);
    }
}
```

Dans cet exemple, la couche de service interagit avec la couche de persistance (`UserRepository`) pour récupérer ou enregistrer des données et applique la logique métier.

#### 3. Couche de persistance (Persistence Layer)

La **couche de persistance** est responsable de l'accès aux données. Elle utilise généralement des repositories pour interagir avec la base de données.

- Elle utilise des interfaces **Repository** ou **DAO (Data Access Object)** pour communiquer avec la base de données.
- Elle utilise souvent des outils de persistance comme **JPA (Java Persistence API)** ou **Spring Data JPA** dans Spring Boot.

**Exemple :**

```java
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
```

Dans cet exemple, `UserRepository` est une interface qui hérite de `JpaRepository`, ce qui permet d'accéder aux opérations CRUD standard (Create, Read, Update, Delete) sur l'entité `User`.

### Comment les couches interagissent-elles ?

Voici un flux typique dans une architecture à trois couches :

1. L'utilisateur envoie une requête HTTP (par exemple, récupérer un utilisateur par son ID).
2. La requête est gérée par le **controller** dans la **couche de présentation**, qui transmet la demande à la **couche de service**.
3. La **couche de service** applique la logique métier, par exemple, vérifier si l'utilisateur existe et renvoyer une réponse appropriée. Pour ce faire, elle demande des données à la **couche de persistance**.
4. La **couche de persistance** interagit avec la base de données pour récupérer ou modifier les données.
5. La **couche de service** renvoie ensuite les données ou le résultat de l'opération au **controller**.
6. Le **controller** renvoie une réponse HTTP au client.

### Avantages de l'architecture à trois couches

1. **Séparation des responsabilités** : Chaque couche a une responsabilité claire, ce qui rend le code plus modulaire et plus facile à maintenir.
2. **Testabilité** : Il est plus facile de tester chaque couche indépendamment.
   - Par exemple, vous pouvez tester la couche de service sans avoir besoin de la base de données en utilisant des mocks pour la couche de persistance.
3. **Réutilisabilité** : La logique métier est centralisée dans la couche de service et peut être réutilisée par plusieurs contrôleurs ou d'autres services.
4. **Scalabilité** : Cette architecture permet de faire évoluer les différentes couches indépendamment, que ce soit en termes de performances ou de fonctionnalités.

### Conclusion

L'architecture à trois couches en Spring Boot est une approche couramment utilisée pour structurer les applications de manière modulaire et maintenable. Chaque couche (présentation, service, persistance) a une responsabilité bien définie, ce qui facilite la gestion du projet à grande échelle, le test des composants et l'évolution du code.