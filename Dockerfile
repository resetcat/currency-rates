FROM openjdk:18
EXPOSE 7000
COPY /lib/currency-rates-jar-with-dependencies.jar /app/currency-rates-jar-with-dependencies.jar
COPY /src/main/resources/database.properties /app/config/database.properties
WORKDIR /app
CMD ["java","-jar","currency-rates-jar-with-dependencies.jar"]