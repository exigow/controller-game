package main;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import main.game.Main;

import java.util.function.Supplier;

public class MainInitializer extends ApplicationAdapter {

  private final Supplier<Main> supplier;
  private Main demo;

  private MainInitializer(Supplier<Main> supplier) {
    this.supplier = supplier;
  }

  private static void initializeLazy(Supplier<Main> supplier) {
    new Lwjgl3Application(new MainInitializer(supplier), createDefaultConfiguration());
  }

  @Override
  public void create() {
    demo = supplier.get();
  }

  @Override
  public void render() {
    demo.onRender();
  }

  private static Lwjgl3ApplicationConfiguration createDefaultConfiguration() {
    Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
    config.setResizable(false);
    config.setWindowedMode(1280, 640);
    return config;
  }

  public static void main(String[] args) {
    initializeLazy(Main::new);
  }

}
