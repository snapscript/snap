package org.snapscript.core.function.index;

import static org.snapscript.core.Reserved.TYPE_CONSTRUCTOR;

import org.snapscript.core.module.Module;
import org.snapscript.core.scope.Scope;
import org.snapscript.core.type.Type;

public class LocalFunctionIndexer {

   private final FunctionIndexer indexer;
   
   public LocalFunctionIndexer(FunctionIndexer indexer) {
      this.indexer = indexer;
   }
   
   public FunctionPointer index(Scope scope, String name, Type... types) throws Exception { 
      Module module = scope.getModule();   
      Type type = module.getType(name);
      
      if(type != null) {
         return indexer.index(type, TYPE_CONSTRUCTOR, types);      
      }
      return null;
   }
   
   public FunctionPointer index(Scope scope, String name, Object... values) throws Exception {
      Module module = scope.getModule();   
      Type type = module.getType(name);
      
      if(type != null) {
         return indexer.index(type, TYPE_CONSTRUCTOR, values);      
      }
      return null;
   }
}
