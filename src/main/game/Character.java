package main.game;

import com.badlogic.gdx.math.Vector2;

public class Character {

  public final Vector2 position = new Vector2(512, 384);
  public float jumpHeight = 0;
  public float jumpAcceleration = 0;

  public void update() {
    jumpHeight += jumpAcceleration;
    if (jumpHeight < 0) {
      jumpAcceleration = 0;
      jumpHeight = 0;
    } else {
      jumpAcceleration -= .1;
    }
  }

  public boolean isTouchingGround() {
    return jumpHeight == 0;
  }

}
