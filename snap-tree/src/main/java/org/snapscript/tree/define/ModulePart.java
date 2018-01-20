package org.snapscript.tree.define;

import org.snapscript.core.Statement;

public interface ModulePart {
   Statement define(ModuleBody body) throws Exception;
}
