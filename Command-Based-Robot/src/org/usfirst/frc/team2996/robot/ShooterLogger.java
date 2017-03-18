package org.usfirst.frc.team2996.robot;

import java.util.logging.Logger;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


public class ShooterLogger {
	public static void main(String[] args) throws Exception, Throwable {
		Logger log = ShooterFileLogging.getLogger("test");
		log.fine(", shooterPressed, shooterRpm, shooterThrottle, shooterVelocity, "
			     + "augerPressed, augerRpm, augerThrottle, augerVelocity");
		Logger log2 = ShooterFileLogging.getLogger("test2");
		log2.fine(", test");

		boolean buttonPushed = true;
		double rpm = 0;
		double throttle = 0;
		boolean shooterButton = true;
		double shooterRpm = 0; 
		double shooterThrottle = 0; 
		double shooterVelocity = 0;
		boolean augerButton = true;
		double augerRpm = 0;
		double augerThrottle = 0;
		double augerVelocity = 0;
		if (buttonPushed) {

			for (int i = 0; i < 100; i++) {
				shooterButton = true;
				shooterRpm = i; 
				shooterThrottle += 2; 
				shooterVelocity += 3;
				augerButton = true;
				augerRpm += 4;
				augerThrottle += 5;
				augerVelocity += 6;
				Thread.sleep(20);
				throttle++;
				rpm++;
				log.fine(", " + shooterButton + ", " + shooterRpm + ", " + shooterThrottle + ", " + shooterVelocity + ", " 
						  + augerButton + ", " + augerRpm + ", " + augerThrottle + ", " + augerVelocity);
				log2.fine(", " + i);
			}
		}
	}
}
