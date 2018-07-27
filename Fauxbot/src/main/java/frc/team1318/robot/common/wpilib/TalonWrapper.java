package frc.team1318.robot.common.wpilib;

import edu.wpi.first.wpilibj.Talon;

public class TalonWrapper implements IMotor
{
    private final Talon wrappedObject;

    public TalonWrapper(int channel)
    {
        this.wrappedObject = new Talon(channel);
    }

    public void set(double power)
    {
        this.wrappedObject.set(power);
    }
}
