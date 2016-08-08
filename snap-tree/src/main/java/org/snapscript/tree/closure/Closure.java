package org.snapscript.tree.closure;

import java.util.concurrent.atomic.AtomicBoolean;

import org.snapscript.core.Evaluation;
import org.snapscript.core.Scope;
import org.snapscript.core.Statement;
import org.snapscript.core.Value;
import org.snapscript.core.ValueType;
import org.snapscript.core.closure.ClosureScopeExtractor;
import org.snapscript.core.function.Function;
import org.snapscript.core.function.Signature;
import org.snapscript.tree.Expression;

public class Closure implements Evaluation {
  
   private final ClosureScopeExtractor extractor;
   private final ClosureParameterList parameters;
   private final ClosureBuilder builder;
   private final AtomicBoolean compile;
   private final Statement closure;
   
   public Closure(ClosureParameterList parameters, Statement statement){  
      this(parameters, statement, null);
   }  
   
   public Closure(ClosureParameterList parameters, Expression expression){
      this(parameters, null, expression);
   }
   
   public Closure(ClosureParameterList parameters, Statement statement, Expression expression){
      this.closure = new ClosureStatement(statement, expression);
      this.extractor = new ClosureScopeExtractor();
      this.builder = new ClosureBuilder(closure);
      this.compile = new AtomicBoolean();
      this.parameters = parameters;
   }
   
   @Override
   public Value evaluate(Scope scope, Object left) throws Exception {
      Signature signature = parameters.create(scope);
      Scope capture = extractor.extract(scope);
      Function function = builder.create(signature, capture); // creating new function each time
      
      if(compile.compareAndSet(false, true)) {
         closure.compile(capture);
      }
      return ValueType.getTransient(function);
   }
}