package org.snapscript.tree.closure;

import static org.snapscript.core.constraint.Constraint.NONE;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import org.snapscript.core.Compilation;
import org.snapscript.core.Evaluation;
import org.snapscript.core.Statement;
import org.snapscript.core.constraint.Constraint;
import org.snapscript.core.function.Function;
import org.snapscript.core.function.FunctionBody;
import org.snapscript.core.function.Signature;
import org.snapscript.core.module.Module;
import org.snapscript.core.module.Path;
import org.snapscript.core.scope.Scope;
import org.snapscript.core.type.Type;
import org.snapscript.core.variable.Value;
import org.snapscript.tree.ExpressionStatement;
import org.snapscript.tree.ModifierChecker;
import org.snapscript.tree.ModifierList;
import org.snapscript.tree.compile.ClosureScopeCompiler;
import org.snapscript.tree.constraint.GenericList;

public class Closure implements Compilation {
   
   private final ClosureParameterList parameters;
   private final ExpressionStatement compilation;
   private final ModifierList modifiers;
   private final GenericList generics;
   private final Statement statement;
   
   public Closure(ModifierList modifiers, GenericList generics, ClosureParameterList parameters, Statement statement){
      this(modifiers, generics, parameters, statement, null);
   }  
   
   public Closure(ModifierList modifiers, GenericList generics, ClosureParameterList parameters, Evaluation expression){
      this(modifiers, generics, parameters, null, expression);
   }
   
   public Closure(ModifierList modifiers, GenericList generics, ClosureParameterList parameters, Statement statement, Evaluation expression){
      this.compilation = new ExpressionStatement(expression);
      this.parameters = parameters;
      this.statement = statement;
      this.modifiers = modifiers;
      this.generics = generics;
   }
   
   @Override
   public Evaluation compile(Module module, Path path, int line) throws Exception {
      Statement closure = statement;
      
      if(closure == null) {
         closure = compilation.compile(module, path, line);
      }
      return new CompileResult(modifiers, generics, parameters, closure, module);
   }
  
   private static class CompileResult extends Evaluation {
   
      private final AtomicReference<FunctionBody> reference;
      private final ClosureParameterList parameters;
      private final ClosureScopeCompiler compiler;
      private final ClosureBuilder builder;
      private final ModifierChecker checker;
      private final GenericList generics;
      private final Module module;

      public CompileResult(ModifierList modifiers, GenericList generics, ClosureParameterList parameters, Statement closure, Module module){
         this.reference = new AtomicReference<FunctionBody>();
         this.builder = new ClosureBuilder(closure, module);
         this.compiler = new ClosureScopeCompiler(generics);
         this.checker = new ModifierChecker(modifiers);
         this.parameters = parameters;
         this.generics = generics;
         this.module = module;
      }
      
      @Override
      public void define(Scope scope) throws Exception {
         int modifiers = checker.getModifiers();
         Scope capture = compiler.define(scope, null);
         List<Constraint> constraints = generics.getGenerics(capture);
         Signature signature = parameters.create(capture, constraints);
         FunctionBody body = builder.create(signature, capture, modifiers); // creating new function each time
         
         body.define(capture);
         reference.set(body);
      }
      
      @Override
      public Constraint compile(Scope scope, Constraint left) throws Exception {
         Type type = scope.getType();
         FunctionBody body = reference.get();
         Function function = body.create(scope);   
         Scope combined = compiler.compile(scope, type, function);
         
         body.compile(combined);
        
         return NONE;
      }
      
      @Override
      public Value evaluate(Scope scope, Value left) throws Exception {
         FunctionBody handle = reference.get();
         Scope capture = compiler.extract(scope, null);
         Function function = handle.create(capture);
         
         return Value.getTransient(function);
      }
   }
}