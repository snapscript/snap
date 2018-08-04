package org.snapscript.core.function;

import org.snapscript.core.Bug;
import org.snapscript.core.error.InternalStateException;
import org.snapscript.core.scope.Scope;
import org.snapscript.core.scope.State;
import org.snapscript.core.variable.Value;
import org.snapscript.core.variable.ValueData;

public class ScopeAccessor implements Accessor<Scope> {

   private final String name;
   
   public ScopeAccessor(String name) {
      this.name = name;
   }
   
   @Override
   public Object getValue(Scope source) {
      State state = source.getState();
      Value field = state.get(name);
      
      if(field == null){
         throw new InternalStateException("Field '" + name + "' does not exist");
      }
      return field.getValue();
   }

   @Bug
   @Override
   public void setValue(Scope source, Object value) {
      State state = source.getState();
      Value field = state.get(name);
      
      if(field == null){
         throw new InternalStateException("Field '" + name + "' does not exist");
      }
      field.setData(new ValueData(value, source.getModule()));
   }

}