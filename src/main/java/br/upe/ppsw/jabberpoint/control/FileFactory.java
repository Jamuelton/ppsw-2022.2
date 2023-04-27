package br.upe.ppsw.jabberpoint.control;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import br.upe.ppsw.jabberpoint.model.Accessor;
import br.upe.ppsw.jabberpoint.model.Presentation;


public class FileFactory{
	 
	
	List<String> extensionList = new ArrayList<>();
	
	
	 public Accessor getPresentationFormat(String fileType) {
		 
		 if(fileType == "xml") {
			 return new XMLAccessor();
		 }else if(fileType == "json") {
			 return new JsonAccessor();
		 }else if(fileType == "html") {
			 return new HTMLAccessor();
		 }else {
			 throw new IllegalArgumentException("Unsupported file type: " + fileType);
		 }
//	        switch (fileType) {
//	            case "xml":
//	                return new XMLAccessor();
//	            case "json":
//	                return new JsonAccessor();
//	            default:
//	                throw new IllegalArgumentException("Unsupported file type: " + fileType);
//	        }
	 }
	 
	 public Presentation load(Presentation pres, String path, String type) throws IOException {
		 return getPresentationFormat(type).loadFile(pres, path);
	 }
	 
	 public void save(Presentation pres, String path, String type) throws IOException {
		 getPresentationFormat(type).saveFile(pres, path);
	 }
}
