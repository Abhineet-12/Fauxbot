package frc.robot.Mechanisms;


import com.google.inject.Inject;
import com.google.inject.Singleton;

import frc.lib.driver.IDriver;
import frc.lib.mechanisms.IMechanism;
import frc.lib.mechanisms.LoggingManager;
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
    private IDoubleSolenoid lifter;
    private IDriver driver;
    private IMotor driveTrainLeft;
    private IMotor driveTrainRight;

    @Inject
    public ForkLiftMechanism(IRobotProvider provider, IDriver driver)
    {
        this.driveTrainLeft=provider.getTalon(ElectronicsConstants.FORKLIFT_LEFT_MOTOR_CHANNEL);
        this.driveTrainRight=provider.getTalon(ElectronicsConstants.FORKLIFT_RIGHT_MOTOR_CHANNEL);
        this.driver = driver;
        this.lifter = provider.getDoubleSolenoid(
            PneumaticsModuleType.PneumaticsControlModule,
            ElectronicsConstants.FORKLIFT_DOUBLE_SOLENOID_FORWARD,
            ElectronicsConstants.FORKLIFT_DOUBLE_SOLENOID_REVERSE
        );

    }

    @Override
    public void readSensors() {
    }

    @Override
    public void update(RobotMode mode) {
        this.driveTrainLeft.set(this.driver.getAnalog(AnalogOperation.MoveLeft));
        this.driveTrainRight.set(this.driver.getAnalog(AnalogOperation.MoveRight));

        if (this.driver.getDigital(DigitalOperation.LiftUp)){
            this.lifter.set(DoubleSolenoidValue.Forward);
        }
       
        else if (this.driver.getDigital(DigitalOperation.LiftDown)){
            this.lifter.set(DoubleSolenoidValue.Reverse);
        }


    }

    @Override
    public void stop() {
        this.driveTrainLeft.set(0.0);
        this.driveTrainRight.set(0.0);
        this.lifter.set(DoubleSolenoidValue.Off);
    }

}
