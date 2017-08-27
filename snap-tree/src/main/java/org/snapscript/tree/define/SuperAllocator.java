package org.snapscript.tree.define;

import org.snapscript.core.Result;
import org.snapscript.core.Scope;
import org.snapscript.core.Type;
import org.snapscript.core.TypeFactory;
import org.snapscript.core.define.Instance;
import org.snapscript.core.function.Signature;
import org.snapscript.core.function.SignatureAligner;
import org.snapscript.tree.function.ParameterExtractor;

public class SuperAllocator implements TypeAllocator {
   
   private final ParameterExtractor extractor;
   private final SignatureAligner aligner;
   private final TypeAllocator allocator;
   private final TypeFactory factory;
   
   public SuperAllocator(TypeFactory factory, TypeAllocator allocator, Signature signature) {
      this.extractor = new ParameterExtractor(signature);
      this.aligner = new SignatureAligner(signature);
      this.allocator = allocator;
      this.factory = factory;
   }

   @Override
   public Instance allocate(Scope scope, Instance object, Object... list) throws Exception {
      Type real = (Type)list[0];
      Object[] arguments = aligner.align(list);
      Scope inner = extractor.extract(object, arguments);
      Result result = factory.execute(inner, real);
      Instance base = result.getValue();
      
      return allocator.allocate(scope, base, list);
   }
}