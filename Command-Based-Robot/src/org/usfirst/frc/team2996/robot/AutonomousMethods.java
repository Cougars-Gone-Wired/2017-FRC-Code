package org.usfirst.frc.team2996.robot;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.AnalogGyro;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class AutonomousMethods {
	int wheelDiameter;
	AHRS gyro;
	Drive drive;

	AutonomousMethods(Robot robot) {
		this.gyro = robot.getGyro();
		this.drive = robot.getDrive();
		this.wheelDiameter = robot.WHEELDIAMETER;
	}

	public double turn(String direction, double angle) {
		gyro.reset();
		if (drive.isMechanum()) {
			drive.arcadeDrive();
		}
		direction = direction.toLowerCase();
		if ((direction.equals("left")) && DriverStation.getInstance().isAutonomous()) {
			while (gyro.getAngle() > -angle) {
				drive.robotDrive.arcadeDrive(0, 0.6);// rotate speed in voltage
			}
		} else {
			while ((gyro.getAngle() < angle) && DriverStation.getInstance().isAutonomous()) {
				drive.robotDrive.arcadeDrive(0, -0.6);
			}
		}
		double finalAngle = gyro.getAngle();
		gyro.reset();
		return finalAngle;
	}

	public double moveStraight(String direction, int distance) {
		double encoderAverage = 0;
		int encodersWorking = 0;
		direction = direction.toLowerCase();
		gyro.reset();
		drive.encoderReset();

		drive.arcadeDrive();

	double distPerTick = (Math.PI * wheelDiameter) / Robot.TICKSPERREVOLUTION;
	double neededEncCounts = distance / distPerTick;
	neededEncCounts = Math.round(neededEncCounts);
	SmartDashboard.putNumber("neededEncCounts", neededEncCounts);

		if (direction.equals("forward")) {
			while ((encoderAverage < neededEncCounts) && DriverStation.getInstance().isAutonomous()) {
				encodersWorking = encodersWorking();
				encoderAverage = encoderAverage(encodersWorking);
				SmartDashboard.putNumber("encoderAVG", encoderAverage);
				SmartDashboard.putNumber("encodersWorking", encodersWorking);
				SmartDashboard.putNumber("frontLeft", drive.frontLeftMotor.getEncPosition());
				SmartDashboard.putNumber("backLeft", drive.backLeftMotor.getEncPosition());
				// gyro correction while driving
				if (gyro.getAngle() < 1) {
					drive.robotDrive.tankDrive(0.5, 0.55);
				} else if (gyro.getAngle() > 1) {
					drive.robotDrive.tankDrive(0.55, 0.5);
				} else {
					drive.robotDrive.tankDrive(0.5, 0.5);
				}
			}
		} else {

			while ((encoderAverage >= -neededEncCounts) && DriverStation.getInstance().isAutonomous()) {
				encodersWorking = encodersWorking(); // amount of encoders
														// working
				encoderAverage = encoderAverage(encodersWorking);
				SmartDashboard.putNumber("encoderAVG", encoderAverage);
				SmartDashboard.putNumber("encodersWorking", encodersWorking);
				SmartDashboard.putNumber("frontLeft", drive.frontLeftMotor.getEncPosition());
				SmartDashboard.putNumber("backLeft", drive.backLeftMotor.getEncPosition());
				if (gyro.getAngle() < -1) {
					drive.robotDrive.tankDrive(-0.5, -0.55);
				} else if (gyro.getAngle() > 1) {
					drive.robotDrive.tankDrive(-0.55, -0.5);
				} else {
					drive.robotDrive.tankDrive(-0.5, -0.5);
				}
			}
		}

		drive.robotDrive.tankDrive(0.0, 0.0);
		drive.encoderReset();

		return encoderAverage;

	}

	public double strafe(String direction, int distance) { // strafe not
															// currently working

		gyro.reset();

		direction = direction.toLowerCase();
		drive.mecanumDrive();

		if (direction == "left") {
			while (gyro.getDisplacementX() > -distance) {
				SmartDashboard.putNumber("backLeft", gyro.getDisplacementY());
				drive.robotDrive.mecanumDrive_Cartesian(0.5, 0, 0, gyro.getAngle());
			}

		} else {
			while (gyro.getDisplacementX() < distance) {
				drive.robotDrive.mecanumDrive_Cartesian(-0.5, 0, 0, gyro.getAngle());
			}
		}
		double displacementX = gyro.getDisplacementX();
		gyro.reset();
		drive.robotDrive.tankDrive(0.0, 0.0);

		return displacementX;
	}

	public int encodersWorking() { // calculates number of encoders working
		int encodersWorking = 4;
		if (Math.abs(drive.frontLeftMotor.getEncPosition()) <= 1) {
			encodersWorking--;
		}
		if (Math.abs(drive.frontRightMotor.getEncPosition()) <= 1) {
			encodersWorking--;
		}
		if (Math.abs(drive.backLeftMotor.getEncPosition()) <= 1) {
			encodersWorking--;
		}
		if (Math.abs(drive.backRightMotor.getEncPosition()) <= 1) {
			encodersWorking--;
		}

		if (encodersWorking == 0) { // starts the encoder counts at 1 so no zero
									// division
			encodersWorking = 1;
		}
		return encodersWorking;
	}

	public int encoderAverage(int encodersWorking) { // calculates average
														// encoder counts
		int average = (drive.getFrontLeftEncoder() + drive.getFrontRightEncoder() + drive.getBackLeftEncoder()
				+ drive.getBackRightEncoder()) / encodersWorking;

		return average;
	}
}
