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
  2) Download the dev_tomcat images
    ```shell
    docker pull g40st/docker_dev_tomcat
    ```


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
