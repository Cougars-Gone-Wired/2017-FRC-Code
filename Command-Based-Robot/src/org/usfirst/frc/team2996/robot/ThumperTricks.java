package org.usfirst.frc.team2996.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Timer;

public class ThumperTricks {

	Timer timer;
	Joystick stick;
	int thumpButton;
	Solenoid backRightSolenoid;
	Solenoid backLeftSolenoid;
	Solenoid frontRightSolenoid;
	Solenoid frontLeftSolenoid;
	
	public ThumperTricks(Robot robot){
		this.timer = robot.getTimer();
		this.stick = robot.getStickDrive();
		this.thumpButton = Robot.THUMP_BUTTON;
		this.backLeftSolenoid = robot.getBackLeftSolenoid();
		this.backRightSolenoid = robot.getBackRightSolenoid();
		this.frontRightSolenoid = robot.getFrontRightSolenoid();
		this.frontLeftSolenoid = robot.getFrontLeftSolenoid();
	}
	
	public void rocking(){
		if(stick.getRawButton(thumpButton) == true){
			backLeftSolenoid.set(true);
			backRightSolenoid.set(true);
			frontRightSolenoid.set(false);
			frontLeftSolenoid.set(false);
		} else {
			backLeftSolenoid.set(false);
			backRightSolenoid.set(false);
			frontRightSolenoid.set(true);
			frontLeftSolenoid.set(true);
		}
	}
	
	public void rockAround(){
		timer.start();
	}
}
