/*
 * Code Name: Jebr.java
 * Created By: 'Umar A.Abu Baker, Fahed N.Shehab <http://www.geekybedouin.com>, <>
 * Computer Science Depatment (CS111) - An-Najah National University
 * Copyright (c) 2014 'Umar A.Abu Baker and Fahed N.Shehab <umr.baker@gmail.com>, <fahed.shehab@gmail.com>
 * 
 * 
 * License: 
 * This file is part of Project Jebr.

    Project Jebr is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License.

    Project Jebr is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program. If not, see <http://www.gnu.org/licenses/gpl-3.0.txt>.
 * 
 * 
 * 
 * 
 * Version: 1.04(Delta)
 * Description: This file contains the source of JIDE(Jebr IDE) which is the official IDE for the Jebr programming language.
 * 
 * 
 * Wanna edit the code? READ FIRST:
 * 
 * For the brave souls who get this far: You are the chosen ones,
 * the valiant knights of programming who toil away, without rest,
 * fixing our most awful code. To you, true saviors, kings of men,
 * I say this: never gonna give you up, never gonna let you down,
 * never gonna run around and desert you. Never gonna make you cry,
 * never gonna say goodbye. Never gonna tell a lie and hurt you.
 * 
 * Now, take the oath:
 * 
 * Errors gather, and now my maintenance begins. It shall not end until my death.
 * I shall take no wife, hold no lands, father no children. I shall wear no crowns and win no glory.
 * I shall live and die maintaining this code. I am the sword in the darkness. I am the maintainer of the code.
 * I am the fire that burns against the cold, the light that brings the dawn, the horn that wakes the sleepers,
 * the shield that guards the realms of men. I pledge my life and honor to maintain this code,
 * for this night and all the nights to come.
 */

package jebr;

import com.thehowtotutorial.splashscreen.JSplash;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;

import java.awt.event.ItemEvent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.text.AttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javax.swing.text.StyledDocument;
import javax.swing.JScrollPane;
import javax.swing.JToolBar;
import javax.swing.JButton;
import javax.swing.JTextPane;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.net.URL;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JTextArea;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.BadLocationException;
import javax.swing.text.MutableAttributeSet;
import org.fife.ui.rsyntaxtextarea.AbstractTokenMakerFactory;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rsyntaxtextarea.SyntaxScheme;
import org.fife.ui.rsyntaxtextarea.Token;
import org.fife.ui.rsyntaxtextarea.TokenMakerFactory;

import org.fife.ui.rsyntaxtextarea.folding.Fold;
import org.fife.ui.rsyntaxtextarea.folding.FoldParserManager;
import org.fife.ui.rtextarea.RTextScrollPane;


class LinuxInteractor {

public static String executeCommand(String command, boolean waitForResponse) {

	String response = "";

	ProcessBuilder pb = new ProcessBuilder("bash", "-c", command);
	pb.redirectErrorStream(true);

	System.out.println("Linux command: " + command);

	try {
		Process shell = pb.start();

		if (waitForResponse) {
			// To capture output from the shell
			InputStream shellIn = shell.getInputStream();

			// Wait for the shell to finish and get the return code
			int shellExitStatus = shell.waitFor();
			System.out.println("Exit status " + shellExitStatus);

			response = convertStreamToStr(shellIn);

			shellIn.close();
		}
	}
	catch (IOException e) {
		System.out.println("Error occured while executing Linux command. Error Description: " + e.getMessage());
	}
	catch (InterruptedException e) {
		System.out.println("Error occured while executing Linux command. Error Description: " + e.getMessage());
    }
    
	return response;
}

/*
* To convert the InputStream to String we use the Reader.read(char[]
* buffer) method. We iterate until the Reader return -1 which means
* there's no more data to read. We use the StringWriter class to
* produce the string.
*/

	public static String convertStreamToStr(InputStream is) throws IOException {
		if (is != null) {
			Writer writer = new StringWriter();

			char[] buffer = new char[1024];
			try {
				Reader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
				int n;
				while ((n = reader.read(buffer)) != -1) {
					writer.write(buffer, 0, n);
				}
			} finally {
				is.close();
			}
			return writer.toString();
		}
		else {
			return "";
		}
	}
}


public class Jebr extends JFrame implements ItemListener, WindowListener, ActionListener, AttributeSet.FontAttribute{
    
    JTextArea lines;
    RTextScrollPane sp;
    RSyntaxTextArea textArea;
    JMenu Themes = new JMenu("Themes");
    ButtonGroup themegroup = new ButtonGroup();
    JRadioButtonMenuItem dark = new JRadioButtonMenuItem("Night Sparkle - Dark");
    JRadioButtonMenuItem dark2 = new JRadioButtonMenuItem("Tron Legacy - Dark");
    JRadioButtonMenuItem light = new JRadioButtonMenuItem("Old Coder - Light");
    JRadioButtonMenuItem light2 = new JRadioButtonMenuItem("Candy Land - Light");
    JRadioButtonMenuItem light3 = new JRadioButtonMenuItem("Charlie - Light");
        
    ImageIcon opico = new ImageIcon("/BakIDE/resources/rsz_open.png");
    ImageIcon coico = new ImageIcon("/BakIDE/resources/rsz_compilerun.png");
    ImageIcon debico = new ImageIcon("/BakIDE/resources/rsz_debug.png");
    ImageIcon exico = new ImageIcon("/BakIDE/resources/rsz_exit.png");
    ImageIcon abico = new ImageIcon("/BakIDE/resources/rsz_about.png");
    ImageIcon fuopico = new ImageIcon("/BakIDE/resources/open.png");
    ImageIcon fucoico = new ImageIcon("/BakIDE/resources/compilerun.png");
    ImageIcon fudebico = new ImageIcon("/BakIDE/resources/debug.png");
    ImageIcon fuexico = new ImageIcon("/BakIDE/resources/exit.png");
    
    JMenu Windows = new JMenu("Windows");
    JMenuItem symtab = new JMenuItem("Symbol Table");
    JMenuItem targets = new JMenuItem("Target Language");
    JButton toolOpen = new JButton(fuopico);
    JButton toolCompile = new JButton(fucoico);
    JButton toolDebug = new JButton(fudebico);
    JButton toolExit = new JButton(fuexico);
    String nameOfFile;
    JMenu tools = new JMenu("Tools");
    JMenuItem debug = new JMenuItem("Debug", debico);
    JMenu settings = new JMenu("Settings");
    JMenu comsett = new JMenu("Compiler Settings");
    JMenu debsett = new JMenu("Debugger Settings");
    JMenuItem Compile = new JMenuItem("Compile", coico);
    JMenuItem Abouty = new JMenuItem("About",abico);
    JMenuItem Aboutx = new JMenuItem("Visit Website");
    JMenuItem Abouti = new JMenuItem("Manuals");
    JMenuItem Exit = new JMenuItem("Exit", exico);
    JMenuItem Open = new JMenuItem("Open", opico);
    

	private JPanel contentPane;
	private JToolBar toolBar;
	private JTextPane textEditor;
	private JScrollPane textEditorScrollPane;
	private StyledDocument textEditorDoc;
        private JTextPane textEditor2;
	private JScrollPane textEditorScrollPane2;
	private StyledDocument textEditorDoc2;

	/**
	 * Launch the application.
     * @param args
	 */
	public static void main(String[] args) {
           
           Color BG = new Color(234, 24, 188);
           Color FG = new Color(130, 78 , 83);
           /*
           Random splashNumberGenerator = new Random();
           int splashNumber = splashNumberGenerator.nextInt(4);
           if (splashNumber <= 0)
           {
               splashNumber=1;
           }
           String resourceName = ""+splashNumber+".png";
            **/
           JSplash S = new JSplash(Jebr.class.getResource("JIDEsplash.jpg"), false, true, false, "", null,FG, FG) ;
           S.splashOn();
           try {
           Thread.sleep(3000);
           S.splashOff();
           }
           catch(Exception s)
           {
               
           }
            
            
		EventQueue.invokeLater(new Runnable() {
            @Override
			public void run() {
				try {
				        Jebr frame = new Jebr();
					frame.setVisible(true);
				} catch (Exception e) {
				}
			}
		});
	}
        
        /*Hii*/
         public static void setJTextPaneFont(JTextPane jtp, Font font, Color c) {
       
        MutableAttributeSet attrs = jtp.getInputAttributes();

        
        StyleConstants.setFontFamily(attrs, font.getFamily());
        StyleConstants.setFontSize(attrs, font.getSize());
        StyleConstants.setItalic(attrs, (font.getStyle() & Font.ITALIC) != 0);
        StyleConstants.setBold(attrs, (font.getStyle() & Font.BOLD) != 0);

       
        StyleConstants.setForeground(attrs, c);

       
        StyledDocument doc = jtp.getStyledDocument();

        
        doc.setCharacterAttributes(0, doc.getLength() + 1, attrs, false);
    }
	/**
         * 
	 * Create the frame.
	 */
	public Jebr() {
                JToolBar toolbar = new JToolBar();
                toolbar.setFloatable(false);
                //toolbar.setBackground(new Color (46, 46, 46));
                toolbar.add(toolOpen);
                toolOpen.addActionListener(new ActionListener(){
            @Override
                    public void actionPerformed(ActionEvent e)
                    {
                        Open.doClick();
                    }
                });
                toolbar.add(toolCompile);
                toolCompile.addActionListener(new ActionListener(){
            @Override
                    public void actionPerformed(ActionEvent e)
                    {
                        Compile.doClick();
                    }
                    
                });
                toolbar.add(toolDebug);
                toolDebug.addActionListener(new ActionListener(){
                    
            @Override
                     public void actionPerformed(ActionEvent e)
                    {
                        debug.doClick();
                    }
                    
                });
                toolbar.add(toolExit);
                toolExit.addActionListener(new ActionListener(){
            @Override
                    public void actionPerformed(ActionEvent e)
                    {
                        Exit.doClick();
                    }
                    
                });
                
                
                
                setTitle("Jebr Project v1.2 - Delta ");
                JMenuBar Bar = new JMenuBar();
                //Bar.setBackground(new Color(44, 44, 44));
                //Bar.setForeground(new Color (0, 0, 0));
                this.addWindowListener(this);
                JMenu File = new JMenu("File");
                try{
                ImageIcon img = new ImageIcon("/bin/favicon.png");
                this.setIconImage(img.getImage());
                }
                catch (Exception e){}
                // iconURL is null when not found
                JMenu Help = new JMenu("Help");
                Help.add(Aboutx);
                Aboutx.addActionListener(new ActionListener(){
                    

            @Override
            public void actionPerformed(ActionEvent ae) {
                 try {
                Desktop.getDesktop().browse(new URL("http://geekybedouin.github.io/Meow/").toURI());
                } catch (Exception e) {
                   e.printStackTrace();
                }          
            }
                });
                Help.add(Abouti);
                Help.addSeparator();
                Help.add(Abouty);
                Abouty.addActionListener(this);
                File.add(Open);
                File.add(Compile);
                File.add(debug);
                debug.addActionListener(this);
                File.setMnemonic('f');
                Open.setMnemonic('o');
                Compile.setMnemonic('m');
                File.addSeparator();
                
                Themes.add(dark);
                Themes.add(dark2);
                Themes.add(light);
                Themes.add(light2);
                Themes.add(light3);
                themegroup.add(dark);
                themegroup.add(dark2);
                dark.setSelected(true);
                dark.addItemListener(this);
                light.addItemListener(this);
                light2.addItemListener(this);
                light3.addItemListener(this);
                dark2.addItemListener(this);
                themegroup.add(light);
                themegroup.add(light2);
                themegroup.add(light3);
                
                Exit.addActionListener(this);
                File.add(Exit);
                tools.add(settings);
                settings.add(comsett);
                settings.add(debsett);
                Compile.addActionListener(this);
                Bar.add(File);
                Bar.add(tools);
                Bar.add(Themes);
                Windows.add(symtab);
                symtab.addActionListener(this);
                targets.addActionListener(this);
                Windows.add(targets);
                Bar.add(Windows);
                Bar.add(Help);
                setJMenuBar(Bar);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setBounds(100, 50, 1000, 600);
                Open.addActionListener(this);
		contentPane = new JPanel();
		//contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout());
		setContentPane(contentPane);

		
                textEditor2 = new JTextPane();
                
                
                
                textArea = new RSyntaxTextArea(20, 60);
                
                AbstractTokenMakerFactory atmf = (AbstractTokenMakerFactory)TokenMakerFactory.getDefaultInstance();
                atmf.putMapping("text/jebr", "jebr.JebrToken");
                textArea.setSyntaxEditingStyle("text/jebr");
                //FoldParserManager.get().addFoldParserMapping(SyntaxConstants.SYNTAX_STYLE_NONE, new org.fife.ui.rsyntaxtextarea.folding.CurlyFoldParser());
                CurlyFoldParser x = new CurlyFoldParser();
                FoldParserManager.get().addFoldParserMapping("text/myLanguage", x );
                
                List<Fold> ll = x.getFolds(textArea);
                
                
                
                //textArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_CPLUSPLUS);
                textArea.setCodeFoldingEnabled(true);
                textArea.setAntiAliasingEnabled(true);
                sp = new RTextScrollPane(textArea);
                
                sp.setFoldIndicatorEnabled(true); 
                //textArea.setBackground(new Color (29, 31, 33));
                textEditor2.setEditable(false);
                //sp.setBackground(new Color (29, 31, 33));
                //SyntaxScheme scheme = textArea.getSyntaxScheme(); //error?
                //textArea.setFont(new Font("Courier",                Font.BOLD, 16));
                //textArea.setBackground(new Color (29, 31, 33));
                //textArea.setForeground(Color.decode("#e0e2e4"));
                //Color par = Color.decode("#e0e2e4");
                //Color reserved = Color.decode("#93c763");
                /*Color data_type = Color.decode("#678c9e");
                Color int_color = Color.decode("#c84e4e");
                Color string_color = Color.decode("#cf6015");
                Color comm_color = Color.decode("#9a7d7e");
                Color Highlight = Color.decode("#3a464b");
                textArea.setTabLineColor(par);
                textArea.setCurrentLineHighlightColor(Highlight);
                textArea.setAnimateBracketMatching(true);
                scheme.getStyle(Token.RESERVED_WORD).foreground = reserved;
                scheme.getStyle(Token.LITERAL_NUMBER_DECIMAL_INT) .foreground = int_color;
                scheme.getStyle(Token.SEPARATOR) .foreground = par; 
                scheme.getStyle(Token.DATA_TYPE).foreground = data_type;
                scheme.getStyle(Token.FUNCTION) .foreground = Color.GRAY;
                scheme.getStyle(Token.LITERAL_STRING_DOUBLE_QUOTE).foreground = string_color;
                scheme.getStyle(Token.LITERAL_STRING_DOUBLE_QUOTE).underline = true;
                scheme.getStyle(Token.COMMENT_EOL).font = new Font("Georgia",
                Font.ITALIC, 12);
                scheme.getStyle(Token.COMMENT_EOL).foreground= comm_color;
                
                scheme.getStyle(Token.COMMENT_DOCUMENTATION).foreground= comm_color;
                scheme.getStyle(Token.COMMENT_MULTILINE).foreground= comm_color;
                */
                textEditorScrollPane2 = new JScrollPane(textEditor2);
		//textEditor2.setBackground(new Color(46,46,46));
                contentPane.add(toolbar, BorderLayout.NORTH); 
		contentPane.add(/*textEditorScrollPane*/ sp, BorderLayout.CENTER);
                textEditor2.setPreferredSize(new Dimension (650, 100));
                contentPane.add(textEditorScrollPane2, BorderLayout.SOUTH);
	}

	
	public void updateTextColor(int offset, int length, Color c) {
                Font font = new Font("Sans_Serif",Font.PLAIN, 16);
		StyleContext sc = StyleContext.getDefaultStyleContext();
               
		AttributeSet aset = sc.addAttribute(SimpleAttributeSet.EMPTY,
				StyleConstants.Foreground, c);
                
		textEditorDoc.setCharacterAttributes(offset, length, aset, true);
                textEditor.setFont(font);
	}

	public void clearTextColors() {
		updateTextColor(0, textEditor.getText().length(), Color.BLACK);
	}


    @Override
    public void windowOpened(WindowEvent e) {
        
        String path;
                                         
           path = "/BakIDE/initial.cpp";
            if (path==null)
            {
                return;
            }
           
            Scanner in;
           try {
               in = new Scanner(new File(path));
           } catch (FileNotFoundException ex) {
               return ;
        }
           
           
            
            while (in.hasNextLine())
            {
                try
                {
                        
                  textArea.append(in.nextLine());
                  textArea.append("\n"); 
                }
                catch(Exception Z)
                {
                    
                }
            }
        
        
        
        
        
        int value=JOptionPane.showConfirmDialog(null,"Click OK to choose your workspace directory","Choose a Workspace",JOptionPane.OK_CANCEL_OPTION,JOptionPane.WARNING_MESSAGE);
        if (value == JOptionPane.YES_OPTION)
        {
            try{
            JFileChooser choose = new JFileChooser();
            choose.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            choose.showOpenDialog(choose);
            nameOfFile = choose.getSelectedFile().getAbsolutePath();
            }
            catch (NullPointerException ope)
            {
               String name;
               JOptionPane.showMessageDialog(null, "You didn't choose a directory, Will be using default BakWorkSpace", "Error!!", JOptionPane.OK_OPTION);
               Scanner inz;
               try{
                   inz = new Scanner(new File("/BakIDE/homepath"));
                    name = inz.nextLine();
               }
               catch(Exception z)
               {
                   name = "/home/minus/Music";
               }
               File fle = new File(name + "/BakWorkspace");
               fle.mkdirs();
               nameOfFile = fle.getAbsolutePath();
               
            }
            
            
            
            
        }
        else
        {
            dispose();
        }
    }

    @Override
    public void windowClosing(WindowEvent e) {
          int pick=JOptionPane.showConfirmDialog(null,"Oh, Jim! Do you want to save the file before you exit","Save?",JOptionPane.YES_NO_CANCEL_OPTION,JOptionPane.INFORMATION_MESSAGE);
          if (pick == JOptionPane.YES_OPTION)
          {
              Compile.doClick();
          }
          if (pick == JOptionPane.NO_OPTION)
          {
              dispose();
          }
          if (pick == JOptionPane.CANCEL_OPTION)
          {
              
          }
          if (pick == JOptionPane.CLOSED_OPTION)
          {
              
          }
    }

    @Override
    public void windowClosed(WindowEvent e) {
      //  throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void windowIconified(WindowEvent e) {
       // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void windowDeiconified(WindowEvent e) {
       // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void windowActivated(WindowEvent e) {
      //  throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void windowDeactivated(WindowEvent e) {
      //  throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void actionPerformed(ActionEvent e) {
       if (e.getSource() == debug )
       {
           PrintStream out = null;
           try {
               
               File file = new File(nameOfFile+"/temp.c");
               file.createNewFile();
               File file2 = new File(nameOfFile+"/symbol.txt");
               file2.createNewFile();
               out = new PrintStream(nameOfFile+"/source.cpp");
               System.setOut(out);
               String []lines = textArea.getText().split("\n");
               int countlines = 0;
               while (countlines<lines.length)
               {
                    out.println(lines[countlines]);
                    countlines++;
               }
               System.setOut(System.out);
               
               String par1 = nameOfFile+"/source.cpp";
               String par2 = nameOfFile+"/temp.c";
               String par3 = nameOfFile+"/symbol.txt";
               
               //,par1,par2,par3
               String[] command = {"kgdb",par1};
                
               Process p = Runtime.getRuntime().exec(command);
                
                //Process p = Runtime.getRuntime().exec("/bin/lexical /home/minus/Music/source.cpp /home/minus/Music/temp.c" );
               p.waitFor();
               
               Scanner x = new Scanner(new File("/BakIDE/kbccresults"));
               StyledDocument doc = textEditor2.getStyledDocument();
               doc.remove(0, doc.getLength());
               SimpleAttributeSet keyWord = new SimpleAttributeSet();
               
                   StyleConstants.setForeground(keyWord, Color.RED);
                   StyleConstants.setBold(keyWord, true);
                   int count = 0;
                   textEditor2.setContentType("text/html");
               while(x.hasNextLine())
               {
                   count++;
                   if (count == 1 )
                   {
                       doc.insertString(doc.getLength(), x.nextLine() + "\n", keyWord );
                       doc.insertString(doc.getLength(),  "\n", keyWord );
                     
                   }
                   else {
                       doc.insertString(doc.getLength(), x.nextLine() + "\n", null );
                   }
                   
                   
                   
               }
               count = 0 ;
               //LinuxInteractor.executeCommand("/bin/lexical " + nameOfFile+"/Source.cpp " + nameOfFile+"temp.c", true);
               //System.out.println( "lexical " + nameOfFile+"/Source.cpp " + nameOfFile+"temp.c");
           } catch (FileNotFoundException ex) {
               //Logger.getLogger(JavaHighlighter.class.getName()).log(Level.SEVERE, null, ex);
               System.setOut(System.out);
               out.println("No file found");
           } catch (IOException ex) {
              // Logger.getLogger(JavaHighlighter.class.getName()).log(Level.SEVERE, null, ex);
           } catch (InterruptedException ex) {
               //Logger.getLogger(JavaHighlighter.class.getName()).log(Level.SEVERE, null, ex);
           } catch (BadLocationException ex) {
               //Logger.getLogger(BakIde.class.getName()).log(Level.SEVERE, null, ex);
           }
           catch (NoSuchElementException ex){
               
           }
           
           
           
           
          
              
           
           
           
       }
       if (e.getSource()==targets)
       {
           //Assembler newgui = new Assembler();
       }
       if (e.getSource()==Compile)
       {
           
           PrintStream out = null;
           try {
               
               File file = new File(nameOfFile+"/temp.c");
               file.createNewFile();
               File file2 = new File(nameOfFile+"/symbol.txt");
               file2.createNewFile();
               out = new PrintStream(nameOfFile+"/source.cpp");
               System.setOut(out);
               String []lines = textArea.getText().split("\n");
               int countlines = 0;
               while (countlines<lines.length)
               {
                    out.println(lines[countlines]);
                    countlines++;
               }
               System.setOut(System.out);
               
               String par1 = nameOfFile+"/source.cpp";
               String par2 = nameOfFile+"/temp.c";
               String par3 = nameOfFile+"/symbol.txt";
               
               //,par1,par2,par3
               String[] command = {"kbcc",par1,par2,par3};
                
               Process p = Runtime.getRuntime().exec(command);
                
                //Process p = Runtime.getRuntime().exec("/bin/lexical /home/minus/Music/source.cpp /home/minus/Music/temp.c" );
               p.waitFor();
               
               Scanner x = new Scanner(new File("/BakIDE/kbccresults"));
               StyledDocument doc = textEditor2.getStyledDocument();
               doc.remove(0, doc.getLength());
               SimpleAttributeSet keyWord = new SimpleAttributeSet();
               
                   StyleConstants.setForeground(keyWord, Color.RED);
                   StyleConstants.setBold(keyWord, true);
                   int count = 0;
                   textEditor2.setContentType("text/html");
               while(x.hasNextLine())
               {
                   count++;
                   if (count == 1 )
                   {
                       doc.insertString(doc.getLength(), x.nextLine() + "\n", keyWord );
                       doc.insertString(doc.getLength(),  "\n", keyWord );
                     
                   }
                   else {
                       doc.insertString(doc.getLength(), x.nextLine() + "\n", null );
                   }
                   
                   
                   
               }
               count = 0 ;
               //LinuxInteractor.executeCommand("/bin/lexical " + nameOfFile+"/Source.cpp " + nameOfFile+"temp.c", true);
               //System.out.println( "lexical " + nameOfFile+"/Source.cpp " + nameOfFile+"temp.c");
           } catch (FileNotFoundException ex) {
               //Logger.getLogger(JavaHighlighter.class.getName()).log(Level.SEVERE, null, ex);
               System.setOut(System.out);
               out.println("No file found");
           } catch (IOException ex) {
              // Logger.getLogger(JavaHighlighter.class.getName()).log(Level.SEVERE, null, ex);
           } catch (InterruptedException ex) {
               //Logger.getLogger(JavaHighlighter.class.getName()).log(Level.SEVERE, null, ex);
           } catch (BadLocationException ex) {
               //Logger.getLogger(BakIde.class.getName()).log(Level.SEVERE, null, ex);
           }
           catch (NoSuchElementException ex){
               
           }
           
           
           
           
          
              
           
           
       }
       if(e.getSource()==Abouty)
           {
              // About x = new About();
           }
       if(e.getSource()== symtab)
       {
         //  SymTab gui= new SymTab(nameOfFile);
       }
       if (e.getSource()==Exit)
       {
             
           dispose();
       }
       if(e.getSource()==Open)
       {
           
           String path;
           JFileChooser open = new JFileChooser();
           try{
           open.showOpenDialog(this);
           FileNameExtensionFilter cppfilter = new FileNameExtensionFilter("cpp files (*.cpp)", "cpp");
            open.setFileFilter(cppfilter);
            open.setDialogTitle("Open a Cpp file into prespective");
 // set selected filter
            open.setFileFilter(cppfilter);
            path=open.getSelectedFile().getAbsolutePath();
           }
           
           catch (NullPointerException exc)
           {
               JOptionPane.showMessageDialog(null, "Jim, The file you've chosen isn't a cpp file  ", "Error",JOptionPane.OK_OPTION);
               path=null;
           }
            
            
            if (path==null)
            {
                return;
            }
           
            Scanner in;
           try {
               in = new Scanner(new File(path));
           } catch (FileNotFoundException ex) {
               return ;
        }
           
           
            
            while (in.hasNextLine())
            {
                try
                {
                        
                  textArea.append(in.nextLine());
                  
                }
                catch(Exception Z)
                {
                    
                }
            }
    }
    }

    private void sleep(int i) {
       // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void itemStateChanged(ItemEvent ie) {
        
        
       if (dark.isSelected())
       {
          SyntaxScheme scheme = textArea.getSyntaxScheme();
                textArea.setFont(new Font("Georgia",
                Font.BOLD, 16));
                textArea.setBackground(new Color (41, 49, 52));
                textArea.setForeground(Color.decode("#e0e2e4"));
                Color par = Color.decode("#e0e2e4");
                Color reserved = Color.decode("#93c763");
                Color data_type = Color.decode("#678c9e");
                Color int_color = Color.decode("#c84e4e");
                Color string_color = Color.decode("#cf6015");
                Color comm_color = Color.decode("#9a7d7e");
                Color Highlight = Color.decode("#3a464b");
                textArea.setCurrentLineHighlightColor(Highlight);
                scheme.getStyle(Token.RESERVED_WORD).foreground = reserved;
                scheme.getStyle(Token.LITERAL_NUMBER_DECIMAL_INT) .foreground = int_color;
                scheme.getStyle(Token.PREPROCESSOR) .foreground = Color.GRAY;
                scheme.getStyle(Token.SEPARATOR) .foreground = par; 
                scheme.getStyle(Token.FUNCTION) .foreground = Color.GRAY; 
                scheme.getStyle(Token.DATA_TYPE).foreground = data_type;
                scheme.getStyle(Token.LITERAL_STRING_DOUBLE_QUOTE).foreground = string_color;
                scheme.getStyle(Token.LITERAL_STRING_DOUBLE_QUOTE).underline = true;
                scheme.getStyle(Token.COMMENT_EOL).font = new Font("Georgia",
                Font.ITALIC, 12);
                scheme.getStyle(Token.COMMENT_EOL).foreground= comm_color;
                
                scheme.getStyle(Token.COMMENT_DOCUMENTATION).foreground= comm_color;
                scheme.getStyle(Token.COMMENT_MULTILINE).foreground= comm_color;
       }
       else if (light.isSelected())
       {
           SyntaxScheme scheme = textArea.getSyntaxScheme();
                textArea.setFont(new Font("Georgia",
                Font.BOLD, 16));
                textArea.setCurrentLineHighlightColor(Color.LIGHT_GRAY);
                textArea.setBackground(new Color (255, 255, 255));
                textArea.setForeground(Color.decode("#000000"));
                Color par = Color.decode("#e0e2e4");
                Color reserved = Color.decode("#93c763");
                Color data_type = Color.decode("#678c9e");
                Color int_color = Color.decode("#c84e4e");
                Color string_color = Color.decode("#cf6015");
                Color comm_color = Color.decode("#9a7d7e");
                Color Highlight = Color.decode("#3a464b");
                scheme.getStyle(Token.FUNCTION) .foreground = Color.BLACK;
                
                scheme.getStyle(Token.RESERVED_WORD).foreground = Color.BLUE;
                scheme.getStyle(Token.LITERAL_NUMBER_DECIMAL_INT) .foreground = Color.MAGENTA;
                scheme.getStyle(Token.LITERAL_NUMBER_FLOAT) .foreground = Color.MAGENTA;
                scheme.getStyle(Token.PREPROCESSOR) .foreground = Color.GREEN;
                scheme.getStyle(Token.SEPARATOR) .foreground = Color.RED; 
                scheme.getStyle(Token.DATA_TYPE).foreground = Color.BLUE;
                scheme.getStyle(Token.LITERAL_STRING_DOUBLE_QUOTE).foreground = Color.RED;
                scheme.getStyle(Token.LITERAL_STRING_DOUBLE_QUOTE).underline = true;
                scheme.getStyle(Token.COMMENT_EOL).font = new Font("Georgia",
                Font.ITALIC, 12);
                scheme.getStyle(Token.COMMENT_EOL).foreground= Color.GRAY;
                
                scheme.getStyle(Token.COMMENT_DOCUMENTATION).foreground= Color.GRAY;
                scheme.getStyle(Token.COMMENT_MULTILINE).foreground= Color.GRAY;
       }
       else if(light2.isSelected())
       {
                SyntaxScheme scheme = textArea.getSyntaxScheme();
                textArea.setFont(new Font("Georgia",
                Font.BOLD, 16));
                textArea.setCurrentLineHighlightColor(Color.decode("#dfdce3"));
                textArea.setBackground(new Color (255, 255, 255));
                textArea.setForeground(Color.decode("#af4179"));
                Color par = Color.decode("#e0e2e4");
                Color reserved = Color.decode("#4f9aed");
                Color data_type = Color.decode("#4f9aed");
                Color int_color = Color.decode("#69bc40");
                Color string_color = Color.decode("#69bc40");
                Color comm_color = Color.decode("#a0509f");
                Color Highlight = Color.decode("#3a464b");
                
                
                scheme.getStyle(Token.RESERVED_WORD).foreground = reserved;
                scheme.getStyle(Token.LITERAL_NUMBER_DECIMAL_INT) .foreground = int_color;
                scheme.getStyle(Token.PREPROCESSOR) .foreground = Color.GRAY;
                scheme.getStyle(Token.SEPARATOR) .foreground = par; 
                scheme.getStyle(Token.FUNCTION) .foreground = Color.decode("#fe9bc7"); 
                scheme.getStyle(Token.DATA_TYPE).foreground = data_type;
                scheme.getStyle(Token.LITERAL_STRING_DOUBLE_QUOTE).foreground = string_color;
                scheme.getStyle(Token.LITERAL_STRING_DOUBLE_QUOTE).underline = true;
                scheme.getStyle(Token.COMMENT_EOL).font = new Font("Georgia",
                Font.ITALIC, 12);
                scheme.getStyle(Token.COMMENT_EOL).foreground= comm_color;
                
                scheme.getStyle(Token.COMMENT_DOCUMENTATION).foreground= comm_color;
                scheme.getStyle(Token.COMMENT_MULTILINE).foreground= comm_color;
       }
       else if(light3.isSelected())
       {
                SyntaxScheme scheme = textArea.getSyntaxScheme();
                textArea.setFont(new Font("Georgia",
                Font.BOLD, 16));
                textArea.setCurrentLineHighlightColor(Color.decode("#e4c6a5"));
                textArea.setBackground(Color.decode("#fbebd4"));
                textArea.setForeground(Color.decode("#846146"));
                Color par = Color.decode("#846146");
                Color reserved = Color.decode("#98676a");
                Color data_type = Color.decode("#98676a");
                Color int_color = Color.decode("#dd4963");
                Color string_color = Color.decode("#dd4963");
                Color comm_color = Color.decode("#94b2b1");
                Color Highlight = Color.decode("#3a464b");
                
                
                scheme.getStyle(Token.RESERVED_WORD).foreground = reserved;
                scheme.getStyle(Token.LITERAL_NUMBER_DECIMAL_INT) .foreground = int_color;
                scheme.getStyle(Token.PREPROCESSOR) .foreground = Color.decode("#dd4963");
                scheme.getStyle(Token.SEPARATOR) .foreground = par; 
                scheme.getStyle(Token.FUNCTION) .foreground = Color.decode("#94b2b1"); 
                scheme.getStyle(Token.DATA_TYPE).foreground = data_type;
                scheme.getStyle(Token.LITERAL_STRING_DOUBLE_QUOTE).foreground = string_color;
                scheme.getStyle(Token.LITERAL_STRING_DOUBLE_QUOTE).underline = true;
                scheme.getStyle(Token.COMMENT_EOL).font = new Font("Georgia",
                Font.ITALIC, 12);
                scheme.getStyle(Token.COMMENT_EOL).foreground= comm_color;
                
                scheme.getStyle(Token.COMMENT_DOCUMENTATION).foreground= comm_color;
                scheme.getStyle(Token.COMMENT_MULTILINE).foreground= comm_color;
       }
       else if (dark2.isSelected())
       {
                SyntaxScheme scheme = textArea.getSyntaxScheme();
                textArea.setFont(new Font("Georgia",
                Font.BOLD, 16));
                textArea.setCurrentLineHighlightColor(Color.decode("#2e353f"));
                textArea.setBackground(Color.decode("#292f39"));
                textArea.setForeground(Color.decode("#b2b8c2"));
                Color par = Color.decode("#b2b8c2");
                Color reserved = Color.decode("#2b81b5");
                Color data_type = Color.decode("#2b81b5");
                Color int_color = Color.decode("#c84e4e");
                Color string_color = Color.decode("#c84e4e");
                Color comm_color = Color.decode("#52565e");
                Color Highlight = Color.decode("#3a464b");
                
                
                scheme.getStyle(Token.RESERVED_WORD).foreground = reserved;
                scheme.getStyle(Token.LITERAL_NUMBER_DECIMAL_INT) .foreground = int_color;
                scheme.getStyle(Token.LITERAL_NUMBER_FLOAT) .foreground = int_color;
                 scheme.getStyle(Token.LITERAL_BOOLEAN) .foreground = int_color;
                scheme.getStyle(Token.PREPROCESSOR) .foreground = Color.GRAY;
                scheme.getStyle(Token.SEPARATOR) .foreground = par; 
                scheme.getStyle(Token.FUNCTION) .foreground = Color.decode("#fe9bc7"); 
                scheme.getStyle(Token.DATA_TYPE).foreground = data_type;
                scheme.getStyle(Token.LITERAL_STRING_DOUBLE_QUOTE).foreground = string_color;
                scheme.getStyle(Token.LITERAL_STRING_DOUBLE_QUOTE).underline = true;
                scheme.getStyle(Token.COMMENT_EOL).font = new Font("Georgia",
                Font.ITALIC, 12);
                scheme.getStyle(Token.COMMENT_EOL).foreground= comm_color;
                
                scheme.getStyle(Token.COMMENT_DOCUMENTATION).foreground= comm_color;
                scheme.getStyle(Token.COMMENT_MULTILINE).foreground= comm_color;
       }
    }
}


/** 
 * 
 * -Audience gives round of applause-
 * "thank you, thank you" *bows*
 * -standing ovation-
 * -curtains close-
 * 
 */