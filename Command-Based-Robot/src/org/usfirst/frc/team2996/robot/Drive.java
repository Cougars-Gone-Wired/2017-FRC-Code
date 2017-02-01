package org.usfirst.frc.team2996.robot;

import com.ctre.CANTalon;
import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;

public class Drive {
	Joystick stick;
	RobotDrive robotDrive;

	CANTalon frontLeftMotor;
	CANTalon frontRightMotor;
	CANTalon backLeftMotor;
	CANTalon backRightMotor;

	int arcadeDriveYAxis;
	int arcadeDriveRotate;
	int mecanumDriveXAxis;
	int mecanumDriveYAxis;
	int mecanumDriveRotate;
	int arcadeDriveYAxisInvert;// IF 1 INVERT JOYSTICK, IF NOT 1 DONT INVERT
								// JOYSTICK
	int arcadeDriveRotateInvert;
	int mecanumDriveXAxisInvert;
	int mecanumDriveYAxisInvert;
	int mecanumDriveRotateInvert;

	Solenoid solenoid1;
	Solenoid solenoid2;
	Solenoid solenoid3;
	Solenoid solenoid4;

	int wheelDiameter;
	int ticksPerRevolution;
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
		this.solenoid1 = robot.getSolenoid1();
		this.solenoid2 = robot.getSolenoid2();
		this.solenoid3 = robot.getSolenoid3();
		this.solenoid4 = robot.getSolenoid4();
		this.frontLeftMotor = robot.getFrontLeftMotor();
		this.frontRightMotor = robot.getFrontRightMotor();
		this.backLeftMotor = robot.getBackLeftMotor();
		this.backRightMotor = robot.getBackRightMotor();
		this.ticksPerRevolution = robot.getTicksPerRevolution();
		this.gyro = robot.getGyro();
		this.arcadeDriveYAxis = robot.getARCADEDRIVEYAXIS();
		this.arcadeDriveRotate = robot.getARCADEDRIVEROTATE();
		this.mecanumDriveYAxis = robot.getMECANUMDRIVEYAXIS();
		this.mecanumDriveXAxis = robot.getMECANUMDRIVEXAXIS();
		this.mecanumDriveRotate = robot.getMECANUMDRIVEROTATE();
		this.arcadeDriveYAxisInvert = robot.getARCADEDRIVEYAXISINVERT();
		this.arcadeDriveRotateInvert = robot.getARCADEDRIVEROTATEINVERT();
		this.mecanumDriveXAxisInvert = robot.getMECANUMDRIVEXAXISINVERT();
		this.mecanumDriveYAxisInvert = robot.getMECANUMDRIVEYAXISINVERT();
		this.mecanumDriveRotateInvert = robot.getMECANUMDRIVEROTATEINVERT();
	}

	public void SetSolenoids(boolean solenoid1, boolean solenoid2, boolean solenoid3, boolean solenoid4) {
		this.solenoid1.set(solenoid1);
		this.solenoid2.set(solenoid2);
		this.solenoid3.set(solenoid3);
		this.solenoid4.set(solenoid4);
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
		robotDrive.arcadeDrive(Threshold.threshold((arcadeDriveYAxisInvert) * stick.getRawAxis(arcadeDriveYAxis)),
				Threshold.threshold((arcadeDriveRotateInvert) * stick.getRawAxis(arcadeDriveRotate)));
		SetSolenoids(true, true, true, true);

	}

	public void mecanumDrive() {
		driveState = true;// in mechanum
		invertMotors(true, false, true, false);
		robotDrive.mecanumDrive_Cartesian(
				Threshold.threshold((mecanumDriveXAxisInvert) * stick.getRawAxis(mecanumDriveXAxis)),
				Threshold.threshold((mecanumDriveYAxisInvert) * stick.getRawAxis(mecanumDriveYAxis)),
				Threshold.threshold((mecanumDriveRotateInvert) * stick.getRawAxis(mecanumDriveRotate)), 0.0);
		SetSolenoids(false, false, false, false);

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
		return -frontLeftMotor.getEncPosition();
	}

	public int getFrontRightEncoder() {
		return frontRightMotor.getEncPosition();
	}

	public int getBackLeftEncoder() {
		return backLeftMotor.getEncPosition();
	}

	public int getBackRightEncoder() {
		return backRightMotor.getEncPosition();
	}
}
