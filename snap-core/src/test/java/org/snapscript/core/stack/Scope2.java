package org.snapscript.core.stack;

import org.snapscript.core.Handle;
import org.snapscript.core.Model;
import org.snapscript.core.Module;
import org.snapscript.core.Scope;
import org.snapscript.core.State;
import org.snapscript.core.Type;

public interface Scope2 extends Handle {
   Type getType();
   Scope getInner();
   Scope getOuter();
   Module getModule();   
   State getState();
   Model getModel();
}
