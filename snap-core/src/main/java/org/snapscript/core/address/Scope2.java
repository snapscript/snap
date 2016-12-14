package org.snapscript.core.address;

import org.snapscript.core.Handle;
import org.snapscript.core.Model;
import org.snapscript.core.Module;
import org.snapscript.core.Scope;
import org.snapscript.core.Type;

public interface Scope2 extends Handle {
   Type getType();
   State2 getStack(); // stack only
   State2 getState(); // both object and stack
   Scope getObject();
   Module getModule();   
   Model getModel();
}
