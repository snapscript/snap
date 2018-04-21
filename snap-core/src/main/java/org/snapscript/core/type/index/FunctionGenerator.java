package org.snapscript.core.type.index;

import java.lang.reflect.Method;

import org.snapscript.core.InternalStateException;
import org.snapscript.core.constraint.Constraint;
import org.snapscript.core.function.Function;
import org.snapscript.core.function.Invocation;
import org.snapscript.core.function.InvocationFunction;
import org.snapscript.core.function.Signature;
import org.snapscript.core.platform.Platform;
import org.snapscript.core.platform.PlatformProvider;
import org.snapscript.core.type.Type;

public class FunctionGenerator {
   
   private final GenericConstraintExtractor extractor;
   private final SignatureGenerator generator;
   private final DefaultMethodChecker checker;
   private final PlatformProvider provider;
   
   public FunctionGenerator(TypeIndexer indexer, PlatformProvider provider) {
      this.extractor = new GenericConstraintExtractor(indexer); 
      this.generator = new SignatureGenerator(indexer);     
      this.checker = new DefaultMethodChecker();
      this.provider = provider;
   }

   public Function generate(Type type, Method method, String name, int modifiers) {
      Signature signature = generator.generate(type, method);

      try {
         Platform platform = provider.create();
         Invocation invocation = platform.createMethod(type, method);
         
         if(checker.check(method)) {
            invocation = new DefaultMethodInvocation(method);
         } else {
            invocation = new MethodInvocation(invocation, method);
         }
         Constraint constraint = extractor.extractMethod(method, modifiers);
         
         if(!method.isAccessible()) {
            method.setAccessible(true);
         }
         return new InvocationFunction(signature, invocation, type, constraint, name, modifiers);
      } catch(Exception e) {
         throw new InternalStateException("Could not create function for " + method, e);
      }
   }
}