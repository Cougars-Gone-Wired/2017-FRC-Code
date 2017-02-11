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
	private Solenoid gearSolenoidRight;
	private Solenoid gearSolenoidLeft;
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
		this.intakeMotor = robot.getIntakeMotor();
		this.gearSolenoidRight = robot.getGearSolenoidRight();
		this.gearSolenoidLeft = robot.getGearSolenoidLeft();
		this.stick = robot.getStickManipulator();
		this.intakeAxis = 3;
		this.outtakeAxis = 2;
		this.gearActivateButton = robot.getGearToggle();
	}

	/**
	 * Changes the direction of the ball and gear intakes depending on an axis diraction
	 */
	public void intakeOuttake() {
		if (Threshold.threshold(stick.getRawAxis(intakeAxis)) > 0.2) {
			intakeMotor.set(1);
		} else if (Threshold.threshold(stick.getRawAxis(outtakeAxis)) > 0.2) {
			intakeMotor.set(-1);
		} else {
			intakeMotor.set(0);
		}
	}

	/**
	 * activates/deactivates the gear solenoid depending on button presses
	 * @param gearToggle
	 */
	
	public void gearActivation(){
		if(gearActivateButton.toggle() == true){
			gearSolenoidRight.set(true);
			gearSolenoidLeft.set(true);
		}else{
			gearSolenoidRight.set(false);
			gearSolenoidLeft.set(false);
		}
	}
}