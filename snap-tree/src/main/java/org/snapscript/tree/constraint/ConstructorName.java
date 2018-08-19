package org.snapscript.tree.constraint;

import static java.util.Collections.EMPTY_LIST;
import static org.snapscript.core.Reserved.TYPE_CONSTRUCTOR;

import java.util.List;

import org.snapscript.core.constraint.Constraint;
import org.snapscript.core.scope.Scope;

public class ConstructorName extends FunctionName {

   public ConstructorName() {
      super(null, null);
   }

   @Override
   public String getName(Scope scope) throws Exception{ // called from outer class
      return TYPE_CONSTRUCTOR;
   }

   @Override
   public List<Constraint> getGenerics(Scope scope) throws Exception {
      return EMPTY_LIST;
   }
}