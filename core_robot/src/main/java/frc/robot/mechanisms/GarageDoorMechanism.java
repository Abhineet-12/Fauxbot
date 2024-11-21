package frc.robot.mechanisms;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import frc.lib.driver.IDriver;
import frc.lib.mechanisms.IMechanism;
import frc.lib.robotprovider.IDigitalInput;
import frc.lib.robotprovider.IMotor;
import frc.lib.robotprovider.IRobotProvider;
import frc.lib.robotprovider.ITalonFX;
import frc.robot.driver.DigitalOperation;

@Singleton
public class GarageDoorMechanism implements IMechanism{
    private final IDigitalInput openSensor;
    private final IDigitalInput closedSensor;
    private final IDigitalInput throughBeamSensor;
    private final IMotor motor;
    private final IDriver driver;
    private boolean isOpen;
    private boolean isClosed;
    private boolean throughBeamBroken;

    @Inject
    public GarageDoorMechanism(IRobotProvider provider, IDriver driver)
    {
        this.driver = driver;
        this.motor = provider.getTalon(0);
        this.openSensor = provider.getDigitalInput(1);
        this.closedSensor = provider.getDigitalInput(2);
        this.throughBeamSensor = provider.getDigitalInput(0);
    }

    public enum GarageDoorState
    {
        Opened,
        Opening,
        Closed,
        Closing
    }
    private GarageDoorState currentState = GarageDoorState.Closed;

    @Override
    public void readSensors()
    {
        this.isOpen = this.openSensor.get();
        this.isClosed = this.closedSensor.get();
        this.throughBeamBroken = this.throughBeamSensor.get();
    }

    @Override
    public void update()
    {
        boolean buttonPressed = driver.getDigital(DigitalOperation.garageButton);
        if (currentState == GarageDoorState.Closed) {
            if (buttonPressed) {
                currentState = GarageDoorState.Opening;
            }
        }
        else if (currentState == GarageDoorState.Closing) {
            if (isClosed) {
                currentState = GarageDoorState.Closed;
            }
            else if (throughBeamBroken || buttonPressed) {
                currentState = GarageDoorState.Opening;
            }
        }
        else if (currentState == GarageDoorState.Opening) {
            if (isOpen) {
                currentState = GarageDoorState.Opened;
            }
            else if (buttonPressed) {
                currentState = GarageDoorState.Closing;
            }
        }
        else if (currentState == GarageDoorState.Opened) {
            if (buttonPressed) {
                currentState = GarageDoorState.Closing;
            }
        }
        
        if (currentState == GarageDoorState.Closing) {
            motor.set(-1.0);
        }
        else if (currentState == GarageDoorState.Opening) {
            motor.set(1.0);
        }
        else {
            motor.set(0);
        }
    }

    @Override
    public void stop()
    {
        this.motor.set(0.0);    
    }
    
}
