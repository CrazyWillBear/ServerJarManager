package net.capbear.serverjarmanager;

public class ApplicationConfig {
    public static String startCmd = "java -jar -Xmx8G -Xms1G server.jar nogui"; // replace `8` with amount of ram you want to dedicate to the server

    public static int maxBackups = 4;

    public static String serverType = "paper";

    public static boolean restartOnCrash = true; // disable this setting in spigot.yml if true
}
