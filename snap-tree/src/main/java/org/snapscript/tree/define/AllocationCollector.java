package org.snapscript.tree.define;

import static org.snapscript.core.result.Result.NORMAL;
import static org.snapscript.core.type.Order.INSTANCE;
import static org.snapscript.core.type.Order.OTHER;
import static org.snapscript.core.type.Order.STATIC;

import java.util.ArrayList;
import java.util.List;

import org.snapscript.core.scope.Scope;
import org.snapscript.core.type.Allocation;
import org.snapscript.core.type.Order;
import org.snapscript.core.type.Type;
import org.snapscript.core.result.Result;
import org.snapscript.core.type.TypeBody;

public class AllocationCollector extends Allocation implements TypeBody {

   private final List<Allocation> instances;   
   private final List<Allocation> statics;
   private final List<Allocation> other;
   private final List<Allocation> list;

   public AllocationCollector(){
      this.instances = new ArrayList<Allocation>();
      this.statics = new ArrayList<Allocation>();
      this.other = new ArrayList<Allocation>();
      this.list = new ArrayList<Allocation>();
   }

   public void update(Allocation factory) throws Exception {
      if(factory != null) {         
         list.add(factory);
      }
   }
   
   @Override
   public Order define(Scope scope, Type type) throws Exception {
      for(Allocation factory : list) {
         Order order = factory.define(scope, type);
         
         if(order.isStatic()) {         
            statics.add(factory);            
         } else if(order.isInstance()) {
            instances.add(factory);
         } else {
            other.add(factory);
         }
      }   
      if(!statics.isEmpty()) {
         return STATIC;
      }
      if(!instances.isEmpty()) {
         return INSTANCE;
      }
      return OTHER;
   } 
   
   @Override
   public void compile(Scope scope, Type type) throws Exception {
      for(Allocation factory : statics) {
         factory.compile(scope, type);
      }
      for(Allocation factory : instances) {
         factory.compile(scope, type);
      }
      for(Allocation factory : other) {
         factory.compile(scope, type);
      }
   } 

   @Override
   public void allocate(Scope scope, Type type) throws Exception {
      for(Allocation factory : statics) {
         factory.allocate(scope, type);
      }
      for(Allocation factory : instances) {
         factory.allocate(scope, type);
      }
      for(Allocation factory : other) {
         factory.allocate(scope, type);
      }
   } 
   
   @Override
   public Result execute(Scope scope, Type type) throws Exception {
      Result last = NORMAL;
      
      for(Allocation factory : statics) {
         factory.execute(scope, type);
      }
      for(Allocation factory : instances) {
         factory.execute(scope, type);
      }
      for(Allocation factory : other) {
         last = factory.execute(scope, type);
      }
      return last;
   }              
}