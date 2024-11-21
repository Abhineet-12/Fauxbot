package frc.robot.mechanisms;

import javax.inject.Singleton;

import com.google.inject.Inject;

import frc.lib.driver.IDriver;
import frc.lib.mechanisms.IMechanism;
import frc.lib.robotprovider.DoubleSolenoidValue;
import frc.lib.robotprovider.IDoubleSolenoid;
import frc.lib.robotprovider.IRobotProvider;
import frc.lib.robotprovider.ITalonSRX;
import frc.lib.robotprovider.PneumaticsModuleType;
import frc.lib.robotprovider.TalonXControlMode;
import frc.lib.robotprovider.TalonXFeedbackDevice;
import frc.robot.ElectronicsConstants;
import frc.robot.driver.AnalogOperation;
import frc.robot.driver.DigitalOperation;

@Singleton
public class ShooterMechanism implements IMechanism {
    private final IDriver driver;
    private final ITalonSRX hoodMotor;
    private final ITalonSRX flywheelMotor;
    private final IDoubleSolenoid pneumaticKicker;
    private double hoodPosition = 0.0;
    private boolean kickerOn = false;
    @Inject
    public ShooterMechanism(IRobotProvider provider, IDriver driver) {
        this.driver = driver;
        this.hoodMotor = provider.getTalonSRX(0);
        this.hoodMotor.setPIDF(0.1, 0.1, 0.1, 0.1, 1);
        this.hoodMotor.setControlMode(TalonXControlMode.Position);
        this.hoodMotor.setSensorType(TalonXFeedbackDevice.QuadEncoder);
        this.hoodMotor.setInvertOutput(true);
        this.flywheelMotor = provider.getTalonSRX(1);
        this.flywheelMotor.setPIDF(0.1, 0.1, 0.1, 0.1, 1);

        this.pneumaticKicker = provider.getDoubleSolenoid(PneumaticsModuleType.PneumaticsControlModule, ElectronicsConstants.SHOOTER_DOUBLE_SOLENOID_FORWARD, ElectronicsConstants.SHOOTER_DOUBLE_SOLENOID_REVERSE);
        
    }

    @Override
    public void readSensors()
    {
       
    }

    @Override
    public void update()
    {
        hoodPosition = this.driver.getAnalog(AnalogOperation.ShooterAngle);
        boolean fireButtonPressed = this.driver.getDigital(DigitalOperation.ShooterFire);
        boolean flywheelOn = this.driver.getDigital(DigitalOperation.ShooterSpin);
        double scaledHoodPosition = (hoodPosition + 1) * 45;

        if (kickerOn) {
            pneumaticKicker.set(DoubleSolenoidValue.Reverse);
            kickerOn = false;
        }
        if (flywheelOn) {
            flywheelMotor.set(100);
        }
        else {
            flywheelMotor.set(0);
        }
        if (fireButtonPressed) {
            pneumaticKicker.set(DoubleSolenoidValue.Forward);
            kickerOn = true;
        }
        hoodMotor.set(scaledHoodPosition);
        
    }

    @Override
    public void stop()
    {
        this.hoodMotor.set(0.0);
        this.flywheelMotor.set(0.0);
        this.pneumaticKicker.set(DoubleSolenoidValue.Off);
    }

}
