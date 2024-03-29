package org.usfirst.frc.team2996.robot;

import com.ctre.CANTalon;
import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;

public class Drive {
	Joystick stick;
	public RobotDrive robotDrive;

	CANTalon frontLeftMotor;
	CANTalon frontRightMotor;
	CANTalon backLeftMotor;
	CANTalon backRightMotor;

	Solenoid frontRightSolenoid;
	Solenoid frontLeftSolenoid;
	Solenoid backRightSolenoid;
	Solenoid backLeftSolenoid;

	boolean ModDrive;
	
	int wheelDiameter;
	AHRS gyro;
	boolean driveState = false; // state = true means Mechanum , false is arcade

	public boolean isMecanum() {
		return driveState;
	}

	public void setDriveState(boolean state) {
		this.driveState = state;
	}

	Drive(Robot robot) {
		this.stick = robot.getStickDrive();
		this.robotDrive = robot.getRobotDrive();
		this.frontRightSolenoid = robot.getFrontRightSolenoid();
		this.frontLeftSolenoid = robot.getFrontLeftSolenoid();
		this.backRightSolenoid = robot.getBackRightSolenoid();
		this.backLeftSolenoid = robot.getBackLeftSolenoid();
		this.frontLeftMotor = robot.getFrontLeftMotor();
		this.frontRightMotor = robot.getFrontRightMotor();
		this.backLeftMotor = robot.getBackLeftMotor();
		this.backRightMotor = robot.getBackRightMotor();
		this.gyro = robot.getGyro();

		frontLeftMotor.configEncoderCodesPerRev(20);
		frontRightMotor.configEncoderCodesPerRev(20);
		backLeftMotor.configEncoderCodesPerRev(20);
		backRightMotor.configEncoderCodesPerRev(20);
		
		ModDrive = SmartDashboard.getBoolean("Mod Drive Speed?", false);
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

	public void arcadeDrive() {
		driveState = false; // in arcade
		invertMotors(false, false, false, false);
		if(ModDrive = false) {
			robotDrive.arcadeDrive(
				Threshold.threshold((Robot.ARCADE_DRIVE_YAXIS_INVERT) * stick.getRawAxis(Robot.ARCADE_DRIVE_YAXIS)),
				Threshold.threshold((Robot.ARCADE_DRIVE_ROTATE_INVERT) * stick.getRawAxis(Robot.ARCADE_DRIVE_ROTATE)));
			setSolenoids(true, true, true, true);
		} else {
			robotDrive.arcadeDrive(
				(Threshold.threshold((Robot.ARCADE_DRIVE_YAXIS_INVERT) * stick.getRawAxis(Robot.ARCADE_DRIVE_YAXIS)))*SmartDashboard.getNumber("Mod Drive Speed", 0.5),
				(Threshold.threshold((Robot.ARCADE_DRIVE_ROTATE_INVERT) * stick.getRawAxis(Robot.ARCADE_DRIVE_ROTATE)))*SmartDashboard.getNumber("Mod Drive Speed", 0.5));
			setSolenoids(true, true, true, true);
		}
	}

	public void mecanumDrive() {
		driveState = true;// in mechanum
		invertMotors(true, false, true, false);
		if(ModDrive = false) {
			robotDrive.mecanumDrive_Cartesian(
				Threshold.threshold((Robot.MECANUM_DRIVE_XAXIS_INVERT) * stick.getRawAxis(Robot.MECANUM_DRIVE_XAXIS)),
				Threshold.threshold((Robot.MECANUM_DRIVE_YAXIS_INVERT) * stick.getRawAxis(Robot.MECANUM_DRIVEY_AXIS)),
				Threshold.threshold((Robot.MECANUM_DRIVE_ROTATE_INVERT) * stick.getRawAxis(Robot.MECANUM_DRIVE_ROTATE)),
				0.0);
			setSolenoids(false, false, false, false);
		} else {
			robotDrive.mecanumDrive_Cartesian(
				(Threshold.threshold((Robot.MECANUM_DRIVE_XAXIS_INVERT) * stick.getRawAxis(Robot.MECANUM_DRIVE_XAXIS)))*SmartDashboard.getNumber("Mod Drive Speed", 0.5),
				(Threshold.threshold((Robot.MECANUM_DRIVE_YAXIS_INVERT) * stick.getRawAxis(Robot.MECANUM_DRIVEY_AXIS)))*SmartDashboard.getNumber("Mod Drive Speed", 0.5),
				(Threshold.threshold((Robot.MECANUM_DRIVE_ROTATE_INVERT) * stick.getRawAxis(Robot.MECANUM_DRIVE_ROTATE)))*SmartDashboard.getNumber("Mod Drive Speed", 0.5),
				0.0);
			setSolenoids(false, false, false, false);
		}
	}
	
	public void halfActivation(){
		invertMotors(false, false, false, false);
		robotDrive.arcadeDrive(
				Threshold.threshold((Robot.ARCADE_DRIVE_YAXIS_INVERT) * stick.getRawAxis(Robot.ARCADE_DRIVE_YAXIS)),
				Threshold.threshold((Robot.ARCADE_DRIVE_ROTATE_INVERT) * stick.getRawAxis(Robot.ARCADE_DRIVE_ROTATE)));
		setSolenoids(true, true, false, false); 
	}

	public void drive(boolean driveToggle) {

		if (driveToggle) {

			arcadeDrive();

		} else {

			mecanumDrive();

		}
	}
	
	public void drive(boolean driveToggle, boolean halfActivationToggle){
		if(halfActivationToggle){
			halfActivation();
		}else if(driveToggle){
			arcadeDrive();
		}else{
			mecanumDrive();
		}
	}

	public void encoderReset() {
		frontLeftMotor.setEncPosition(0);

		frontRightMotor.setEncPosition(0);

		backLeftMotor.setEncPosition(0);

		backRightMotor.setEncPosition(0);

	}

	public int getFrontLeftEncoder() {
		return (Robot.FRONT_LEFT_MOTOR_NEGATE_ENCODER) * frontLeftMotor.getEncPosition();
	}

	public int getFrontRightEncoder() {
		return (Robot.FRONT_RIGHT_MOTOR_NEGATE_ENCODER) * frontRightMotor.getEncPosition();
	}

	public int getBackLeftEncoder() {
		return (Robot.BACK_LEFT_MOTOR_NEGATE_ENCODER) * backLeftMotor.getEncPosition();
	}

	public int getBackRightEncoder() {
		return (Robot.BACK_RIGHT_MOTOR_NEGATE_ENCODER) * backRightMotor.getEncPosition();
	}
}
