# WINded Health Tracker Application
## SENG202 Team 8
##### Lorenzo Fasano (lfa69, 34853558)
##### Sam Verdellen (sgv15, 33899963)
##### Will Wallace (wwa52, 11389447)
##### Jack Orchard (jco165, 49860178)
##### Joel Ridden (jnr26, 97580731)
##### Clarke Mcfadzien (cmc280, 75890469)

## Requirements
Please download the following tools:

1. Git (How to install git: https://www.linode.com/docs/development/version-control/how-to-install-git-on-linux-mac-and-windows/)
2. Maven (How to install Maven: http://maven.apache.org/install.html )
3. Oracle Java 1.8 (How to install Oracle Java 1.8: https://www.oracle.com/technetwork/java/javase/downloads/index.html )

## Download repository
To download the repository, you need to:

1. Open a Terminal Window
2. Navigate to the directory where you wish to store the code
3. Type in the terminal:```$ git clone https://eng-git.canterbury.ac.nz/lfa69/SENG202Team8.git```

Following these instructions creates a new copy of the WINded application code in the directory where the git command is called.

## Importing project into IntelliJ

To import the project into IntelliJ you need to:
1. Open IntelliJ
2. On the initial menu click on the 'Import Project' option 
3. Navigate to the folder downloaded from git
4. Select this folder and confirm
5. In the 'Import Project' pop-up window that just appeared select the second option ('import project from external module') and then select the Maven option.
6. The 'Import Project' window should now display many options, you can now do any editing on the way IntelliJ interacts with the project, to open the project though you are only required to tick the 'Search for project recursively' box and you can leave the other values as default and press 'Next'
7. Now select the seng202.group8:HealthTrackerApp:1.0-SNAPSHOT[HealthTrackerApp] option and click 'Next'
8. Select the java SDK you want to use for the project (highly recommended java-8-oracle version, due to JavaFX possible compatibility errors with newer and older versions), then click 'Next'
9. Finally select the name you want to use for the project or leave it as default and click 'Finish'

IntelliJ should now display the project structure

## Packaging a .jar file

To package a .jar file you need to:

1. Navigate to the directory where you downloaded the WINded application code from the already opened terminal
2. Navigate to “/SENG202Team8/HealthTrackerApp” running the following command in the terminal: ```$ cd SENG202/HealthTrackerApp```
3. Type: ```$ mvn package``` in the terminal and press enter

Following these instructions creates a .jar file, follow the next sections steps to run the .jar file



## Run the .jar file 

To run the .jar file you need to:

1. Navigate to “target” folder by running the following command in the terminal: ```$ cd target``` (if you are in the same directory where you performed the mvn package command)
2. Type the following command in the terminal: ```$ java -jar HealthTrackerApp-1.0-SNAPSHOT.jar``` and press enter

This will run the whole application.