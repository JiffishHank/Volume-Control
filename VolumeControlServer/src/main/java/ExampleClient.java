import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class ExampleClient {
   public static void main(String[] args) throws IOException {

      try {
         InetAddress host = InetAddress.getByName("192.168.0.21");
         DatagramSocket socket = new DatagramSocket(null);

         byte[] buffer = "Lower Volume".getBytes();
         DatagramPacket packet = new DatagramPacket(buffer, buffer.length, host, 8080);
         socket.send(packet);

         buffer = "mute".getBytes();
         packet = new DatagramPacket(buffer, buffer.length, host, 10001);
         socket.send(packet);

         try {
            Thread.sleep(2000);
         } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
         }

         buffer = "unmute".getBytes();
         packet = new DatagramPacket(buffer, buffer.length, host, 4466);
         socket.send(packet);

         socket.close();
      } catch (Exception e) {
         e.printStackTrace();
      }
   }
}
