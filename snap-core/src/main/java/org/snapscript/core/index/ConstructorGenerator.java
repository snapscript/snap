package org.snapscript.core.index;

import static org.snapscript.core.Reserved.TYPE_CONSTRUCTOR;

import java.lang.reflect.Constructor;

import org.snapscript.core.InternalStateException;
import org.snapscript.core.Type;
import org.snapscript.core.bridge.BridgeBuilder;
import org.snapscript.core.bridge.BridgeProvider;
import org.snapscript.core.function.Function;
import org.snapscript.core.function.Invocation;
import org.snapscript.core.function.InvocationFunction;
import org.snapscript.core.function.Signature;

public class ConstructorGenerator {

   private final SignatureGenerator generator;
   private final BridgeProvider provider;
   
   public ConstructorGenerator(TypeIndexer indexer, BridgeProvider provider) {
      this.generator = new SignatureGenerator(indexer);
      this.provider = provider;
   }
   
   public Function generate(Type type, Constructor constructor, Class[] types, int modifiers) {
      BridgeBuilder builder = provider.create();
      Signature signature = generator.generate(type, constructor);
      Invocation invocation = builder.thisConstructor(type, constructor);
      
      try {
         invocation = new ConstructorInvocation(invocation, constructor);
         
         if(!constructor.isAccessible()) {
            constructor.setAccessible(true);
         }
         return new InvocationFunction(signature, invocation, type, type, TYPE_CONSTRUCTOR, modifiers);
      } catch(Exception e) {
         throw new InternalStateException("Could not create function for " + constructor, e);
      }
   } 
}