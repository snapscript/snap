package org.snapscript.core;

import junit.framework.TestCase;

public class FilePathConverterTest extends TestCase {
   
   public void testPath() throws Exception {
      PathConverter parser = new FilePathConverter();
      
      assertEquals("game.tetris", parser.createModule("/game/tetris.snap"));
      assertEquals("game.tetris", parser.createModule("/game/tetris.snap"));
      assertEquals("game.tetris", parser.createModule("game/tetris.snap"));
      assertEquals("game.tetris", parser.createModule("game\\tetris.snap"));
      assertEquals("game.tetris", parser.createModule("\\game\\tetris.snap"));
      assertEquals("game.tetris", parser.createModule("game.tetris"));
      assertEquals("game.tetris", parser.createModule("game.tetris"));
      assertEquals("test", parser.createModule("test.snap"));
      assertEquals("test", parser.createModule("test.snap"));
      assertEquals("test", parser.createModule("/test.snap"));
      assertEquals("test", parser.createModule("/test.snap"));
      assertEquals("test", parser.createModule("\\test.snap"));
      assertEquals("test", parser.createModule("test"));
      assertEquals("some.package", parser.createModule("/some/package/Builder.snap"));
      assertEquals("some.package", parser.createModule("some/package/Builder.snap"));
      assertEquals("some.package", parser.createModule("some.package.Builder"));
      
      assertEquals("/test.snap", parser.createPath("test"));
      assertEquals("/game/tetris.snap", parser.createPath("game.tetris"));
      assertEquals("/test.snap", parser.createPath("/test.snap"));
   }

}
