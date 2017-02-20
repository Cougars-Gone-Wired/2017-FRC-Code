package org.usfirst.frc.team2996.robot;

import java.util.function.BiPredicate;
import java.util.function.Predicate;

import edu.wpi.first.wpilibj.Joystick;

public class TwoButtonToggle {
	private boolean buttonWasPressed;
	private Joystick stick;
	private int buttonNumber;
	private int buttonNumber2;
	private boolean state = false;

	public TwoButtonToggle(Joystick stick, int buttonNumber, int buttonNumber2) {
		this.stick = stick;
		this.buttonNumber = buttonNumber;
		this.buttonNumber2 = buttonNumber2;
	}

	public boolean toggle() {
		if (stick.getRawButton(buttonNumber) && stick.getRawButton(buttonNumber2)) {
			if (!buttonWasPressed) {
				state = !state;
			}
			buttonWasPressed = true;
		} else {
			buttonWasPressed = false;
		}
		return state;
	}
	
	public boolean reset(){
		state = false;
		return state;
	}
	
	public boolean set(){
		state = true;
		return state;
	}
}
