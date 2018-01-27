package org.snapscript.core;

import java.util.List;

import org.snapscript.common.Progress;

public interface Type extends Entity {
   Progress<Phase> getProgress();
   List<Type> getTypes();
   Category getCategory();
   Module getModule();
   Scope getScope();
   Class getType();
   Type getOuter();
   Type getEntry();
   int getOrder();
}