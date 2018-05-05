package org.snapscript.core.constraint.transform;

import org.snapscript.core.type.Type;
import org.snapscript.core.type.TypeCache;

public class GenericCache  {

   private final TypeCache<GenericTransformTable> cache;
   
   public GenericCache() {
      this.cache = new TypeCache<GenericTransformTable>();
   }
   
   public GenericTransformTable fetch(Type type) {
      GenericTransformTable table = cache.fetch(type);
      
      if(table == null) {
         table = new GenericTransformTable();
         cache.cache(type, table);
      }
      return table;
      
   }
}
