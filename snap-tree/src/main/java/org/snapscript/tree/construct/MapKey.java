package org.snapscript.tree.construct;

import java.util.concurrent.atomic.AtomicReference;

import org.snapscript.core.Evaluation;
import org.snapscript.core.scope.Scope;
import org.snapscript.core.scope.State;
import org.snapscript.core.scope.index.Address;
import org.snapscript.core.scope.index.Index;
import org.snapscript.core.scope.index.Table;
import org.snapscript.core.variable.Value;
import org.snapscript.tree.NameReference;

public class MapKey extends Evaluation {
   
   private final AtomicReference<Address> location;
   private final NameReference reference;
   
   public MapKey(Evaluation key) {
      this.location = new AtomicReference<Address>();
      this.reference = new NameReference(key);
   }
   
   @Override
   public void define(Scope scope) throws Exception{
      String name = reference.getName(scope);
      Index index = scope.getIndex();
      Address address = index.get(name);

      location.set(address);
   }
   
   @Override
   public Value evaluate(Scope scope, Value left) throws Exception{
      String name = reference.getName(scope);
      Address address = location.get();
      
      if(address == null){
         State state = scope.getState(); 
         Value value = state.getValue(name);
         
         if(value != null) { 
            return value;
         }
      }else {
         Table table = scope.getTable(); // here we use the stack
         Value value = table.getValue(address);
         
         if(value != null) { 
            return value;
         }
      }
      return Value.getTransient(name);
   }
}