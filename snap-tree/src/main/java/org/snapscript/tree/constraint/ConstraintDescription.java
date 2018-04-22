package org.snapscript.tree.constraint;

import java.util.List;

import org.snapscript.core.constraint.Constraint;
import org.snapscript.core.module.Module;
import org.snapscript.core.scope.Scope;
import org.snapscript.core.type.Type;

public class ConstraintDescription {
   
   private final Constraint constraint;
   private final Module module;
   
   public ConstraintDescription(Constraint constraint, Module module) {
      this.constraint = constraint;
      this.module = module;
   }
   
   public String getDescription(){
      StringBuilder builder = new StringBuilder();
      
      if(constraint != null) {
         Scope scope = module.getScope();
         Type type = constraint.getType(scope);
         List<Constraint> generics = constraint.getGenerics(scope);
         int length = generics.size();
         
         builder.append(type);
         
         if(length > 0) {
            builder.append("<");
            
            for(int i = 0; i < length; i++){
               Constraint generic = generics.get(i);
               Type bound = generic.getType(scope);
               String name = generic.getName(scope);
               
               if(i > 0) {
                  builder.append(", ");
               }
               if(bound != null) {
                  String entry = bound.getName();
                  
                  if(name != null) {
                     builder.append(name);
                     builder.append(": ");
                  }
                  builder.append(entry);
               } else {
                  if(name != null) {
                     builder.append(name);
                  }
               }
            }
            builder.append(">");
         }              
      }
      return builder.toString();
   }
   
   @Override
   public String toString() {
      return getDescription();
   }
}
