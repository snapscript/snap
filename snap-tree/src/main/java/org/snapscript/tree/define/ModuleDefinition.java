package org.snapscript.tree.define;

import static org.snapscript.core.Reserved.TYPE_THIS;

import java.util.concurrent.atomic.AtomicReference;

import org.snapscript.core.Module;
import org.snapscript.core.Result;
import org.snapscript.core.Scope;
import org.snapscript.core.State;
import org.snapscript.core.Statement;
import org.snapscript.core.Value;
import org.snapscript.tree.annotation.AnnotationList;

public class ModuleDefinition extends Statement {   
   
   private final AtomicReference<Module> reference;
   private final ModuleBuilder builder;
   private final Statement body;
   
   public ModuleDefinition(AnnotationList annotations, ModuleName module, Statement... body) {
      this.builder = new ModuleBuilder(annotations, module);
      this.reference = new AtomicReference<Module>();
      this.body = new ModuleBody(body);
   }
   
   @Override
   public Result define(Scope scope) throws Exception {
      Module module = builder.create(scope);
      Scope inner = module.getScope();
      
      reference.set(module);
      
      return body.define(inner); 
   }

   @Override
   public Result compile(Scope scope) throws Exception {
      Module module = reference.get();
      Value value = Value.getTransient(module);
      Scope inner = module.getScope();
      State state = inner.getState();
      
      state.add(TYPE_THIS, value);
      
      return body.compile(inner); // must be module scope
   }
   
   @Override
   public Result execute(Scope scope) throws Exception {
      Module module = reference.get();
      Scope inner = module.getScope();
      
      return body.execute(inner); // requires order for use!
   }
}