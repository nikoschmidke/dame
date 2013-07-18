package de.bujan.java;

import de.bujan.core.Dame;
import playn.core.PlayN;
import playn.java.JavaPlatform;

public class DameJava {

  public static void main(String[] args) {
    JavaPlatform platform = JavaPlatform.register();
    platform.assets().setPathPrefix("de/bujan/resources");
    PlayN.run(new Dame(10));
  }
}
