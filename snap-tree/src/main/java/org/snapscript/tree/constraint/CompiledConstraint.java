package org.snapscript.tree.constraint;

import static org.snapscript.core.type.Phase.DEFINE;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import org.snapscript.common.Progress;
import org.snapscript.core.InternalStateException;
import org.snapscript.core.constraint.Constraint;
import org.snapscript.core.scope.Scope;
import org.snapscript.core.type.Phase;
import org.snapscript.core.type.Type;

public class CompiledConstraint extends Constraint {
   
   private final AtomicBoolean defined;
   private final Constraint constraint;
   private final long duration;

   public CompiledConstraint(Constraint constraint) {
      this(constraint, 10000);
   }
   
   public CompiledConstraint(Constraint constraint, long duration) {
      this.defined = new AtomicBoolean();
      this.constraint = constraint;
      this.duration = duration;
   }
   
   @Override
   public List<Constraint> getGenerics(Scope scope) {
      if(!defined.get()) {
         Type result = constraint.getType(scope);
         Progress<Phase> progress = result.getProgress();
         
         if(!progress.wait(DEFINE, duration)) {
            throw new InternalStateException("Type '" + result + "' not compiled");
         }
         defined.set(true);
      }
      return constraint.getGenerics(scope);
   }
   
   @Override
   public Type getType(Scope scope) {
      if(!defined.get()) {
         Type result = constraint.getType(scope);
         Progress<Phase> progress = result.getProgress();
         
         if(!progress.wait(DEFINE, duration)) {
            throw new InternalStateException("Type '" + result + "' not compiled");
         }
         defined.set(true);
      }
      return constraint.getType(scope);
   }
   
   @Override
   public String getName(Scope scope) {
      if(!defined.get()) {
         Type result = constraint.getType(scope);
         Progress<Phase> progress = result.getProgress();
         
         if(!progress.wait(DEFINE, duration)) {
            throw new InternalStateException("Type '" + result + "' not compiled");
         }
         defined.set(true);
      }
      return constraint.getName(scope);
   }
}