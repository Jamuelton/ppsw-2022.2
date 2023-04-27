package br.upe.ppsw.jabberpoint.control;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Vector;

import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;

import br.upe.ppsw.jabberpoint.model.Accessor;
import br.upe.ppsw.jabberpoint.model.BitmapItem;
import br.upe.ppsw.jabberpoint.model.Presentation;
import br.upe.ppsw.jabberpoint.model.Slide;
import br.upe.ppsw.jabberpoint.model.SlideItem;
import br.upe.ppsw.jabberpoint.model.TextItem;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class JsonAccessor implements Accessor {
	
	private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
	
	protected static final String DEFAULT_API_TO_USE = "dom";

	  protected static final String SHOWTITLE = "showtitle";
	  protected static final String SLIDETITLE = "title";
	  protected static final String SLIDE = "slides";
	  protected static final String ITEM = "items";
	  protected static final String LEVEL = "level";
	  protected static final String KIND = "kind";
	  protected static final String TEXT = "text";
	  protected static final String IMAGE = "image";

	  protected static final String PCE = "Parser Configuration Exception";
	  protected static final String UNKNOWNTYPE = "Unknown Element type";
	  protected static final String NFE = "Number Format Exception";

	
////		File file = new File(fileName);
////	    Path path = file.toPath();
////
////	    byte[] bytes = Files.readAllBytes(path);
////	    String content = new String(bytes, StandardCharsets.UTF_8);
////	    
////	    try (FileReader reader = new FileReader(content)) {
////	        Presentation loadedPresentation = GSON.fromJson(reader, Presentation.class);
////	        
////	        return loadedPresentation;
////	      }
//
////	    JSONObject jsonObject = new JSONObject(content);
////	   
////	    Presentation presentation1 = new Presentation();
////	   
////	    
////	    JSONArray slides = jsonObject.getJSONArray("slide");
////	    for (int i = 0; i < slides.length(); i++) {
////	        JSONObject slide = slides.getJSONObject(i);
////	        String title = slide.getString("title");
////	        Object item = slide.get("item");
////
////	        presentation1.setTitle(title);
//// 
////	    }
////
////	    
////	    return presentation1;
//		
//		
////	    Path path = Paths.get(fileName);
////	    
////	    Reader reader = Files.newBufferedReader(path,StandardCharsets.UTF_8);
////	    
////		byte[] bytes = Files.readAllBytes(path);
////	    String content = new String(bytes, StandardCharsets.UTF_8);
////	    
////	    ObjectMapper mapper = new ObjectMapper();
////	    
////	    Presentation presentation1 = mapper.readValue(reader, Presentation.class);
////	    
////	    return presentation1;
//
//	}
	 
	  @Override
	  public Presentation loadFile(Presentation presentation, String fileName) throws IOException { // adicionar um por um
	      try {
	          int slideNumber, itemNumber, max = 0, maxItems = 0;
	          
	          JSONParser parse2 = new JSONParser();
	          
	          FileReader reader = new FileReader(fileName);
	          
	          JSONObject obj = (JSONObject) parse2.parse(reader);
	         
	          String slideTitle = (String) obj.get(SLIDETITLE); // pega o título
	          
	          presentation.setTitle(slideTitle);
	          
	          JSONArray slides = (JSONArray) obj.get(SLIDE); // pega o array do slide
	          
	          System.out.print(slides);
	          
	          max = slides.size();
	          
	          for (slideNumber = 0; slideNumber < max; slideNumber++) {
	           
	              Slide slide = new Slide();
	              
	              JSONObject eachSlide = (JSONObject) slides.get(slideNumber);
	              slide.setTitle((String) eachSlide.get(SLIDETITLE)); // pega o título
	              
	              JSONArray slidesItems = (JSONArray) eachSlide.get(ITEM); // pega os itens
	              
	              maxItems = slidesItems.size();
	              for (itemNumber = 0; itemNumber < maxItems; itemNumber++) { // add cada um com o for
	            	  
	            	  System.out.print(itemNumber);
	            	  
	                  JSONObject eachSlideItem =  (JSONObject) slidesItems.get(itemNumber);
	                  
	                  System.out.print(eachSlideItem);
	                  
	                  SlideItem slideItem = null;
	                  
	                  Long levelItem = (Long) eachSlideItem.get(LEVEL);
	                  
	                  
	                  System.out.print(levelItem);
	                  System.out.print(LEVEL);
	                  System.out.print(" ");
	                  
	                  String textItem = (String) eachSlideItem.get(TEXT);
	                  
	                  String kindItem = (String) eachSlideItem.get(KIND);
	                  
	                  if (kindItem.equals("image")) {
	                      slideItem = new BitmapItem(Math.toIntExact(levelItem), textItem); //conversao forcada de Long para int
	                  } else {
	                      slideItem = new TextItem(Math.toIntExact(levelItem), textItem);
	                  }
	                  
	                  slide.append(slideItem);
	              }
	              
	              presentation.append(slide);
	          }
	          
	          return presentation;
	          
	      } catch (Exception exc) {
	          exc.printStackTrace();
	          return null;
	      }
	  }
	  
	@Override
	public void saveFile(Presentation presentation, String fileName) throws IOException {
		
		 	PrintWriter out = new PrintWriter(new FileWriter(fileName));
//		 	Slide slide = presentation.getSlide(0);
		 	out.println("{");
		 	out.println("\"title\":\"" + presentation.getTitle() + "\"");
		 	
		 	out.println(",");
		 	out.println("\"slides\":");
		 	out.println("[");
//		 	out.println("\"title\":" + slide.getTitle());
		 	
		    for (int slideNumber = 0; slideNumber < presentation.getSize(); slideNumber++) {
		      Slide slideLow = presentation.getSlide(slideNumber);
		      
		      Vector<SlideItem> slideItems = slideLow.getSlideItems();
		      
		      
		      for (int itemNumber = 0; itemNumber < slideItems.size(); itemNumber++) {
		    	  SlideItem slideItem = (SlideItem) slideItems.elementAt(itemNumber);
		    
		    	  if (slideItem instanceof TextItem) {
		    		  out.println("{");
		    		  out.println("\"level\":" + slideItem.getLevel());
		    		  out.println(",");
		    		  out.println("\"text\":\"" + ((TextItem) slideItem).getText() + "\"");
		    		  out.println("}");
		    		 
		    			  out.println(",");
		    		  
		    	  }else {
		    		  System.out.println("Ignoring " + slideItem);
		    	  }
		      }
//		      out.println("<slide>");
//		      out.println("<title>" + slide.getTitle() + "</title>");
//
//		      Vector<SlideItem> slideItems = slide.getSlideItems();
//		      for (int itemNumber = 0; itemNumber < slideItems.size(); itemNumber++) {
//		        SlideItem slideItem = (SlideItem) slideItems.elementAt(itemNumber);
//		        out.print("<item kind=");
//
//		        if (slideItem instanceof TextItem) {
//		          out.print("\"text\" level=\"" + slideItem.getLevel() + "\">");
//		          out.print(((TextItem) slideItem).getText());
//		        } else {
//		          if (slideItem instanceof BitmapItem) {
//		            out.print("\"image\" level=\"" + slideItem.getLevel() + "\">");
//		            out.print(((BitmapItem) slideItem).getName());
//		          } else {
//		            System.out.println("Ignoring " + slideItem);
//		          }
//		        }
//
//		        out.println("</item>");
//		      }
//
//		      out.println("</slide>");
		    }
		    
		    out.println("]");
		    out.println("}");

		    out.close();
		  
//		File file = new File(fileName);
//		Path path = file.toPath();
//		
//		String json = GSON.toJson(presentation);
//		
//		Files.write(path, json.getBytes(StandardCharsets.UTF_8));
		
//		 try (FileWriter writer = new FileWriter(fileName)) {
//	            GSON.toJson(presentation, writer);
//	        } catch (IOException e) {
//	            e.printStackTrace();
//	        }
		
//		ObjectMapper mapper = new ObjectMapper();
//        File file = new File(fileName);
//        mapper.writeValue(file, presentation.class);
//		
	}

}
