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
	int thumpFrontBackButton;
	int thumpSideSideButton;
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
		this.stick = robot.getStickDrive();
		this.thumpFrontBackButton = Robot.THUMP_FRONT_BACK_BUTTON;
		this.thumpSideSideButton = Robot.THUMP_SIDE_SIDE_BUTTON;
		this.backLeftSolenoid = robot.getBackLeftSolenoid();
		this.backRightSolenoid = robot.getBackRightSolenoid();
		this.frontRightSolenoid = robot.getFrontRightSolenoid();
		this.frontLeftSolenoid = robot.getFrontLeftSolenoid();
		this.frontLeftMotor = robot.getFrontLeftMotor();
		this.frontRightMotor = robot.getFrontRightMotor();
		this.backLeftMotor = robot.getBackLeftMotor();
		this.backRightMotor = robot.getBackRightMotor();
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
		if(stick.getRawButton(thumpFrontBackButton) == true){
			setSolenoids(true, true, false, false);
			sleep(1000);
			setSolenoids(false, false, true, true);
		} else if(stick.getRawButton(thumpSideSideButton) == true){
			setSolenoids(true, false, true, false);
			sleep(1000);  
			setSolenoids(false, true, false, true);
		} else {
			setSolenoids(true, true, true, true);
		}
	}

		public static void sleep(int x){
			try {
				Thread.sleep(x);
			} catch (InterruptedException e) {
				//TODO Auto-generated catch block
				//e.printStackTrace();
			}
		}
	}


