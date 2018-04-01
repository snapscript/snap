package org.snapscript.tree.define;

import static org.snapscript.core.Category.CLASS;
import static org.snapscript.core.Phase.COMPILED;
import static org.snapscript.core.Phase.CREATED;
import static org.snapscript.core.Phase.DEFINED;
import static org.snapscript.core.result.Result.NORMAL;

import java.util.concurrent.atomic.AtomicBoolean;

import org.snapscript.common.Progress;
import org.snapscript.core.Execution;
import org.snapscript.core.NoExecution;
import org.snapscript.core.Phase;
import org.snapscript.core.Scope;
import org.snapscript.core.Statement;
import org.snapscript.core.Type;
import org.snapscript.core.Allocation;
import org.snapscript.core.TypePart;
import org.snapscript.tree.annotation.AnnotationList;

public class ClassDefinition extends Statement {   
   
   private final FunctionPropertyGenerator generator;
   private final DefaultConstructor constructor;
   private final AllocationCollector collector;
   private final Allocation constants;
   private final AtomicBoolean compile;
   private final AtomicBoolean define;
   private final AtomicBoolean create;
   private final ClassBuilder builder;
   private final Execution execution;
   private final TypePart[] parts;
   
   public ClassDefinition(AnnotationList annotations, TypeName name, TypeHierarchy hierarchy, TypePart... parts) {
      this.builder = new ClassBuilder(annotations, name, hierarchy, CLASS);
      this.generator = new FunctionPropertyGenerator(); 
      this.constants = new StaticState();
      this.collector = new AllocationCollector();
      this.constructor = new DefaultConstructor();
      this.execution = new NoExecution(NORMAL);
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
         Scope scope = type.getScope();
               
         try {
            for(TypePart part : parts) {
               part.create(collector, type, scope);               
            } 
         } finally {
            progress.done(CREATED);
         }
      }
   }

   @Override
   public boolean define(Scope outer) throws Exception {
      if(!define.compareAndSet(false, true)) {
         Type type = builder.define(outer);
         Progress<Phase> progress = type.getProgress();
         Scope scope = type.getScope();
         
         try {
            collector.update(constants); // collect static constants first
            
            for(TypePart part : parts) {
               Allocation factory = part.define(collector, type, scope);
               collector.update(factory);
            }
            constructor.define(collector, type, scope);
            collector.define(scope, type);
            generator.generate(type);
         } finally {
            progress.done(DEFINED);
         }
      }
      return true;
   }

   @Override
   public Execution compile(Scope outer) throws Exception {
      if(!compile.compareAndSet(false, true)) {
         Type type = builder.compile(outer);
         Progress<Phase> progress = type.getProgress();
         Scope scope = type.getScope();
         Scope local = scope.getStack(); // make it temporary
         
         try {
            for(TypePart part : parts) {
               part.compile(collector, type, local);
            } 
            constructor.compile(collector, type, local);
            collector.compile(local, type);
         } finally {
            progress.done(COMPILED);
         }
      }
      return execution;
   }
}