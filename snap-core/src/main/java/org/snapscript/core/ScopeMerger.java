package org.snapscript.core;

import static org.snapscript.core.StateType.MODULE;

import org.snapscript.core.thread.ThreadStack;
import org.snapscript.core.thread.ThreadState;

public class ScopeMerger {

   private final PathConverter converter;
   private final Context context;
   
   public ScopeMerger(Context context) {
      this.converter = new PathConverter();
      this.context = context;
   }
   
   public Scope merge(Model model, String name) {
      String path = converter.createPath(name);
      
      if(path == null) {
         throw new InternalStateException("Module '" +name +"' does not have a path"); 
      }
      return merge(model, name, path);
   }
   
   public Scope merge(Model model, String name, String path) {
      ModuleRegistry registry = context.getRegistry();
      Module module = registry.addModule(name, path);
      ThreadStack stack = context.getStack();
      ThreadState state = stack.state();
      Scope scope = module.getScope();
      int order = module.getOrder();
      
      return new ProgramScope(module, scope, state, model, order | MODULE.mask);
   }
}
