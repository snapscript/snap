package org.snapscript.core.type.index;

import static org.snapscript.core.Reserved.ANY_TYPE;
import static org.snapscript.core.Reserved.DEFAULT_PACKAGE;

import org.snapscript.core.NameFormatter;
import org.snapscript.core.type.Type;

public class PrimitiveLoader {

   private final PrimitiveIndexer generator;
   private final NameFormatter formatter;
   
   public PrimitiveLoader(TypeIndexer indexer, NameFormatter builder) {
      this.generator = new PrimitiveIndexer(indexer);
      this.formatter = builder;
   }
   
   public Type loadType(String type) {      
      if(type.endsWith(ANY_TYPE)) {
         String name = formatter.formatFullName(DEFAULT_PACKAGE, ANY_TYPE);
         
         if(name.equals(type)) {
            return generator.indexAny();
         }
      }
      return null;
   }
}
