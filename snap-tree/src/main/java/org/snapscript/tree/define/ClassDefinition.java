package org.snapscript.tree.define;

import static org.snapscript.core.Category.CLASS;
import static org.snapscript.core.Phase.DEFINED;
import static org.snapscript.core.Phase.CREATED;
import static org.snapscript.core.Phase.COMPILED;

import java.util.concurrent.atomic.AtomicBoolean;

import org.snapscript.common.Progress;
import org.snapscript.core.Phase;
import org.snapscript.core.Scope;
import org.snapscript.core.Statement;
import org.snapscript.core.Type;
import org.snapscript.core.TypeFactory;
import org.snapscript.tree.annotation.AnnotationList;

public class ClassDefinition extends Statement {   
   
   private final FunctionPropertyGenerator generator;
   private final DefaultConstructor constructor;
   private final TypeFactoryCollector collector;
   private final TypeFactory constants;
   private final AtomicBoolean validate;
   private final AtomicBoolean compile;
   private final AtomicBoolean define;
   private final ClassBuilder builder;
   private final TypePart[] parts;
   
   public ClassDefinition(AnnotationList annotations, TypeName name, TypeHierarchy hierarchy, TypePart... parts) {
      this.builder = new ClassBuilder(annotations, name, hierarchy, CLASS);
      this.generator = new FunctionPropertyGenerator(); 
      this.constants = new StaticConstantFactory();
      this.collector = new TypeFactoryCollector();
      this.constructor = new DefaultConstructor();
      this.validate = new AtomicBoolean(true);
      this.compile = new AtomicBoolean(true);
      this.define = new AtomicBoolean(true);
      this.parts = parts;
   }
   
   @Override
   public void create(Scope outer) throws Exception {
      if(!define.compareAndSet(false, true)) {
         Type type = builder.define(outer);
         Progress<Phase> progress = type.getProgress();
         
         try {
            for(TypePart part : parts) {
               TypeFactory factory = part.create(collector, type);
               collector.update(factory);
            } 
         } finally {
            progress.done(CREATED);
         }
      }
   }

   @Override
   public void define(Scope outer) throws Exception {
      if(!compile.compareAndSet(false, true)) {
         Type type = builder.compile(outer);
         Progress<Phase> progress = type.getProgress();
         
         try {
            collector.update(constants); // collect static constants first
            
            for(TypePart part : parts) {
               TypeFactory factory = part.define(collector, type);
               collector.update(factory);
            } 
            constructor.define(collector, type);
            generator.generate(type);
         } finally {
            progress.done(DEFINED);
         }
      }
   }

   @Override
   public void compile(Scope outer) throws Exception {
      if(!validate.compareAndSet(false, true)) {
         Type type = builder.validate(outer);
         Progress<Phase> progress = type.getProgress();
         
         try {
            for(TypePart part : parts) {
               TypeFactory factory = part.compile(collector, type);
               collector.update(factory);
            } 
            constructor.compile(collector, type);
         } finally {
            progress.done(COMPILED);
         }
      }
   }
}