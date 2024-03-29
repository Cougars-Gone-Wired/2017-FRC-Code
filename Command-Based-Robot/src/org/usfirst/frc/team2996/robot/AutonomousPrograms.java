package org.usfirst.frc.team2996.robot;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class AutonomousPrograms extends AutonomousMethods {

	public AutonomousPrograms(Robot robot) {
		super(robot);
	}

	public void placeGearLeftPeg() {
		moveStraight("forward",(int) SmartDashboard.getNumber("auto first drive distance", 0), SmartDashboard.getNumber("auto drive speed", 0));
		sleep();
		turn("right", SmartDashboard.getNumber("auto turn angle", 0), SmartDashboard.getNumber("Auto Turn Speed", 0));
		sleep();
		moveStraight("forward", (int) SmartDashboard.getNumber("auto second drive distance", 0), SmartDashboard.getNumber("Auto Second Drive Speed", 0));
		sleep();
		gearDrop(SmartDashboard.getNumber("Gear Drop Time", 0), SmartDashboard.getNumber("auto gear speed", 0));
		stop();
	}
	
	public void placeGearRightPeg(){
		moveStraight("forward", (int) SmartDashboard.getNumber("auto first drive distance", 0), SmartDashboard.getNumber("auto drive speed", 0));
		sleep();
		turn("left", SmartDashboard.getNumber("auto turn angle", 0), SmartDashboard.getNumber("Auto Turn Speed", 0));
		sleep();
		moveStraight("forward",  (int) SmartDashboard.getNumber("auto second drive distance", 0), SmartDashboard.getNumber("Auto Second Drive Speed", 0));
		sleep();
		gearDrop(SmartDashboard.getNumber("Gear Drop Time", 0), SmartDashboard.getNumber("auto gear speed", 0));
		stop();
	}
	
	public void placeGearCenterPeg(){
		moveStraight("forward",(int) SmartDashboard.getNumber("auto first drive distance", 0), SmartDashboard.getNumber("auto drive speed", 0));
		sleep();
		gearDrop(SmartDashboard.getNumber("Gear Drop Time", 0), SmartDashboard.getNumber("auto drive speed", 0));
		stop();
	}

	public void driveForward(){
		moveStraight("forward", (int) SmartDashboard.getNumber("auto first drive distance", 0), SmartDashboard.getNumber("auto drive speed", 0));
		stop();
	}
	
}
