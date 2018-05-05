package org.snapscript.core.constraint;

import java.util.List;

import org.snapscript.core.Entity;
import org.snapscript.core.scope.Scope;
import org.snapscript.core.type.Type;

public class ConstraintDescription {
   
   private final Constraint constraint;
   private final Entity entity;
   
   public ConstraintDescription(Constraint constraint, Entity entity) {
      this.constraint = constraint;
      this.entity = entity;
   }
   
   public String getDescription(){
      StringBuilder builder = new StringBuilder();
      
      if(constraint != null && entity != null) {
         Scope scope = entity.getScope();
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
