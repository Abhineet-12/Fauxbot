package frc.robot.mechanisms;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import frc.lib.driver.IDriver;
import frc.lib.mechanisms.IMechanism;
import frc.lib.robotprovider.DoubleSolenoidValue;
import frc.lib.robotprovider.IDoubleSolenoid;
import frc.lib.robotprovider.IRobotProvider;
import frc.lib.robotprovider.ITalonSRX;
import frc.lib.robotprovider.RobotMode;
import frc.lib.robotprovider.TalonSRXControlMode;
import frc.robot.driver.AnalogOperation;
import frc.robot.driver.DigitalOperation;

@Singleton
public class PrinterMechanism implements IMechanism {

    private final IDriver driver;
    private final ITalonSRX xAxisMotor;
    private final ITalonSRX yAxisMotor;
    private final IDoubleSolenoid pen;
    

    @Inject
    public PrinterMechanism(IRobotProvider provider, IDriver driver) {
        
        this.driver = driver;
        this.xAxisMotor = provider.getTalonSRX(0);
        this.yAxisMotor = provider.getTalonSRX(1);
        this.pen = provider.getDoubleSolenoid(null, 7, 8);
        
    }
    
    @Override
    public void readSensors() {
        
    }

    @Override
    public void update(RobotMode mode) {
        double xAxisMotorPower = this.driver.getAnalog(AnalogOperation.xAxisPosition);
        double yAxisMotorPower = this.driver.getAnalog(AnalogOperation.yAxisPosition);
        
        this.xAxisMotor.set(TalonSRXControlMode.MotionMagicPosition, xAxisMotorPower);
        this.yAxisMotor.set(TalonSRXControlMode.MotionMagicPosition, yAxisMotorPower);

        boolean penDownPressed = this.driver.getDigital(DigitalOperation.PenDown);
        boolean penUpPressed = this.driver.getDigital(DigitalOperation.PenUp);

        if (penDownPressed) {
            this.pen.set(DoubleSolenoidValue.Forward);
        }

        if (penUpPressed) {
            this.pen.set(DoubleSolenoidValue.Reverse);
        }

    }

    @Override
    public void stop() {
        this.pen.set(DoubleSolenoidValue.Off);
        this.xAxisMotor.set(0.0);
        this.yAxisMotor.set(0.0);
    }
    
}
