package org.snapscript.tree;

import java.util.concurrent.atomic.AtomicBoolean;

import org.snapscript.core.Bug;
import org.snapscript.core.Constraint;
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
   private Constraint constraint;
   private Statement execute;
   private Statement compile;

   public StatementInvocationBuilder(Signature signature, Statement compile, Statement execute, Constraint constraint) {
      this(signature, compile, execute, constraint, false);
   }
   
   public StatementInvocationBuilder(Signature signature, Statement compile, Statement execute, Constraint constraint, boolean closure) {
      if(constraint == null){
         throw new IllegalArgumentException();
      }
      this.extractor = new ParameterExtractor(signature, closure);
      this.aligner = new SignatureAligner(signature);
      this.constraint = constraint;
      this.execute = execute;
      this.compile = compile;
   }
   
   @Override
   public Invocation define(Scope scope) throws Exception {
      if(invocation == null) {
         invocation = compile(scope);
      }
      return invocation;
   }
   
   @Bug("this is totally fucked")
   private Invocation compile(Scope scope) throws Exception {
      Module module = scope.getModule();
      Context context = module.getContext();
      ConstraintMatcher matcher = context.getMatcher();
      Scope inner = scope.getStack();
      if(compile != null) {
         extractor.define(inner); // count parameters
         compile.define(inner); // start counting from here
        //compile.compile(scope);
      }
      return new ResultConverter(matcher, execute, inner, execute == compile);
   }

   private class ResultConverter implements Invocation<Object> {
      
      private final ConstraintMatcher matcher;
      private final AtomicBoolean compile;
      private final Statement statement;
      private final Scope define;
      
      public ResultConverter(ConstraintMatcher matcher, Statement statement, Scope define, boolean compile) {
         this.compile = new AtomicBoolean(compile);
         this.statement = statement;
         this.matcher = matcher;
         this.define = define;
      }
      
      @Override
      public Object invoke(Scope scope, Object object, Object... list) throws Exception {
         Object[] arguments = aligner.align(list);
         Scope inner = extractor.extract(scope, arguments);
         
         if(compile.compareAndSet(false, true)) {
            extractor.define(inner); // update stack
            statement.define(inner); // this is for static calls
            //statement.compile(inner); /// ????????????????????????????????????????? do we need this??
         }
         Type y = constraint.getType(scope);
         ConstraintConverter converter = matcher.match(y);
         // THIS IS WRONG!!
         Result result = statement.compile(inner).execute(inner);
         Object value = result.getValue();
         
         if(value != null) {
            value = converter.assign(value);
         }
         return value;
      }
   }
}