package br.upe.ppsw.jabberpoint.model;

import java.io.IOException;

public interface Accessor {

  public static final String DEMO_NAME = "Apresentação de Demonstração";
  public static final String DEFAULT_EXTENSION = ".xml";

  public void loadFile(Presentation presentation, String fileName) throws IOException;

}
