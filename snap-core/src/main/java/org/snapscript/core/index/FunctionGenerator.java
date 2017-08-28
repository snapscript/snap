package org.snapscript.core.index;

import java.lang.reflect.Method;

import org.snapscript.core.Any;
import org.snapscript.core.InternalStateException;
import org.snapscript.core.Scope;
import org.snapscript.core.Type;
import org.snapscript.core.bridge.BridgeBuilder;
import org.snapscript.core.bridge.BridgeProvider;
import org.snapscript.core.function.Function;
import org.snapscript.core.function.Invocation;
import org.snapscript.core.function.InvocationFunction;
import org.snapscript.core.function.Signature;

public class FunctionGenerator {
   
   private final SignatureGenerator generator;
   private final DefaultMethodChecker checker;
   private final BridgeProvider provider;
   private final TypeIndexer indexer;
   
   public FunctionGenerator(TypeIndexer indexer, BridgeProvider provider) {
      this.generator = new SignatureGenerator(indexer);
      this.checker = new DefaultMethodChecker();
      this.provider = provider;
      this.indexer = indexer;
   }

   public Function generate(Type type, Method method, Class[] types, String name, int modifiers) {
      Signature signature = generator.generate(type, method);
      Class real = method.getReturnType();
      
      try {
         Scope scope = type.getScope();
         BridgeBuilder builder = provider.create(type);
         Invocation invocation = builder.thisInvocation(scope, method);
         
         if(checker.check(method)) {
            invocation = new DefaultMethodInvocation(method);
         } else {
            invocation = new MethodInvocation(invocation, method);
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