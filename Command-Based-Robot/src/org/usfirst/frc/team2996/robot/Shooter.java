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
	private CANTalon auger;
	private CANTalon shooter;
	private CANTalon deflectorMotor;
	private Joystick stick;
	private int shooterForwardButton;
	private int shooterBackwardButton;
	private int augerForwardButton;
	private int augerBackwardButton;
//	private Toggle toggleUpButton;
//	private Toggle toggleDownButton;
//	private DigitalInput upperLimit;
//	private DigitalInput lowerLimit;

	/**
	 * 
	 * Constructor that gets parameters from the robot class
	 * @param robot
	 * 
	 */
	public Shooter(Robot robot) {
		this.auger = robot.getAugerMotor();
		this.shooter = robot.getShooterMotor();
		this.stick = robot.getStickManipulator();
		this.deflectorMotor = robot.getDeflectorMotor();
		this.shooterForwardButton = 1;
		this.shooterBackwardButton = 2;
		this.augerForwardButton = 3;
		this.augerBackwardButton = 4;
		//this.toggleUpButton = robot.getToggleUpButton();
		//this.toggleDownButton = robot.getToggleDownButton();
		//this.upperLimit = robot.getUpperLimit();
		//this.lowerLimit = robot.getLowerLimit();
		
		shooter.setProfile(0);
		shooter.setF(0.1097);
		shooter.setP(0.22);
		shooter.setI(0);
		shooter.setD(0);
		shooter.setFeedbackDevice(FeedbackDevice.QuadEncoder);
		shooter.reverseSensor(false);
		shooter.configNominalOutputVoltage(+0.0f, -0.0f);
		shooter.configPeakOutputVoltage(+12.0f, 0.0f);
		shooter.configEncoderCodesPerRev(20);
		shooter.reverseSensor(robot.SHOOTER_REVERSE_SENSOR);
		
		auger.changeControlMode(TalonControlMode.PercentVbus);
	}
	
	/**
	 * Changes the voltage of the shooter CANTalon depending on which button is pressed
	 */
	public void shooter() {
		shooter.setP(SmartDashboard.getNumber("P", 1));
		shooter.setI(SmartDashboard.getNumber("I", 1));
		shooter.setD(SmartDashboard.getNumber("D", 1));

		if (stick.getRawButton(shooterForwardButton)) {
			shooter.changeControlMode(TalonControlMode.Speed);
			shooter.set(SmartDashboard.getNumber("shooter speed", 0));
		}  else {
			shooter.changeControlMode(TalonControlMode.PercentVbus);
			shooter.set(0);
		}

	}

	/**
	 * Changes the voltage of the auger CANTalon depending on which button is pressed
	 */
	public void auger() {
		if (stick.getRawButton(augerForwardButton)) {
			auger.changeControlMode(TalonControlMode.PercentVbus);
			auger.set(SmartDashboard.getNumber("auger voltage", 0));
		} else if (stick.getRawButton(augerBackwardButton)) {
			auger.changeControlMode(TalonControlMode.PercentVbus);
			auger.set(-(SmartDashboard.getNumber("auger voltage", 0)));
		} else {
			auger.changeControlMode(TalonControlMode.PercentVbus);
			auger.set(0);
		}			
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
	}

