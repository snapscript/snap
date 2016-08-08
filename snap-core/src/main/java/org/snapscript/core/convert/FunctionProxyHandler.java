package org.snapscript.core.convert;

import static org.snapscript.core.Reserved.METHOD_EQUALS;
import static org.snapscript.core.Reserved.METHOD_HASH_CODE;
import static org.snapscript.core.Reserved.METHOD_TO_STRING;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.concurrent.Callable;

import org.snapscript.core.Context;
import org.snapscript.core.InternalStateException;
import org.snapscript.core.Result;
import org.snapscript.core.Transient;
import org.snapscript.core.Value;
import org.snapscript.core.bind.FunctionBinder;
import org.snapscript.core.function.Function;

public class FunctionProxyHandler implements InvocationHandler {
   
   private final ProxyArgumentExtractor extractor;
   private final Function function;
   private final Context context;
   private final Value value;
   
   public FunctionProxyHandler(ProxyWrapper wrapper, Context context, Function function) {
      this.extractor = new ProxyArgumentExtractor(wrapper);
      this.value = new Transient(function);
      this.function = function;
      this.context = context;
   }
   
   @Override
   public Object invoke(Object proxy, Method method, Object[] arguments) throws Throwable {
      Object[] convert = extractor.extract(arguments);
      String name = method.getName();
      int width = convert.length;
      
      if(name.equals(METHOD_HASH_CODE)) {
         if(width != 0) {
            throw new InternalStateException("Closure "+ METHOD_HASH_CODE +" does not accept " + width + " arguments");
         }
         return function.hashCode();
      }
      if(name.equals(METHOD_TO_STRING)) {
         if(width != 0) {
            throw new InternalStateException("Closure "+METHOD_TO_STRING+" does not accept " + width + " arguments");
         }
         return function.toString();
      }
      if(name.equals(METHOD_EQUALS)) {
         if(width != 1) {
            throw new InternalStateException("Closure "+METHOD_EQUALS+" does not accept " + width + " arguments");
         }
         return function.equals(convert[0]);
      }
      return invoke(convert);
   }
   
   private Object invoke(Object[] arguments) throws Throwable {
      FunctionBinder binder = context.getBinder();  
      Callable<Result> call = binder.bind(value, arguments); // here arguments can be null!!!
      int width = arguments.length;
      
      if(call == null) {
         throw new InternalStateException("Closure not matched with " + width +" arguments");
      }
      Result result = call.call();
      Object data = result.getValue();
      
      return data;  
   }
   
   public Function extract() {
      return function;
   }
   
}
