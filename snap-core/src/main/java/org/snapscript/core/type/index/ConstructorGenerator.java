package org.snapscript.core.type.index;

import static org.snapscript.core.Reserved.TYPE_CONSTRUCTOR;

import java.lang.reflect.Constructor;

import org.snapscript.core.InternalStateException;
import org.snapscript.core.type.Type;
import org.snapscript.core.constraint.Constraint;
import org.snapscript.core.function.Function;
import org.snapscript.core.function.Invocation;
import org.snapscript.core.function.InvocationFunction;
import org.snapscript.core.function.Signature;
import org.snapscript.core.platform.Platform;
import org.snapscript.core.platform.PlatformProvider;

public class ConstructorGenerator {

   private final SignatureGenerator generator;
   private final PlatformProvider provider;
   
   public ConstructorGenerator(TypeIndexer indexer, PlatformProvider provider) {
      this.generator = new SignatureGenerator(indexer);
      this.provider = provider;
   }
   
   public Function generate(Type type, Constructor constructor, Class[] types, int modifiers) {
      Platform platform = provider.create();
      Signature signature = generator.generate(type, constructor);
      Invocation invocation = platform.createConstructor(type, constructor);
      Constraint constraint = Constraint.getConstraint(type);
      
      try {
         invocation = new ConstructorInvocation(invocation, constructor);
         
         if(!constructor.isAccessible()) {
            constructor.setAccessible(true);
         }
         return new InvocationFunction(signature, invocation, type, constraint, TYPE_CONSTRUCTOR, modifiers);
      } catch(Exception e) {
         throw new InternalStateException("Could not create function for " + constructor, e);
      }
   } 
}