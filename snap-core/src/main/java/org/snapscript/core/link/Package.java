package org.snapscript.core.link;

import org.snapscript.core.scope.Scope;

public interface Package {
   PackageDefinition create(Scope scope) throws Exception;
}