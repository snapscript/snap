package org.snapscript.core.type.index;

import org.snapscript.core.convert.PrimitivePromoter;
import org.snapscript.core.type.Type;

public class GenericClassMapper {
   
   private final PrimitivePromoter promoter;
   private final TypeIndexer indexer;
   
   public GenericClassMapper(TypeIndexer indexer) {
      this.promoter = new PrimitivePromoter();
      this.indexer = indexer;
   }
   
   public Type map(Class type) {
      Class real = convert(type);
      
      if(real != null) {
         return indexer.loadType(real);
      }
      return null;
   }
   
   private Class convert(Class type) {
      if(type == Object.class) {
         return null;
      }
      if(type == void.class){
         return null;
      }
      return promoter.promote(type);
   }
}
