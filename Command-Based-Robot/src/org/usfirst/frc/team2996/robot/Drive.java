package org.usfirst.frc.team2996.robot;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;

public class Drive {
Joystick stick = new Joystick(0);
RobotDrive myRobot;
int arcadeDriveYAxis = 1;
int arcadeDriveRotate = 4;
int mecanumDriveXAxis = 0;
int mecanumDriveYAxis = 1;
int mecanumDriveRotate = 4;
CANTalon frontLeftMotor;
CANTalon frontRightMotor;
CANTalon backLeftMotor;
CANTalon backRightMotor;
DoubleSolenoid solenoid1;
DoubleSolenoid solenoid2;
DoubleSolenoid solenoid3;
DoubleSolenoid solenoid4;
Drive(Joystick stick, RobotDrive myRobot, DoubleSolenoid solenoid1,DoubleSolenoid solenoid2, DoubleSolenoid solenoid3, DoubleSolenoid solenoid4,  CANTalon frontLeftMotor, CANTalon frontRightMotor,CANTalon backLeftMotor, CANTalon backRightMotor  ){
	this.stick = stick;
	this.myRobot = myRobot;
	this.solenoid1 = solenoid1;
	this.solenoid2 = solenoid2;
	this.solenoid3 = solenoid3;
	this.solenoid4 = solenoid4;
	this.frontLeftMotor = frontLeftMotor;
	this.frontRightMotor = frontRightMotor;
	this.backLeftMotor = backLeftMotor;
	this.backRightMotor = backRightMotor;
}

public void SetSolenoids(DoubleSolenoid.Value Value1,DoubleSolenoid.Value Value2,DoubleSolenoid.Value Value3,DoubleSolenoid.Value Value4){
	solenoid1.set(Value1);
	solenoid2.set(Value2);
	solenoid3.set(Value3);
	solenoid4.set(Value4);
}
public void invertMotors( boolean frontLeftMotorInvert, boolean frontRightMotorInvert, boolean backLeftMotorInvert, boolean backRightMotorInvert){
	frontLeftMotor.setInverted(frontLeftMotorInvert);
	frontRightMotor.setInverted(frontRightMotorInvert);
	backLeftMotor.setInverted(backLeftMotorInvert);
	backRightMotor.setInverted(backRightMotorInvert);
}
Drive(Joystick stick, RobotDrive myRobot){
	this.stick = stick;
	this.myRobot = myRobot;
}
public  void arcadeDrive(){
	 myRobot.arcadeDrive(Threshold.threshold(-stick.getRawAxis(4)), Threshold.threshold(-stick.getRawAxis(1)));
	 SetSolenoids(DoubleSolenoid.Value.kForward, DoubleSolenoid.Value.kForward, DoubleSolenoid.Value.kForward, DoubleSolenoid.Value.kForward);

}
public void mecanumDrive(){
	  myRobot.mecanumDrive_Cartesian(Threshold.threshold(stick.getRawAxis(0)) ,Threshold.threshold(-stick.getRawAxis(1)),Threshold.threshold(-stick.getRawAxis(4)) ,0.0);
	SetSolenoids(DoubleSolenoid.Value.kReverse, DoubleSolenoid.Value.kReverse, DoubleSolenoid.Value.kReverse, DoubleSolenoid.Value.kReverse);
	

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
