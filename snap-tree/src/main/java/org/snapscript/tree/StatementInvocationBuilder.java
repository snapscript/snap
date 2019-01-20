package org.snapscript.tree;

import static org.snapscript.core.type.Phase.COMPILE;

import org.snapscript.common.Progress;
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

public class StatementInvocationBuilder implements InvocationBuilder {

   private ParameterExtractor extractor;
   private ScopeCalculator calculator;
   private SignatureAligner aligner;
   private Constraint constraint;
   private Invocation invocation;
   private Statement statement;
   private Execution execution;
   private Type type;

   public StatementInvocationBuilder(Signature signature, Statement statement, Constraint constraint, Type type, int modifiers) {
      this.extractor = new ParameterExtractor(signature, modifiers);
      this.statement = new AsyncStatement(statement, modifiers);
      this.aligner = new SignatureAligner(signature);
      this.calculator = new ScopeCalculator();
      this.constraint = constraint;
      this.type = type;
   }
   
   @Override
   public void define(Scope scope) throws Exception {
      extractor.define(scope); // count parameters
      statement.define(scope); // start counting from here
      calculator.define(scope);
      constraint.getType(scope);
   }
   
   @Override
   public void compile(Scope scope) throws Exception {
      if(execution != null) {
         throw new InternalStateException("Function has already been compiled");
      }
      if(execution == null) {
         Scope compound = calculator.compile(scope);

         if(compound == null) {
            throw new InternalStateException("Function scope could not be calculated");
         }
         execution = statement.compile(compound, constraint);
      }
   }
   
   @Override
   public Invocation create(Scope scope) throws Exception {
      if(invocation == null) {
         Progress progress = type.getProgress();

         if (progress.wait(COMPILE)) {
            if (execution == null) {
               throw new InternalStateException("Function has not been compiled");
            }
            invocation = build(scope);
         }
      }
      return invocation;
   }
   
   private Invocation build(Scope scope) throws Exception {
      Module module = scope.getModule();
      Context context = module.getContext();
      ConstraintMatcher matcher = context.getMatcher();      

      return new ExecutionInvocation(matcher, execution);
   }

   private class ExecutionInvocation implements Invocation<Object> {
      
      private final ConstraintMatcher matcher;
      private final Execution execution;
      
      public ExecutionInvocation(ConstraintMatcher matcher, Execution execution) {
         this.execution = execution;
         this.matcher = matcher;
      }

      @Override
      public Object invoke(Scope scope, Object object, Object... list) throws Exception {
         Object[] arguments = aligner.align(list);
         Scope inner = extractor.extract(scope, arguments);
         Scope stack = calculator.calculate(inner);
         Type type = constraint.getType(scope);
         ConstraintConverter converter = matcher.match(type);
         Result result = execution.execute(stack);
         Object value = result.getValue();
         
         if(value != null) {
            value = converter.assign(value);
         }
         return value;
      }
   }
}