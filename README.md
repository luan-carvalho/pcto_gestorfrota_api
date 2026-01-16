# PCTO_GESTORFROTA_API 🚓

![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![Maven](https://img.shields.io/badge/apache_maven-C71A36?style=for-the-badge&logo=apachemaven&logoColor=white)

## 📖 About the Project

**PCTO_GESTORFROTA_API** is a fleet management solution designed to assist the **State Civil Police** in the administration, tracking, and maintenance of their operational vehicles.

This project focuses on modernizing internal processes, ensuring better resource allocation, and providing a robust backend API for fleet data management.


### 🎓 Academic Context

This API was developed by the **6th-period students** of the **Sistemas para Internet** (Internet Systems) course at the **Instituto Federal de Educação, Ciência e Tecnologia (IF)**.

It serves as part of the practical curriculum to demonstrate proficiency in backend development, API architecture, and software deployment.

---

## 🚀 Getting Started

Follow the instructions below to get a copy of the project up and running on your local machine for development and testing purposes.

### Prerequisites

Before you begin, ensure you have the following installed on your machine:

* **Java JDK** (Version 21 or higher)
* **Apache Maven**
* **Git**

### 🛠️ Installation & Execution

To run the application, you simply need to clone the repository, compile the source code, and run the resulting JAR file.

#### 1. Clone the Repository
    
Open your terminal and run:

```bash
git clone https://github.com/luan-carvalho/pcto_gestorfrota_api.git
cd PCTO_GESTORFROTA_API
```
#### 2\. Build the Project

Use Maven to clean the target directory and package the application into a JAR file:

```bash
mvn clean package
```

> **Note:** If you want to skip unit tests during the build to speed up the process, you can use `mvn clean package -DskipTests`.

#### 3\. Run the Application

Once the build is complete, the JAR file will be located in the `target/` directory. Run it using:

```bash
java -jar target/PCTO_GESTORFROTA_API-0.0.1-SNAPSHOT.jar
```
> **Note:** The actual name of the jar file inside the target folder might vary slightly depending on your `pom.xml` version settings. Check the folder if the command above fails.*
