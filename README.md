# ListLinks

This solution will track all links from a given URL. 
For Example:
If you use https://www.ibm.com/br-pt/ for Input, all url with tag a as href will be tracked and the system will track again all URL´s for each "Child" URL.

- The app needs to receive an URL;
- The app will find all links inside this given URL;
- The app will save this links found in database (H2)
- The app will list all links saved in the database.
- After collecting all links from the initial URL. Collect from the newly found links. I mean, the system gets the first link saved, and start the process (get all links and keep on the database). Follow does it to the second, third and successively until the last link saved and tracked.

# Creating Deployment Package

With this source code is possible to create an Deployment Package called FatJar.
just use command mvn package to create .jar file.
To create Jar file you need to have installed in your machine mvn and jdk 1.8

You can download .jar file from: https://bit.ly/2Z8jvKo

# Running

To start this application just go to the folder where you saved .jar file and with Prompt use command 'java -jar LinkList-0.0.1-SNAPSHOT.jar'

After Install just run http://localhost:8080 in your browser or mapped link.

## Operation
Just input an Full URL path (including https:// or http://) and click on Track! button.

## Usage
Spring Boot
MVN
JDK1.8
H2 DB
Tomcat
