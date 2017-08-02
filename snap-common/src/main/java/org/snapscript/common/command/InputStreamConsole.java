package org.snapscript.common.command;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.Reader;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class InputStreamConsole implements Console {

   private final LineNumberReader parser;
   private final List<String> lines;
   private final Reader reader;

   public InputStreamConsole(InputStream source) {
      this.lines = new CopyOnWriteArrayList<String>();
      this.reader = new InputStreamReader(source);
      this.parser = new LineNumberReader(reader);
   }

   public List<String> readAll() throws IOException {
      while (true) {
         String line = parser.readLine();

         if (line != null) {
            lines.add(line);
         } else {
            break;
         }
      }
      return lines;
   }

   @Override
   public String readLine() throws IOException {
      return parser.readLine();
   }

}