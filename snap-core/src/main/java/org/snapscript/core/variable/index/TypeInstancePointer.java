package org.snapscript.core.variable.index;

import java.util.concurrent.atomic.AtomicReference;

import org.snapscript.core.Context;
import org.snapscript.core.constraint.Constraint;
import org.snapscript.core.module.Module;
import org.snapscript.core.scope.Scope;
import org.snapscript.core.type.Type;
import org.snapscript.core.type.TypeExtractor;
import org.snapscript.core.variable.Value;
import org.snapscript.core.variable.bind.VariableFinder;
import org.snapscript.core.variable.bind.VariableResult;

public class TypeInstancePointer implements VariablePointer {
   
   private final AtomicReference<VariableResult> reference;
   private final VariableFinder finder;
   private final String name;
   
   public TypeInstancePointer(VariableFinder finder, String name) {
      this.reference = new AtomicReference<VariableResult>();
      this.finder = finder;
      this.name = name;
   }
   
   @Override
   public Constraint getConstraint(Scope scope, Constraint left) {
      VariableResult result = reference.get();
      
      if(result == null) {
         Type type = left.getType(scope);
         
         if(type != null) {
            VariableResult match = finder.findAll(scope, type, name);
            
            if(match != null) {
               reference.set(match);
               return match.getConstraint(left);
            }
         }
         return null;
      }
      return result.getConstraint(left);
   }
   
   @Override
   public Value getValue(Scope scope, Value left) {
      Object object = left.getValue();
      VariableResult result = reference.get();
      
      if(result == null) {
         Module module = scope.getModule();
         Context context = module.getContext();
         TypeExtractor extractor = context.getExtractor();
         Type type = extractor.getType(object);
         VariableResult match = finder.findAll(scope, type, name);
         
         if(match != null) {
            reference.set(match);
            return match.getValue(object);
         }
         return null;
      }
      return result.getValue(object);
   }

}