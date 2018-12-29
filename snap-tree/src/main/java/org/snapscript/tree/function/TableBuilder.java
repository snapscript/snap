package org.snapscript.tree.function;

import java.util.ArrayList;
import java.util.List;

import org.snapscript.core.scope.Scope;
import org.snapscript.core.scope.index.Address;
import org.snapscript.core.scope.index.Index;

public class TableBuilder {   

   private final List<TableAllocation> allocations;
   private final TableAllocationBuilder builder;
   
   public TableBuilder(){
      this.allocations = new ArrayList<TableAllocation>();
      this.builder = new TableAllocationBuilder();
   }

   public void define(Scope scope) throws Exception {
      Index index = scope.getIndex();   
      
      for(Address address : index){
         TableAllocation allocation = builder.allocate(scope, address);
         
         if(allocation != null) {
            allocations.add(allocation);
         }
      }
   }
   
   public Scope compile(Scope scope) throws Exception {
      for(TableAllocation allocation : allocations) {
         allocation.compile(scope);
      }
      return scope;
   }
   
   public Scope extract(Scope scope) throws Exception {
      for(TableAllocation allocation : allocations) {
         allocation.allocate(scope);
      }
      return scope;
   }  
}

