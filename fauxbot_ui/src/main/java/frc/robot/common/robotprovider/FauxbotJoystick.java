package frc.robot.common.robotprovider;

import java.util.HashMap;
import java.util.Map;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class FauxbotJoystick implements IJoystick
{
    private final Map<Integer, BooleanProperty> buttons;
    private final Map<Integer, DoubleProperty> axes;
    private IntegerProperty povProperty;

    public FauxbotJoystick(int port)
    {
        this.buttons = new HashMap<Integer, BooleanProperty>();
        this.axes = new HashMap<Integer, DoubleProperty>();
        this.povProperty = new SimpleIntegerProperty();

        FauxbotJoystickManager.set(port, this);
    }

    public boolean getRawButton(int buttonNumber)
    {
        synchronized(this)
        {
            if (!this.buttons.containsKey(buttonNumber))
            {
                return false;
            }

            BooleanProperty property = this.buttons.get(buttonNumber);
            boolean result = property.get();
            return result;
        }
    }

    public int getPOV()
    {
        synchronized(this)
        {
            return this.povProperty.get();
        }
    }

    public double getAxis(int relevantAxis)
    {
        synchronized(this)
        {
            if (!this.axes.containsKey(relevantAxis))
            {
                return 0.0;
            }

            return this.axes.get(relevantAxis).get();
        }
    }

    public BooleanProperty getButtonProperty(int buttonNumber)
    {
        synchronized(this)
        {
            if (!this.buttons.containsKey(buttonNumber))
            {
                this.buttons.put(buttonNumber, new SimpleBooleanProperty());
            }

            return this.buttons.get(buttonNumber);
        }
    }

    public DoubleProperty getAxisProperty(int relevantAxis)
    {
        synchronized(this)
        {
            if (!this.axes.containsKey(relevantAxis))
            {
                this.axes.put(relevantAxis, new SimpleDoubleProperty());
            }

            return this.axes.get(relevantAxis);
        }
    }

    public IntegerProperty getPovProperty()
    {
        synchronized(this)
        {
            return this.povProperty;
        }
    }

    public void setButtonProperty(int buttonNumber, boolean value)
    {
        synchronized(this)
        {
            if (!this.buttons.containsKey(buttonNumber))
            {
                this.buttons.put(buttonNumber, new SimpleBooleanProperty());
            }

            BooleanProperty property = this.buttons.get(buttonNumber);
            property.set(value);
        }
    }
}
