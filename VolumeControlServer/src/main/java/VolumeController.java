public class VolumeController {

   private static final int MAX_VOLUME_BYTES = 65535;

   /*
       Calls the appropriate methods based on the signal
    */
   public static void handleInput(String signal) {

      if (signal.equals("mute")) {
         muteVolume();
      } else if (signal.equals("unmute")) {
         unmuteVolume();
      } else if (signal.contains("raise")) {
         unmuteVolume();
         raiseVolume(signal);
      } else if (signal.contains("lower")) {
         unmuteVolume();
         lowerVolume(signal);
      }
   }

   /*
       Mutes the volume
    */
   public static boolean muteVolume() {
      String command = "cd C:\\Program Files\\nircmd-x64 && nircmd.exe mutesysvolume 1";
      boolean success = ProcessController.executeCommand(command);

      if (success) {
         System.out.println("Volume muted.");
      }

      return success;
   }

   /*
       Unmutes the volume
    */
   public static boolean unmuteVolume() {
      String command = "cd C:\\Program Files\\nircmd-x64 && nircmd.exe mutesysvolume 0";
      boolean success = ProcessController.executeCommand(command);

      if (success) {
         System.out.println("Volume unmuted.");
      }

      return success;
   }

   /*
       Raises the volume by the specified amount
    */
   public static boolean raiseVolume(String signal) {
      int value = calculateValue(signal);
      boolean success = false;

      if (value > 0 && value <= MAX_VOLUME_BYTES) {
         String command = "cd C:\\Program Files\\nircmd-x64 && nircmd.exe changesysvolume " + value;
         success = ProcessController.executeCommand(command);

         if (success) {
            System.out.println("Volume raised by " + value + ".");
         }
      }

      return success;
   }

   /*
       Lowers the volume by the specified amount
    */
   public static boolean lowerVolume(String signal) {
      int value = calculateValue(signal);
      boolean success = false;

      if (value > 0 && value <= MAX_VOLUME_BYTES) {
         String command = "cd C:\\Program Files\\nircmd-x64 && nircmd.exe changesysvolume -" + value;
         success = ProcessController.executeCommand(command);

         if (success) {
            System.out.println("Volume lowered by " + value + ".");
         }
      }

      return success;
   }

   /*
       Parses the value and converts it to a commandline-ready form
    */
   public static int calculateValue(String signal) {
      int value = -1;

      try {
         // Removes the action in order to leave just the value -> "lower 20" becomes "20"
         if (signal.contains("lower")) {
            signal = signal.replace("lower ", "");
            value = Integer.parseInt(signal);
         } else if (signal.contains("raise")) {
            signal = signal.replace("raise ", "");
            value = Integer.parseInt(signal);
         }

         // Convert the value based on nircmd (65535)
         if (value > 0) {
            value = MAX_VOLUME_BYTES / value;
         }
      } catch (NumberFormatException e) {
         return -1;
      }

      return value;
   }
}
