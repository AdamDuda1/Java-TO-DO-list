package app;

import java.io.File;
import java.util.ArrayList;
import java.util.Objects;

public class Settings {

    public void openSettings(ArrayList<String> lines) {
        while (true) {
            Utils.cls();

            Utils.i("\n  -----    /--\\    |--\\     /--\\");
            Utils.i("    |     |    |   |   |   |    |    |\\  |  |---");
            Utils.i("    |     |    |   |   |   |    |    | \\ |  |---");
            Utils.i("    |      \\--/    |--/     \\--/     |  \\|  |---");
            Utils.i("                                    by Adam Duda\n");

            Utils.i("Settings:");
            Utils.i("\ne - back to the main menu");
            Utils.i("sn - change name (" + Utils.PURPLE + Menu.name + Utils.CYAN + ")                (COMING SOON)");
            Utils.i("se - change encryption                (COMING SOON)");
            Utils.i("sp - change password (if encryption is  enabled)                (COMING SOON)");
            Utils.i("st - change preferred list type                (COMING SOON)");
            Utils.i("d - delete all data");

            System.out.print(Utils.PURPLE);
            String choice = Menu.scanner.nextLine();
            System.out.print(Utils.RESET);

            if (Objects.equals(choice, "e")) {
                break;
            } else if (Objects.equals(choice, "d")) {
                Utils.cls();

                Utils.i("\n  -----    /--\\    |--\\     /--\\");
                Utils.i("    |     |    |   |   |   |    |    |\\  |  |---");
                Utils.i("    |     |    |   |   |   |    |    | \\ |  |---");
                Utils.i("    |      \\--/    |--/     \\--/     |  \\|  |---");
                Utils.i("                                    by Adam Duda\n");

                Utils.i("Are you sure? This action will delete all the user data. To confirm, type \"delete\". Enter anything else to cancel the operation.");

                System.out.print(Utils.PURPLE);
                String choiceTemp = Menu.scanner.nextLine();
                System.out.print(Utils.RESET);

                if (Objects.equals(choiceTemp, "delete")) {
                    Utils.i("");

                    File temp;

                    for (int i = 0; i < lines.size(); i++) {
                        temp = new File(lines.get(i) + ".list");
                        Utils.i("Delete: " + temp.getPath());
                        if (temp.delete()) Utils.s(); else Utils.e("Failed to delete: " + temp.getPath());
                    }

                    temp = new File("config.data");
                    Utils.i("Delete: " + temp.getPath());
                    if (temp.delete()) Utils.s(); else Utils.e("Failed to delete: " + temp.getPath());

                    Utils.i("");

                    System.out.print(Utils.CYAN);
                    System.exit(0);
                    break;
                }
            } else if (Objects.equals(choice, "sn")) {
                Utils.cls();

                Utils.i("\n  -----    /--\\    |--\\     /--\\");
                Utils.i("    |     |    |   |   |   |    |    |\\  |  |---");
                Utils.i("    |     |    |   |   |   |    |    | \\ |  |---");
                Utils.i("    |      \\--/    |--/     \\--/     |  \\|  |---");
                Utils.i("                                    by Adam Duda\n");

                Utils.i("OK! Enter a new name:");

                System.out.print(Utils.PURPLE);
                String choiceTemp = Menu.scanner.nextLine();
                System.out.print(Utils.RESET);

                if (Objects.equals(choiceTemp, "delete")) {
                    Utils.i("");

                    File temp;

                    for (int i = 0; i < lines.size(); i++) {
                        temp = new File(lines.get(i) + ".list");
                        Utils.i("Delete: " + temp.getPath());
                        if (temp.delete()) Utils.s(); else Utils.e("Failed to delete: " + temp.getPath());
                    }

                    temp = new File("config.data");
                    Utils.i("Delete: " + temp.getPath());
                    if (temp.delete()) Utils.s(); else Utils.e("Failed to delete: " + temp.getPath());

                    Utils.i("");

                    System.out.print(Utils.CYAN);
                    System.exit(0);
                    break;
                }
            }
        }
    }
}
