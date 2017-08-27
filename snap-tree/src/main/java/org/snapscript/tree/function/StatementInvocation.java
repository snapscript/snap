package org.snapscript.tree.function;

import org.snapscript.core.Result;
import org.snapscript.core.Scope;
import org.snapscript.core.Statement;
import org.snapscript.core.Type;
import org.snapscript.core.function.Invocation;
import org.snapscript.core.function.Signature;
import org.snapscript.core.function.SignatureAligner;
import org.snapscript.tree.StatementConverter;

public class StatementInvocation implements Invocation<Object> {

   private final StatementConverter converter;
   private final ParameterExtractor extractor;
   private final SignatureAligner aligner;

   public StatementInvocation(Signature signature, Statement statement, Type constraint) {
      this.converter = new StatementConverter(statement, constraint);
      this.extractor = new ParameterExtractor(signature);
      this.aligner = new SignatureAligner(signature);
   }
   
   @Override
   public Result invoke(Scope scope, Object object, Object... list) throws Exception {
      Object[] arguments = aligner.align(list); 
      Scope outer = scope.getOuter(); 
      Scope inner = extractor.extract(outer, arguments);
      Statement statement = converter.convert(inner);
      
      return statement.execute(inner);
   }
}