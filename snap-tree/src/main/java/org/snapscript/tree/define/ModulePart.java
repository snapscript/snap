package org.snapscript.tree.define;

import org.snapscript.core.Statement;
import org.snapscript.core.module.Module;

public interface ModulePart {
   Statement define(ModuleBody body, Module module) throws Exception;
}
