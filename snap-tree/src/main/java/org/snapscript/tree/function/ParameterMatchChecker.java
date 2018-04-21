package org.snapscript.tree.function;

import org.snapscript.core.scope.Scope;
import org.snapscript.core.type.Type;
import org.snapscript.core.constraint.Constraint;
import org.snapscript.core.function.Parameter;

public class ParameterMatchChecker {

   private ParameterDeclaration[] list;
   
   public ParameterMatchChecker(ParameterDeclaration... list) {
      this.list = list;
   }
   
   public boolean isAbsolute(Scope scope) throws Exception {
      int length = list.length;
      
      if(length > 0) {
         ParameterDeclaration declaration = list[length-1];
         
         if(declaration != null) {
            Parameter parameter = declaration.get(scope);
            Constraint constraint = parameter.getConstraint();
            Type type = constraint.getType(scope);
            
            if(type != null) {
               Type entry = type.getEntry();
               
               if(entry != null) {
                  return false;
               }
            }
         }
      }
      return true;
   }
   
   public boolean isVariable(Scope scope) throws Exception {
      int length = list.length;
      
      for(int i = 0; i < length - 1; i++) {
         ParameterDeclaration declaration = list[i];
         
         if(declaration != null) {
            Parameter parameter = declaration.get(scope);
            String name = parameter.getName();
         
            if(parameter.isVariable()) {
               throw new IllegalStateException("Illegal declaration " + name + "... at index " + i);
            }
         }
         
      }
      if(length > 0) {
         ParameterDeclaration declaration = list[length-1];
         
         if(declaration != null) {
            Parameter parameter = declaration.get(scope);
            
            if(parameter.isVariable()) {
               return true;
            }
         }
      }
      return false;
   }
}