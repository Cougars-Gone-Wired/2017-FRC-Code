package org.usfirst.frc.team2996.robot;

import com.ctre.CANTalon;
import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;


public class Drive {
Joystick stick = new Joystick(0);
RobotDrive robotDrive;
int arcadeDriveYAxis = 1;
int arcadeDriveRotate = 4;
int mecanumDriveXAxis = 0;
int mecanumDriveYAxis = 1;
int mecanumDriveRotate = 4;
CANTalon frontLeftMotor;
CANTalon frontRightMotor;
CANTalon backLeftMotor;
CANTalon backRightMotor;


Solenoid solenoid1;
Solenoid solenoid2;
Solenoid solenoid3;
Solenoid solenoid4;

int wheelDiameter;
int ticksPerRevolution;
AHRS gyro;

Drive(Robot robot){
	this.stick = robot.getStick();
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
}

public void SetSolenoids(boolean solenoid1,boolean solenoid2,boolean solenoid3,boolean solenoid4){
	this.solenoid1.set(solenoid1);
	this.solenoid2.set(solenoid2);
	this.solenoid3.set(solenoid3);
	this.solenoid4.set(solenoid4);
}
public void invertMotors( boolean frontLeftMotorInvert, boolean frontRightMotorInvert, boolean backLeftMotorInvert, boolean backRightMotorInvert){
	frontLeftMotor.setInverted(frontLeftMotorInvert);
	frontRightMotor.setInverted(frontRightMotorInvert);
	backLeftMotor.setInverted(backLeftMotorInvert);
	backRightMotor.setInverted(backRightMotorInvert);
}

public  void arcadeDrive(){
	 robotDrive.arcadeDrive(Threshold.threshold(-stick.getRawAxis(4)), Threshold.threshold(-stick.getRawAxis(1)));
	 SetSolenoids(true, true, true, true);

}
public void mecanumDrive(){
	  robotDrive.mecanumDrive_Cartesian(Threshold.threshold(stick.getRawAxis(0)) ,Threshold.threshold(-stick.getRawAxis(1)),Threshold.threshold(-stick.getRawAxis(4)) ,0.0);
	SetSolenoids(false, false, false, false);
	

}
public void drive(boolean driveToggle){

	if(driveToggle){
		   invertMotors(true, false, true, false);
	   arcadeDrive();
	
	}
	else{
		   invertMotors(true, false, true, false);
   mecanumDrive();

	}
}
}
