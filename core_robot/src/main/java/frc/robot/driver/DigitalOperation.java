package frc.robot.driver;

import frc.lib.driver.IOperation;

public enum DigitalOperation implements IOperation {
    // GarageDoor operations:
    GarageDoorToggle,
    // Forklift operations: 
    ForkLiftUp,
    ForkLiftDown,
    // Elevator operations:
    ElevatorFirstFloorButton,
    ElevatorSecondFloorButton,
    ElevatorThirdFloorButton,
    ElevatorFourthFloorButton,
    ElevatorFifthFloorButton,
    // Printer operations:
    PrinterPenDown,
    PrinterPenUp,
    PrinterToggleMode,
    // Shooter operations:
    ShooterSpinButton,
    ShooterFireButton,
}
