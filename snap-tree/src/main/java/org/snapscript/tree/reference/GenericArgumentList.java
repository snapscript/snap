package org.snapscript.tree.reference;

import java.util.ArrayList;
import java.util.List;

import org.snapscript.core.constraint.Constraint;
import org.snapscript.core.error.InternalStateException;
import org.snapscript.core.scope.Scope;
import org.snapscript.core.type.Type;

public class GenericArgumentList {

   private final GenericArgument[] arguments;

   public GenericArgumentList(GenericArgument... arguments) {
      this.arguments = arguments;
   }
   
   public List<String> getReferences(Scope scope) throws Exception {
      List<String> result = new ArrayList<String>();
      
      for(GenericArgument argument : arguments) {
         Constraint constraint = argument.getConstraint();
         List<String> imports = constraint.getImports(scope);
         
         result.addAll(imports);
      }
      return result;
   }
   
   public List<Constraint> getConstraints(Scope scope) throws Exception {
      List<Constraint> result = new ArrayList<Constraint>();
      
      for(GenericArgument argument : arguments) {
         Constraint constraint = argument.getConstraint();
         Type type = constraint.getType(scope);
         
         if(type == null) {
            throw new InternalStateException("Could not find constraint");
         }
         result.add(constraint);
      }
      return result;
   }
}
