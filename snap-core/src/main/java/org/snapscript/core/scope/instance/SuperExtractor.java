package org.snapscript.core.scope.instance;

import java.util.List;

import org.snapscript.core.constraint.Constraint;
import org.snapscript.core.scope.Scope;
import org.snapscript.core.type.Type;

public class SuperExtractor {
   
   public SuperExtractor() {
      super();
   }
   
   public Type extractor(Type type) {
      List<Constraint> types = type.getTypes();
      Scope scope = type.getScope();
      
      for(Constraint base : types) {
         return base.getType(scope);
      }
      return null;
   }

}