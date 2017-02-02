package org.snapscript.core;

import java.io.PrintStream;

public class StreamConsole implements Console {
   
   private final PrintStream stream;
   
   public StreamConsole() {
      this(System.out);
   }
   
   public StreamConsole(PrintStream stream) {
      this.stream = stream;
   }

   @Override
   public void print(Object value) {
      stream.print(value);
   }

   @Override
   public void println(Object value) {
      stream.println(value);
   }

   @Override
   public void println() {
      stream.println();
   }

}
