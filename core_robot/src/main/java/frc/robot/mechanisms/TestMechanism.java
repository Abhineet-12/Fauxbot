package frc.robot.mechanisms;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import frc.lib.driver.IDriver;
import frc.lib.mechanisms.IMechanism;
import frc.lib.robotprovider.DoubleSolenoidValue;
import frc.lib.robotprovider.IDoubleSolenoid;
import frc.lib.robotprovider.IEncoder;
import frc.lib.robotprovider.IMotor;
import frc.lib.robotprovider.IRobotProvider;
import frc.lib.robotprovider.RobotMode;
import frc.robot.driver.AnalogOperation;
import frc.robot.driver.DigitalOperation;

@Singleton
public class TestMechanism implements IMechanism {
    
    private final IDriver driver;
    private final IMotor motor;
    private final IDoubleSolenoid doubleSolenoid;
    private final IEncoder encoder;
    double encoderReading;
    
    
    @Inject
    public TestMechanism(IRobotProvider provider, IDriver driver) {
        
        this.driver = driver;

        this.motor = provider.getTalon(0);
        // this provider method is a method that tells the motor what type it is, and what pwm channel it is connected to.

        this.doubleSolenoid = provider.getDoubleSolenoid(null, 7, 8);
        // this provider method tells the module type of the double solenoid, and the forward and reverse channel that it is connected to.

        this.encoder = provider.getEncoder(0, 1);
        // this provider method allows the variable to get the information of the encoder from the digital IO channels in the parameters.
    
    }
    @Override
    public void readSensors() {
        
        this.encoderReading = this.encoder.get();
        // this sets the doulble known as encoderReading to whatever the encoder reads from the physical encoder.
    
    }

    @Override
    public void update(RobotMode mode) {

        double stickValue = this.driver.getAnalog(AnalogOperation.TestAnalogOperation);
        // This sets the value that is obtained from the Analog Operations file to a double -1.0 to 1.0.
        // The button map tell this double what value it is going to be set to.

        boolean buttonPressed = this.driver.getDigital(DigitalOperation.TestButton);
        // This boolean sets the value to true when the DigitalOperation is activated.
        // The button map will change the value of the button to true if someone activates it.
        
        this.motor.set(stickValue);
        // The motor value gets set to whatever the double is currently.
        
        if (buttonPressed) {
            this.doubleSolenoid.set(DoubleSolenoidValue.Forward);
        }
        
        if (!buttonPressed) {
            this.doubleSolenoid.set(DoubleSolenoidValue.Reverse);
        }
        // These if statments check for when the button is pressed, it will set the double solenoid to forward.
        // If the button is not pressed, the double solenoid goes back into it's reversed position.

    }

    @Override
    public void stop() {

        this.motor.set(0.0);
        // When the program stops, the power gets set to 0.0, stopping the motor.
        
        this.doubleSolenoid.set(DoubleSolenoidValue.Off);
        // When the program stops, the double solenoid turns off.
    }
    
}
