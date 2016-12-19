package org.snapscript.tree.variable;

import org.snapscript.core.Evaluation;
import org.snapscript.core.InternalStateException;
import org.snapscript.core.Scope;
import org.snapscript.core.State;
import org.snapscript.core.Value;
import org.snapscript.tree.NameExtractor;

public class Variable implements Evaluation {
   
   private final VariableResolver resolver;
   private final NameExtractor extractor;
   
   public Variable(Evaluation identifier) {
      this.extractor = new NameExtractor(identifier);
      this.resolver = new VariableResolver();
   }
   
   @Override
   public Value evaluate(Scope scope, Object left) throws Exception{
      String name = extractor.extract(scope);
      
      if(left == null) {
         State state = scope.getState();
         Value value = state.getValue(name);
         
         if(value != null) { 
            return value;
         }
      }
      return resolve(scope, left, name);
   }  
   
   private Value resolve(Scope scope, Object left, String name) throws Exception {
      Value value = resolver.resolve(scope, left, name);
      
      if(value == null) {
         throw new InternalStateException("Could not resolve '" + name +"' in scope");
      }
      return value;
   }
}