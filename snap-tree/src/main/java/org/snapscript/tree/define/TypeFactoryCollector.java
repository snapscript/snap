package org.snapscript.tree.define;

import java.util.ArrayList;
import java.util.List;

import org.snapscript.core.Result;
import org.snapscript.core.Scope;
import org.snapscript.core.Type;
import org.snapscript.core.TypeFactory;

public class TypeFactoryCollector extends TypeFactory {
   
   private final List<TypeFactory> list;
   
   public TypeFactoryCollector(){
      this.list = new ArrayList<TypeFactory>();
   }

   public void update(TypeFactory factory) throws Exception {
      if(factory != null) {         
         list.add(factory);
      }
   }
   
   @Override
   public void compile(Scope scope, Type type) throws Exception {
      for(TypeFactory factory : list) {
         factory.compile(scope, type);
      }
   } 
   
   @Override
   public void validate(Scope scope, Type type) throws Exception {
      for(TypeFactory factory : list) {
         factory.validate(scope, type);
      }
   } 

   @Override
   public Result execute(Scope scope, Type type) throws Exception {
      Result last = null;

      for(TypeFactory factory : list) {
         Result result = factory.execute(scope, type);
         
         if(!result.isNormal()){
            return result;
         }
         last = result;
      }
      if(last == null) {
         return Result.getNormal();
      }
      return last;
   }              
}