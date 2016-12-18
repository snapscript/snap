package org.snapscript.core;

import org.snapscript.core.State;

public interface Scope extends Handle {
   Type getType();
   Scope getInner();
   Scope getObject();
   Module getModule();   
   State getState();
   State getStack();
   Model getModel();
}
