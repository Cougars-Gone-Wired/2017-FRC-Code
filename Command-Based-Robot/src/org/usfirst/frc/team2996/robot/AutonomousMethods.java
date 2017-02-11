package org.usfirst.frc.team2996.robot;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.AnalogGyro;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class AutonomousMethods {
	int wheelDiameter;
	public AHRS gyro;
	public Drive drive;
	public Robot robot;

	public AutonomousMethods(Robot robot) {
		this.robot = robot;
		this.gyro = robot.getGyro();
		this.drive = robot.getDrive();
		this.wheelDiameter = Robot.WHEEL_DIAMETER;
	}

	public double turn(String direction, double angle) {
		gyro.reset();
		
		sleep();
		
		if (drive.isMechanum()) {
			drive.arcadeDrive();
		}
		
		direction = direction.toLowerCase();
		if ((direction.equals("left")) && DriverStation.getInstance().isAutonomous()) {
			while (gyro.getAngle() > -angle) {
				SmartDashboard.putNumber("gyro", gyro.getAngle());
				drive.robotDrive.arcadeDrive(0, 0.6);// rotate speed in voltage
			}
		} else {
			while ((gyro.getAngle() < angle) && DriverStation.getInstance().isAutonomous()) {
				SmartDashboard.putNumber("gyro", gyro.getAngle());
				drive.robotDrive.arcadeDrive(0, -0.6);
			}
		}
		double finalAngle = gyro.getAngle();
		gyro.reset();
		return finalAngle;
	}

	public double moveStraight(String direction, int distance, double speed) {
		double encoderAverage = 0;
		int encodersWorking = 0;
		direction = direction.toLowerCase();
		gyro.reset();
		drive.encoderReset();
		drive.arcadeDrive();
		
		sleep();
		
	double distPerTick = (Math.PI * wheelDiameter) / Robot.TICKS_PER_REVOLUTION;
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
				if (gyro.getAngle() < -1) {
					drive.robotDrive.tankDrive(speed + 0.05, speed);
				} else if (gyro.getAngle() > 1) {
					drive.robotDrive.tankDrive(speed, speed + 0.05);
				} else {
					drive.robotDrive.tankDrive(speed, speed);
				}
			}
		} else {

			while ((-encoderAverage >= -neededEncCounts) && DriverStation.getInstance().isAutonomous()) {
				encodersWorking = encodersWorking(); // amount of encoders
														// working
				encoderAverage = encoderAverage(encodersWorking);
				SmartDashboard.putNumber("encoderAVG", encoderAverage);
				SmartDashboard.putNumber("encodersWorking", encodersWorking);
				SmartDashboard.putNumber("frontLeft", drive.frontLeftMotor.getEncPosition());
				SmartDashboard.putNumber("backLeft", drive.backLeftMotor.getEncPosition());
				if (gyro.getAngle() < -1) {
					drive.robotDrive.tankDrive(-speed, -speed - 0.1);
				} else if (gyro.getAngle() > 1) {
					drive.robotDrive.tankDrive(-speed - 0.1, -speed);
				} else {
					drive.robotDrive.tankDrive(-speed, -speed);
				}
			}
		}

		drive.robotDrive.tankDrive(0.0, 0.0);
		drive.encoderReset();
		robot.wait(100);
		return encoderAverage;

	}

	public double strafe(String direction, int distance, double speed) {
		drive.mecanumDrive();
		gyro.reset();
		drive.encoderReset();
		
		sleep();
		
		double encoderAverage = 0;
		int encodersWorking = 0;
		direction = direction.toLowerCase();
		

		if (direction == "left") {
			while ((-encoderAverage > -distance) && DriverStation.getInstance().isAutonomous()) {
				encodersWorking = encodersWorking();
                encoderAverage = encoderAverage(encodersWorking);
				drive.robotDrive.mecanumDrive_Cartesian(speed, 0, 0, 0);
			}

		} else {
			while ((encoderAverage < distance) && DriverStation.getInstance().isAutonomous()) {
				encodersWorking = encodersWorking();
				encoderAverage = encoderAverage(encodersWorking);
				drive.robotDrive.mecanumDrive_Cartesian(-speed, 0, 0, 0);
			}
		}
		drive.robotDrive.tankDrive(0.0, 0.0);
		gyro.reset();
		drive.encoderReset();

		return encoderAverage;
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
		int average = (Math.abs(drive.getFrontLeftEncoder()) + Math.abs(drive.getFrontRightEncoder()) + Math.abs(drive.getBackLeftEncoder())
		+ Math.abs(drive.getBackRightEncoder()) / encodersWorking);

		return average;
	}
	
	
	public void stop(){
		drive.robotDrive.tankDrive(0, 0);
	}
	public static void sleep(){
		try {
			Thread.sleep(Robot.SLEEP_AUTO);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void sleep(int x){
		try {
			Thread.sleep(x);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
