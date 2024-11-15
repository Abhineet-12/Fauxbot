package frc.robot.driver.mechanisms;

import frc.lib.driver.IDriver;
import frc.lib.mechanisms.IMechanism;
import frc.lib.robotprovider.IDigitalInput;
import frc.lib.robotprovider.IMotor;
import frc.lib.robotprovider.IRobotProvider;
import frc.lib.robotprovider.RobotMode;
import frc.robot.ElectronicsConstants;
import frc.robot.driver.DigitalOperation;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class GarageDoorMechanism implements IMechanism {
    private final IDriver driver;
    private final IMotor motor;
    private final IDigitalInput isBlocked;
    private final IDigitalInput isOpen;
    private final IDigitalInput isClosed;
    private boolean isMoving = false;

    @Inject
    public GarageDoorMechanism(IDriver driver, IRobotProvider provider) {
        this.driver = driver;
        this.motor = provider.getTalon(ElectronicsConstants.GARAGE_MOTOR_CH_ID);
        this.isBlocked = provider.getDigitalInput(ElectronicsConstants.GARAGE_SENSOR_CH_BLOCKED_ID);
        this.isOpen = provider.getDigitalInput(ElectronicsConstants.GARAGE_SENSOR_CH_OPEN_ID);
        this.isClosed = provider.getDigitalInput(ElectronicsConstants.GARAGE_SENSOR_CH_CLOSED_ID);
    }

    @Override
    public void readSensors() {
        boolean isBlocked = this.isBlocked.get();
        boolean isOpen = this.isOpen.get();
        boolean isClosed = this.isClosed.get();
    }

    @Override
    public void update(RobotMode mode) {
        boolean isBlocked = this.isBlocked.get();
        boolean isOpen = this.isOpen.get();
        boolean isClosed = this.isClosed.get();

        if (this.driver.getDigital(DigitalOperation.GarageDoorToggle)) {
            if ((isClosed || isOpen) && isMoving) {
                System.out.println("Stopped motor!");
                this.motor.set(0);
                isMoving = false;
            }

            if (!isBlocked && isClosed) {
                isMoving = true;
                this.motor.set(1);
            }

            if (!isBlocked && isOpen) {
                isMoving = true;
                this.motor.set(-1);
            }

        }
    }

    @Override
    public void stop() {
        this.motor.set(0);
    }

}
