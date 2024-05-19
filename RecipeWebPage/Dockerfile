#################################################
# Imagen base para el contenedor de compilación
################################################# 3.8.5
FROM maven:3.9.6-eclipse-temurin-17 as builder

# Define el directorio de trabajo donde ejecutar comandos
WORKDIR /project

# Copia las dependencias del proyecto
COPY pom.xml /project/

# Descarga las dependencias del proyecto
RUN mvn clean verify

# Copia el código del proyecto
COPY src /project/src

# Compila proyecto
RUN mvn package -o -DskipTests=true

#################################################
# Imagen base para el contenedor de la aplicación
#################################################
FROM openjdk:17

# Define el directorio de trabajo donde se encuentra el JAR
WORKDIR /usr/app/

# Copia el JAR del contenedor de compilación
COPY --from=builder /project/target/*.jar /usr/app/

#Copia el script para crear el certificado de cockroach
RUN curl --create-dirs -o ~/.postgresql/root.crt -O https://cockroachlabs.cloud/clusters/94285693-fa2e-4a98-a3ab-589ceb61e17a/cert

# Indica el puerto que expone el contenedor
EXPOSE 8080

# Comando que se ejecuta al hacer docker run
CMD [ "java", "-jar", "app.jar" ]