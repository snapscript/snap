package org.snapscript.tree.define;

import org.snapscript.core.Type;
import org.snapscript.core.define.Initializer;

public interface TypePart {
   Initializer define(Initializer initializer, Type type) throws Exception;
   Initializer compile(Initializer initializer, Type type) throws Exception;
}
