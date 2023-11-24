import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.event.*;
import java.io.*;
import java.io.FileWriter;

import org.json.JSONObject;

public class sfm extends JFrame {
    // start attributes
    private SignFile sf;
    private gui g;
    private JButton bLoadSignFile1 = new JButton();
    private JLabel lContents1 = new JLabel();
    private JLabel lNameofUSBStick1 = new JLabel();
    private JLabel lYourName1 = new JLabel();
    private JLabel lContactInformation1 = new JLabel();
    private JTextField jTextField3 = new JTextField();
    private JTextField jTextField2 = new JTextField();
    private JTextField jTextField1 = new JTextField();
    private JLabel lFurtherInformation1 = new JLabel();
    private JFileChooser jFileChooser1 = new JFileChooser();
    private JFileChooser jFileChooser2 = new JFileChooser();
    private JButton bSaveandUse1 = new JButton();
    private JButton bUse1 = new JButton();
    private JTextArea jTextArea1 = new JTextArea();
    private JScrollPane jTextArea1ScrollPane = new JScrollPane(jTextArea1);
    // end attributes

    public sfm(gui pGUI, SignFile pSF) { 
        // Frame init
        super();
        g = pGUI;
        sf = pSF;
        loadSignFile(sf);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        int frameWidth = 300; 
        int frameHeight = 330;
        setSize(frameWidth, frameHeight);
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (d.width - getSize().width) / 2;
        int y = (d.height - getSize().height) / 2;
        setLocation(x, y);
        setTitle("USB Sign: Sign File Manager");
        setResizable(false);
        Container cp = getContentPane();
        cp.setLayout(null);
        // start components
        bLoadSignFile1.setBounds(8, 8, 264, 24);
        bLoadSignFile1.setText("Load Sign File");
        bLoadSignFile1.setMargin(new Insets(2, 2, 2, 2));
        bLoadSignFile1.addActionListener(new ActionListener() { 
                public void actionPerformed(ActionEvent evt) { 
                    bLoadSignFile1_ActionPerformed(evt);
                }
            });
        bLoadSignFile1.setFont(new Font("Dialog", Font.PLAIN, 12));
        cp.add(bLoadSignFile1);
        lContents1.setBounds(8, 40, 264, 24);
        lNameofUSBStick1.setBounds(8, 64, 108, 24);
        lYourName1.setBounds(8, 88, 108, 24);
        lContactInformation1.setBounds(8, 112, 111, 24);
        jTextField3.setBounds(120, 112, 152, 24);
        jTextField2.setBounds(120, 88, 152, 24);
        jTextField1.setBounds(120, 64, 152, 24);
        lFurtherInformation1.setBounds(8, 136, 111, 24);
        lContents1.setText("Contents");
        lContents1.setFont(new Font("Dialog", Font.BOLD, 14));
        lContents1.setHorizontalAlignment(SwingConstants.CENTER);
        cp.add(lContents1);
        lNameofUSBStick1.setText("Name of USB Stick");
        lNameofUSBStick1.setFont(new Font("Dialog", Font.PLAIN, 12));
        cp.add(lNameofUSBStick1);
        lYourName1.setText("Your Name");
        lYourName1.setFont(new Font("Dialog", Font.PLAIN, 12));
        cp.add(lYourName1);
        lContactInformation1.setText("Contact Information");
        lContactInformation1.setFont(new Font("Dialog", Font.PLAIN, 12));
        cp.add(lContactInformation1);
        jTextField3.setToolTipText("");
        jTextField3.setText("");
        cp.add(jTextField3);
        jTextField2.setToolTipText("");
        jTextField2.setText("");
        cp.add(jTextField2);
        jTextField1.setToolTipText("");
        jTextField1.setText("");
        cp.add(jTextField1);
        lFurtherInformation1.setText("Further Information");
        lFurtherInformation1.setFont(new Font("Dialog", Font.PLAIN, 12));
        cp.add(lFurtherInformation1);
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Sign Files (.usbsf)", "usbsf");
        jFileChooser1.setFileFilter(filter);
        jFileChooser2.setFileFilter(filter);
        bSaveandUse1.setBounds(16, 264, 192, 24);
        bSaveandUse1.setText("Save and Use");
        bSaveandUse1.setMargin(new Insets(2, 2, 2, 2));
        bSaveandUse1.addActionListener(new ActionListener() { 
                public void actionPerformed(ActionEvent evt) { 
                    bSaveandUse1_ActionPerformed(evt);
                }
            });
        cp.add(bSaveandUse1);
        bUse1.setBounds(216, 264, 56, 24);
        bUse1.setText("Use");
        bUse1.setMargin(new Insets(2, 2, 2, 2));
        bUse1.addActionListener(new ActionListener() { 
                public void actionPerformed(ActionEvent evt) { 
                    bUse1_ActionPerformed(evt);
                }
            });
        bUse1.setFont(new Font("Dialog", Font.PLAIN, 12));
        cp.add(bUse1);
        jTextArea1ScrollPane.setBounds(8, 160, 264, 80);
        jTextArea1.setFont(new Font("Dialog", Font.PLAIN, 12));
        jTextArea1.setText("Tip: Information on how to read this data\nwill be written to the Stick automatically.");
        cp.add(jTextArea1ScrollPane);
        // end components
        setVisible(true);
    }

    public void bLoadSignFile1_ActionPerformed(ActionEvent evt) {
        File saveFile = jFileChooser1_openFile();
        if (saveFile != null) {
            try {
                BufferedReader reader = new BufferedReader(new FileReader(saveFile));
                String s = reader.readLine();
                reader.close();

                sf = SignFile.JSONToSignFile(new JSONObject(s));
                loadSignFile(sf);
            } catch(Exception e) {
                new DialogBox("USB Sign: Error", "An error occured while loading " + saveFile.getAbsolutePath() + ": \n" + e.getMessage());
                e.printStackTrace();
            } // end of try
        } // end of if

    } // end of bLoadSignFile1_ActionPerformed

    public void bSaveandUse1_ActionPerformed(ActionEvent evt) {
        bUse1_ActionPerformed(evt);
        File saveFile = jFileChooser2_saveFile();

        if (saveFile != null) {
            if (!saveFile.getName().endsWith(".usbsf")) {
                saveFile = new File(saveFile.getAbsolutePath() + ".usbsf");     
            } // end of if
            try {
                saveFile.delete();
                saveFile.createNewFile();

                FileWriter fw = new FileWriter(saveFile);
                fw.write(SignFile.SignFileToJSON(sf).toString());
                fw.close();
            } catch(IOException e) {
                new DialogBox("USB Sign: Error", "An error occured while writing to " + saveFile.getAbsolutePath() + ": \n" + e.getMessage());
                e.printStackTrace();
            } // end of try  
        } // end of if
    } // end of bSaveandUse1_ActionPerformed

    public void bUse1_ActionPerformed(ActionEvent evt) {
        sf = new SignFile(jTextField1.getText(), jTextField2.getText(), jTextField3.getText(), jTextArea1.getText());
        g.updateSignFile(sf);
        dispose();
    } // end of bUse1_ActionPerformed

    public void loadSignFile(SignFile pSF){
        String[] data = pSF.getStack();
        jTextField1.setText(data[0]);
        jTextField2.setText(data[1]);
        jTextField3.setText(data[2]);
        jTextArea1.setText(data[3]);  
    }

    public File jFileChooser1_openFile() {
        if (jFileChooser1.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            return jFileChooser1.getSelectedFile();
        } else {
            return null;
        }
    }

    public File jFileChooser2_saveFile() {
        if (jFileChooser2.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
            return jFileChooser2.getSelectedFile();
        } else {
            return null;
        }
    }

    // end methods
    // start methods
}