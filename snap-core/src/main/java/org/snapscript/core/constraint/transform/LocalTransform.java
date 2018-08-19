package org.snapscript.core.constraint.transform;

import static java.util.Collections.EMPTY_MAP;

import java.util.List;

import org.snapscript.core.attribute.Attribute;
import org.snapscript.core.constraint.Constraint;
import org.snapscript.core.error.InternalStateException;
import org.snapscript.core.scope.Scope;
import org.snapscript.core.scope.State;
import org.snapscript.core.scope.index.Local;
import org.snapscript.core.scope.index.Table;

public class LocalTransform implements ConstraintTransform {
   
   private final ConstraintSource source;
   private final ConstraintIndex index;
   private final Attribute attribute;
   
   public LocalTransform(Attribute attribute){
      this.source = new EmptySource();
      this.index = new PositionIndex(source, EMPTY_MAP);
      this.attribute = attribute;
   }
   
   @Override
   public ConstraintRule apply(Constraint left){
      return new LocalRule(index, attribute, left);
   }
   
   private static class LocalRule implements ConstraintRule {
      
      private final ConstraintIndex index;
      private final Attribute attribute;
      private final Constraint left;
      
      public LocalRule(ConstraintIndex index, Attribute attribute, Constraint left) {
         this.attribute = attribute;
         this.index = index;
         this.left = left;
      }

      @Override
      public Constraint getResult(Scope scope, Constraint returns) {
         List<Constraint> defaults = attribute.getGenerics();
         int count = defaults.size();
         
         if(count > 0) {     
            Table table = scope.getTable();
            State state = scope.getState();
            Constraint first = table.getConstraint(0);    
               
            for(int i = 0; i < count; i++) {
               Constraint parameter = table.getConstraint(i);
               Constraint constraint = defaults.get(i);
               String name = constraint.getName(scope);               
               
               if(parameter != null) {
                  Local local = Local.getConstant(parameter, name);
                  state.add(name, local);
               } else {
                  Local local = Local.getConstant(constraint, name);
                  
                  if(first != null) {               
                     throw new InternalStateException("Generic parameter '" + name +"' not specified");
                  }
                  state.add(name, local);
               }
            }
         }
         return index.update(scope, left, returns);
      }

      @Override
      public Constraint getSource() {
         return null;
      }      
   }
}
