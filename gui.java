import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.event.ActionEvent;

import java.io.File;
import java.io.FileWriter;
import org.json.JSONObject;
import javax.swing.filechooser.FileSystemView;

public class gui extends JFrame {
    // start attributes
    private SignFile sf = new SignFile();
    private JButton bSign1 = new JButton();
    private JButton bFormatSign1 = new JButton();
    private JComboBox<String> jComboBox1 = new JComboBox<>();
    private DefaultComboBoxModel<String> jComboBox1Model = new DefaultComboBoxModel<>();
    private JButton bSignFileManager1 = new JButton();
    private JLabel lCurrentSignFileNone1 = new JLabel();
    private File[] paths;
    // end attributes

    public gui() { 
        // Frame init
        super();
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        int frameWidth = 300; 
        int frameHeight = 180;
        setSize(frameWidth, frameHeight);
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (d.width - getSize().width) / 2;
        int y = (d.height - getSize().height) / 2;
        setLocation(x, y);
        setTitle("USB Sign");
        setResizable(false);
        Container cp = getContentPane();
        cp.setLayout(null);
        // start components

        bSign1.setBounds(216, 112, 56, 24);
        bSign1.setText("Sign");
        bSign1.setMargin(new Insets(2, 2, 2, 2));
        bSign1.addActionListener(new ActionListener() { 
                public void actionPerformed(ActionEvent evt) { 
                    bSign1_ActionPerformed(evt);
                }
            });
        bSign1.setFont(new Font("Dialog", Font.PLAIN, 12));
        cp.add(bSign1);
        bFormatSign1.setBounds(8, 112, 192, 24);
        bFormatSign1.setText("Format & Sign");
        bFormatSign1.setMargin(new Insets(2, 2, 2, 2));
        bFormatSign1.addActionListener(new ActionListener() { 
                public void actionPerformed(ActionEvent evt) { 
                    bFormatSign1_ActionPerformed(evt);
                }
            });
        cp.add(bFormatSign1);
        jComboBox1.setModel(jComboBox1Model);
        jComboBox1.setBounds(8, 80, 264, 24);
        jComboBox1Model.addElement("(A:) USB Stick 1");
        jComboBox1Model.addElement("(B:) USB Stick 2");
        jComboBox1Model.addElement("(C:) USB Stick 3");
        jComboBox1.setFont(new Font("Dialog", Font.PLAIN, 12));
        cp.add(jComboBox1);
        bSignFileManager1.setBounds(8, 8, 264, 24);
        bSignFileManager1.setText("Sign File Manager");
        bSignFileManager1.setMargin(new Insets(2, 2, 2, 2));
        bSignFileManager1.addActionListener(new ActionListener() { 
                public void actionPerformed(ActionEvent evt) { 
                    bSignFileManager1_ActionPerformed(evt);
                }
            });
        bSignFileManager1.setFont(new Font("Dialog", Font.PLAIN, 12));
        cp.add(bSignFileManager1);
        lCurrentSignFileNone1.setBounds(8, 32, 264, 24);
        lCurrentSignFileNone1.setText("Current Sign File: None");
        lCurrentSignFileNone1.setFont(new Font("Dialog", Font.PLAIN, 12));
        lCurrentSignFileNone1.setHorizontalAlignment(SwingConstants.CENTER);
        cp.add(lCurrentSignFileNone1);
        // end components
        updateDrives();
        setVisible(true);
    } // end of public gui

    // start methods

    public static void main(String[] args) {
        new gui();
    } // end of main

    public void bSign1_ActionPerformed(ActionEvent evt) {
        JSONObject json = SignFile.SignFileToJSON(sf);

        try {
            File signFile = new File(paths[jComboBox1.getSelectedIndex()].getAbsolutePath() + sf.getName() + ".usbsf");
            signFile.delete();
            signFile.createNewFile();
            File infoFile = new File(paths[jComboBox1.getSelectedIndex()].getAbsolutePath() + "Found this USB Stick.txt");
            infoFile.delete();
            infoFile.createNewFile();

            FileWriter fw = new FileWriter(signFile);
            fw.write(SignFile.SignFileToJSON(sf).toString());
            fw.close();
            fw = new FileWriter(infoFile);
            fw.write("This USB Stick was signed using a .usbsf-File.\nIf you found this USB Stick, please visit\n\nhttps://github.com/derZCKR/usb-sign\n\nfor more information on how to return it to its owner.");
            fw.close();

            new DialogBox("USB Sign: Success!", "Successfully signed " + paths[jComboBox1.getSelectedIndex()].getAbsolutePath() + "!");
        } catch(Exception e) {
            new DialogBox("USB Sign: Error", "An error occured while writing to " + paths[jComboBox1.getSelectedIndex()].getAbsolutePath() + ": \n" + e.getMessage());
            e.printStackTrace();
        } // end of try
    } // end of bSign1_ActionPerformed

    public void bFormatSign1_ActionPerformed(ActionEvent evt) {
        new ConfirmDeletionBox("USB Sign: Warning", "You are about to delete all files in\n" + jComboBox1Model.getSelectedItem() + ".\nDo you want to continue?", this);
        //bSign1_ActionPerformed(evt);

    } // end of bFormatSign1_ActionPerformed

    public void delete(){
        for (File f : paths[jComboBox1.getSelectedIndex()].listFiles() ) {
            try {
                f.delete();
            } catch(Exception e) {
                new DialogBox("USB Sign: Error", "An error occured while deleting " + f.getAbsolutePath() + ": \n" + e.getMessage());
                e.printStackTrace();
            } // end of try  
        } // end of for
        new DialogBox("USB Sign: Success!", "Successfully formated " + paths[jComboBox1.getSelectedIndex()].getAbsolutePath() + "!");
        bSign1_ActionPerformed(new ActionEvent(this, 0, "")); 
    }

    public void bSignFileManager1_ActionPerformed(ActionEvent evt) {
        new sfm(this, sf);
    } // end of bSignFileManager1_ActionPerformed

    public void updateSignFile(SignFile pSF){
        sf = pSF;
        lCurrentSignFileNone1.setText("Current Sign File: " + sf.getName());  
    }

    public void updateDrives() {
        jComboBox1Model.removeAllElements();
        FileSystemView fsv = FileSystemView.getFileSystemView();
        paths = File.listRoots();
        for(File path:paths) {
            jComboBox1Model.addElement(fsv.getSystemDisplayName(path));
        }  
    }
    // end methods
} // end of class gui
