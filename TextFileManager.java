import java.awt.event.*;
import javax.swing.*;
import javax.swing.filechooser.*;
import java.util.*;
import java.io.*;
//import javax.jnlp.FileSaveService;

public class TextFileManager {
	public JFileChooser textFileChooser;
	private JFileChooser textFileSaver;
	private JFileChooser textFileExporter;

	private File textFileHolder;
	private File textFileOutput;

	private InputStream input;
	private FileOutputStream output;


	private String textFilePath;
	private String textFileContent;
	private String textLine;
	private String lineSep = System.getProperty("line.separator");
	private StringBuilder textBuilder;
	private BufferedReader fileRead;

	private TextFileFilter onlyTxt;

	//private FileViewUI fileUI;
	private int userInput;

	public class TextFileInput
	implements ActionListener {

		public void actionPerformed(ActionEvent e) {
	    // Set up the file chooser.
			userInput = textFileChooser.showOpenDialog(null);
	    	if (textFileChooser == null) {
	      		textFileChooser = new JFileChooser();

	      		//onlyTxt = new TextFileFilter();

		    	textFileChooser.addChoosableFileFilter(new TextFileFilter());
		    	textFileChooser.setAcceptAllFileFilterUsed(false);
		    	userInput = textFileChooser.showOpenDialog(null);

	    		if(userInput == JFileChooser.APPROVE_OPTION) {
	            	textFileHolder = 
	            	new File(textFileChooser.getSelectedFile().getAbsolutePath());
	            	fileToText(textFileHolder.getAbsolutePath());
	            	textFileChooser.setSelectedFile(null);
	            }
	    	}
		}
		public void fileToText(String inFile) {
	        try { 
	            fileRead = new BufferedReader(new FileReader(inFile));
	            textLine = null;
	            textBuilder = new StringBuilder();
		        while((textLine = fileRead.readLine()) != null ) {
			        textBuilder.append(textLine);
			        textBuilder.append(lineSep);
	    		}
	    		MainWindow.writeToBody(textBuilder.toString());
	    	} 
	        catch(IOException ie) {
	            System.out.println(ie.getMessage());
	        }
        
    	}
	}

	    /*
		private class FileViewUI extends FileView {

		}
		*/
		private class TextFileFilter extends javax.swing.filechooser.FileFilter {
			private String fileExten;
			private boolean isTxt;

			public boolean accept(File f) {
				return f.isDirectory() || 
					   f.getAbsolutePath().endsWith(".txt");

				/*
	    		if (f.isDirectory())
	      			return true;
	    		fileExten= Utils.getExtension(f);

	    		if(fileExten!=null)
	    			if(fileExten==Utils.txt)
	    				return isTxt = true;
	    			else
	    				return isTxt = false;
	    		*/
			}
			public String getDescription() { return "...it's a text file"; }
			public boolean isTextFile() {
				return isTxt;
			}
		}

		public class TextFileSaver
		implements ActionListener {
			public void actionPerformed(ActionEvent e) {
			}
		
		}
		
	



}