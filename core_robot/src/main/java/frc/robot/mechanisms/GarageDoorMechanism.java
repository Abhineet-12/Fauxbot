package frc.robot.mechanisms;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import frc.lib.driver.IDriver;
import frc.lib.mechanisms.IMechanism;
import frc.lib.robotprovider.IDigitalInput;
import frc.lib.robotprovider.IMotor;
import frc.lib.robotprovider.IRobotProvider;
import frc.lib.robotprovider.RobotMode;
import frc.robot.ElectronicsConstants;
import frc.robot.driver.DigitalOperation;

@Singleton
public class GarageDoorMechanism implements IMechanism {
    private final IDriver driver;
    private final IMotor motor;
    private final IDigitalInput openSensor;
    private final IDigitalInput closedSensor;
    private final IDigitalInput throughBeamSensor;
    private boolean openSensed;
    private boolean closedSensed;
    private boolean throughBeamBroken;
    public enum GarageDoorState
    {
        Opened,
        Opening,
        Closed,
        Closing
    }
    private GarageDoorState state;

    @Inject
    public GarageDoorMechanism(IDriver driver, IRobotProvider provider){
        this.driver = driver;
        this.motor = provider.getTalon(ElectronicsConstants.GARAGE_DOOR_MOTOR);
        this.openSensor = provider.getDigitalInput(ElectronicsConstants.GARAGE_DOOR_SENSOR_OPEN);
        this.closedSensor = provider.getDigitalInput(ElectronicsConstants.GARAGE_DOOR_SENSOR_CLOSED);
        this.throughBeamSensor = provider.getDigitalInput(ElectronicsConstants.GARAGE_DOOR_THROUGH_BEAM);
        this.state = GarageDoorState.Closed;
        this.openSensed = false;
        this.closedSensed = false;
        this.throughBeamBroken = false;
    }

    @Override
    public void readSensors() {
        this.openSensed = this.openSensor.get();
        this.closedSensed = this.closedSensor.get();
        this.throughBeamBroken = this.throughBeamSensor.get();
    }

    @Override
    public void update(RobotMode mode) {
        boolean buttonPressed = driver.getDigital(DigitalOperation.Button);

        if (state == GarageDoorState.Opening)
        {
            if (buttonPressed)
            {
                this.state = GarageDoorState.Closing;
            }
            if (openSensed)
            {
                this.state = GarageDoorState.Opened;
            }
        }
        else if (state == GarageDoorState.Opened)
        {
            if (buttonPressed)
            {
                this.state = GarageDoorState.Closing;
            }
        }
        else if (state == GarageDoorState.Closing)
        {
            if (buttonPressed)
            {
                this.state = GarageDoorState.Opening;
            }
            else if (closedSensed)
            {
                this.state = GarageDoorState.Closed;
            }
            else if (throughBeamBroken)
            {
                this.state = GarageDoorState.Opening;
            }
        }
        else if (state == GarageDoorState.Closed)
        {
            if (buttonPressed)
            {
                this.state = GarageDoorState.Opening;
            }
        }
        if (this.state == GarageDoorState.Opening)
        {
            this.motor.set(1.0);
        }
        else if (this.state == GarageDoorState.Closing)
        {
            this.motor.set(-1.0);
        }
        else
        {
            this.motor.set(0);
        }
    }

    @Override
    public void stop() {
        this.motor.set(0);
    }
}