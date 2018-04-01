package org.snapscript.core.function;

import org.snapscript.core.error.InternalStateException;
import org.snapscript.core.scope.Scope;
import org.snapscript.core.scope.State;
import org.snapscript.core.scope.Value;
import org.snapscript.core.type.Type;
import org.snapscript.core.type.TypeBody;

public class StaticAccessor implements Accessor {

   private final TypeBody body;
   private final Accessor accessor;
   private final Scope scope;
   private final String name;
   private final Type type;
   
   public StaticAccessor(TypeBody body, Scope scope, Type type, String name) {
      this.accessor = new ScopeAccessor(name);
      this.body = body;
      this.scope = scope;
      this.name = name;
      this.type = type;
   }
   
   @Override
   public Object getValue(Object source) {
      try {
         State state = scope.getState();
         Value field = state.get(name);
         
         if(field == null) {
            body.allocate(scope, type);           
         }
      }catch(Exception e){
         throw new InternalStateException("Static reference to '" + name + "' in '" + type + "' failed", e);
      }
      return accessor.getValue(scope);
   }

   @Override
   public void setValue(Object source, Object value) {
      try {
         State state = scope.getState();
         Value field = state.get(name);
         
         if(field == null) {
            body.allocate(scope, type);           
         }    
      }catch(Exception e){
         throw new InternalStateException("Static reference to '" + name + "' in '" + type + "' failed", e);
      }   
      accessor.setValue(scope,value);
   }

}