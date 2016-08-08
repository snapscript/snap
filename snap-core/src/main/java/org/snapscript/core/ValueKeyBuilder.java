package org.snapscript.core;

public class ValueKeyBuilder {
   
   private final ValueTypeExtractor extractor;
   
   public ValueKeyBuilder() {
      this.extractor = new ValueTypeExtractor();
   }

   public Object create(Scope scope, Object left, String name) throws Exception {
      if(left != null) {
         Type type = extractor.extract(scope, left);
         
         if(type != null) {
            return new ValueKey(name, type);
         }
      }
      return name;
   }
}
