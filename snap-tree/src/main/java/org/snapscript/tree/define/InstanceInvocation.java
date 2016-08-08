package org.snapscript.tree.define;

import org.snapscript.core.Context;
import org.snapscript.core.InternalStateException;
import org.snapscript.core.Module;
import org.snapscript.core.Result;
import org.snapscript.core.ResultType;
import org.snapscript.core.Scope;
import org.snapscript.core.Statement;
import org.snapscript.core.Type;
import org.snapscript.core.convert.ConstraintConverter;
import org.snapscript.core.convert.ConstraintMatcher;
import org.snapscript.core.function.Invocation;
import org.snapscript.core.function.Signature;
import org.snapscript.core.function.SignatureAligner;
import org.snapscript.tree.function.ParameterExtractor;

public class InstanceInvocation implements Invocation<Scope> {

   private final ParameterExtractor extractor;
   private final SignatureAligner aligner;
   private final Statement statement;
   private final Type constraint;
   
   public InstanceInvocation(Signature signature, Statement statement, Type constraint) {
      this.extractor = new ParameterExtractor(signature);
      this.aligner = new SignatureAligner(signature);
      this.constraint = constraint;
      this.statement = statement;
   }
   
   @Override
   public Result invoke(Scope scope, Scope instance, Object... list) throws Exception {
      if(statement == null) {
         throw new InternalStateException("Function is abstract");
      }
      Object[] arguments = aligner.align(list); 

      if(instance == null) {
         instance = scope; // this is for super
      }
      Module module = scope.getModule();
      Context context = module.getContext();
      Scope outer = instance.getOuter();
      Scope inner = outer.getInner(); // create a writable scope
      
      if(arguments.length > 0) {
         extractor.extract(inner, arguments);
      }
      ConstraintMatcher matcher = context.getMatcher();
      ConstraintConverter converter = matcher.match(constraint);
      Result result = statement.execute(inner);
      Object value = result.getValue();
      
      if(value != null) {
         value = converter.convert(value);
      }
      return ResultType.getNormal(value);
   }

}
