package org.usfirst.frc.team2996.robot;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.vision.VisionThread;
import edu.wpi.cscore.AxisCamera;
import edu.wpi.cscore.CvSink;
import edu.wpi.cscore.CvSource;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.SPI;

import javax.naming.LimitExceededException;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.usfirst.frc.team2996.robot.commands.ExampleCommand;
import org.usfirst.frc.team2996.robot.subsystems.ExampleSubsystem;

import com.ctre.CANTalon;
import com.ctre.CANTalon.FeedbackDevice;
import com.ctre.CANTalon.VelocityMeasurementPeriod;
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
	Timer timer = new Timer();

	public Command getAutonomousCommand() {
		return autonomousCommand;
	}
	
	public static final boolean isCompBot = false;
	
	static int FRONT_LEFT_MOTOR_ID;
	static int FRONT_RIGHT_MOTOR_ID;
	static int BACK_LEFT_MOTOR_ID;
	static int BACK_RIGHT_MOTOR_ID;
	static int SHOOTER_MOTOR_ID;
	static int AUGER_MOTOR_ID;
	static int DEFLECTOR_MOTOR_ID;
	static int CLIMBER_MOTOR_ID;
	static int INTAKE_MOTOR_ID;
	static int LIMIT_PORT;
	
	static int BOILER_DEFLECTOR_ANGLE;
	static int SHIP_DEFLECTOR_ANGLE;
	
	static double AUGER_SPEED;
	static int ENCODER_CODES_PER_REV;
	
	static int FRONT_LEFT_MOTOR_SOLENOID;
	static int FRONT_RIGHT_MOTOR_SOLENOID;
	static int BACK_LEFT_MOTOR_SOLENOID;
	static int BACK_RIGHT_MOTOR_SOLENOID;
	static int GEAR_SOLENOID;
	static int GEAR_SOLENOID_LEFT;
	
	static int TICKS_PER_REVOLUTION;
	
	static int WHEEL_DIAMETER;
	
	static int STICK_DRIVE;
	static int STICK_MANIPULATOR;
	static int ARCADE_DRIVE_YAXIS;
	static int ARCADE_DRIVE_ROTATE;
	static int MECANUM_DRIVE_XAXIS;
	static int MECANUM_DRIVEY_AXIS;
	static int MECANUM_DRIVE_ROTATE;
	static int ARCADE_DRIVE_YAXIS_INVERT;// IF -1 INVERT JOYSTICK, IF 1 Dont
	static int INTAKE_AXIS;
	static int OUTAKE_AXIS;
	static int SHOOTER_BUTTON;
	static int AUGER_FORWARD_BUTTON;
	static int AUGER_BACKWARD_BUTTON;
	static int DRIVE_TOGGLE_JOYSTICK_BUTTON;
	static int GEAR_TOGGLE_BUTTON;
	static int HALF_ACTIVATION_TOGGLE;
	static int LEFT_PID_TOGGLE;
	static int RIGHT_PID_TOGGLE;
	static int SHOOTER_UP_TOGGLE;
	static int SHOOTER_DOWN_TOGGLE;
	static int CLIMB_AXIS;
	static int THUMPER_TRICKS_ENABLE;
	static int THUMP_BUTTON;
	
	static int ARCADE_DRIVE_ROTATE_INVERT;// INVERT JOYSTICK
	static int MECANUM_DRIVE_XAXIS_INVERT;
	static int MECANUM_DRIVE_YAXIS_INVERT;
	static int MECANUM_DRIVE_ROTATE_INVERT;
	static int AUGER_INVERT;
	static int FRONT_LEFT_MOTOR_NEGATE_ENCODER; // negates encoder counts
	static int FRONT_RIGHT_MOTOR_NEGATE_ENCODER;
	static int BACK_LEFT_MOTOR_NEGATE_ENCODER;
	static int BACK_RIGHT_MOTOR_NEGATE_ENCODER;
	static boolean SHOOTER_REVERSE_SENSOR;
	static int DEFLECTOR_REVERSE_ENCODER;
	
	static int SLEEP_AUTO;	// how long it waits before going to next .....step in auto //minimum = 100

//	public double centerX;
//	public double centerY;
	
	Joystick stickDrive;
	Joystick stickManipulator;
	
	AHRS gyro;
	
	int autoLoopCounter;
	
	CANTalon frontLeftMotor;
	CANTalon frontRightMotor;
	CANTalon backLeftMotor;
	CANTalon backRightMotor;
	CANTalon shooterMotor;
	CANTalon augerMotor;
	CANTalon deflectorMotor;
	CANTalon climberMotor;
	CANTalon intakeMotor;

	Compressor compressor;
	
	Solenoid frontLeftSolenoid;
	Solenoid frontRightSolenoid;
	Solenoid backLeftSolenoid;
	Solenoid backRightSolenoid;
	Solenoid gearSolenoid;

	Drive drive;
	Toggle driveToggle;
	Toggle gearToggle;
	TwoButtonToggle PIDToggle;
	Toggle halfActivation;
	Toggle thumperTricksToggle;
	AutonomousPrograms auto;
	Climber climber;
	Shooter PIDShooter;
	RobotDrive robotDrive;
	Intake intake;
	ThumperTricks thumperTricks;
	boolean autoFinished; // checks if autonomous is finished
	boolean shooterState;
//	Thread visionThread;
	Toggle toggleUpButton;
	Toggle toggleDownButton;
	
//	Mat visionMat;
//	GripPipeline gripPipeline;
//	private final Object imageLock = new Object();

	
	
	
	
	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {
		setConstants(Robot.isCompBot);
		
		frontLeftMotor = new CANTalon(FRONT_LEFT_MOTOR_ID);
		frontRightMotor = new CANTalon(FRONT_RIGHT_MOTOR_ID);
		backLeftMotor = new CANTalon(BACK_LEFT_MOTOR_ID);
		backRightMotor = new CANTalon(BACK_RIGHT_MOTOR_ID);
		shooterMotor = new CANTalon(SHOOTER_MOTOR_ID);
		augerMotor = new CANTalon(AUGER_MOTOR_ID);
		deflectorMotor = new CANTalon(DEFLECTOR_MOTOR_ID);
		climberMotor = new CANTalon(CLIMBER_MOTOR_ID);
		intakeMotor = new CANTalon(INTAKE_MOTOR_ID);

		compressor = new Compressor();
		
		frontLeftSolenoid = new Solenoid(FRONT_LEFT_MOTOR_SOLENOID);
		frontRightSolenoid = new Solenoid(FRONT_RIGHT_MOTOR_SOLENOID);
		backLeftSolenoid = new Solenoid(BACK_LEFT_MOTOR_SOLENOID);
		backRightSolenoid = new Solenoid(BACK_RIGHT_MOTOR_SOLENOID);
		gearSolenoid = new Solenoid(GEAR_SOLENOID);
		
		oi = new OI();
		
		stickDrive = new Joystick(STICK_DRIVE);
		stickManipulator = new Joystick(STICK_MANIPULATOR);
		
		robotDrive = new RobotDrive(backLeftMotor, frontLeftMotor, backRightMotor, frontRightMotor);
		
		driveToggle = new Toggle(stickDrive, DRIVE_TOGGLE_JOYSTICK_BUTTON);
		gearToggle = new Toggle(stickManipulator, GEAR_TOGGLE_BUTTON); // this toggles the gear intake
		halfActivation = new Toggle(stickDrive, HALF_ACTIVATION_TOGGLE);
		PIDToggle = new TwoButtonToggle(stickManipulator, LEFT_PID_TOGGLE, RIGHT_PID_TOGGLE);
		thumperTricksToggle = new Toggle(stickDrive, THUMPER_TRICKS_ENABLE);
		
		gyro = new AHRS(SPI.Port.kMXP);
		
		drive = new Drive(this);
		PIDShooter = new Shooter(this);
		auto = new AutonomousPrograms(this);
		intake = new Intake(this);
		climber = new Climber(this);
		thumperTricks = new ThumperTricks(this);
		
		toggleUpButton = new Toggle(stickManipulator, SHOOTER_UP_TOGGLE);
		toggleDownButton = new Toggle(stickManipulator, SHOOTER_DOWN_TOGGLE);
		
		displaySettings();
		
		CameraServer camera = CameraServer.getInstance();
		UsbCamera usbCam = camera.startAutomaticCapture("usb", 0);
		usbCam.setResolution(1280, 720);
		AxisCamera axisCamera = camera.addAxisCamera("10.29.96.11");
		
//		gripPipeline = new GripPipeline();
//		  new Thread(() -> {
//              UsbCamera camera = CameraServer.getInstance().startAutomaticCapture("usb", 0);
//              camera.setResolution(640, 480);
//              
//              CvSink cvSink = CameraServer.getInstance().getVideo();
//              CvSource outputStream = CameraServer.getInstance().putVideo("blur", 640, 480);
//              
//              Mat source = new Mat();
//              Mat output = new Mat();
//              
//              while(!Thread.interrupted()) {
//                 cvSink.grabFrame(source);
//                 Core.extractChannel(source, output, 1);
//                 Imgproc.cvtColor(source, output, Imgproc.COLOR_BGR2HSV);
//                 outputStream.putFrame(output);
//              }
//          }).start();

//		visionThread = new VisionThread(usbCam, new GripPipeline(), pipeline -> {
//		pipeline.process(visionMat);
//		if (!pipeline.filterContoursOutput().isEmpty()) {
//            Rect rectangle = Imgproc.boundingRect(pipeline.filterContoursOutput().get(0));
//            synchronized (imageLock) {
//                //centerX = rectangle.x + (rectangle.width / 2);
//            }
//		}
//	});
		
		
//		visionThread = new VisionThread(usbCam, new GripPipeline(), pipeline -> {
//		pipeline.process(visionMat);
//		if (!pipeline.filterContoursOutput().isEmpty()) {
//            Rect rectangle = Imgproc.boundingRect(pipeline.filterContoursOutput().get(0));
//            synchronized (imageLock) {
//                //centerX = rectangle.x + (rectangle.width / 2);
//            }
//		}
//	});
		
//	visionThread.setDaemon(true);
//	visionThread.start();
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
		// Resets stuff
		autoFinished = false;
		compressor.setClosedLoopControl(true);
		compressor.start();
		gyro.reset();
		drive.arcadeDrive();
 		drive.encoderReset();
	}

	/**
	 * This function is called periodically during autonomous
	 */
	@Override
	public void autonomousPeriodic() {

		Scheduler.getInstance().run();
		
		//Runs the autonomous programs depending on the field side string (will make boolean)
		if (autoFinished == false && SmartDashboard.getNumber("Field Side Number", 0) == 0) // if autonomous is not finished keep going
		{
			int autonomous = (int) SmartDashboard.getNumber("Autonomous Select", 0);
			switch (autonomous) {
			case 0:
				auto.stop();// do nothing
				break;
			case 1:
				auto.placeGearLeftPeg(); // drive forward, turn, place gear
				break;
			case 2:
				auto.moveStraight("forward", (int) SmartDashboard.getNumber("auto first drive distance", 0), 0.6);
				break;
			default:
				auto.stop();
				break;
			}
			autoFinished = true;
		} else { // if autonomous has finished
			auto.stop();
			autoFinished = true;
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
		PIDToggle.set(); // THIS BASICALLY SETS THE SHOOTER TO VOLTAGE FOR PID USE .RESET()
	}

	/**
	 * This function is called periodically during operator control
	 */
	@Override
	public void teleopPeriodic() { //Runs the functions for teleop in the other classes
		boolean state = driveToggle.toggle();
		drive.drive(state);
//		if(thumperTricksToggle.toggle() == true){
//			thumperTricks.rocking();
//		}else{
//			boolean state = driveToggle.toggle();
//			drive.drive(state);
//		}
		PIDShooter.shoot(PIDToggle.toggle());
		PIDShooter.auger();
		PIDShooter.deflector();
		intake.intakeOuttake();
		intake.gearActivation();
		climber.climb();
		Scheduler.getInstance().run(); // driverstation stuff
		SmartDashboard.putNumber("gyro", gyro.getAngle());
		//Displays the encoder for each motor (for debugging)
		SmartDashboard.putNumber("shooter", shooterMotor.getSpeed());
		SmartDashboard.putNumber("frontLeftMotor", drive.getFrontLeftEncoder());
		SmartDashboard.putNumber("frontRightMotor", drive.getFrontRightEncoder());
		SmartDashboard.putNumber("backLeftMotor", drive.getBackLeftEncoder());
		SmartDashboard.putNumber("backRightMotor", drive.getBackRightEncoder());
		SmartDashboard.putBoolean("Thumper Tricks Enabled", thumperTricksToggle.toggle());
	}
	/**
	 * This function is called periodically during test mode
	 */
	@Override
	public void testPeriodic() {
		LiveWindow.run();
	}


	public void wait(int sleep) {
		try {
			Thread.sleep(sleep);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void displaySettings(){
		
		SmartDashboard.putNumber("Autonomous Select", 0); // the number put in the dashboard corresponds to an autonomous program
		SmartDashboard.putString("Field Side", ""); // red or blue
		SmartDashboard.putNumber("auto turn angle", 0);
		SmartDashboard.putNumber("auto first drive distance", 0);
		SmartDashboard.putNumber("auto second drive distance", 0);
		SmartDashboard.putNumber("Gear Drop Time", 0);
		SmartDashboard.putNumber("auto drive speed", 0);
		
//		shooterMotor.SetVelocityMeasurementPeriod(VelocityMeasurementPeriod.Period_10Ms); // new method that helps with PID
//		shooterMotor.SetVelocityMeasurementWindow((int) SmartDashboard.getNumber("Velocity Measurement Period", 0)); // new method that helps with PID
		SmartDashboard.putNumber("F", 1);
		SmartDashboard.putNumber("P", 1);
		SmartDashboard.putNumber("I", 1); //PID Stuff 
		SmartDashboard.putNumber("D", 1);
		SmartDashboard.putNumber("shooter speed", 0);
		SmartDashboard.putNumber("auger voltage", 0.5);
		SmartDashboard.putNumber("climber full speed", 1.0);  //Dashboard variables that control motor speeds (mainly for testing)
		SmartDashboard.putNumber("climber steady", 0.6);
		SmartDashboard.putNumber("shooter test rpm", 0);
		SmartDashboard.putNumber("shooter test velocity", 0);
		SmartDashboard.putNumber("shooter voltage", -0.6);
		SmartDashboard.putNumber("Deflector Angle", 0);
		SmartDashboard.putNumber("Field Side Number", 0);
		
	}

	//SO MANY GETTERS
	public RobotDrive getRobotDrive() {
		return robotDrive;
	}

	public Joystick getStickDrive() {
		return stickDrive;
	}
	
	public Joystick getStickManipulator() {
		return stickManipulator;
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

	public CANTalon getShooterMotor() {
		return shooterMotor;
	}

	public CANTalon getAugerMotor() {
		return augerMotor;
	}

	public CANTalon getDeflectorMotor() {
		return deflectorMotor;
	}

	public CANTalon getClimberMotor() {
		return climberMotor;
	}
	
	public CANTalon getIntakeMotor() {
		return intakeMotor;
	}

	public Compressor getCompressor() {
		return compressor;
	}

	public Solenoid getFrontLeftSolenoid() {
		return frontLeftSolenoid;
	}

	public Solenoid getFrontRightSolenoid() {
		return frontRightSolenoid;
	}
	
	public Solenoid getBackLeftSolenoid() {
		return backLeftSolenoid;
	}
	public Solenoid getBackRightSolenoid() {
		return backRightSolenoid;
	}
	public Solenoid getGearSolenoid() {
		return gearSolenoid;
	}

	public Drive getDrive() {
		return drive;
	}

	public Toggle getDriveToggle() {
		return driveToggle;
	}

	public AutonomousPrograms getAuto() {
		return auto;
	}

	public boolean isAutoFinished() {
		return autoFinished;
	}
	
	public Timer getTimer() {
		return timer;
	}

	public Toggle getToggleDownButton() {
		return toggleDownButton;
	}
	
	public Toggle getToggleUpButton() {
		return toggleUpButton;
	}
	
	public Toggle getGearToggle() {
		return gearToggle;
	}
	
	public TwoButtonToggle getPIDToggle() {
		return PIDToggle;
	}
	
	public Shooter getPIDShooter() {
		return PIDShooter;
	}
	
	public Intake getIntake() {
		return intake;
	}
	
	public static void setConstants(boolean compBot){//practiceBot is false, compBot is true
		//IF ROBOT IS COMPBOT
		if(compBot){
			 FRONT_LEFT_MOTOR_ID = 5;
			 FRONT_RIGHT_MOTOR_ID = 8;
			 BACK_LEFT_MOTOR_ID = 0;
			 BACK_RIGHT_MOTOR_ID = 3;
			 SHOOTER_MOTOR_ID = 7;
			 AUGER_MOTOR_ID = 2;
			 DEFLECTOR_MOTOR_ID = 6;
			 CLIMBER_MOTOR_ID = 1;
			 INTAKE_MOTOR_ID = 4;
			 
			 AUGER_SPEED = 0.5;
			 LIMIT_PORT = 0;
			 
			 BOILER_DEFLECTOR_ANGLE = 80;
			 SHIP_DEFLECTOR_ANGLE = 60;
			
			 ENCODER_CODES_PER_REV = 20;
			
			 FRONT_LEFT_MOTOR_SOLENOID = 4;
			 FRONT_RIGHT_MOTOR_SOLENOID = 0;
			 BACK_LEFT_MOTOR_SOLENOID = 5;
			 BACK_RIGHT_MOTOR_SOLENOID = 1;
			 GEAR_SOLENOID = 2;
			
			 TICKS_PER_REVOLUTION = 20;
			
			 WHEEL_DIAMETER = 5;
			
			 STICK_DRIVE = 0;
			 STICK_MANIPULATOR = 1;
			 ARCADE_DRIVE_YAXIS = 1;
			 ARCADE_DRIVE_ROTATE = 4;
			 MECANUM_DRIVE_XAXIS = 0;
			 MECANUM_DRIVEY_AXIS = 1;
			 MECANUM_DRIVE_ROTATE = 4;
			 ARCADE_DRIVE_YAXIS_INVERT = -1;// IF -1 INVERT JOYSTICK, IF 1 Dont
			 INTAKE_AXIS = 3;
			 OUTAKE_AXIS = 2;
			 SHOOTER_BUTTON = 1;
			 AUGER_FORWARD_BUTTON = 6;
			 AUGER_BACKWARD_BUTTON = 5;
			 AUGER_INVERT = -1;
			 DRIVE_TOGGLE_JOYSTICK_BUTTON = 1;
			 GEAR_TOGGLE_BUTTON = 2;
			 HALF_ACTIVATION_TOGGLE = 2;
			 LEFT_PID_TOGGLE = 7;
			 RIGHT_PID_TOGGLE = 8;
			 SHOOTER_UP_TOGGLE = 4;
			 SHOOTER_DOWN_TOGGLE = 3;
			 CLIMB_AXIS = 3;
			 THUMPER_TRICKS_ENABLE = 4;
			 THUMP_BUTTON = 3;
			
			 ARCADE_DRIVE_ROTATE_INVERT = -1;// INVERT JOYSTICK
			 MECANUM_DRIVE_XAXIS_INVERT = 1;
			 MECANUM_DRIVE_YAXIS_INVERT = -1;
			 MECANUM_DRIVE_ROTATE_INVERT = -1;
			 FRONT_LEFT_MOTOR_NEGATE_ENCODER = -1; // negates encoder counts
			 FRONT_RIGHT_MOTOR_NEGATE_ENCODER = 1;
			 BACK_LEFT_MOTOR_NEGATE_ENCODER = 1;
			 BACK_RIGHT_MOTOR_NEGATE_ENCODER = 1;
			 SHOOTER_REVERSE_SENSOR = true;
			 DEFLECTOR_REVERSE_ENCODER = -1;
			 
			 SLEEP_AUTO = 100;	// how long it waits before going to next .....step in auto //minimum = 100
		}
		//IF ROBOT IS PRACTICE BOT
		else{
			 FRONT_LEFT_MOTOR_ID = 0;
			 FRONT_RIGHT_MOTOR_ID = 1;
			 BACK_LEFT_MOTOR_ID = 2;
			 BACK_RIGHT_MOTOR_ID = 3;
			 SHOOTER_MOTOR_ID = 4;
			 AUGER_MOTOR_ID = 5;
			 DEFLECTOR_MOTOR_ID = 7;
			 CLIMBER_MOTOR_ID = 6;
			 INTAKE_MOTOR_ID = 8;
			 
			 AUGER_SPEED = 0.5;
			 LIMIT_PORT = 0;
			 
			 BOILER_DEFLECTOR_ANGLE = 80;
			 SHIP_DEFLECTOR_ANGLE = 60;
			
			 ENCODER_CODES_PER_REV = 20;
			
			 FRONT_LEFT_MOTOR_SOLENOID = 4;
			 FRONT_RIGHT_MOTOR_SOLENOID = 0;
			 BACK_LEFT_MOTOR_SOLENOID = 5;
			 BACK_RIGHT_MOTOR_SOLENOID = 1;
			 GEAR_SOLENOID = 2;
			
			 TICKS_PER_REVOLUTION = 20;
			
			 WHEEL_DIAMETER = 5;
			
			 STICK_DRIVE = 0;
			 STICK_MANIPULATOR = 1;
			 ARCADE_DRIVE_YAXIS = 1;
			 ARCADE_DRIVE_ROTATE = 4;
			 MECANUM_DRIVE_XAXIS = 0;
			 MECANUM_DRIVEY_AXIS = 1;
			 MECANUM_DRIVE_ROTATE = 4;
			 ARCADE_DRIVE_YAXIS_INVERT = -1;// IF -1 INVERT JOYSTICK, IF 1 Dont
			 INTAKE_AXIS = 3;
			 OUTAKE_AXIS = 2;
			 SHOOTER_BUTTON = 1;
			 AUGER_FORWARD_BUTTON = 6;
			 AUGER_BACKWARD_BUTTON = 5;
			 AUGER_INVERT = -1;
			 DRIVE_TOGGLE_JOYSTICK_BUTTON = 1;
			 GEAR_TOGGLE_BUTTON = 2;
			 HALF_ACTIVATION_TOGGLE = 2;
			 LEFT_PID_TOGGLE = 7;
			 RIGHT_PID_TOGGLE = 8;
			 SHOOTER_UP_TOGGLE = 4;
			 SHOOTER_DOWN_TOGGLE = 3;
			 CLIMB_AXIS = 3;
			 THUMPER_TRICKS_ENABLE = 4;
			 THUMP_BUTTON = 3;
			
			 ARCADE_DRIVE_ROTATE_INVERT = -1;// INVERT JOYSTICK
			 MECANUM_DRIVE_XAXIS_INVERT = 1;
			 MECANUM_DRIVE_YAXIS_INVERT = -1;
			 MECANUM_DRIVE_ROTATE_INVERT = -1;
			 FRONT_LEFT_MOTOR_NEGATE_ENCODER = -1; // negates encoder counts
			 FRONT_RIGHT_MOTOR_NEGATE_ENCODER = 1;
			 BACK_LEFT_MOTOR_NEGATE_ENCODER = 1;
			 BACK_RIGHT_MOTOR_NEGATE_ENCODER = 1;
			 SHOOTER_REVERSE_SENSOR = true;
			 DEFLECTOR_REVERSE_ENCODER = -1;
			 
			 SLEEP_AUTO = 100;	// how long it waits before going to next .....step in auto //minimum = 100
		}
	}
}
