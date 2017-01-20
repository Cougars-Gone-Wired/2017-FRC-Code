package org.usfirst.frc.team2996.robot;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.command.PIDSubsystem;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;

public class Shooter extends PIDSubsystem {
	CANTalon shooter;
	public Shooter(CANTalon shooter){
		super("Shooter", 1.0, 5, 1.0);
		this.shooter = shooter;
		setAbsoluteTolerance(0.2);
		getPIDController().setContinuous(false);
		LiveWindow.addActuator("shooter", "PID Subsystem", getPIDController());
		
	}

	@Override
	protected double returnPIDInput() {
		return shooter.getOutputVoltage();
	}

	@Override
	protected void usePIDOutput(double output) {
		shooter.pidWrite(output);		
	}

	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub
		
	}

}