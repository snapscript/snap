package org.snapscript.tree.collection;

import java.util.List;

import org.snapscript.core.Context;
import org.snapscript.core.Evaluation;
import org.snapscript.core.InternalArgumentException;
import org.snapscript.core.Module;
import org.snapscript.core.Scope;
import org.snapscript.core.Value;
import org.snapscript.core.convert.ProxyWrapper;
import org.snapscript.tree.Argument;

public class ArrayIndex implements Evaluation {
   
   private final ArrayListConverter converter;
   private final Argument[] list;
   private final Argument first;
   private final Array array;
  
   public ArrayIndex(Array array, Argument first, Argument... list) {
      this.converter = new ArrayListConverter();
      this.array = array;        
      this.first = first;
      this.list = list;
   }

   @Override
   public Value evaluate(Scope scope, Object left) throws Exception {
      Value index = first.evaluate(scope, null);
      Value value = array.evaluate(scope, left);
      Module module = scope.getModule();
      Context context = module.getContext();
      ProxyWrapper wrapper = context.getWrapper();
      Integer number = index.getInteger();
      List source = value.getValue();

      for(int i = 0; i < list.length; i++) {
         Argument argument = list[i];
         Object entry = source.get(number);
         int length = i + 1;
         
         if(!converter.accept(entry)) {
            throw new InternalArgumentException("Array contains only " + length + " dimensions");
         }
         Class type = entry.getClass();
         
         source = converter.convert(entry);
         index = argument.evaluate(scope, null);
         number = index.getInteger();
      }
      return new ListValue(wrapper, source, number);
   }
}