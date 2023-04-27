package br.upe.ppsw.jabberpoint.model;

import java.io.IOException;

public interface Accessor {

  public static final String DEMO_NAME = "Apresentação de Demonstração";
  public static final String DEFAULT_EXTENSION = ".xml";

  public Presentation loadFile(Presentation presentation, String fileName) throws IOException;
  
  public void saveFile(Presentation presentation, String fileName) throws IOException;

}
