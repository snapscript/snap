package org.snapscript.tree.define;

import java.util.ArrayList;
import java.util.List;

import org.snapscript.core.constraint.Constraint;
import org.snapscript.core.scope.Scope;

public class GenericList {

   private final GenericDeclaration[] declarations;
   
   public GenericList(GenericDeclaration... declarations) {
      this.declarations = declarations;
   }
   
   public List<Constraint> getGenerics(Scope scope) throws Exception {
      List<Constraint> generics = new ArrayList<Constraint>();
      
      if(declarations != null) {
         for(GenericDeclaration declaration : declarations) {
            Constraint constraint = declaration.getGeneric(scope);
            generics.add(constraint);
         }
      }
      return generics;
   }
}
