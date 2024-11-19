package frc.robot.mechanisms;

import frc.robot.driver.DigitalOperation;

import com.google.inject.Inject;
import com.google.inject.Singleton;

// import frc.lib.controllers.PIDHandler;
import frc.lib.driver.IDriver;
import frc.lib.mechanisms.IMechanism;
import frc.lib.robotprovider.IEncoder;
import frc.lib.robotprovider.IMotor;
import frc.lib.robotprovider.IRobotProvider;
import frc.lib.robotprovider.RobotMode;

@Singleton
public class ElevatorMechanism implements IMechanism {

    private final IDriver driver;
    private final IMotor motor;
    private final IEncoder encoder;
    // private final PIDHandler pid;

    double targetPos;
    double currentPos;
    
    @Inject
    public ElevatorMechanism(IRobotProvider provider, IDriver driver) {
        
        this.driver = driver;
        this.motor = provider.getTalon(0);
        this.encoder = provider.getEncoder(0, 1);

    }
    
    @Override
    public void readSensors() {
        this.currentPos = this.encoder.get();
    }

    @Override
    public void update(RobotMode mode) {
        boolean firstFloorButtonPressed = this.driver.getDigital(DigitalOperation.FirstFloorButton);
        boolean secondFloorButtonPressed = this.driver.getDigital(DigitalOperation.SecondFloorButton);
        boolean thirdFloorButtonPressed = this.driver.getDigital(DigitalOperation.ThirdFloorButton);
        boolean fourthFloorButtonPressed = this.driver.getDigital(DigitalOperation.FourthFloorButton);
        boolean fifthFloorButtonPressed = this.driver.getDigital(DigitalOperation.FifthFloorButton);

        if (firstFloorButtonPressed) {
            this.targetPos = 0.0;
        }
        if (secondFloorButtonPressed) {
            this.targetPos = 50.0;
        }
        if (thirdFloorButtonPressed) {
            this.targetPos = 100.0;
        }
        if (fourthFloorButtonPressed) {
            this.targetPos = 150.0;
        }
        if (fifthFloorButtonPressed) {
            this.targetPos = 200.0;
        }

    }

    @Override
    public void stop() {
        this.motor.set(0.0);
    }
    
}