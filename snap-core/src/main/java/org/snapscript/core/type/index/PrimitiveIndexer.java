package org.snapscript.core.type.index;

import static org.snapscript.core.ModifierType.CONSTANT;
import static org.snapscript.core.ModifierType.PUBLIC;
import static org.snapscript.core.Reserved.ANY_TYPE;
import static org.snapscript.core.Reserved.DEFAULT_PACKAGE;
import static org.snapscript.core.Reserved.METHOD_EQUALS;
import static org.snapscript.core.Reserved.METHOD_HASH_CODE;
import static org.snapscript.core.Reserved.METHOD_NOTIFY;
import static org.snapscript.core.Reserved.METHOD_NOTIFY_ALL;
import static org.snapscript.core.Reserved.METHOD_TO_STRING;
import static org.snapscript.core.Reserved.METHOD_WAIT;
import static org.snapscript.core.Reserved.TYPE_CONSTRUCTOR;
import static org.snapscript.core.Reserved.TYPE_THIS;
import static org.snapscript.core.constraint.Constraint.BOOLEAN;
import static org.snapscript.core.constraint.Constraint.INTEGER;
import static org.snapscript.core.constraint.Constraint.NONE;
import static org.snapscript.core.constraint.Constraint.STRING;
import static org.snapscript.core.type.Category.CLASS;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import org.snapscript.core.constraint.Constraint;
import org.snapscript.core.function.Function;
import org.snapscript.core.function.Invocation;
import org.snapscript.core.result.Result;
import org.snapscript.core.scope.Scope;
import org.snapscript.core.scope.State;
import org.snapscript.core.scope.instance.Instance;
import org.snapscript.core.type.Type;
import org.snapscript.core.variable.Value;

public class PrimitiveIndexer{
   
   private final AtomicReference<Type> reference;
   private final PrimitiveFunctionGenerator generator;
   private final TypeIndexer indexer;
   
   public PrimitiveIndexer(TypeIndexer indexer){
      this.reference = new AtomicReference<Type>();
      this.generator = new PrimitiveFunctionGenerator(indexer);
      this.indexer = indexer;
   }

   public Type indexAny() {
      Type type  = reference.get();
      
      if(type == null) {
         Type result = indexer.defineType(DEFAULT_PACKAGE, ANY_TYPE, CLASS);
         List<Function> functions = result.getFunctions();
         Function constructor = generator.generate(result, NONE, TYPE_CONSTRUCTOR, NewInvocation.class, Object.class);
         Function hashCode = generator.generate(result, INTEGER, METHOD_HASH_CODE, HashCodeInvocation.class);
         Function toString = generator.generate(result, STRING, METHOD_TO_STRING, ToStringInvocation.class);
         Function equals = generator.generate(result, BOOLEAN, METHOD_EQUALS, EqualsInvocation.class, Object.class);
         Function wait = generator.generate(result, NONE, METHOD_WAIT, WaitInvocation.class);
         Function waitFor = generator.generate(result, NONE, METHOD_WAIT, WaitForInvocation.class, Long.class);
         Function notify = generator.generate(result, NONE, METHOD_NOTIFY, NotifyInvocation.class);
         Function notifyAll = generator.generate(result, NONE, METHOD_NOTIFY_ALL, NotifyAllInvocation.class);
         
         functions.add(constructor);
         functions.add(wait);
         functions.add(waitFor);
         functions.add(notify);
         functions.add(notifyAll);
         functions.add(hashCode);
         functions.add(equals);
         functions.add(toString);
         reference.set(type);
         
         return result;
      }
      return type;
   }
   
   private static class NewInvocation implements Invocation<Object> {
      
      private final PrimitiveInstanceBuilder constructor;
      
      public NewInvocation() {
         this.constructor = new PrimitiveInstanceBuilder();
      }
      
      @Override
      public Object invoke(Scope scope, Object object, Object... list) throws Exception {
         Type real = (Type)list[0];
         Constraint constraint = Constraint.getConstraint(real);
         Instance instance = constructor.create(scope, real);
         State state = instance.getState();
         Value value = Value.getProperty(object, constraint, PUBLIC.mask | CONSTANT.mask); // this needs to be a blank
         
         state.add(TYPE_THIS, value); // reference to 'this'
         
         return instance;
      }
   }
   
   private static class WaitInvocation implements Invocation<Object> {
      
      public WaitInvocation() {
         super();
      }
      
      @Override
      public Object invoke(Scope scope, Object object, Object... list) throws Exception {
         object.wait();
         return null;
      }
   }
   
   private static class WaitForInvocation implements Invocation<Object> {
      
      public WaitForInvocation() {
         super();
      }
      
      @Override
      public Object invoke(Scope scope, Object object, Object... list) throws Exception {
         Number argument = (Number)list[0];
         long time = argument.longValue();
         
         object.wait(time);
         return null;
      }
   }
   
   private static class NotifyInvocation implements Invocation<Object> {
      
      public NotifyInvocation() {
         super();
      }
      
      @Override
      public Result invoke(Scope scope, Object object, Object... list) throws Exception {
         object.notify();
         return null;
      }
   }
   
   private static class NotifyAllInvocation implements Invocation<Object> {
      
      public NotifyAllInvocation() {
         super();
      }
      
      @Override
      public Object invoke(Scope scope, Object object, Object... list) throws Exception {
         object.notifyAll();
         return null;
      }
   }
   
   private static class HashCodeInvocation implements Invocation<Object> {
      
      public HashCodeInvocation() {
         super();
      }

      @Override
      public Object invoke(Scope scope, Object object, Object... list) throws Exception {
         return object.hashCode();
      }
   }
   
   private static class EqualsInvocation implements Invocation<Object> {
      
      public EqualsInvocation() {
         super();
      }

      @Override
      public Object invoke(Scope scope, Object object, Object... list) throws Exception {
         return object.equals(list[0]);
      }
   }
   
   private static class ToStringInvocation implements Invocation<Object> {
      
      public ToStringInvocation() {
         super();
      }
      
      @Override
      public Object invoke(Scope scope, Object object, Object... list) throws Exception {
         return object + "@" + object.hashCode();
      }
   }
}