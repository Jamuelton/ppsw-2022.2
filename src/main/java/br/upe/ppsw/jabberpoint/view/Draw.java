package br.upe.ppsw.jabberpoint.view;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.ImageObserver;



public interface Draw {

    
    public Rectangle getBoundingBox(Graphics g, ImageObserver observer, float scale,
      Style style);

    public void draw(int x, int y, float scale, Graphics g, Style style,
      ImageObserver observer);

      


}
