package frc.robot.mechanisms;

import javax.inject.Inject;
import javax.inject.Singleton;

import frc.lib.driver.IDriver;
import frc.lib.mechanisms.IMechanism;
import frc.lib.robotprovider.IMotor;
import frc.lib.robotprovider.IRobotProvider;
import frc.robot.driver.AnalogOperation;

@Singleton
public class SampleMechanism implements IMechanism
{
    private final IMotor motor;
    private final IDriver driver;

    @Inject
    public SampleMechanism(IRobotProvider provider, IDriver driver)
    {
        this.motor = provider.getTalon(1);
        this.driver = driver;
    }

    @Override
    public void readSensors()
    {
    }

    @Override
    public void update()
    {
        double speed = this.driver.getAnalog(AnalogOperation.SampleMove);
        this.motor.set(speed);
    }

    @Override
    public void stop()
    {
        this.motor.set(0.0);
    }   
}
