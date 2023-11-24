import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

/**
 *
 * Description
 *
 * @version 1.0 from 24.11.2023
 * @author 
 */

public class ConfirmDeletionBox extends JDialog {
    // start attributes
    private JTextArea jMessage = new JTextArea();
    private JScrollPane jMessageScrollPane = new JScrollPane(jMessage);
    private JButton bOK1 = new JButton();
    private JButton bCancel1 = new JButton();
    private gui guii;
    // end attributes

    public ConfirmDeletionBox(String title, String message, gui pGui) { 
        // Dialog init
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        int frameWidth = 300; 
        int frameHeight = 200;
        setSize(frameWidth, frameHeight);
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (d.width - getSize().width) / 2;
        int y = (d.height - getSize().height) / 2;
        setLocation(x, y);
        setTitle(title);
        Container cp = getContentPane();
        cp.setLayout(null);
        // start components

        jMessageScrollPane.setBounds(8, 8, 264, 112);
        bOK1.setBounds(192, 128, 80, 24);
        jMessage.setEditable(false);
        jMessage.setText(message);
        cp.add(jMessageScrollPane);
        bOK1.setText("OK");
        bOK1.setMargin(new Insets(2, 2, 2, 2));
        bOK1.addActionListener(new ActionListener() { 
                public void actionPerformed(ActionEvent evt) { 
                    bOK1_ActionPerformed(evt);
                }
            });
        bOK1.setFont(new Font("Dialog", Font.PLAIN, 12));
        cp.add(bOK1);
        bCancel1.setBounds(8, 128, 168, 24);
        bCancel1.setText("Cancel");
        bCancel1.setMargin(new Insets(2, 2, 2, 2));
        bCancel1.addActionListener(new ActionListener() { 
                public void actionPerformed(ActionEvent evt) { 
                    bCancel1_ActionPerformed(evt);
                }
            });
        cp.add(bCancel1);
        // end components
        guii = pGui;
        setResizable(false);
        setVisible(true);
    } // end of public ConfirmDeletionBox

    // start methods
    public void bOK1_ActionPerformed(ActionEvent evt) {
        guii.delete();
        dispose();
    } // end of bOK1_ActionPerformed

    public void bCancel1_ActionPerformed(ActionEvent evt) {
        dispose();

    } // end of bCancel1_ActionPerformed

    // end methods

} // end of class ConfirmDeletionBox
