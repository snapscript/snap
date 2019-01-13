package org.snapscript.tree.constraint;

import static org.snapscript.core.scope.extract.ScopePolicy.COMPILE_GENERICS;

import java.util.List;

import org.snapscript.core.constraint.Constraint;
import org.snapscript.core.scope.Scope;
import org.snapscript.core.scope.extract.ScopePolicyExtractor;
import org.snapscript.core.scope.index.Address;
import org.snapscript.core.scope.index.AddressCache;
import org.snapscript.core.scope.index.ScopeTable;

public class GenericParameterExtractor {
   
   private final ScopePolicyExtractor extractor;
   private final GenericList generics;
   
   public GenericParameterExtractor(GenericList generics) {
      this.extractor = new ScopePolicyExtractor(COMPILE_GENERICS);
      this.generics = generics;
   }

   public Scope extract(Scope local) throws Exception {
      List<Constraint> constraints = generics.getGenerics(local);
      Scope scope = extractor.extract(local);
      ScopeTable table = scope.getTable();
      int size = constraints.size();

      for(int i = 0; i < size; i++) {
         Constraint constraint = constraints.get(i);
         Address address = AddressCache.getAddress(i);
         
         table.addConstraint(address, constraint);
      }
      return scope;
   }
}

