## 🚀 Getting Started

Follow the instructions below to get a copy of the project up and running on your local machine for development and testing purposes.

### Prerequisites

Before you begin, ensure you have the following installed on your machine:

- **Java JDK** (Version 21 or higher)

- **Apache Maven**

- **Git**

### 🛠️ Installation & Execution

To run the application, you simply need to clone the repository, compile the source code, and run the resulting JAR file.

#### 1. Clone the Repository

Open your terminal and run:

```bash


git clone --branch develop https://github.com/luan-carvalho/pcto_gestorfrota_api.git


cd pcto_gestorfrota_api


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


java -jar target/pcto_gestorfrota_api-0.0.1-SNAPSHOT.jar


```

> **Note:** The actual name of the jar file inside the target folder might vary slightly depending on your `pom.xml` version settings. Check the folder if the command above fails.\*
