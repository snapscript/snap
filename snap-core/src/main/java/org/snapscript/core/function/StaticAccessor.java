package org.snapscript.core.function;

import org.snapscript.core.error.InternalStateException;
import org.snapscript.core.scope.Scope;
import org.snapscript.core.scope.ScopeState;
import org.snapscript.core.type.Type;
import org.snapscript.core.type.TypeBody;
import org.snapscript.core.variable.Value;

public class StaticAccessor implements Accessor {

   private final Accessor accessor;
   private final TypeBody body;
   private final String alias;
   private final String name;
   private final Type type;
   
   public StaticAccessor(TypeBody body, Type type, String name, String alias) {
      this.accessor = new ScopeAccessor(name, alias);
      this.alias = alias;
      this.body = body;
      this.name = name;
      this.type = type;
   }
   
   @Override
   public Object getValue(Object source) {
      Scope scope = type.getScope();
      ScopeState state = scope.getState();
      Value field = state.getValue(alias);
      
      try {
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
      Scope scope = type.getScope();
      ScopeState state = scope.getState();
      Value field = state.getValue(alias);
      
      try {
         if(field == null) {
            body.allocate(scope, type);           
         }    
      }catch(Exception e){
         throw new InternalStateException("Static reference to '" + name + "' in '" + type + "' failed", e);
      }   
      accessor.setValue(scope,value);
   }

}