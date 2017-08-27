package org.snapscript.tree.define;

import org.snapscript.core.Result;
import org.snapscript.core.Scope;
import org.snapscript.core.Statement;
import org.snapscript.core.Type;
import org.snapscript.core.function.Invocation;
import org.snapscript.core.function.Signature;
import org.snapscript.core.function.SignatureAligner;
import org.snapscript.tree.StatementConverter;
import org.snapscript.tree.function.ParameterExtractor;

public class StaticInvocation implements Invocation<Object> {

   private final StatementConverter converter;
   private final ParameterExtractor extractor;
   private final SignatureAligner aligner;
   private final Scope inner;
   
   public StaticInvocation(Signature signature, Statement statement, Scope inner, Type constraint) {
      this.converter = new StatementConverter(statement, constraint);
      this.extractor = new ParameterExtractor(signature);
      this.aligner = new SignatureAligner(signature);
      this.inner = inner;
   }
   
   @Override
   public Result invoke(Scope outer, Object object, Object... list) throws Exception {
      Object[] arguments = aligner.align(list); 
      Scope scope = extractor.extract(inner, arguments);;
      Statement statement = converter.convert(scope);
      
      return statement.execute(scope);
   }
}