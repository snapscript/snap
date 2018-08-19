package org.snapscript.tree.compile;

import java.util.List;

import org.snapscript.core.Bug;
import org.snapscript.core.constraint.Constraint;
import org.snapscript.core.function.Function;
import org.snapscript.core.scope.Scope;
import org.snapscript.core.scope.State;
import org.snapscript.core.scope.index.Index;
import org.snapscript.core.scope.index.Local;
import org.snapscript.core.scope.index.LocalScopeExtractor;
import org.snapscript.core.scope.index.Table;
import org.snapscript.core.type.Type;
import org.snapscript.tree.constraint.FunctionName;

public class FunctionScopeCompiler extends ScopeCompiler {
   
   private final LocalScopeExtractor extractor;
   private final FunctionName identifier;
   
   public FunctionScopeCompiler(FunctionName identifier) {
      this.extractor = new LocalScopeExtractor(false, true);
      this.identifier = identifier;
   }

   @Bug("generic loss of data")
   public Scope define(Scope local, Type type) throws Exception{
      List<Constraint> generics = identifier.getGenerics(local);
      Scope scope = extractor.extract(local);
      Scope stack = scope.getStack();
      State state = stack.getState();
      int size = generics.size();
      
      for(int i = 0; i < size; i++) {
         Constraint constraint = generics.get(i);    
         String name = constraint.getName(stack);
         Type param = constraint.getType(stack); // loss of generic info here
         Local value = Local.getConstant(param, name, constraint);
         
         state.add(name, value);
         //table.add(i, value);
      }
      return stack;
   }
   
   @Override
   @Bug("generic loss of data")
   public Scope compile(Scope local, Type type, Function function) throws Exception {
      List<Constraint> generics = identifier.getGenerics(local);
      Scope scope = extractor.extract(local);
      Scope stack = scope.getStack();
      State state = stack.getState();
      int size = generics.size();
      
      for(int i = 0; i < size; i++) {
         Constraint constraint = generics.get(i);    
         String name = constraint.getName(stack);
         Type param = constraint.getType(stack); // loss of generic info here
         Local value = Local.getConstant(param, name, constraint);
         
         state.add(name, value);
         //table.add(i, value);
      }
      compileParameters(stack, function);
      
      return stack;
   }
}
