package frc.robot.mechanisms;

import java.util.ServiceLoader;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.sun.java.swing.action.OpenAction;

import frc.lib.driver.IDriver;
import frc.lib.mechanisms.IMechanism;
import frc.lib.robotprovider.IDigitalInput;
import frc.lib.robotprovider.IMotor;
import frc.lib.robotprovider.IRobotProvider;
import frc.lib.robotprovider.RobotMode;
import frc.robot.driver.AnalogOperation;
import frc.robot.driver.DigitalOperation;
import frc.robot.mechanisms.GaragedoorMechanism.garageDoorState;
import frc.robot.mechanisms.GaragedoorMechanism.garageDoorState;

@Singleton
public class GaragedoorMechanism implements IMechanism {

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
    public GaragedoorMechanism(IRobotProvider provider, IDriver driver) {
        this.driver = driver;
        this.state = garageDoorState.open;
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
        if (button) {
            if (this.state == garageDoorState.open) {
                this.state = garageDoorState.closing;
            }
            if (this.state == garageDoorState.closed) {
                this.state = garageDoorState.opening;
            }
            if (this.state == garageDoorState.closing) {
                this.state = garageDoorState.opening;
            }
            if (this.state == garageDoorState.opening) {
                this.state = garageDoorState.closing;
            }
        }
        if (throughBeamBroken && this.state == garageDoorState.closing) {
            this.state = garageDoorState.opening;
        }
        if (openSensorTriggered) {
            this.state = garageDoorState.open;
        }
        if (closedSensorTriggered) {
            this.state = garageDoorState.closed;
        }
    }

    @Override
    public void stop() {
        this.garageMotor.set(0.0);
    }
    
}