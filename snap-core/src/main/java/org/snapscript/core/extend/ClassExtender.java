package org.snapscript.core.extend;

import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import org.snapscript.core.TypeLoader;
import org.snapscript.core.function.Function;
import org.snapscript.core.thread.ThreadStack;

public class ClassExtender {
   
   private final ExtensionRegistry registry;
   private final AtomicBoolean done;
   
   public ClassExtender(TypeLoader loader, ThreadStack stack) {
      this.registry = new ExtensionRegistry(loader, stack);
      this.done = new AtomicBoolean();
   }
   
   public List<Function> extend(Class type){
      if(done.compareAndSet(false, true)) {
         registry.register(File.class, FileExtension.class);
         registry.register(Date.class, DateExtension.class);
      }
      return registry.extract(type);
   }

}
