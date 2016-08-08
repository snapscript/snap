package org.snapscript.compile;

import junit.framework.TestCase;

import org.snapscript.tree.collection.CharacterList;

public class CharacterListTest extends TestCase {

   public void testList() throws Exception {
      Character[] array = new Character[1024];
      CharacterList list = new CharacterList(array);
      
      assertEquals(array.length, list.size());
      
      for(int i = 0; i < array.length; i++) {
         assertEquals(list.get(i),null);
      }
   }
}
