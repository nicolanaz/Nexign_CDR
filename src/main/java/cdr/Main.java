package cdr;

import java.io.File;

public class Main {
    public static void main(String[] args) throws Exception {
        CallDataParser callDataParser = new CallDataParser();
        callDataParser.parse(new File("cdr.txt"));
    }
}
