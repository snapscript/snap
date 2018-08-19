package org.snapscript.core.constraint.transform;

import java.util.List;

import org.snapscript.core.Bug;
import org.snapscript.core.attribute.Attribute;
import org.snapscript.core.constraint.Constraint;
import org.snapscript.core.error.InternalStateException;
import org.snapscript.core.scope.Scope;
import org.snapscript.core.scope.State;
import org.snapscript.core.scope.index.Table;

public class AttributeTransform implements ConstraintTransform {
   
   private final ConstraintTransform transform;
   private final Attribute attribute;
   
   public AttributeTransform(ConstraintTransform transform, Attribute attribute) {
      this.transform = transform;
      this.attribute = attribute;
   }

   @Override
   public ConstraintRule apply(Constraint left) {
      ConstraintRule rule = transform.apply(left);
      return new AttributeRule(rule, attribute);
   }
   
   private static class AttributeRule implements ConstraintRule {
      
      private final Attribute attribute;
      private final ConstraintRule rule;
      
      public AttributeRule(ConstraintRule rule, Attribute attribute) {
         this.attribute = attribute;
         this.rule = rule;
      }
      
      @Bug
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
                  state.addConstraint(name, parameter);
               } else {
                  if(first != null) {               
                     throw new InternalStateException("Generic parameter '" + name +"' not specified");
                  }
                  state.addConstraint(name, constraint);
               }
            }
         }
         return rule.getResult(scope, returns);
      }

      @Override
      public Constraint getSource() {
         return rule.getSource();
      }      
   }
}
