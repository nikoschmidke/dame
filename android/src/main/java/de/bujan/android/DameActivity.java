package de.bujan.android;

import playn.android.GameActivity;
import playn.core.PlayN;

import de.bujan.core.Dame;

public class DameActivity extends GameActivity {

  @Override
  public void main(){
    platform().assets().setPathPrefix("de/bujan/resources");
    PlayN.run(new Dame());
  }
}
