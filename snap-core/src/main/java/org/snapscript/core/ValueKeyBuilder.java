package org.snapscript.core;

public class ValueKeyBuilder {
   
   public ValueKeyBuilder() {
      super();
   }

   @Bug("what do we do to figure out type")
   public Object create(Scope scope, Object left, String name) throws Exception {
      if(left != null) {
         Module module = scope.getModule();
         Context context = module.getContext();
         TypeExtractor extractor = context.getExtractor();
         Type type = extractor.getType(left, true);
         
         if(type != null) {
            return new ValueKey(name, type);
         }
      }
      return name;
   }
}
