package org.snapscript.tree.closure;

import java.util.List;

import org.snapscript.core.constraint.Constraint;
import org.snapscript.core.function.Signature;
import org.snapscript.core.scope.Scope;
import org.snapscript.tree.function.ParameterDeclaration;
import org.snapscript.tree.function.ParameterList;

public class ClosureParameterList {
   
   private final ParameterList multiple;
   private final ParameterList single;
   
   public ClosureParameterList() {
      this(null, null);
   }
  
   public ClosureParameterList(ParameterList multiple) {
      this(multiple, null);
   }
   
   public ClosureParameterList(ParameterDeclaration single) {
      this(null, single);
   }
   
   public ClosureParameterList(ParameterList multiple, ParameterDeclaration single) {
      this.single = new ParameterList(single);
      this.multiple = multiple;
   }
   
   public Signature create(Scope scope, List<Constraint> generics) throws Exception{
      if(multiple != null) {
         return multiple.create(scope, generics);
      }
      return single.create(scope, generics);
   }
}