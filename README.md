# java-sdk
<Initial version>

Steps to start this application: 
1. Clone this repository and check out to **sdk-init** branch.
2. Create a table under the schema **sample** and table name as **post_contents** in your mysql server localhost.

   CREATE TABLE sample.post_content (
    id VARCHAR(50) NOT NULL AUTO_INCREMENT,
    post_name VARCHAR(100),
    post_contents VARCHAR(500),
    PRIMARY KEY (id)
   );

3. Start the docker daeman : command : **dockerd**
4. Change the directory to the root of the porject in another terminal : **cd java-sdk**
5. Execute the docker compose file to bring up the Jaegar server : **docker compose up**
   docker-compose.yml is placed in the root directory. So it will pick the configuations from here for Jaegar.
6. Build the java application : **mvn clean install -D skipTests**
7. Make sure the jar file is created under the project's **target** folder.
8. Execute the command :
   **java -jar target/java-sdk-0.0.1-SNAPSHOT.jar --spring.application.name=java-sdk --server.port=8080**

9. Now Make post call to the service on postman:

   URL : http://localhost:8080/api/createNewPost
   Method : POST
   Body :
   {
    "postName": "Sample",
    "postContents": "Random things"
   }

10. Reload Jaegar and click on find traces.

