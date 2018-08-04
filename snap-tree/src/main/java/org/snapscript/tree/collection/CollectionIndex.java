package org.snapscript.tree.collection;

import static org.snapscript.core.constraint.Constraint.NONE;
import static org.snapscript.core.variable.Value.NULL;

import java.util.List;
import java.util.Map;

import org.snapscript.core.Context;
import org.snapscript.core.Evaluation;
import org.snapscript.core.constraint.Constraint;
import org.snapscript.core.convert.proxy.ProxyWrapper;
import org.snapscript.core.error.InternalArgumentException;
import org.snapscript.core.module.Module;
import org.snapscript.core.scope.Scope;
import org.snapscript.core.type.Type;
import org.snapscript.core.variable.ListValue;
import org.snapscript.core.variable.MapValue;
import org.snapscript.core.variable.Value;
import org.snapscript.tree.Argument;

public class CollectionIndex extends Evaluation {
   
   private final CollectionConverter converter;    
   private final Argument argument;
  
   public CollectionIndex(Argument argument) {
      this.converter = new CollectionConverter();
      this.argument = argument;
   }

   @Override
   public void define(Scope scope) throws Exception {
      argument.define(scope);
   }
   
   @Override
   public Constraint compile(Scope scope, Constraint left) throws Exception {
      Constraint index = argument.compile(scope, null);
      Type type = left.getType(scope);
      
      if(type != null) {
         Type entry = type.getEntry();
         
         if(entry != null) { // is this a compile error?
            return Constraint.getConstraint(entry);
         }         
      }
      return NONE;
   }
   
   @Override
   public Value evaluate(Scope scope, Value left) throws Exception {
      Value index = argument.evaluate(scope, NULL);
      Object object = left.getValue();
      
      if(index == null) {
         throw new InternalArgumentException("Illegal index with null");
      }
      if(object == null) {
         throw new InternalArgumentException("Illegal index of null");
      }
      return index(scope, object, index);
   }
   
   private Value index(Scope scope, Object object, Value index) throws Exception {
      Module module = scope.getModule();
      Context context = module.getContext();
      ProxyWrapper wrapper = context.getWrapper();
      Object source = converter.convert(object);
      Class type = object.getClass();
      
      if(List.class.isInstance(source)) {
         int number = index.getData().getInteger();
         List list = (List)source;
         
         return new ListValue(wrapper, list, module, number);
      }
      if(Map.class.isInstance(source)) {
         Object key = index.getValue();
         Map map = (Map)source;
         
         return new MapValue(wrapper, map, module, key);
      }
      if(Type.class.isInstance(type)) {
         throw new InternalArgumentException("Illegal index of type " + object);
      }
      throw new InternalArgumentException("Illegal index of " + type);
   }
}