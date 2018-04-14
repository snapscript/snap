package org.snapscript.tree.define;

import static org.snapscript.core.Reserved.TYPE_THIS;
import static org.snapscript.core.constraint.Constraint.NONE;

import java.util.concurrent.atomic.AtomicReference;

import org.snapscript.core.Execution;
import org.snapscript.core.Statement;
import org.snapscript.core.constraint.Constraint;
import org.snapscript.core.module.Module;
import org.snapscript.core.scope.Scope;
import org.snapscript.core.scope.State;
import org.snapscript.core.variable.Value;
import org.snapscript.tree.annotation.AnnotationList;

public class ModuleDefinition extends Statement {   
   
   private final AtomicReference<Module> reference;
   private final ModuleBuilder builder;
   private final Statement body;
   
   public ModuleDefinition(AnnotationList annotations, ModuleName module, ModulePart... parts) {
      this.builder = new ModuleBuilder(annotations, module);
      this.reference = new AtomicReference<Module>();
      this.body = new ModuleBody(parts);
   }
   
   @Override
   public void create(Scope scope) throws Exception {
      Module module = builder.create(scope);
      Scope inner = module.getScope();
      
      reference.set(module);
      body.create(inner); 
   }

   @Override
   public boolean define(Scope scope) throws Exception {
      Module module = reference.get();
      Value value = Value.getTransient(module);
      Scope inner = module.getScope();
      State state = inner.getState();
      
      state.add(TYPE_THIS, value);
      body.define(inner); // must be module scope
      
      return true;
   }
   
   @Override
   public Execution compile(Scope scope, Constraint returns) throws Exception {
      Module module = reference.get();
      Scope inner = module.getScope();
      Scope local = inner.getStack();
      
      return body.compile(local, null); // must be module scope
   }
}