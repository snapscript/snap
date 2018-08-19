package org.snapscript.tree.compile;

import java.util.List;

import org.snapscript.core.Bug;
import org.snapscript.core.constraint.Constraint;
import org.snapscript.core.function.Function;
import org.snapscript.core.scope.Scope;
import org.snapscript.core.scope.index.LocalScopeExtractor;
import org.snapscript.core.scope.index.Table;
import org.snapscript.core.type.Type;
import org.snapscript.tree.constraint.GenericList;

public class GenericScopeCompiler extends ScopeCompiler {
   
   private final LocalScopeExtractor extractor;
   private final GenericList generics;
   
   public GenericScopeCompiler(GenericList generics) {
      this.extractor = new LocalScopeExtractor(false, true);
      this.generics = generics;
   }

   @Override
   @Bug("generic loss of data")
   public Scope compile(Scope local, Type type, Function function) throws Exception {
      List<Constraint> constraints = generics.getGenerics(local);
      Scope scope = extractor.extract(local);    
      Scope inner = scope.getStack();
      Table table = inner.getTable();
      int size = constraints.size();

      for(int i = 0; i < size; i++) {
         Constraint constraint = constraints.get(i);
         table.addConstraint(i, constraint);
      }
      return inner;
   }
}