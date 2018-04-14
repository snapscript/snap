package org.snapscript.tree;

import org.snapscript.core.Context;
import org.snapscript.core.Execution;
import org.snapscript.core.InternalStateException;
import org.snapscript.core.Statement;
import org.snapscript.core.scope.Scope;
import org.snapscript.core.type.Type;
import org.snapscript.core.constraint.Constraint;
import org.snapscript.core.convert.ConstraintConverter;
import org.snapscript.core.convert.ConstraintMatcher;
import org.snapscript.core.function.Invocation;
import org.snapscript.core.function.InvocationBuilder;
import org.snapscript.core.function.Signature;
import org.snapscript.core.function.SignatureAligner;
import org.snapscript.core.module.Module;
import org.snapscript.core.result.Result;
import org.snapscript.tree.function.ParameterExtractor;

public class StatementInvocationBuilder implements InvocationBuilder {
   
   private ParameterExtractor extractor;
   private ResultConverter converter;
   private SignatureAligner aligner;
   private Constraint constraint;
   private Statement statement;
   private Execution execution;

   public StatementInvocationBuilder(Signature signature, Statement statement, Constraint constraint) {
      this(signature, statement, constraint, false);
   }
   
   public StatementInvocationBuilder(Signature signature, Statement statement, Constraint constraint, boolean closure) {
      this.extractor = new ParameterExtractor(signature, closure);
      this.aligner = new SignatureAligner(signature);
      this.constraint = constraint;
      this.statement = statement;
   }
   
   @Override
   public void define(Scope scope) throws Exception {
      Scope inner = scope.getStack();
      
      if(statement != null) {
         extractor.define(inner); // count parameters
         statement.define(inner); // start counting from here
      }
   }
   
   @Override
   public void compile(Scope scope) throws Exception {
      if(execution != null) {
         throw new InternalStateException("Function has already been compiled");
      }
      if(execution == null && statement != null) {
         execution = statement.compile(scope, constraint);
      }
   }
   
   @Override
   public Invocation create(Scope scope) throws Exception {
      if(statement == null) {
         throw new InternalStateException("Function is abstract");         
      }
      if(execution == null) {
         throw new InternalStateException("Function has not been compiled");
      }
      if(converter == null) {
         converter = build(scope);
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
         Type type = constraint.getType(scope);
         ConstraintConverter converter = matcher.match(type);
         Result result = execution.execute(inner);
         Object value = result.getValue();
         
         if(value != null) {
            value = converter.assign(value);
         }
         return value;
      }
   }
}