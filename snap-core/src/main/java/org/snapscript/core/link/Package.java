package org.snapscript.core.link;

import org.snapscript.core.Scope;

public interface Package {
   PackageDefinition create(Scope scope) throws Exception;
}