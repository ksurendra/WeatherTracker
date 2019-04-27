# The Weather Tracker #
This application allows users to submit measurements and retrieve them from a server, in a pre-defined format. 

## Documentation ##

Weather Tracker API Documentation is configured at http://localhost:8000/swagger-ui.html#/

## Build & Deployment ##
The applciation is built using Java and Spring framework. Uses Sping Boot to build, deploy and run the application.

System requirements
* Java 8 SDK
* Apache Maven 3.x

### How to Build & Test ###

To compile and test:

    mvn clean verify

Test output is written to both `stdout` and `integration-test.log`

If you would like to add additional dependencies, add their information to
`pom.xml`.

### How to Run the application ###
To run the app:

    mvn spring-boot:run -e

## Addtional notes ##
If you wish to run the integration tests on your own machine, you will need to
install [NodeJS][] v8 or greater in addition to JDK 8 and Maven 2

[NodeJS]: https://nodejs.org/
