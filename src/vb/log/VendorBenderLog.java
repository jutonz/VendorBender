package vb.log;

import org.apache.commons.io.input.Tailer;
import org.apache.commons.io.input.TailerListenerAdapter;
import vb.core.Result;
import vb.core.VendorBender;

import java.io.File;
import java.util.Scanner;

/**
 *
 * Created by Justin on 19th March 2014.
 */
public class VendorBenderLog extends TailerListenerAdapter {

    public static final String KEYWORD = "calc";
    public static final String STOPWORD = "stop";
    public static final String DEFAULT_LOG_PATH = "C:\\Program Files (x86)\\Steam\\SteamApps\\common\\Path of Exile\\logs\\Client.txt";
    private boolean displayLines = false;

    private Tailer tailer;

    public VendorBenderLog() {
        File file = new File(DEFAULT_LOG_PATH);
        tailer = new Tailer(file, this, 1000, true);
        Thread tailerThread = new Thread(tailer);
        tailerThread.start();
//        System.out.println("Monitoring file: " + file.getAbsolutePath());
    }

    /**
     * This method is called whenever there is a change in
     * the log file. If the changed line is prefixed with the
     * KEYWORD, the remaining contents of the line will be
     * processed as integers and calculated.
     *
     * @param line the new line
     */
    public void handle(String line) {
        if (displayLines) System.out.println("Received: " + line);
        if (line.contains(KEYWORD)) {
            System.out.println("Contains keyword. Parsing...");
            VendorBender vb = new VendorBender();
            Scanner scan = new Scanner(line);
            boolean containsValues = false;
            while (scan.hasNext()) {
                if (scan.hasNextInt()) {
                    int add = scan.nextInt();
                    if (vb.add(add)) {
                        containsValues = true;
                        System.out.println("Added: " + add);
                    }
                } else if (scan.next().equals(STOPWORD)) {
                    tailer.stop();
                    System.out.println("Received stop signal.");
                }
            }
            scan.close();
            if (containsValues) System.out.println("Calculating...");
            for (Result r : vb.getResults()) {
                System.out.print("Result (" + r.getSum() + "): ");
                for (Integer i : r.getValues())
                    System.out.print(i + " ");
                System.out.println();
            }
        }
    }

    public static void main(String[] args) {
        new VendorBenderLog();
    }

}

