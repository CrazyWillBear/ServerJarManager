package net.capbear.serverjarmanager;

public class PreRun {
    public static void preRun() throws Exception {
        Process p = Runtime.getRuntime().exec("mkdir -p backups");
    }
}
