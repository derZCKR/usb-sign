import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

public class DialogBox extends JDialog {
    // start attributes
    private JTextArea jMessage = new JTextArea();
    private JScrollPane jMessageScrollPane = new JScrollPane(jMessage);
    private JButton bOK1 = new JButton();
    // end attributes

    public DialogBox(String title, String message) { 
        // Dialog init
        super();
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
        jMessage.setText(message);
        jMessage.setEditable(false);
        cp.add(jMessageScrollPane);
        bOK1.setBounds(8, 128, 264, 24);
        bOK1.setText("OK");
        bOK1.setMargin(new Insets(2, 2, 2, 2));
        bOK1.addActionListener(new ActionListener() { 
                public void actionPerformed(ActionEvent evt) { 
                    bOK1_ActionPerformed(evt);
                }
            });
        cp.add(bOK1);
        // end components

        setResizable(false);
        setVisible(true);
    } // end of public DialogBox

    // start methods
    public void bOK1_ActionPerformed(ActionEvent evt) {

        dispose();
    } // end of bOK1_ActionPerformed

    // end methods

} // end of class DialogBox
