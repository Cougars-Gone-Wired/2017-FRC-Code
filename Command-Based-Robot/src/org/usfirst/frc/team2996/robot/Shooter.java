package org.usfirst.frc.team2996.robot;

import com.ctre.CANTalon;
import com.ctre.CANTalon.FeedbackDevice;
import com.ctre.CANTalon.TalonControlMode;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Timer;
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
	Timer shooterTimer = new Timer();
	private CANTalon augerMotor;
	private CANTalon shooterMotor;
	private CANTalon deflectorMotor;
	private Joystick stick;
	private int shooterButton;
	private int augerForwardButton;
	private int augerBackwardButton;
	Solenoid frontLeft;
	Solenoid frontRight;
	Solenoid backLeft;
	Solenoid backRight;
	private Toggle toggleUpButton;
	private Toggle toggleDownButton;

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
		this.frontLeft = robot.getFrontLeftSolenoid();
		this.frontRight = robot.getFrontRightSolenoid();
		this.backLeft = robot.getBackLeftSolenoid();
		this.backRight = robot.getBackRightSolenoid();
		
		this.toggleUpButton = robot.getToggleUpButton();
		this.toggleDownButton = robot.getToggleDownButton();
		
		setPID();
		shooterTimer.start();
	}
	
	public int getDeflectorEncoder(){
		return Robot.DEFLECTOR_REVERSE_ENCODER*deflectorMotor.getEncPosition();
	}
	
	/**
	 * Changes the voltage of the shooter CANTalon depending on which button is pressed
	 */
	
	public void shootingMode(boolean toggle){
		if(toggle ==  true){
			pidShooter();
			SmartDashboard.putString("Shooting Mode", "PID");
		}else{
			voltageShoot();
			SmartDashboard.putString("Shooting Mode", "Voltage");
		}
	}
	public void pidShooter() {
	setPID();
	if(deflectorMotor.isFwdLimitSwitchClosed() == false){
		if (stick.getRawButton(shooterButton)) {
			shooterMotor.changeControlMode(TalonControlMode.Speed);
			shooterMotor.set(SmartDashboard.getNumber("shooter speed", 0));
			
			/*if(shooterTimer.get() % 5 <=2){
				backRight.set(true);
			} else {
				backRight.set(false);
			}
			*/
		} else {
			shooterMotor.changeControlMode(TalonControlMode.PercentVbus);
			shooterMotor.set(0);
			
	} } else {
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
		
//		if(shooterTimer.get() % 5 <=0.25){
//		backRight.set(true);
//	} else {
//		backRight.set(false);
//	}

	}
	/**
	 * Changes the voltage of the auger CANTalon depending on which button is pressed
	 */
	public void auger() {
		if(deflectorMotor.isFwdLimitSwitchClosed() == false){
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
	}else {
		augerMotor.changeControlMode(TalonControlMode.PercentVbus);
		augerMotor.set(0);
	}
	}
	
	public void auger(double speed){
		augerMotor.set(speed);
	}
	
	
	public void deflector() {
		double boilerEncoder =  SmartDashboard.getNumber("Boiler Deflector Position", 0);
		double shipEncoder = SmartDashboard.getNumber("Ship Deflector Position", 0);
		
		deflectorMotor.changeControlMode(TalonControlMode.PercentVbus);
		deflectorMotor.configEncoderCodesPerRev(2048);
		deflectorMotor.setFeedbackDevice(FeedbackDevice.QuadEncoder);
		
		SmartDashboard.putBoolean("Toggle Up Button", toggleUpButton.toggle());
		SmartDashboard.putBoolean("Toggle Down Button", toggleDownButton.toggle());
		
//		deflectorMotor.set(-(Threshold.threshold(stick.getRawAxis(1)) / 5));
		
		int deflectorEncoder = getDeflectorEncoder();
		 if (toggleUpButton.toggle() == true && toggleDownButton.toggle() == true){
			toggleDownButton.reset();
			toggleUpButton.reset(); 
//		} else if(getDeflectorEncoder()> shipEncoder && toggleDownButton.toggle() == true){
//			deflectorMotor.set(0.2 * Robot.DEFLECTOR_AUTO_INVERT);
//		} else if (getDeflectorEncoder() < shipEncoder && toggleUpButton.toggle() == true){
//			deflectorMotor.set(-0.2* Robot.DEFLECTOR_AUTO_INVERT);
		} else if (getDeflectorEncoder() < boilerEncoder && toggleUpButton.toggle() == true){
			deflectorMotor.set(-0.2* Robot.DEFLECTOR_AUTO_INVERT);
		} else if(getDeflectorEncoder()> boilerEncoder && toggleDownButton.toggle() == true){
			deflectorMotor.set(0.2* Robot.DEFLECTOR_AUTO_INVERT);
		}else if(deflectorMotor.isFwdLimitSwitchClosed() == true){
			toggleDownButton.reset();
			toggleUpButton.reset();
		} else if((deflectorEncoder >  (boilerEncoder - 20)) && (deflectorEncoder < (boilerEncoder + 20))){
			toggleUpButton.reset();
			toggleDownButton.reset();
//		} else if((deflectorEncoder > (shipEncoder -20)) && (deflectorEncoder < (shipEncoder + 20))){
//			toggleUpButton.reset();
//			toggleDownButton.reset();
		}else{
			deflectorMotor.set(0);
		}
		SmartDashboard.putNumber("Deflector Encoder", getDeflectorEncoder());
		SmartDashboard.putBoolean("Deflector Limit", deflectorMotor.isFwdLimitSwitchClosed());
	}
	
	public void setPID(){ // This method sets all PID settings for the shooter
		shooterMotor.setProfile(0);
		shooterMotor.setF(SmartDashboard.getNumber("F", 0.1097));
		shooterMotor.setP(SmartDashboard.getNumber("P", 5));
		shooterMotor.setI(SmartDashboard.getNumber("I", 0.011));
		shooterMotor.setD(SmartDashboard.getNumber("D", 0.05));
		shooterMotor.setFeedbackDevice(FeedbackDevice.QuadEncoder);
		shooterMotor.configNominalOutputVoltage(+0.0f, -0.0f);
		shooterMotor.configPeakOutputVoltage(+12.0f, -12.0f);
		shooterMotor.configEncoderCodesPerRev(Robot.ENCODER_CODES_PER_REV);
		shooterMotor.reverseOutput(Robot.SHOOTER_REVERSE_OUTPUT);
		}
	
	public void voltageShoot(){
		shooterMotor.configEncoderCodesPerRev(Robot.ENCODER_CODES_PER_REV);
		if(deflectorMotor.isFwdLimitSwitchClosed() == false){
		if (stick.getRawButton(shooterButton)) {
			shooterMotor.changeControlMode(TalonControlMode.PercentVbus);
			shooterMotor.set(SmartDashboard.getNumber("shooter voltage", 0));
			
//			if(shooterTimer.get() % 5 <=0.25){
//				backRight.set(true);
//			} else {
//				backRight.set(false);
//			}
		}	else {
			shooterMotor.changeControlMode(TalonControlMode.PercentVbus);
			shooterMotor.set(0);
			}
		}  else {
			shooterMotor.changeControlMode(TalonControlMode.PercentVbus);
			shooterMotor.set(0);
		}
	}
}

