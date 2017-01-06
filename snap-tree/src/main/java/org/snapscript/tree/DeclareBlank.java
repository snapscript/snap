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

public class DeclareBlank extends DeclareProperty {

   public DeclareBlank(TextLiteral identifier, ModifierData modifiers) {
      super(identifier, modifiers, null, null);
   }
   
   public DeclareBlank(TextLiteral identifier, ModifierData modifiers, Constraint constraint) {      
      super(identifier, modifiers, constraint, null);
   }
   
   public DeclareBlank(TextLiteral identifier, ModifierData modifiers, Evaluation value) {
      this(identifier, modifiers, null, value);
   }
   
   public DeclareBlank(TextLiteral identifier, ModifierData modifiers, Constraint constraint, Evaluation value) {
      super(identifier, modifiers, constraint, value);
   }
   
   @Override
   protected Value declare(Scope scope, Value value, String name, int modifiers) throws Exception {
      Object object = value.getValue();
      Type type = value.getConstraint();
      State state = scope.getState();
      
      try {      
         Value blank = ValueType.getBlank(object, type, modifiers);
         state.add(name, blank);
         return blank;
      }catch(Exception e) {
         throw new InternalStateException("Declaration of constant '" + name +"' failed", e);
      }      
   }  
}