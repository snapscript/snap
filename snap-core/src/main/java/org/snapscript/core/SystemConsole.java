package org.snapscript.core;

import java.io.PrintStream;

public class SystemConsole implements Console {

   private final PrintStream stream;
   private final Console console;
   
   public SystemConsole(Console console) {
      this.stream = System.out;
      this.console = console;
   }
   
   public void printf(Object value, Object... values) {
      String text = String.valueOf(value);
      String result = String.format(text, values);
      
      if(console != null) {
         console.print(result);
      } else {
         stream.print(result);
      }
   }
   
   @Override
   public void print(Object value) {
      if(console != null) {
         console.print(value);
      } else {
         stream.print(value);
      }
   }

   @Override
   public void println(Object value) {
      if(console != null) {
         console.println(value);
      } else {
         stream.println(value);
      }
   }

   @Override
   public void println() {
      if(console != null) {
         console.println();
      } else {
         stream.println();
      }
   }
}
