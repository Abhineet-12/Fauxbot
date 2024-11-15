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
    private final IDigitalInput thruBeamSensor;
    private final IDigitalInput openSensor;
    private final IDigitalInput closedSensor;
    private States state;

    boolean isBlocked;
    boolean isOpen;
    boolean isClosed;
    boolean toggled;
    boolean reversing;

    @Inject
    public GarageDoorMechanism(IDriver driver, IRobotProvider provider) {
        this.driver = driver;
        this.motor = provider.getTalon(ElectronicsConstants.GARAGE_MOTOR_CH_ID);
        this.thruBeamSensor = provider.getDigitalInput(ElectronicsConstants.GARAGE_SENSOR_CH_BLOCKED_ID);
        this.openSensor = provider.getDigitalInput(ElectronicsConstants.GARAGE_SENSOR_CH_OPEN_ID);
        this.closedSensor = provider.getDigitalInput(ElectronicsConstants.GARAGE_SENSOR_CH_CLOSED_ID);

        this.state = States.Closed;
    }

    private enum States {
        Open, Closed, Opening, Closing;
    }

    @Override
    public void readSensors() {
        isBlocked = this.thruBeamSensor.get();
        isOpen = this.openSensor.get();
        isClosed = this.closedSensor.get();
    }

    @Override
    public void update(RobotMode mode) {
        toggled = this.driver.getDigital(DigitalOperation.GarageDoorToggle);

        if (this.state == States.Open) {
            reversing = false;
            if (toggled && !isBlocked) {
                this.state = States.Closing;
            }
        } else if (this.state == States.Closed) {
            reversing = false;
            if (toggled && !isBlocked) {
                this.state = States.Opening;
            }
        } else if (this.state == States.Opening) {
            if (isBlocked && !reversing) {
                this.state = States.Closing;
                reversing = true;
            }
            if (toggled) {
                this.state = States.Closing;
            }
            if (isOpen) {
                this.state = States.Open;
            }
        } else if (this.state == States.Closing) {
            if (isBlocked && !reversing) {
                this.state = States.Opening;
                reversing = true;
            }
            if (toggled) {
                this.state = States.Opening;
            }
            if (isClosed) {
                this.state = States.Closed;
            }
        }

        switch (this.state) {
            case Open, Closed:
                this.motor.set(0);
                break;
            case Opening:
                this.motor.set(1);
                break;
            case Closing:
                this.motor.set(-1);
                break;
        }

    }

    @Override
    public void stop() {
        this.motor.set(0);
    }

}
