package org.snapscript.tree;

import java.util.concurrent.atomic.AtomicBoolean;

import org.snapscript.core.Context;
import org.snapscript.core.Module;
import org.snapscript.core.Result;
import org.snapscript.core.ResultType;
import org.snapscript.core.Scope;
import org.snapscript.core.Statement;
import org.snapscript.core.Type;
import org.snapscript.core.convert.ConstraintConverter;
import org.snapscript.core.convert.ConstraintMatcher;
import org.snapscript.core.function.Invocation;
import org.snapscript.core.function.InvocationBuilder;
import org.snapscript.core.function.Signature;
import org.snapscript.tree.function.ParameterExtractor;

public class StatementConverter implements InvocationBuilder {
   
   private ParameterExtractor extractor;
   private Invocation invocation;
   private Statement execute;
   private Statement compile;
   private Type constraint;

   public StatementConverter(Signature signature, Statement compile, Statement execute, Type constraint) {
      this.extractor = new ParameterExtractor(signature);
      this.constraint = constraint;
      this.execute = execute;
      this.compile = compile;
   }
   
   @Override
   public Invocation create(Scope scope) throws Exception {
      if(invocation == null) {
         invocation = compile(scope);
      }
      return invocation;
   }
   
   private Invocation compile(Scope scope) throws Exception {
      Module module = scope.getModule();
      Context context = module.getContext();
      ConstraintMatcher matcher = context.getMatcher();
      ConstraintConverter converter = matcher.match(constraint);
      Scope inner = scope.getInner();
      
      if(compile != null) {
         extractor.compile(inner); // count parameters
         compile.compile(inner); // start counting from here - BLOCK STATEMENT MAY BE COMPILED!!
      }
      return new ResultConverter(converter, execute, execute == compile);
   }

   private class ResultConverter implements Invocation {
      
      private final ConstraintConverter converter;
      private final AtomicBoolean compile;
      private final Statement statement;
      
      public ResultConverter(ConstraintConverter converter, Statement statement, boolean compile) {
         this.compile = new AtomicBoolean(compile);
         this.converter = converter;
         this.statement = statement;
      }
      
      public Result invoke(Scope scope, Object object, Object... list) throws Exception {
         Scope inner = extractor.extract(scope, list);
         
         if(compile.compareAndSet(false, true)) {
            statement.compile(inner); // this is for static calls
         }
         Result result = statement.execute(inner);
         Object value = result.getValue();
         
         if(value != null) {
            value = converter.assign(value);
         }
         return Result.getNormal(value);
      }
   }
}
