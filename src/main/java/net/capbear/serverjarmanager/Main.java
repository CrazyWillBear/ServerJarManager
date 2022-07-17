// This Source Code Form is subject to the terms of the Mozilla Public
// License, v. 2.0. If a copy of the MPL was not distributed with this
// file, You can obtain one at http://mozilla.org/MPL/2.0/.

package net.capbear.serverjarmanager;

import java.util.Scanner;

import static java.lang.Thread.sleep;

public class Main {

    public static void main(String args[]) throws Exception {
        Scanner scan = new Scanner(System.in);
        Thread thread = null;

        PreRun.preRun();

        Backups.backupServer();

        Server.start();
        if (ApplicationConfig.restartOnCrash) {
            thread = Server.restartServerFollowingServerClose();
        } else {
            Server.closeProgramFollowingServer();
        }

        ServerJar.updateJar();

        while (true) {
            String input = scan.nextLine();

            if (input.toCharArray()[0] != '.') {
                Server.runCommand(input);
            } else {
                switch (input.split(" ")[0]) {
                    case ".help":
                        System.out.println("::Help Menu:\n" +
                                                "\t- .stop: stops server and kills client\n" +
                                                "\t\t- .stop announce: countdown shutdown from 10 seconds\n" +
                                                "\t- .restart: stops and starts server\n" +
                                                "\t- .alert <alert>: sends alert to everyone on server");
                        break;

                    case ".stop":
                        if (input.split(" ").length > 1 && input.split(" ")[1].equalsIgnoreCase("announce")) {
                            Server.runCommand("tellraw @a \"§3§l[Server] §r§3The server is shutting down in 10 seconds...\"");
                            sleep(7000);
                            Server.runCommand("tellraw @a \"§3§l[Server] §r§3The server is shutting down in 3 seconds...\"");
                            sleep(1000);
                            Server.runCommand("tellraw @a \"§3§l[Server] §r§3The server is shutting down in 2 seconds...\"");
                            sleep(1000);
                            Server.runCommand("tellraw @a \"§3§l[Server] §r§3The server is shutting down in 1 seconds...\"");
                            sleep(1000);
                            Server.runCommand("tellraw @a \"§3§l[Server] §r§3The server is shutting down...\"");
                        }
                        System.out.println("::Stopping server and client...");
                        thread.stop();
                        Server.stop();
                        return;

                    case ".restart":
                        Server.runCommand("tellraw @a \"§3§l[Server] §r§3The server is restarting in 10 seconds...\"");
                        sleep(7000);
                        Server.runCommand("tellraw @a \"§3§l[Server] §r§3The server is restarting in 3 seconds...\"");
                        sleep(1000);
                        Server.runCommand("tellraw @a \"§3§l[Server] §r§3The server is restarting in 2 seconds...\"");
                        sleep(1000);
                        Server.runCommand("tellraw @a \"§3§l[Server] §r§3The server is restarting in 1 seconds...\"");
                        sleep(1000);
                        Server.runCommand("tellraw @a \"§3§l[Server] §r§3The server is restarting...\"");
                        Server.stop();
                        Backups.backupServer();
                        Server.start();
                        break;

                    case ".alert":
                        Server.runCommand("tellraw @a \"§3§l[Server] §r§3" + input.replace(".alert ", "") + "\"");
                        break;

                    default:
                        System.out.println("::Unknown command, run .help for help");
                        break;
                }
            }
        }
    }
}
