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
public class ShooterMechanism implements IMechanism {

    private final IDriver driver;
    private final ITalonSRX hoodMotor;
    private final ITalonSRX flyWheelMotor;
    private final IDoubleSolenoid kicker;

    private double hoodPosition;
    private double flyWheelPosition;

    private double hoodVelocity;
    private double flyWheelVelocity;

    private double timeout;

    @Inject
    public ShooterMechanism(IDriver driver, IRobotProvider provider) {
        this.driver = driver;
        this.hoodMotor = provider.getTalonSRX(ElectronicsConstants.SHOOTER_HOOD_MOTOR_CH_ID);
        this.flyWheelMotor = provider.getTalonSRX(ElectronicsConstants.SHOOTER_FLYWHEEL_MOTOR_CH_ID);
        this.kicker = provider.getDoubleSolenoid(
                PneumaticsModuleType.PneumaticsControlModule,
                ElectronicsConstants.SHOOTER_KICKER_SOLENOID_FORWARD_CH_ID,
                ElectronicsConstants.SHOOTER_KICKER_SOLENOID_REVERSE_CH_ID);
    }

    @Override
    public void readSensors() {
//        hoodPosition = this.hoodMotor.getPosition();
//        flyWheelPosition = this.flyWheelMotor.getPosition();
//
//        hoodVelocity = this.hoodMotor.getVelocity();
//        flyWheelVelocity = this.flyWheelMotor.getVelocity();
    }

    @Override
    public void update(RobotMode mode) {
        //Hood angle position
        double analogHoodPosition = this.driver.getAnalog(AnalogOperation.ShooterHoodAnglePosition);
        this.hoodMotor.set(analogHoodPosition);

        System.out.println("Joystick Position: " + analogHoodPosition);

        //Spin button

        if (this.driver.getDigital(DigitalOperation.ShooterSpinButton)) {
            this.flyWheelMotor.set(200);
        } else {
            this.flyWheelMotor.set(0);
        }

        //Fire button
        if (this.driver.getDigital(DigitalOperation.ShooterFireButton)) {
            this.kicker.set(DoubleSolenoidValue.Forward);
            timeout = 100;
        }

        if (timeout > 0) {
            timeout = timeout - ElectronicsConstants.SHOOTER_KICKER_TIMEOUT;
        } else {
            timeout = 0;
            this.kicker.set(DoubleSolenoidValue.Reverse);
        }

    }

    @Override
    public void stop() {
        this.hoodMotor.set(0);
        this.flyWheelMotor.set(0);
        this.kicker.set(DoubleSolenoidValue.Off);
    }
}
