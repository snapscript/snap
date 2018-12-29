package org.snapscript.tree.function;

import org.snapscript.core.scope.Scope;
import org.snapscript.core.scope.index.Address;
import org.snapscript.core.scope.index.ScopeTable;
import org.snapscript.core.variable.Value;

public class ScopeAllocation {
   
   private ScopeMatcher matcher;
   private Address address;
   private Value compile;
   private Value execute;
   private boolean cache;
   
   public ScopeAllocation(ScopeMatcher matcher, Address address, boolean cache) {
      this.matcher = matcher;
      this.address = address;
      this.cache = cache;
   }
   
   public void compile(Scope scope) throws Exception {
      ScopeTable table = scope.getTable();
      Value value = compile;
      
      if(value == null) {
         value = matcher.compile(scope);
         
         if(value != null && cache) {
            compile = value;
         }
      }
      table.addValue(address, value);
   }
   
   public void allocate(Scope scope) throws Exception {
      ScopeTable table = scope.getTable();
      Value value = execute;
      
      if(value == null) {
         value = matcher.execute(scope);
         
         if(value != null && cache) {
            execute = value;
         }
      }
      table.addValue(address, value);
   }
}