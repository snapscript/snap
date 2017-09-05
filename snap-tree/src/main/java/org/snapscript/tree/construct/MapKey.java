package org.snapscript.tree.construct;

import java.util.concurrent.atomic.AtomicInteger;

import org.snapscript.core.Bug;
import org.snapscript.core.Evaluation;
import org.snapscript.core.Scope;
import org.snapscript.core.State;
import org.snapscript.core.Value;
import org.snapscript.tree.NameReference;

public class MapKey extends Evaluation {
   
   private final NameReference reference;
   private final AtomicInteger index;
   
   public MapKey(Evaluation key) {
      this.reference = new NameReference(key);
      this.index = new AtomicInteger(-1);
   }
   
   @Bug
   @Override
   public Value compile(Scope scope, Object left) throws Exception{
      String name = reference.getName(scope);
      State state = scope.getState();
      int value = state.getLocal(name);

      if(value != -1) {
         System.err.println("REF >> name=" +name + " index="+value+" "+System.identityHashCode(this));
         index.set(value);
      }
      return Value.getTransient(null);
   }
   
   @Override
   public Value evaluate(Scope scope, Object left) throws Exception{
      String name = reference.getName(scope);
      int s = index.get();
      
      if(s == -1){
         State state = scope.getState(); // here we use the stack
         Value value = state.getScope(name);
         
         if(value != null) { 
            return value;
         }
      }else {
         State state = scope.getState(); // here we use the stack
         Value value = state.getLocal(s);
         //System.err.println("ref="+name+" index="+s);
         if(value != null) { 
            return value;
         }
      }
      return Value.getTransient(name);
   }
}