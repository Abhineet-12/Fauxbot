package org.usfirst.frc.team1318.robot;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.usfirst.frc.team1318.robot.driver.ButtonMap;
import org.usfirst.frc.team1318.robot.driver.Driver;
import org.usfirst.frc.team1318.robot.driver.MacroOperation;
import org.usfirst.frc.team1318.robot.driver.Operation;
import org.usfirst.frc.team1318.robot.driver.buttons.ButtonType;
import org.usfirst.frc.team1318.robot.driver.descriptions.AnalogOperationDescription;
import org.usfirst.frc.team1318.robot.driver.descriptions.DigitalOperationDescription;
import org.usfirst.frc.team1318.robot.driver.descriptions.MacroOperationDescription;
import org.usfirst.frc.team1318.robot.driver.descriptions.OperationDescription;
import org.usfirst.frc.team1318.robot.driver.descriptions.OperationType;
import org.usfirst.frc.team1318.robot.driver.descriptions.UserInputDevice;
import org.usfirst.frc.team1318.robot.driver.states.AnalogOperationState;
import org.usfirst.frc.team1318.robot.driver.user.UserDriver;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.JoystickManager;
import edu.wpi.first.wpilibj.MotorBase;
import edu.wpi.first.wpilibj.MotorManager;
import edu.wpi.first.wpilibj.SensorBase;
import edu.wpi.first.wpilibj.SensorManager;
import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Fauxbot extends Application
{
    @SuppressWarnings("serial")
    private final Map<Integer, String> sensorNameMap = new HashMap<Integer, String>()
    {
        {
            this.put(0, "Through-Beam sensor:");
            this.put(1, "Open sensor:");
            this.put(2, "Closed sensor:");
        }
    };

    @SuppressWarnings("serial")
    private final Map<Integer, String> motorNameMap = new HashMap<Integer, String>()
    {
        {
            this.put(0, "Door motor:");
        }
    };

    private final RealWorldSimulator simulator;

    private final Driver driver;
    private final ComponentManager components;
    private final ControllerManager controllers;
    private final FauxbotRunner runner;
    private final Thread runnerThread;

    public Fauxbot()
    {
        super();

        this.components = new ComponentManager();
        this.driver = new UserDriver(this.components);
        this.controllers = new ControllerManager(this.components);

        this.controllers.setDriver(this.driver);
        this.simulator = new RealWorldSimulator();
        this.runner = new FauxbotRunner(this.controllers, this.driver, this.simulator);
        this.runnerThread = new Thread(this.runner);
    }

    @Override
    public void start(Stage primaryStage) throws Exception
    {
        primaryStage.setTitle("Fauxbot");

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        int rowIndex = 0;

        Text buttonsTitle = new Text("Buttons");
        buttonsTitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        grid.add(buttonsTitle, 0, rowIndex++, 2, 1);
        for (Operation op : Operation.values())
        {
            OperationDescription description = ButtonMap.OperationSchema.get(op);

            if (description != null)
            {
                int joystickPort = -1;
                if (description.getUserInputDevice() == UserInputDevice.Driver)
                {
                    joystickPort = ElectronicsConstants.JOYSTICK_DRIVER_PORT;
                }
                else if (description.getUserInputDevice() == UserInputDevice.CoDriver)
                {
                    joystickPort = ElectronicsConstants.JOYSTICK_CO_DRIVER_PORT;
                }

                if (joystickPort != -1)
                {
                    final Joystick joystick = JoystickManager.get(joystickPort);
                    if (joystick != null)
                    {
                        int thisRowIndex = rowIndex;
                        rowIndex++;

                        Label operationNameLabel = new Label(op.toString());
                        grid.add(operationNameLabel, 0, thisRowIndex);

                        if (description.getType() == OperationType.Digital)
                        {
                            DigitalOperationDescription digitalDescription = (DigitalOperationDescription)description;
                            int buttonNumber = digitalDescription.getUserInputDeviceButton().Value;
                            if (digitalDescription.getButtonType() == ButtonType.Click)
                            {
                                Button operationButton = new Button("Click");
                                operationButton.setOnMouseClicked(
                                    (MouseEvent event) ->
                                    {
                                        joystick.getButtonProperty(buttonNumber).set(true);
                                    });

                                grid.add(operationButton, 1, thisRowIndex);
                            }
                            else if (digitalDescription.getButtonType() == ButtonType.Toggle)
                            {
                                CheckBox operationCheckBox = new CheckBox();
                                grid.add(operationCheckBox, 1, thisRowIndex);
                                Bindings.bindBidirectional(joystick.getButtonProperty(buttonNumber), operationCheckBox.selectedProperty());
                            }
                            else if (digitalDescription.getButtonType() == ButtonType.Simple)
                            {
                                Button operationButton = new Button("Simple");
                                operationButton.setOnMouseClicked(
                                    (MouseEvent event) ->
                                    {
                                        joystick.getButtonProperty(buttonNumber).set(true);;
                                    });

                                grid.add(operationButton, 1, thisRowIndex);
                            }
                        }
                        else if (description.getType() == OperationType.Analog)
                        {
                            AnalogOperationDescription analogDescription = (AnalogOperationDescription)description;

                            Slider analogSlider = new Slider();
                            analogSlider.setMin(-1.0);
                            analogSlider.setMax(1.0);
                            analogSlider.setBlockIncrement(0.1);
                            analogSlider.setShowTickMarks(true);

                            grid.add(analogSlider, 1, thisRowIndex);
                            Bindings.bindBidirectional(joystick.getAxisProperty(AnalogOperationState.fromAxis(analogDescription.getUserInputDeviceAxis())), analogSlider.valueProperty());
                        }
                    }
                }
            }
        }

        // add a spacer:
        rowIndex++;

        if (MacroOperation.values().length > 0)
        {
            Text macrosTitle = new Text("Macros");
            macrosTitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
            grid.add(macrosTitle, 0, rowIndex++, 2, 1);
            for (MacroOperation op : MacroOperation.values())
            {
                MacroOperationDescription description = ButtonMap.MacroSchema.get(op);

                if (description != null)
                {
                    int joystickPort = -1;
                    if (description.getUserInputDevice() == UserInputDevice.Driver)
                    {
                        joystickPort = ElectronicsConstants.JOYSTICK_DRIVER_PORT;
                    }
                    else if (description.getUserInputDevice() == UserInputDevice.CoDriver)
                    {
                        joystickPort = ElectronicsConstants.JOYSTICK_CO_DRIVER_PORT;
                    }

                    if (joystickPort != -1)
                    {
                        final Joystick joystick = JoystickManager.get(joystickPort);
                        if (joystick != null)
                        {
                            int thisRowIndex = rowIndex;
                            rowIndex++;

                            Label operationNameLabel = new Label(op.toString());
                            grid.add(operationNameLabel, 0, thisRowIndex);

                            int buttonNumber = description.getUserInputDeviceButton().Value;
                            if (description.getButtonType() == ButtonType.Click)
                            {
                                Button operationButton = new Button("Click");
                                operationButton.setOnMouseClicked(
                                    (MouseEvent event) ->
                                    {
                                        joystick.getButtonProperty(buttonNumber).set(true);;
                                    });

                                grid.add(operationButton, 1, thisRowIndex);
                            }
                            else if (description.getButtonType() == ButtonType.Toggle)
                            {
                                CheckBox operationCheckBox = new CheckBox();
                                grid.add(operationCheckBox, 1, thisRowIndex);
                                Bindings.bindBidirectional(joystick.getButtonProperty(buttonNumber), operationCheckBox.selectedProperty());
                            }
                            else if (description.getButtonType() == ButtonType.Simple)
                            {
                                Button operationButton = new Button("Simple");
                                operationButton.setOnMouseClicked(
                                    (MouseEvent event) ->
                                    {
                                        joystick.getButtonProperty(buttonNumber).set(true);;
                                    });

                                grid.add(operationButton, 1, thisRowIndex);
                            }
                        }
                    }
                }
            }

            // add a spacer:
            rowIndex++;
        }

        Text sensorsTitle = new Text("Sensors");
        sensorsTitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        grid.add(sensorsTitle, 0, rowIndex++, 2, 1);

        for (int i = 0; i <= SensorManager.getHightestPort(); i++)
        {
            SensorBase sensor = SensorManager.get(i);
            if (sensor != null)
            {
                String sensorName = "Sensor " + i + ":";
                if (this.sensorNameMap.containsKey(i))
                {
                    sensorName = this.sensorNameMap.get(i);
                }

                int thisRowIndex = rowIndex;
                rowIndex++;

                Label sensorNameLabel = new Label(sensorName);
                grid.add(sensorNameLabel, 0, thisRowIndex);

                if (sensor instanceof DigitalInput)
                {
                    CheckBox sensorCheckBox = new CheckBox();
                    grid.add(sensorCheckBox, 1, thisRowIndex);
                    Bindings.bindBidirectional(((DigitalInput)sensor).getProperty(), sensorCheckBox.selectedProperty());
                }
                else if (sensor instanceof AnalogInput)
                {
                    Slider sensorSlider = new Slider();
                    sensorSlider.setMin(-1.0);
                    sensorSlider.setMax(1.0);
                    sensorSlider.setBlockIncrement(0.1);
                    sensorSlider.setShowTickMarks(true);

                    grid.add(sensorSlider, 1, thisRowIndex);
                    Bindings.bindBidirectional(((AnalogInput)sensor).getProperty(), sensorSlider.valueProperty());
                }
            }
        }

        // add a spacer:
        rowIndex++;

        Text motorsTitle = new Text("Motors");
        motorsTitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        grid.add(motorsTitle, 0, rowIndex++, 2, 1);
        for (int i = 0; i <= MotorManager.getHightestPort(); i++)
        {
            MotorBase motor = MotorManager.get(i);
            if (motor != null)
            {
                String motorName = "Motor " + i + ":";
                if (this.motorNameMap.containsKey(i))
                {
                    motorName = this.motorNameMap.get(i);
                }

                int thisRowIndex = rowIndex;
                rowIndex++;

                Label motorNameLabel = new Label(motorName);
                grid.add(motorNameLabel, 0, thisRowIndex);

                Slider sensorSlider = new Slider();
                sensorSlider.setMin(-1.0);
                sensorSlider.setMax(1.0);
                sensorSlider.setBlockIncrement(0.25);
                sensorSlider.setShowTickLabels(true);
                sensorSlider.setShowTickMarks(true);

                grid.add(sensorSlider, 1, thisRowIndex);
                Bindings.bindBidirectional(motor.getProperty(), sensorSlider.valueProperty());
            }
        }

        Scene scene = new Scene(grid, 375, 400);
        primaryStage.setScene(scene);
        primaryStage.show();

        // start the runner:
        this.runnerThread.start();
    }

    @Override
    public void stop() throws Exception
    {
        this.runner.stop();
        this.runnerThread.join(500);
    }

    public static void main(String[] args) throws InterruptedException, IOException
    {
        Application.launch(args);
    }
}
