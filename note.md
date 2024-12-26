The `var` keyword in Java is not a traditional type; rather, it is a reserved type name introduced in Java 10 that allows for **type inference**. This means that when you declare a variable using `var`, the Java compiler infers its type based on the value assigned to it at the time of declaration.

### Key Points about `var` in Java:

- **Type Inference**: The type of the variable is determined by the compiler from the right-hand side of the assignment. For example, if you write `var x = 10;`, the type of `x` is inferred to be `int`[1][2][3].
  
- **Usage Context**: The `var` keyword can only be used for local variable declarations within methods. It cannot be used for method parameters, return types, instance variables, or static variables[1][2][3].

- **Strong Typing**: Despite using `var`, Java remains a strongly typed language. Once a variable's type is inferred, it cannot change to another type later in the code[2][4].

- **Not a Keyword**: Technically, `var` is considered a **reserved type name** rather than a keyword. This distinction allows it to be used as an identifier (e.g., as a variable name) in some contexts[5].

### Example:
Here’s a simple example demonstrating the use of `var`:

```java
public class Example {
    public static void main(String[] args) {
        var number = 5; // inferred as int
        var message = "Hello, World!"; // inferred as String
        System.out.println(number);
        System.out.println(message);
    }
}
```

In this example, `number` is inferred to be of type `int`, and `message` is inferred to be of type `String`.

### Records

Java introduced **records** in version 16 as a way to simplify the creation of classes that are primarily used to hold immutable data. Records provide a more concise syntax compared to traditional classes, allowing developers to define data carriers with minimal boilerplate code.

## Key Features of Records

- **Compact Syntax**: Records allow you to declare a class with a simple syntax that includes the fields directly in the record declaration. For example:
  ```java
  public record User(UUID id, String firstName, String lastName) {}
  ```
  This declaration automatically generates private final fields, a public constructor, and accessor methods for each field.

- **Immutability**: All fields in a record are implicitly `final`, meaning their values cannot be changed after the record is created. This characteristic makes records ideal for situations where data integrity is important.

- **Automatic Method Generation**: When you define a record, the Java compiler automatically generates several methods:
  - `equals()`
  - `hashCode()`
  - `toString()`
  
  This reduces the need for boilerplate code typically associated with data classes.

- **Canonical Constructor**: Records come with a canonical constructor that matches the parameters defined in the record header. This constructor is automatically created and allows for easy instantiation of the record.

- **No Extending Other Classes**: Records cannot extend other classes, but they can implement interfaces. This restriction helps maintain their simplicity and purpose as data carriers.

## When to Use Records

Records are particularly useful when:
- The primary purpose of the class is to store and communicate data.
- You want to avoid boilerplate code for common methods like `equals()`, `hashCode()`, and `toString()`.
- The data structure is intended to be immutable.

### Example of a Record
Here’s an example of defining and using a record in Java:

```java
public record Person(String name, String email) {}

public class Main {
    public static void main(String[] args) {
        Person person = new Person("Alice", "alice@example.com");
        System.out.println(person.name()); // Accessor method
        System.out.println(person); // Automatically calls toString()
    }
}
```

In this example, `Person` is defined as a record with two fields: `name` and `email`. The generated methods allow easy access to these fields without additional code.

### JDBC Client
Le JDBC Client (Java Database Connectivity) est une API Java qui permet aux applications Java de se connecter et d'interagir avec des bases de données relationnelles. Elle fournit une interface standard pour exécuter des requêtes SQL, récupérer des résultats et gérer les transactions.
Fonctionnalités Principales
Connexion à la Base de Données : JDBC permet d'établir une connexion à diverses bases de données en utilisant des drivers spécifiques. Il supporte plusieurs types de bases de données comme MySQL, PostgreSQL, Oracle, etc.
Exécution de Requêtes SQL : Avec JDBC, les développeurs peuvent exécuter des requêtes SQL pour insérer, mettre à jour, supprimer ou interroger des données.
Gestion des Résultats : JDBC fournit des mécanismes pour traiter les résultats des requêtes SQL, que ce soit sous forme de lignes ou d'ensembles de résultats.
Transactions : JDBC prend en charge la gestion des transactions, permettant aux développeurs de contrôler le commit ou le rollback des opérations sur la base de données.
Support des Types de Données : JDBC gère divers types de données SQL et permet leur conversion en types Java appropriés.
Architecture
La structure typique d'une application utilisant JDBC comprend :
Driver Manager : Gère les drivers JDBC et établit les connexions.
Driver JDBC : Implémentation spécifique pour chaque type de base de données.
Connection : Représente une connexion à la base de données.
Statement : Utilisé pour exécuter des requêtes SQL.
ResultSet : Représente les résultats d'une requête exécutée.


```java
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class JdbcExample {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/your_database";
        String user = "your_username";
        String password = "your_password";

        try {
            // Établir la connexion
            Connection connection = DriverManager.getConnection(url, user, password);
            Statement statement = connection.createStatement();

            // Exécuter une requête
            ResultSet resultSet = statement.executeQuery("SELECT * FROM customers");

            // Traiter les résultats
            while (resultSet.next()) {
                System.out.println("Nom: " + resultSet.getString("nom_client"));
                System.out.println("Email: " + resultSet.getString("email_client"));
            }

            // Fermer la connexion
            resultSet.close();
            statement.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
```


