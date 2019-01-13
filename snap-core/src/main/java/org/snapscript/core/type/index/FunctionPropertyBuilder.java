package org.snapscript.core.type.index;

import static org.snapscript.core.ModifierType.ABSTRACT;
import static org.snapscript.core.ModifierType.OVERRIDE;

import java.util.List;

import org.snapscript.core.Context;
import org.snapscript.core.constraint.Constraint;
import org.snapscript.core.error.InternalStateException;
import org.snapscript.core.function.AccessorProperty;
import org.snapscript.core.function.Function;
import org.snapscript.core.function.Parameter;
import org.snapscript.core.function.Signature;
import org.snapscript.core.function.bind.FunctionBinder;
import org.snapscript.core.function.bind.FunctionMatcher;
import org.snapscript.core.module.Module;
import org.snapscript.core.property.Property;
import org.snapscript.core.type.Type;

public class FunctionPropertyBuilder {

   private static final int MODIFIERS = OVERRIDE.mask | ABSTRACT.mask;

   public FunctionPropertyBuilder() {
      super();
   }
   
   public Property create(Function function, String name) throws Exception {
      String identifier = function.getName();
      Type type = function.getSource();
      Module module = type.getModule();
      Context context = module.getContext();
      FunctionBinder binder = context.getBinder();
      FunctionMatcher matcher = binder.bind(identifier);
      Signature signature = function.getSignature();
      List<Parameter> names = signature.getParameters();
      Constraint constraint = function.getConstraint();
      int modifiers = function.getModifiers(); 
      int count = names.size();
      
      if(count > 0) {
         throw new InternalStateException("Function '" + function + "' is not a valid property");
      }
      FunctionAccessor accessor = new FunctionAccessor(matcher, module, identifier);
      AccessorProperty property = new AccessorProperty(name, name, type, constraint, accessor, modifiers & ~MODIFIERS);

      return property;
   }
}