package org.usfirst.frc.team2996.robot;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;

public class ShooterLoggerFormatter extends Formatter {
	private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss.SSS");

	@Override
	public String format(LogRecord RPM) {
		StringBuilder sb = new StringBuilder();
		Date d = new Date();
		sb.append(dateFormat.format(d));
		sb.append(RPM.getMessage());
		sb.append("\n");
		return sb.toString();
	}
}
