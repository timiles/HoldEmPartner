//$Id: AboutDialog.java,v 1.3 1998/05/05 06:47:20 Dale Exp $ 

package com.timiles.holdempartner.myUtils;
import javax.swing.*; 
import javax.swing.event.*; 
import javax.swing.text.html.*; 
import java.awt.*; 
import java.awt.event.*; 

/** 
* An About dialog that uses a JEditorPane to display content. JEditorPane 
* can render html and even make the links work, so this About dialog can 
* actually connect the user to my home page. 
* 
* @author Dale Anson 
* @revision $Revision: 1.3 $ 
*/ 
public class AboutDialog extends JDialog { 

   /** 
    * Constructor 
    * @param owner parent frame hosting this dialog 
    * @param title title to display on dialog, probably just "About" 
    * @param mime_type JEditorPane allows "text/html", "text/plain", and "text/rtf" 
    * @param contents the stuff to show, coded in the correct mime type 
    */ 
   public AboutDialog(Frame owner, String title, String mime_type, String contents) { 
      // initialize 
      super(owner, title, true); 
      setSize(new Dimension(300, 250)); 

      // set up JEditorPane 
      JEditorPane editor = new JEditorPane(mime_type, contents); 
      editor.setEditable(false); 
      editor.addHyperlinkListener(new Hyperactive()); 

      // set up main panel 
      JPanel panel = new JPanel(); 
      panel.setLayout(new BorderLayout()); 
      panel.add(new JScrollPane(editor), BorderLayout.CENTER); 

      // set up button panel -- this makes the button centered and "regular" 
      // size, not stretched across the entire bottom of the dialog 
      JPanel btn_panel = new JPanel(); 
      btn_panel.setLayout(new GridLayout(1, 1)); 
      JButton ok_btn = new JButton("Ok"); 
      ok_btn.addActionListener(new ActionListener(){ 
                                  public void actionPerformed(ActionEvent ae) { 
                                     setVisible(false); 
                                     dispose(); 
                                  } 
                               }); 
      btn_panel.add(ok_btn); 
      JPanel bottom_panel = new JPanel(); 
      bottom_panel.setLayout(new FlowLayout(FlowLayout.CENTER)); 
      bottom_panel.add(btn_panel); 
      panel.add(bottom_panel, BorderLayout.SOUTH); 

      setContentPane(panel); 
   } 

   /** 
    * Makes the hyperlinks work, stolen verbatim from the Swing javadoc. 
    */ 
   class Hyperactive implements HyperlinkListener { 

      public void hyperlinkUpdate(HyperlinkEvent e) { 
         if ( e.getEventType() == HyperlinkEvent.EventType.ACTIVATED ) { 
            JEditorPane pane = (JEditorPane) e.getSource(); 
            if ( e instanceof HTMLFrameHyperlinkEvent ) { 
               HTMLFrameHyperlinkEvent evt = (HTMLFrameHyperlinkEvent)e; 
               HTMLDocument doc = (HTMLDocument)pane.getDocument(); 
               doc.processHTMLFrameHyperlinkEvent(evt); 
            } 
            else { 
               try { 
                  pane.setPage(e.getURL()); 
               } 
               catch ( Throwable t ) { 
                  JOptionPane.showMessageDialog 
                     (AboutDialog.this, 
                      "Unable to open URL.", 
                      "Hyperlink Error", 
                      JOptionPane.ERROR_MESSAGE);; 
               } 
            } 
         } 
      } 
   } 
} 
