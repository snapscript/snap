package org.snapscript.tree.construct;

import org.snapscript.core.constraint.Constraint;
import org.snapscript.core.scope.Scope;
import org.snapscript.core.type.Type;
import org.snapscript.tree.ArgumentList;

public class ConstructArgumentList {

   private final Constraint constraint;
   private final ArgumentList list;
   private final Object[] empty;
   
   public ConstructArgumentList(Constraint constraint, ArgumentList list) {
      this.empty = new Object[]{};
      this.constraint = constraint;
      this.list = list;
   }

   public void define(Scope scope) throws Exception {
      if(list != null) {
         list.define(scope);
      }
   }
   
   public Constraint compile(Scope scope, Type type) throws Exception {
      if(list != null) {
         list.compile(scope, type);
      }
      return constraint;
   }
   
   public Object[] create(Scope scope, Type type) throws Exception {
      Class real = type.getType();
      
      if(list != null) {
         if(real == null) {
            return list.create(scope, type);
         }
         return list.create(scope);
      }
      if(real == null) {
         return new Object[]{type};
      }
      return empty;
   }
   
}
