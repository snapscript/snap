package org.snapscript.tree;

import org.snapscript.core.Evaluation;
import org.snapscript.core.InternalStateException;
import org.snapscript.core.Scope;
import org.snapscript.core.State;
import org.snapscript.core.Type;
import org.snapscript.core.Value;
import org.snapscript.core.ValueType;
import org.snapscript.tree.constraint.Constraint;
import org.snapscript.tree.literal.TextLiteral;

public class DeclareVariable implements Evaluation {
   
   private final DeclarationConverter checker;
   private final NameExtractor extractor;
   private final Evaluation value;
   
   public DeclareVariable(TextLiteral identifier) {
      this(identifier, null, null);
   }
   
   public DeclareVariable(TextLiteral identifier, Constraint constraint) {      
      this(identifier, constraint, null);
   }
   
   public DeclareVariable(TextLiteral identifier, Evaluation value) {
      this(identifier, null, value);
   }
   
   public DeclareVariable(TextLiteral identifier, Constraint constraint, Evaluation value) {
      this.checker = new DeclarationConverter(constraint);
      this.extractor = new NameExtractor(identifier);
      this.value = value;
   }   

   @Override
   public Value evaluate(Scope scope, Object left) throws Exception {
      String name = extractor.extract(scope);
      Value value = create(scope, name);

      return declare(scope, value, name);
   }
   
   protected Value create(Scope scope, String name) throws Exception {
      Object object = null;
      
      if(value != null) {         
         Value result = value.evaluate(scope, null);         
         
         if(result != null) {
            object = result.getValue();
         }
      }
      return checker.convert(scope, object, name);
   }
   
   protected Value declare(Scope scope, Value value, String name) throws Exception {
      Object object = value.getValue();
      Type type = value.getConstraint();
      State state = scope.getState();
      
      try {      
         Value reference = ValueType.getReference(object, type);
         state.addVariable(name, reference);
         return reference;
      }catch(Exception e) {
         throw new InternalStateException("Declaration of variable '" + name +"' failed", e);
      }      
   }
}