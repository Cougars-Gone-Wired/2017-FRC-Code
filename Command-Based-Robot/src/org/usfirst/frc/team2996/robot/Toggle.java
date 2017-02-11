package org.usfirst.frc.team2996.robot;

import java.util.function.BiPredicate;
import java.util.function.Predicate;

import edu.wpi.first.wpilibj.Joystick;

public class Toggle {
	private boolean buttonWasPressed;
	private Joystick stick;
	private int buttonNumber;
	private boolean state = false;

	public Toggle(Joystick stick, int buttonNumber) {
		this.stick = stick;
		this.buttonNumber = buttonNumber;
	}

	public boolean toggle() {
		if (stick.getRawButton(buttonNumber)) {
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
}
