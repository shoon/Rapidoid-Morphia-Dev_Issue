# Rapidoid and Morphia integration test
This project highlights a method to integrate Morphia into a Rapidoid-web project.

## Requirements
1. MongoDB instance installed and running with no auth on localhost
2. Java 1.8.x
3. Maven

## API test
Use curl or a REST client / browser plugin to POST and GET. General steps
1. Run this project (defaults to dev mode and runs on port 8888)
2. Save a message to the database
    ```
    $ curl -s -X POST -d 'test' http://localhost:8888/m
    "5760a254141618b9a0ac92db"
    ```
3. Get a message by ObjectId from the database
    ```
    $ curl -s -X GET http://localhost:8888/m/5760a254141618b9a0ac92db
    "Hello : 0:0:0:0:0:0:0:1, you asked for message id 5760a254141618b9a0ac92db, what I found: test"
    ```
4. Modify some code in this project and hit save
5. Rapidoid will indicate the project will restart
    ```
    INFO | watcher1 | org.rapidoid.setup.App | Detected class or resource changes
    ```
6. Repeat step #3
    ```
    $ curl -s -X GET http://localhost:8888/m/5760a254141618b9a0ac92db
    {
        "error": "Can not set org.bson.types.ObjectId field com.example.rapidoid.morphia.dev_issue.models.Message.id to com.example.rapidoid.morphia.dev_issue.models.Message",
        "code": 500,
        "status": "Internal Server Error"
    }
    ```
