package org.snapscript.tree.compile;

import java.util.List;

import org.snapscript.core.constraint.Constraint;
import org.snapscript.core.function.Function;
import org.snapscript.core.scope.Scope;
import org.snapscript.core.scope.State;
import org.snapscript.core.scope.index.LocalScopeExtractor;
import org.snapscript.core.type.Type;
import org.snapscript.tree.constraint.GenericList;

public class FunctionScopeCompiler extends ScopeCompiler {

   protected final LocalScopeExtractor extractor;
   protected final GenericList generics;

   public FunctionScopeCompiler(GenericList generics) {
      this.extractor = new LocalScopeExtractor(false, true);
      this.generics = generics;
   }

   @Override
   public Scope define(Scope local, Type type) throws Exception{
      List<Constraint> constraints = generics.getGenerics(local);
      Scope scope = extractor.extract(local);
      State state = scope.getState();
      int size = constraints.size();

      for(int i = 0; i < size; i++) {
         Constraint constraint = constraints.get(i);
         String name = constraint.getName(scope);

         state.addConstraint(name, constraint);
      }
      return scope;
   }

   @Override
   public Scope compile(Scope local, Type type, Function function) throws Exception {
      List<Constraint> constraints = generics.getGenerics(local);
      Scope scope = extractor.extract(local);
      State state = scope.getState();
      int size = constraints.size();

      compileParameters(scope, function);

      for(int i = 0; i < size; i++) {
         Constraint constraint = constraints.get(i);
         String name = constraint.getName(scope);

         state.addConstraint(name, constraint);
      }
      return scope;
   }
}
