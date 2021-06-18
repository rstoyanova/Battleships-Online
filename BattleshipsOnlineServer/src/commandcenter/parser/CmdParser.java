package commandcenter.parser;

public class CmdParser {

    public static String getCMD(String argv) {
        return argv.split(" ", 2)[0];
    }

    public static String[] getArg(String argv) {
        String[] tokens = argv.split(" ", 2);
        if (tokens.length == 1) {
            return null;
        }
        return tokens[1].split(" ");
    }
}
