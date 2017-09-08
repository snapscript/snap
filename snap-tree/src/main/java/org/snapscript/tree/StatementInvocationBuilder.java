package org.snapscript.tree;

import java.util.concurrent.atomic.AtomicBoolean;

import org.snapscript.core.Context;
import org.snapscript.core.Module;
import org.snapscript.core.Result;
import org.snapscript.core.Scope;
import org.snapscript.core.Statement;
import org.snapscript.core.Type;
import org.snapscript.core.convert.ConstraintConverter;
import org.snapscript.core.convert.ConstraintMatcher;
import org.snapscript.core.function.Invocation;
import org.snapscript.core.function.InvocationBuilder;
import org.snapscript.core.function.Signature;
import org.snapscript.core.function.SignatureAligner;
import org.snapscript.tree.function.ParameterExtractor;

public class StatementInvocationBuilder implements InvocationBuilder {
   
   private ParameterExtractor extractor;
   private SignatureAligner aligner;
   private Invocation invocation;
   private Statement execute;
   private Statement compile;
   private Type constraint;

   public StatementInvocationBuilder(Signature signature, Statement compile, Statement execute, Type constraint) {
      this(signature, compile, execute, constraint, false);
   }
   
   public StatementInvocationBuilder(Signature signature, Statement compile, Statement execute, Type constraint, boolean closure) {
      this.extractor = new ParameterExtractor(signature, closure);
      this.aligner = new SignatureAligner(signature);
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
      Scope inner = scope.getStack();
      
      if(compile != null) {
         extractor.compile(inner); // count parameters
         compile.compile(inner); // start counting from here - BLOCK STATEMENT MAY BE COMPILED!!
      }
      return new ResultConverter(matcher, execute, execute == compile);
   }

   private class ResultConverter implements Invocation<Object> {
      
      private final ConstraintMatcher matcher;
      private final AtomicBoolean compile;
      private final Statement statement;
      
      public ResultConverter(ConstraintMatcher matcher, Statement statement, boolean compile) {
         this.compile = new AtomicBoolean(compile);
         this.statement = statement;
         this.matcher = matcher;
      }
      
      @Override
      public Object invoke(Scope scope, Object object, Object... list) throws Exception {
         Object[] arguments = aligner.align(list);
         Scope inner = extractor.extract(scope, arguments);
         
         if(compile.compareAndSet(false, true)) {
            extractor.compile(inner); // update stack
            statement.compile(inner); // this is for static calls
         }
         ConstraintConverter converter = matcher.match(constraint); 
         Result result = statement.execute(inner);
         Object value = result.getValue();
         
         if(value != null) {
            value = converter.assign(value);
         }
         return value;
      }
   }
}
