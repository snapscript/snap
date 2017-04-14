
package org.snapscript.tree.define;

import org.snapscript.core.Type;
import org.snapscript.core.TypeFactory;

public interface TypePart {
   TypeFactory define(TypeFactory factory, Type type) throws Exception;
   TypeFactory compile(TypeFactory factory, Type type) throws Exception;
}
