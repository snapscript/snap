package org.snapscript.tree.define;

import static org.snapscript.core.Category.TRAIT;
import static org.snapscript.core.Phase.COMPILED;
import static org.snapscript.core.Phase.DEFINED;

import java.util.concurrent.atomic.AtomicBoolean;

import org.snapscript.common.Progress;
import org.snapscript.core.Phase;
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
   public void define(Scope outer) throws Exception {
      if(!define.compareAndSet(false, true)) {
         Type type = builder.define(outer);
         Progress<Phase> progress = type.getProgress();
         
         try {
            for(TypePart part : parts) {
               TypeFactory factory = part.define(collector, type);
               collector.update(factory);
            } 
         } finally {
            progress.done(DEFINED);
         }
      }
   }

   @Override
   public void compile(Scope outer) throws Exception {
      if(!compile.compareAndSet(false, true)) {
         Type type = builder.compile(outer);
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
      }
   }
}