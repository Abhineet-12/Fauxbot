package frc.robot.driver.mechanisms;

import frc.lib.driver.IDriver;
import frc.lib.mechanisms.IMechanism;
import frc.lib.robotprovider.IEncoder;
import frc.lib.robotprovider.IMotor;
import frc.lib.robotprovider.IRobotProvider;
import frc.lib.robotprovider.RobotMode;
import frc.robot.ElectronicsConstants;
import frc.robot.driver.DigitalOperation;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class ElevatorMechanism implements IMechanism {
    private final IDriver driver;
    private final IMotor motor;
    private final IEncoder encoder;
    private int targetHeight = 0;

    @Inject
    public ElevatorMechanism(IDriver driver, IRobotProvider provider) {
        this.driver = driver;
        this.motor = provider.getTalon(ElectronicsConstants.ELEVATOR_MOTOR_CH_ID);
        this.encoder = provider.getEncoder(ElectronicsConstants.ELEVATOR_ENCODER_CH_A_ID,
                ElectronicsConstants.ELEVATOR_ENCODER_CH_B_ID);
    }

    @Override
    public void readSensors() {
        double encoder = this.encoder.get();
    }

    @Override
    public void update(RobotMode mode) {

        double encoderValue = this.encoder.get();

        if (this.driver.getDigital(DigitalOperation.ElevatorFirstFloorButton)) {
            targetHeight = 0;
            if (encoderValue > targetHeight) {
                this.motor.set(-1);
            } else {
                this.motor.set(1);
            }
        }
        if (this.driver.getDigital(DigitalOperation.ElevatorSecondFloorButton)) {
            targetHeight = 50;
            if (encoderValue > targetHeight) {
                this.motor.set(-1);
            } else {
                this.motor.set(1);
            }
        }
        if (this.driver.getDigital(DigitalOperation.ElevatorThirdFloorButton)) {
            targetHeight = 100;
            if (encoderValue > targetHeight) {
                this.motor.set(-1);
            } else {
                this.motor.set(1);
            }
        }
        if (this.driver.getDigital(DigitalOperation.ElevatorFourthFloorButton)) {
            targetHeight = 150;
            if (encoderValue > targetHeight) {
                this.motor.set(-1);
            } else {
                this.motor.set(1);
            }
        }
        if (this.driver.getDigital(DigitalOperation.ElevatorFifthFloorButton)) {
            targetHeight = 200;
            if (encoderValue > targetHeight) {
                this.motor.set(-1);
            } else {
                this.motor.set(1);
            }
        }

        if (encoderValue == targetHeight) {
            this.motor.set(0);
        }

    }

    @Override
    public void stop() {
        this.motor.set(0);
        this.encoder.reset();
    }

}
