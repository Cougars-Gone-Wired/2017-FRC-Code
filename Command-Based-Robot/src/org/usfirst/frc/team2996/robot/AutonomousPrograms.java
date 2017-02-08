package org.usfirst.frc.team2996.robot;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class AutonomousPrograms extends AutonomousMethods {

	public AutonomousPrograms(Robot robot) {
		super(robot);
	}

	public void moveForwardTurnLeftPlaceGear() {
		moveStraight("forward", (int) SmartDashboard.getNumber("auto first drive distance", 0),
				SmartDashboard.getNumber("auto drive speed", 0));
		sleep();
		turn("right", SmartDashboard.getNumber("auto turn angle", 0));
		sleep();
		moveStraight("forward", (int) SmartDashboard.getNumber("auto second drive distance", 0),
				SmartDashboard.getNumber("auto drive speed", 0));
		stop();
	}
}