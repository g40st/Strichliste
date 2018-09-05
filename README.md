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
  short video: https://youtu.be/0M3CPRcA-HA

  1) Install docker on your system
  2) Download the tomcat image
```shell
docker pull g40st/docker_tally_list
```
  3) Run the docker image:
```shell
docker run --rm -p 8888:8080 -v /home/$USER/Downloads/TallyList:/home/user/data -v /home/$USER/Downloads/TallyListBackup:/home/user/backup  g40st/docker_tally_list:latest
```

  -v /home/$USER/Downloads/TallyList:... -> This is the location, where the databse will be mounted on the host system. You can specify the location. 

  -v /home/$USER/Downloads/TallyListBackup -> The second volumne contains a backup of the database. You can point this volume to a USB stick or network drive.

  4) Copy the conf/sample-users.txt (repo) in the specified directory (/home/$USER/Downloads/TallyList) and rename it to users.txt. You can also edit the users of the application.
      
  5) Reload the application by the tomcat manager.


## Development
  1) Install docker on your system
  2) Download the dev_tomcat image
```shell
docker pull g40st/docker_dev_tomcat
```
  3) Download the repository to your local system. That could look like:
 ```shell
/home/ghost/Project/Web-based-tally-list
```   
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
cd /home/user/project/Web-based-tally-list/ && \
cp /home/user/project/Web-based-tally-list/conf/sample-users.txt /home/user/data/users.txt && \
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
You can edit the users by editing the users.txt. This file is mapped to the filesystem of the host. You can specify the location of the file. After editing the file you have to reload the applicaiton.

```shell
-v /home/$USER/Downloads/StrichlisteDaten:/home/user/data
```


## Author
Christian Högerle

## Licensing
The code in this project is licensed under MIT license.
