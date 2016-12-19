package org.snapscript.core.function;

import org.snapscript.core.InternalStateException;
import org.snapscript.core.Scope;
import org.snapscript.core.State;
import org.snapscript.core.Value;

public class ScopeAccessor implements Accessor<Scope> {

   private final String name;
   
   public ScopeAccessor(String name) {
      this.name = name;
   }
   
   @Override
   public Object getValue(Scope source) {
      State state = source.getState();
      Value field = state.getValue(name);
      
      if(field == null){
         throw new InternalStateException("Field '" + name + "' does not exist");
      }
      return field.getValue();
   }

   @Override
   public void setValue(Scope source, Object value) {
      State state = source.getState();
      Value field = state.getValue(name);
      
      if(field == null){
         throw new InternalStateException("Field '" + name + "' does not exist");
      }
      field.setValue(value);
   }

}
