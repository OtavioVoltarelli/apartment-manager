# Apartment Manager

**Apartment Manager** is a REST API developed with Spring Boot to manage apartments and residents, including records of tenants and their cars. This project serves as a comprehensive demonstration of building a complete application using modern technologies.

## 🚀 Features

- **Apartment Management**: Add and modify apartment information.
- **Resident Management**: Add and edit resident information, including linking to apartments and cars.
- **Linking Residents to Apartments and Cars**: Associate residents with apartments and register their cars.
- **Validation and Error Handling**: Includes robust validations and proper error handling.

## ⚒️ Technologies Used

- **Spring Boot**: Framework for building the application.
- **JPA/Hibernate**: Facilitates database interaction and entity management.
- **MySQL**: Relational database for storing information.
- **Docker**: Containers the application, ensuring a consistent and easy-to-configure runtime environment.

## 🏗️ Project Structure

- **`src/main/java/com/apartment_manager`**: Application source code.
  - **`controllers`**: REST controllers for handling requests.
  - **`services`**: Business logic and services of the application.
  - **`models`**: JPA models and entities.
  - **`repositories`**: Repository interfaces for data access.
- **`src/main/resources`**: Configuration files and properties.
- **`Dockerfile`**: Configuration for containerizing the application.
- **`pom.xml`**: Maven dependency management.

## 📦 How to Run the Project

1. **Clone the Repository**

   ```bash
   git clone https://github.com/OtavioVoltarelli/apartment-manager.git
   cd apartment-manager
   ```

  2. **Build the Docker Image**

     ```bash
     docker build -t apartment-manager
      ```


2. **Run the container**

     ```bash
     docker run -p 8080:8080 apartment-manager
      ```

   
4. **Access the API**

     The API will be available at http://localhost:8080.



## 🤝 Contributions
If you would like to contribute to the project, feel free to open an issue or submit a pull request.
