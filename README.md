# TicTacToe



### Execute tests

```bash
./gradlew clean test
```

### Run the application using Gradle:

```bash
./gradlew bootRun
```

### Run the application using the standalone Jar
```bash
# Generate the fat executable Jar
./gradlew clean bootJar
# Run the application
java -jar build/libs/TicTacToe-0.0.1-SNAPSHOT.jar
```



The application is accessible on the following path: `http://localhost:8080/game`

The application has 2 endpoints, one for the player to introduce his play: `/play` and one to reset the game: `/reset`.