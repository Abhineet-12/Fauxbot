package frc.robot.mechanisms;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import frc.lib.driver.IDriver;
import frc.lib.mechanisms.IMechanism;
import frc.lib.robotprovider.DoubleSolenoidValue;
import frc.lib.robotprovider.IDoubleSolenoid;
import frc.lib.robotprovider.IMotor;
import frc.lib.robotprovider.IRobotProvider;
import frc.lib.robotprovider.RobotMode;
import frc.robot.driver.AnalogOperation;
import frc.robot.driver.DigitalOperation;

@Singleton
public class ForkliftMechanism implements IMechanism {
    
    private final IMotor driveTrainLeft;
    private final IMotor driveTrainRight;
    private final IDoubleSolenoid lifter;
    private final IDriver driver;
    
    @Inject
    public ForkliftMechanism(IRobotProvider provider, IDriver driver) {
        this.driver = driver;
        this.lifter = provider.getDoubleSolenoid(null, 7, 8);
        this.driveTrainLeft = provider.getTalon(0);
        this.driveTrainRight = provider.getTalon(1);

    }
    @Override
    public void readSensors() {
        
    }

    @Override
    public void update(RobotMode mode) {
        boolean forkliftUp = this.driver.getDigital(DigitalOperation.ForkliftUp);
        boolean forkliftDown = this.driver.getDigital(DigitalOperation.ForkliftDown);
        
        double leftMotor = this.driver.getAnalog(AnalogOperation.DriveTrainLeft);
        double rightMotor = this.driver.getAnalog(AnalogOperation.DriveTrainRight);

        if (forkliftUp) {
            this.lifter.set(DoubleSolenoidValue.Forward);
        }

        if (forkliftDown) {
            this.lifter.set(DoubleSolenoidValue.Reverse);
        }

        this.driveTrainLeft.set(leftMotor);
        this.driveTrainRight.set(rightMotor);
        
    }

    @Override
    public void stop() {
        this.lifter.set(DoubleSolenoidValue.Off);
        this.driveTrainLeft.set(0);
        this.driveTrainRight.set(0);
    }
    
}
