package org.snapscript.common.command;

import java.util.List;

import junit.framework.TestCase;

public class CommandBuilderTest extends TestCase {
   
   public void testExecutor() throws Exception{
      CommandBuilder executor = new CommandBuilder();
      List<String> list = executor.create("ls -al", "c:\\Work").call().readAll();
      
      for(String entry : list) {
         System.err.println(entry);
      }
   }

}
