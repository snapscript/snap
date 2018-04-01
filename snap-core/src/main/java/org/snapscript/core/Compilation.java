package org.snapscript.core;

import org.snapscript.core.module.Module;
import org.snapscript.core.module.Path;

public interface Compilation {
   Object compile(Module module, Path path, int line) throws Exception;
}