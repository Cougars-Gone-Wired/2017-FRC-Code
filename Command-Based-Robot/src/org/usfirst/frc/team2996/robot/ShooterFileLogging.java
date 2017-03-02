package org.usfirst.frc.team2996.robot;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ShooterFileLogging {
	private static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd_HHmmss");
	private static Date start = new Date();
	private static Logger teamLogger = null;

	public static Logger getLogger() throws Exception, Throwable {
		teamLogger = Logger.getGlobal();
		teamLogger.setUseParentHandlers(false);
		Level level = Level.FINE;
		teamLogger.setLevel(level);
		File file = new File("RPMTime_" + format.format(start) + ".csv");
		System.out.println(file.getAbsolutePath());
		FileHandler fh = new FileHandler(file.getAbsolutePath());
		fh.setFormatter(new ShooterLoggerFormatter());
		teamLogger.addHandler(fh);
		return teamLogger;
	}
}
