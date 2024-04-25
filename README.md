# <p align="center">👨‍🍳 Delantal de Estrellas</p>

**Delantal de Estrellas** is a web platform designed for both amateur cooks and professional chefs to discover, create, and manage recipes, chefs, and ingredients. It provides a user-friendly environment for culinary exploration and documentation, fostering a community around the joy of cooking.

## Table of Contents

1. [Team Members](#team-members)
2. [Database Diagram](#database-diagram)
3. [Database Configuration](#database-configuration)

## Team Members

| Name                  | URJC Email                        | GitHub Nickname              |
|-----------------------|-----------------------------------|------------------------------|
| Tarek Elshami Ahmed   | t.elshami.2021@alumnos.urjc.es    | [@TarekElshami](https://github.com/TarekElshami) |
| Álvaro Serrano Rodrigo| a.serranor.2021@alumnos.urjc.es   | [@AlvaroS3rrano](https://github.com/AlvaroS3rrano) |
| Ángel Marqués García  | a.marquesg.2021@alumnos.urjc.es   | [@AngelMarquesGarcia](https://github.com/AngelMarquesGarcia) |

## Database Diagram
![Database Diagram](Database%20design.svg)

*DataBase Diagram*

## Database Configuration

Follow these steps to configure your MySQL database for Delantal de Estrellas:

1. **Download MySQL**: Version 8.0.36.0 from the [official site](https://dev.mysql.com/downloads/installer/).
2. **Configuration**: Use the following settings during installation:
   
   | Section    | Value      |
   | :--------: | :--------: |
   | Port       | `3306`     |
   | User name  | `Admin`    |
   | Password   | `password` |

3. **Windows Service**: Configure MySQL Server as a Windows Service.
4. **User Permissions**: Grant full access to the user.
5. **Schema Creation**: Create a new Schema named `delantaldeestrellas` in the server using MySQL Workbench.
