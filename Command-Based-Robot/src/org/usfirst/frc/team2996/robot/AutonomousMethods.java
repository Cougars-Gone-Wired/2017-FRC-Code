package org.usfirst.frc.team2996.robot;

import com.ctre.CANTalon;
import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class AutonomousMethods {
	int wheelDiameter;
	Robot robot;
	AHRS gyro;
	CANTalon frontLeftMotor, frontRightMotor, backLeftMotor, backRightMotor;
	Drive drive;
	Shooter PIDShooter;
	Intake intake;
	
	public AutonomousMethods(Robot robot) {
		this.robot = robot;
		this.gyro = robot.getGyro();
		this.drive = robot.getDrive();
		this.wheelDiameter = Robot.WHEEL_DIAMETER;
		this.PIDShooter = robot.getPIDShooter();
		this.intake = robot.getIntake();
	}

	public double turn(String direction, double angle, double speed) { //add string parameter for side of field (red or blue)
		gyro.reset();
		sleep();
		
		if (drive.isMecanum()) {
			drive.arcadeDrive();
		}
		
		direction = direction.toLowerCase();
		if ((direction.equals("left")) && DriverStation.getInstance().isAutonomous()) {
			while (gyro.getAngle() > -angle) {
				SmartDashboard.putNumber("gyro", gyro.getAngle());
				drive.robotDrive.tankDrive(-speed, speed);// rotate speed in voltage
			}
		} else {
			while ((gyro.getAngle() < angle) && DriverStation.getInstance().isAutonomous()) {
				SmartDashboard.putNumber("gyro", gyro.getAngle());
				drive.robotDrive.tankDrive(speed, -speed);
			}
		}
		double finalAngle = gyro.getAngle();
		gyro.reset();
		return finalAngle;
	}

	public double moveStraight(String direction, int distance, double speed) { // add string parameter for side of field (red or blue)
		double encoderAverage = 0;
		int encodersWorking = 0;
		direction = direction.toLowerCase();
		gyro.reset();
		drive.encoderReset();
	
			drive.arcadeDrive();

		
		sleep();
		
//	double distPerTick = (Math.PI * wheelDiameter);
//	double circumference = distPerTick / Robot.TICKS_PER_REVOLUTION;
//	double neededEncCounts = distance / circumference;
//	neededEncCounts = Math.round(neededEncCounts);
//	SmartDashboard.putNumber("neededEncCounts", neededEncCounts);

		if (direction.equals("forward")) {
			while ((encoderAverage < distance) && DriverStation.getInstance().isAutonomous()) {
				encodersWorking = encodersWorking();
				encoderAverage = encoderAverage(encodersWorking);
				SmartDashboard.putNumber("encoderAVG", encoderAverage);
				SmartDashboard.putNumber("encodersWorking", encodersWorking);
				SmartDashboard.putNumber("frontLeft", drive.frontLeftMotor.getEncPosition());
				SmartDashboard.putNumber("backLeft", drive.backLeftMotor.getEncPosition());
				// gyro.correction while driving
				if (gyro.getAngle() < -0.5) {
					drive.robotDrive.tankDrive(speed + 0.05, speed);
				} else if (gyro.getAngle() > 0.5) {
					drive.robotDrive.tankDrive(speed, speed + 0.05);
				} else {
					drive.robotDrive.tankDrive(speed, speed);
				}
			}
		} else {

			while ((-encoderAverage >= -distance) && DriverStation.getInstance().isAutonomous()) {
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

	public double moveStraightMecanum(String direction, int distance, double speed) { // add string parameter for side of field (red or blue)
		double encoderAverage = 0;
		int encodersWorking = 0;
		direction = direction.toLowerCase();
		gyro.reset();
		drive.encoderReset();
	
			drive.mecanumDrive();

		
		sleep();
		
//	double distPerTick = (Math.PI * wheelDiameter);
//	double circumference = distPerTick / Robot.TICKS_PER_REVOLUTION;
//	double neededEncCounts = distance / circumference;
//	neededEncCounts = Math.round(neededEncCounts);
//	SmartDashboard.putNumber("neededEncCounts", neededEncCounts);

		if (direction.equals("forward")) {
			while ((encoderAverage < distance) && DriverStation.getInstance().isAutonomous()) {
				encodersWorking = encodersWorking();
				encoderAverage = encoderAverage(encodersWorking);
				SmartDashboard.putNumber("encoderAVG", encoderAverage);
				SmartDashboard.putNumber("encodersWorking", encodersWorking);
				SmartDashboard.putNumber("frontLeft", drive.frontLeftMotor.getEncPosition());
				SmartDashboard.putNumber("backLeft", drive.backLeftMotor.getEncPosition());
				// gyro.correction while driving
				if (gyro.getAngle() < -1) {
					drive.robotDrive.tankDrive(-(speed + 0.05), speed);
				} else if (gyro.getAngle() > 1) {
					drive.robotDrive.tankDrive(-speed, speed + 0.05);
				} else {
					drive.robotDrive.tankDrive(-speed, speed);
				}
			}
		} else {

			while ((-encoderAverage >= -distance) && DriverStation.getInstance().isAutonomous()) {
				encodersWorking = encodersWorking(); // amount of encoders
														// working
				encoderAverage = encoderAverage(encodersWorking);
				SmartDashboard.putNumber("encoderAVG", encoderAverage);
				SmartDashboard.putNumber("encodersWorking", encodersWorking);
				SmartDashboard.putNumber("frontLeft", drive.frontLeftMotor.getEncPosition());
				SmartDashboard.putNumber("backLeft", drive.backLeftMotor.getEncPosition());
				if (gyro.getAngle() < -1) {
					drive.robotDrive.tankDrive(-(-speed), -speed - 0.1);
				} else if (gyro.getAngle() > 1) {
					drive.robotDrive.tankDrive(-(-speed - 0.1), -speed);
				} else {
					drive.robotDrive.tankDrive(-(-speed), -speed);
				}
			}
		}

		drive.robotDrive.tankDrive(0.0, 0.0);
		drive.encoderReset();
		robot.wait(100);
		return encoderAverage;

	}
	public double strafe(String direction, int distance, double speed) { // add string parameter for the side of field (red or blue)
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
	
	public void shoot(double shootTime){
		sleep();
		Timer shootTimer = new Timer();
		shootTimer.reset();
		PIDShooter.setPID();
		shootTimer.start();
		while(shootTimer.get() <= shootTime && DriverStation.getInstance().isAutonomous()){
			PIDShooter.auger(Robot.AUGER_SPEED);
			PIDShooter.pidShooter(true);
		}
			PIDShooter.auger(0);
			PIDShooter.pidShooter(false);
		shootTimer.reset();
		sleep();
	}
	
	public void gearDrop(double dropTime, double driveSpeed){
		sleep();
		Timer gearTimer = new Timer();
		gearTimer.reset();
		gearTimer.start();
		drive.mecanumDrive();
		while(gearTimer.get() <= dropTime-1 && DriverStation.getInstance().isAutonomous()){
			intake.intakeOuttake(false, false);
			intake.gearActivation(false);
		}
		moveStraightMecanum("forward",500,driveSpeed);
		while(gearTimer.get() <= dropTime && DriverStation.getInstance().isAutonomous()){
			intake.intakeOuttake(true, false);
			intake.gearActivation(true);
			moveStraightMecanum("backward",1000,driveSpeed);
		}
		
		intake.intakeOuttake(false, true);
		intake.gearActivation(false);
		gearTimer.reset();
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

		if (encodersWorking == 0) { // starts the encoder counts at 1 so no zero division
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
		PIDShooter.auger(0);
		PIDShooter.pidShooter(false);
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
	

	public double saisTurn(String direction, double angle, double speed) { //add string parameter for side of field (red or blue)
		gyro.reset();
		
		sleep();
		
		if (drive.isMecanum()) {
			drive.arcadeDrive();
		}
		
		direction = direction.toLowerCase();
		if ((direction.equals("left"))) {
			if (gyro.getAngle() > -angle) {
				SmartDashboard.putNumber("gyro", gyro.getAngle());
				drive.robotDrive.tankDrive(-speed, speed);// rotate speed in voltage
			}
		} else {
			if ((gyro.getAngle() < angle)) {
				SmartDashboard.putNumber("gyro", gyro.getAngle());
				drive.robotDrive.tankDrive(speed, -speed);
			}
		}
		double finalAngle = gyro.getAngle();
		return finalAngle;
	}
	
	public boolean isTurnFinished(String direction, int angle){
		direction = direction.toLowerCase();
		if(direction.equals("left")){
			return gyro.getAngle() < -angle;
		} else{
			return gyro.getAngle() > angle;
		}
	}
}
