package org.usfirst.frc.team2996.robot;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.AnalogGyro;


public class AutonomousMethods {
	AHRS gyro;
	Drive drive;
	AutonomousMethods(AHRS gyro, Drive drive){
		this.gyro = gyro;
		this.drive = drive;
	}
	public double turn(String direction, double angle){
		gyro.reset();
		drive.arcadeDrive();
direction = direction.toLowerCase();
if(direction == "left"){
	while(gyro.getAngle()>-angle){
	drive.robotDrive.arcadeDrive(0, 0.5);
	}
}else{
	while(gyro.getAngle()<angle){
		drive.robotDrive.arcadeDrive(0, -0.5);
	}
}
drive.robotDrive.tankDrive(0, 0);
gyro.reset();
return gyro.getAngle();
	}
	
/*	public double move(String direction, double distance){
	
		direction = direction.toLowerCase();
		if(direction == "back"){
			while(encoder.get()>-distance){
				//move backwards
			}
		}else{
			while(encoder.get()<distance){
				//move forwards
			}
		}
		
	}
	*/
}
