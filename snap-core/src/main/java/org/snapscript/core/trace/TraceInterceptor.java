package org.snapscript.core.trace;

import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

import org.snapscript.core.Scope;
import org.snapscript.core.stack.ThreadStack;

public class TraceInterceptor implements TraceListener {
   
   private final Set<TraceListener> listeners;
   private final TraceErrorCollector collector;
   private final ThreadStack stack;
   
   public TraceInterceptor(TraceErrorCollector collector, ThreadStack stack) {
      this.listeners = new CopyOnWriteArraySet<TraceListener>();
      this.collector = collector;
      this.stack = stack;
   }
   
   @Override
   public void before(Scope scope, Trace trace) {
      stack.before(trace);
      
      if(!listeners.isEmpty()) {
         for(TraceListener listener : listeners) {
            listener.before(scope, trace);
         }
      }
   }

   
   @Override
   public void error(Scope scope, Trace trace, Exception cause) {
      collector.update(cause, trace);
      
      if(!listeners.isEmpty()) {
         for(TraceListener listener : listeners) {
            listener.error(scope, trace, cause);
         }
      }
   }
   
   @Override
   public void after(Scope scope, Trace trace) {
      stack.after(trace);
      
      if(!listeners.isEmpty()) {
         for(TraceListener listener : listeners) {
            listener.after(scope, trace);
         }
      }
   }

   public void register(TraceListener listener) {
      listeners.add(listener);
   }
   
   public void remove(TraceListener listener) {
      listeners.remove(listener);
   }
   
   public void clear() {
      listeners.clear();
   }
}