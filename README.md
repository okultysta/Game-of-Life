This is the project I have been working on  with my college mate (https://github.com/okultysta) during the Componnent Programming course held during winter semester 2024/2025.

This JavaFX application implements the famous simulation "Game of Life" in a form of interactive simulation.

Features:
- Board with choosable dimensions with cells interactive on-click
- three fillment levels
- Saving/Reading the board to/from file and database
- two language versions : English and Polish

Technological stack:

Programming language: Java 21
Project build tool: Maven 3.9
GUI Library: JavaFX
DB: PostgreSQL 17

How to run:
Elements needed:
POstgreSQL 17.x (default user is "postgres", def. password is "")
Maven 3.x (preferably 3.9)

to build the whole project with reports in th main directory execute the following command:
mvn clean install site

to run the app:
cd ./View
mvn javafx:run



