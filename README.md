# GroceriesScraper
This is a Spring Boot application to scrape and process grocery data from a website and give output in Json format.
# Technologies Used
* JDK 1.8
* Spring Boot 2.3.5
* Maven 3.6.1
# Running the application
* Go to the path where the project is extracted and run the below command in any CLI
````
mvn spring-boot:run
````
* Once all the maven dependencies are installed & the Spring Boot application has started,
go to the below URL(default port :8080) in the browser.
    
````
http://localhost:8080/getGroceryDetails
````
# TO-DO
* Currently the website Url is hardcoded but can be enhanced to entertain User input.
* Swagger API Implementation also needs to be implemented.
* More generic Utility functions to scrape the website.