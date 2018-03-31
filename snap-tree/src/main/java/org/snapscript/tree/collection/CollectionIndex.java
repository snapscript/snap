package org.snapscript.tree.collection;

import static org.snapscript.core.constraint.Constraint.NONE;

import java.util.List;
import java.util.Map;

import org.snapscript.core.Context;
import org.snapscript.core.Evaluation;
import org.snapscript.core.InternalArgumentException;
import org.snapscript.core.InternalStateException;
import org.snapscript.core.Module;
import org.snapscript.core.Scope;
import org.snapscript.core.Type;
import org.snapscript.core.Value;
import org.snapscript.core.constraint.Constraint;
import org.snapscript.core.convert.ProxyWrapper;
import org.snapscript.tree.Argument;

public class CollectionIndex extends Evaluation {
   
   private final CollectionConverter converter;
   private final Evaluation[] evaluations; // func()[1][3]
   private final Evaluation variable;
   private final Argument argument;
  
   public CollectionIndex(Evaluation variable, Argument argument, Evaluation... evaluations) {
      this.converter = new CollectionConverter();
      this.evaluations = evaluations;
      this.argument = argument;
      this.variable = variable;  
   }

   @Override
   public void define(Scope scope) throws Exception {
      variable.define(scope);
      argument.define(scope);
      
      for(Evaluation evaluation : evaluations) {
         evaluation.define(scope);
      }
   }
   
   @Override
   public Constraint compile(Scope scope, Constraint left) throws Exception {
      Constraint constraint = variable.compile(scope, left);
      Constraint index = argument.compile(scope, null);
      Type type = constraint.getType(scope);
      
      if(type != null) {
         Type entry = type.getEntry();
         Constraint next = Constraint.getVariable(entry);
               
         for(Evaluation evaluation : evaluations) {
            next = evaluation.compile(scope, next);
         }
         return next;
      }
      return NONE;
   }
   
   @Override
   public Value evaluate(Scope scope, Object left) throws Exception {
      Value value = variable.evaluate(scope, left);
      Value index = argument.evaluate(scope, null);
      Object source = value.getValue();
      
      if(source == null) {
         throw new InternalArgumentException("Illegal index of null");
      }
      Value result = index(scope, source, index);

      for(Evaluation evaluation : evaluations) {
         Object object = result.getValue();
         
         if(object == null) {
            throw new InternalStateException("Result was null"); 
         }
         result = evaluation.evaluate(scope, object);
      }
      return result;
   }
   
   private Value index(Scope scope, Object left, Value index) throws Exception {
      Module module = scope.getModule();
      Context context = module.getContext();
      ProxyWrapper wrapper = context.getWrapper();
      Object source = converter.convert(left);
      Class type = left.getClass();
      
      if(List.class.isInstance(source)) {
         int number = index.getInteger();
         List list = (List)source;
         
         return new ListValue(wrapper, list, number);
      }
      if(Map.class.isInstance(source)) {
         Object key = index.getValue();
         Map map = (Map)source;
         
         return new MapValue(wrapper, map, key);
      }
      throw new InternalArgumentException("Illegal index of " + type);
   }
}