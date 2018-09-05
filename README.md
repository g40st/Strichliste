# Web-based tally list (docker)

This is a web-based tally list to track the drinks of some users. You can specify the users in the source code.

ATTENTION: There are no security measures implemented like session handling. You can reengineer the communication protocol to access all functions. Therefore do not use this web-based tally list on a public network.

![strichliste](https://user-images.githubusercontent.com/7523395/34341782-252e2f6a-e99f-11e7-99d3-f6b987df4d0c.gif)

## Dependencies
* Apache Tomcat
* [jQuery](https://jquery.com/)
* [Bootstrap](http://getbootstrap.com/)
* Apache ant
* Java JDK


## Installing / Getting started (Docker)
  1) Install docker on your system
  2) Download the dev_tomcat image
   ```shell
    docker pull g40st/docker_dev_tomcat
   ```
  3) Download the repository to your local system. That could look like:
    
    /home/ghost/Project/Web-based-tally-list
  
  4) Start the docker image using this command:
  ```shell
   docker run --rm -p 8888:8080 -v /home/$USER/Project:/home/user/project -v /home/$USER/Downloads/StrichlisteDaten:/home/user/data -v /home/$USER/Downloads/StrichlisteDaten/Backup:/home/user/backup  g40st/docker_dev_tomcat:latest
   ```
  -rm   ->  deletes the container 
  
  -p 8888:8080  ->   maps the port 8080 (tomcat) to the port 8888 (host)
  
  -v .../home/user/project ->  maps project directory from the hostsystem to the container
  
  -v .../home/user/data ->  maps the db-file to a directory on the hostsystem
  
  -v .../home/user/backup -> maps the db-file to a directory on the hostsystem

![dataSchema](https://user-images.githubusercontent.com/7523395/45096074-f9550000-b11f-11e8-8c0c-3c53cb63f073.png)

  5) Connect to the container: (You can get the actual container name by docker ps)
  ```shell
    docker exec -it <container_name> bash   
  ```
  6) Now you can compile the tally list on the container:
  ```shell
    cd /home/user/project/Web-based-tally-list/
    ant
  ```
  7) You can access the tomcat server and the tally list with:
      http://localhost:8888/strichliste/#
    

## Code Editing
  Edit the code on your local system (/home/ghost/Project/Web-based-tally-list). Then use the bash in the container to run ant on the container. 

## Using
  Use your browser and go to "http://<IP_ADDRESS>:8888/strichliste/#"

## Nice To Know

### Database
There will be a kind of database stored on your local filesystem. The database will be stored on your local user directory. The file is named "StrichlisteDB.txt".

### Users
Adopt the array size and the array elements. After that run ant and you will get your users.

```java
    private static final User[] users = new User[4];

    private Singleton() {
        users[0] = new User("user1");
        users[1] = new User("user2");
        users[2] = new User("user3");
        users[3] = new User("user4");
 ```

## Author
Christian Högerle

## Licensing
The code in this project is licensed under MIT license.
