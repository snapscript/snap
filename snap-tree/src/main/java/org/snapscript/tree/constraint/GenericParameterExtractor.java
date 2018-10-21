package org.snapscript.tree.constraint;

import static org.snapscript.core.scope.index.CaptureType.GENERICS;

import java.util.List;

import org.snapscript.core.constraint.Constraint;
import org.snapscript.core.scope.Scope;
import org.snapscript.core.scope.index.CaptureScopeExtractor;
import org.snapscript.core.scope.index.Table;

public class GenericParameterExtractor {
   
   private final CaptureScopeExtractor extractor;
   private final GenericList generics;
   
   public GenericParameterExtractor(GenericList generics) {
      this.extractor = new CaptureScopeExtractor(GENERICS);
      this.generics = generics;
   }

   public Scope extract(Scope local) throws Exception {
      List<Constraint> constraints = generics.getGenerics(local);
      Scope scope = extractor.extract(local);    
      Table table = scope.getTable();
      int size = constraints.size();

      for(int i = 0; i < size; i++) {
         Constraint constraint = constraints.get(i);
         table.addConstraint(i, constraint);
      }
      return scope;
   }
}

