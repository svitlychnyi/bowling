//////////////////////////////////////////////////////////////////////////////////
//                   Bowling Game Score Tracker Application for Conject
//////////////////////////////////////////////////////////////////////////////////

1. ABOUT:
    This is test task for Conject.
    Main task of the application is to provide possibility to track scores for Bowling game.
    Current implementation supports Single Player mode with Console (NOGUI) user interface.

    Sample output example:
        =====================================================================
        Welcome
        Please give the single hit result from 0 to 10, or 'q' to exit.
        1
        ----------------------------------------------------------
        | user || 1 (1) ||  ||  ||  ||  ||  ||  ||  ||  ||  || 1 |
        ----------------------------------------------------------
        Please give the single hit result from 0 to 10, or 'q' to exit.
        2
        --------------------------------------------------------------
        | user || 1 / 2 (3) ||  ||  ||  ||  ||  ||  ||  ||  ||  || 3 |
        --------------------------------------------------------------
        Please give the single hit result from 0 to 10, or 'q' to exit.
        q
        Goodbye
        =====================================================================

    Log file is located in build directory (target/logs/bowling.log)
    log4j was used for logging and its configuration file located in src/main/resources

2. BUILD:
    Project can be build using following command:
        =====================================================================
        mvn clean install
        =====================================================================

    The build output will be a jar file with dependencies located in target folder:
            bowling-1.0-SNAPSHOT.jar

    Following environment was used for development:
        - Apache Maven 3.0.5
        - Java(TM) SE Runtime Environment (build 1.8.0_91-b14) (jdk1.8.0_91)
        - Linux (Ubuntu 14.04, build: 3.13.0-67-generic)

    Static code analysis will take place and build will fail in case bugs are found.
    Following tools are used:
        - PMD maven plugin, version 3.6
        - FindBugs maven plugin, version 3.0.3

    Unit tests will be started during build
    Following tools where used for unit test:
        - JUnit, version 4.12
        - EasyMock, version 3.4
        - Cobertura maven plugin, version 2.7

    Unit tests code coverage can be collected by running following command:
        =====================================================================
        mvn cobertura:cobertura
        =====================================================================

3. RUN:
    To run application from command line use following command:
        =====================================================================
        java -jar bowling-1.0-SNAPSHOT.jar
        =====================================================================

    NOTE: On some environments permissions should be set on jar file in order make it executable

4. TODO:
    - Implement language switching mechanism using UserInterface and Language enum