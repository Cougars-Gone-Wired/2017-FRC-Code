package org.usfirst.frc.team2996.robot;

import edu.wpi.first.wpilibj.Timer;

public class RampUp {
	double desiredVoltage;
	double steps;
	Robot robot;
	double duration;
	double sleepTime;
	double angle;

	public RampUp(double desiredVoltage, double steps, Robot robot, double duration, double angle) {
		this.desiredVoltage = desiredVoltage;
		this.steps = steps;
		this.robot = robot;
		this.duration = duration;
		sleepTime = duration / steps;
		this.angle = angle;

	}

	public void run() {
		for (int I = 0; I < steps; I++) {

			double ramp = ((Math.E - 1) / steps) * I;
			double voltage = (desiredVoltage * Math.log1p(ramp));
			robot.getRobotDrive().drive(voltage, angle);
			Timer.delay(sleepTime);
		}
	}
}
