# Quickbooks OAuth2

Get initial access and refresh tokens for your Quickbooks app.

## What provides

After authentication, it shows:

- an access token
- a refresh token
- a realm id

## Requirements

In order to successfully run this sample app you need a few things:

1. Java 1.8
2. A [developer.intuit.com](http://developer.intuit.com) account
3. An app on [developer.intuit.com](http://developer.intuit.com) and the associated client id and client secret.

## First Use Instructions

Clone the GitHub repo to your computer

## Running the code

Once the sample app code is on your computer, you can do the following steps to run the app:

1. cd to the project directory</li>
2. Run the command:`./gradlew bootRun` (Mac OS) or `gradlew.bat bootRun` (Windows)</li>
3. Wait until the terminal output displays the "Started Application in xxx seconds" message.
4. Your app should be up now in [http://localhost:8080/](http://localhost:8080/)
5. Navigate to [http://localhost:8080/](http://localhost:8080/), fill the form and submit it
6. Copy your tokens

## Run the existing Docker image

Run with

```bash
docker run -it --rm --name quickbooks-oauth2 -p 8080:8080 ezvpn/quickbooks-oauth2
```

and stop it with ctrl-c

## Build your Docker image

Build and run image with

```bash
./gradlew build && docker build --build-arg JAR_FILE=build/libs/Quickbooks-OAuth2.jar -t <your tag> .
docker run -it --rm --name quickbooks-oauth2 -p 8080:8080 <your tag>
```
