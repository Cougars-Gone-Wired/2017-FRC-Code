package org.usfirst.frc.team2996.robot;

import com.ctre.CANTalon;
import com.ctre.CANTalon.FeedbackDevice;
import com.ctre.CANTalon.TalonControlMode;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * 
 * Stores methods for changing the voltage 
 * of the shooter and auger CANTalons 
 * based on button presses
 * @author Tieguy01
 *
 */
public class Shooter {
	private CANTalon augerMotor;
	private CANTalon shooterMotor;
	private CANTalon deflectorMotor;
	private Joystick stick;
	private int shooterButton;
	private int augerForwardButton;
	private int augerBackwardButton;
	private Toggle toggleUpButton;
	private Toggle toggleDownButton;
	private DigitalInput limit;

	/**
	 * 
	 * Constructor that gets parameters from the robot class
	 * @param robot
	 * 
	 */
	public Shooter(Robot robot) {
		this.augerMotor = robot.getAugerMotor();
		this.shooterMotor = robot.getShooterMotor();
		this.stick = robot.getStickManipulator();
		this.deflectorMotor = robot.getDeflectorMotor();
		this.shooterButton = Robot.SHOOTER_BUTTON;
		this.augerForwardButton = Robot.AUGER_FORWARD_BUTTON;
		this.augerBackwardButton = Robot.AUGER_BACKWARD_BUTTON;
		
		this.toggleUpButton = robot.getToggleUpButton();
		this.toggleDownButton = robot.getToggleDownButton();
		this.limit = robot.getLimit();
	}
	
	/**
	 * Changes the voltage of the shooter CANTalon depending on which button is pressed
	 */
	
	public void shoot(boolean toggle){
		if(toggle ==  false){
			pidShooter();
			SmartDashboard.putString("Shooting Mode", "PID");
		}else{
			voltageShoot();
			SmartDashboard.putString("Shooting Mode", "Voltage");
		}
	}
	public void pidShooter() {
		shooterMotor.setF(SmartDashboard.getNumber("F", 0.1097));
		shooterMotor.setP(SmartDashboard.getNumber("P", 1));
		shooterMotor.setI(SmartDashboard.getNumber("I", 1));
		shooterMotor.setD(SmartDashboard.getNumber("D", 1));

		if (stick.getRawButton(shooterButton)) {
			shooterMotor.changeControlMode(TalonControlMode.Speed);
			shooterMotor.set(SmartDashboard.getNumber("shooter speed", 0));
		}  else {
			shooterMotor.changeControlMode(TalonControlMode.PercentVbus);
			shooterMotor.set(0);
		}

	}

	public void pidShooter(boolean shoot) {
		if (shoot) {
			shooterMotor.changeControlMode(TalonControlMode.Speed);
			shooterMotor.set(SmartDashboard.getNumber("shooter speed", 0));
		}  else {
			shooterMotor.changeControlMode(TalonControlMode.PercentVbus);
			shooterMotor.set(0);
		}

	}
	/**
	 * Changes the voltage of the auger CANTalon depending on which button is pressed
	 */
	public void auger() {
		if (stick.getRawButton(augerForwardButton)) {
			augerMotor.changeControlMode(TalonControlMode.PercentVbus);
			augerMotor.set(Robot.AUGER_INVERT*SmartDashboard.getNumber("auger voltage", 0));
		} else if (stick.getRawButton(augerBackwardButton)) {
			augerMotor.changeControlMode(TalonControlMode.PercentVbus);
			augerMotor.set(-Robot.AUGER_INVERT*SmartDashboard.getNumber("auger voltage", 0));
		} else {
			augerMotor.changeControlMode(TalonControlMode.PercentVbus);
			augerMotor.set(0);
		}			
	}
	
	public void auger(double speed){
		augerMotor.set(speed);
	}
	public void deflector() {
//		deflectorMotor.changeControlMode(TalonControlMode.PercentVbus);
//		deflectorMotor.configEncoderCodesPerRev(2048);
//		deflectorMotor.setFeedbackDevice(FeedbackDevice.QuadEncoder);
		deflectorMotor.set(Threshold.threshold(stick.getRawAxis(1)));
//		double angle = SmartDashboard.getNumber("Deflector Angle", 0);
//		double encPerDegree = 2048 / 360;
//		double neededEnc = encPerDegree * angle;
//		
//		if(limit.get() == true || ((deflectorMotor.getEncPosition() <= neededEnc + 10) && (deflectorMotor.getEncPosition() >= neededEnc - 10))){
//			toggleUpButton.reset();
//			toggleDownButton.reset();
//		} else if(limit.get() == true && toggleDownButton.toggle() == true){
//			deflectorMotor.setEncPosition(0);
//			deflectorMotor.set(0);
//		} else if (toggleUpButton.toggle() == true && (deflectorMotor.getEncPosition() <= neededEnc)){
//			deflectorMotor.set(0.2);
//		} else if (toggleDownButton.toggle() == true && deflectorMotor.getEncPosition() >= neededEnc){
//			deflectorMotor.set(-0.2);
//		} else {
//			deflectorMotor.set(0);
//		}
		SmartDashboard.putNumber("Deflector Encoder", deflectorMotor.getEncPosition());
	}
	
	public void setPID(){ // This method sets all PID settings for the shooter
		shooterMotor.setProfile(0);
		shooterMotor.setF(SmartDashboard.getNumber("F", 0.1097));
		shooterMotor.setP(SmartDashboard.getNumber("P", 1));
		shooterMotor.setI(SmartDashboard.getNumber("I", 1));
		shooterMotor.setD(SmartDashboard.getNumber("D", 1));
		shooterMotor.setFeedbackDevice(FeedbackDevice.QuadEncoder);
		shooterMotor.reverseSensor(false);
		shooterMotor.configNominalOutputVoltage(+0.0f, -0.0f);
		shooterMotor.configPeakOutputVoltage(+12.0f, 0.0f);
		shooterMotor.configEncoderCodesPerRev(Robot.ENCODER_CODES_PER_REV);
		shooterMotor.reverseSensor(Robot.SHOOTER_REVERSE_SENSOR);
		
		}
	
	public void voltageShoot(){
		if (stick.getRawButton(shooterButton)) {
			shooterMotor.changeControlMode(TalonControlMode.PercentVbus);
			shooterMotor.set(SmartDashboard.getNumber("shooter voltage", 0));
		}  else {
			shooterMotor.changeControlMode(TalonControlMode.PercentVbus);
			shooterMotor.set(0);
		}
	}
}

