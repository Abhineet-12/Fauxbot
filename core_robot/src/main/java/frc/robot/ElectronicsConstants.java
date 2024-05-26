package frc.robot;

import frc.lib.robotprovider.PneumaticsModuleType;
import frc.lib.robotprovider.PowerDistributionModuleType;

/**
 * All constants describing how the electronics are plugged together.
 * 
 * @author Will
 * 
 */
public class ElectronicsConstants
{
    // We expect the following to be true.  Change INVERT_*_AXIS to true if any of the following are not met:
    // 1. forwards/up on a joystick is positive, backwards/down is negative.
    // 2. right on a joystick is positive, left on a joystick is negative.
    // 3. pressed on a trigger is positive, released is negative/zero.
    public static final boolean INVERT_XBONE_LEFT_X_AXIS = false;
    public static final boolean INVERT_XBONE_RIGHT_X_AXIS = false;
    public static final boolean INVERT_XBONE_LEFT_Y_AXIS = true;
    public static final boolean INVERT_XBONE_RIGHT_Y_AXIS = true;
    public static final boolean INVERT_XBONE_LEFT_TRIGGER = false;
    public static final boolean INVERT_XBONE_RIGHT_TRIGGER = false;

    public static final boolean INVERT_PS4_LEFT_X_AXIS = false;
    public static final boolean INVERT_PS4_RIGHT_X_AXIS = false;
    public static final boolean INVERT_PS4_LEFT_Y_AXIS = true;
    public static final boolean INVERT_PS4_RIGHT_Y_AXIS = true;
    public static final boolean INVERT_PS4_LEFT_TRIGGER = false;
    public static final boolean INVERT_PS4_RIGHT_TRIGGER = false;

    public static final boolean INVERT_THROTTLE_AXIS = true;
    public static final boolean INVERT_TRIGGER_AXIS = false;

    public static final int POWER_DISTRIBUTION_CAN_ID = 1;
    public static final PowerDistributionModuleType POWER_DISTRIBUTION_TYPE = PowerDistributionModuleType.PowerDistributionHub;

    public static final double REV_THROUGHBORE_ENCODER_DUTY_CYCLE_MIN = 1.0 / 1024.0;
    public static final double REV_THROUGHBORE_ENCODER_DUTY_CYCLE_MAX = 1023.0 / 1024.0;

    public static final String CANIVORE_NAME = "CANIVORE1"; // Module A

    public static final int PNEUMATICS_MODULE_A = 1; // Module A
    public static final PneumaticsModuleType PNEUMATICS_MODULE_TYPE_A = PneumaticsModuleType.PneumaticsHub; // Module A

    public static final int PNEUMATICS_MODULE_B = 2; // Module B
    public static final PneumaticsModuleType PNEUMATICS_MODULE_TYPE_B = PneumaticsModuleType.PneumaticsHub; // Module B

    public static final boolean PNEUMATICS_USE_HYBRID = false;
    public static final boolean PNEUMATICS_USE_ANALOG = false;
    public static final double PNEUMATICS_MIN_PSI = 110.0;
    public static final double PNEUMATICS_MAX_PSI = 120.0;

    //================================================== Garage Door =======================================================

    public static final int GARAGEDOOR_MOTOR_PWM_CHANNEL = 0;

    public static final int GARAGEDOOR_THROUGHBEAMSENSOR_DIGITAL_CHANNEL = 0;
    public static final int GARAGEDOOR_OPENSENSOR_DIGITAL_CHANNEL = 1;
    public static final int GARAGEDOOR_CLOSEDSENSOR_DIGITAL_CHANNEL = 2;

    //================================================== Elevator ==========================================================

    public static final int ELEVATOR_MOTOR_PWM_CHANNEL = 0;

    public static final int ELEVATOR_ENCODER_DIGITAL_CHANNEL_A = 0;
    public static final int ELEVATOR_ENCODER_DIGITAL_CHANNEL_B = 1;

   //================================================== Forklift =======================================================

    public static final int FORKLIFT_DRIVE_LEFT_MOTOR_CAN_ID = 0;
    public static final int FORKLIFT_DRIVE_RIGHT_MOTOR_CAN_ID = 1; 

    public static final int FORKLIFT_LIFTER_FORWARD_PCM_CHANNEL = 7;
    public static final int FORKLIFT_LIFTER_BACKWARD_PCM_CHANNEL = 8;

    //================================================== Shooter =======================================================

    public static final int SHOOTER_ANGLE_MOTOR_CAN_ID = 0;
    public static final int SHOOTER_FLY_WHEEL_MOTOR_CAN_ID = 1;

    public static final int SHOOTER_KICKER_FORWARD_PCM_CHANNEL = 7;
    public static final int SHOOTER_KICKER_BACKWARD_PCM_CHANNEL = 8;

    //================================================== Printer =======================================================

    public static final int PRINTER_X_MOTOR_CAN_ID = 0;
    public static final int PRINTER_Y_MOTOR_CAN_ID = 1; 

    public static final int PRINTER_PEN_FORWARD_PCM_CHANNEL = 7;
    public static final int PRINTER_PEN_BACKWARD_PCM_CHANNEL = 8;
}
