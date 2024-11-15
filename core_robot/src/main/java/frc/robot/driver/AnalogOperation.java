package frc.robot.driver;

import frc.lib.driver.IOperation;

public enum AnalogOperation implements IOperation {
    // Forklift operations:
    ForkLiftLeftPower,
    ForkLiftRightower,
    // Printer operations:
    PrinterXAxisPosition,
    PrinterYAxisPosition,
    // Shooter operations:
    ShooterHoodAnglePosition,

}
