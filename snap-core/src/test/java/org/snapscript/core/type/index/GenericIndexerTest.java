package org.snapscript.core.type.index;

import java.util.Map;

import junit.framework.TestCase;

public class GenericIndexerTest extends TestCase {
   
   public static class Foo<A extends String, B extends Map<String, String>> {
      
   }
   
   public void testGenericParams() throws Exception {
      GenericIndexer indexer = new GenericIndexer(null);
      indexer.index(Foo.class);
   }

}
