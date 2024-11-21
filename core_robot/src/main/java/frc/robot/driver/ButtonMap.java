package frc.robot.driver;

import javax.inject.Singleton;

import frc.lib.driver.*;
import frc.lib.driver.buttons.*;
import frc.lib.driver.descriptions.*;
import frc.lib.helpers.Helpers;
import frc.robot.*;
import frc.robot.driver.controltasks.*;

@Singleton
public class ButtonMap implements IButtonMap
{
    private static ShiftDescription[] ShiftButtonSchema = new ShiftDescription[]
    {
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
        //     Shift.Test2Debug,
        //     UserInputDevice.Test2,
        //     UserInputDeviceButton.XBONE_LEFT_BUTTON),
    };

    public static AnalogOperationDescription[] AnalogOperationSchema = new AnalogOperationDescription[]
    {
        /** Example Analog operation entry:
        new AnalogOperationDescription(
            Operation.SomeAnalogOperation,
            UserInputDevice.Driver,
            AnalogAxis.JOYSTICK_X,
            ElectronicsConstants.INVERT_X_AXIS,
            TuningConstants.DRIVETRAIN_X_DEAD_ZONE),*/
        /* new AnalogOperationDescription(
            AnalogOperation.SampleMove,
            UserInputDevice.Driver,
            AnalogAxis.XBONE_LT,
            false,
            0.1), 

        new AnalogOperationDescription(
            AnalogOperation.MoveLeft, 
            UserInputDevice.Driver, 
            AnalogAxis.XBONE_LT,
            false,
            0.1
            ),

        new AnalogOperationDescription(
            AnalogOperation.MoveRight,
            UserInputDevice.Driver,
            AnalogAxis.XBONE_RT,
            false,
            0.1
            ),*/
        new AnalogOperationDescription(
            AnalogOperation.ShooterAngle, 
            UserInputDevice.Driver, 
            AnalogAxis.BUTTONPAD_DPAD_X, 
            false, 
            0.1),
    };

    public static DigitalOperationDescription[] DigitalOperationSchema = new DigitalOperationDescription[]
    {
        /** Example Digital operation entry:
        new DigitalOperationDescription(
            DigitalOperation.SomeDigitalOperation,
            UserInputDevice.Driver,
            UserInputDeviceButton.JOYSTICK_STICK_TRIGGER_BUTTON,
            ButtonType.Toggle),*/
        
         /**new DigitalOperationDescription(
            DigitalOperation.LiftUp,
            UserInputDevice.Driver,
            UserInputDeviceButton.XBONE_LEFT_BUTTON,
            ButtonType.Click
        ),

        new DigitalOperationDescription(
            DigitalOperation.LiftDown,
            UserInputDevice.Driver,
            UserInputDeviceButton.XBONE_RIGHT_BUTTON,
            ButtonType.Click
        ), 

        new DigitalOperationDescription(
            DigitalOperation.garageButton, 
            UserInputDevice.Driver, 
            UserInputDeviceButton.BUTTON_PAD_BUTTON_1, 
            ButtonType.Click
        ), 
        
        new DigitalOperationDescription(
            DigitalOperation.FirstFloor,
            UserInputDevice.Driver,
            UserInputDeviceButton.BUTTON_PAD_BUTTON_1,
            ButtonType.Click
        ),

        new DigitalOperationDescription(
            DigitalOperation.SecondFloor,
            UserInputDevice.Driver,
            UserInputDeviceButton.BUTTON_PAD_BUTTON_2,
            ButtonType.Click
        ),

        new DigitalOperationDescription(
            DigitalOperation.ThirdFloor,
            UserInputDevice.Driver,
            UserInputDeviceButton.BUTTON_PAD_BUTTON_3,
            ButtonType.Click
        ),

        new DigitalOperationDescription(
            DigitalOperation.FourthFloor,
            UserInputDevice.Driver,
            UserInputDeviceButton.BUTTON_PAD_BUTTON_4,
            ButtonType.Click
        ),

        new DigitalOperationDescription(
            DigitalOperation.FifthFloor,
            UserInputDevice.Driver,
            UserInputDeviceButton.BUTTON_PAD_BUTTON_5,
            ButtonType.Click
        ),*/

        new DigitalOperationDescription(
            DigitalOperation.ShooterFire, 
            UserInputDevice.Driver, 
            UserInputDeviceButton.XBONE_B_BUTTON,
            ButtonType.Click
        ),

        new DigitalOperationDescription(
            DigitalOperation.ShooterSpin, 
            UserInputDevice.Driver, 
            UserInputDeviceButton.XBONE_X_BUTTON,
            ButtonType.Toggle
        ),
    };

    public static MacroOperationDescription[] MacroSchema = new MacroOperationDescription[]
    {
        /** Example Macro operation entry:
        new MacroOperationDescription(
            MacroOperation.SomeMacroOperation,
            UserInputDevice.Driver,
            UserInputDeviceButton.JOYSTICK_STICK_THUMB_BUTTON,
            ButtonType.Toggle,
            () -> SequentialTask.Sequence(),
            new IOperation[]
            {
                AnalogOperation.SomeAnalogOperation,
                DigitalOperation.SomeDigitalOperation,
            }),*/
    };

    @Override
    public ShiftDescription[] getShiftSchema()
    {
        return ButtonMap.ShiftButtonSchema;
    }

    @Override
    public AnalogOperationDescription[] getAnalogOperationSchema()
    {
        return ButtonMap.AnalogOperationSchema;
    }

    @Override
    public DigitalOperationDescription[] getDigitalOperationSchema()
    {
        return ButtonMap.DigitalOperationSchema;
    }

    @Override
    public MacroOperationDescription[] getMacroOperationSchema()
    {
        return ButtonMap.MacroSchema;
    }
}
