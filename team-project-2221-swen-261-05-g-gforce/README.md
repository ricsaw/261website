# E-Store:  RARAJ

# Modify this document to expand any and all sections that are applicable for a better understanding from your users/testers/collaborators (remove this comment and other instructions areas for your FINAL release)

An online E-store system built in Java 17, Maven, Angular and Spring.

## Team

- Robert Tetreault
- James De Luca
- Richard Sawh
- Antar Narayan Chowdhury
- Aidan Collins

## Prerequisites

- Java 17
- Maven
- Angular

## How to run it

1. Clone the repository and go to the root directory.
2. Execute `mvn compile exec:java`
3. Open in your browser `http://localhost:8080/`
4. In terminal locate the angular file.
5. Execute the Angular `ng serve --open`
6. Open in yout browser `http://localhost:4020/`
7. To open the test files - JoCoCo Report - use `mvn clean test`

## Known bugs and disclaimers

1. When the user click on somewhere on dropdown fro shoppingCart -except the buttons- it disapears.

Document any known bug or nuisance.
If any shortcomings, make clear what these are and where they are located.

## How to test it

The Maven build script provides hooks for run unit tests and generate code coverage
reports in HTML.

To run tests on all tiers together do this:

1. Execute `mvn clean test jacoco:report`
2. Open in your browser the file at `PROJECT_API_HOME/target/site/jacoco/index.html`

To run tests on a single tier do this:

1. Execute `mvn clean test-compile surefire:test@tier jacoco:report@tier` where `tier` is one of `controller`, `model`, `persistence`
2. Open in your browser the file at `PROJECT_API_HOME/target/site/jacoco/{controller, model, persistence}/index.html`

To run tests on all the tiers in isolation do this:

1. Execute `mvn exec:exec@tests-and-coverage`
2. To view the Controller tier tests open in your browser the file at `PROJECT_API_HOME/target/site/jacoco/model/index.html`
3. To view the Model tier tests open in your browser the file at `PROJECT_API_HOME/target/site/jacoco/model/index.html`
4. To view the Persistence tier tests open in your browser the file at `PROJECT_API_HOME/target/site/jacoco/model/index.html`

*(Consider using `mvn clean verify` to attest you have reached the target threshold for coverage)

## How to generate the Design documentation PDF

1. Access the `PROJECT_DOCS_HOME/` directory
2. Execute `mvn exec:exec@docs`
3. The generated PDF will be in `PROJECT_DOCS_HOME/` directory

## How to setup/run/test program

1. Tester, first obtain the Acceptance Test plan
2. IP address of target machine running the app
3. Execute `mvn compile exec:java` in Estore-api path
4. Execute the angular in Estore-GUI path using `ng serve --open`
5. seach http://localhost:4020

## License

MIT License

See LICENSE for details.
