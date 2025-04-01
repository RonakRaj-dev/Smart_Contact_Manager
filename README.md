
# Smart Contact Manager

Smart Contact Manager is a Spring Boot-based web application designed to securely manage and organize personal and professional contacts. The application provides users with an intuitive interface to store, edit, delete, and search for contacts efficiently.

## Features

- **User Authentication & Authorization** – Secure login, registration, and role-based access using Spring Security.
- **Contact Management** – Add, edit, delete, and search for contacts.
- **Pagination & Sorting** – Efficient browsing through large contact lists.
- **Profile Upload** – Store user profile images securely.
- **RESTful API** – Backend services using Spring Boot.
- **Responsive UI** – Built with Tailwind CSS for a clean and modern design.
- **Database Integration** – Uses MySQL for data storage.
- **Docker Support** – Easily deploy the app using Docker.
- **JWT Authentication** – Secure token-based authentication.


## Tech Stack

- **Frontend** : HTML, Tailwind CSS, JavaScript

- **Backend** : Spring Boot, Spring Security, Spring Data JPA

- **Database** : MySQL

- **Build Tool** : Maven

- **Deployment** : Docker, Free Hosting Platforms


## Screenshots

![Image](https://github.com/RonakRaj-dev/Smart_Contact_Manager/blob/main/Screenshot%202025-04-01%20120448.png?raw=true)

*Login Page*

![Image](https://github.com/RonakRaj-dev/Smart_Contact_Manager/blob/main/Screenshot%202025-04-01%20120317.png?raw=true)

*Dashboard*

![Image](https://github.com/RonakRaj-dev/Smart_Contact_Manager/blob/main/Screenshot%202025-04-01%20123954.png?raw=true)

*Contact Page*



## Setup

### 1. Clone the Repository

```bash
git clone https://github.com/RonakRaj-dev/Smart_Contact_Manager.git
cd Smart_Contact_Manager
```

### 2. Configure MySQL Database

```bash
spring.datasource.url=jdbc:mysql://localhost:3306/smart_contact_manager
spring.datasource.username=root
spring.datasource.password=yourpassword
spring.jpa.hibernate.ddl-auto=update
```

### 3. Build and Run the Application

```bash
mvn clean install
mvn spring-boot:run
```

## Contributing

#### Feel free to contribute to this project! 🚀

- Fork the repository.

- Create a new branch.

- Commit your changes.

- Submit a pull request.
