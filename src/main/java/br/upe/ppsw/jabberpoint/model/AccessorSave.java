package br.upe.ppsw.jabberpoint.model;

import java.io.IOException;

public interface AccessorSave extends Accessor{
	
	public void saveFile(Presentation presentation, String fileName) throws IOException;
}
