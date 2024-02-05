# Recipe Finder

<img width="1190" alt="image" src="https://github.com/rahildhodapkar/recipe-finder/assets/115059842/f717c2b1-ceda-4aef-84cf-f0f235b364cf">

[LINK TO WEBSITE](https://recipesearch.up.railway.app/recipeSearch)

## Table of Contents
1. [Description](#description)
2. [Technologies Used](#technologies-used)
3. [How to Use](#how-to-use)
4. [Run Locally](#run-locally)
5. [Contact Information](#contact-information)
## Description
Recipe Finder allows one to input a list of ingredients they have on hand to generate an assortment of curated recipes based on the input. Edamam's [Recipe Search and Diet](https://rapidapi.com/edamam/api/recipe-search-and-diet/) API is used to fetch the recipes. Here is what I want to implement in the future:
- Login
   - Used to have one but removed it as user information is currently useless
- Better styling/web-design practices

## Technologies Used

This project leverages a variety of technologies to create a seamless and interactive recipe finding experience. Here's a breakdown of the technologies and frameworks utilized:

### Backend

1. **Spring Framework**
   
2. **PostgreSQL Database**:
   - Utilized as the primary database to store user data and other relevant information.
   - Temporarily unused due to removal of login

3. **Gmail SMTP**:
   - Integrated for sending email notifications and verifications to users.
   - Ensures secure and reliable email delivery.
   - Temporarilyh unused due to removal of login

### Frontend

4. **JS/HTML/CSS**
  
### API Integration

5. **Edamam Recipe API**:
   - Integrated to fetch a curated list of recipes based on the ingredients input by the user.
   - Offers a vast collection of recipes with various dietary and nutritional filters.

## How to Use
Navigate to: recipesearch.up.railway.app/recipeSearch
Enter ingredients into the form, press submit, and recipes will displayed that best match the list of ingredients you inputted.

## Run Locally
If you would like to run this program locally, there are a few steps you will need to follow:

### Prerequisites
1. Java 17 or later
2. PostgreSQL
3. Maven

### Clone Repository
Clone and navigate to this repository by using these following commands:
```
git clone https://github.com/rahildhodapkar/recipe-finder.git
cd recipe-finder
```

### Local PSQL Database
Because this program will eventually require user information, it requires a PSQL DB.

After installing PostgreSQL, create a new local PostgreSQL database.

Create two tables in the public schema with the following script:

```
create table user_info (
	id serial primary key,
	username text,
	password text,
	email text,
	is_verified bool,
	email_verification_token text,
	pword_verification_token text
);

create table role_info (
	id serial primary key,
	username text,
	role text
);
```

### Email SMTP
This project will use SMTP for email verification, but currently does not do so.

### Edamam API Key
To set up the api, go to this [link](https://rapidapi.com/edamam/api/recipe-search-and-diet/), and sign up for a RapidAPI account (if you do not have one). A generated key will then be available for you to use. Please keep track of this key.

### Environment Variables
Ensure you have the following environment variables set in your run configuration:
DEV_DB_HOST=Your PSQL DB host

DEV_DB_PORT=Your PSQL DB port

DEV_DB_USER=Your PSQL DB username

DEV_DB_PASSWORD=Your PSQL DB password

GMAIL_ADDRESS=Can make random string

GMAIL_PASSWORD=Can make random string

EDAMAM_API_KEY=Your Edamam API Key

SPRING_PROFILES_ACTIVE=dev

Ensure the only active profile is dev.

### To run
Use this command in the directory to build:
```
./mvnw clean install
```

And this command to run:
```
./mvnw spring-boot:run
```

Navigate to localhost:8080.

## Contact Information
Developer: Rahil Dhodapkar
Email: rahildhodapkar@gmail.com
Thank you for taking the time to check out my project!
