package frc.robot.mechanisms;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import frc.lib.driver.IDriver;
import frc.lib.mechanisms.IMechanism;
import frc.lib.robotprovider.DoubleSolenoidValue;
import frc.lib.robotprovider.IDoubleSolenoid;
import frc.lib.robotprovider.IMotor;
import frc.lib.robotprovider.IRobotProvider;
import frc.lib.robotprovider.PneumaticsModuleType;
import frc.robot.ElectronicsConstants;
import frc.robot.driver.AnalogOperation;
import frc.robot.driver.DigitalOperation;

@Singleton
public class ForkliftMechanism implements IMechanism{
    private final IDoubleSolenoid lifter;
    private final IMotor rightMotor;
    private final IMotor leftMotor;
    private final IDriver driver;

    @Inject
    public ForkliftMechanism(IRobotProvider provider, IDriver driver) {
        this.leftMotor = provider.getTalon(ElectronicsConstants.FORKLIFT_LEFT_MOTOR_CHANNEL);
        this.rightMotor = provider.getTalon(ElectronicsConstants.FORKLIFT_RIGHT_MOTOR_CHANNEL);
        this.driver = driver;
        this.lifter = provider.getDoubleSolenoid(
            PneumaticsModuleType.PneumaticsControlModule, 
            ElectronicsConstants.FORKLIFT_DOUBLE_SOLENOID_FORWARD, 
            ElectronicsConstants.FORKLIFT_DOUBLE_SOLENOID_REVERSE
        );
    }

    @Override
    public void readSensors()
    {
        
        
    }

    @Override
    public void update()
    {
        this.leftMotor.set(this.driver.getAnalog(AnalogOperation.MoveLeft));
        this.rightMotor.set(this.driver.getAnalog(AnalogOperation.MoveRight));

        if (this.driver.getDigital(DigitalOperation.LiftUp)) {
            this.lifter.set(DoubleSolenoidValue.Forward);
        }
        else if (this.driver.getDigital(DigitalOperation.LiftDown)){
            this.lifter.set(DoubleSolenoidValue.Reverse);
        }
    }

    @Override
    public void stop()
    {
        // TODO Auto-generated method stub
        this.leftMotor.set(0.0);
        this.rightMotor.set(0.0);
        this.lifter.set(DoubleSolenoidValue.Off);
    }
    
}
