package org.snapscript.core.convert;

import junit.framework.TestCase;

import org.snapscript.core.TestType;
import org.snapscript.core.Type;
import org.snapscript.core.convert.CharacterConverter;
import org.snapscript.core.convert.Score;

public class CharacterConverterTest extends TestCase {

   public void testCharacter() throws Exception {
      Type type = new TestType(null, null, null, Character.class);
      CharacterConverter converter = new CharacterConverter(type);
      
      assertEquals(converter.score('s'), Score.EXACT);
      assertEquals(converter.score("s"), Score.POSSIBLE);
      assertEquals(converter.score("ss"), Score.INVALID);
      assertEquals(converter.score((Object)null), Score.POSSIBLE);
      
      assertEquals(converter.convert('s'), 's');
      assertEquals(converter.convert("s"), 's');
      assertEquals(converter.convert((Object)null), null);
   }
   
   public void testPrimitiveCharacter() throws Exception {
      Type type = new TestType(null, null, null, char.class);
      CharacterConverter converter = new CharacterConverter(type);
      
      assertEquals(converter.score('s'), Score.EXACT);
      assertEquals(converter.score("s"), Score.POSSIBLE);
      assertEquals(converter.score("ss"), Score.INVALID);
      assertEquals(converter.score((Object)null), Score.INVALID);
      
      assertEquals(converter.convert('s'), 's');
      assertEquals(converter.convert("s"), 's');
   }
}
