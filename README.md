This is the project I have been working on  with my college mate (https://github.com/Kacperux1) for Component Programming course held during winter semester 2024/2025.

This JavaFX application implements the famous simulation "Game of Life" in a form of desktop application.

Features:
- Board with choosable dimensions with cells interactive on-click (changeable state (alive-dead) of the cell)
- three fillment levels
- saving/reading/deleting the board to/from file and database
- two language versions : English and Polish
- Docker support for database

Technological stack:

Programming language: Java 21
Project build tool: Maven 3.9
GUI Library: JavaFX
DB: PostgreSQL 17

How to run:
Elements needed:
Docker installed on local machine (prefferably with WSL)
Maven 3.x (preferably 3.9)

to build the whole project with reports in th main directory execute the following command:
mvn clean install site

to run the app:
First, the DB container needs to be running, execute the following commands in shell:
docker compose up (preferably with -d option)

to run the executable:
cd ./View
mvn javafx:run (also with clean option)

to turn down the container:
docker-compose down








