# Webbasierte Strichliste

Dieses Projekt stellt eine webbasierte Strichliste dar. Das Projekt wurde erfolgreich auf einem Tomcat (Version 7.0.77) deployt. Gegenfalls muss die build.xml angepasst werden. Im Projekt sind Windows als auch Linux vermerke. 

# Tomcat User anpassen (fuers deployen über ant):
Datei: conf/tomcat-users.xml

```xml
<tomcat-users>
  <role rolename="manager-gui"/>
  <user username="admin" password="admin" roles="manager-gui,admin-gui,manager-script,admin-script"/>
</tomcat-users>
```

# Screenshots

![ScreenShot](https://raw.githubusercontent.com/g40st/Strichliste/master/Stuff/Home.png)

![ScreenShot](https://raw.githubusercontent.com/g40st/Strichliste/master/Stuff/Drink.png)

Christian Högerle
