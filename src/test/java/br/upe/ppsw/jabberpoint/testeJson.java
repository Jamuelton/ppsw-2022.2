package br.upe.ppsw.jabberpoint;


import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.platform.commons.annotation.Testable;
import org.springframework.boot.test.autoconfigure.json.JsonTest;

import br.upe.ppsw.jabberpoint.control.JsonAccessor;
import br.upe.ppsw.jabberpoint.model.Presentation;

public class testeJson {
	
	private static final String FILE_NAME = "test.json";
	
	private Presentation presentation = null;
	private JsonAccessor jsonAccessor = null;

	@BeforeEach
	private void init() {
		this.presentation = new Presentation();
		this.jsonAccessor = new JsonAccessor();
	}

	@Testable
	public void testeCriacaoArquivoJson() throws IOException {
		this.jsonAccessor.saveFile(presentation, "src/main/resources/ppsw.json");
	}
}
