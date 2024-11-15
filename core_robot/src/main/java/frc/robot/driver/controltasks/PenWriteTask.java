package frc.robot.driver.controltasks;

import frc.robot.driver.DigitalOperation;

public class PenWriteTask extends CompositeOperationTask {

    public final static DigitalOperation[] possibleOperations = new DigitalOperation[]{
            DigitalOperation.PrinterPenUp,
            DigitalOperation.PrinterPenDown
    };

    public PenWriteTask(boolean penUp) {
        super(penUp ? DigitalOperation.PrinterPenUp : DigitalOperation.PrinterPenDown, possibleOperations);
    }
}
