package org.usfirst.frc.team2996.robot;

import com.ctre.CANTalon;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Solenoid;

/**
 * 
 * Stores methods for intaking and outaking
 * the ball and gear intakes, as well as activating 
 * the gear intake solenoid
 * @author shahaek0622
 *
 */
public class Intake {
	private CANTalon intakeMotor;
	private CANTalon deflectorMotor;
	private Solenoid gearSolenoid;
	private Joystick stick;
	private int intakeAxis;
	private int outtakeAxis;
	private Toggle gearActivateButton;
	
	/**
	 * 
	 * Constructor that gets parameters from the robot class
	 * @param robot
	 * 
	 */
	public Intake(Robot robot) {
		this.deflectorMotor = robot.getDeflectorMotor();
		this.intakeMotor = robot.getIntakeMotor();
		this.gearSolenoid = robot.getGearSolenoid();
		this.stick = robot.getStickManipulator();
		this.intakeAxis = Robot.INTAKE_AXIS;
		this.outtakeAxis = Robot.OUTAKE_AXIS;
		this.gearActivateButton = robot.getGearToggle();
	}

	/**
	 * Changes the direction of the ball and gear intakes depending on an axis diraction
	 */
	public void intakeOuttake() {
		if (Threshold.threshold(stick.getRawAxis(intakeAxis)) > 0.2) {
			intakeMotor.set(1);
			deflectorMotor.set(1);
		} else if (Threshold.threshold(stick.getRawAxis(outtakeAxis)) > 0.2) {
			intakeMotor.set(-1);
		} else {
			intakeMotor.set(0);
		}
	}
	
	public void intakeOuttake(boolean outtake, boolean stop) {
		if(stop == true){
			intakeMotor.set(0);
		}else if(outtake == true){
			intakeMotor.set(Robot.AUTO_INTAKE_MOTOR_REVERSE*1);
		}else{
			intakeMotor.set(Robot.AUTO_INTAKE_MOTOR_REVERSE*-1);
		}
		
	}

	/**
	 * activates/deactivates the gear solenoid depending on button presses
	 * @param gearToggle
	 */
	
	public void gearActivation(){
		if(gearActivateButton.toggle() == true){
			gearSolenoid.set(true);
		}else{
			gearSolenoid.set(false);
		}
	}
	
	public void gearActivation(boolean down){
		if(down == true){
			gearSolenoid.set(true);
		}else{
			gearSolenoid.set(false);
		}
	}
}