# Webbasierte Strichliste

Dieses Projekt stellt eine webbasierte Strichliste dar. Das Projekt wurde erfolgreich auf einem Tomcat (Version 7.0.77) deployt. Gegenfalls muss die build.xml angepasst werden. Im Projekt sind Windows als auch Linux vermerke. 

# Tomcat User anpassen (fuers deployen über ant):
Datei: conf/tomcat-users.xml

<tomcat-users>
  <role rolename="manager-gui"/>
  <user username="admin" password="admin" roles="manager-gui,admin-gui,manager-script,admin-script"/>
</tomcat-users>

# Screenshots

![Alt text](/Stuff/Home.jpg?raw=true "home view")


![Alt text](/Stuff/Drink.jpg?raw=true "home view")

Christian Högerle
