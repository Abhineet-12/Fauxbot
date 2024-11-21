package frc.robot.driver;

import frc.lib.driver.IOperation;

public enum DigitalOperation implements IOperation
{
    // GarageDoor operations:
    garageButton,

    // Forklift operations: 
    LiftUp,
    LiftDown,

    // Elevator operations:
    FirstFloor,
    SecondFloor,
    ThirdFloor,
    FourthFloor,
    FifthFloor,

    // Printer operations:

    // Shooter operations:
    ShooterSpin,
    ShooterFire,
}
