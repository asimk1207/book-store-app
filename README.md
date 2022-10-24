# Bookstore
Online Book Store REST API’s Implementation

•	CRUD operations on Books.

•	Checkout operation for single or multiple books which will return the total payable amount.


# Execute Unit Tests
book-store-app> mvn clean test

# Package Application
:\book-store-app> mvn clean package -DskipTests

# Run Application
:\book-store-app> java -jar target/bookstore-0.0.1.jar

# Build Docker Image
:\book-store-app> docker build -t book-store-app .  

# Run BookStore Docker Container
:\book-store-app> docker run -d --name book-store-app -p 8080:8080 -t book-store-app:latest  

# Actuator
http://localhost:8080/bookstore/actuator
