import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.io.IOException;
import java.net.*;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

@PrepareForTest(VolumeController.class)
@RunWith(PowerMockRunner.class)

public class IntegrationTest {

   private InetAddress host;
   private DatagramSocket socket;
   byte[] buffer;

   /*
      Creates the client and starts the server
    */
   @Before
   public void createServerAndClient() {
      try {
         host = InetAddress.getByName("192.168.0.21");
         socket = new DatagramSocket(null);
      } catch (SocketException e) {
         e.printStackTrace();
      } catch (UnknownHostException e) {
         e.printStackTrace();
      }

      // Create a new thread for the server to run on
      Thread thread = new Thread() {
         public void run() {
            Server.prepareConnection();
            Server.receiveInput();
         }
      };
      thread.start();
   }

   /*
      Checks to see if the server is calling the mute method
      When the client sends a packet with the "mute" command

      NOTE: This test currently does not work as intended.  However, it does
            successfully stub the method call so that the volume is not muted.
    */
   @Test
   public void testMuteCalled() throws Exception {

      PowerMockito.mockStatic(VolumeController.class);
      when(VolumeController.muteVolume()).thenReturn(false);

      try {
         buffer = "mute".getBytes();
         DatagramPacket packet = new DatagramPacket(buffer, buffer.length, host, 8080);
         socket.send(packet);
      } catch (IOException e) {
         e.printStackTrace();
      }

      // This next line of code does not actually verify that it was called one time
      // Need to look into this more
      PowerMockito.verifyStatic(times(1));
   }

   /*
      Checks to see if the server is calling hte mute method
      When the client sends a packet with the "mute" command

      NOTE: This test currently does not work and tries to accomplish
            the same goal as the previous but through the use of spies
    */
   @Test
   public void testMuteCalled2() {
      VolumeController spy = PowerMockito.spy(new VolumeController());
      Mockito.when(spy.muteVolume()).thenReturn(true);

      try {
         buffer = "mute".getBytes();
         DatagramPacket packet = new DatagramPacket(buffer, buffer.length, host, 8080);
         socket.send(packet);
      } catch (IOException e) {
         e.printStackTrace();
      }

      // This next line of code does not actually verify that it was called one time
      // Need to look into this more
      Mockito.verify(spy, times(1)).muteVolume();
   }
}
