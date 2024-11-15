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
import frc.lib.robotprovider.RobotMode;
import frc.robot.ElectronicsConstants;
import frc.robot.driver.AnalogOperation;
import frc.robot.driver.DigitalOperation;

@Singleton
public class ForkLiftMechanism implements IMechanism {
    private IMotor leftMotor;
    private IMotor rightMotor;
    private final IDoubleSolenoid piston;
    private IDriver driver;

    @Inject
    public ForkLiftMechanism(IDriver driver, IRobotProvider provider){
        this.driver = driver;
        this.rightMotor = provider.getTalon(ElectronicsConstants.FORKLIFT_RIGHT_MOTOR_CHANNEL);
        this.leftMotor = provider.getTalon(0);
        this.piston = provider.getDoubleSolenoid(PneumaticsModuleType.PneumaticsControlModule,7,8 );
    }

    @Override
    public void readSensors() {
        // TODO Auto-generated method stub
    }

    @Override
    public void update(RobotMode mode) {
        
        if(this.driver.getDigital(DigitalOperation.ForkliftUp)){
            this.piston.set(DoubleSolenoidValue.Forward);
        }
        
        else if(this.driver.getDigital(DigitalOperation.ForkliftDown)){
            this.piston.set(DoubleSolenoidValue.Reverse);
        }
        this.leftMotor.set(this.driver.getAnalog(AnalogOperation.DriveTrainLeft));
        this.rightMotor.set(this.driver.getAnalog(AnalogOperation.DriveTrainRight));

    }

    @Override
    public void stop() {
        // TODO Auto-generated method stub
        this.leftMotor.set(0.0);
        this.rightMotor.set(0.0);
        this.piston.set(DoubleSolenoidValue.Off);
    }
    
}
