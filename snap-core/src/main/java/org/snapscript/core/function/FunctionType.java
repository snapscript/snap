package org.snapscript.core.function;

import static org.snapscript.core.Reserved.METHOD_CLOSURE;

import java.util.Collections;
import java.util.List;

import org.snapscript.core.Module;
import org.snapscript.core.Scope;
import org.snapscript.core.Type;
import org.snapscript.core.TypeScope;
import org.snapscript.core.annotation.Annotation;
import org.snapscript.core.property.Property;

public class FunctionType implements Type {
   
   private final Function function;
   private final Module module;
   private final Scope scope;
   
   public FunctionType(Signature signature, Module module) {
      this.function = new EmptyFunction(signature, METHOD_CLOSURE);
      this.scope = new TypeScope(this);
      this.module = module;
   }
   
   @Override
   public List<Annotation> getAnnotations() {
      return Collections.emptyList();
   }

   @Override
   public List<Property> getProperties() {
      return Collections.emptyList();
   }

   @Override
   public List<Function> getFunctions() {
      return Collections.singletonList(function);
   }

   @Override
   public List<Type> getTypes() {
      return Collections.emptyList();
   }

   @Override
   public Module getModule() {
      return module;
   }
   
   @Override
   public Scope getScope(){
      return scope;
   }

   @Override
   public Class getType() {
      return null;
   }

   @Override
   public Type getEntry() {
      return null;
   }

   @Override
   public String getName() {
      return null;
   }
   
   @Override
   public int getOrder() {
      return 0;
   }

}
