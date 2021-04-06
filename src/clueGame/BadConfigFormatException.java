package clueGame;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * 
 * @author Mark Baldwin
 *
 */
public class BadConfigFormatException  extends Exception {
	public static String logFile = "ClueLog.txt";
    private BufferedWriter logFileWriter;

	/**
	 * Default constructor
	 */
	public BadConfigFormatException() {
		super("A bad configuration exception has occurred");
	}
	
	/**
	 * Constructor with message
	 * 
	 * @param message - message to be used
	 */
	public BadConfigFormatException(String message) {
		super(message);
        File logFileObj = new File(logFile);
        try {
        	FileWriter fileWriter = new FileWriter(logFileObj, true);
        	logFileWriter = new BufferedWriter(fileWriter);
            if (logFileWriter != null) {
                logFileWriter.write(message + "\n");
                logFileWriter.flush();
            }
        } catch (IOException e) {
        	System.out.println("Error writing to log file: " + logFile);
        	System.out.println(e.getMessage());
        }
	}

}
