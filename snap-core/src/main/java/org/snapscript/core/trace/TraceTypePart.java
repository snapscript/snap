package org.snapscript.core.trace;

import org.snapscript.core.Scope;
import org.snapscript.core.Type;
import org.snapscript.core.TypeFactory;
import org.snapscript.core.TypePart;
import org.snapscript.core.error.ErrorHandler;

public class TraceTypePart extends TypePart {
   
   private final ErrorHandler handler;
   private final TypePart part;
   private final Trace trace;
   
   public TraceTypePart(ErrorHandler handler, TypePart part, Trace trace) {
      this.handler = handler;
      this.trace = trace;
      this.part = part;
   }

   @Override
   public TypeFactory create(TypeFactory factory, Type type, Scope scope) throws Exception {
      try {
         TypeFactory result = part.create(factory, type, scope);
         
         if(result != null) {
            return new TraceTypeFactory(handler, result, trace);
         }
      }catch(Exception cause) {
         handler.throwInternalError(scope, cause, trace);
      }
      return null;
   }
   
   @Override
   public TypeFactory define(TypeFactory factory, Type type, Scope scope) throws Exception {
      try {
         TypeFactory result = part.define(factory, type, scope);
         
         if(result != null) {
            return new TraceTypeFactory(handler, result, trace);
         }
      }catch(Exception cause) {
         handler.throwInternalError(scope, cause, trace);
      }
      return null;
   }
   
   @Override
   public TypeFactory compile(TypeFactory factory, Type type, Scope scope) throws Exception {
      try {
         TypeFactory result = part.compile(factory, type, scope);
         
         if(result != null) {
            return new TraceTypeFactory(handler, result, trace);
         }
      }catch(Exception cause) {
         handler.throwInternalError(scope, cause, trace);
      }
      return null;
   }
}
