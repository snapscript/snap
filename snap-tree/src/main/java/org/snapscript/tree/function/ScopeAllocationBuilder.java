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
import org.snapscript.core.scope.ScopeState;
import org.snapscript.core.scope.index.Address;
import org.snapscript.core.scope.index.AddressType;
import org.snapscript.core.variable.Value;
import org.snapscript.core.variable.bind.VariableBinder;

public class ScopeAllocationBuilder {

   public ScopeAllocation allocate(Scope scope, Address address) throws Exception {
      Module module = scope.getModule();
      Context context = module.getContext();
      ErrorHandler handler = context.getHandler();
      ProxyWrapper wrapper = context.getWrapper();
      AddressType type = address.getType();
      String name = address.getName();

      if(type == INSTANCE) {
         ScopeMatcher allocator = new StateMatcher(handler, wrapper, name);
         return new ScopeAllocation(allocator, address, false);
      }
      if(type == STATIC) {
         ScopeMatcher allocator = new StateMatcher(handler, wrapper, name);
         return new ScopeAllocation(allocator, address, true);
      }
      if(type == TYPE) {
         ScopeMatcher allocator = new StaticMatcher(handler, wrapper, name);
         return new ScopeAllocation(allocator, address, true);
      }
      if(type == MODULE) {
         ScopeMatcher allocator = new StaticMatcher(handler, wrapper, name);
         return new ScopeAllocation(allocator, address, true);
      }
      return null;
   }   

   private static class StateMatcher implements ScopeMatcher {
      
      private final VariableBinder binder;   
      private final String name;
      
      public StateMatcher(ErrorHandler handler, ProxyWrapper wrapper, String name) {
         this.binder = new VariableBinder(handler, wrapper, name);
         this.name = name;
      }
      
      @Override
      public Value compile(Scope scope) throws Exception {
         ScopeState state = scope.getState();         
         return state.getValue(name);
      }
      
      @Override
      public Value execute(Scope scope) throws Exception {
         return binder.bind(scope);
      }
   }

   private static class StaticMatcher implements ScopeMatcher {
      
      private final VariableBinder binder;   
      
      public StaticMatcher(ErrorHandler handler, ProxyWrapper wrapper, String name) {
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
