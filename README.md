# Agile Delivery Services (ADS 1) FedMedCo

## http://www.fedmedco.info

Provides a responsive human centered design interface that enables both novice and experienced users to search the openFDA data to identify critical drug information and display results across multiple devices.

Please login using the following information:
user id: john
password: password

## Features

* Reported Drug Reactions
* Drug to Food Interaction
* Interesting facts about drugs
* Open Query for search engine
* Multi-Device compatibility

## Web Technologies

|   | **Technology** | **Description** | **License** | **Version** | **Date** |
| --- | --- | --- | --- | --- | --- |
| 1. | Grails | Web Framework | Apache 2 | 2.5 | 2015 |
| 2. | Groovy | Language | Apache 2 | 2.4 | 2015 |
| 3. | HTML5/CSS3 using Bootstrap | Presentation / Responsive design supporting multi-device | MIT | 3.3.4 | 2015 |
| 4. | Sitemesh | HTML template framework based on Decoration Model | OpenSymphony(Apache) | 2.1.5 | 2015 |
| 5. | GORM | Object relationship mapping | Apache 2 | 2.5.0 | 2015 |
| 6. | MongoDB | NoSQL database JSON-like documents | GNU GPL | 3.0.4 | 2015 |
| 7. | JQuery | JavaScript library | MIT | 1.11.1 | 2014 |
| 8. | Selenium | Web Testing | Apache 2 | 2.46 | 2015 |
| 9. | Spock | Integration Test | Apache 2 | 0.7 | 2012 |

## Approach

Lockheed Martin has provided a Minimum Viable Product (MVP) directed at three main categories of users: patients, pharmaceutical manufacturers, and regulatory agencies. The drug reaction and food to drug interaction features are primarily for patients seeking information about medications they are using. The interesting facts about drugs feature and the open query are for both pharmaceutical manufacturers and regulatory agencies who have pharmaceutical domain knowledge and are performing a deep dive.

## Design Documents

* [Architecture Diagrams](docs/#architecture-diagrams)
* [Personas & Use Cases](docs/#personas--use-cases)
* [Screen Mockups](docs/#screen-mockups)

## Installation instructions for Amazon Linux
### Install JDK

1. Run command `uname -a`` to determine what Linux version is running on the box. i686 indicates a 32 bit OS and x86-64 indicates a 64bit OS.

2. Download the right java package from Oracle (http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)
  - jdk-8u45-linux-i586.tar.gz for 32 bit OS
  - jdk-8u45-linux-x64.tar.gz for 64 bit OS

3. Install in /opt

   `sudo mkdir /opt/java`

3. Unzip the downloaded package

  `cd /opt/java`

  `tar xzf jdk-8u45-linux-x64.tar.gz`

4. `cd /opt/java/jdk1.8.0_45/`
5. `sudo alternatives --install /usr/bin/java java /opt/java/jdk1.8.0_45/bin/java 2`
6. `sudo alternatives --config java`


6. Modify your environment file (for example, for bash, modify .bashrc)
  
  `JAVA_HOME=/opt/java/jdk1.8.0_45`
  
  `export JAVA_HOME`
  


### Install Grails
1. Download Grails  from https://grails.org/download.html - Select 2.5.0 from "Previous Versions" drop down menu
2. `mkdir ~/grails`
3. `cd ~/InstalledPrograms/`
4. `unzip grails-2.5.0.zip`
5. Set environment variables
  
  `GRAILS_HOME=/home/ec2-user/InstalledPrograms/grails-2.5.0`
  
  `export GRAILS_HOME`    
  
  `PATH=$PATH:$GRAILS_HOME/bin`
  
  `export PATH`
  
### Install git
  `sudo yum install git`

### Install MongoDB
  
  `sudo su`
  
  `cd /etc/yum.repos.d/`
  
  `vi mongodb-org-2.6.repo`  

  * Add the following
  
     `[mongodb-org-3.0]`
  
     `name=MongoDB Repository`
  
     `baseurl=https://repo.mongodb.org/yum/amazon/2013.03/mongodb-org/3.0/x86_64/`
  
     `gpgcheck=0`
  
     `enabled=1`
     
  * Now install Mongod
    
    `sudo yum install -y mongodb-org-3.0.4 mongodb-org-server-3.0.4 mongodb-org-shell-3.0.4 mongodb-org-mongos-3.0.4 mongodb-org-tools-3.0.4`

  * Start MongoDB
    
    `sudo service mongod start`

### install tomcat8
  
  `yum install tomcat8`
  
  `yum install tomcat8-webapps`

### Get the source code from git
  
  `git clone https://github.com/anuchandpamra/Fedmedco.git`

### Deploy to tomcat
  
  `cd FedMedCo`
  
  `git pull`
  
  `grails war`
  
  `sudo su`
  
  `mv target/FedMedCo.war /usr/share/tomcat8/webapps`
  
  `service tomcat8 restart`
  
  `tail -f /var/log/tomcat8/catalina.out`

## Deployment

## Monitoring

The application is monitored via the the Amazon Web Services (AWS) control panel. See screenshots of our [AWS Monitoring](docs/#monitoring).

## API Reference

[https://open.fda.gov/api/reference/](https://open.fda.gov/api/reference/)

## Test

[Latest Test Report](docs/test-reports/html/all.html)

## Contributors

Lockheed Martin

## License

All license agreements are Free and Open Source
[http://www.apache.org/licenses/License-2.0](http://www.apache.org/licenses/License-2.0)
[http://opensource.org/licenses/MIT](http://opensource.org/licenses/MIT)
[http://www.gnu.org/licenses/gpl-3.0.en.html](http://www.gnu.org/licenses/gpl-3.0.en.html)
[https://svn.apache.org/repos/asf/tiles/framework/trunk/OGNL-LICENSE.txt](https://svn.apache.org/repos/asf/tiles/framework/trunk/OGNL-LICENSE.txt)
