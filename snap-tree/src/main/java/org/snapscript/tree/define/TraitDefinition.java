package org.snapscript.tree.define;

import static org.snapscript.core.result.Result.NORMAL;
import static org.snapscript.core.type.Category.TRAIT;
import static org.snapscript.core.type.Phase.COMPILE;
import static org.snapscript.core.type.Phase.CREATE;
import static org.snapscript.core.type.Phase.DEFINE;

import java.util.concurrent.atomic.AtomicBoolean;

import org.snapscript.common.Progress;
import org.snapscript.core.Execution;
import org.snapscript.core.NoExecution;
import org.snapscript.core.Statement;
import org.snapscript.core.constraint.Constraint;
import org.snapscript.core.scope.Scope;
import org.snapscript.core.type.TypeState;
import org.snapscript.core.type.Phase;
import org.snapscript.core.type.Type;
import org.snapscript.core.type.TypePart;
import org.snapscript.tree.annotation.AnnotationList;

public class TraitDefinition extends Statement {   
   
   private final FunctionPropertyGenerator generator;
   private final TypeStateCollector collector;
   private final TypeState constants;
   private final AtomicBoolean compile;
   private final AtomicBoolean define;
   private final AtomicBoolean create;
   private final ClassBuilder builder;
   private final Execution execution;
   private final TypePart[] parts;
   
   public TraitDefinition(AnnotationList annotations, TraitName name, TypeHierarchy hierarchy, TypePart... parts) {
      this.builder = new ClassBuilder(annotations, name, hierarchy, TRAIT);
      this.generator = new FunctionPropertyGenerator(); 
      this.constants = new StaticState();
      this.collector = new TypeStateCollector();
      this.execution = new NoExecution(NORMAL);
      this.compile = new AtomicBoolean(true);
      this.define = new AtomicBoolean(true);
      this.create = new AtomicBoolean(true);
      this.parts = parts;
   }
   
   @Override
   public void create(Scope outer) throws Exception {
      if(!create.compareAndSet(false, true)) {
         Type type = builder.create(collector, outer);
         Progress<Phase> progress = type.getProgress();
         Scope scope = type.getScope();
         
         try {
            for(TypePart part : parts) {
               part.create(collector, type, scope);               
            } 
         } finally {
            progress.done(CREATE);
         }
      }
   }

   @Override
   public boolean define(Scope outer) throws Exception {
      if(!define.compareAndSet(false, true)) {
         Type type = builder.define(collector, outer);
         Progress<Phase> progress = type.getProgress();
         Scope scope = type.getScope();
         
         try {
            collector.update(constants); // collect static constants first
            
            for(TypePart part : parts) {
               TypeState state = part.define(collector, type, scope);
               collector.update(state);
            } 
            collector.define(scope, type);
            generator.generate(type);
         } finally {
            progress.done(DEFINE); 
         }
      }
      return true;
   }
   
   @Override
   public Execution compile(Scope outer, Constraint returns) throws Exception {
      if(!compile.compareAndSet(false, true)) {
         Type type = builder.compile(collector, outer);
         Progress<Phase> progress = type.getProgress();
         Scope scope = type.getScope();
         Scope local = scope.getStack(); // make it temporary
         
         try {
            collector.compile(local, type);
         } finally {
            progress.done(COMPILE); 
         }
      }
      return execution;
   }
}