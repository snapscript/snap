package org.snapscript.core.trace;

import org.snapscript.core.error.ErrorHandler;
import org.snapscript.core.scope.Scope;
import org.snapscript.core.type.TypeState;
import org.snapscript.core.type.Type;
import org.snapscript.core.type.TypeBody;
import org.snapscript.core.type.TypePart;

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
   public TypeState define(TypeBody body, Type type, Scope scope) throws Exception {
      try {
         TypeState statement = part.define(body, type, scope);
         
         if(statement != null) {
            return new TraceTypeState(interceptor, handler, statement, trace);
         }
      }catch(Exception cause) {
         interceptor.traceCompileError(scope, trace, cause);
      }
      return null;
   }
}
