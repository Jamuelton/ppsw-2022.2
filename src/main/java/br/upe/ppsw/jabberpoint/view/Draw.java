package br.upe.ppsw.jabberpoint.view;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.ImageObserver;



public abstract class Draw {

    
    public abstract Rectangle getBoundingBox(Graphics g, ImageObserver observer, float scale,
      Style style);

    public abstract void draw(int x, int y, float scale, Graphics g, Style style,
      ImageObserver observer);

      


}
