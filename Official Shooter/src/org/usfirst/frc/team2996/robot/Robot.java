package org.usfirst.frc.team2996.robot;

import com.ctre.CANTalon;
import com.ctre.CANTalon.FeedbackDevice;
import com.ctre.CANTalon.TalonControlMode;
import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.AnalogGyro;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Joystick.AxisType;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {
	AHRS gyro;
	CANTalon shooter, auger, deflectorMotor, climber;
	// Shooter myShooter;
	RobotDrive myRobot = new RobotDrive(0, 1);
	Joystick stick;
	Shooter ethansshooter;
	//Climber climb;
	//DigitalInput upperLimit;
	//DigitalInput lowerLimit;
	//Toggle toggleUpButton;
	//Toggle toggleDownButton;
	Timer timer = new Timer();
	double P, I, D;

static final boolean SHOOTERREVERSESENSOR = true;
	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {
		deflectorMotor = new CANTalon(4);
		shooter = new CANTalon(2);
		auger = new CANTalon(3);
		//climber = new CANTalon(4);
		stick = new Joystick(0);
		//upperLimit = new DigitalInput(0);
		//lowerLimit = new DigitalInput(1);
		//toggleUpButton = new Toggle(stick, 4);
		//toggleDownButton = new Toggle(stick, 3);

	//	climb = new Climber(this);
		ethansshooter = new Shooter(this);
		shooter.setFeedbackDevice(FeedbackDevice.QuadEncoder);
		shooter.configEncoderCodesPerRev(20);
		SmartDashboard.putNumber("P", 1);
		SmartDashboard.putNumber("I", 1);
		SmartDashboard.putNumber("D", 1);
		SmartDashboard.putNumber("shooter speed", 0);
		SmartDashboard.putNumber("auger voltage", 0);
		SmartDashboard.putNumber("climber full speed", 0);
		SmartDashboard.putNumber("climber steady", 0);
		SmartDashboard.putNumber("shooter test rpm", 0);
		SmartDashboard.putNumber("shooter test velocity", 0);
		gyro = new AHRS(SPI.Port.kMXP);

	}

	

	/**
	 * This function is run once each time the robot enters autonomous mode
	 */
	@Override
	public void autonomousInit() {
		timer.reset();
		timer.start();
	}

	/**
	 * This function is called periodically during autonomous
	 */
	@Override
	public void autonomousPeriodic() {

	}

	/**
	 * This function is called once each time the robot enters tele-operateds
	 * mode
	 */
	@Override
	public void teleopInit() {
	//	shooter.changeControlMode(TalonControlMode.PercentVbus);
		//auger.changeControlMode(TalonControlMode.PercentVbus);
		/*shooter.changeControlMode(TalonControlMode.Speed);

		shooter.reset();
		//Shooter = new Shooter(shooter, SmartDashboard.getNumber("P",1.0),
		SmartDashboard.getNumber("I",1.0);
		SmartDashboard.getNumber("D",1.0);
*/
	}

	/**
	 * This function is called periodically during operator control
	 */
	@Override
	public void teleopPeriodic() {
		SmartDashboard.putNumber("shooter test rpm", -shooter.getSpeed());
		SmartDashboard.putNumber("shooter test velocity", -shooter.getEncVelocity());
		SmartDashboard.putNumber("shooter test output voltage", -shooter.getOutputVoltage());
		SmartDashboard.putNumber("shooter test bus voltage", -shooter.getBusVoltage());
		//ethansshooter.auger();
		//ethansshooter.shooter();
	//	climb.climb();

		myRobot.tankDrive(0, 0);
		//boolean button1 = stick.getRawButton(1);
		if(stick.getRawAxis(2) > 0.5){
			shooter.set(SmartDashboard.getNumber("shooter voltage", 0));
		}else{
			shooter.set(0);
		}
		if(stick.getRawAxis(3) > 0.5){
			auger.set(SmartDashboard.getNumber("auger voltage", 0));
		}else if(stick.getRawButton(6)){
			auger.set(-SmartDashboard.getNumber("auger voltage", 0));
		}else{
			auger.set(0);
		}
		
		ethansshooter.deflector();
		
		//climber.set(stick.getRawAxis(1));
		//shooter.set(1000);

		// shooter.set(0.5);
		/*
		 * int rpm = shooter.getEncVelocity(); double voltageMultiply = 1.01; if
		 * (button1 == true) { while (true) { if (rpm < 100) { shooter.set(0.6 *
		 * voltageMultiply); } else { shooter.set(0.6 / voltageMultiply); } }
		 * 
		 * } else { shooter.set(0.0); }
		 * 
		 * // myShooter.setSetpoint(0.1); // myShooter.setOutputRange(-100000,
		 * 100000); /* if(button1){
		 * shooter.set(SmartDashboard.getNumber("speed", 1)); }else{
		 * shooter.set(0); }
		 */
	/*	shooter.setP(SmartDashboard.getNumber("P", 1));
		shooter.setI(SmartDashboard.getNumber("I", 1));
		shooter.setD(SmartDashboard.getNumber("D", 1));

		/ (button1) {
			double target = stick.getRawAxis(1) * 1.0;
			shooter.changeControlMode(TalonControlMode.Speed);
			shooter.set(SmartDashboard.getNumber("speed", 0));
			} else {
			shooter.changeControlMode(TalonControlMode.PercentVbus);
			shooter.set(0);
			*/
		//}
		// shooter.set(stick.getRawAxis(0)* 1500);
		/*SmartDashboard.putNumber("encoder value", shooter.getEncVelocity());
		SmartDashboard.putNumber("Displacement", gyro.getDisplacementX());
		SmartDashboard.putNumber("feedback velocity", FeedbackDevice.QuadEncoder.value);
		SmartDashboard.putNumber("RPM", shooter.getSpeed());
		*/
		
		
	}

	public CANTalon getShooter() {
		return shooter;
	}

	public CANTalon getAuger() {
		return auger;
	}

	public CANTalon getDeflectorMotor() {
		return deflectorMotor;
	}

	/*public DigitalInput getUpperLimit() {
		return upperLimit;
	}

	public DigitalInput getLowerLimit() {
		return lowerLimit;
	}

	public Toggle getToggleUpButton() {
		return toggleUpButton;
	}

	public Toggle getToggleDownButton() {
		return toggleDownButton;
	}
*/
	public Joystick getStick() {
		return stick;
	}
	
	public CANTalon getClimber() {
		return climber;
	}
	
	@Override
	public void testPeriodic() {
		LiveWindow.setEnabled(true);
		LiveWindow.run();

	}
	
	
}
