package org.snapscript.core.type.index;

import static org.snapscript.core.Reserved.ANY_TYPE;
import static org.snapscript.core.Reserved.DEFAULT_PACKAGE;

import org.snapscript.core.type.NameBuilder;
import org.snapscript.core.type.Type;

public class PrimitiveLoader {

   private final PrimitiveIndexer generator;
   private final NameBuilder builder;
   
   public PrimitiveLoader(TypeIndexer indexer, NameBuilder builder) {
      this.generator = new PrimitiveIndexer(indexer);
      this.builder = builder;
   }
   
   public Type loadType(String type) {      
      if(type.endsWith(ANY_TYPE)) {
         String name = builder.createFullName(DEFAULT_PACKAGE, ANY_TYPE);
         
         if(name.equals(type)) {
            return generator.indexAny();
         }
      }
      return null;
   }
}
