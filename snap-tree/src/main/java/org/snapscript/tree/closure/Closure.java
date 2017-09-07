package org.snapscript.tree.closure;

import org.snapscript.core.LocalScopeExtractor;
import org.snapscript.core.Compilation;
import org.snapscript.core.Evaluation;
import org.snapscript.core.Module;
import org.snapscript.core.Path;
import org.snapscript.core.Scope;
import org.snapscript.core.Statement;
import org.snapscript.core.Value;
import org.snapscript.core.function.Function;
import org.snapscript.core.function.Signature;
import org.snapscript.core.function.FunctionCompiler;
import org.snapscript.tree.Expression;
import org.snapscript.tree.ExpressionStatement;

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
   
      private final LocalScopeExtractor extractor;
      private final ClosureParameterList parameters;
      private final ClosureBuilder builder;
      private final Module module;

      public CompileResult(ClosureParameterList parameters, Statement closure, Module module){
         this.extractor = new LocalScopeExtractor(false, true);
         this.builder = new ClosureBuilder(closure, module);
         this.parameters = parameters;
         this.module = module;
      }
      
      @Override
      public Value evaluate(Scope scope, Object left) throws Exception {
         Scope parent = module.getScope();
         Signature signature = parameters.create(parent);
         Scope capture = extractor.extract(scope);
         FunctionCompiler compiler = builder.create(signature, capture); // creating new function each time
         Function function = compiler.create(capture);
         
         compiler.compile(capture);
         
         return Value.getTransient(function);
      }
   }
}