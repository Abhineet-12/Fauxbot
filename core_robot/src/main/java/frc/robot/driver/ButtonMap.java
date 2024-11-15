package frc.robot.driver;

import frc.lib.driver.AnalogAxis;
import frc.lib.driver.IButtonMap;
import frc.lib.driver.IOperation;
import frc.lib.driver.UserInputDeviceButton;
import frc.lib.driver.buttons.ButtonType;
import frc.lib.driver.descriptions.*;
import frc.robot.driver.controltasks.PenMoveTask;
import frc.robot.driver.controltasks.PenWriteTask;
import frc.robot.driver.controltasks.SequentialTask;

import javax.inject.Singleton;

@Singleton
public class ButtonMap implements IButtonMap {
    private static ShiftDescription[] ShiftButtonSchema = new ShiftDescription[]{
            new ShiftDescription(
                    Shift.DriverDebug,
                    UserInputDevice.Driver,
                    UserInputDeviceButton.XBONE_SELECT_BUTTON),
            new ShiftDescription(
                    Shift.CodriverDebug,
                    UserInputDevice.Codriver,
                    UserInputDeviceButton.XBONE_LEFT_BUTTON),
            new ShiftDescription(
                    Shift.Test1Debug,
                    UserInputDevice.Test1,
                    UserInputDeviceButton.XBONE_LEFT_BUTTON),
            // new ShiftDescription(
            // Shift.Test2Debug,
            // UserInputDevice.Test2,
            // UserInputDeviceButton.XBONE_LEFT_BUTTON),
    };

    public static AnalogOperationDescription[] AnalogOperationSchema = new AnalogOperationDescription[]{

            /*
            Forklift button maps
            new AnalogOperationDescription(
                    AnalogOperation.LeftForkLiftPower,
                    UserInputDevice.Driver,
                    AnalogAxis.XBONE_LSX,
                    false,
                    0.1),
            new AnalogOperationDescription(
                    AnalogOperation.RightForkLiftPower,
                    UserInputDevice.Driver,
                    AnalogAxis.XBONE_RSX,
                    false,
                    0.1),



            Shooter button maps
            new AnalogOperationDescription(
                    AnalogOperation.ShooterHoodAnglePosition,
                    UserInputDevice.Driver,
                    AnalogAxis.XBONE_RSX,
                    false,
                    0.1),
             */

            //Printer button maps

            new AnalogOperationDescription(
                    AnalogOperation.PrinterXAxisPosition,
                    UserInputDevice.Driver,
                    AnalogAxis.XBONE_LSX,
                    false,
                    0.1),
            new AnalogOperationDescription(
                    AnalogOperation.PrinterYAxisPosition,
                    UserInputDevice.Driver,
                    AnalogAxis.XBONE_LSY,
                    false,
                    0.1),


    };

    public static DigitalOperationDescription[] DigitalOperationSchema = new DigitalOperationDescription[]{
            /*

            Forklift button maps
            new DigitalOperationDescription(
                    DigitalOperation.ForkLiftUp,
                    UserInputDevice.Driver,
                    UserInputDeviceButton.XBONE_A_BUTTON,
                    ButtonType.Click),
            new DigitalOperationDescription(
                    DigitalOperation.ForkLiftDown,
                    UserInputDevice.Driver,
                    UserInputDeviceButton.XBONE_B_BUTTON,
                    ButtonType.Click),



            Elevator button maps
            new DigitalOperationDescription(
                    DigitalOperation.ElevatorFirstFloorButton,
                    UserInputDevice.Driver,
                    UserInputDeviceButton.XBONE_A_BUTTON,
                    ButtonType.Click),
            new DigitalOperationDescription(
                    DigitalOperation.ElevatorSecondFloorButton,
                    UserInputDevice.Driver,
                    UserInputDeviceButton.XBONE_B_BUTTON,
                    ButtonType.Click),
            new DigitalOperationDescription(
                    DigitalOperation.ElevatorThirdFloorButton,
                    UserInputDevice.Driver,
                    UserInputDeviceButton.XBONE_Y_BUTTON,
                    ButtonType.Click),
            new DigitalOperationDescription(
                    DigitalOperation.ElevatorFourthFloorButton,
                    UserInputDevice.Driver,
                    UserInputDeviceButton.XBONE_X_BUTTON,
                    ButtonType.Click),
            new DigitalOperationDescription(
                    DigitalOperation.ElevatorFifthFloorButton,
                    UserInputDevice.Driver,
                    UserInputDeviceButton.XBONE_LEFT_STICK_BUTTON,
                    ButtonType.Click),



            Garage door button maps
            new DigitalOperationDescription(
                    DigitalOperation.GarageDoorToggle,
                    UserInputDevice.Driver,
                    UserInputDeviceButton.XBONE_A_BUTTON,
                    ButtonType.Click),



            Shooter button maps
            new DigitalOperationDescription(
                    DigitalOperation.ShooterSpinButton,
                    UserInputDevice.Driver,
                    UserInputDeviceButton.XBONE_A_BUTTON,
                    ButtonType.Toggle),
            new DigitalOperationDescription(
                    DigitalOperation.ShooterSpinButton,
                    UserInputDevice.Driver,
                    UserInputDeviceButton.XBONE_B_BUTTON,
                    ButtonType.Click),

           */

            //Printer button maps
            new DigitalOperationDescription(
                    DigitalOperation.PrinterPenDown,
                    UserInputDevice.Driver,
                    UserInputDeviceButton.XBONE_A_BUTTON,
                    ButtonType.Click),
            new DigitalOperationDescription(
                    DigitalOperation.PrinterPenUp,
                    UserInputDevice.Driver,
                    UserInputDeviceButton.XBONE_B_BUTTON,
                    ButtonType.Click),
            new DigitalOperationDescription(
                    DigitalOperation.PrinterToggleMode,
                    UserInputDevice.Driver,
                    UserInputDeviceButton.XBONE_Y_BUTTON,
                    ButtonType.Toggle),

    };

    public static MacroOperationDescription[] MacroSchema = new MacroOperationDescription[]{
            /**
             * Example Macro operation entry:
             * new MacroOperationDescription(
             * MacroOperation.ExampleAlpha,
             * UserInputDevice.Driver,
             * UserInputDeviceButton.XBONE_RIGHT_BUTTON,
             * ButtonType.Toggle,
             * () -> SequentialTask.Sequence(),
             * new IOperation[]
             * {
             * AnalogOperation.ExampleOne,
             * DigitalOperation.ExampleA,
             * }),
            */

            new MacroOperationDescription(
                    MacroOperation.DrawSquare,
                    UserInputDevice.Driver,
                    UserInputDeviceButton.XBONE_X_BUTTON,
                    ButtonType.Toggle,
                    () -> SequentialTask.Sequence(
                            new PenWriteTask(true),
                            new PenMoveTask(50.0, 50.0),
                            new PenWriteTask(false),
                            new PenMoveTask(100.0, 50.0),
                            new PenMoveTask(100.0, 100.0),
                            new PenMoveTask(50.0, 100.0),
                            new PenMoveTask(50.0, 50.0),
                            new PenWriteTask(true)
                    ),
                    new IOperation[]{
                            AnalogOperation.PrinterXAxisPosition,
                            AnalogOperation.PrinterYAxisPosition,
                            DigitalOperation.PrinterPenDown,
                            DigitalOperation.PrinterPenUp,
                            DigitalOperation.PrinterToggleMode
                    }
            )


    };

    @Override
    public ShiftDescription[] getShiftSchema() {
        return ButtonMap.ShiftButtonSchema;
    }

    @Override
    public AnalogOperationDescription[] getAnalogOperationSchema() {
        return ButtonMap.AnalogOperationSchema;
    }

    @Override
    public DigitalOperationDescription[] getDigitalOperationSchema() {
        return ButtonMap.DigitalOperationSchema;
    }

    @Override
    public MacroOperationDescription[] getMacroOperationSchema() {
        return ButtonMap.MacroSchema;
    }
}
