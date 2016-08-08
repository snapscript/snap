package org.snapscript.core.index;

import static org.snapscript.core.Reserved.TYPE_CONSTRUCTOR;

import java.lang.reflect.Constructor;

import org.snapscript.core.InternalStateException;
import org.snapscript.core.Type;
import org.snapscript.core.function.Function;
import org.snapscript.core.function.Invocation;
import org.snapscript.core.function.InvocationFunction;
import org.snapscript.core.function.Signature;

public class ConstructorGenerator {

   private final SignatureGenerator generator;
   
   public ConstructorGenerator(TypeIndexer indexer) {
      this.generator = new SignatureGenerator(indexer);
   }
   
   public Function generate(Type type, Constructor constructor, Class[] types, int modifiers) {
      Signature signature = generator.generate(type, constructor);
      
      try {
         Invocation invocation = new ConstructorInvocation(constructor);
         
         if(!constructor.isAccessible()) {
            constructor.setAccessible(true);
         }
         return new InvocationFunction(signature, invocation, type, type, TYPE_CONSTRUCTOR, modifiers);
      } catch(Exception e) {
         throw new InternalStateException("Could not create function for " + constructor, e);
      }
   } 
}
