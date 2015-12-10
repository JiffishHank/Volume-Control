public class ProcessController {

   private static ProcessBuilder builder = new ProcessBuilder();

   /*
     Executes the appropriate command
   */
   protected static boolean executeCommand(String command) {
      boolean success = false;

      try {
         builder.command("cmd.exe", "/c", command);
         builder.redirectErrorStream(true);
         builder.start();
         success = true;
      } catch (Exception e) {
         e.printStackTrace();
      }

      return success;
   }
}
