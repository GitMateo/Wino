# WIT-java-file-transporterProject
A school project of an app in SWING that does multithreaded copying of files.


The app: 
- will have a button for picking the source folder which contents you want to copy
- another button for picking the destination folder
- area for putting the mask of file selection ( default would be: *.* ) used to copy files from folders nested starting from the one selected in source folder
- The interface needs start button with an icon that will be easily recognizable as start button, this button begins the copying process. After pressing the button it should validate the input parameters. In case of detecting errors  ( such as the lack of wrong values ), the program should show a popup with the error to inform the user about it. If the parameters are correct ( the source folder, destination folder and copying mask ) it should start the process of multithreaded copying with the mask.

REQUIREMENTS:

- It's a maven project where we want to use the least amount of external dependencies 
- the result package should be a .jar 
- Its sources and classes should work with Java 11
- App needs an WINDOW interface in SWING, multithreaded, with handling of thread pools
- handling of parameters in its own class: amount of threads in a pool
- the code needs to have unit tests
- code needs to contain comments to classes, methods and their variables
- project needs proper javadoc

TO CONSIDER: 

- Error/Exception Handling
- Progress Indicator
- Cancel Functionality
- Thread pool validation

TO DO:

- Setting up the Maven project and project structure. [DONE]
- Designing and implementing the Swing GUI. [DONE]
- Implementing the multithreaded file copying functionality.
- Implementing the input validation and error handling.
- Implementing the unit tests.
- Generating Javadoc.
- Packaging the project into a .jar file.
