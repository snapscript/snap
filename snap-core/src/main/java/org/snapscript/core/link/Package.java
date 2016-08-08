package org.snapscript.core.link;

import org.snapscript.core.Scope;
import org.snapscript.core.Statement;

public interface Package {
   Statement compile(Scope scope) throws Exception;
}
