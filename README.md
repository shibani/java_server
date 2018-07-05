##To package server/create project in Intellij:
Install Intellij:
Clone git repo for the Java server - https://github.com/shibani/java_server
Open Intellij and select the option to import into Intellij (keep all settings the same, accept all the defaults)
Brew install maven
Build the executable jar file by running mvn package in terminal in root of your server dir
Set up cobspec as below

To run Cobspec tests:
In Terminal:
Get source code:
Copy repo URL:
In browser, navigate to: https://github.com/8thlight/cob_spec
Copy URL: Clone or Download → Copy icon
Get code:
cd [directory name]
git clone https://github.com/8thlight/cob_spec.git
Update jar file: mvn package
Check version: javac -version
If version is not 1.8, set it:
alias setJdk8='export JAVA_HOME=$(/usr/libexec/java_home -v 1.8)'
setJdk8
Run jar file: java -jar fitnesse.jar -p 9090
Add port and destination to cobspec by clicking on the HTTPTestSuite link in localhost:9090 and then clicking on edit. Edit the path to your jar file and then edit the path to your cobspec’s public folder
In browser, navigate to: http://localhost:9090 → click link HttpTestSuite → click menu option Suite
