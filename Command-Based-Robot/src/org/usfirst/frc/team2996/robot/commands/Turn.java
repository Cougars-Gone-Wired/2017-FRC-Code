package org.usfirst.frc.team2996.robot.commands;

import org.usfirst.frc.team2996.robot.AutonomousPrograms;
import org.usfirst.frc.team2996.robot.Drive;
import org.usfirst.frc.team2996.robot.Robot;
import org.usfirst.frc.team2996.robot.Shooter;

import edu.wpi.first.wpilibj.command.Command;

public class Turn extends Command {
	Drive drive;
	AutonomousPrograms auto;
	int angle;
    public Turn(Robot robot, int angle) {
        drive = robot.getDrive();
        auto = robot.getAuto();
        this.angle = angle;
      }

    // Called just before this Command runs the first time
    protected void initialize() {
    	
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	auto.saisTurn("left", angle, 0.6);
   }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return auto.isTurnFinished("left", angle);
    }

    // Called once after isFinished returns true
    protected void end() {
    	auto.stop();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
