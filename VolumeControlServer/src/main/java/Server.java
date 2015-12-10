import javax.swing.*;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class Server extends JFrame {

   private static DatagramPacket packet = null;
   private static DatagramSocket socket = null;
   private static byte[] buffer = new byte[64];

   /*
       Creates a datagram socket pointed to port 8080
    */
   public static boolean prepareConnection() {
      try {
         socket = new DatagramSocket(8080);
         packet = new DatagramPacket(buffer, buffer.length);

         System.out.println("Server started");
         return true;
      } catch (Exception e) {
         e.printStackTrace();
         return false;
      }
   }

   /*
       Start listening for incoming packets
    */
   public static void receiveInput() {
      while (true) {
         try {
            socket.receive(packet);
            String message = new String(packet.getData(), 0, packet.getLength());
            VolumeController.handleInput(message);
         } catch (IOException e) {
            e.printStackTrace();
         }
      }
   }

   public static void terminate(){
      packet = null;
      socket = null;
   }

   /*
       *** MAIN METHOD ***
    */
   public static void main(String[] args) {
      new ServerGUI();

      boolean success = prepareConnection();
      if (success) {
         receiveInput();
      }
   }
}
