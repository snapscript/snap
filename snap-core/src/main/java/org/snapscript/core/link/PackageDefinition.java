package org.snapscript.core.link;

import org.snapscript.core.Statement;
import org.snapscript.core.module.Path;
import org.snapscript.core.scope.Scope;

public interface PackageDefinition {
   Statement define(Scope scope, Path from) throws Exception;
}