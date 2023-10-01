package app;

import java.io.*;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;


public class Menu {

    private final Settings settings = new Settings();
    private final ListView listView = new ListView();

    File config = new File("config.data");

    public static Scanner scanner;

    ArrayList<String> lines = new ArrayList<>();

    public static String name, listType, password;
    boolean encrypted;
    int linesInFile;

    public Menu() {
        scanner = new Scanner(System.in);

        //System.out.print(" " + Utils.BG_WHITE + Utils.BLUE + " X " + Utils.RESET + Utils.CYAN + " [ESC] Close\n\n");

        setup();

        loadData();

        if (encrypted) {
            Utils.cls();

            Utils.i("\n  -----    /--\\    |--\\     /--\\");
            Utils.i("    |     |    |   |   |   |    |    |\\  |  |---");
            Utils.i("    |     |    |   |   |   |    |    | \\ |  |---");
            Utils.i("    |      \\--/    |--/     \\--/     |  \\|  |---");
            Utils.i("                                    by Adam Duda\n");

            Utils.i("Hi, " + Utils.PURPLE + name + Utils.RESET + Utils.CYAN + "! Enter a password to see your lists:\n");

            System.out.print(Utils.PURPLE);
            String passwordEntered = scanner.nextLine();
            System.out.print(Utils.RESET);

            if (Objects.equals(passwordEntered, password)) {
                mainMenu();
            } else {
                Utils.i("");
                Utils.e("Wrong password.", true);

                System.out.print(Utils.CYAN);
                System.exit(0);
            }
        } else {
            mainMenu();
        }
    }

    public void mainMenu() {
        loadData();

        while (true) {
            Utils.cls();

            Utils.i("\n  -----    /--\\    |--\\     /--\\ " + Utils.GREEN + "     UPDATE 0.1");
            Utils.i("    |     |    |   |   |   |    |    |\\  |  |---");
            Utils.i("    |     |    |   |   |   |    |    | \\ |  |---");
            Utils.i("    |      \\--/    |--/     \\--/     |  \\|  |---");
            Utils.i("                                    by Adam Duda\n");

            Utils.i("Welcome back, " + Utils.PURPLE + name + Utils.RESET + Utils.CYAN + "!");

            //Utils.sleep(400);

            Utils.i("\ne - exit");
            Utils.i("s - settings");
            Utils.i("a - new list\n");

            if (lines.isEmpty()) {
                Utils.i("    - no lists -");
            } else {
                for (int i = 0; i < lines.size(); i++) {
                    Utils.i("   " + (i + 1) + " - " + lines.get(i));
                }
            }
            Utils.i("");

            System.out.print(Utils.PURPLE);
            String choice = scanner.nextLine();
            System.out.print(Utils.RESET);

            for (int i = 0; i < lines.size(); i++) {
                if (Objects.equals(choice, Integer.toString(i + 1))) {
                    listView.openList(lines.get(i));
                }
            }

            if (Objects.equals(choice, "a")) {
                Utils.cls();

                Utils.i("\n  -----    /--\\    |--\\     /--\\");
                Utils.i("    |     |    |   |   |   |    |    |\\  |  |---");
                Utils.i("    |     |    |   |   |   |    |    | \\ |  |---");
                Utils.i("    |      \\--/    |--/     \\--/     |  \\|  |---");
                Utils.i("                                    by Adam Duda\n");

                Utils.i("Chose a name for your new list, " + Utils.PURPLE + name + Utils.RESET + Utils.CYAN + " (or leave to cancel). If a list with a same name already exists, a new one won't be created.");

                System.out.print(Utils.PURPLE);
                String listName = scanner.nextLine();
                System.out.print(Utils.RESET);

                if (!listName.isBlank()) {
                    if (!lines.contains(listName)) {
                        boolean listTypeSimple = false;
                        if (Objects.equals(listType, "ask")) {
                            while (true) {
                                Utils.cls();

                                Utils.i("\n  -----    /--\\    |--\\     /--\\");
                                Utils.i("    |     |    |   |   |   |    |    |\\  |  |---");
                                Utils.i("    |     |    |   |   |   |    |    | \\ |  |---");
                                Utils.i("    |      \\--/    |--/     \\--/     |  \\|  |---");
                                Utils.i("                                    by Adam Duda\n");

                                Utils.i("OK! Chose a list type for \"" + Utils.PURPLE + listName + Utils.RESET + Utils.CYAN + "\".\n");
                                Utils.i("1 - simple");
                                Utils.i("2 - advanced");

                                System.out.print(Utils.PURPLE);
                                String choiceTemp = scanner.nextLine();
                                System.out.print(Utils.RESET);

                                if (Objects.equals(choiceTemp, "1")) {
                                    listTypeSimple = false;
                                    break;
                                } else if (Objects.equals(choiceTemp, "2")) {
                                    listTypeSimple = true;
                                    break;
                                }
                            }
                        } else if (Objects.equals(listType, "simple")) {
                            listTypeSimple = false;
                        } else if (Objects.equals(listType, "advanced")) {
                            listTypeSimple = true;
                        }

                        try {
                            BufferedWriter bw_config_add = new BufferedWriter(new FileWriter("config.data", true));
                            BufferedWriter bw_newList = new BufferedWriter(new FileWriter(listName + ".list", true));

                            bw_config_add.write(listName);
                            bw_config_add.newLine();

                            bw_newList.write(Boolean.toString(listTypeSimple));
                            bw_newList.newLine();

                            bw_config_add.close();
                            bw_newList.close();
                        } catch (IOException e) {
                            Utils.e("Write: " + e);
                        }

                        loadData();
                    }
                }
            } else if (Objects.equals(choice, "s")) {
                settings.openSettings(lines);
            } else if (Objects.equals(choice, "e")) {
                System.exit(0);
            } else if (Objects.equals(choice, "/devmode")) {
                Utils.s("DEV MODE ENABLED");
                Utils.s("DEBUGGER ATTACHED");

                try {
                    System.in.read();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            } else if (Objects.equals(choice, "/deletefs")) {
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

                new Menu();
            } else if (Objects.equals(choice, "/clearls")) {
                Utils.i("");

                lines.clear();

                try {
                    BufferedReader br = new BufferedReader(new FileReader("config.data"));

                    String line;
                    while ((line = br.readLine()) != null) {
                        linesInFile++;
                        lines.add(line);
                    }
                    br.close();

                    BufferedWriter bw_config = new BufferedWriter(new FileWriter("config.data"));

                    bw_config.write(lines.get(0) + "\n");
                    bw_config.write(lines.get(1) + "\n");
                    bw_config.write(lines.get(2) + "\n");
                    bw_config.write(lines.get(3) + "\n");

                    bw_config.close();
                } catch (IOException e) {
                    Utils.e(e.toString());
                }

                File temp;

                for (int i = 4; i < lines.size(); i++) {
                    temp = new File(lines.get(i) + ".list");
                    Utils.i("Delete: " + temp.getPath());
                    if (temp.delete()) Utils.s(); else Utils.e("Failed to delete: " + temp.getPath());
                }

                Utils.i("");

                new Menu();
            }
        }
    }

    public void loadData() {
        lines.clear();

        try {
            BufferedReader br = new BufferedReader(new FileReader("config.data"));

            String line;
            while ((line = br.readLine()) != null) {
                linesInFile++;
                lines.add(line);
            }
            br.close();

        } catch (IOException e) {
            Utils.e(e.toString());
        }

        name = lines.get(0);
        listType = lines.get(1);
        if (Objects.equals(lines.get(2), "no")) encrypted = false; else if (Objects.equals(lines.get(2), "yes")) encrypted = true; else Utils.e("Invalid config syntax.");
        password = lines.get(3);

        lines.remove(0);
        lines.remove(0);
        lines.remove(0);
        lines.remove(0);
    }

    public void setup() {
        if (!config.exists()) {
            Utils.cls();

            Utils.i("\n  -----    /--\\    |--\\     /--\\");
            Utils.i("    |     |    |   |   |   |    |    |\\  |  |---");
            Utils.i("    |     |    |   |   |   |    |    | \\ |  |---");
            Utils.i("    |      \\--/    |--/     \\--/     |  \\|  |---");
            Utils.i("                                    by Adam Duda\n");

            Utils.i("Welcome! It seems like you are using my app for the first time. If not, you can import the settings by copying them to the /data folder.\n" +
                    "For now, lets start the setup process. You will be able to change all the setting later.\n");

            String name, listsType, isEncrypted, password = "none";
            String[] lists = new String[5000];
            String[] listType = new String[5000];

            Utils.i("For better user experience, please tell us, how we should call you. You can enter your name or nickname.\n");

            System.out.print(Utils.PURPLE);
            name = scanner.nextLine();
            System.out.print(Utils.RESET);

            while (true) {
                Utils.cls();

                Utils.i("\n  -----    /--\\    |--\\     /--\\");
                Utils.i("    |     |    |   |   |   |    |    |\\  |  |---");
                Utils.i("    |     |    |   |   |   |    |    | \\ |  |---");
                Utils.i("    |      \\--/    |--/     \\--/     |  \\|  |---");
                Utils.i("                                    by Adam Duda\n");

                Utils.i("Welcome! It seems like you are using my app for the first time. If not, you can import the settings by copying them to the /data folder.\n" +
                        "For now, lets start the setup process. You will be able to change all the setting later.\n");

                Utils.i("Hi " + Utils.PURPLE + name + Utils.CYAN + "! We have only a few questions left and then you will be able to use the app.\n\n" +
                        "Do you want you lists to be simple or more advanced? (importance level, color, ect.)\n\n" +
                        "1 - simple\n2 - advanced\n3 - ask each time I create a list.\n");

                System.out.print(Utils.PURPLE);
                String listsTypeTemp = scanner.nextLine();
                System.out.print(Utils.RESET);

                if (Objects.equals(listsTypeTemp, "1")) {
                    listsType = "simple";
                    break;
                } else if (Objects.equals(listsTypeTemp, "2")) {
                    listsType = "advanced";
                    break;
                } else if (Objects.equals(listsTypeTemp, "3")) {
                    listsType = "ask";
                    break;
                }
            }

            while (true) {
                Utils.cls();

                Utils.i("\n  -----    /--\\    |--\\     /--\\");
                Utils.i("    |     |    |   |   |   |    |    |\\  |  |---");
                Utils.i("    |     |    |   |   |   |    |    | \\ |  |---");
                Utils.i("    |      \\--/    |--/     \\--/     |  \\|  |---");
                Utils.i("                                    by Adam Duda\n");

                Utils.i("Welcome! It seems like you are using my app for the first time. If not, you can import the settings by copying them to the /data folder.\n" +
                        "For now, lets start the setup process. You will be able to change all the setting later.\n");

                Utils.i("Thanks! Now, do you wish for your lists to be encrypted? (You will be asked for a password you set each time you open this app)\n\n" +
                        "1 - Yes\n2 - No\n");

                System.out.print(Utils.PURPLE);
                String isEncryptedTemp = scanner.nextLine();
                System.out.print(Utils.RESET);

                if (Objects.equals(isEncryptedTemp, "1")) {
                    isEncrypted = "yes";

                    Utils.cls();

                    Utils.i("\n  -----    /--\\    |--\\     /--\\");
                    Utils.i("    |     |    |   |   |   |    |    |\\  |  |---");
                    Utils.i("    |     |    |   |   |   |    |    | \\ |  |---");
                    Utils.i("    |      \\--/    |--/     \\--/     |  \\|  |---");
                    Utils.i("                                    by Adam Duda\n");

                    Utils.i("Welcome! It seems like you are using my app for the first time. If not, you can import the settings by copying them to the /data folder.\n" +
                            "For now, lets start the setup process. You will be able to change all the setting later.\n");

                    Utils.i("OK! Please, enter Your password, or type 1 if you changed your mind to turn off encryption.\n");

                    System.out.print(Utils.PURPLE);
                    password = scanner.nextLine();
                    System.out.print(Utils.RESET);

                    if (Objects.equals(password, "1")) {
                        isEncrypted = "no";
                        password = "none";
                    }

                    break;
                } else if (Objects.equals(isEncryptedTemp, "2")) {
                    isEncrypted = "no";
                    break;
                }
            }

            /*Utils.cls();

            Utils.i("\n  -----    /--\\    |--\\     /--\\");
            Utils.i("    |     |    |   |   |   |    |    |\\  |  |---");
            Utils.i("    |     |    |   |   |   |    |    | \\ |  |---");
            Utils.i("    |      \\--/    |--/     \\--/     |  \\|  |---");
            Utils.i("                                    by Adam Duda\n");


            Utils.i("Setup is complete! Now, you can start using TO-DOne. Enter the name of the first list to start it. Leave blank for \"Default\".\n");

            System.out.print(Utils.PURPLE);
            lists[0] = scanner.nextLine();
            System.out.print(Utils.RESET);

            if (lists[0].isBlank()) lists[0] = "Default";

            boolean listTypeSimple = false; // TODO assign
            if (Objects.equals(listsType, "ask")) {
                while (true) {
                    Utils.cls();

                    Utils.i("\n  -----    /--\\    |--\\     /--\\");
                    Utils.i("    |     |    |   |   |   |    |    |\\  |  |---");
                    Utils.i("    |     |    |   |   |   |    |    | \\ |  |---");
                    Utils.i("    |      \\--/    |--/     \\--/     |  \\|  |---");
                    Utils.i("                                    by Adam Duda\n");

                    Utils.i("OK! Chose a list type for \"" + Utils.PURPLE + lists[0] + Utils.RESET + Utils.CYAN + "\".\n");
                    Utils.i("1 - simple");
                    Utils.i("2 - advanced");

                    System.out.print(Utils.PURPLE);
                    String choiceTemp = scanner.nextLine();
                    System.out.print(Utils.RESET);

                    if (Objects.equals(choiceTemp, "1")) {
                        listTypeSimple = false;
                        break;
                    } else if (Objects.equals(choiceTemp, "2")) {
                        listTypeSimple = true;
                        break;
                    }
                }
            } else if (Objects.equals(listsType, "simple")) {
                listTypeSimple = false;
            } else if (Objects.equals(listsType, "advanced")) {
                listTypeSimple = true;
            }*/

            try {
                BufferedWriter bw_config = new BufferedWriter(new FileWriter("config.data"));
                //BufferedWriter bw_firstList = new BufferedWriter(new FileWriter(lists[0] + ".list", true));

                bw_config.write(name + "\n");
                bw_config.write(listsType + "\n");
                bw_config.write(isEncrypted + "\n");
                bw_config.write(password + "\n");
                //bw_config.write(lists[0] + "\n");

                //bw_firstList.write(Boolean.toString(true)); // Boolean.toString(listTypeSimple)
                //bw_firstList.newLine();

                bw_config.close();
                //bw_firstList.close();
            } catch (IOException e) {
                Utils.e("Write: " + e);
            }

            Utils.cls();

            Utils.i("\n  -----    /--\\    |--\\     /--\\");
            Utils.i("    |     |    |   |   |   |    |    |\\  |  |---");
            Utils.i("    |     |    |   |   |   |    |    | \\ |  |---");
            Utils.i("    |      \\--/    |--/     \\--/     |  \\|  |---");
            Utils.i("                                    by Adam Duda\n");


            Utils.i("Great! Press enter or any key to get to the main menu where you can create your first list.\n"); // TODO

            try {
                System.in.read();
            } catch (IOException e) {
                Utils.e("System.in.read: " + e);
            }
        }
    }
}

/*

    TODO Base64.getEncoder().encodeToString(originalInput.getBytes())
    TODO Base64.getDecoder().decode(encodedString)

    TODO help list

 */