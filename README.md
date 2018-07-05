## This project runs on JDK8   
* To install JDK8, visit:  

   http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html  

   Accept the License Agreement and install Java SE Development Kit 8u172  

## To create the project in Intellij and package your server:  
* Install Intellij    
* Clone git repo for the Java server - https://github.com/shibani/java_server  
* Open Intellij and select the option to __import__ into Intellij  
    * keep all settings the same, accept all the defaults
* Then in __Terminal__
    * run __brew install maven__ in terminal  
    * run __mvn package__ in the root of your server dir to build the executable jar file  
* Set up cobspec as below

## To run Cobspec tests:  
#### In Terminal:
* __Get source code:__  
    * __Copy repo URL:__
      * In browser, navigate to: https://github.com/8thlight/cob_spec
      * Copy URL: Clone or Download → Copy icon
    * __Get code:__
      * cd [directory name]
      * git clone https://github.com/8thlight/cob_spec.git
* __Update jar file:__ mvn package
* __Check JDK version:__ javac -version
    * If version is not 1.8, set it:
      * alias setJdk8='export JAVA_HOME=$(/usr/libexec/java_home -v 1.8)'
      * setJdk8
* __Run jar file:__ java -jar fitnesse.jar -p 9090
* Add port and destination to cobspec by clicking on link __HttpTestSuite__ in localhost:9090 and then clicking on __edit__   
* Edit the path to your jar file and then edit the path to your cobspec’s public folder  
#### In browser, navigate to: http://localhost:9090  
* click on link __HttpTestSuite__  
* then click on menu option __Suite__
