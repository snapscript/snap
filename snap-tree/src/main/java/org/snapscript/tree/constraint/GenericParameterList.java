package org.snapscript.tree.constraint;

import java.util.ArrayList;
import java.util.List;

import org.snapscript.core.Bug;
import org.snapscript.core.constraint.Constraint;
import org.snapscript.core.scope.Scope;
import org.snapscript.core.scope.index.Local;
import org.snapscript.core.scope.index.Table;

public class GenericParameterList implements GenericList {

   private final GenericParameter[] declarations;
   
   public GenericParameterList(GenericParameter... declarations) {
      this.declarations = declarations;
   }
   
   @Override
   public List<Constraint> getGenerics(Scope scope) throws Exception {
      List<Constraint> generics = new ArrayList<Constraint>();
      
      if(declarations != null) {
         for(GenericParameter declaration : declarations) {
            Constraint constraint = declaration.getGeneric(scope);
            generics.add(constraint);
         }
      }
      return generics;
   }

   
   
   @Bug
   public Scope getScope(Scope scope) throws Exception {
      List<Constraint> generics = getGenerics(scope);      
      Scope inner = scope.getStack();
      Table table = inner.getTable();
      int size = generics.size();
      
      for(int i = 0; i < size; i++) {
         Constraint constraint = generics.get(i);    
         String name = constraint.getName(inner);
         Local value = Local.getConstant(constraint, name);
         
         table.addLocal(i, value);
      }
      return inner;
   }
}
