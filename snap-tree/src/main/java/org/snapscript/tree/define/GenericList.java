package org.snapscript.tree.define;

import java.util.ArrayList;
import java.util.List;

import org.snapscript.core.scope.Scope;

public class GenericList {

   private final GenericDeclaration[] declarations;
   
   public GenericList(GenericDeclaration... declarations) {
      this.declarations = declarations;
   }
   
   public List<Generic> getGenerics(Scope scope) throws Exception {
      List<Generic> generics = new ArrayList<Generic>();
      
      if(declarations != null) {
         for(GenericDeclaration declaration : declarations) {
            Generic generic = declaration.getGeneric(scope);
            generics.add(generic);
         }
      }
      return generics;
   }
}
