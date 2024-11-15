package frc.robot.driver.mechanisms;

import frc.lib.controllers.PIDHandler;
import frc.lib.driver.IDriver;
import frc.lib.mechanisms.IMechanism;
import frc.lib.robotprovider.*;
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
    private PIDHandler pidHandler;
    private double encoderValue;

    @Inject
    public ElevatorMechanism(IDriver driver, IRobotProvider provider, ITimer timer) {
        this.driver = driver;
        this.motor = provider.getTalon(ElectronicsConstants.ELEVATOR_MOTOR_CH_ID);
        this.encoder = provider.getEncoder(ElectronicsConstants.ELEVATOR_ENCODER_CH_A_ID,
                ElectronicsConstants.ELEVATOR_ENCODER_CH_B_ID);
        this.pidHandler = new PIDHandler(1, 0, 0.1, 0, 1, -1.0, 1.0, timer);
    }

    @Override
    public void readSensors() {
        encoderValue = this.encoder.get();
    }

    @Override
    public void update(RobotMode mode) {

        if (this.driver.getDigital(DigitalOperation.ElevatorFirstFloorButton)) {
            targetHeight = 0;
        }
        if (this.driver.getDigital(DigitalOperation.ElevatorSecondFloorButton)) {
            targetHeight = 50;
        }
        if (this.driver.getDigital(DigitalOperation.ElevatorThirdFloorButton)) {
            targetHeight = 100;
        }
        if (this.driver.getDigital(DigitalOperation.ElevatorFourthFloorButton)) {
            targetHeight = 150;
        }
        if (this.driver.getDigital(DigitalOperation.ElevatorFifthFloorButton)) {
            targetHeight = 200;
        }

        this.motor.set(pidHandler.calculatePosition(targetHeight, encoderValue));
    }

    @Override
    public void stop() {
        this.motor.set(0);
        this.encoder.reset();
    }

}
