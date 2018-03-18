package org.snapscript.core.function;

import org.snapscript.core.Bug;
import org.snapscript.core.Execution;
import org.snapscript.core.InternalStateException;
import org.snapscript.core.Module;
import org.snapscript.core.Scope;
import org.snapscript.core.State;
import org.snapscript.core.Statement;
import org.snapscript.core.Value;

public class ModuleAccessor implements Accessor {

   private Execution ex;
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
   
   @Bug("this is crap")
   @Override
   public Object getValue(Object source) {
      try {
         State state = scope.getState();
         Value field = state.get(name);
         
         if(field == null) {
            body.define(scope);
            if(ex==null){
               ex=body.compile(scope);
            }
            ex.execute(scope);
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
            body.define(scope);
            if(ex==null){
               ex=body.compile(scope);
            }
            ex.execute(scope);     
         }    
      }catch(Exception e){
         throw new InternalStateException("Reference to '" + name + "' in '" + module + "' failed", e);
      }   
      accessor.setValue(scope,value);
   }

}