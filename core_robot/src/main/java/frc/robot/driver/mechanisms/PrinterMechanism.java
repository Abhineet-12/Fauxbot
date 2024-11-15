package frc.robot.driver.mechanisms;

import frc.lib.driver.IDriver;
import frc.lib.mechanisms.IMechanism;
import frc.lib.robotprovider.*;
import frc.robot.ElectronicsConstants;
import frc.robot.driver.AnalogOperation;
import frc.robot.driver.DigitalOperation;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class PrinterMechanism implements IMechanism {

    private final IDriver driver;
    private final ITalonSRX xAxisMotor;
    private final ITalonSRX yAxisMotor;
    private final IDoubleSolenoid penPosition;

    private double xAxisPosition;
    private double yAxisPosition;
    private double targetXAxisPosition = 0.0;
    private double targetYAxisPosition = 0.0;

    @Inject
    public PrinterMechanism(IDriver driver, IRobotProvider provider) {
        this.driver = driver;
        this.xAxisMotor = provider.getTalonSRX(ElectronicsConstants.PRINTER_X_AXIS_MOTOR_CH_ID);
        this.yAxisMotor = provider.getTalonSRX(ElectronicsConstants.PRINTER_Y_AXIS_MOTOR_CH_ID);
        this.penPosition = provider.getDoubleSolenoid(
                PneumaticsModuleType.PneumaticsControlModule,
                ElectronicsConstants.PRINTER_PEN_SOLENOID_FORWARD_CH_ID,
                ElectronicsConstants.PRINTER_PEN_SOLENOID_REVERSE_CH_ID);

        xAxisMotor.setControlMode(TalonSRXControlMode.Position);
        yAxisMotor.setControlMode(TalonSRXControlMode.Position);
        xAxisMotor.setSensorType(TalonSRXFeedbackDevice.QuadEncoder);
        yAxisMotor.setSensorType(TalonSRXFeedbackDevice.QuadEncoder);
        xAxisMotor.setPIDF(0.1, 0.0, 0.1, 0, 0);
        yAxisMotor.setPIDF(0.1, 0.0, 0.1, 0, 0);

    }

    @Override
    public void readSensors() {
        xAxisPosition = this.xAxisMotor.getPosition();
        yAxisPosition = this.yAxisMotor.getPosition();
    }

    @Override
    public void update(RobotMode mode) {
        double joystickXAxis = this.driver.getAnalog(AnalogOperation.PrinterXAxisPosition);
        double joystickYAxis = this.driver.getAnalog(AnalogOperation.PrinterYAxisPosition);

        if (this.driver.getDigital(DigitalOperation.PrinterToggleMode)) {

            if (this.xAxisPosition >= 0 && this.xAxisPosition <= 200) {
                this.targetXAxisPosition += joystickXAxis / 2;
            }
            if (this.yAxisPosition >= 0 && this.yAxisPosition <= 200) {
                this.targetYAxisPosition += joystickYAxis / 2;
            }

            this.targetXAxisPosition = Math.max(0, Math.min(200, this.targetXAxisPosition));
            this.targetYAxisPosition = Math.max(0, Math.min(200, this.targetYAxisPosition));

            System.out.println("Smooth mode on");

        } else {
            targetXAxisPosition = convertRange(joystickXAxis);
            targetYAxisPosition = convertRange(joystickYAxis);
        }

        //Set the position after calculations

        this.xAxisMotor.set(this.targetXAxisPosition);
        this.yAxisMotor.set(this.targetYAxisPosition);

        //Pen up and down logic

        if (this.driver.getDigital(DigitalOperation.PrinterPenDown)) {
            this.penPosition.set(DoubleSolenoidValue.Forward);
        }

        if (this.driver.getDigital(DigitalOperation.PrinterPenUp)) {
            this.penPosition.set(DoubleSolenoidValue.Reverse);
        }
    }

    double convertRange(double value) {
        return (value + 1) * 100;
    }

    @Override
    public void stop() {
        this.xAxisMotor.set(0);
        this.yAxisMotor.set(0);
        this.penPosition.set(DoubleSolenoidValue.Off);
    }

    public double getXAxisPosition() {
        return this.xAxisPosition;
    }

    public double getYAxisPosition() {
        return this.yAxisPosition;
    }
}