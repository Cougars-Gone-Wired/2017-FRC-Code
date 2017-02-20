package org.usfirst.frc.team2996.robot;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Climber {
	
	CANTalon climber;
	Joystick stick;
	
	Climber(Robot robot){
		this.climber = robot.getClimberMotor();
		this.stick = robot.getStickDrive();
	}
	public void climb(){
		climber.set(Threshold.threshold(stick.getRawAxis(Robot.CLIMB_AXIS)));
	}
}
