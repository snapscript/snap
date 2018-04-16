package org.snapscript.tree.define;

import static org.snapscript.core.result.Result.NORMAL;
import static org.snapscript.core.type.Order.INSTANCE;
import static org.snapscript.core.type.Order.OTHER;
import static org.snapscript.core.type.Order.STATIC;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.snapscript.core.result.Result;
import org.snapscript.core.scope.Scope;
import org.snapscript.core.type.Order;
import org.snapscript.core.type.Type;
import org.snapscript.core.type.TypeBody;
import org.snapscript.core.type.TypeState;

public class TypeStateCollector extends TypeState implements TypeBody {

   private final List<TypeState> instances;   
   private final List<TypeState> statics;
   private final List<TypeState> other;
   private final List<TypeState> list;
   private final Order order;

   public TypeStateCollector(){
      this(OTHER);
   }
   
   public TypeStateCollector(Order order){
      this.statics = new CopyOnWriteArrayList<TypeState>();
      this.instances = new ArrayList<TypeState>();
      this.other = new ArrayList<TypeState>();
      this.list = new ArrayList<TypeState>();
      this.order = order;
   }

   public void update(TypeState state) throws Exception {
      if(state != null) {         
         list.add(state);
      }
   }
   
   @Override
   public Order define(Scope scope, Type type) throws Exception {
      for(TypeState state : list) {
         Order order = state.define(scope, type);
         
         if(order.isStatic()) {         
            statics.add(state);            
         } else if(order.isInstance()) {
            instances.add(state);
         } else {
            other.add(state);
         }
      }   
      if(!statics.isEmpty()) {
         return STATIC;
      }
      if(!instances.isEmpty()) {
         return INSTANCE;
      }
      return order;
   } 
   
   @Override
   public void compile(Scope scope, Type type) throws Exception {
      for(TypeState state : statics) {
         state.compile(scope, type);
      }
      for(TypeState state : instances) {
         state.compile(scope, type);
      }
      for(TypeState state : other) {
         state.compile(scope, type);
      }
   } 

   @Override
   public void allocate(Scope scope, Type type) throws Exception {
      for(TypeState state : statics) {
         state.allocate(scope, type);
      }
      for(TypeState state : other) {
         state.allocate(scope, type);
      }
      statics.clear();
   } 
   
   @Override
   public Result execute(Scope scope, Type type) throws Exception {
      Result last = NORMAL;
      
      for(TypeState state : instances) {
         state.execute(scope, type);
      }
      for(TypeState state : other) {
         last = state.execute(scope, type);
      }
      return last;
   }              
}