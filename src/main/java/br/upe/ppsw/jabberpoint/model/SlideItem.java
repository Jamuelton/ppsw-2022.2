package br.upe.ppsw.jabberpoint.model;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.ImageObserver;

import br.upe.ppsw.jabberpoint.view.Draw;
import br.upe.ppsw.jabberpoint.view.Style;

public abstract class SlideItem extends Draw{

  private int level = 0;

  public SlideItem(int lev) {
    level = lev;
  }

  public SlideItem() {
    this(0);
  }

  public int getLevel() {
    return level;
  }

  

}
