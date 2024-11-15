package frc.robot.driver.mechanisms;

import frc.lib.driver.IDriver;
import frc.lib.mechanisms.IMechanism;
import frc.lib.robotprovider.*;
import frc.robot.ElectronicsConstants;
import frc.robot.driver.AnalogOperation;
import frc.robot.driver.DigitalOperation;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class forkLiftMechanism implements IMechanism {
    private final IMotor leftMotor;
    private final IMotor rightMotor;
    private final IDoubleSolenoid piston;
    private final IDriver driver;

    @Inject
    public forkLiftMechanism(IDriver driver, IRobotProvider provider) {
        this.driver = driver;
        this.leftMotor = provider.getTalon(ElectronicsConstants.FORKLIFT_LEFT_MOTOR_CH_ID);
        this.rightMotor = provider.getTalon(ElectronicsConstants.FORKLIFT_RIGHT_MOTOR_CH_ID);
        this.piston = provider.getDoubleSolenoid(
                PneumaticsModuleType.PneumaticsControlModule, ElectronicsConstants.FORKLIFT_PISTON_FORWARD_CH_ID,
                ElectronicsConstants.FORKLIFT_PISTON_REVERSE_CH_ID);
    }

    @Override
    public void readSensors() {
    }

    @Override
    public void update(RobotMode mode) {
        double leftPower = this.driver.getAnalog(AnalogOperation.ForkLiftLeftPower);
        double rightPower = this.driver.getAnalog(AnalogOperation.ForkLiftRightower);
        this.leftMotor.set(leftPower);
        this.rightMotor.set(rightPower);
        if (this.driver.getDigital(DigitalOperation.ForkLiftUp)) {
            this.piston.set(DoubleSolenoidValue.Forward);
        }

        if (this.driver.getDigital(DigitalOperation.ForkLiftDown)) {
            this.piston.set(DoubleSolenoidValue.Reverse);
        }
    }

    @Override
    public void stop() {
        this.leftMotor.set(0.0);
        this.rightMotor.set(0.0);
        this.piston.set(DoubleSolenoidValue.Off);
    }

}
