package org.snapscript.core.function;

import org.snapscript.core.Execution;
import org.snapscript.core.InternalStateException;
import org.snapscript.core.Statement;
import org.snapscript.core.module.Module;
import org.snapscript.core.scope.Scope;
import org.snapscript.core.scope.State;
import org.snapscript.core.variable.Value;

public class ModuleAccessor implements Accessor {

   private final Accessor accessor;
   private final Statement body;
   private final Module module;
   private final Scope scope;
   private final String name;
   
   public ModuleAccessor(Module module, Statement body, Scope scope, String name) {
      this.accessor = new ScopeAccessor(name);
      this.module = module;
      this.scope = scope;
      this.name = name;
      this.body = body;
   }
   
   @Override
   public Object getValue(Object source) {
      try {
         State state = scope.getState();
         Value field = state.get(name);
         
         if(field == null) {
            Execution execution = body.compile(scope, null);
            execution.execute(scope);
         }
      }catch(Exception e){
         throw new InternalStateException("Reference to '" + name + "' in '" + module + "' failed", e);
      }
      return accessor.getValue(scope);
   }

   @Override
   public void setValue(Object source, Object value) {
      try {
         State state = scope.getState();
         Value field = state.get(name);
         
         if(field == null) {
            Execution execution = body.compile(scope, null);
            execution.execute(scope);
         }
      }catch(Exception e){
         throw new InternalStateException("Reference to '" + name + "' in '" + module + "' failed", e);
      }   
      accessor.setValue(scope,value);
   }

}