# Recipe Finder
## Table of Contents
1. [Description](#description)
2. [Technologies Used](#technologies-used)
3. [How to Use](#how-to-use)
4. [Demo](#demo)
5. [Closing](#closing)
## Description
This project's goal is to utilize my love for both food and coding to further my learning of web development and using industry-standard technologies/frameworks/languages. Recipe Finder allows one to input a list of ingredients they have on hand to generate an assortment of curated recipes based on the input. Edamam's [Recipe Search and Diet](https://rapidapi.com/edamam/api/recipe-search-and-diet/) API is used to fetch the recipes. Please note that development for this project has essentially just started. Right now, this app only does the barebones requirements (log-in with password encryption and email verification, ability to input ingredients and receive recipes based on input). As my learning progresses, so too will this app's development. As it stands, here is what I want to implement:
- Community ranking system
- Detailed modern styling, potentially using a framework like Bootstrap
- Ability to save recent recipes
- Blocking certain features for unverified accounts
- Deployment to web server
- Many other features

## Technologies Used

This project leverages a variety of technologies to create a seamless and interactive recipe finding experience. Here's a breakdown of the technologies and frameworks utilized:

### Backend

1. **Spring Framework**
   
2. **PostgreSQL Database**:
   - Utilized as the primary database to store user data and other relevant information.

3. **Gmail SMTP**:
   - Integrated for sending email notifications and verifications to users.
   - Ensures secure and reliable email delivery.

### Frontend

4. **HTML/CSS**
  
### API Integration

5. **Edamam Recipe API**:
   - Integrated to fetch a curated list of recipes based on the ingredients input by the user.
   - Offers a vast collection of recipes with various dietary and nutritional filters.

## How to Use
Before running this app, you must set up the applications.yml file.
### API
To set up the api, go to this [link](https://rapidapi.com/edamam/api/recipe-search-and-diet/), and sign up for a RapidAPI account (if you do not have one). A generated key will then be available for you to use. Please insert this into the .yml file where it says 'INSERT_EDAMAM_API_KEY_HERE'.

### SMTP
In the .yml file, where it says 'INSERT_EMAIL_ADDRESS_HERE', insert the gmail address you want to use. 
Then, go to this [link](https://myaccount.google.com/apppasswords) while logged into the gmail account you want to use. Enter a name of your choosing, and then copy the generated password given to you. Insert this password in the .yml file where it says 'INSERT_PASSWORD_HERE'. 
Where it says 'INSERT_USERNAME_HERE', insert your gmail address. 

### PostgreSQL DB
To set up the database locally, ensure you have PostgreSQL [installed](https://www.postgresql.org/download/). 

Create a new databse in PostgreSQL using the following command:
```sh
createdb <your_database_name>
```

Then restore the database from the dump using the following command:
```sh
pg_restore -U <username> -d <your_database_name> -1 "src/database/dump-postgres-202309111836"
```

username corresponds to your PSQL username

your_database_name corresponds to your PSQL database name

### To Run
Ensure you have Java 11 or later and have set up the API, SMTP, and PSQL DB

1. Clone the repository
2. Navigate to the root of the project directory in your terminal
3. Build the project using
```sh
./mvnw clean install
```  
4. Run the application using
```sh
./mvnw spring-boot:run
```

Once running, you can access the application at http://localhost:8080

## Demo
[Link to video](https://youtu.be/-aDJEWW2Gvo)

## Closing
Thank you for taking the time to check out my project!


  

