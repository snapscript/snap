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

public class StaticInvocationBuilder implements InvocationBuilder {
   
   private ParameterExtractor extractor;
   private ScopeCalculator calculator;
   private SignatureAligner aligner;
   private Constraint constraint;
   private Invocation invocation;
   private Statement statement;
   private Execution execution;
   private Execution compile;
   private int modifiers;

   public StaticInvocationBuilder(Signature signature, Execution compile, Statement statement, Constraint constraint, int modifiers) {
      this.extractor = new ParameterExtractor(signature, modifiers);
      this.aligner = new SignatureAligner(signature);
      this.calculator = new ScopeCalculator();
      this.constraint = constraint;
      this.statement = statement;
      this.compile = compile;
      this.modifiers = modifiers;
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
      scope = calculator.compile(scope);
      execution = statement.compile(scope, constraint);
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

      return new ExecutionInvocation(matcher, compile, execution, modifiers);
   }

   private class ExecutionInvocation implements Invocation<Object> {
      
      private final ConstraintMatcher matcher;
      private final AtomicBoolean execute;
      private final Execution execution;
      private final Execution compile;
      
      public ExecutionInvocation(ConstraintMatcher matcher, Execution compile, Execution execution, int modifiers) {
         this.execution = new AsyncExecution(execution, modifiers);
         this.execute = new AtomicBoolean(false);
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