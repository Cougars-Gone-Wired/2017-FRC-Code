package org.usfirst.frc.team2996.robot;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.vision.VisionRunner;
import edu.wpi.first.wpilibj.vision.VisionThread;
import edu.wpi.cscore.AxisCamera;
import edu.wpi.cscore.CvSink;
import edu.wpi.cscore.CvSource;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.SPI;

import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
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
public class RobotNotWorking extends IterativeRobot {

	public static final ExampleSubsystem exampleSubsystem = new ExampleSubsystem();
	public static OI oi;
	Command autonomousCommand;

	RobotDrive robotDrive;
	Thread visionThread;

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
	static final int FRONTLEFTMOTORNEGATENCODER = -1; // negates encoder counts
	static final int FRONTRIGHTMOTORNEGATENCODER = 1;
	static final int BACKLEFTMOTORNEGATENCODER = 1;
	static final int BACKRIGHTMOTORNEGATENCODER = 1;
	static final int SLEEPAUTO = 100;	// how long it waits before going to next
										// step in auto //minimum = 100
//	public volatile double centerX;
//	public volatile double centerY;
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
	AutonomousPrograms auto;
	boolean autoFinished; // checks if autonomous is finished
	Mat visionMat;
	GripPipeline gripPipeline;
	private final Object imageLock = new Object();

	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {
		visionMat = new Mat();
		gripPipeline = new GripPipeline();
		CameraServer camera = CameraServer.getInstance();
		UsbCamera usbCam = camera.startAutomaticCapture("usb", 0);
		usbCam.setResolution(1280, 720);
//		GripPipelineListener listener = new GripPipelineListener();
//		VisionRunner<GripPipeline> visionRunner = new VisionRunner<>(usbCam, gripPipeline, listener);
//		VisionThread visionThread = new VisionThread(visionRunner);
//		visionRunner.runOnce();
//		// CameraServer camera = CameraServer.getInstance();
		
		 // camera.addAxisCamera("10.29.96.11");
		 
		// UsbCamera usb = camera.startAutomaticCapture("usb", 0);
		// usb.setResolution(1280, 720);
		
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
//                  cvSink.grabFrame(source);
//                  Imgproc.cvtColor(source, output, Imgproc.COLOR_BGR2GRAY);
//                  outputStream.putFrame(output);
//              }
//          }).start();
//		
		visionThread = new VisionThread(usbCam, new GripPipeline(), pipeline -> {
			pipeline.process(visionMat);
			if (!pipeline.filterContoursOutput().isEmpty()) {
	            Rect rectangle = Imgproc.boundingRect(pipeline.filterContoursOutput().get(0));
	            synchronized (imageLock) {
	                //centerX = rectangle.x + (rectangle.width / 2);
	            }
			}
		});
		
		visionThread.setDaemon(true);
		visionThread.start();
		/*oi = new OI();
		stickDrive = new Joystick(STICKDRIVE);
		//chooser = new SendableChooser<>();
		// chooser.addObject("My Auto", new MyAutoCommand());
		//SmartDashboard.putData("Auto mode", chooser);
		robotDrive = new RobotDrive(backLeftMotor, frontLeftMotor, backRightMotor, frontRightMotor);
		//drive = new Drive(this);
		driveToggle = new Toggle(stickDrive, DRIVETOGGLEJOYSTICKBUTTON);

		gyro = new AHRS(SPI.Port.kMXP);
		gyro.reset();
		//auto = new AutonomousPrograms(this);
		SmartDashboard.putNumber("Autonomous Select", 0);
		SmartDashboard.putNumber("auto turn angle", 0);
		SmartDashboard.putNumber("auto first drive distance", 0);
		SmartDashboard.putNumber("auto second drive distance", 0);
		SmartDashboard.putNumber("auto drive speed", 0);
*/
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
//		autoFinished = false;
//		compressor.setClosedLoopControl(true);
//		compressor.start();
//		gyro.reset();
//		drive.arcadeDrive();

		// drive.encoderReset();
	}

	/**
	 * This function is called periodically during autonomous
	 */
	@Override
	public void autonomousPeriodic() {

		Scheduler.getInstance().run();

		if (autoFinished == false) // if autonomous is not finished keep going
		{
			int autonomous = (int) SmartDashboard.getNumber("Autonomous Select", 0);
			switch (autonomous) {
			case 0:
				auto.stop();// do nothing
				break;
			case 1:
				auto.moveForwardTurnLeftPlaceGear();
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
//		drive.encoderReset(); // reset all encoders
//		gyro.reset();
//		compressor.setClosedLoopControl(true);
//		compressor.start();
		if (autonomousCommand != null) {
			autonomousCommand.cancel();
	}
		//drive.arcadeDrive();
	}

	/**
	 * This function is called periodically during operator control
	 */
	@Override
	public void teleopPeriodic() {
//		SmartDashboard.putNumber("CenterX", centerX);
//		SmartDashboard.putNumber("CenterY", centerY);
//		Scheduler.getInstance().run(); // driverstation stuff
//		//SmartDashboard.putNumber("GRIP", gripPipeline.findContoursOutput().indexOf(0));
//		boolean state = driveToggle.toggle();
//		drive.drive(state);
//		// display stuff
//		SmartDashboard.putNumber("gyro", gyro.getAngle());
//		SmartDashboard.putNumber("frontLeftMotor", drive.getFrontLeftEncoder());
//		// SmartDashboard.putBoolean("inverted",
//		// drive.frontLeftMotor.getFrontLeftEncoder());
//		SmartDashboard.putNumber("frontRightMotor", drive.getFrontRightEncoder());
//		SmartDashboard.putNumber("backLeftMotor", drive.getBackLeftEncoder());
//		SmartDashboard.putNumber("backRightMotor", drive.getBackRightEncoder());
//		// SmartDashboard.putNumber("Gyro", gyro.getAngle());

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
