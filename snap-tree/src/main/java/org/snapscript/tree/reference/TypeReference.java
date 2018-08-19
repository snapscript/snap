package org.snapscript.tree.reference;

import org.snapscript.core.NameFormatter;
import org.snapscript.core.constraint.Constraint;
import org.snapscript.core.constraint.ConstraintWrapper;
import org.snapscript.core.error.InternalStateException;
import org.snapscript.core.scope.Scope;
import org.snapscript.core.scope.index.Local;
import org.snapscript.core.type.Type;
import org.snapscript.core.variable.Value;

public class TypeReference extends TypeNavigation {
   
   private ConstraintWrapper mapper;
   private NameFormatter formatter;
   private TypeNavigation[] list;
   private TypeNavigation root;
   private Value type;
   
   public TypeReference(TypeNavigation root, TypeNavigation... list) {
      this.mapper = new ConstraintWrapper();
      this.formatter = new NameFormatter();
      this.root = root;
      this.list = list;
   }
   
   @Override
   public String qualify(Scope scope, String left) throws Exception {
      String name = root.qualify(scope, left);
      
      for(int i = 0; i < list.length; i++) {
         name = list[i].qualify(scope, name);
      }
      return name;
   }
   
   @Override
   public Value evaluate(Scope scope, Value left) throws Exception {
      if(type == null) {
         Value result = root.evaluate(scope, left);
         
         for(int i = 0; i < list.length; i++) {
            Object next = result.getValue();
            
            if(next == null) {
               throw new InternalStateException("Could not determine type");
            }
            result = list[i].evaluate(scope, result);
         }
         type = create(scope, result);
      }
      return type;
   }
   
   private Value create(Scope scope, Value result) throws Exception {
      String name = result.getName();
      Object value = result.getValue();
      Constraint constraint = mapper.toConstraint(value);
      
      if(Constraint.class.isInstance(value)){
         return result;
      }
      if(name != null) {
         Type type = constraint.getType(scope);
         String defined = type.getName();
         String actual = formatter.formatInnerName(defined);
         
         if(!name.equals(actual)) { 
            Constraint parameter = mapper.toConstraint(value, name);
            return Local.getConstant(value, name, parameter);
         }                 
      }
      return Local.getConstant(value, null, constraint);
   }
}