package org.snapscript.core.stack;

import java.util.Iterator;

import org.snapscript.common.ArrayStack;
import org.snapscript.common.Stack;
import org.snapscript.core.function.Function;
import org.snapscript.core.trace.Trace;

public class TraceStack implements Iterable<Object>{

   private final Stack<Function> functions;
   private final Stack<Object> events;

   public TraceStack() {
      this.functions = new ArrayStack<Function>();
      this.events = new ArrayStack<Object>();
   }
   
   @Override
   public Iterator<Object> iterator() {
      return events.iterator();
   }
   
   public Function peek(){
      return functions.peek();
   }
   
   public void push(Trace trace) {
      events.push(trace);
   }
   
   public void push(Function function) {
      functions.push(function);
      events.push(function);
   }
   
   public void pop(Object trace) {
      while(!events.isEmpty()) {
         Object next = events.pop();
         Object peek = functions.peek();
         
         if(next == peek) {
            functions.pop();
         }
         if(next == trace) {
            break;
         }
      }
   }
   
   public void clear() {
      functions.clear();
      events.clear();
   }
}
