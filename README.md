# currency-rates
---
### Project Description
1. currency-rates is Java app that gets currency rates from [bank.lv](https://www.bank.lv/statistika/valutu-kursi/aktualie) and saves them in database.
2. it can display newest rates or desiret currency historic rates in web API 
3. All app runs in docker

---

### Requirements:
Have docker installed on your system. You can download it here https://www.docker.com/

---
### Configuration
1# Copy this project: `git@github.com:resetcat/currency-rates.git`<br />
2# Create your docker network by entering in your console: 

    docker network create currency_network
3# Then to start MySql dockered server type:

    docker run -p 3307:3306 --name currency_rates_db --add-host host.docker.internal:host-gateway  -e MYSQL_ROOT_PASSWORD=misa -e MYSQL_DATABASE=currency_rates -e MYSQL_USER=root -e MYSQL_PASSWORD=misa --network=currency_network  -d mysql/mysql-server:latest    
4# Build docker from jar file:

    docker build -t currency-rates-jar-with-dependencies.jar .
5# And run the app with command:

    docker run -p 7000:7000 --network=test_network --name currency-rates  -t -i  currency-rates-jar-with-dependencies.jar
6# To update and fill database with currency rate data enter in console:

    update
7# Launch web-api on `http://localhost:7000/` by entering:

    start
    
---
### Endpoint description
