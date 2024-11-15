package frc.robot.mechanisms;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import frc.lib.driver.*;
import frc.lib.mechanisms.IMechanism;
import frc.lib.robotprovider.*;
import frc.robot.driver.*;

@Singleton
public class GarageDoorMechanism implements IMechanism
{

private IDigitalInput throughBeamSensor;
private IDigitalInput closedSensor;
private IDigitalInput openSensor;
private IDriver driver;
private IMotor garageDoorMotor;
private boolean isOpen;
private boolean isClosed;
private boolean throughBeamBroken;
private enum garageDoorState {
    open,
    opening,
    closed,
    closing
}

private garageDoorState state;

@Inject
public GarageDoorMechanism (IDriver driver, IRobotProvider provider) {
    this.openSensor = provider.getDigitalInput(1);
    this.closedSensor = provider.getDigitalInput(0);
    this.throughBeamSensor = provider.getDigitalInput(2);
    this.driver = driver;
    this.garageDoorMotor = provider.getTalon(0);
    this.isOpen = false;
    this.isClosed = true;
    this.throughBeamBroken = false;
    this.state = garageDoorState.closed;

}




@Override
public void readSensors() {
    this.isOpen = openSensor.get();
    this.isClosed = closedSensor.get();
    this.throughBeamBroken = throughBeamSensor.get();
    
}
@Override
public void update(RobotMode mode) {
    if (this.driver.getDigital(DigitalOperation.garageButton) && state == garageDoorState.open) 
    {
        state = garageDoorState.closing;
    }

    else if (this.driver.getDigital(DigitalOperation.garageButton) && state == garageDoorState.closed) 
    {
        state = garageDoorState.opening;
    }

    else if (this.driver.getDigital(DigitalOperation.garageButton) && state == garageDoorState.closing) 
    {
        state = garageDoorState.opening;
    }

    else if (this.driver.getDigital(DigitalOperation.garageButton) && state == garageDoorState.opening) 
    {
        state = garageDoorState.closing;
    }

    else if (isOpen == true && state == garageDoorState.opening) 
    {
        state = garageDoorState.open;
    }
    else if (isClosed == true && state == garageDoorState.closing) 
    {
        state = garageDoorState.closed;

    }
    else if (throughBeamBroken == true && state == garageDoorState.closing) 
    {
        state = garageDoorState.opening;

    }
    else if (throughBeamBroken == true && state == garageDoorState.opening) 
    {
        state = garageDoorState.closing;

    }



    // After state logic
    if (state == garageDoorState.closing) {
        this.garageDoorMotor.set(-1);
    }
    if (state == garageDoorState.opening) {
        this.garageDoorMotor.set(1);
    }
    if (state == garageDoorState.closed || state == garageDoorState.open) {
        this.garageDoorMotor.set(0);
    }


}
@Override
public void stop() {
   this.garageDoorMotor.set(0.0);
}
    
}
