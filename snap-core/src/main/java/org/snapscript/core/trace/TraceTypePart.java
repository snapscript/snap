package org.snapscript.core.trace;

import org.snapscript.core.Allocation;
import org.snapscript.core.Scope;
import org.snapscript.core.Type;
import org.snapscript.core.TypeBody;
import org.snapscript.core.TypePart;
import org.snapscript.core.error.ErrorHandler;

public class TraceTypePart extends TypePart {
   
   private final TraceInterceptor interceptor;
   private final ErrorHandler handler;
   private final TypePart part;
   private final Trace trace;
   
   public TraceTypePart(TraceInterceptor interceptor, ErrorHandler handler, TypePart part, Trace trace) {
      this.interceptor = interceptor;
      this.handler = handler;
      this.trace = trace;
      this.part = part;
   }

   @Override
   public void create(TypeBody body, Type type, Scope scope) throws Exception {
      try {
         part.create(body, type, scope);
      }catch(Exception cause) {
         interceptor.traceCompileError(scope, trace, cause);
      }
   }
   
   @Override
   public Allocation define(TypeBody body, Type type, Scope scope) throws Exception {
      try {
         Allocation statement = part.define(body, type, scope);
         
         if(statement != null) {
            return new TraceAllocation(interceptor, handler, statement, trace);
         }
      }catch(Exception cause) {
         interceptor.traceCompileError(scope, trace, cause);
      }
      return null;
   }
   
   @Override
   public void compile(TypeBody body, Type type, Scope scope) throws Exception {
      try {
         part.compile(body, type, scope);
      }catch(Exception cause) {
         interceptor.traceCompileError(scope, trace, cause);
      }
   }
}
