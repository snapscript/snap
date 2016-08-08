package org.snapscript.tree.define;

import java.util.concurrent.atomic.AtomicBoolean;

import org.snapscript.core.Result;
import org.snapscript.core.ResultType;
import org.snapscript.core.Scope;
import org.snapscript.core.Statement;
import org.snapscript.core.Type;
import org.snapscript.core.define.Initializer;
import org.snapscript.tree.annotation.AnnotationList;

public class EnumDefinition extends Statement {

   private final DefaultConstructor constructor;
   private final InitializerCollector collector;
   private final AtomicBoolean compile;
   private final EnumBuilder builder;
   private final EnumList list;
   private final TypePart[] parts;
   
   public EnumDefinition(AnnotationList annotations, TypeName name, TypeHierarchy hierarchy, EnumList list, TypePart... parts) {
      this.builder = new EnumBuilder(name, hierarchy);
      this.constructor = new DefaultConstructor(true);
      this.collector = new InitializerCollector();
      this.compile = new AtomicBoolean(true);
      this.parts = parts;
      this.list = list;
   }
   
   @Override
   public Result define(Scope outer) throws Exception {
      return builder.define(outer);
   }

   @Override
   public Result compile(Scope outer) throws Exception {
      if(!compile.compareAndSet(false, true)) {
         Result result = builder.compile(outer);
         Type type = result.getValue();
         Initializer keys = list.compile(collector, type);
         Scope scope = type.getScope();
         
         for(TypePart part : parts) {
            Initializer initializer = part.compile(collector, type);
            collector.update(initializer);
         }  
         constructor.compile(collector, type); 
         keys.execute(scope, type);
         collector.compile(scope, type); 
         
         return result;
      }
      return ResultType.getNormal();
   }
}
