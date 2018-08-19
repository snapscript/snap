package org.snapscript.core.link;

import java.util.List;
import java.util.concurrent.Future;

import org.snapscript.common.BitSet;
import org.snapscript.core.Entity;
import org.snapscript.core.NameChecker;
import org.snapscript.core.module.Module;
import org.snapscript.core.scope.Scope;
import org.snapscript.core.scope.State;
import org.snapscript.core.type.Type;
import org.snapscript.core.variable.Value;

public class ImplicitImportLoader {
   
   private final NameChecker checker;

   public ImplicitImportLoader() {
      this.checker = new NameChecker(true);
   }
   
   public boolean loadImports(Scope scope, String name) throws Exception {
      if(checker.isEntity(name)) {
         Module module = scope.getModule();
         ImportManager manager = module.getManager();
         State state = scope.getState();
         Value value = state.getValue(name);         
         
         if(value == null) {
            return manager.getImport(name) != null;
         }                   
         return true;
      }
      return false;
   }

   public boolean loadImports(Scope scope, List<String> names) throws Exception {
      Type type = scope.getType();
      int count = names.size();
      
      if(count > 0) {
         BitSet done = new BitSet(count);
         
         while(type != null) {
            Scope inner = type.getScope();            
            
            if(loadImports(inner, names, done)) {
               return true;
            }            
            type = type.getOuter();
         }
         return loadImports(scope, names, done);               
      }
      return true;
   }
   
   private boolean loadImports(Scope scope, List<String> names, BitSet done) throws Exception {
      int count = names.size();      
      int remain = count;
      
      for(int i = 0; i < count; i++) {         
         if(!done.get(i)) {
            String name = names.get(i);
            
            if(loadImports(scope, name)) {
               done.set(i);
               remain--;
            }
         } else {
            remain--;
         }
      }
      return remain <= 0;
   }
}
