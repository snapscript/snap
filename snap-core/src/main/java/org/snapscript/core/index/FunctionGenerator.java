package org.snapscript.core.index;

import java.lang.reflect.Method;

import org.snapscript.core.Any;
import org.snapscript.core.InternalStateException;
import org.snapscript.core.Type;
import org.snapscript.core.function.Function;
import org.snapscript.core.function.Invocation;
import org.snapscript.core.function.InvocationFunction;
import org.snapscript.core.function.Signature;

public class FunctionGenerator {
   
   private final SignatureGenerator generator;
   private final DefaultMethodChecker checker;
   private final TypeIndexer indexer;
   
   public FunctionGenerator(TypeIndexer indexer) {
      this.generator = new SignatureGenerator(indexer);
      this.checker = new DefaultMethodChecker();
      this.indexer = indexer;
   }

   public Function generate(Type type, Method method, Class[] types, String name, int modifiers) {
      Signature signature = generator.generate(type, method);
      Class real = method.getReturnType();
      
      try {
         Invocation invocation = null;
         
         if(checker.check(method)) {
            invocation = new DefaultMethodInvocation(method);
         } else {
            invocation = new MethodInvocation(method);
         }
         Type returns = indexer.loadType(real);
         
         if(!method.isAccessible()) {
            method.setAccessible(true);
         }
         if(real != void.class && real != Any.class && real != Object.class) {
            return new InvocationFunction(signature, invocation, type, returns, name, modifiers);
         }
         return new InvocationFunction(signature, invocation, type, null, name, modifiers);
      } catch(Exception e) {
         throw new InternalStateException("Could not create function for " + method, e);
      }
   }
}
