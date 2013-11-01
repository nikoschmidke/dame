package de.bujan.android;

import de.bujan.core.Dame;
import playn.android.GameActivity;
import playn.core.PlayN;

public class DameActivity extends GameActivity {

  @Override
  public void main(){
    PlayN.run(new Dame(100));
  }
}
