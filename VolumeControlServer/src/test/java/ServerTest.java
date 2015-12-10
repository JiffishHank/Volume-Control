import org.junit.Test;

import java.lang.reflect.Field;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class ServerTest {

   /*
      Checks that the different fields are being initiated properly
    */
   @Test
   public void testFieldsInitialized() throws NoSuchFieldException, IllegalAccessException {
      boolean prepared = Server.prepareConnection();

      if (!prepared) {
         fail();
      } else {
         Server server = new Server();

         Field packet = server.getClass().getDeclaredField("packet");
         Field socket = server.getClass().getDeclaredField("socket");
         Field buffer = server.getClass().getDeclaredField("buffer");

         packet.setAccessible(true);
         socket.setAccessible(true);
         buffer.setAccessible(true);

         assertNotNull("The packet should be initialized", packet.get(server));
         assertNotNull("The socket should be initialized", socket.get(server));
         assertNotNull("The buffer should be initialized", buffer.get(server));
      }
   }

   /*
      Checks whether the server is prepared successfully or not
    */
   @Test
   public void testPrepareConnection() {
      boolean prepared = Server.prepareConnection();
      assertTrue("The server should be initialized.", prepared);
   }
}