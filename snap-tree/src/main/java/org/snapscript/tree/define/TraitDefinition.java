package org.snapscript.tree.define;

import static org.snapscript.core.Category.TRAIT;
import static org.snapscript.core.Phase.COMPILED;
import static org.snapscript.core.Phase.CREATED;
import static org.snapscript.core.Phase.DEFINED;
import static org.snapscript.core.ResultType.NORMAL;

import java.util.concurrent.atomic.AtomicBoolean;

import org.snapscript.common.Progress;
import org.snapscript.core.Execution;
import org.snapscript.core.NoExecution;
import org.snapscript.core.Phase;
import org.snapscript.core.Reserved;
import org.snapscript.core.Scope;
import org.snapscript.core.Statement;
import org.snapscript.core.Type;
import org.snapscript.core.TypeFactory;
import org.snapscript.core.TypePart;
import org.snapscript.core.Value;
import org.snapscript.tree.annotation.AnnotationList;

public class TraitDefinition extends Statement {   
   
   private final FunctionPropertyGenerator generator;
   private final TypeFactoryCollector collector;
   private final TypeFactory constants;
   private final AtomicBoolean compile;
   private final AtomicBoolean define;
   private final AtomicBoolean create;
   private final ClassBuilder builder;
   private final TypePart[] parts;
   
   public TraitDefinition(AnnotationList annotations, TraitName name, TypeHierarchy hierarchy, TypePart... parts) {
      this.builder = new ClassBuilder(annotations, name, hierarchy, TRAIT);
      this.generator = new FunctionPropertyGenerator(); 
      this.constants = new StaticConstantFactory();
      this.collector = new TypeFactoryCollector();
      this.compile = new AtomicBoolean(true);
      this.define = new AtomicBoolean(true);
      this.create = new AtomicBoolean(true);
      this.parts = parts;
   }
   
   @Override
   public void create(Scope outer) throws Exception {
      if(!create.compareAndSet(false, true)) {
         Type type = builder.create(outer);
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
      if(!define.compareAndSet(false, true)) {
         Type type = builder.define(outer);
         Progress<Phase> progress = type.getProgress();
         Scope scope = type.getScope();
         
         try {
            collector.update(constants); // collect static constants first
            
            for(TypePart part : parts) {
               TypeFactory factory = part.define(collector, type);
               collector.update(factory);
            } 
            collector.define(scope, type);
            generator.generate(type);
         } finally {
            progress.done(DEFINED); 
         }
      }
   }
   
   @Override
   public Execution compile(Scope outer) throws Exception {
      if(!compile.compareAndSet(false, true)) {
         Type type = builder.compile(outer);
         Progress<Phase> progress = type.getProgress();
         Scope scope = type.getScope();
         Scope local = scope.getStack(); // make it temporary
         
         try {
            local.getState().add(Reserved.TYPE_THIS, Value.getReference(null, type));
            
            for(TypePart part : parts) {
               TypeFactory factory = part.compile(collector, type);
               collector.update(factory);
            } 
            collector.compile(local, type);
         } finally {
            progress.done(COMPILED); 
         }
      }
      return new NoExecution(NORMAL);
   }
}