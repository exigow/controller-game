package main.controller;

import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.ControllerAdapter;
import com.badlogic.gdx.controllers.Controllers;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PadController {

  private final Map<Button, State> states = initialiseWithStartingState();
  private final Map<Button, Event> pressEvents = new HashMap<>(Button.values().length);

  private PadController() {
    Adapter adapter = new Adapter();
    Controllers.addListener(adapter);
  }

  public static PadController initialise() {
    return new PadController();
  }

  public State stateOf(Button button) {
    return states.get(button);
  }

  private class Adapter extends ControllerAdapter {

    @Override
    public boolean buttonDown(Controller controller, int i) {
      changeState(i, State.PRESSED);
      Event event = pressEvents.get(resolveButton(i));
      if (event != null)
        event.doEvent();
      return false;
    }

    @Override
    public boolean buttonUp(Controller controller, int i) {
      changeState(i, State.RELEASED);
      return false;
    }

  }

  private void changeState(int index, State newState) {
    Button button = resolveButton(index);
    log("Button " + button + " state changed to " + newState);
    assert states.get(button) != newState;
    states.put(button, newState);
  }

  private static Button resolveButton(int index) {
    return Stream.of(Button.values())
      .filter(e -> e.index == index)
      .findFirst()
      .orElseThrow(() -> new RuntimeException("Undefined index: " + index));
  }

  private static Map<Button, State> initialiseWithStartingState() {
    return Stream.of(Button.values()).collect(Collectors.toMap(e -> e, startingState -> State.RELEASED));
  }

  public PadController registerEventOnPress(Button button, Event event) {
    assert pressEvents.get(button) == null : "Event already registered";
    pressEvents.put(button, event);
    log("Registering press event under " + button);
    return this;
  }

  private static void log(String message) {
    System.out.println("Pad: " + message);
  }

  @FunctionalInterface
  public interface Event {

    void doEvent();

  }

}
