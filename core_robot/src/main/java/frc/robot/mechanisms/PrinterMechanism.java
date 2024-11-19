package frc.robot.mechanisms;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import frc.lib.driver.IDriver;
import frc.lib.mechanisms.IMechanism;
import frc.lib.robotprovider.IRobotProvider;
import frc.lib.robotprovider.RobotMode;

@Singleton
public class PrinterMechanism implements IMechanism {

    private final IDriver driver;

    @Inject
    public PrinterMechanism(IRobotProvider provider, IDriver driver) {
        this.driver = driver;
    }
    
    @Override
    public void readSensors() {
        
    }

    @Override
    public void update(RobotMode mode) {
        
    }

    @Override
    public void stop() {
        
    }
    
}
