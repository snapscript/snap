package org.snapscript.core.variable.index;

import java.util.concurrent.atomic.AtomicReference;

import org.snapscript.core.Context;
import org.snapscript.core.constraint.Constraint;
import org.snapscript.core.function.Function;
import org.snapscript.core.module.Module;
import org.snapscript.core.property.Property;
import org.snapscript.core.property.PropertyValue;
import org.snapscript.core.scope.Scope;
import org.snapscript.core.type.Type;
import org.snapscript.core.type.TypeExtractor;
import org.snapscript.core.variable.Value;
import org.snapscript.core.variable.bind.VariableFinder;

public class ClosurePointer implements VariablePointer<Function> {

   private final AtomicReference<Property> reference;
   private final VariableFinder finder;
   private final String name;
   
   public ClosurePointer(VariableFinder finder, String name) {
      this.reference = new AtomicReference<Property>();
      this.finder = finder;
      this.name = name;
   }
   
   @Override
   public Constraint compile(Scope scope, Constraint left) {
      Property accessor = reference.get();
      
      if(accessor == null) {
         Module module = scope.getModule();
         Context context = module.getContext();
         TypeExtractor extractor = context.getExtractor();
         Type type = extractor.getType(Function.class);
         Property match = finder.findAll(scope, type, name);
         
         if(match != null) {
            reference.set(match);
            return match.getConstraint();
         }
         return null;
      }
      return accessor.getConstraint();
   }
   
   @Override
   public Value get(Scope scope, Function left) {
      Property accessor = reference.get();
      
      if(accessor == null) {
         Module module = scope.getModule();
         Context context = module.getContext();
         TypeExtractor extractor = context.getExtractor();
         Type type = extractor.getType(Function.class);
         Property match = finder.findAll(scope, type, name);
         
         if(match != null) {
            reference.set(match);
            return new PropertyValue(match, left, name);
         }
         return null;
      }
      return new PropertyValue(accessor, left, name);
   }
}
