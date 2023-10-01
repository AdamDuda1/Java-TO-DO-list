package app;

import java.io.IOException;

public class Utils {
    public static final String RESET = "\u001B[0m";
    public static final String BLACK = "\u001B[30m";
    public static final String RED = "\u001B[31m";
    public static final String GREEN = "\u001B[32m";
    public static final String YELLOW = "\u001B[33m";
    public static final String BLUE = "\u001B[34m";
    public static final String PURPLE = "\u001B[35m";
    public static final String CYAN = "\u001B[36m";
    public static final String WHITE = "\u001B[37m";

    public static final String BG_BLACK = "\u001B[40m";
    public static final String BG_RED = "\u001B[41m";
    public static final String BG_GREEN = "\u001B[42m";
    public static final String BG_YELLOW = "\u001B[43m";
    public static final String BG_BLUE = "\u001B[44m";
    public static final String BG_PURPLE = "\u001B[45m";
    public static final String BG_CYAN = "\u001B[46m";
    public static final String BG_WHITE = "\u001B[47m";

    public static void i(String message) {
        System.out.print(CYAN + message + "\n" + RESET);
    }

    public static void t(String message) {
        System.out.print(BG_CYAN + WHITE + message + "\n" + RESET);
    }

    public static void e(String message) {
        System.out.print(WHITE + BG_RED + "[E] " + message + " (Enter to continue). \n" + RESET);

        try {
            System.in.read();
        } catch (IOException e) {
            Utils.e(e.toString());
        }
    }

    public static void e(String message, boolean skipWaitForEnter) {
        System.out.print(WHITE + BG_RED + "[E] " + message + ". \n" + RESET);

        if (!skipWaitForEnter) {
            try {
                System.in.read();
            } catch (IOException e) {
                Utils.e(e.toString());
            }
        }
    }

    public static void s() {
        System.out.print(WHITE + BG_GREEN + "Done. " + "\n" + RESET);
    }

    public static void s(String message) {
        System.out.print("   " + WHITE + BG_GREEN + " " + message + " " + "\n" + RESET);
    }

    public static void cls() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public static void sleep(int mills) {
        try {
            Thread.sleep(mills);
        } catch (InterruptedException e) {
            Utils.e(e.toString());
        }
    }
}
