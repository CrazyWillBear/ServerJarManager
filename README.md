# ServerJarManager

## Summary
A wrapper for Minecraft server jars, configurable with start command, max backups, server type, and restartOnCrash.

## Usage
Configure in ApplicationConfig.java, build with Maven, then place the compiled .jar file in the same directory as your server, making sure that the eula.txt is filled out. Run the jar file, and `.help` to see a list of commands.

## Warnings
- ***RELIES ON A DEPRECATED API -- DOESN'T UPDATE JARS***
- ***DO NOT RUN WITHOUT FIRST AGREEING TO eula.txt IN YOUR SERVER***
- ***DISABLE RESTARTONCRASH IN spigot.yml IF ENABLED IN SERVERJARMANAGER***

## License
MPL 2.0, because I like it.
