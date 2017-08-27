package org.snapscript.tree.define;

import org.snapscript.core.InternalStateException;
import org.snapscript.core.Result;
import org.snapscript.core.Scope;
import org.snapscript.core.Statement;
import org.snapscript.core.Type;
import org.snapscript.core.function.Invocation;
import org.snapscript.core.function.Signature;
import org.snapscript.core.function.SignatureAligner;
import org.snapscript.tree.StatementConverter;
import org.snapscript.tree.function.ParameterExtractor;

public class InstanceInvocation implements Invocation<Scope> {

   private final StatementConverter converter;
   private final ParameterExtractor extractor;
   private final SignatureAligner aligner;
   private final ThisScopeBinder binder;
   private final Statement statement;
   private final String name;

   public InstanceInvocation(Signature signature, Statement statement, Type constraint, String name) {
      this.converter = new StatementConverter(statement, constraint, false);
      this.extractor = new ParameterExtractor(signature);
      this.aligner = new SignatureAligner(signature);
      this.binder = new ThisScopeBinder();
      this.statement = statement;
      this.name = name;
   }
   
   @Override
   public Result invoke(Scope scope, Scope instance, Object... list) throws Exception {
      if(statement == null) {
         throw new InternalStateException("Function '" + name + "' is abstract");
      }
      Object[] arguments = aligner.align(list); 
      Scope outer = binder.bind(scope, instance);
      Scope inner = extractor.extract(outer, arguments);
      Statement statement = converter.convert(inner);
      
      return statement.execute(inner);
   }
}