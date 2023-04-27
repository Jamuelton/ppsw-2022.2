package br.upe.ppsw.jabberpoint.control;

import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.util.Vector;

import br.upe.ppsw.jabberpoint.model.Accessor;
import br.upe.ppsw.jabberpoint.model.BitmapItem;
import br.upe.ppsw.jabberpoint.model.Presentation;
import br.upe.ppsw.jabberpoint.model.Slide;
import br.upe.ppsw.jabberpoint.model.SlideItem;
import br.upe.ppsw.jabberpoint.model.TextItem;

public class HTMLAccessor implements Accessor{
	
	 protected static final String DEFAULT_API_TO_USE = "dom";

	  protected static final String SHOWTITLE = "showtitle";
	  protected static final String SLIDETITLE = "title";
	  protected static final String SLIDE = "slide";
	  protected static final String ITEM = "item";
	  protected static final String LEVEL = "level";
	  protected static final String KIND = "kind";
	  protected static final String TEXT = "text";
	  protected static final String IMAGE = "image";

	  protected static final String PCE = "Parser Configuration Exception";
	  protected static final String UNKNOWNTYPE = "Unknown Element type";
	  protected static final String NFE = "Number Format Exception";

	@Override
	public Presentation loadFile(Presentation presentation, String fileName) throws IOException {
	    FileInputStream fileInputStream = new FileInputStream(fileName);
	    ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
	    Presentation loadedPresentation = null;
		try {
			loadedPresentation = (Presentation) objectInputStream.readObject();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    objectInputStream.close();
	    fileInputStream.close();
	    return loadedPresentation;
	}


	@Override
	public void saveFile(Presentation presentation, String filename) throws IOException {
	    PrintWriter out = new PrintWriter(new FileWriter(filename));

	    out.println("<!DOCTYPE html>");
	    out.println("<html>");
	    out.println("<head>");
	    out.println("<title>" + presentation.getTitle() + "</title>");
	    out.println("</head>");
	    out.println("<body>");

	    for (int slideNumber = 0; slideNumber < presentation.getSize(); slideNumber++) {
	      Slide slide = presentation.getSlide(slideNumber);

	      out.println("<div>");
	      out.println("<h2>" + slide.getTitle() + "</h2>");

	      Vector<SlideItem> slideItems = slide.getSlideItems();
	      for (int itemNumber = 0; itemNumber < slideItems.size(); itemNumber++) {
	        SlideItem slideItem = (SlideItem) slideItems.elementAt(itemNumber);

	        if (slideItem instanceof TextItem) {
	          out.print("<p>");
	          out.print(((TextItem) slideItem).getText());
	          out.println("</p>");
	        } else {
	          if (slideItem instanceof BitmapItem) {
	            out.print("<img src=\"" + ((BitmapItem) slideItem).getName() + "\">");
	          } else {
	            System.out.println("Ignoring " + slideItem);
	          }
	        }
	      }

	      out.println("</div>");
	    }

	    out.println("</body>");
	    out.println("</html>");

	    out.close();
	  }

    
}
