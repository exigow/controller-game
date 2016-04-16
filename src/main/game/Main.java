package main.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import main.controller.PadController;
import main.controller.State;

import static main.controller.Button.*;
import static main.controller.State.PRESSED;
import static main.controller.State.RELEASED;

public class Main {

  private final ShapeRenderer renderer = new ShapeRenderer();
  private final OrthographicCamera camera = initialiseCamera();
  private final Character character = new Character();
  private final PadController controller = PadController.initialise()
    .registerEventOnPress(A, () -> {
      if (character.isTouchingGround())
        character.jumpAcceleration += 4;
    });

  public void onRender() {
    character.update();
    clearWindow();
    renderer.setProjectionMatrix(camera.combined);
    Vector2 movementVector = resolveMovementVector(controller).scl(64);
    drawVector(movementVector);
    drawCharacter(character);
  }

  private void drawVector(Vector2 vector) {
    float x = 256;
    float y = 256;
    float size = 64;
    renderer.begin(ShapeRenderer.ShapeType.Line);
    renderer.setColor(Color.WHITE);
    renderer.line(x, y, x + vector.x, y + vector.y);
    renderer.circle(x, y, size, 8);
    renderer.end();
  }

  private void drawCharacter(Character character) {
    float width = 48;
    float height = 64;
    float shadowHeight = 16;
    float x = character.position.x;
    float y = character.position.y;
    float yJumped = y + character.jumpHeight;
    renderer.begin(ShapeRenderer.ShapeType.Filled);
    renderer.setColor(Color.BLACK);
    renderer.ellipse(x - width / 2, y - shadowHeight / 2, width, shadowHeight);
    renderer.setColor(Color.LIGHT_GRAY);
    renderer.rect(x - width / 2, yJumped, width, height);
    renderer.setColor(Color.RED);
    renderer.circle(x, y, 4, 8);
    renderer.end();
  }

  private static Vector2 resolveMovementVector(PadController controller) {
    State leftState = controller.stateOf(LEFT);
    State rightState = controller.stateOf(RIGHT);
    State upState = controller.stateOf(UP);
    State downState = controller.stateOf(DOWN);
    if (leftState == RELEASED && rightState == RELEASED && upState == RELEASED && downState == RELEASED)
      return Vector2.Zero;
    Vector2 result = new Vector2();
    if (leftState == PRESSED)
      result.x = -1;
    else if (rightState == PRESSED)
      result.x = 1;
    if (downState == PRESSED)
      result.y = -1;
    else if (upState == PRESSED)
      result.y = 1;
    return result.nor();
  }

  private static OrthographicCamera initialiseCamera() {
    OrthographicCamera camera = new OrthographicCamera();
    camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    return camera;
  }

  private static void clearWindow() {
    Gdx.gl20.glClearColor(.5f, .5f, .5f, 1);
    Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);
  }

}
