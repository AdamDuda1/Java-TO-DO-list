package app;

import java.io.*;
import java.util.ArrayList;
import java.util.Objects;

public class ListView {

    ArrayList<String> listLines = new ArrayList<>();
    int linesInFile;
    String listType;

    public void openList(String listName) {
        while (true) {
            Utils.cls();

            System.out.print("\n  ");
            Utils.t("\"" + listName + "\" list:");

            Utils.i("\ne - back to the main menu");
            Utils.i("s - list settings");
            Utils.i("a - add to the list (add ! to the start for high importance even in type simple lists)\n");
            Utils.i("d[number] - delete an item");
            Utils.i("c[number] - delete an item\n");

            listLines.clear();

            try {
                BufferedReader br = new BufferedReader(new FileReader(listName + ".list"));

                String line;
                while ((line = br.readLine()) != null) {
                    linesInFile++;
                    listLines.add(line);
                }
                br.close();

                listType = listLines.get(0);
            } catch (IOException e) {
                Utils.e(e.toString());
            }

            if (listLines.size() < 2) {
                Utils.i("   - no items - ");
            } else {
                for (int i = 1; i < listLines.size(); i++) {
                    if (listLines.get(i).startsWith("IMPORTANT")) {
                        Utils.i("  " + i + ". " + Utils.RED + "IMPORTANT" + Utils.RESET + Utils.CYAN + " " + listLines.get(i).substring(10));
                    } else if (listLines.get(i).startsWith("DONE")) {
                        if (listLines.get(i).startsWith("DONEIMPORTANT")) {
                            Utils.i("  " + i + ". " + Utils.GREEN + "DONE " + Utils.RED + "IMPORTANT" + Utils.RESET + Utils.CYAN + " " + listLines.get(i).substring(14));
                        } else {
                            Utils.i("  " + i + ". " + Utils.GREEN + "DONE" + Utils.RESET + Utils.CYAN + " " + listLines.get(i).substring(4));
                        }
                    } else {
                        Utils.i("  " + i + ". " + listLines.get(i));
                    }
                }
            }

            Utils.i("");

            System.out.print(Utils.PURPLE);
            String choice = Menu.scanner.nextLine();
            System.out.print(Utils.RESET);

            for (int i = 1; i < listLines.size(); i++) {
                if (Objects.equals(choice, "d" + Integer.toString(i))) {
                    listLines.remove(i);

                    try {
                        BufferedWriter bw_list = new BufferedWriter(new FileWriter(listName + ".list"));

                        bw_list.write(listType + "\n");

                        BufferedWriter _bw_list = new BufferedWriter(new FileWriter(listName + ".list", true));

                        for (int j = 1; j < listLines.size(); j++) {
                            _bw_list.write(listLines.get(j) + "\n");
                        }

                        bw_list.close();
                        _bw_list.close();
                    } catch (IOException e) {
                        Utils.e("Write: " + e);
                    }

                    break;
                } else if (Objects.equals(choice, "c" + Integer.toString(i))) {
                    listLines.add(i + 1, "DONE" + listLines.get(i));
                    listLines.remove(i);

                    try {
                        BufferedWriter bw_list = new BufferedWriter(new FileWriter(listName + ".list"));

                        bw_list.write(listType + "\n");

                        BufferedWriter _bw_list = new BufferedWriter(new FileWriter(listName + ".list", true));

                        for (int j = 1; j < listLines.size(); j++) {
                            _bw_list.write(listLines.get(j) + "\n");
                        }


                        bw_list.close();
                        _bw_list.close();
                    } catch (IOException e) {
                        Utils.e("Write: " + e);
                    }

                    break;
                }
            }

            if (Objects.equals(choice, "e")) {
                break;
            } else if (Objects.equals(choice, "/clr")) {
                try {
                    BufferedWriter bw_list = new BufferedWriter(new FileWriter(listName + ".list"));

                    bw_list.write(listType + "\n");

                    bw_list.close();
                } catch (IOException e) {
                    Utils.e("Write: " + e);
                }
            } else if (Objects.equals(choice, "a")) {
                Utils.cls();

                System.out.print("\n  ");
                Utils.t("\"" + listName + "\" list:");

                Utils.i("\nAdd an item to this list. Add \"!\" to the start to make it \"important\".\n");

                if (listLines.size() < 2) {
                    Utils.i("   - no items - ");
                } else {
                    for (int i = 1; i < listLines.size(); i++) {
                        if (listLines.get(i).startsWith("IMPORTANT")) {
                            Utils.i("  " + i + ". " + Utils.RED + "IMPORTANT" + Utils.RESET + Utils.CYAN + " " + listLines.get(i).substring(10));
                        } else if (listLines.get(i).startsWith("DONE")) {
                            if (listLines.get(i).startsWith("DONEIMPORTANT")) {
                                Utils.i("  " + i + ". " + Utils.GREEN + "DONE " + Utils.RED + "IMPORTANT" + Utils.RESET + Utils.CYAN + " " + listLines.get(i).substring(14));
                            } else {
                                Utils.i("  " + i + ". " + Utils.GREEN + "DONE" + Utils.RESET + Utils.CYAN + " " + listLines.get(i).substring(4));
                            }
                        }  else {
                            Utils.i("  " + i + ". " + listLines.get(i));
                        }
                    }
                }

                Utils.i("");

                System.out.print(Utils.PURPLE);
                String item = Menu.scanner.nextLine();
                System.out.print(Utils.RESET);

                if (!item.isEmpty() && !item.isBlank()) {
                    if (item.startsWith("!")) {
                        try {
                            BufferedWriter bw_list = new BufferedWriter(new FileWriter(listName + ".list", true));

                            bw_list.write("IMPORTANT" + item + "\n");

                            bw_list.close();
                        } catch (IOException e) {
                            Utils.e("Write: " + e);
                        }
                    } else {
                        try {
                            BufferedWriter bw_list = new BufferedWriter(new FileWriter(listName + ".list", true));

                            bw_list.write(item + "\n");

                            bw_list.close();
                        } catch (IOException e) {
                            Utils.e("Write: " + e);
                        }
                    }
                }
            }
        }
    }
}
