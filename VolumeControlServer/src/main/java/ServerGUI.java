import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;

public class ServerGUI extends JFrame implements ActionListener {

   static JFrame mainFrame;
   static JLabel addressLabel;
   static JButton showIPButton;
   static JButton endServerButton;

   public ServerGUI() {

      // COMMENTED OUT UNTIL I ADD A NEW ICON
//      URL iconURL = getClass().getResource("Volume Control Icon.jpg");
//      ImageIcon icon = new ImageIcon(iconURL);

      mainFrame = new JFrame("Volume Control Server");
//      mainFrame.setIconImage(icon.getImage());
      mainFrame.setSize(325, 150);
      mainFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

      addComponents(mainFrame.getContentPane());

      mainFrame.setVisible(true);
   }

   public void addComponents(Container pane) {
      pane.setLayout(new BoxLayout(pane, BoxLayout.Y_AXIS));

      showIPButton = new JButton("Refresh IP");
      showIPButton.addActionListener(this);
      showIPButton.setAlignmentX(Component.CENTER_ALIGNMENT);
      pane.add(showIPButton);

      addressLabel = new JLabel();
      addressLabel.setText(getAddress());
      addressLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
      pane.add(addressLabel);

      endServerButton = new JButton("End Server");
      endServerButton.addActionListener(this);
      endServerButton.setAlignmentX(Component.CENTER_ALIGNMENT);
      endServerButton.setAlignmentY(Component.BOTTOM_ALIGNMENT);
      pane.add(endServerButton);
   }

   public static String getAddress() {
      String address = "IP Address not found.";
      try {
         address = InetAddress.getLocalHost().toString();
      } catch (UnknownHostException ex) {
         ex.printStackTrace();
      }

      return address;
   }

   public void actionPerformed(ActionEvent e) {
      if (e.getSource() == showIPButton) {
         addressLabel.setText(getAddress());
      } else if (e.getSource() == endServerButton) {
         System.exit(0);
      }
   }
}
