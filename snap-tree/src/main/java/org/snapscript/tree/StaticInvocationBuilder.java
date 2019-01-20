package org.snapscript.tree;

import java.util.concurrent.atomic.AtomicBoolean;

import org.snapscript.core.Context;
import org.snapscript.core.Execution;
import org.snapscript.core.Statement;
import org.snapscript.core.constraint.Constraint;
import org.snapscript.core.convert.ConstraintConverter;
import org.snapscript.core.convert.ConstraintMatcher;
import org.snapscript.core.error.InternalStateException;
import org.snapscript.core.function.Invocation;
import org.snapscript.core.function.InvocationBuilder;
import org.snapscript.core.function.Signature;
import org.snapscript.core.function.SignatureAligner;
import org.snapscript.core.module.Module;
import org.snapscript.core.result.Result;
import org.snapscript.core.scope.Scope;
import org.snapscript.core.type.Type;
import org.snapscript.tree.function.ParameterExtractor;
import org.snapscript.tree.function.ScopeCalculator;
import org.snapscript.tree.resume.AsyncStatement;

public class StaticInvocationBuilder implements InvocationBuilder {
   
   private ParameterExtractor extractor;
   private ScopeCalculator calculator;
   private SignatureAligner aligner;
   private Constraint constraint;
   private Invocation invocation;
   private Statement statement;
   private Execution execution;
   private Execution compile;

   public StaticInvocationBuilder(Signature signature, Execution compile, Statement statement, Constraint constraint, int modifiers) {
      this.extractor = new ParameterExtractor(signature, modifiers);
      this.statement = new AsyncStatement(statement, modifiers);
      this.aligner = new SignatureAligner(signature);
      this.calculator = new ScopeCalculator();
      this.constraint = constraint;
      this.compile = compile;
   }
   
   @Override
   public void define(Scope scope) throws Exception {
      extractor.define(scope); // count parameters
      statement.define(scope);
      calculator.define(scope);
      constraint.getType(scope);
   }
   
   @Override
   public void compile(Scope scope) throws Exception {
      if(execution != null) {
         throw new InternalStateException("Function has already been compiled");
      }
      Scope compound = calculator.compile(scope);

      if(compound == null) {
         throw new InternalStateException("Function scope could not be calculated");
      }
      execution = statement.compile(compound, constraint);
   }
   
   @Override
   public Invocation create(Scope scope) throws Exception {
      if(invocation == null) {
         invocation = build(scope);
      }
      return invocation;
   }
   
   private Invocation build(Scope scope) throws Exception {
      Module module = scope.getModule();
      Context context = module.getContext();
      ConstraintMatcher matcher = context.getMatcher();      

      return new ExecutionInvocation(matcher, compile, execution);
   }

   private class ExecutionInvocation implements Invocation<Object> {
      
      private final ConstraintMatcher matcher;
      private final AtomicBoolean execute;
      private final Execution execution;
      private final Execution compile;
      
      public ExecutionInvocation(ConstraintMatcher matcher, Execution compile, Execution execution) {
         this.execute = new AtomicBoolean(false);
         this.execution = execution;
         this.matcher = matcher;
         this.compile = compile;
      }

      @Override
      public Object invoke(Scope scope, Object object, Object... list) throws Exception {
         Object[] arguments = aligner.align(list);
         Scope inner = extractor.extract(scope, arguments);
         
         if(execute.compareAndSet(false, true)) {
            compile.execute(inner); // could be a static block
         }
         Scope stack = calculator.calculate(inner);
         Result result = execution.execute(stack);
         Object value = result.getValue();
         
         if(value != null) {
            Type type = constraint.getType(scope);
            ConstraintConverter converter = matcher.match(type);

            return converter.assign(value);
         }
         return value;
      }
   }
}