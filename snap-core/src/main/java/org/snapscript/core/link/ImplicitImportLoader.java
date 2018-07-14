package org.snapscript.core.link;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.Future;

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
   
   public void loadImports(Scope scope, String name) throws Exception {
      List<String> names = Arrays.asList(name);
      
      if(checker.isEntity(name)) {
         loadImports(scope, names);         
      }
   }

   public void loadImports(Scope scope, Collection<String> names) throws Exception {
      Type type = scope.getType();
      
      if(!names.isEmpty()) {
         Set<String> required = new CopyOnWriteArraySet<String>(names);
         
         while(type != null) {
            Scope inner = type.getScope();
            
            if(!required.isEmpty()) {
               resolveImports(inner, required);
            }
            type = type.getOuter();
         }
         if(!required.isEmpty()) {
            resolveImports(scope, required);
         }      
      }
   }
   
   private void resolveImports(Scope scope, Collection<String> names) throws Exception {
      Module module = scope.getModule();
      ImportManager manager = module.getManager();
      State state = scope.getState();
      
      for(String name : names) {
         Value value = state.get(name);         
         
         if(value == null) {
            Future<Entity> entity = manager.getImport(name);
            
            if(entity != null) {
               names.remove(name);
            }
         } else {
            names.remove(name);
         }         
      }
   }
}
