package frc.robot.mechanisms;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import frc.lib.driver.IDriver;
import frc.lib.mechanisms.IMechanism;
import frc.lib.robotprovider.DoubleSolenoidValue;
import frc.lib.robotprovider.IDigitalInput;
import frc.lib.robotprovider.IDoubleSolenoid;
import frc.lib.robotprovider.IMotor;
import frc.lib.robotprovider.IRobotProvider;
import frc.lib.robotprovider.PneumaticsModuleType;
import frc.lib.robotprovider.RobotMode;
import frc.robot.driver.AnalogOperation;
import frc.robot.driver.DigitalOperation;

@Singleton
public class Forklift implements IMechanism
{
    private IMotor leftMotor;
    private IMotor rightMotor;
    private IDoubleSolenoid lifter;
    private IDriver driver;
    private IDigitalInput sensor;
    @Inject
    public Forklift (IDriver driver, IRobotProvider provider)
    {
        this.driver = driver;
        this.leftMotor = provider.getTalon(0);
        this.rightMotor = provider.getTalon(1);
        this.lifter = provider.getDoubleSolenoid(PneumaticsModuleType.PneumaticsControlModule, 7,8);
        this.sensor = provider.getDigitalInput(0);
    }

    @Override
    public void readSensors()
    {
        sensor.get();
    }

    @Override
    public void update(RobotMode mode) 
    {
        double leftMotorPower = this.driver.getAnalog(AnalogOperation.LeftMotorAnalog);
        double rightMotorPower = this.driver.getAnalog(AnalogOperation.RightMotorAnalog);

        this.leftMotor.set(leftMotorPower);
        this.rightMotor.set(rightMotorPower);
        
        boolean forkliftUp = this.driver.getDigital(DigitalOperation.ForkliftUp);
        boolean forliftDown = this.driver.getDigital(DigitalOperation.ForkliftDown);

        if(forkliftUp == true)
        {
            this.lifter.set(DoubleSolenoidValue.Forward);
        }

        if (forliftDown == true) {
            this.lifter.set(DoubleSolenoidValue.Reverse);
        }

    }

    @Override
    public void stop()
    {
        this.lifter.set(DoubleSolenoidValue.Off);
        this.rightMotor.set(0);
        this.leftMotor.set(0);
    }
}
