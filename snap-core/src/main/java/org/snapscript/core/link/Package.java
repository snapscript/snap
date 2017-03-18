
package org.snapscript.core.link;

import org.snapscript.core.Scope;

public interface Package {
   PackageDefinition define(Scope scope) throws Exception;
}
