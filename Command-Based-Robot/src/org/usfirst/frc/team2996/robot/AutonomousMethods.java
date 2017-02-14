package org.usfirst.frc.team2996.robot;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class AutonomousMethods {
	int wheelDiameter;
	public Robot robot;
	public Timer timer;
	
	public AutonomousMethods(Robot robot) {
		this.robot = robot;
		this.wheelDiameter = Robot.WHEEL_DIAMETER;
		this.timer = robot.getTimer();
	}

	public double turn(String direction, double angle, double speed) { //add string parameter for side of field (red or blue)
		robot.gyro.reset();
		
		sleep();
		
		if (robot.drive.isMechanum()) {
			robot.drive.arcadeDrive();
		}
		
		direction = direction.toLowerCase();
		if ((direction.equals("left")) && DriverStation.getInstance().isAutonomous()) {
			while (robot.gyro.getAngle() > -angle) {
				SmartDashboard.putNumber("gyro", robot.gyro.getAngle());
				robot.drive.robotDrive.tankDrive(-speed, speed);// rotate speed in voltage
			}
		} else {
			while ((robot.gyro.getAngle() < angle) && DriverStation.getInstance().isAutonomous()) {
				SmartDashboard.putNumber("gyro", robot.gyro.getAngle());
				robot.drive.robotDrive.tankDrive(speed, -speed);
			}
		}
		double finalAngle = robot.gyro.getAngle();
		robot.gyro.reset();
		return finalAngle;
	}

	public double moveStraight(String direction, int distance, double speed) { // add string parameter for side of field (red or blue)
		double encoderAverage = 0;
		int encodersWorking = 0;
		direction = direction.toLowerCase();
		robot.gyro.reset();
		robot.drive.encoderReset();
		robot.drive.arcadeDrive();
		
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
				SmartDashboard.putNumber("frontLeft", robot.drive.frontLeftMotor.getEncPosition());
				SmartDashboard.putNumber("backLeft", robot.drive.backLeftMotor.getEncPosition());
				// robot.gyro.correction while driving
				if (robot.gyro.getAngle() < -1) {
					robot.drive.robotDrive.tankDrive(speed + 0.05, speed);
				} else if (robot.gyro.getAngle() > 1) {
					robot.drive.robotDrive.tankDrive(speed, speed + 0.05);
				} else {
					robot.drive.robotDrive.tankDrive(speed, speed);
				}
			}
		} else {

			while ((-encoderAverage >= -neededEncCounts) && DriverStation.getInstance().isAutonomous()) {
				encodersWorking = encodersWorking(); // amount of encoders
														// working
				encoderAverage = encoderAverage(encodersWorking);
				SmartDashboard.putNumber("encoderAVG", encoderAverage);
				SmartDashboard.putNumber("encodersWorking", encodersWorking);
				SmartDashboard.putNumber("frontLeft", robot.drive.frontLeftMotor.getEncPosition());
				SmartDashboard.putNumber("backLeft", robot.drive.backLeftMotor.getEncPosition());
				if (robot.gyro.getAngle() < -1) {
					robot.drive.robotDrive.tankDrive(-speed, -speed - 0.1);
				} else if (robot.gyro.getAngle() > 1) {
					robot.drive.robotDrive.tankDrive(-speed - 0.1, -speed);
				} else {
					robot.drive.robotDrive.tankDrive(-speed, -speed);
				}
			}
		}

		robot.drive.robotDrive.tankDrive(0.0, 0.0);
		robot.drive.encoderReset();
		robot.wait(100);
		return encoderAverage;

	}

	public double strafe(String direction, int distance, double speed) { // add string parameter for the side of field (red or blue)
		robot.drive.mecanumDrive();
		robot.gyro.reset();
		robot.drive.encoderReset();
		
		sleep();
		
		double encoderAverage = 0;
		int encodersWorking = 0;
		direction = direction.toLowerCase();
		

		if (direction == "left") {
			while ((-encoderAverage > -distance) && DriverStation.getInstance().isAutonomous()) {
				encodersWorking = encodersWorking();
                encoderAverage = encoderAverage(encodersWorking);
				robot.drive.robotDrive.mecanumDrive_Cartesian(speed, 0, 0, 0);
			}

		} else {
			while ((encoderAverage < distance) && DriverStation.getInstance().isAutonomous()) {
				encodersWorking = encodersWorking();
				encoderAverage = encoderAverage(encodersWorking);
				robot.drive.robotDrive.mecanumDrive_Cartesian(-speed, 0, 0, 0);
			}
		}
		robot.drive.robotDrive.tankDrive(0.0, 0.0);
		robot.gyro.reset();
		robot.drive.encoderReset();

		return encoderAverage;
	}
	
	public void shoot(double shootTime){
		
		sleep();
		robot.PIDShooter.setPID();
		
		timer.start();
		while(timer.get() <= shootTime){
			robot.PIDShooter.auger(Robot.AUGER_SPEED);
			robot.PIDShooter.shooter(true);
		}
			robot.PIDShooter.auger(0);
			robot.PIDShooter.shooter(false);
		
		sleep();
	}

	public int encodersWorking() { // calculates number of encoders working
		int encodersWorking = 4;
		if (Math.abs(robot.drive.frontLeftMotor.getEncPosition()) <= 1) {
			encodersWorking--;
		}
		if (Math.abs(robot.drive.frontRightMotor.getEncPosition()) <= 1) {
			encodersWorking--;
		}
		if (Math.abs(robot.drive.backLeftMotor.getEncPosition()) <= 1) {
			encodersWorking--;
		}
		if (Math.abs(robot.drive.backRightMotor.getEncPosition()) <= 1) {
			encodersWorking--;
		}

		if (encodersWorking == 0) { // starts the encoder counts at 1 so no zero division
			encodersWorking = 1;
		}
		return encodersWorking;
	}

	public int encoderAverage(int encodersWorking) { // calculates average
														// encoder counts
		int average = (Math.abs(robot.drive.getFrontLeftEncoder()) + Math.abs(robot.drive.getFrontRightEncoder()) + Math.abs(robot.drive.getBackLeftEncoder())
		+ Math.abs(robot.drive.getBackRightEncoder()) / encodersWorking);

		return average;
	}
	
	
	public void stop(){
		robot.drive.robotDrive.tankDrive(0, 0);
		robot.PIDShooter.auger(0);
		robot.PIDShooter.shooter(false);
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
