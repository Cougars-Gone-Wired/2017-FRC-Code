package org.usfirst.frc.team2996.robot;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Climber {
	
	CANTalon climber;
	Joystick stick;
	int climbUpButton;
	int climbDownButton;
	
	Climber(Robot robot){
		this.climber = robot.getClimberMotor();
		this.stick = robot.getStickDrive();
		this.climbUpButton = Robot.CLIMB_UP_BUTTON;
		this.climbDownButton = Robot.CLIMB_DOWN_BUTTON;
	}
	public void climb(){
		if(stick.getRawButton(climbUpButton)){
			climber.set(SmartDashboard.getNumber("climber full speed", 0));
		}else if(stick.getRawButton(climbDownButton)){
			climber.set(-(SmartDashboard.getNumber("climber full speed", 0)));
		}else if(stick.getRawButton(climbUpButton) && stick.getRawButton(climbDownButton)){
			climber.set(SmartDashboard.getNumber("climber steady", 0));
		}else{
			climber.set(0);
		}
	}

}
