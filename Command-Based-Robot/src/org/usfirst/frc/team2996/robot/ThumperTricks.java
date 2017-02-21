package org.usfirst.frc.team2996.robot;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Timer;

public class ThumperTricks {
	
	public RobotDrive robotDrive;
	Timer timer;
	Joystick stick;
	int thumpFrontButton;
	int thumpBackButton;
	Solenoid backRightSolenoid;
	Solenoid backLeftSolenoid;
	Solenoid frontRightSolenoid;
	Solenoid frontLeftSolenoid;
	CANTalon frontRightMotor;
	CANTalon frontLeftMotor;
	CANTalon backRightMotor;
	CANTalon backLeftMotor;
	boolean driveState = false;
	
	public ThumperTricks(Robot robot){
		this.timer = robot.getTimer();
		this.stick = robot.getStickDrive();
		this.thumpFrontButton = Robot.THUMP_FRONT_BUTTON;
		this.thumpBackButton = Robot.THUMP_BACK_BUTTON;
		this.backLeftSolenoid = robot.getBackLeftSolenoid();
		this.backRightSolenoid = robot.getBackRightSolenoid();
		this.frontRightSolenoid = robot.getFrontRightSolenoid();
		this.frontLeftSolenoid = robot.getFrontLeftSolenoid();
		this.robotDrive = robot.getRobotDrive();
	}
	
		public void setSolenoids(boolean frontRightSolenoid, boolean frontLeftSolenoid, boolean backRightSolenoid, boolean backLeftSolenoid) {
			this.frontRightSolenoid.set(frontRightSolenoid);
			this.frontLeftSolenoid.set(frontLeftSolenoid);
			this.backRightSolenoid.set(backRightSolenoid);
			this.backLeftSolenoid.set(backLeftSolenoid);
		}

		public void invertMotors(boolean frontLeftMotorInvert, boolean frontRightMotorInvert, boolean backLeftMotorInvert, boolean backRightMotorInvert) {
			frontLeftMotor.setInverted(frontLeftMotorInvert);
			frontRightMotor.setInverted(frontRightMotorInvert);
			backLeftMotor.setInverted(backLeftMotorInvert);
			backRightMotor.setInverted(backRightMotorInvert);
		}
		
		public void rockingArcadeDrive(){
		invertMotors(false, false, false, false);
		robotDrive.arcadeDrive(
				Threshold.threshold((Robot.ARCADE_DRIVE_YAXIS_INVERT) * stick.getRawAxis(Robot.ARCADE_DRIVE_YAXIS)),
				Threshold.threshold((Robot.ARCADE_DRIVE_ROTATE_INVERT) * stick.getRawAxis(Robot.ARCADE_DRIVE_ROTATE)));
		if(stick.getRawButton(thumpFrontButton) == true){
			setSolenoids(true, true, false, false);
		} else if(stick.getRawButton(thumpBackButton) == true){
			setSolenoids(false, false, true, true);
		} else {
			setSolenoids(true, true, true, true);
		}
	}
}

