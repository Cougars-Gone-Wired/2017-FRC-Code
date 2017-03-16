package org.usfirst.frc.team2996.robot;

import java.util.logging.Logger;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class RobotLogger implements Runnable {
	private Joystick stickManipulator;
	boolean shooterButtonPushed = false;
	private CANTalon shooterMotor;
	Logger log = null;
	Robot robot;

	RobotLogger(Robot robot) {
		this.stickManipulator = robot.getStickManipulator();
		this.shooterMotor = robot.getShooterMotor();
		this.robot = robot;

	}

	public void run() {
		boolean loggingActive = SmartDashboard.getBoolean("logging", false);
		if (loggingActive) {
			PIDlog();
		}
	}

	public void PIDlog() {
		while (robot.isAutonomous() || robot.isOperatorControl()) {
			if (stickManipulator.getRawButton(Robot.SHOOTER_BUTTON)) {
				if (!shooterButtonPushed) {
					try {
						log = ShooterFileLogging.getLogger("ShooterFPID_F:" + shooterMotor.getF() + "_P:" + shooterMotor.getP()
								+ "_I:" + shooterMotor.getI() + "_D:" + shooterMotor.getD());
						log.fine(", shooterRpm");
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (Throwable e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					shooterButtonPushed = true;
				}

				if (shooterButtonPushed) {
					log.fine(", " + shooterMotor.getSpeed());
				}

			} else {
				shooterButtonPushed = false;
			}
		}
		//20 milliseconds
		Timer.delay(0.02);
	}
}
