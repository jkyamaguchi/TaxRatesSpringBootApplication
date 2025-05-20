
# TaxRatesSpringBootApplication

This is a project of SpringBoot Application using Eclipse IDE - version 2022-06 (4.24.0).
This document reports the steps of the development and how to run it.


#### Technologies

- Springboot - framework for dependency injection and inversion of control
- Java 17
- Web Tools - for HTML pages editing
- H2 - lightweight Java database
- JPA
- JSON
- Lombok - annotations library for standard Java code like getters/setters, etc.
- Thymeleaf - server-side Java template engine for both web and standalone environments
- Bootstrap - version 5.2.0


#### Setting up the environment


##### Installing Spring Boot in Eclipse

- Open eclipse
- Menu Help → Eclipse Marketplace
- Search and install:
  - Spring boot
  - Web Tools - for .html files
<br/>
<br/>

##### Running the Application

- Open the project on Eclipse.
- Right-click the project folder and run the application as a Spring Boot App.
- Access `http://localhost:8081/`
<br/>
<br/>


##### Accessing H2 Database

- Once the application is running, access: `http://localhost:8081/h2/`
- JDBC URL: `jdbc:h2:file:~/Municipalities`
- User name: sa 
- Password: (not required)
<br/>

  
#### Project Structure


##### Entities

- `Municipality`: represents a municipality (e.g. Copenhagen)
- `TaxRate`: holds information about a single tax record, including the scheduled dates, rate value, and the municipality to which the tax applies. Multiple `TaxRate` entries can be applied to a municipality.
<br/>
<br/>


##### Repositories

- `MunicipalityRepository`: repository interface for retrieving and managing municipality records.
- `TaxRateRepository`: repository interface for retrieving and managing tax rate records associated with a given municipality.
<br/>
<br/>


##### Services

- `MunicipalityService`: manages transactions and core functionalities involving the `MunicipalityRepository`.
- `TaxRateService`: manages transactions and core functionalities involving the `TaxRateRepository`.
- `TaxRateRetrieveService`: retrieves the tax rates associated with a municipality.
<br/>
<br/>


##### Controllers

- `MunicipalityController`:  defines REST endpoints that interact with the MunicipalityService to manage municipality-related operations.
- `TaxRateController`:  defines REST endpoints that interact with TaxRateService and MunicipalityService to manage the association between tax rates and municipalities, as well as perform operations related to tax rates.
- `TaxRateRetrieveController`: coordinates with TaxRateRetrieveService and MunicipalityService to retrieve tax rates for a specific municipality.
<br/>
<br/>


##### DataLoader.java

- Package: `src/main/java/com/example/spring/boot`
- `DataLoader` is a Spring component that populates the database with initial data when it is empty, allowing the application to run properly.
<br/>
<br/>


##### Thymeleaf Templates

Thymeleaf templates are standard HTML files (with a .html extension) that can be used both in web browsers and within web applications.
By default, they are located in the `src/main/resources/templates/` directory, and Spring Boot automatically detects and renders them as needed.
<br/>
<br/>


#### JUnit Tests

- `TaxRateServiceIntegrationTest.java`: validates the transactional behavior of `TaxRateService` in coordination with `TaxRateRepository`.
- `TaxRateRetrieveServiceTest.java`: tests whether `TaxRateRetrieveService` returns the expected rate values for a given municipality and date.
<br/>

#### Handling Exceptions

##### Package `com.example.spring.controllers`

- `ControllerException.java`: abstract class that manages a Thymeleaf view to display a message describing the RuntimeException.
- `TaxRateNotFoundException.java`: implements `ControllerException` to handle error messages when a tax rate cannot be retrieved for a given municipality and date. 

<br/>

##### Package `com.example.spring.controllers.advice`

- `RestAdvice.java`: annotated with `@RestControllerAdvice`, handles exceptions from REST operations.
- `Advice.java`: annotated with `@ControllerAdvice`, handles `TaxRateNotFoundException` and maintains the current Thymeleaf view by adding required model attributes.


### Future work

- Implement the addition of new tax records for municipalities individually and respective JUnit tests associated with this functionality.

