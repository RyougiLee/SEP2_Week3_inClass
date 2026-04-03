
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests


FROM eclipse-temurin:21-jre
WORKDIR /app


RUN apt-get update && apt-get install -y \
    libgtk-3-0 \
    libglu1-mesa \
    libxtst6 \
    libxxf86vm1 \
    libcanberra-gtk-module \
    libcanberra-gtk3-module \
    && rm -rf /var/lib/apt/lists/*


COPY --from=build /app/target/*.jar app.jar


ENTRYPOINT ["java", "-jar", "app.jar"]