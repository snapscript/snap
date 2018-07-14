package org.snapscript.core;

import org.snapscript.common.Progress;
import org.snapscript.core.scope.Scope;
import org.snapscript.core.type.Phase;

public interface Entity extends Any{ // Artifact
   Progress<Phase> getProgress();
   Scope getScope();
   String getName();
   int getModifiers();
   int getOrder();
}
