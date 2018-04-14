package org.snapscript.tree.closure;

import static org.snapscript.core.constraint.Constraint.NONE;

import java.util.concurrent.atomic.AtomicReference;

import org.snapscript.core.Compilation;
import org.snapscript.core.Evaluation;
import org.snapscript.core.Statement;
import org.snapscript.core.module.Module;
import org.snapscript.core.module.Path;
import org.snapscript.core.scope.Scope;
import org.snapscript.core.scope.index.LocalScopeExtractor;
import org.snapscript.core.type.Type;
import org.snapscript.core.variable.Value;
import org.snapscript.core.constraint.Constraint;
import org.snapscript.core.function.Function;
import org.snapscript.core.function.FunctionBody;
import org.snapscript.core.function.Signature;
import org.snapscript.tree.Expression;
import org.snapscript.tree.ExpressionStatement;
import org.snapscript.tree.compile.ClosureScopeCompiler;

public class Closure implements Compilation {
   
   private final ClosureParameterList parameters;
   private final ExpressionStatement compilation;
   private final Statement statement;
   
   public Closure(ClosureParameterList parameters, Statement statement){  
      this(parameters, statement, null);
   }  
   
   public Closure(ClosureParameterList parameters, Expression expression){
      this(parameters, null, expression);
   }
   
   public Closure(ClosureParameterList parameters, Statement statement, Expression expression){
      this.compilation = new ExpressionStatement(expression);
      this.parameters = parameters;
      this.statement = statement;
   }
   
   @Override
   public Evaluation compile(Module module, Path path, int line) throws Exception {
      Statement closure = statement;
      
      if(closure == null) {
         closure = compilation.compile(module, path, line);
      }
      return new CompileResult(parameters, closure, module);
   }
  
   private static class CompileResult extends Evaluation {
   
      private final AtomicReference<FunctionBody> reference;
      private final LocalScopeExtractor extractor;
      private final ClosureParameterList parameters;
      private final ClosureScopeCompiler compiler;
      private final ClosureBuilder builder;
      private final Module module;

      public CompileResult(ClosureParameterList parameters, Statement closure, Module module){
         this.reference = new AtomicReference<FunctionBody>();
         this.extractor = new LocalScopeExtractor(false, true);
         this.builder = new ClosureBuilder(closure, module);
         this.compiler = new ClosureScopeCompiler();
         this.parameters = parameters;
         this.module = module;
      }
      
      @Override
      public void define(Scope scope) throws Exception {
         Scope parent = module.getScope();
         Signature signature = parameters.create(parent);
         Scope capture = extractor.extract(scope);
         FunctionBody body = builder.create(signature, capture); // creating new function each time
         Function function = body.create(capture);  
         Constraint constraint = function.getConstraint();
         
         body.define(capture);
         constraint.getType(scope);
         reference.set(body);
      }
      
      @Override
      public Constraint compile(Scope scope, Constraint left) throws Exception {
         Type type = scope.getType();
         FunctionBody body = reference.get();
         Scope capture = extractor.extract(scope);
         Function function = body.create(capture);   
         Scope combined = compiler.compile(capture, type, function);
         
         body.compile(combined);
        
         return NONE;
      }
      
      @Override
      public Value evaluate(Scope scope, Object left) throws Exception {
         FunctionBody handle = reference.get();
         Scope capture = extractor.extract(scope);
         Function function = handle.create(capture);
         
         return Value.getTransient(function);
      }
   }
}