package br.upe.ppsw.jabberpoint.control;

import java.awt.Frame;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.MenuShortcut;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.springframework.util.ResourceUtils;

import br.upe.ppsw.jabberpoint.model.Accessor;

import br.upe.ppsw.jabberpoint.model.Presentation;
import br.upe.ppsw.jabberpoint.view.AboutBox;

public class MenuController extends MenuBar {

  private static final long serialVersionUID = 227L;

  private Frame parent;
  private Presentation presentation;
 
  protected static final String ABOUT = "Sobre";
  protected static final String FILE = "Arquivo";
  protected static final String EXIT = "Sair";
  protected static final String GOTO = "Pular para";
  protected static final String HELP = "Ajuda";
  protected static final String NEW = "Novo";
  protected static final String NEXT = "Próximo";
  protected static final String OPEN = "Abrir";
  protected static final String PAGENR = "Npumero do Slide?";
  protected static final String PREV = "Anteior";
  protected static final String SAVE = "Salvar";
  protected static final String VIEW = "Visualizar";

  protected static final String TESTFILE = "classpath:test.xml";
  protected static final String SAVEFILE = "classpath:dump.xml";

  protected static final String IOEX = "IO Exception: ";
  protected static final String LOADERR = "Erro ao carregar";
  protected static final String SAVEERR = "Erro ao salvar";

  public MenuController(Frame frame, Presentation pres) {
    parent = frame;
    presentation = pres;
 
    MenuItem menuItem;

    Menu fileMenu = new Menu(FILE);
    fileMenu.add(menuItem = mkMenuItem(OPEN));
    
    menuItem.addActionListener(new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			JFileChooser fileChooser = new JFileChooser();
//			fileChooser.setFileFilter(new FileNameExtensionFilter("ArquivoXML","xml"));
//			fileChooser.setFileFilter(new FileNameExtensionFilter("ArquivoJSON","json"));
			FileNameExtensionFilter xmlFilter = new FileNameExtensionFilter("ArquivoXML", "xml");
			FileNameExtensionFilter jsonFilter = new FileNameExtensionFilter("ArquivoJSON", "json");
			FileNameExtensionFilter htmlFilter = new FileNameExtensionFilter("ArquivoHTML", "html");
			fileChooser.setFileFilter(xmlFilter);
			fileChooser.addChoosableFileFilter(jsonFilter);
			fileChooser.addChoosableFileFilter(htmlFilter);
			int res =  fileChooser.showOpenDialog(null);
			if(res == JFileChooser.APPROVE_OPTION) {
				String path = fileChooser.getSelectedFile().getAbsolutePath();
		        FileFactory file = new FileFactory();
		        FileNameExtensionFilter selectedFilter = (FileNameExtensionFilter)fileChooser.getFileFilter();
		        String fileExtension = selectedFilter.getExtensions()[0];
				try {
					pres.clear();
					file.load(pres,path,fileExtension);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}
    	
    });
   

    fileMenu.add(menuItem = mkMenuItem(NEW));

    menuItem.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent actionEvent) {
        presentation.clear();
        parent.repaint();
      }
    });

    fileMenu.add(menuItem = mkMenuItem(SAVE));
    
    menuItem.addActionListener(new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			JFileChooser fileChooser = new JFileChooser();
			fileChooser.setDialogTitle("Save AS");
			FileNameExtensionFilter xmlFilter = new FileNameExtensionFilter("ArquivoXML", "xml");
			FileNameExtensionFilter jsonFilter = new FileNameExtensionFilter("ArquivoJSON", "json");
			FileNameExtensionFilter htmlFilter = new FileNameExtensionFilter("ArquivoHTML", "html");
			fileChooser.setFileFilter(xmlFilter);
			fileChooser.addChoosableFileFilter(jsonFilter);
			fileChooser.addChoosableFileFilter(htmlFilter);
			int res =  fileChooser.showOpenDialog(frame);
			if(res == JFileChooser.APPROVE_OPTION) {
				try {
					String path = fileChooser.getSelectedFile() + ".xml";
					path = fileChooser.getSelectedFile() + ".json";
					path = fileChooser.getSelectedFile() + ".html";
					FileFactory file = new FileFactory();
			        FileNameExtensionFilter selectedFilter = (FileNameExtensionFilter)fileChooser.getFileFilter();
			        String fileExtension = selectedFilter.getExtensions()[0];
					 
					file.save(pres,path,fileExtension);
				}catch(Exception exc){
					JOptionPane.showMessageDialog(parent, IOEX + exc, SAVEERR, JOptionPane.ERROR_MESSAGE);
				}
				
				
			}
		}
    	
    });

//    menuItem.addActionListener(new ActionListener() {
//      public void actionPerformed(ActionEvent e) {
//        Accessor xmlAccessor = new XMLAccessor(); // mudando tipo de xmlAcessor de Accessor para AcessorSave
//        try {
//          xmlAccessor.saveFile(presentation, SAVEFILE);
//        } catch (IOException exc) {
//          JOptionPane.showMessageDialog(parent, IOEX + exc, SAVEERR, JOptionPane.ERROR_MESSAGE);
//        }
//      }
//    });

    fileMenu.addSeparator();

    fileMenu.add(menuItem = mkMenuItem(EXIT));

    menuItem.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent actionEvent) {
        presentation.exit(0);
      }
    });

    add(fileMenu);
    
    //------------------------------------------------------------- dividindo código

    Menu viewMenu = new Menu(VIEW);
    viewMenu.add(menuItem = mkMenuItem(NEXT));

    menuItem.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent actionEvent) {
        presentation.nextSlide();
      }
    });

    viewMenu.add(menuItem = mkMenuItem(PREV));

    menuItem.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent actionEvent) {
        presentation.prevSlide();
      }
    });

    viewMenu.add(menuItem = mkMenuItem(GOTO));

    menuItem.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent actionEvent) {
        String pageNumberStr = JOptionPane.showInputDialog((Object) PAGENR);
        int pageNumber = Integer.parseInt(pageNumberStr);
        if(pageNumber <= 0 || pageNumber > presentation.getSlideNumber()) {
        	System.err.println("Ocorreu um erro!");
        }
        presentation.setSlideNumber(pageNumber - 1);
      }
    });

    add(viewMenu);
    
    //------------------------------------------------------------ dividindo código

    Menu helpMenu = new Menu(HELP);
    helpMenu.add(menuItem = mkMenuItem(ABOUT));

    menuItem.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent actionEvent) {
        AboutBox.show(parent);
      }
    });

    setHelpMenu(helpMenu);
  }

  public MenuItem mkMenuItem(String name) {
    return new MenuItem(name, new MenuShortcut(name.charAt(0)));
  }
}
