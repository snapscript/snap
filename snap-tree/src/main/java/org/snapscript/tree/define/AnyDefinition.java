package org.snapscript.tree.define;

import static org.snapscript.core.ModifierType.PUBLIC;
import static org.snapscript.core.Reserved.ANY_TYPE;
import static org.snapscript.core.Reserved.DEFAULT_PACKAGE;
import static org.snapscript.core.Reserved.METHOD_ARGUMENT;
import static org.snapscript.core.Reserved.METHOD_EQUALS;
import static org.snapscript.core.Reserved.METHOD_HASH_CODE;
import static org.snapscript.core.Reserved.METHOD_TO_STRING;

import java.util.ArrayList;
import java.util.List;

import org.snapscript.core.Context;
import org.snapscript.core.Module;
import org.snapscript.core.Result;
import org.snapscript.core.ResultType;
import org.snapscript.core.Scope;
import org.snapscript.core.Type;
import org.snapscript.core.TypeLoader;
import org.snapscript.core.function.Function;
import org.snapscript.core.function.Invocation;
import org.snapscript.core.function.InvocationFunction;
import org.snapscript.core.function.Parameter;
import org.snapscript.core.function.Signature;

public class AnyDefinition{
   
   private final DefaultConstructor constructor;
   
   public AnyDefinition(){
      this.constructor = new DefaultConstructor();
   }

   public Type create(Scope scope) throws Exception {
      Module module = scope.getModule();
      Context context = module.getContext();
      TypeLoader loader = context.getLoader();
      Type type = loader.defineType(DEFAULT_PACKAGE, ANY_TYPE);
      List<Function> functions = type.getFunctions();
      
      if(functions.isEmpty()) {
         Type string = loader.loadType(String.class);
         Type integer = loader.loadType(Integer.class);
         Type bool = loader.loadType(Boolean.class);
         Function hashCode = createHashCode(module, type, integer);
         Function toString = createToString(module, type, string);
         Function equals = createEquals(module, type, bool);
         
         functions.add(hashCode);
         functions.add(equals);
         functions.add(toString);
         constructor.compile(null, type);
      }
      return type;
   }
   
   private Function createHashCode(Module module, Type type, Type returns) {
      List<Parameter> parameters = new ArrayList<Parameter>();
      Signature signature = new Signature(parameters, module);
      Invocation<Object> invocation = new HashCodeInvocation();
      
      return new InvocationFunction<Object>(signature, invocation, type, returns, METHOD_HASH_CODE, PUBLIC.mask);
   }
   
   private Function createEquals(Module module, Type type, Type returns) {
      List<Parameter> parameters = new ArrayList<Parameter>();
      Parameter parameter = new Parameter(METHOD_ARGUMENT, null);
      Signature signature = new Signature(parameters, module);
      Invocation<Object> invocation = new EqualsInvocation();

      parameters.add(parameter);
      
      return new InvocationFunction<Object>(signature, invocation, type, returns, METHOD_EQUALS, PUBLIC.mask);
   }
   
   private Function createToString(Module module, Type type, Type returns) {
      List<Parameter> parameters = new ArrayList<Parameter>();
      Signature signature = new Signature(parameters, module);
      Invocation<Object> invocation = new ToStringInvocation();
      
      return new InvocationFunction<Object>(signature, invocation, type, returns, METHOD_TO_STRING, PUBLIC.mask);
   }
   
   private static class HashCodeInvocation implements Invocation<Object> {
      
      @Override
      public Result invoke(Scope scope, Object object, Object... list) throws Exception {
         int hash = object.hashCode();
         return ResultType.getNormal(hash);
      }
   }
   
   private static class EqualsInvocation implements Invocation<Object> {
      
      @Override
      public Result invoke(Scope scope, Object object, Object... list) throws Exception {
         Object argument = list[0];
         boolean equal = object.equals(argument);
         
         return ResultType.getNormal(equal);
      }
   }
   
   private static class ToStringInvocation implements Invocation<Object> {
      
      @Override
      public Result invoke(Scope scope, Object object, Object... list) throws Exception {
         String value = object.toString();
         int hash = object.hashCode();
         
         return ResultType.getNormal(value + "@" + hash);
      }
   }
}
