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
import org.snapscript.tree.function.ScopeBuilder;

public class StatementInvocationBuilder implements InvocationBuilder {
   
   private ParameterExtractor extractor;
   private ResultConverter converter;
   private SignatureAligner aligner;
   private ScopeBuilder builder;
   private Constraint constraint;
   private Statement statement;
   private Execution execution;
   private Type type;

   public StatementInvocationBuilder(Signature signature, Statement statement, Constraint constraint, Type type) {
      this(signature, statement, constraint, type, false);
   }
   
   public StatementInvocationBuilder(Signature signature, Statement statement, Constraint constraint, Type type, boolean closure) {
      this.extractor = new ParameterExtractor(signature, closure);
      this.aligner = new SignatureAligner(signature);
      this.builder = new ScopeBuilder();
      this.constraint = constraint;
      this.statement = statement;
      this.type = type;
   }
   
   @Override
   public void define(Scope scope) throws Exception {
      if(statement != null) {
         extractor.define(scope); // count parameters
         statement.define(scope); // start counting from here
         builder.define(scope);
      }
      constraint.getType(scope);
   }
   
   @Override
   public void compile(Scope scope) throws Exception {
      if(execution != null) {
         throw new InternalStateException("Function has already been compiled");
      }
      if(execution == null && statement != null) {
         scope = builder.compile(scope);
         execution = statement.compile(scope, constraint);
      }
   }
   
   @Override
   public Invocation create(Scope scope) throws Exception {
      if(converter == null) {
         Progress progress = type.getProgress();

         if(statement == null) {
            throw new InternalStateException("Function is abstract");
         }
         if (progress.wait(COMPILE)) {
            if (execution == null) {
               throw new InternalStateException("Function has not been compiled");
            }
            converter = build(scope);
         }
      }
      return converter;
   }
   
   private ResultConverter build(Scope scope) throws Exception {
      Module module = scope.getModule();
      Context context = module.getContext();
      ConstraintMatcher matcher = context.getMatcher();      

      return new ResultConverter(matcher, execution);
   }

   private class ResultConverter implements Invocation<Object> {
      
      private ConstraintMatcher matcher;
      private Execution execution;
      
      public ResultConverter(ConstraintMatcher matcher, Execution execution) {
         this.execution = execution;
         this.matcher = matcher;
      }

      @Override
      public Object invoke(Scope scope, Object object, Object... list) throws Exception {
         Object[] arguments = aligner.align(list);
         Scope inner = extractor.extract(scope, arguments);
         Scope stack = builder.extract(inner);
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