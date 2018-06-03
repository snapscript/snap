package org.snapscript.core.type.index;

import static org.snapscript.core.ModifierType.ABSTRACT;
import static org.snapscript.core.ModifierType.OVERRIDE;

import java.util.List;

import org.snapscript.core.Context;
import org.snapscript.core.constraint.Constraint;
import org.snapscript.core.function.AccessorProperty;
import org.snapscript.core.function.Function;
import org.snapscript.core.function.Parameter;
import org.snapscript.core.function.Signature;
import org.snapscript.core.module.Module;
import org.snapscript.core.property.Property;
import org.snapscript.core.stack.ThreadStack;
import org.snapscript.core.type.Type;

public class FunctionPropertyBuilder {

   private static final int MODIFIERS = OVERRIDE.mask | ABSTRACT.mask;

   public FunctionPropertyBuilder() {
      super();
   }
   
   public Property create(Function function, String name) throws Exception {
      Type type = function.getSource();
      Module module = type.getModule();
      Context context = module.getContext();
      ThreadStack stack = context.getStack();
      Signature signature = function.getSignature();
      List<Parameter> names = signature.getParameters();
      Constraint constraint = function.getConstraint();
      int modifiers = function.getModifiers(); 
      int count = names.size();
      
      if(count == 0) {
         FunctionAccessor accessor = new FunctionAccessor(function, stack);
         AccessorProperty property = new AccessorProperty(name, type, constraint, accessor, modifiers & ~MODIFIERS);
   
         return property;
      }
      return null;
   }
}