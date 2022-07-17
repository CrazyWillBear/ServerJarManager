package net.capbear.serverjarmanager;

public class ServerJar {
    public static void updateJar() throws Exception {
        if (FileHandler.needsUpdate()) {
            System.out.println("::Updating server jar to latest...");
            Util.downloadFile("https://serverjars.com/api/fetchJar/" + ApplicationConfig.serverType, "server.jar");
            System.out.println("::Updated server jar to latest");
        }
        System.out.println("::Server jar on latest version");
    }
}
