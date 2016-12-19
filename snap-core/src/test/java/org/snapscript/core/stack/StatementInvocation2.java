package org.snapscript.core.stack;

import java.util.concurrent.atomic.AtomicBoolean;

import org.snapscript.core.Context;
import org.snapscript.core.Module;
import org.snapscript.core.Result;
import org.snapscript.core.ResultType;
import org.snapscript.core.Statement;
import org.snapscript.core.Type;
import org.snapscript.core.convert.ConstraintConverter;
import org.snapscript.core.convert.ConstraintMatcher;
import org.snapscript.core.function.Signature;
import org.snapscript.core.function.SignatureAligner;

public class StatementInvocation2  {

   //private final ParameterExtractor extractor;
   private final SignatureAligner aligner;
   private final AtomicBoolean compile;
   private final Statement statement;
   private final Type constraint;
   
   public StatementInvocation2(Signature signature, Statement statement, Type constraint) {
      //this.extractor = new ParameterExtractor(signature);
      this.aligner = new SignatureAligner(signature);
      this.compile = new AtomicBoolean();
      this.constraint = constraint;
      this.statement = statement;
   }
   
   public Result invoke(Scope2 scope, Object object, Object... list) throws Exception {
      //
      // THIS IS THE LIST OF ARGUMENTS... DO NOT NEED TO BE TAKEN FROM
      // PREVIIOUS SCOPE!!!!!
      //
      //
      Object[] arguments = aligner.align(list); 
      Module module = scope.getModule();
      Context context = module.getContext();
      State2 previous = scope.getStack(); 
      State2 next = previous.create();
      
      try {
         if(arguments.length > 0) {
            //extractor.extract(previous, next, arguments); // maybe this returns scope.getStack().create()
            //
            // State2 next = extractor.extract(previous, arguments);
            //
            
         }
         if(compile.compareAndSet(false, true)) {
            // scope = scope.getObject()
            // 
            //statement.compile(scope);
         } 
         ConstraintMatcher matcher = context.getMatcher();
         ConstraintConverter converter = matcher.match(constraint);
         //
         // scope = scope.getObject();
         //Result result = statement.execute(scope);
   //      Object value = result.getValue();
   //      
   //      if(value != null) {
   //         value = converter.convert(value);
   //      }
   //      return ResultType.getNormal(value); 
      return null;
      } finally {
         next.clear();
      }
   }
}
