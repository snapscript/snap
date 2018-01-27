package org.snapscript.core.shell;

import org.snapscript.common.Cache;
import org.snapscript.common.CopyOnWriteCache;
import org.snapscript.core.Module;
import org.snapscript.core.Scope;
import org.snapscript.core.Type;
import org.snapscript.core.TypeCache;
import org.snapscript.core.define.PrimitiveInstance;
import org.snapscript.core.function.Invocation;

public class ShellFactory {
   
   private static Class[] TYPES = {
      InterfaceBuilder.class,
      NativeBuilder.class,
      SerializationBuilder.class,
      ConstructorBuilder.class
   };

   private final Cache<Class, ShellBuilder> builders;
   private final TypeCache<Object> shells;
   
   public ShellFactory() {
      this.builders = new CopyOnWriteCache<Class, ShellBuilder>();
      this.shells = new TypeCache<Object>();
   }

   public Invocation createInvocation(Type type) {
      Object instance = shells.fetch(type);
      
      if(instance == null) {
         try {
            instance = createInstance(type);
            
            if(instance != null) {
               shells.cache(type, instance);
               return new ConstantInvocation(instance);
            }
         }catch(Exception e) {
            throw new IllegalStateException("Could not create instance of '" + type + "'", e);
         }
      }
      return new ConstantInvocation(instance);
   }
   
   private Object createInstance(Type type) throws Exception {
      Module module = type.getModule();
      Scope scope = type.getScope();
      Class real = type.getType();
      
      if(real != null) {
         int count = builders.size();
         
         if(count < TYPES.length) {
            for(Class entry : TYPES) {
               Object instance = entry.newInstance();
               ShellBuilder builder = (ShellBuilder)instance;
              
               builders.cache(entry, builder);
            }
         }
      for(Class entry : TYPES) {
         ShellBuilder builder = builders.fetch(entry);
         Object value = builder.create(real);
         
         if(value != null) {
            return value;
         }
      }  
      }
      return new PrimitiveInstance(module, scope, type);
   }
}
