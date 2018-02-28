package org.usfirst.frc.team1318.robot;

/**
 * All constants describing how the electronics are plugged together.
 * 
 * @author Will
 * 
 */
public class ElectronicsConstants
{
    // change INVERT_X_AXIS to true if positive on the joystick isn't to the right, and negative isn't to the left
    public static final boolean INVERT_X_AXIS = false;

    // change INVERT_Y_AXIS to true if positive on the joystick isn't forward, and negative isn't backwards.
    public static final boolean INVERT_Y_AXIS = false;

    // change INVERT_THROTTLE_AXIS to true if positive on the joystick isn't forward, and negative isn't backwards.
    public static final boolean INVERT_THROTTLE_AXIS = true;

    public static final int PCM_A_MODULE = 0;
    public static final int PCM_B_MODULE = 1;

    public static final int JOYSTICK_DRIVER_PORT = 0;
    public static final int JOYSTICK_CO_DRIVER_PORT = 1;

    //================================================== Auto ==============================================================

    public static final int AUTO_DIP_SWITCH_A_DIGITAL_CHANNEL = 0;

    //================================================== Garage Door =======================================================

    public static final int GARAGEDOOR_MOTOR_DIGITAL_CHANNEL = 0;

    public static final int GARAGEDOOR_THROUGHBEAMSENSOR_DIGITAL_CHANNEL = 0;
    public static final int GARAGEDOOR_OPENSENSOR_DIGITAL_CHANNEL = 1;
    public static final int GARAGEDOOR_CLOSEDSENSOR_DIGITAL_CHANNEL = 2;

    //================================================== Elevator ==========================================================

    public static final int ELEVATOR_MOTOR_PWM_CHANNEL = 0;

    public static final int ELEVATOR_ENCODER_DIGITAL_CHANNEL_A = 0;
    public static final int ELEVATOR_ENCODER_DIGITAL_CHANNEL_B = 1;
    
   //================================================== Drive Train =======================================================

    public static final int DRIVETRAIN_LEFT_MOTOR_PWM_CHANNEL = 0;
    public static final int DRIVETRAIN_RIGHT_MOTOR_PWM_CHANNEL = 1; 
}
