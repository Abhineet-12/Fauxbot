package frc.robot.driver.controltasks;

import frc.lib.helpers.Helpers;
import frc.lib.robotprovider.ITimer;
import frc.robot.driver.AnalogOperation;
import frc.robot.driver.mechanisms.PrinterMechanism;

public class PenMoveTask extends ControlTaskBase {

    private final Double xAxis;
    private final Double yAxis;
    private PrinterMechanism printerMechanism;
    private ITimer timer;
    private double endTime = 0;

    public PenMoveTask(Double xAxis, Double yAxis) {
        this.xAxis = xAxis;
        this.yAxis = yAxis;
    }

    @Override
    public void begin() {
        printerMechanism = this.getInjector().getInstance(PrinterMechanism.class);
        this.timer = this.getInjector().getInstance(ITimer.class);
    }

    @Override
    public void update() {
        this.setAnalogOperationState(AnalogOperation.PrinterXAxisPosition, (xAxis / 100) - 1);
        this.setAnalogOperationState(AnalogOperation.PrinterYAxisPosition, (yAxis / 100) - 1);
    }

    @Override
    public void end() {

    }

    @Override
    public boolean hasCompleted() {
        if (Helpers.RoughEquals(xAxis, printerMechanism.getXAxisPosition(), 1) && Helpers.RoughEquals(yAxis, printerMechanism.getYAxisPosition(), 1) && endTime == 0) {
            endTime = timer.get();
        }

        return timer.get() >= endTime + 3 && endTime != 0;
        //return Helpers.RoughEquals(xAxis, printerMechanism.getXAxisPosition(), 1) && Helpers.RoughEquals(yAxis, printerMechanism.getYAxisPosition(), 1);
    }
}
