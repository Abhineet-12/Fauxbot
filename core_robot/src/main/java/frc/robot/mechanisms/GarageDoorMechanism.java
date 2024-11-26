package frc.robot.mechanisms;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import frc.lib.driver.IDriver;
import frc.lib.mechanisms.IMechanism;
import frc.lib.robotprovider.IDigitalInput;
import frc.lib.robotprovider.IMotor;
import frc.lib.robotprovider.IRobotProvider;
import frc.lib.robotprovider.RobotMode;
import frc.robot.driver.DigitalOperation;

@Singleton
public class GarageDoorMechanism implements IMechanism {

    private final IMotor garageMotor;
    private enum garageDoorState {
        open,
        opening,
        closed,
        closing
    }
    private garageDoorState state;
    private final IDriver driver;
    private final IDigitalInput throughBeamSensor;
    private final IDigitalInput openSensor;
    private final IDigitalInput closedSensor;

    boolean openSensorTriggered;
    boolean closedSensorTriggered;
    boolean throughBeamBroken;

    @Inject
    public GarageDoorMechanism(IRobotProvider provider, IDriver driver) {
        this.driver = driver;
        this.state = garageDoorState.closed;
        this.garageMotor = provider.getTalon(0);
        this.openSensor = provider.getDigitalInput(1);
        this.closedSensor = provider.getDigitalInput(2);
        this.throughBeamSensor = provider.getDigitalInput(0);
    }

    @Override
    public void readSensors() {
        this.openSensorTriggered = openSensor.get();
        this.closedSensorTriggered = closedSensor.get();
        this.throughBeamBroken = throughBeamSensor.get();
    }

    @Override
    public void update(RobotMode mode) {
        boolean button = this.driver.getDigital(DigitalOperation.Button);
        
        
        if (this.state == garageDoorState.open) {
            this.garageMotor.set(0.0);
            if (button) {
                this.state = garageDoorState.closing;
            }
        }
        if (this.state == garageDoorState.closed) {
            this.garageMotor.set(0.0);
            if (button) {
                this.state = garageDoorState.opening;
            }
        }
        if (this.state == garageDoorState.opening) {
            this.garageMotor.set(-1.0);
            if (button) {
                this.state = garageDoorState.closing;
            }
            if (openSensorTriggered) {
                this.state = garageDoorState.open;
            }
        }
        if (this.state == garageDoorState.closing) {
            this.garageMotor.set(1.0);
            if (button || throughBeamBroken) {
                this.state = garageDoorState.opening;
            }
            if (closedSensorTriggered) {
                this.state = garageDoorState.closed;
            }
        }
    }

    @Override
    public void stop() {
        this.garageMotor.set(0.0);
    }
    
}