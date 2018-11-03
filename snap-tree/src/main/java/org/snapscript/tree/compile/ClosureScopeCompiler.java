package org.snapscript.tree.compile;

import static org.snapscript.core.scope.index.CaptureType.COMPILE_CLOSURE;

import java.util.List;

import org.snapscript.core.constraint.Constraint;
import org.snapscript.core.function.Function;
import org.snapscript.core.scope.Scope;
import org.snapscript.core.scope.State;
import org.snapscript.core.type.Type;
import org.snapscript.tree.constraint.GenericList;

public class ClosureScopeCompiler extends FunctionScopeCompiler{
   
   public ClosureScopeCompiler(GenericList generics) {
      super(generics, COMPILE_CLOSURE);
   }
   
   @Override
   public Scope compile(Scope closure, Type type, Function function) throws Exception {
      List<Constraint> constraints = generics.getGenerics(closure);
      Scope scope = extractor.extract(closure);
      Scope stack = scope.getStack();
      State state = stack.getState();
      int size = constraints.size();

      compileParameters(stack, function);
      compileProperties(stack, type);

      for(int i = 0; i < size; i++) {
         Constraint constraint = constraints.get(i);
         String name = constraint.getName(stack);

         state.addConstraint(name, constraint);
      }
      return stack;
   }
   
   public Scope extract(Scope closure, Type type) throws Exception {
      return extractor.extract(closure);
   }
}
