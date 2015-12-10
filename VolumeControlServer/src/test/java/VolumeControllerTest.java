import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@PrepareForTest(ProcessController.class)
@RunWith(PowerMockRunner.class)

public class VolumeControllerTest {

   /*
      Checks the result is correct when passed good input
    */
   @Test
   public void testCalculateValueGoodInput() {
      String signal = "raise 10";
      int result = VolumeController.calculateValue(signal);
      int expected = 6553;
      assertEquals("The volume should be 6553", expected, result);
   }

   /*
      Whenever bad input is passed, it should return a negative number.
    */
   @Test
   public void testCalculateValueBadInput() {
      String[] signals = new String[8];
      signals[0] = "raise -40";
      signals[1] = "notacommand 10";
      signals[2] = "does not contain an int";
      signals[3] = "0";
      signals[4] = "raise " + Integer.MAX_VALUE;
      signals[5] = "raise " + Integer.MIN_VALUE;
      signals[6] = "";
      signals[7] = "raise 0.1";

      for (String signal : signals) {
         int result = VolumeController.calculateValue(signal);
         assertTrue("Expects a number less than 1", result < 1);
      }
   }

   /*
      Checks that the volume successfully unmutes the volume when called
    */
   @Test
   public void testUnmuteVolume() {
      PowerMockito.mockStatic(ProcessController.class);
      when(ProcessController.executeCommand("cd C:\\Program Files\\nircmd-x64 && nircmd.exe mutesysvolume 0")).thenReturn(true);

      boolean success = VolumeController.unmuteVolume();
      assertTrue(success);
   }

   /*
      Checks that the volume is successfully muted when the method is called
    */
   @Test
   public void testMuteVolume() {
      PowerMockito.mockStatic(ProcessController.class);
      when(ProcessController.executeCommand("cd C:\\Program Files\\nircmd-x64 && nircmd.exe mutesysvolume 1")).thenReturn(true);

      boolean success = VolumeController.muteVolume();
      assertTrue(success);
   }

   /*
      Checks to make sure that the volume is raised when passed valid input
    */
   @Test
   public void testRaiseVolumeValid() {
      PowerMockito.mockStatic(ProcessController.class);
      when(ProcessController.executeCommand("cd C:\\Program Files\\nircmd-x64 && nircmd.exe changesysvolume 6553")).thenReturn(true);

      boolean success = VolumeController.raiseVolume("raise 10");
      assertTrue("It should raise the volume by 10", success);
   }

   /*
      Checks that the volume does not raise when passed bad input
    */
   @Test
   public void testRaiseVolumeInvalid(){
      boolean success = VolumeController.raiseVolume("raise -1");
      assertFalse("It should fail to raise the volume by a negative amount.", success);

      success = VolumeController.raiseVolume("raise 0");
      assertFalse("It should fail to raise the volume.", success);
   }

   /*
      Checks that the volume is lowered when passed valid input
    */
   @Test
   public void testLowerVolumeValid() {
      PowerMockito.mockStatic(ProcessController.class);
      when(ProcessController.executeCommand("cd C:\\Program Files\\nircmd-x64 && nircmd.exe changesysvolume -6553")).thenReturn(true);

      boolean success = VolumeController.lowerVolume("lower 10");
      assertTrue("It should lower the volume by 10", success);
   }

   /*
      Checks that the volume does not lower when passed bad input
    */
   @Test
   public void testLowerVolumeInvalid(){
      boolean success = VolumeController.lowerVolume("lower -1");
      assertFalse("It should fail to lower the volume by a negative amount.", success);

      success = VolumeController.lowerVolume("lower 0");
      assertFalse("It should fail to lower the volume.", success);
   }
}