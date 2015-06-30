# Agile Delivery Services (ADS 1) FedMedCo

## http://www.fedmedco.info

This prototype provides a responsive human centered design interface that enables both novice and experienced users to search the openFDA data to identify critical drug information and display results across multiple devices.

Please login using the following information:
  * user id: john
  * password: password

## Overview

Lockheed Martin has provided a Minimum Viable Product (MVP) directed at three main categories of users: patients, pharmaceutical manufacturers, and regulatory agencies. The drug reaction and food to drug interaction features are primarily for patients seeking information about medications they are using. The interesting facts about drugs feature and the open query are for both pharmaceutical manufacturers and regulatory agencies who have pharmaceutical domain knowledge and are performing a deep dive.

## Features

* Reported Drug Reactions
* Drug to Food Interaction
* Interesting facts about drugs
* Open Query for search engine
* Multi-Device compatibility

## Description

Lockheed Martin, headquartered in Bethesda with over 113,000 employees and greater than 45B in sales, selected to put their best and brightest on the task for the 18F challenge. We are known for our engineering expertise in building complex systems, but not our agility in delivering rapid solutions.  We see this challenge as a step in the right direction in changing both government as well as our own corporate culture ensuring we are agile in delivering needed capabilities to the field.

Beginning with the user in mind our team engaged an FDA subject matter expert (SME) to identify capabilities that would be valuable to the end users of FDA applications. Our team determined to focus on three main personas: patients, pharmaceutical manufacturers, and pharmaceutical regulators.  Our understanding of the data led us to believe that 80% of the usage would be patients looking for information regarding their medications. Our user interface developer paired with the FDA SME to develop personas, scenarios, screen mockups and use cases. Next we identified architecture and technology stack needed to deliver the use cases.  

We identified the appropriate business and architectural epics that would provide the capabilities identified and then developed conditions of acceptance for those capabilities. We made use of VersionOne to track the sprint and associated metrics. The team then decomposed the epics into user stories and aligned them to the appropriate personas and linked them to test cases. Our team then set a time box of one week to complete the user stories to be delivered. Our product owner worked with the team to prioritize the user stories against business value ensuring we delivered in priority order. 

We selected to utilize scrum as our core Agile management practice due to its structure, predefined business rhythms and associated artifacts. At the start of our one week sprint the team decomposed the user stories into 2-8 hour tasks to maximize transparency into delivery.  Each of the tasks was tracked within our Agile project management tool. At the start of each day the team had a virtual daily stand-up given the distributed nature of the team across multiple time zones.  

We leveraged the architecture design developed by our solutions architect and rapidly set up the environment.  We made use of Apache Tomcat for our web server, Grails for a lightweight web framework,  HTML5/CSS3 couple with bootstrap to develop a presentation that would support responsive design; Sitemesh to provide HTML templates; MongoDB as a NoSQL database; and RESTful webservice to pull out the needed data.

Once our environment was set up we instantiated continuous integration with GIT, Jenkins, and SonarQube. Technical excellence is a key aspect of Agile architecture therefore we wanted to ensure we managed technical debt from the start. Upon the completion of each build in Jenkins the team could rapidly see if they had increased technical debt into the baseline and make decisions from a position of knowledge regarding its cleanup. With the knowledge that social coding is closely linked to the ease of being able to extend the baseline we focused on ensuring our code was the highest quality possible.

In order to realize the benefits of DevOps our team mapped the value stream to delivery at the start, leveraging Amazon Web Services (AWS) as our target environment. The team was able to deploy multiple times to AWS enabling us to remove bottlenecks early that we may not have found until the end of our time box. 

Usability testing was critical to the success of our delivery and our FDA SME spent hours verifying the capabilities and ensuring they met the acceptance criteria. With the knowledge that the patient persona would utilize the site the most the team provided access to the site to a random number of employees at Lockheed Martin with various skillsets and backgrounds. These users were able to identify issues our SME had not considered such as if the end user typed something irrelevant such as `Dog` into the search. We updated the application to provide informative error messages to the user.

In a digital society the team wanted to ensure the security of the application given the criticality of the data and the impact on the end user if the application was tampered with. The team had security scans done at multiple times during the course of the week to understand vulnerabilities being exposed and incorporating feedback to remove any security concerns.

## Design Documents

* [Architecture Diagrams](docs/Docs.md#architecture-diagrams)
* [Personas & Use Cases](docs/Docs.md#personas--use-cases)
* [Screen Mockups](docs/Docs.md#screen-mockups)

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

## Installation instructions for Amazon Linux
### Install JDK

1. Run command `uname -a` to determine what Linux version is running on the box. i686 indicates a 32-bit OS and x86-64 indicates a 64-bit OS.

2. Download the right java package from Oracle (http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)
  * jdk-8u45-linux-i586.tar.gz for 32-bit OS
  * jdk-8u45-linux-x64.tar.gz for 64-bit OS

3. Unzip and install the downloaded package in /opt
  ```Shell
  sudo mkdir /opt/java
  cd /opt/java
  tar xzf jdk-8u45-linux-x64.tar.gz
  cd /opt/java/jdk1.8.0_45/
  sudo alternatives --install /usr/bin/java java /opt/java/jdk1.8.0_45/bin/java
  sudo alternatives --config java
  ```

4. Modify your environment file (for example, for bash, modify .bashrc)
  ```Shell
  JAVA_HOME=/opt/java/jdk1.8.0_45
  export JAVA_HOME
  ```

### Install Grails
1. Download and install Grails from https://grails.org/download.html - Select 2.5.0 from "Previous Versions" drop down menu
  ```Shell
  mkdir ~/grails
  cd ~/grails/
  unzip grails-2.5.0.zip
  ```

2. Set environment variables
  ```Shell
  GRAILS_HOME=/home/ec2-user/InstalledPrograms/grails-2.5.0
  export GRAILS_HOME
  PATH=$PATH:$GRAILS_HOME/bin
  export PATH
  ```
  
### Install git
  ```Shell
  sudo yum install git
  ```

### Install MongoDB
  Create a `/etc/yum.repos.d/mongodb-org-3.0.repo ` file so that you can install MongoDB directly, using `yum`.
  ```Shell
  sudo su
  cd /etc/yum.repos.d/
  vi mongodb-org-3.0.repo
  ```

  * Add the following
     ```Shell
     [mongodb-org-3.0]
     name=MongoDB Repository
     baseurl=https://repo.mongodb.org/yum/amazon/2013.03/mongodb-org/3.0/x86_64/
     gpgcheck=0
     enabled=1
     ```
     
  * Now install Mongod
    ```Shell
    sudo yum install -y mongodb-org-3.0.4 mongodb-org-server-3.0.4 mongodb-org-shell-3.0.4 mongodb-org-mongos-3.0.4 mongodb-org-tools-3.0.4
    ```

  * Start MongoDB
    ```Shell
    sudo service mongod start
    ```

### Install Tomcat 8
  ```Shell
  yum install tomcat8
  yum install tomcat8-webapps
  ```

### Get the source code from git
  ```Shell
  git clone https://github.com/anuchandpamra/Fedmedco.git
  ```

### Deploy to Tomcat
  ```Shell
  cd FedMedCo
  git pull
  grails war
  sudo su
  mv target/FedMedCo.war /usr/share/tomcat8/webapps
  service tomcat8 restart
  tail -f /var/log/tomcat8/catalina.out
  ```

## Monitoring

The application is monitored via the the Amazon Web Services (AWS) control panel. See screenshots of our [AWS Monitoring](docs/Docs.md#monitoring).

## API Reference

[https://open.fda.gov/api/reference/](https://open.fda.gov/api/reference/)

## Test

[Latest Test Report](docs/test-reports/TestReports.md)

## Contributors

Lockheed Martin

## Contact Information
### Technical POC
    Robin Yeman
    1800 Jonathan Way
    Reston VA 20190
    Email: robin.yeman@lmco.com
    571-535-5854
    
### Management POC
    Phil Magrogan
    2453 Dakota Lakes Dr. 
    Oak Hill VA 20171
    Email: phil.magrogan@lmco.com
    703-336-2328

## License

All license agreements are Free and Open Source
* [Apache 2.0](http://www.apache.org/licenses/LICENSE-2.0)
* [MIT](http://opensource.org/licenses/MIT)
* [GPL-3.0](http://www.gnu.org/licenses/gpl-3.0.en.html)
* [OpenSymphony 1.1](https://svn.apache.org/repos/asf/tiles/framework/trunk/OGNL-LICENSE.txt)
