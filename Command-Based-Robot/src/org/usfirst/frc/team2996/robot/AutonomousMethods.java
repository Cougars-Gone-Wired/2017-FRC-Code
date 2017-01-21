package org.usfirst.frc.team2996.robot;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.AnalogGyro;

public class AutonomousMethods {
	int wheelDiameter = 5;
	int encoderTick;

	AHRS gyro;
	Drive drive;

	AutonomousMethods(Robot robot) {
		this.gyro = robot.getGyro();
		this.drive = robot.getDrive();
	}

	public boolDoub turn(String direction, double angle) {
		double initialAngle = gyro.getAngle();// get initial angle
		boolean finished = false;
		drive.arcadeDrive();
		direction = direction.toLowerCase();
		if (direction == "left") {
			if (gyro.getAngle() > -angle) {
				drive.robotDrive.arcadeDrive(0, 0.5);
			}else{
				finished = true;
			}
		} else {
			if (gyro.getAngle() < angle) {
				drive.robotDrive.arcadeDrive(0, -0.5);
			}else{
				finished = true;
			}
		}
		return new boolDoub(finished, gyro.getAngle());
	}

	public double moveStraight(String direction, int distance) {
		direction = direction.toLowerCase();
		gyro.reset();
		double distPerTick = (Math.PI * wheelDiameter) / drive.ticksPerRevolution;
		double neededEncCounts = distance / distPerTick;
		neededEncCounts = Math.round(neededEncCounts);

		if (direction == "forward") {
			
			
			while ((average(drive.frontLeftMotor.getEncPosition(),
					drive.frontRightMotor.getEncPosition()) < neededEncCounts)
					|| (average(drive.frontLeftMotor.getEncPosition(),
							drive.backRightMotor.getEncPosition()) < neededEncCounts)
					|| (average(drive.frontLeftMotor.getEncPosition(),
							drive.backLeftMotor.getEncPosition()) < neededEncCounts)
					|| (average(drive.frontRightMotor.getEncPosition(),
							drive.backRightMotor.getEncPosition()) < neededEncCounts)
					|| (average(drive.frontLeftMotor.getEncPosition(),
							drive.backLeftMotor.getEncPosition()) < neededEncCounts)
					|| (average(drive.backLeftMotor.getEncPosition(),
							drive.backRightMotor.getEncPosition()) < neededEncCounts)) {
				if(gyro.getAngle() < -1){
					drive.robotDrive.tankDrive(0.55, 0.5);
				}else if(gyro.getAngle() > 1){
					drive.robotDrive.tankDrive(0.5, 0.55);
				}else{
					drive.robotDrive.tankDrive(0.5, 0.5);
				}
			}
		} else {
			while ((average(drive.frontLeftMotor.getEncPosition(),
					drive.frontRightMotor.getEncPosition()) > neededEncCounts)
					|| (average(drive.frontLeftMotor.getEncPosition(),
							drive.backRightMotor.getEncPosition()) > neededEncCounts)
					|| (average(drive.frontLeftMotor.getEncPosition(),
							drive.backLeftMotor.getEncPosition()) > neededEncCounts)
					|| (average(drive.frontRightMotor.getEncPosition(),
							drive.backRightMotor.getEncPosition()) > neededEncCounts)
					|| (average(drive.frontLeftMotor.getEncPosition(),
							drive.backLeftMotor.getEncPosition()) > neededEncCounts)
					|| (average(drive.backLeftMotor.getEncPosition(),
							drive.backRightMotor.getEncPosition()) > neededEncCounts)) {
				if(gyro.getAngle() < -1){
					drive.robotDrive.tankDrive(-0.5, -0.55);
				}else if(gyro.getAngle() > 1){
					drive.robotDrive.tankDrive(-0.55, -0.5);
				}else{
					drive.robotDrive.tankDrive(-0.5, -0.5);
				}

	
			}
			drive.robotDrive.tankDrive(0.0, 0.0);
		}

		return average(average(drive.frontLeftMotor.getEncPosition(), drive.frontRightMotor.getEncPosition()),
				average(drive.backLeftMotor.getEncPosition(), drive.backRightMotor.getEncPosition()));
	}

	public double strafe(String direction, int distance) {

		gyro.reset();

		direction = direction.toLowerCase();

		double distPerTick = (Math.PI * wheelDiameter) / drive.ticksPerRevolution;
		double neededEncCounts = distance / distPerTick;
		neededEncCounts = Math.round(neededEncCounts);
		drive.mecanumDrive();

		if (direction == "left") {
			while (gyro.getDisplacementX() > -distance) {
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

	public double average(double x, double y) {
		return (x + y) / 2;
	}
	/*
	 * public double move(String direction, double distance){
	 * 
	 * direction = direction.toLowerCase(); if(direction == "back"){
	 * while(encoder.get()>-distance){ //move backwards } }else{
	 * while(encoder.get()<distance){ //move forwards } }
	 * 
	 * }
	 */
}
