package frc.robot.driver;

import frc.lib.driver.IOperation;

public enum DigitalOperation implements IOperation
{
    ExampleA,
    ExampleB,

    // GarageDoor operations:
    Button,
    // Forklift operations: 
    ForkliftUp,
    ForkliftDown,
    // Elevator operations:
    FirstFloorButton,
    SecondFloorButton,
    ThirdFloorButton,
    FourthFloorButton,
    FifthFloorButton,
    // Printer operations:

    // Shooter operations:
    SpinButton,
    FireButton,
}
