package frc.robot.driver.Mechanisms;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import frc.lib.driver.IDriver;
import frc.lib.mechanisms.IMechanism;
import frc.lib.robotprovider.DoubleSolenoidValue;
import frc.lib.robotprovider.IDoubleSolenoid;
import frc.lib.robotprovider.IMotor;
import frc.lib.robotprovider.IRobotProvider;
import frc.lib.robotprovider.PneumaticsModuleType;
import frc.lib.robotprovider.RobotMode;
import frc.robot.driver.AnalogOperation;
import frc.robot.driver.DigitalOperation;

@Singleton
public class ForkliftMechanism implements IMechanism{
    private IDriver driver;
    private IMotor leftMotor;
    private IMotor rightMotor;
    private IDoubleSolenoid forklift;
    @Inject
    public ForkliftMechanism (IDriver driver, IRobotProvider provider) {
        this.driver = driver;
        this.leftMotor = provider.getTalon(0);
        this.rightMotor = provider.getTalon(1);
        this.forklift = provider.getDoubleSolenoid(PneumaticsModuleType.PneumaticsControlModule, 7, 8);
  
    }


    @Override
    public void readSensors() {
        
    }

    @Override
    public void update(RobotMode mode) {
        double leftPower = this.driver.getAnalog(AnalogOperation.TurnLeft);
        this.leftMotor.set(leftPower);

        double rightPower = this.driver.getAnalog(AnalogOperation.TurnRight);
        this.leftMotor.set(rightPower);

        boolean ForkliftUp = this.driver.getDigital(DigitalOperation.ForkliftUp);
        
        boolean ForkliftDown;
        ForkliftDown = this.driver.getDigital(DigitalOperation.ForkliftDown);

        if(ForkliftUp)
        {
            this.forklift.set(DoubleSolenoidValue.Forward);
        }

        if (ForkliftDown)
        {
            this.forklift.set(DoubleSolenoidValue.Reverse);
        }
    }

    @Override
    public void stop() {
        this.leftMotor.set(0);
        this.rightMotor.set(0);
        this.forklift.set(DoubleSolenoidValue.Off);
    }
    
}
