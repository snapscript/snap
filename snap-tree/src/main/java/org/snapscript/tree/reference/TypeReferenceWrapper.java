package org.snapscript.tree.reference;

import org.snapscript.core.Entity;
import org.snapscript.core.NameFormatter;
import org.snapscript.core.constraint.Constraint;
import org.snapscript.core.constraint.ConstraintWrapper;
import org.snapscript.core.scope.Scope;
import org.snapscript.core.scope.index.Local;
import org.snapscript.core.type.Type;
import org.snapscript.core.variable.Value;

public class TypeReferenceWrapper {

   private final ConstraintWrapper mapper;
   private final NameFormatter formatter;

   public TypeReferenceWrapper() {
      this.mapper = new ConstraintWrapper();
      this.formatter = new NameFormatter();
   }

   public Value toValue(Scope scope, Constraint type, String name) throws Exception {
      return Local.getConstant(type, name, type);
   }

   public Value toValue(Scope scope, Entity entity, String name) throws Exception {
      Constraint constraint = mapper.toConstraint(entity);

      if(name != null) {
         Type type = constraint.getType(scope);
         String defined = type.getName();
         String actual = formatter.formatInnerName(defined);

         if(!name.equals(actual)) {
            Constraint parameter = mapper.toConstraint(entity, name);
            return Local.getConstant(entity, name, parameter);
         }
      }
      return Local.getConstant(entity, null, constraint);
   }
}
