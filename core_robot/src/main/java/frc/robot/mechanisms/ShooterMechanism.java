package frc.robot.mechanisms;

import java.awt.Button;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import frc.lib.driver.IDriver;
import frc.lib.mechanisms.IMechanism;
import frc.lib.robotprovider.DoubleSolenoidValue;
import frc.lib.robotprovider.IDoubleSolenoid;
import frc.lib.robotprovider.IMotor;
import frc.lib.robotprovider.IRobotProvider;
import frc.lib.robotprovider.RobotMode;
import frc.robot.driver.AnalogOperation;
import frc.robot.driver.DigitalOperation;

@Singleton
public class ShooterMechanism implements IMechanism{
    
    private final IMotor hoodMotor;
    private final IDriver driver;
    private final IMotor flywheelMotor;
    private final IDoubleSolenoid kicker;

    @Inject
    public ShooterMechanism(IRobotProvider provider, IDriver driver) {
        this.hoodMotor = provider.getTalonSRX(0);
        this.driver = driver;
        this.flywheelMotor = provider.getTalonSRX(1);
        this.kicker = provider.getDoubleSolenoid(null, 7, 8);

    }

    @Override
    public void readSensors() {
        
    }

    @Override
    public void update(RobotMode mode) {
        double desiredHoodPos = this.driver.getAnalog(AnalogOperation.HoodAnglePosition);
        double currentHoodPos;
        
        boolean fire = this.driver.getDigital(DigitalOperation.FireButton);
        boolean spin = this.driver.getDigital(DigitalOperation.SpinButton);

        if (spin) {
            this.flywheelMotor.set(1.0);
        }
        if (spin) {
            this.flywheelMotor.set(0.0);
        }
        if (fire) {
            this.kicker.set(DoubleSolenoidValue.Forward);
        }
        if (!fire) {
            this.kicker.set(DoubleSolenoidValue.Reverse);
        }
        
    }

    @Override
    public void stop() {
        this.flywheelMotor.set(0.0);
        this.kicker.set(DoubleSolenoidValue.Off);
        this.hoodMotor.set(0.0);
    }
    
}
