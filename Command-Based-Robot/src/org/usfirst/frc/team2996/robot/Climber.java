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
		if(stick.getRawButton(Robot.CLIMB_UP_BUTTON) && stick.getRawButton(Robot.CLIMB_DOWN_BUTTON)){
			climber.set(Robot.CLIMBER_INVERT * (SmartDashboard.getNumber("Climb Half Speed", 0)));
		} else if(stick.getRawButton(Robot.CLIMB_UP_BUTTON)){
			climber.set(Robot.CLIMBER_INVERT * (1));
		} else if(stick.getRawButton(Robot.CLIMB_DOWN_BUTTON)){
			climber.set(Robot.CLIMBER_INVERT * (-1));
		} else {
			climber.set(0);
		}
	  /*if(stick.getRawAxis(Robot.CLIMB_AXIS)>0.5){
		climber.set(Threshold.threshold(stick.getRawAxis(Robot.CLIMB_AXIS)));
		} else if(Threshold.threshold(stick.getRawAxis(2))>0){
		climber.set(-Threshold.threshold(stick.getRawAxis(2)));
		} else{
			climber.set(0);
		}*/
	}
}