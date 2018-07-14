package org.snapscript.core.link;

import java.util.concurrent.Future;

import org.snapscript.core.Entity;
import org.snapscript.core.module.Module;
import org.snapscript.core.type.Type;

public interface ImportManager {
   void addImport(String prefix);
   void addImport(String type, String alias);
   void addImport(Type type, String alias);
   void addImports(Module module);
   Future<Entity> getImport(String name);
}