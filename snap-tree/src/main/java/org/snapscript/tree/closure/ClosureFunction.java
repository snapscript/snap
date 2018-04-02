package org.snapscript.tree.closure;

import java.util.List;

import org.snapscript.core.type.Type;
import org.snapscript.core.annotation.Annotation;
import org.snapscript.core.constraint.Constraint;
import org.snapscript.core.convert.proxy.FunctionProxy;
import org.snapscript.core.function.Function;
import org.snapscript.core.function.Invocation;
import org.snapscript.core.function.Signature;

public class ClosureFunction implements Function {
   
   private final Invocation invocation;
   private final FunctionProxy proxy;
   private final Function template;
   
   public ClosureFunction(Function template, Invocation invocation) {
      this.proxy = new FunctionProxy(this);
      this.invocation = invocation;
      this.template = template;
   }

   @Override
   public int getModifiers() {
      return template.getModifiers();
   }

   @Override
   public String getName() {
      return template.getName();
   }
   
   @Override
   public Type getType() {
      return template.getType();
   }
   
   @Override
   public Type getHandle() {
      return template.getHandle();
   }

   @Override
   public Signature getSignature() {
      return template.getSignature();
   }

   @Override
   public Constraint getConstraint() {
      return template.getConstraint();
   }

   @Override
   public List<Annotation> getAnnotations() {
      return template.getAnnotations();
   }

   @Override
   public Invocation getInvocation() {
      return invocation;
   }

   @Override
   public String getDescription() {
      return template.getDescription();
   }

   @Override
   public Object getProxy(Class type) {
      return proxy.getProxy(type);
   }

   @Override
   public Object getProxy() {
      return proxy.getProxy();
   }
   
   @Override
   public String toString(){
      return String.valueOf(template);
   }

}
