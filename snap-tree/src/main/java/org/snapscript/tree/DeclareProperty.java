package org.snapscript.tree;

import org.snapscript.core.Evaluation;
import org.snapscript.core.InternalStateException;
import org.snapscript.core.Scope;
import org.snapscript.core.Type;
import org.snapscript.core.Value;
import org.snapscript.core.ValueType;
import org.snapscript.core.State;
import org.snapscript.tree.constraint.Constraint;
import org.snapscript.tree.literal.TextLiteral;

public class DeclareProperty implements Evaluation {
   
   private final DeclarationConverter checker;
   private final NameExtractor extractor;
   private final ModifierData modifiers;
   private final Evaluation value;
   
   public DeclareProperty(TextLiteral identifier, ModifierData modifiers) {
      this(identifier, modifiers, null, null);
   }
   
   public DeclareProperty(TextLiteral identifier, ModifierData modifiers, Constraint constraint) {      
      this(identifier, modifiers, constraint, null);
   }
   
   public DeclareProperty(TextLiteral identifier, ModifierData modifiers, Evaluation value) {
      this(identifier, modifiers, null, value);
   }
   
   public DeclareProperty(TextLiteral identifier, ModifierData modifiers, Constraint constraint, Evaluation value) {
      this.checker = new DeclarationConverter(constraint);
      this.modifiers = new ModifierChecker(modifiers); // cache the result
      this.extractor = new NameExtractor(identifier);
      this.value = value;
   }   

   @Override
   public Value evaluate(Scope scope, Object left) throws Exception {
      String name = extractor.extract(scope);
      Value value = create(scope, name);
      int mask = modifiers.getModifiers();

      return declare(scope, value, name, mask);
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
   
   protected Value declare(Scope scope, Value value, String name, int modifiers) throws Exception {
      Object object = value.getValue();
      Type type = value.getConstraint();
      State state = scope.getState();
      
      try { 
         Value reference = ValueType.getProperty(object, type, modifiers);
         state.add(name, reference);
         return reference;
      }catch(Exception e) {
         throw new InternalStateException("Declaration of variable '" + name +"' failed", e);
      }      
   }
}