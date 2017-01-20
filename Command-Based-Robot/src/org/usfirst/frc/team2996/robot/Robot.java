package org.usfirst.frc.team2996.robot;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

import edu.wpi.first.wpilibj.AnalogGyro;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Joystick.AxisType;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.RobotDrive.MotorType;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;

import org.usfirst.frc.team2996.robot.commands.ExampleCommand;
import org.usfirst.frc.team2996.robot.subsystems.ExampleSubsystem;

import com.ctre.CANTalon;
import com.kauailabs.navx.frc.AHRS;


/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {

	public static final ExampleSubsystem exampleSubsystem = new ExampleSubsystem();
	public static OI oi;
	Command autonomousCommand;
	SendableChooser<Command> chooser = new SendableChooser<>();
	RobotDrive robotDrive;
	Joystick stick;
	AHRS gyro;
	int autoLoopCounter;
    CANTalon frontLeftMotor = new CANTalon(1);
	CANTalon frontRightMotor = new CANTalon(2);
	CANTalon backLeftMotor = new CANTalon(0);
	CANTalon backRightMotor = new CANTalon(3);
	Compressor compressor = new Compressor();
	   DoubleSolenoid solenoid1 = new DoubleSolenoid(0,1);
	   DoubleSolenoid solenoid2 = new DoubleSolenoid(2,3);
	   DoubleSolenoid solenoid3 = new DoubleSolenoid(4,5);
	   DoubleSolenoid solenoid4 = new DoubleSolenoid(6,7);
	   Drive drive;
	   Toggle driveToggle;
	   AutonomousMethods auto;
		boolean autoFinished;

	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {
		oi = new OI();
		stick = new Joystick(0);
		chooser.addDefault("Default Auto", new ExampleCommand());
		// chooser.addObject("My Auto", new MyAutoCommand());
		SmartDashboard.putData("Auto mode", chooser);
		robotDrive = new RobotDrive(backLeftMotor, frontLeftMotor, backRightMotor, frontRightMotor);
		drive = new Drive(stick, robotDrive, solenoid1, solenoid2, solenoid3, solenoid4, frontLeftMotor, frontRightMotor, backLeftMotor, backRightMotor);
    	driveToggle = new Toggle(stick, 1);
    
    	gyro = new AHRS(SPI.Port.kMXP);
    	gyro.reset();
    	auto = new AutonomousMethods(gyro, drive);
    	autoFinished = false;
	}

	/**
	 * This function is called once each time the robot enters Disabled mode.
	 * You can use it to reset any subsystem information you want to clear when
	 * the robot is disabled.
	 */
	@Override
	public void disabledInit() {

	}

	@Override
	public void disabledPeriodic() {
		Scheduler.getInstance().run();
	}

	/**
	 * This autonomous (along with the chooser code above) shows how to select
	 * between different autonomous modes using the dashboard. The sendable
	 * chooser code works with the Java SmartDashboard. If you prefer the
	 * LabVIEW Dashboard, remove all of the chooser code and uncomment the
	 * getString code to get the auto name from the text box below the Gyro
	 *
	 * You can add additional auto modes by adding additional commands to the
	 * chooser code above (like the commented example) or additional comparisons
	 * to the switch structure below with additional strings & commands.
	 */
	@Override
	public void autonomousInit() {
		autoFinished = false;
		gyro.reset();
		autonomousCommand = chooser.getSelected();
	
		/*
		 * String autoSelected = SmartDashboard.getString("Auto Selector",
		 * "Default"); switch(autoSelected) { case "My Auto": autonomousCommand
		 * = new MyAutoCommand(); break; case "Default Auto": default:
		 * autonomousCommand = new ExampleCommand(); break; }
		 */
	
		// schedule the autonomous command (example)
		if (autonomousCommand != null)
			autonomousCommand.start();
	}

	/**
	 * This function is called periodically during autonomous
	 */
	@Override
	public void autonomousPeriodic() {
		Scheduler.getInstance().run();
	
		if(autoFinished == false) //Check if we've completed 750 loops (approximately 15 seconds)
		{
			SmartDashboard.putNumber("Gyro", gyro.getAngle());
			
			auto.turn("right", 86);//our gyro reads values 4 degrees less than the target
		robotDrive.tankDrive(0, 0);
			
			autoFinished = true;

			} else {
				
			robotDrive.tankDrive(0.0, 0.0); 	// stop robot
		}
	
		
	}

	@Override
	public void teleopInit(){
		// This makes sure that the autonomous stops running when
		// teleop starts running. If you want the autonomous to
		// continue until interrupted by another command, remove
		// this line or comment it out.
		gyro.reset();
		compressor.setClosedLoopControl(true);
    	compressor.start();
		if (autonomousCommand != null){
			autonomousCommand.cancel();
		}
		
	}

	/**
	 * This function is called periodically during operator control
	 */
	@Override
	public void teleopPeriodic() {
    	
		Scheduler.getInstance().run();

    	    boolean state = driveToggle.toggle();
    		drive.drive(state);
    		SmartDashboard.putNumber("Gyro", gyro.getAngle());
    	
    	
	}

	/**
	 * This function is called periodically during test mode
	 */
	@Override
	public void testPeriodic() {
		LiveWindow.run();
	}
}
