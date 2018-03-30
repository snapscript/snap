package org.snapscript.tree;

import org.snapscript.core.Allocator;
import org.snapscript.core.Evaluation;
import org.snapscript.core.Local;
import org.snapscript.core.ModifierType;
import org.snapscript.core.Scope;
import org.snapscript.core.Type;
import org.snapscript.core.Value;
import org.snapscript.core.constraint.Constraint;
import org.snapscript.tree.constraint.SafeConstraint;

public class DeclarationAllocator implements Allocator {

   private final DeclarationConverter converter;
   private final Constraint constraint;
   private final Evaluation expression;
   
   public DeclarationAllocator(Constraint constraint, Evaluation expression) {      
      this.constraint = new SafeConstraint(constraint);
      this.converter = new DeclarationConverter();
      this.expression = expression;
   }   
   
   @Override
   public <T extends Value> T compile(Scope scope, String name, int modifiers) throws Exception {
      Type type = constraint.getType(scope);
      
      if(expression != null) {
         Constraint object = expression.compile(scope, null);
         Type real = object.getType(scope);
         
         if(real != null) {
            type = converter.compile(scope, real, constraint, name);        
         }
      }
      return create(scope, name, type, type, modifiers);
   }
   
   
   @Override
   public <T extends Value> T allocate(Scope scope, String name, int modifiers) throws Exception {
      Type type = constraint.getType(scope);
      Object object = null;
      
      if(expression != null) {
         Value value = expression.evaluate(scope, null);
         Object original = value.getValue();
         
         if(type != null) {
            object = converter.convert(scope, original, constraint, name);
         } else {
            object = original;
         }
      }
      return create(scope, name, object, type, modifiers);
   }
   
   protected <T extends Value> T create(Scope scope, String name, Object value, Type type, int modifiers) throws Exception {
      if(ModifierType.isConstant(modifiers)) {
         return (T)Local.getConstant(value, name, type);
      }
      return (T)Local.getReference(value, name, type);
   }
}