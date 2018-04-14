package org.snapscript.tree;

import java.util.concurrent.atomic.AtomicBoolean;

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

public class StaticInvocationBuilder implements InvocationBuilder {
   
   private ParameterExtractor extractor;
   private ResultConverter converter;
   private SignatureAligner aligner;
   private Constraint constraint;
   private Statement statement;
   private Execution execution;
   private Execution compile;

   public StaticInvocationBuilder(Signature signature, Execution compile, Statement statement, Constraint constraint) {
      this.extractor = new ParameterExtractor(signature, true);
      this.aligner = new SignatureAligner(signature);
      this.constraint = constraint;
      this.statement = statement;
      this.compile = compile;
   }
   
   @Override
   public void define(Scope scope) throws Exception {
      Scope inner = scope.getStack();
      
      extractor.define(inner); // count parameters
      statement.define(scope);     
   }
   
   @Override
   public void compile(Scope scope) throws Exception {
      if(execution != null) {
         throw new InternalStateException("Function has already been compiled");
      }
      execution = statement.compile(scope, constraint);
   }
   
   @Override
   public Invocation create(Scope scope) throws Exception {
      if(converter == null) {
         converter = build(scope);
      }
      return converter;
   }
   
   private ResultConverter build(Scope scope) throws Exception {
      Module module = scope.getModule();
      Context context = module.getContext();
      ConstraintMatcher matcher = context.getMatcher();      

      return new ResultConverter(matcher, compile, execution);
   }

   private class ResultConverter implements Invocation<Object> {
      
      private final ConstraintMatcher matcher;
      private final AtomicBoolean execute;
      private final Execution execution;
      private final Execution compile;
      
      public ResultConverter(ConstraintMatcher matcher, Execution compile, Execution execution) {
         this.execute = new AtomicBoolean(false);
         this.execution = execution;
         this.matcher = matcher;
         this.compile = compile;
      }
      
      @Override
      public Object invoke(Scope scope, Object object, Object... list) throws Exception {
         Object[] arguments = aligner.align(list);
         Scope inner = extractor.extract(scope, arguments);
         Type type = constraint.getType(scope);
         ConstraintConverter converter = matcher.match(type);
         
         if(execute.compareAndSet(false, true)) {
            compile.execute(inner); // could be a static block
         }
         Result result = execution.execute(inner);
         Object value = result.getValue();
         
         if(value != null) {
            value = converter.assign(value);
         }
         return value;
      }
   }
}