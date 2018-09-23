WINded Health Tracker Application
SENG202 Team 8
Lorenzo Fasano (lfa69, 34853558)
Sam Verdellen ()
Will Wallace ()
Jack Orchard ()
Joel Ridden ()
Clarke Mcfadzien ()
Requirements
    • Git (How to install git: https://www.linode.com/docs/development/version-control/how-to-install-git-on-linux-mac-and-windows/)
    • Maven (How to install Maven: http://maven.apache.org/install.html )
    • Oracle Java 1.8 (How to install Oracle Java 1.8: https://www.oracle.com/technetwork/java/javase/downloads/index.html )

Download repository
To download the repository, you need to:
    • Open a Terminal Window
    • Navigate to the directory where you wish to store the code
    • Type: git clone https://eng-git.canterbury.ac.nz/lfa69/SENG202Team8.git in the terminal and press enter
Following these instructions creates a new copy of the WINded application code in the directory where the git command is called.

Packaging a .jar file
 To package a .jar file you need to:
    • Navigate to the directory where you downloaded the WINded application code from the already opened terminal
    • Navigate to “/SENG202Team8/HealthTrackerApp” running the following command in the terminal: cd SENG202/HealthTrackerApp
    • Type: mvn package in the terminal and press enter
Following these instructions creates a .jar file, follow the next sections steps to run the .jar file



Run the .jar file 

To run the .jar file you need to:
    • Navigate to “target” folder by running the following command in the terminal: cd target (if you are in the same directory where you performed the mvn package command)
    • Type the following command in the terminal: java -jar HealthTrackerApp-1.0-SNAPSHOT.jar and press enter
This will run the whole application.