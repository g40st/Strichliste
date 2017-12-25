# Web-based tally list

This is a web-based tally list to track the drinks of some users. You can specify the users in the source code.

![strichliste](https://user-images.githubusercontent.com/7523395/34341782-252e2f6a-e99f-11e7-99d3-f6b987df4d0c.gif)

## Dependencies
* Apache Tomcat
* [jQuery](https://jquery.com/)
* [Bootstrap](http://getbootstrap.com/)
* Apache ant
* Java JDK

```shell
apt-get install ant
apt-get install openjdk-8-jdk
```

## Installing / Getting started (Linux)
  1) Download Apache Tomcat (tested under Apache Tomcat 8.5.24)
  2) Copy the files to /opt/tomcat
  3) Adopt the tomcat users:
  
      File: opt/tomcat/conf/tomcat-users.xml
      ```xml
      <tomcat-users>
        <role rolename="manager-gui"/>
        <user username="admin" password="admin" roles="manager-gui,admin-gui,manager-script,admin-script"/>
      </tomcat-users>
      ```
   4) starting tomcat using: 
   ```shell 
   /opt/tomcat/bin/startup.sh 
   ```
   5) Go to the repository directory and run ant
   6) At the first deployment you have to deploy the .war file by hand. Use the build-in manager. This manager is avaliable under <IP_ADDRESS>:8080/manager/html. Then go to "WAR file to deploy" and select the .war file to deploy it.

## Using
  Use your Browser and go to "http://<IP_ADDRESS>:8080/strichliste/#"

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
