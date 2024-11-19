package frc.robot.driver;

import frc.lib.driver.IOperation;

public enum AnalogOperation implements IOperation
{
    ExampleOne,
    ExampleTwo,

    // Forklift operations: 
    DriveTrainLeft,
    DriveTrainRight,
    // Printer operations:
    xAxisPosition,
    yAxisPosition,
    // Shooter operations:
    HoodAnglePosition,
}
