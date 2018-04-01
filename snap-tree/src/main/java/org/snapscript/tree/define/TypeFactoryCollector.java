package org.snapscript.tree.define;

import static org.snapscript.core.result.Result.NORMAL;

import java.util.ArrayList;
import java.util.List;

import org.snapscript.core.Scope;
import org.snapscript.core.Type;
import org.snapscript.core.TypeFactory;
import org.snapscript.core.result.Result;

public class TypeFactoryCollector extends TypeFactory {

   private final List<TypeFactory> statics;
   private final List<TypeFactory> list;

   public TypeFactoryCollector(){
      this.statics = new ArrayList<TypeFactory>();
      this.list = new ArrayList<TypeFactory>();
   }

   public void update(TypeFactory factory) throws Exception {
      if(factory != null) {         
         list.add(factory);
      }
   }
   
   @Override
   public boolean define(Scope scope, Type type) throws Exception {
      for(TypeFactory factory : list) {
         if(factory.define(scope, type)) {
            statics.add(factory);
         }
      }      
      return list.removeAll(statics);
   } 
   
   @Override
   public void compile(Scope scope, Type type) throws Exception {
      for(TypeFactory factory : statics) {
         factory.compile(scope, type);
      }
      for(TypeFactory factory : list) {
         factory.compile(scope, type);
      }
   } 

   @Override
   public void allocate(Scope scope, Type type) throws Exception {
      for(TypeFactory factory : statics) {
         factory.allocate(scope, type);
      }
      for(TypeFactory factory : list) {
         factory.allocate(scope, type);
      }
   } 
   
   @Override
   public Result execute(Scope scope, Type type) throws Exception {
      Result last = NORMAL;
      
      for(TypeFactory factory : statics) {
         factory.execute(scope, type);
      }
      for(TypeFactory factory : list) {
         last = factory.execute(scope, type);
      }
      return last;
   }              
}