package org.snapscript.core.convert.proxy;

import static org.snapscript.core.Reserved.METHOD_EQUALS;
import static org.snapscript.core.Reserved.METHOD_HASH_CODE;
import static org.snapscript.core.Reserved.METHOD_TO_STRING;

import java.lang.reflect.Method;
import java.util.concurrent.Callable;

import org.snapscript.core.Context;
import org.snapscript.core.InternalStateException;
import org.snapscript.core.scope.Scope;
import org.snapscript.core.type.Type;
import org.snapscript.core.variable.Transient;
import org.snapscript.core.variable.Value;
import org.snapscript.core.function.Function;
import org.snapscript.core.function.resolve.FunctionCall;
import org.snapscript.core.function.resolve.FunctionResolver;

public class FunctionProxyHandler implements ProxyHandler { 
   
   private final ProxyArgumentExtractor extractor;
   private final ProxyWrapper wrapper;
   private final Function function;
   private final Context context;
   private final Value value;
   
   public FunctionProxyHandler(ProxyWrapper wrapper, Context context, Function function) {
      this.extractor = new ProxyArgumentExtractor(wrapper);
      this.value = new Transient(function);
      this.function = function;
      this.wrapper = wrapper;
      this.context = context;
   }
   
   @Override
   public Object invoke(Object proxy, Method method, Object[] arguments) throws Throwable {
      Object[] convert = extractor.extract(arguments);
      String name = method.getName();
      int width = convert.length;
      
      if(name.equals(METHOD_HASH_CODE)) {
         if(width != 0) {
            throw new InternalStateException("Closure '"+ METHOD_HASH_CODE +"' does not accept " + width + " arguments");
         }
         return function.hashCode();
      }
      if(name.equals(METHOD_TO_STRING)) {
         if(width != 0) {
            throw new InternalStateException("Closure '"+METHOD_TO_STRING+"' does not accept " + width + " arguments");
         }
         return function.toString();
      }
      if(name.equals(METHOD_EQUALS)) {
         if(width != 1) {
            throw new InternalStateException("Closure '"+METHOD_EQUALS+"' does not accept " + width + " arguments");
         }
         return function.equals(convert[0]);
      }
      if(convert.length > 0) {
         return invoke(proxy, name, convert, arguments); // arguments could be null
      }
      return invoke(proxy, name, convert, convert);
   }
   
   private Object invoke(Object proxy, String name, Object[] convert, Object[] arguments) throws Throwable {
      FunctionCall call = resolve(proxy, name, convert, arguments);  
      int width = arguments.length;
      
      if(call == null) {
         throw new InternalStateException("Closure not matched with " + width +" arguments");
      }
      Value value = call.call();
      Object data  = value.getValue();
      
      return wrapper.toProxy(data);  
   }
   
   private FunctionCall resolve(Object proxy, String name, Object[] convert, Object[] arguments) throws Throwable {
      Type type = function.getType();
      FunctionResolver resolver = context.getResolver();  

      if(type != null) {
         Scope scope = type.getScope();
         FunctionCall call = resolver.resolveInstance(scope, proxy, name, arguments); 
         
         if(call != null) {
            return call;
         }
      }
      return resolver.resolveValue(value, convert); // here arguments can be null!!! 
   }
   
   @Override
   public Function extract() {
      return function;
   }
   
}