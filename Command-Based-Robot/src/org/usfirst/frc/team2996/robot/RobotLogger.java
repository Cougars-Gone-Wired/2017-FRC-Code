package org.usfirst.frc.team2996.robot;

import java.util.logging.Logger;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class RobotLogger implements Runnable {
	private Joystick stickManipulator;
	private CANTalon shooterMotor;
	Logger log = null;
	Logger autoLog;
	Logger teleLog;
	Robot robot;
	private volatile boolean running = false;
	boolean autonomousState = false;
	boolean teleopState = false;
	boolean shooterState = false;

	RobotLogger(Robot robot) {
		this.stickManipulator = robot.getStickManipulator();
		this.shooterMotor = robot.getShooterMotor();
		this.robot = robot;
		running = true;

	}

	public void halt() {
		System.out.println("Logging Halted");
		running = false;
	}

	public void run() {
		System.out.println("Logging Started");
		while (running) {
			boolean loggingActive = SmartDashboard.getBoolean("logging", false);
			boolean enabled = robot.isEnabled();
			if (loggingActive && enabled) {
				try {
					PIDlog();
					movementLog();
				} catch (Throwable e) {
					// TODO Auto-generated catch block
					// e.printStackTrace();
				}

			} else {
				autonomousState = false;
				teleopState = false;
				shooterState = false;
			}
			// 20 milliseconds
			Timer.delay(0.02);
		}
	}

	public void movementLog() throws Throwable {
		if (robot.isAutonomous()) {
			if (!autonomousState) {
				autoLog = ShooterFileLogging.getLogger("AutonomousMovement");
				autoLog.fine(", yawAngle, accelX, accelY, frontLeft, backLeft, frontRight, backRight, mecanumFlag");
				autonomousState = true;
			}

			if (autonomousState) {
				autoLog.fine(", " + robot.getGyro().getYaw() + ", " + robot.getGyro().getWorldLinearAccelX() + ", "
						+ robot.getGyro().getWorldLinearAccelY() + ", " + robot.getFrontLeftMotor().getEncPosition()
						+ ", " + robot.getBackLeftMotor().getEncPosition() + ", "
						+ robot.getFrontRightMotor().getEncPosition() + ", " + robot.getBackRightMotor().getEncPosition()
						+ ", " + robot.getDrive().isMecanum());
			}

		} else {
			autonomousState = false;
		}

		if (robot.isOperatorControl()) {
			if (!teleopState) {
				teleLog = ShooterFileLogging.getLogger("TeleopMovement");
				teleLog.fine(", yawAngle, accelX, accelY, frontLeft, backLeft, frontRight, backRight, mecanumFlag");
				teleopState = true;
			}

			if (teleopState) {
				teleLog.fine(", " + robot.getGyro().getYaw() + ", " + robot.getGyro().getWorldLinearAccelX() + ", "
						+ robot.getGyro().getWorldLinearAccelY() + ", " + robot.getFrontLeftMotor().getEncPosition()
						+ ", " + robot.getBackLeftMotor().getEncPosition() + ", "
						+ robot.getFrontRightMotor().getEncPosition() + ", " + robot.getBackRightMotor().getEncPosition()
						+ ", " + robot.getDrive().isMecanum());
			}

		} else {
			teleopState = false;
		}
	}

	public void PIDlog() throws Throwable {
		if (robot.isOperatorControl()) {
			if (!shooterState) {
				log = ShooterFileLogging.getLogger("ShooterFPID_F-" + shooterMotor.getF() + "_P-" + shooterMotor.getP()
						+ "_I-" + shooterMotor.getI() + "_D-" + shooterMotor.getD());
				log.fine(", shooterRpm, augerFwdPushed, augerBackPushed, augerCurrent");

				shooterState = true;
			}

			if (shooterState) {
				log.fine(", " + shooterMotor.getSpeed() + ", "
						+ stickManipulator.getRawButton(Robot.AUGER_FORWARD_BUTTON) + ", "
						+ stickManipulator.getRawButton(Robot.AUGER_BACKWARD_BUTTON) + ", " + robot.getAugerMotor().getOutputCurrent());
			}

		} else {
			shooterState = false;
		}
	}
}
