package org.usfirst.frc.team2996.robot;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.cscore.AxisCamera;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.SPI;

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

	public Command getAutonomousCommand() {
		return autonomousCommand;
	}

	static final int FRONTLEFTMOTORTALONNUMBER = 1;
	static final int FRONTRIGHTMOTORTALONNUMBER = 2;
	static final int BACKLEFTMOTORTALONNUMBER = 0;
	static final int BACKRIGHTMOTORTALONNUMBER = 3;
	static final int FRONTLEFTMOTORSOLENOID = 0;
	static final int FRONTRIGHTMOTORSOLENOID = 1;
	static final int BACKLEFTMOTORSOLENOID = 2;
	static final int BACKRIGHTMOTORSOLENOID = 3;
	static final int STICKDRIVE = 0;
	static final int TICKSPERREVOLUTION = 20;
	static final int WHEELDIAMETER = 5;
	static final int ARCADEDRIVEYAXIS = 1;
	static final int ARCADEDRIVEROTATE = 4;
	static final int MECANUMDRIVEXAXIS = 0;
	static final int MECANUMDRIVEYAXIS = 1;
	static final int MECANUMDRIVEROTATE = 4;
	static final int ARCADEDRIVEYAXISINVERT = -1;// IF -1 INVERT JOYSTICK, IF 1 DONT									
	static final int ARCADEDRIVEROTATEINVERT = -1;// INVERT JOYSTICK
	static final int MECANUMDRIVEXAXISINVERT = 1;
	static final int MECANUMDRIVEYAXISINVERT = -1;
	static final int MECANUMDRIVEROTATEINVERT = -1;
	static final int DRIVETOGGLEJOYSTICKBUTTON = 1;
	static final int FRONTLEFTMOTORNEGATENCODER = -1; //negates encoder counts
	static final int FRONTRIGHTMOTORNEGATENCODER = 1;
	static final int BACKLEFTMOTORNEGATENCODER = 1;
	static final int BACKRIGHTMOTORNEGATENCODER = 1;
	
	Joystick stickDrive;
	AHRS gyro;
	int autoLoopCounter;
	CANTalon frontLeftMotor = new CANTalon(FRONTLEFTMOTORTALONNUMBER);
	CANTalon frontRightMotor = new CANTalon(FRONTRIGHTMOTORTALONNUMBER);
	CANTalon backLeftMotor = new CANTalon(BACKLEFTMOTORTALONNUMBER);
	CANTalon backRightMotor = new CANTalon(BACKRIGHTMOTORTALONNUMBER);
	Compressor compressor = new Compressor();
	Solenoid solenoid1 = new Solenoid(FRONTLEFTMOTORSOLENOID);
	Solenoid solenoid2 = new Solenoid(FRONTRIGHTMOTORSOLENOID);
	Solenoid solenoid3 = new Solenoid(BACKLEFTMOTORSOLENOID);
	Solenoid solenoid4 = new Solenoid(BACKRIGHTMOTORSOLENOID);

	Drive drive;
	Toggle driveToggle;
	AutonomousMethods auto;
	boolean autoFinished; // checks if autonomous is finished

	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {
		// CAMERA CODE WORKS
		/*
		 * //AxisCamera axis = new AxisCamera("axis", "axis-camera");
		 * CameraServer camera = CameraServer.getInstance();
		 * //camera.addCamera(axis); UsbCamera usb = new UsbCamera("usb", 0);
		 * //UsbCamera usb = camera.startAutomaticCapture("usb", 0);
		 * usb.setResolution(1280, 720); usb.setFPS(1);
		 * camera.startAutomaticCapture(usb); //UsbCamera usb2 =
		 * camera.startAutomaticCapture("usb2", 0); AxisCamera axis =
		 * camera.addAxisCamera("10.29.96.11"); axis.setResolution(800, 600);
		 * //camera.startAutomaticCapture(axis);
		 * 
		 */
		oi = new OI();
		stickDrive = new Joystick(STICKDRIVE);
		chooser.addDefault("Default Auto", new ExampleCommand());
		// chooser.addObject("My Auto", new MyAutoCommand());
		SmartDashboard.putData("Auto mode", chooser);
		robotDrive = new RobotDrive(backLeftMotor, frontLeftMotor, backRightMotor, frontRightMotor);
		drive = new Drive(this);
		driveToggle = new Toggle(stickDrive, DRIVETOGGLEJOYSTICKBUTTON);

		gyro = new AHRS(SPI.Port.kMXP);
		gyro.reset();
		auto = new AutonomousMethods(this);

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
		drive.arcadeDrive();
		if (autonomousCommand != null)
			autonomousCommand.start();
	}

	/**
	 * This function is called periodically during autonomous
	 */
	@Override
	public void autonomousPeriodic() {
		Scheduler.getInstance().run();

		if (autoFinished == false) // if autonomous is not finished keep going
		{
			// BUNCH OF TESTING AUTO METHOD
			// SmartDashboard.putNumber("Gyro", gyro.getAngle());

			// auto.turn("right", 90);// our gyro reads values 4 degrees less
			// than the target
			 auto.moveStraight("forward", 5000);
			// auto.moveStraight("backward", 1000);
			// robotDrive.tankDrive(0, 0);
			//auto.strafe("left", 100);
			autoFinished = true;

		} else { // if autonomous has finished

			robotDrive.tankDrive(0.0, 0.0); // stop robot
		}

	}

	@Override
	public void teleopInit() {
		drive.encoderReset(); // reset all encoders
		gyro.reset();
		compressor.setClosedLoopControl(true);
		compressor.start();
		if (autonomousCommand != null) {
			autonomousCommand.cancel();
		}
		drive.arcadeDrive();
	}

	/**
	 * This function is called periodically during operator control
	 */
	@Override
	public void teleopPeriodic() {
		Scheduler.getInstance().run(); // driverstation stuff
		boolean state = driveToggle.toggle();
		drive.drive(state);
		// display stuff
		SmartDashboard.putNumber("frontLeftMotor", drive.frontLeftMotor.getEncPosition());
		SmartDashboard.putBoolean("inverted", drive.frontLeftMotor.getInverted());
		SmartDashboard.putNumber("frontRightMotor", drive.frontRightMotor.getEncPosition());
		SmartDashboard.putNumber("backLeftMotor", drive.backLeftMotor.getEncPosition());
		SmartDashboard.putNumber("backRightMotor", drive.backRightMotor.getEncPosition());
		// SmartDashboard.putNumber("Gyro", gyro.getAngle());

	}

	/**
	 * This function is called periodically during test mode
	 */
	@Override
	public void testPeriodic() {
		LiveWindow.run();
	}

	// getters and setters
	public SendableChooser<Command> getChooser() {
		return chooser;
	}

	public RobotDrive getRobotDrive() {
		return robotDrive;
	}

	public Joystick getStickDrive() {
		return stickDrive;
	}

	public AHRS getGyro() {
		return gyro;
	}

	public int getAutoLoopCounter() {
		return autoLoopCounter;
	}

	public CANTalon getFrontLeftMotor() {
		return frontLeftMotor;
	}

	public CANTalon getFrontRightMotor() {
		return frontRightMotor;
	}

	public CANTalon getBackLeftMotor() {
		return backLeftMotor;
	}

	public CANTalon getBackRightMotor() {
		return backRightMotor;
	}

	public Compressor getCompressor() {
		return compressor;
	}

	public Solenoid getSolenoid1() {
		return solenoid1;
	}

	public Solenoid getSolenoid2() {
		return solenoid2;
	}

	public Solenoid getSolenoid3() {
		return solenoid3;
	}

	public Solenoid getSolenoid4() {
		return solenoid4;
	}

	public Drive getDrive() {
		return drive;
	}

	public Toggle getDriveToggle() {
		return driveToggle;
	}

	public AutonomousMethods getAuto() {
		return auto;
	}

	public boolean isAutoFinished() {
		return autoFinished;
	}


}
