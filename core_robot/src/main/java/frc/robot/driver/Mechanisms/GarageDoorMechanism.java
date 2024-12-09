package frc.robot.driver.Mechanisms;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import frc.lib.driver.IDriver;
import frc.lib.mechanisms.IMechanism;
import frc.lib.robotprovider.DoubleSolenoidValue;
import frc.lib.robotprovider.IDigitalInput;
import frc.lib.robotprovider.IDoubleSolenoid;
import frc.lib.robotprovider.IMotor;
import frc.lib.robotprovider.IRobotProvider;
import frc.lib.robotprovider.PneumaticsModuleType;
import frc.lib.robotprovider.RobotMode;
import frc.robot.driver.AnalogOperation;
import frc.robot.driver.DigitalOperation;
@Singleton
public class GarageDoorMechanism implements IMechanism{
    private IDigitalInput throughBeamSensor;
    private IDigitalInput openSensor;
    private IDigitalInput closedSensor;
    private garageDoorState state;
    private IDriver driver;
    private boolean openSensorOutput;
    private boolean closedSensorOutput;
    private boolean throughBeamSensorOutput;
    private IMotor motor;
    public enum garageDoorState {
        open,
        opening,
        close,
        closing,
    }

    @Inject
    public GarageDoorMechanism (IDriver driver, IRobotProvider provider) {
        this.openSensor = provider.getDigitalInput(1);
        this.closedSensor = provider.getDigitalInput(2);
        this.throughBeamSensor = provider.getDigitalInput(0);
        this.driver = driver;
        this.openSensorOutput = false;
        this.motor = provider.getTalon(0);
        state = garageDoorState.close;
    }
    
    @Override
    public void readSensors() {
        // TODO Auto-generated method stub
        openSensorOutput = this.openSensor.get();
        closedSensorOutput = this.closedSensor.get();
        throughBeamSensorOutput = this.throughBeamSensor.get();

        
    }
    @Override
    public void update(RobotMode mode) {
        if (state == garageDoorState.closing && closedSensorOutput) {
            state = garageDoorState.close;
        }
        if (state == garageDoorState.close && driver.getDigital(DigitalOperation.ButtonPress)) {
            state = garageDoorState.opening;
        }
        if (state == garageDoorState.opening && openSensorOutput) {
            state = garageDoorState.open;
        }
        if (state == garageDoorState.open && driver.getDigital(DigitalOperation.ButtonPress)) {
            state = garageDoorState.closing;
        }
        if (state == garageDoorState.closing && throughBeamSensorOutput) {
            state = garageDoorState.opening;
        }
        if (state == garageDoorState.opening && driver.getDigital(DigitalOperation.ButtonPress)) {
            state = garageDoorState.closing;
        }
        if (state == garageDoorState.closing && driver.getDigital(DigitalOperation.ButtonPress)) {
            state = garageDoorState.opening;
        }

        if (state == garageDoorState.open) {
            this.motor.set(0);
        }
        if (state == garageDoorState.close) {
            this.motor.set(0);
        }
        if (state == garageDoorState.opening) {
            this.motor.set(1);
        }
        if (state == garageDoorState.closing) {
            this.motor.set(-1);
        }
     
    }
    @Override
    public void stop() {
        // TODO Auto-generated method stub
        motor.set(0);
    }
}
