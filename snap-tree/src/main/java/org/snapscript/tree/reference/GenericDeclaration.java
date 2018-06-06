package org.snapscript.tree.reference;

import java.util.List;

import org.snapscript.core.Entity;
import org.snapscript.core.Evaluation;
import org.snapscript.core.ModifierType;
import org.snapscript.core.constraint.Constraint;
import org.snapscript.core.constraint.GenericParameterConstraint;
import org.snapscript.core.error.InternalStateException;
import org.snapscript.core.module.Path;
import org.snapscript.core.scope.Scope;
import org.snapscript.core.type.Type;
import org.snapscript.core.variable.Value;

public class GenericDeclaration { 

   private final GenericArgumentList list;
   private final Evaluation type;
   private final Path path;
   private final int line;
   
   public GenericDeclaration(Evaluation type, GenericArgumentList list, Path path, int line) {
      this.type = type;
      this.list = list;
      this.path = path;
      this.line = line;
   }

   public Constraint declare(Scope scope) throws Exception { 
      try {
         Value value = type.evaluate(scope, null);
         String name = value.getName(scope);
         Entity entity = value.getValue();
         int modifiers = entity.getModifiers();
               
         if(!ModifierType.isModule(modifiers)) {
            Type type = value.getValue();
            
            if(list != null) {
               List<Constraint> generics = list.create(scope);    
               
               if(!generics.isEmpty()) {
                  return new GenericParameterConstraint(type, generics, name);
               }
            }
            return new GenericParameterConstraint(type, name);
         }
         return value;
      }catch(Exception e) {
         throw new InternalStateException("Invalid constraint in " + path + " at line " + line, e);
      }
   }
}