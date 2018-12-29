package org.snapscript.tree.function;

import static org.snapscript.core.scope.index.AddressType.INSTANCE;
import static org.snapscript.core.scope.index.AddressType.MODULE;
import static org.snapscript.core.scope.index.AddressType.STATIC;
import static org.snapscript.core.scope.index.AddressType.TYPE;

import org.snapscript.core.Context;
import org.snapscript.core.convert.proxy.ProxyWrapper;
import org.snapscript.core.error.ErrorHandler;
import org.snapscript.core.module.Module;
import org.snapscript.core.scope.Scope;
import org.snapscript.core.scope.State;
import org.snapscript.core.scope.index.Address;
import org.snapscript.core.scope.index.AddressType;
import org.snapscript.core.variable.Value;
import org.snapscript.core.variable.bind.VariableBinder;

public class TableAllocationBuilder {

   public TableAllocation allocate(Scope scope, Address address) throws Exception {
      Module module = scope.getModule();
      Context context = module.getContext();
      ErrorHandler handler = context.getHandler();
      ProxyWrapper wrapper = context.getWrapper();
      AddressType type = address.getType();
      String name = address.getName();

      if(type == INSTANCE) {
         VariableMatcher allocator = new LocalMatcher(handler, wrapper, name);
         return new TableAllocation(allocator, address, false);
      }
      if(type == STATIC) {
         VariableMatcher allocator = new LocalMatcher(handler, wrapper, name);
         return new TableAllocation(allocator, address, true);
      }
      if(type == TYPE) {
         VariableMatcher allocator = new GlobalMatcher(handler, wrapper, name);
         return new TableAllocation(allocator, address, true);
      }
      if(type == MODULE) {
         VariableMatcher allocator = new GlobalMatcher(handler, wrapper, name);
         return new TableAllocation(allocator, address, true);
      }
      return null;
   }   

   private static class LocalMatcher implements VariableMatcher {
      
      private final VariableBinder binder;   
      private final String name;
      
      public LocalMatcher(ErrorHandler handler, ProxyWrapper wrapper, String name) {
         this.binder = new VariableBinder(handler, wrapper, name);
         this.name = name;
      }
      
      @Override
      public Value compile(Scope scope) throws Exception {
         State state = scope.getState();         
         return state.getValue(name);
      }
      
      @Override
      public Value execute(Scope scope) throws Exception {
         return binder.bind(scope);
      }
   }

   private static class GlobalMatcher implements VariableMatcher {
      
      private final VariableBinder binder;   
      
      public GlobalMatcher(ErrorHandler handler, ProxyWrapper wrapper, String name) {
         this.binder = new VariableBinder(handler, wrapper, name);         
      }
      
      @Override
      public Value compile(Scope scope) throws Exception {
         return binder.bind(scope);
      }
      
      @Override
      public Value execute(Scope scope) throws Exception {
         return binder.bind(scope);

      }
   }
}
