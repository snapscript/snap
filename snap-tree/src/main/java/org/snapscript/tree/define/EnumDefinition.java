package org.snapscript.tree.define;

import static org.snapscript.core.Phase.COMPILED;
import static org.snapscript.core.Phase.DEFINED;

import java.util.concurrent.atomic.AtomicBoolean;

import org.snapscript.common.Progress;
import org.snapscript.core.Phase;
import org.snapscript.core.Result;
import org.snapscript.core.ResultType;
import org.snapscript.core.Scope;
import org.snapscript.core.Statement;
import org.snapscript.core.Type;
import org.snapscript.core.TypeFactory;
import org.snapscript.tree.annotation.AnnotationList;

public class EnumDefinition extends Statement {

   private final DefaultConstructor constructor;
   private final TypeFactoryCollector collector;
   private final AtomicBoolean compile;
   private final AtomicBoolean define;
   private final EnumBuilder builder;
   private final EnumList list;
   private final TypePart[] parts;
   
   public EnumDefinition(AnnotationList annotations, TypeName name, TypeHierarchy hierarchy, EnumList list, TypePart... parts) {
      this.builder = new EnumBuilder(name, hierarchy);
      this.constructor = new DefaultConstructor(true);
      this.collector = new TypeFactoryCollector();
      this.compile = new AtomicBoolean(true);
      this.define = new AtomicBoolean(true);
      this.parts = parts;
      this.list = list;
   }
   
   @Override
   public Result define(Scope outer) throws Exception {
      if(!define.compareAndSet(false, true)) {
         Result result = builder.define(outer);
         Type type = result.getValue();
         Progress<Phase> progress = type.getProgress();
      
         progress.done(DEFINED);
         
         return result;
      }
      return ResultType.getNormal();
   }

   @Override
   public Result compile(Scope outer) throws Exception {
      if(!compile.compareAndSet(false, true)) {
         Result result = builder.compile(outer);
         Type type = result.getValue();
         TypeFactory keys = list.compile(collector, type);
         Scope scope = type.getScope();
         Progress<Phase> progress = type.getProgress();
         
         for(TypePart part : parts) {
            TypeFactory factory = part.compile(collector, type);
            collector.update(factory);
         }  
         constructor.compile(collector, type); 
         keys.execute(scope, type);
         collector.compile(scope, type); 
         progress.done(COMPILED);
         
         return result;
      }
      return ResultType.getNormal();
   }
}