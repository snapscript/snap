package org.snapscript.tree.variable;

import java.util.concurrent.atomic.AtomicInteger;

import org.snapscript.core.Evaluation;
import org.snapscript.core.Scope;
import org.snapscript.core.State;
import org.snapscript.core.Value;
import org.snapscript.tree.NameReference;

public class Variable extends Evaluation {
   
   private final NameReference reference;
   private final VariableBinder binder;
   private final AtomicInteger index;
   
   public Variable(Evaluation identifier) {
      this.reference = new NameReference(identifier);
      this.binder = new VariableBinder();
      this.index = new AtomicInteger(-1);
   }
   private boolean compiled=false;
   @Override
   public Value compile(Scope scope, Object left) throws Exception{
      String name = reference.getName(scope);
      State state = scope.getState();
      int value = state.getLocal(name);
      if(name.equals("a") && value ==-1){
         System.err.println();
      }
      compiled=true;
      if(value != -1) {
         System.err.println("REF >> name=" +name + " index="+value+" "+System.identityHashCode(this));
         index.set(value);
      }
      return Value.getTransient(null);
   }
   
   @Override
   public Value evaluate(Scope scope, Object left) throws Exception{
      String name = reference.getName(scope);
      
      if(left == null) {
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
      }
      return binder.bind(scope, left, name);
   }  
}