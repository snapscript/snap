package org.snapscript.tree.define;

import static org.snapscript.core.Category.TRAIT;
import static org.snapscript.core.Phase.COMPILED;
import static org.snapscript.core.Phase.DEFINED;

import java.util.concurrent.atomic.AtomicBoolean;

import org.snapscript.common.Progress;
import org.snapscript.core.Phase;
import org.snapscript.core.Result;
import org.snapscript.core.Scope;
import org.snapscript.core.Statement;
import org.snapscript.core.Type;
import org.snapscript.core.TypeFactory;
import org.snapscript.tree.annotation.AnnotationList;

public class TraitDefinition extends Statement {   
   
   private final FunctionPropertyGenerator generator;
   private final TypeFactoryCollector collector;
   private final TypeFactory constants;
   private final AtomicBoolean compile;
   private final AtomicBoolean define;
   private final ClassBuilder builder;
   private final TypePart[] parts;
   
   public TraitDefinition(AnnotationList annotations, TraitName name, TypeHierarchy hierarchy, TypePart... parts) {
      this.builder = new ClassBuilder(annotations, name, hierarchy, TRAIT);
      this.generator = new FunctionPropertyGenerator(); 
      this.constants = new StaticConstantFactory();
      this.collector = new TypeFactoryCollector();
      this.compile = new AtomicBoolean(true);
      this.define = new AtomicBoolean(true);
      this.parts = parts;
   }
   
   @Override
   public Result define(Scope outer) throws Exception {
      if(!define.compareAndSet(false, true)) {
         Result result = builder.define(outer);
         Type type = result.getValue();
         Progress<Phase> progress = type.getProgress();
         
         try {
            for(TypePart part : parts) {
               TypeFactory factory = part.define(collector, type);
               collector.update(factory);
            } 
         } finally {
            progress.done(DEFINED);
         }
         return result;
      }
      return Result.getNormal();
   }

   @Override
   public Result compile(Scope outer) throws Exception {
      if(!compile.compareAndSet(false, true)) {
         Result result = builder.compile(outer);
         Type type = result.getValue();
         Progress<Phase> progress = type.getProgress();
         
         try {
            collector.update(constants); // collect static constants first
            
            for(TypePart part : parts) {
               TypeFactory factory = part.compile(collector, type);
               collector.update(factory);
            } 
            generator.generate(type);
         } finally {
            progress.done(COMPILED); 
         }
         return result;
      }
      return Result.getNormal();
   }

}