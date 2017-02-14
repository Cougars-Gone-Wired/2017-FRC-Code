package org.usfirst.frc.team2996.robot;

import com.ctre.CANTalon;
import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;

public class Drive {
	Joystick stick;
	public RobotDrive robotDrive;

	CANTalon frontLeftMotor;
	CANTalon frontRightMotor;
	CANTalon backLeftMotor;
	CANTalon backRightMotor;

	Solenoid forwardDriveSolenoid;
	Solenoid backwardDriveSolenoid;

	int wheelDiameter;
	AHRS gyro;
	boolean driveState = false; // state = true means Mechanum , false is arcade

	public boolean isMechanum() {
		return driveState;
	}

	public void setDriveState(boolean state) {
		this.driveState = state;
	}

	Drive(Robot robot) {
		this.stick = robot.getStickDrive();
		this.robotDrive = robot.getRobotDrive();
		this.forwardDriveSolenoid = robot.getForwardDriveSolenoid();
		this.backwardDriveSolenoid = robot.getBackwardDriveSolenoid();
		this.frontLeftMotor = robot.getFrontLeftMotor();
		this.frontRightMotor = robot.getFrontRightMotor();
		this.backLeftMotor = robot.getBackLeftMotor();
		this.backRightMotor = robot.getBackRightMotor();
		this.gyro = robot.getGyro();

		frontLeftMotor.configEncoderCodesPerRev(20);
		frontRightMotor.configEncoderCodesPerRev(20);
		backLeftMotor.configEncoderCodesPerRev(20);
		backRightMotor.configEncoderCodesPerRev(20);
	}

	public void SetSolenoids(boolean forwardDriveSolenoid, boolean backwardDriveSolenoid) {
		this.forwardDriveSolenoid.set(forwardDriveSolenoid);
		this.backwardDriveSolenoid.set(backwardDriveSolenoid);
	}

	public void invertMotors(boolean frontLeftMotorInvert, boolean frontRightMotorInvert, boolean backLeftMotorInvert,
			boolean backRightMotorInvert) {
		frontLeftMotor.setInverted(frontLeftMotorInvert);
		frontRightMotor.setInverted(frontRightMotorInvert);
		backLeftMotor.setInverted(backLeftMotorInvert);
		backRightMotor.setInverted(backRightMotorInvert);
	}

	public void arcadeDrive() {
		driveState = false; // in arcade
		invertMotors(false, false, false, false);
		robotDrive.arcadeDrive(
				Threshold.threshold((Robot.ARCADE_DRIVE_YAXIS_INVERT) * stick.getRawAxis(Robot.ARCADE_DRIVE_YAXIS)),
				Threshold.threshold((Robot.ARCADE_DRIVE_ROTATE_INVERT) * stick.getRawAxis(Robot.ARCADE_DRIVE_ROTATE)));
		SetSolenoids(true, true);

	}

	public void mecanumDrive() {
		driveState = true;// in mechanum
		invertMotors(true, false, true, false);
		robotDrive.mecanumDrive_Cartesian(
				Threshold.threshold((Robot.MECANUM_DRIVE_XAXIS_INVERT) * stick.getRawAxis(Robot.MECANUM_DRIVE_XAXIS)),
				Threshold.threshold((Robot.MECANUM_DRIVE_YAXIS_INVERT) * stick.getRawAxis(Robot.MECANUM_DRIVEY_AXIS)),
				Threshold.threshold((Robot.MECANUM_DRIVE_ROTATE_INVERT) * stick.getRawAxis(Robot.MECANUM_DRIVE_ROTATE)),
				0.0);
		SetSolenoids(false, false);

	}

	public void drive(boolean driveToggle) {

		if (driveToggle) {

			arcadeDrive();

		} else {

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
