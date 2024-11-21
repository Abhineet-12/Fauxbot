package frc.robot.mechanisms;

import javax.inject.Inject;
import javax.inject.Singleton;

import frc.lib.controllers.PIDHandler;
import frc.lib.driver.IDriver;
import frc.lib.mechanisms.IMechanism;
import frc.lib.robotprovider.IEncoder;
import frc.lib.robotprovider.IMotor;
import frc.lib.robotprovider.IRobotProvider;
import frc.lib.robotprovider.ITimer;
import frc.robot.ElectronicsConstants;
import frc.robot.driver.DigitalOperation;

@Singleton
public class ElevatorMechanism implements IMechanism{
    private final IMotor motor;
    private final PIDHandler pid;
    private final IDriver driver;
    private final IEncoder encoder;
    private double currentHeight;
    private double targetHeight;

    @Inject
    public ElevatorMechanism(IRobotProvider provider, IDriver driver, ITimer timer) {
        this.driver = driver;
        this.motor = provider.getTalon(ElectronicsConstants.ELEVATOR_MOTOR);
        this.pid = new PIDHandler(1, 0, 0.1, 0, 1.0, -1.0, 1.0, timer);
        this.encoder = provider.getEncoder(ElectronicsConstants.ELEVATOR_SENSOR_A, ElectronicsConstants.ELEVATOR_SENSOR_B);
        this.encoder.setDistancePerPulse(1.0);
    }

    @Override
    public void readSensors()
    {
        currentHeight = this.encoder.getDistance();
    }

    @Override
    public void update()
    {
        if (driver.getDigital(DigitalOperation.FirstFloor)) {
            targetHeight = 0.0;
        }
        if (driver.getDigital(DigitalOperation.SecondFloor)) {
            targetHeight = 50.0;
        }
        if (driver.getDigital(DigitalOperation.ThirdFloor)) {
            targetHeight = 100.0;
        }
        if (driver.getDigital(DigitalOperation.FourthFloor)) {
            targetHeight = 150.0;
        }
        if (driver.getDigital(DigitalOperation.FifthFloor)) {
            targetHeight = 200.0;
        }

        double power = this.pid.calculatePosition(targetHeight, currentHeight);
        this.motor.set(power);
    }

    @Override
    public void stop()
    {
        motor.set(0.0);
    }
    
}
