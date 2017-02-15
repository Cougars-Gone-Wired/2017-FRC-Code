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
	private CANTalon intakeMotor;
	private Joystick stick;
	private int shooterButton;
	private int augerForwardButton;
	private int augerBackwardButton;
	private Toggle toggleUpButton;
	private Toggle toggleDownButton;
	private DigitalInput upperLimit;
	private DigitalInput lowerLimit;

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
		this.intakeMotor = robot.getIntakeMotor();
		this.shooterButton = Robot.SHOOTER_BUTTON;
		this.augerForwardButton = Robot.AUGER_FORWARD_BUTTON;
		this.augerBackwardButton = Robot.AUGER_BACKWARD_BUTTON;
		
		this.toggleUpButton = robot.getToggleUpButton();
		this.toggleDownButton = robot.getToggleDownButton();
		this.upperLimit = robot.getUpperLimit();
		this.lowerLimit = robot.getLowerLimit();
	}
	
	/**
	 * Changes the voltage of the shooter CANTalon depending on which button is pressed
	 */
	public void shooter() {
		shooterMotor.setF(SmartDashboard.getNumber("F", 0.1097));
		shooterMotor.setP(SmartDashboard.getNumber("P", 1));
		shooterMotor.setI(SmartDashboard.getNumber("I", 1));
		shooterMotor.setD(SmartDashboard.getNumber("D", 1));

		if (stick.getRawButton(shooterButton)) {
			shooterMotor.changeControlMode(TalonControlMode.Speed);
			shooterMotor.set(SmartDashboard.getNumber("shooter speed", 0));
			intakeMotor.set(1);
		}  else {
			shooterMotor.changeControlMode(TalonControlMode.PercentVbus);
			shooterMotor.set(0);
		}

	}

	public void shooter(boolean shoot) {
		if (shoot) {
			shooterMotor.changeControlMode(TalonControlMode.Speed);
			shooterMotor.set(SmartDashboard.getNumber("shooter speed", 0));
			intakeMotor.set(1);
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
			augerMotor.set(SmartDashboard.getNumber("auger voltage", 0));
		} else if (stick.getRawButton(augerBackwardButton)) {
			augerMotor.changeControlMode(TalonControlMode.PercentVbus);
			augerMotor.set(-(SmartDashboard.getNumber("auger voltage", 0)));
		} else {
			augerMotor.changeControlMode(TalonControlMode.PercentVbus);
			augerMotor.set(0);
		}			
	}
	
	public void auger(double speed){
		augerMotor.set(speed);
	}
	public void deflector() {
		deflectorMotor.set(stick.getRawAxis(1));
		/*
		if(upperLimit.get() == true || lowerLimit.get() == true){
			toggleUpButton.reset();
			toggleDownButton.reset();
		}else 
		if(toggleUpButton.toggle() == true && upperLimit.get() == false){
			deflectorMotor.set(0.2);
		}else if(toggleDownButton.toggle() == true && lowerLimit.get() == false){
			deflectorMotor.set(-0.2);
		}else{
			deflectorMotor.set(0);
		}
		*/
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
		augerMotor.changeControlMode(TalonControlMode.PercentVbus);
	}
	}

