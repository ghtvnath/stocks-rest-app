# stocks-rest-app
This project is created as MVP for an assignment

The project is developed using maven, and Spring Boot. 

Spring MVC is used as the controller for the web application backend. 

H2DB in-memory database is used for storing data, Hibernate along with Spring-data is used for 
interacting with the database in code level. 

Once the application is run, APIs are available as following. 

  Get all stocks    > [GET] http://localhost:8080/api/stocks
  
  Get stock by ID   > [GET] http://localhost:8080/api/stocks/1
      
  Add new stock     > [POST] http://localhost:8080/api/stocks
              {
                  "name": "ANT",
                  "description": "Another",
                  "currentPrice": "101"
              }

  Update existing stock > [PUT] http://localhost:8080/api/stocks
             Same payload as above POST with id parameter set.
             
             {
                  "id":"1"
                  "name": "ANT",
                  "description": "Another",
                  "currentPrice": "101"
              }
              
  Assumptions : 
        Name is unique for a stock. Hence if some one tries to create a stock using the same stock name, 
        existing stock will be updated. 
