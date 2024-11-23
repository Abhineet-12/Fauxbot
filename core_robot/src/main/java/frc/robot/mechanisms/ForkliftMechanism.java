package frc.robot.mechanisms;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import frc.lib.driver.IDriver;
import frc.lib.driver.IOperation;
import frc.lib.mechanisms.IMechanism;
import frc.lib.robotprovider.DoubleSolenoidValue;
import frc.lib.robotprovider.IDoubleSolenoid;
import frc.lib.robotprovider.IMotor;
import frc.lib.robotprovider.IRobotProvider;
import frc.lib.robotprovider.PneumaticsModuleType;
import frc.lib.robotprovider.RobotMode;
import frc.robot.ElectronicsConstants;
import frc.robot.driver.AnalogOperation;
import frc.robot.driver.DigitalOperation;

@Singleton
public class ForkliftMechanism implements IMechanism
{

    private IDriver driver;
    private final IMotor leftMotor;
    private final IMotor rightMotor;
    private final IDoubleSolenoid forklift;
    
    @Inject
    public ForkliftMechanism(IDriver driver, IRobotProvider provider) 
    {
        
        this.driver = driver;
        this.leftMotor = provider.getTalon(ElectronicsConstants.FORKLIFT_LEFT_MOTOR_CHANNEL);
        this.rightMotor = provider.getTalon(ElectronicsConstants.FORKLIFT_RIGHT_MOTOR_CHANNEL);
        this.forklift = provider.getDoubleSolenoid(PneumaticsModuleType.PneumaticsControlModule, ElectronicsConstants.FORKLIFT_FOWARDS_CHANNEL,ElectronicsConstants.FORKLIFT_BACKWARDS_CHANNEL);
    }
    @Override
    public void readSensors()
    {
       //This project does not require sensors
       // throw new UnsupportedOperationException("Unimplemented method 'readSensors'");
    
    }

    @Override
    public void update(RobotMode mode)
    {
        double leftPower;
        leftPower = this.driver.getAnalog(AnalogOperation.TurnLeft);
        this.leftMotor.set(leftPower);
        double rightPower;
        rightPower = this.driver.getAnalog(AnalogOperation.TurnRight);
        this.rightMotor.set(rightPower);



     
        if (this.driver.getDigital(DigitalOperation.ForkLiftUp))
        {
            this.forklift.set(DoubleSolenoidValue.Forward);
        }
        if (this.driver.getDigital(DigitalOperation.ForkLiftDown))
        {
            this.forklift.set(DoubleSolenoidValue.Reverse);
        }

    }

    @Override
    public void stop()
    {
        // TODO Auto-generated method stub
        this.leftMotor.set(0);
        this.rightMotor.set(0);
        this.forklift.set(DoubleSolenoidValue.Off);
        //throw new UnsupportedOperationException("Unimplemented method 'stop'");
    }

}