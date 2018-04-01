package org.snapscript.core.compile;

import static org.snapscript.core.Reserved.TYPE_THIS;

import java.util.List;

import org.snapscript.core.Context;
import org.snapscript.core.constraint.Constraint;
import org.snapscript.core.function.Function;
import org.snapscript.core.function.Parameter;
import org.snapscript.core.function.Signature;
import org.snapscript.core.module.Module;
import org.snapscript.core.property.Property;
import org.snapscript.core.scope.Scope;
import org.snapscript.core.scope.State;
import org.snapscript.core.scope.Value;
import org.snapscript.core.scope.index.Local;
import org.snapscript.core.scope.index.Table;
import org.snapscript.core.type.Type;
import org.snapscript.core.type.TypeLoader;

public abstract class ScopeCompiler {
   
   protected void compileProperties(Scope scope, Type type) {
      List<Property> properties = type.getProperties();
      State state = scope.getState();
      
      for(Property property : properties) {
         String name = property.getName();
         
         if(!name.equals(TYPE_THIS)) {
            Value field = compileProperty(scope, property);
            Value current = state.get(name);
            
            if(current == null) {
               state.add(name, field);
            }
         }
      }
   }

   protected Value compileProperty(Scope scope, Property property) {
      Constraint constraint = property.getConstraint();
      Type result = constraint.getType(scope);

      if(constraint.isConstant()) {
         return Value.getConstant(null, result);  
      }
      return Local.getReference(null, result);      
   }
   
   protected void compileParameters(Scope scope, Function function) {
      Signature signature = function.getSignature();
      State state = scope.getState();
      Table table = scope.getTable();
      List<Parameter> parameters = signature.getParameters();
      int count = parameters.size();
      
      for(int i = 0; i < count; i++) {
         Parameter parameter = parameters.get(i);
         String name = parameter.getName();
         Local local = compileParameter(scope, parameter);
         
         state.add(name, local);
         table.add(i, local);
      }
   }
   
   protected Local compileParameter(Scope scope, Parameter parameter) {
      String name = parameter.getName();
      Constraint constraint = parameter.getType();
      Type result = constraint.getType(scope);
      
      if(parameter.isVariable()) {
         result = compileArray(scope, result);
      }
      if(parameter.isConstant()) {
         return Local.getConstant(null, name, result);  
      }
      return Local.getReference(null, name, result);      
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
