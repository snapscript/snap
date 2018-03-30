package org.snapscript.core;

import static org.snapscript.core.Reserved.TYPE_THIS;

import java.util.List;

import org.snapscript.core.constraint.Constraint;
import org.snapscript.core.function.Function;
import org.snapscript.core.function.Parameter;
import org.snapscript.core.function.Signature;
import org.snapscript.core.property.Property;

public abstract class ScopeCompiler {
   
   @Bug("TYPE_THIS is not generic")
   protected void compileProperties(Scope scope, Type type) {
      List<Property> properties = type.getProperties();
      State state = scope.getState();
      
      for(Property property : properties) {
         String name = property.getName();
         
         if(!name.equals(TYPE_THIS)) {
            Constraint constraint = property.getConstraint();
            Type result = constraint.getType(scope);
            Value field = Value.getReference(null, result);
            Value current = state.get(name);
            
            if(current == null) {
               state.add(name, field);
            }
         }
      }
   }
   
   protected void compileParameters(Scope scope, Function function) {
      Signature signature = function.getSignature();
      State state = scope.getState();
      Table table = scope.getTable();
      List<Parameter> parameters = signature.getParameters();
      boolean variable = signature.isVariable();
      int count = parameters.size();
      
      for(int i = 0; i < count; i++) {
         Parameter parameter = parameters.get(i);
         String name = parameter.getName();
         Constraint constraint = parameter.getType();
         Type result = constraint.getType(scope);
         
         if(variable && i + 1 >= count) {
            result = compileArray(scope, result);
         }
         Local local = Local.getReference(null, name, result);
         
         state.add(name, local);
         table.add(i, local);
      }
   }
   
   protected Type compileArray(Scope scope, Type type) {
      if(type != null) {
         Module module = type.getModule();
         Context context = module.getContext();
         TypeLoader loader = context.getLoader();
         String prefix = module.getName();
         String name = type.getName();
         
         return loader.resolveArrayType(prefix, name, 1);
      }
      return null;
   }   
   
   public abstract Scope compile(Scope local, Type type, Function function) throws Exception; 
}
