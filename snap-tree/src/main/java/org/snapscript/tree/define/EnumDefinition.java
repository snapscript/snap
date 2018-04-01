package org.snapscript.tree.define;

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
import org.snapscript.core.TypeFactory;
import org.snapscript.core.TypePart;
import org.snapscript.tree.annotation.AnnotationList;

public class EnumDefinition extends Statement {

   private final DefaultConstructor constructor;
   private final TypeFactoryCollector collector;
   private final AtomicBoolean compile;
   private final AtomicBoolean define;
   private final AtomicBoolean create;
   private final EnumBuilder builder;
   private final Execution execution;
   private final EnumList list;
   private final TypePart[] parts;
   
   public EnumDefinition(AnnotationList annotations, TypeName name, TypeHierarchy hierarchy, EnumList list, TypePart... parts) {
      this.builder = new EnumBuilder(name, hierarchy);
      this.constructor = new DefaultConstructor(true);
      this.collector = new TypeFactoryCollector();
      this.execution = new NoExecution(NORMAL);
      this.compile = new AtomicBoolean(true);
      this.define = new AtomicBoolean(true);
      this.create = new AtomicBoolean(true);
      this.parts = parts;
      this.list = list;
   }
   
   @Override
   public void create(Scope outer) throws Exception {
      if(!create.compareAndSet(false, true)) {
         Type type = builder.create(outer);
         Progress<Phase> progress = type.getProgress();
      
         progress.done(CREATED);
      }
   }

   @Override
   public boolean define(Scope outer) throws Exception {
      if(!define.compareAndSet(false, true)) {
         Type type = builder.define(outer);
         Scope scope = type.getScope();
         TypeFactory keys = list.define(collector, type, scope);
         Progress<Phase> progress = type.getProgress();
         
         try {
            collector.update(keys); // collect enum constants first
            
            for(TypePart part : parts) {
               TypeFactory factory = part.define(collector, type, scope);
               collector.update(factory);
            }  
            constructor.define(collector, type, scope); 
            collector.define(scope, type); 
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
               TypeFactory factory = part.compile(collector, type, local);
               collector.update(factory);
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