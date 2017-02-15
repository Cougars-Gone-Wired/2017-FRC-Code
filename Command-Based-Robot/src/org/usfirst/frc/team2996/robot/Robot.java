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
	static int BACL_LEFT_MOTOR_ID;
	static int BACK_RIGHT_MOTOR_ID;
	static int SHOOTER_MOTOR_ID;
	static int AUGER_MOTOR_ID;
	static int DEFLECTOR_MOTOR_ID;
	static int CLIMBER_MOTOR_ID;
	static int INTAKE_MOTOR_ID;
	static double AUGER_SPEED;
	
	static int ENCODER_CODES_PER_REV;
	
	static int FRONT_LEFT_MOTOR_SOLENOID;
	static int FRONT_RIGHT_MOTOR_SOLENOID;
	static int BACK_LEFT_MOTOR_SOLENOID;
	static int BACK_RIGHT_MOTOR_SOLENOID;
	static int GEAR_SOLENOID_RIGHT;
	static int GEAR_SOLENOID_LEFT;
	
	static int STICK_DRIVE;
	
	static boolean SHOOTER_REVERSE_SENSOR;
	
	static int TICKS_PER_REVOLUTION;
	
	static int WHEEL_DIAMETER;
	
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
	static int CLIMB_UP_BUTTON;
	static int CLIMB_DOWN_BUTTON;
	
	static int ARCADE_DRIVE_ROTATE_INVERT;// INVERT JOYSTICK
	static int MECANUM_DRIVE_XAXIS_INVERT;
	static int MECANUM_DRIVE_YAXIS_INVERT;
	static int MECANUM_DRIVE_ROTATE_INVERT;
	
	static int DRIVE_TOGGLE_JOYSTICK_BUTTON;
	
	static int FRONT_LEFT_MOTOR_NEGATE_ENCODER; // negates encoder counts
	static int FRONT_RIGHT_MOTOR_NEGATE_ENCODER;
	static int BACK_LEFT_MOTOR_NEGATE_ENCODER;
	static int BACK_RIGHT_MOTOR_NEGATE_ENCODER;
	static int SLEEP_AUTO;	// how long it waits before going to next .....step in auto //minimum = 100
	static int GEAR_DROP_TIME;//how long it takes to drop a gear

//	public double centerX;
//	public double centerY;
	
	Joystick stickDrive;
	Joystick stickManipulator;
	
	AHRS gyro;
	
	int autoLoopCounter;
	
	CANTalon frontLeftMotor = new CANTalon(FRONT_LEFT_MOTOR_ID);
	CANTalon frontRightMotor = new CANTalon(FRONT_RIGHT_MOTOR_ID);
	CANTalon backLeftMotor = new CANTalon(BACL_LEFT_MOTOR_ID);
	CANTalon backRightMotor = new CANTalon(BACK_RIGHT_MOTOR_ID);
	CANTalon shooterMotor = new CANTalon(SHOOTER_MOTOR_ID);
	CANTalon augerMotor = new CANTalon(AUGER_MOTOR_ID);
	CANTalon deflectorMotor = new CANTalon(DEFLECTOR_MOTOR_ID);
	CANTalon climberMotor = new CANTalon(CLIMBER_MOTOR_ID);
	CANTalon intakeMotor = new CANTalon(INTAKE_MOTOR_ID);

	Compressor compressor = new Compressor();
	
	Solenoid frontLeftSolenoid = new Solenoid(FRONT_LEFT_MOTOR_SOLENOID);
	Solenoid frontRightSolenoid = new Solenoid(FRONT_RIGHT_MOTOR_SOLENOID);
	Solenoid backLeftSolenoid = new Solenoid(BACK_LEFT_MOTOR_SOLENOID);
	Solenoid backRightSolenoid = new Solenoid(BACK_RIGHT_MOTOR_SOLENOID);
	Solenoid gearSolenoidRight = new Solenoid(GEAR_SOLENOID_RIGHT);
	Solenoid gearSolenoidLeft = new Solenoid(GEAR_SOLENOID_LEFT);

	Drive drive;
	Toggle driveToggle;
	Toggle gearToggle;
	AutonomousPrograms auto;
	Climber climber;
	Shooter PIDShooter;
	RobotDrive robotDrive;
	Intake intake;
	boolean autoFinished; // checks if autonomous is finished
	
//	Thread visionThread;
	DigitalInput upperLimit;
	DigitalInput lowerLimit;
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
		SmartDashboard.putNumber("Velocity Measurement Window", 0);
		
		oi = new OI();
		
		stickDrive = new Joystick(0);
		stickManipulator = new Joystick(1);
		
		robotDrive = new RobotDrive(backLeftMotor, frontLeftMotor, backRightMotor, frontRightMotor);
		driveToggle = new Toggle(stickDrive, DRIVE_TOGGLE_JOYSTICK_BUTTON);
		
		gearToggle = new Toggle(stickManipulator, 2); // this toggles the gear intake
		
		gyro = new AHRS(SPI.Port.kMXP);
		
		drive = new Drive(this);
		PIDShooter = new Shooter(this);
		auto = new AutonomousPrograms(this);
		intake = new Intake(this);
		
		SmartDashboard.putNumber("Autonomous Select", 0); // the number put in the dashboard corresponds to an autonomous program
		SmartDashboard.putString("Field Side", null); // red or blue
		SmartDashboard.putNumber("auto turn angle", 0);
		SmartDashboard.putNumber("auto first drive distance", 0);
		SmartDashboard.putNumber("auto second drive distance", 0);
		SmartDashboard.putNumber("auto drive speed", 0);
		
		shooterMotor.setFeedbackDevice(FeedbackDevice.QuadEncoder);
		shooterMotor.configEncoderCodesPerRev(20);
		shooterMotor.SetVelocityMeasurementPeriod(VelocityMeasurementPeriod.Period_10Ms); // new method that helps with PID
		shooterMotor.SetVelocityMeasurementWindow((int) SmartDashboard.getNumber("Velocity Measurement Period", 0)); // new method that helps with PID
		SmartDashboard.putNumber("F", 1);
		SmartDashboard.putNumber("P", 1);
		SmartDashboard.putNumber("I", 1); //PID Stuff 
		SmartDashboard.putNumber("D", 1);
		SmartDashboard.putNumber("shooter speed", 0);
		SmartDashboard.putNumber("auger voltage", 0);
		SmartDashboard.putNumber("climber full speed", 0);  //Dashboard variables that control motor speeds (mainly for testing)
		SmartDashboard.putNumber("climber steady", 0);
		SmartDashboard.putNumber("shooter test rpm", 0);
		SmartDashboard.putNumber("shooter test velocity", 0);
		
//		gripPipeline = new GripPipeline();
//		CameraServer camera = CameraServer.getInstance();
//		UsbCamera usbCam = camera.startAutomaticCapture("usb", 0);
//		usbCam.setResolution(1280, 720);
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
		if (autoFinished == false && SmartDashboard.getString("Field Side", null) == "blue") // if autonomous is not finished keep going
		{
			int autonomous = (int) SmartDashboard.getNumber("Autonomous Select", 0);
			switch (autonomous) {
			case 0:
				auto.stop();// do nothing
				break;
			case 1:
				auto.moveForwardTurnRightPlaceGearBlue();
				break;
			default:
				auto.stop();
				break;
			}
			autoFinished = true;
		} else { // if autonomous has finished
			auto.stop();
		}
		
		if (autoFinished == false && SmartDashboard.getString("Field Side", null) == "red"){
			int autonomous = (int) SmartDashboard.getNumber("Autonomous Select", 0);
			switch (autonomous) {
			case 0:
				auto.stop();// do nothing
				break;
			case 1:
				auto.moveForwardTurnRightPlaceGearRed();
				break;
			default:
				auto.stop();
				break;
			}
			autoFinished = true;
		} else { // if autonomous has finished
			auto.stop();
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
		//Runs the functions for teleop in the other classes
		boolean state = driveToggle.toggle();
		drive.drive(state);
		PIDShooter.shooter();
		PIDShooter.auger();
		PIDShooter.deflector();
		intake.intakeOuttake();
		intake.gearActivation();
		Scheduler.getInstance().run(); // driverstation stuff
		SmartDashboard.putNumber("gyro", gyro.getAngle());
		//Displays the encoder for each motor (for debugging)
		SmartDashboard.putNumber("frontLeftMotor", drive.getFrontLeftEncoder());
		SmartDashboard.putNumber("frontRightMotor", drive.getFrontRightEncoder());
		SmartDashboard.putNumber("backLeftMotor", drive.getBackLeftEncoder());
		SmartDashboard.putNumber("backRightMotor", drive.getBackRightEncoder());
	}
	
	public static void setConstants(boolean compBot){//practiceBot is false, compBot is true
		//IF ROBOT IS COMPBOT
		if(compBot){
			 FRONT_LEFT_MOTOR_ID = 1;
			 FRONT_RIGHT_MOTOR_ID = 2;
			 BACL_LEFT_MOTOR_ID = 0;
			 BACK_RIGHT_MOTOR_ID = 3;
			 SHOOTER_MOTOR_ID = 4;
			 AUGER_MOTOR_ID = 5;
			 DEFLECTOR_MOTOR_ID = 6;
			 CLIMBER_MOTOR_ID = 7;
			 INTAKE_MOTOR_ID = 8;
			 AUGER_SPEED = 0.5;
			
			 ENCODER_CODES_PER_REV = 20;
			
			 FRONT_LEFT_MOTOR_SOLENOID = 0;
			 FRONT_RIGHT_MOTOR_SOLENOID = 1;
			 BACK_LEFT_MOTOR_SOLENOID = 2;
			 BACK_RIGHT_MOTOR_SOLENOID = 3;
			 GEAR_SOLENOID_RIGHT = 4;
			 GEAR_SOLENOID_LEFT = 5;
			
			 STICK_DRIVE = 0;
			
			 SHOOTER_REVERSE_SENSOR = true;
			
			 TICKS_PER_REVOLUTION = 20;
			
			 WHEEL_DIAMETER = 5;
			
			 ARCADE_DRIVE_YAXIS = 1;

			 ARCADE_DRIVE_ROTATE = 4;
			 MECANUM_DRIVE_XAXIS = 0;
			 MECANUM_DRIVEY_AXIS = 1;
			 MECANUM_DRIVE_ROTATE = 4;
			 ARCADE_DRIVE_YAXIS_INVERT = -1;// IF -1 INVERT JOYSTICK, IF 1 Dont
			 INTAKE_AXIS = 3;
			 OUTAKE_AXIS = 2;
			 SHOOTER_BUTTON = 1;
			 AUGER_FORWARD_BUTTON = 3;
			 AUGER_BACKWARD_BUTTON = 4;
			 CLIMB_UP_BUTTON = 6;
			 CLIMB_DOWN_BUTTON = 5;
			
			 ARCADE_DRIVE_ROTATE_INVERT = -1;// INVERT JOYSTICK
			 MECANUM_DRIVE_XAXIS_INVERT = 1;
			 MECANUM_DRIVE_YAXIS_INVERT = -1;
			 MECANUM_DRIVE_ROTATE_INVERT = -1;
			
			 DRIVE_TOGGLE_JOYSTICK_BUTTON = 1;
			
			 FRONT_LEFT_MOTOR_NEGATE_ENCODER = -1; // negates encoder counts
			 FRONT_RIGHT_MOTOR_NEGATE_ENCODER = 1;
			 BACK_LEFT_MOTOR_NEGATE_ENCODER = 1;
			 BACK_RIGHT_MOTOR_NEGATE_ENCODER = 1;
			 SLEEP_AUTO = 100;	// how long it waits before going to next .....step in auto //minimum = 100
			GEAR_DROP_TIME = 3;//how long it takes to drop a gear
		}
		//IF ROBOT IS PRACTICE BOT
		else{
			 FRONT_LEFT_MOTOR_ID = 1;
			 FRONT_RIGHT_MOTOR_ID = 2;
			 BACL_LEFT_MOTOR_ID = 0;
			 BACK_RIGHT_MOTOR_ID = 3;
			 SHOOTER_MOTOR_ID = 4;
			 AUGER_MOTOR_ID = 5;
			 DEFLECTOR_MOTOR_ID = 6;
			 CLIMBER_MOTOR_ID = 7;
			 INTAKE_MOTOR_ID = 8;
			 AUGER_SPEED = 0.5;
			
			 ENCODER_CODES_PER_REV = 20;
			
			 FRONT_LEFT_MOTOR_SOLENOID = 0;
			 FRONT_RIGHT_MOTOR_SOLENOID = 1;
			 BACK_LEFT_MOTOR_SOLENOID = 2;
			 BACK_RIGHT_MOTOR_SOLENOID = 3;
			 GEAR_SOLENOID_RIGHT = 4;
			 GEAR_SOLENOID_LEFT = 5;
			
			 STICK_DRIVE = 0;
			
			SHOOTER_REVERSE_SENSOR = true;
			
			 TICKS_PER_REVOLUTION = 20;
			
			 WHEEL_DIAMETER = 5;
			
			 ARCADE_DRIVE_YAXIS = 1;

			 ARCADE_DRIVE_ROTATE = 4;
			 MECANUM_DRIVE_XAXIS = 0;
			 MECANUM_DRIVEY_AXIS = 1;
			 MECANUM_DRIVE_ROTATE = 4;
			 ARCADE_DRIVE_YAXIS_INVERT = -1;// IF -1 INVERT JOYSTICK, IF 1 Dont
			 INTAKE_AXIS = 3;
			 OUTAKE_AXIS = 2;
			 SHOOTER_BUTTON = 1;
			 AUGER_FORWARD_BUTTON = 3;
			 AUGER_BACKWARD_BUTTON = 4;
			 CLIMB_UP_BUTTON = 6;
			 CLIMB_DOWN_BUTTON = 5;
			
			 ARCADE_DRIVE_ROTATE_INVERT = -1;// INVERT JOYSTICK
			 MECANUM_DRIVE_XAXIS_INVERT = 1;
			 MECANUM_DRIVE_YAXIS_INVERT = -1;
			 MECANUM_DRIVE_ROTATE_INVERT = -1;
			
			 DRIVE_TOGGLE_JOYSTICK_BUTTON = 1;
			
			 FRONT_LEFT_MOTOR_NEGATE_ENCODER = -1; // negates encoder counts
			 FRONT_RIGHT_MOTOR_NEGATE_ENCODER = 1;
			 BACK_LEFT_MOTOR_NEGATE_ENCODER = 1;
			 BACK_RIGHT_MOTOR_NEGATE_ENCODER = 1;
			 SLEEP_AUTO = 100;	// how long it waits before going to next .....step in auto //minimum = 100
			 GEAR_DROP_TIME = 3;//how long it takes to drop a gear
		}
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
	public Solenoid getGearSolenoidRight() {
		return gearSolenoidRight;
	}
	public Solenoid getGearSolenoidLeft() {
		return gearSolenoidLeft;
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
	
	public Timer getTimer() {
		return timer;
	}
	
	public DigitalInput getUpperLimit() {
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
	
	public Toggle getGearToggle() {
		return gearToggle;
	}
	
	public Shooter getPIDShooter() {
		return PIDShooter;
	}
	public Intake getIntake() {
		return intake;
	}
	
	

}
