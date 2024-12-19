package util;

public class Debug {
    public enum type {
        REJECTED, IGNORED, MODIFIED, INFORMATION, SUCCESS, OTHER
    }

    public static void printLoggingHeader(String filename) {
        System.out.println("\n" + ConsoleColor.WHITE_BOLD_BRIGHT + "Output for: \"" + filename + "\"\nStatus -------\tMessage ------------------------------------------------------------------------------");
    }

    public static void log(type type, String message) {
        switch (type) {
            case REJECTED:
                System.out.println(ConsoleColor.RED_BOLD_BRIGHT + "Rejected " + ConsoleColor.RED + "\t\t" + message + ConsoleColor.RESET);
                break;

            case IGNORED:
                System.out.println(ConsoleColor.YELLOW_BOLD_BRIGHT+ "Ignored " + ConsoleColor.YELLOW + "\t\t" + message + ConsoleColor.RESET);
                break;

            case MODIFIED:
                System.out.println(ConsoleColor.YELLOW_BOLD_BRIGHT+ "Modified " + ConsoleColor.YELLOW + "\t\t" + message + ConsoleColor.RESET);
                break;

            case INFORMATION:
                System.out.println(ConsoleColor.BLUE_BOLD_BRIGHT + "Information " + ConsoleColor.RESET + "\t" + message + ConsoleColor.RESET);
                break;

            case SUCCESS:
                System.out.println(ConsoleColor.GREEN_BOLD_BRIGHT+ "Success " + ConsoleColor.GREEN + "\t\t" + message + ConsoleColor.RESET);
                break;

            case OTHER:
                System.out.println(message);
        }
    }

}