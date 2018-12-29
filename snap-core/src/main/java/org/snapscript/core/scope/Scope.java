package org.snapscript.core.scope;

import org.snapscript.core.Handle;
import org.snapscript.core.module.Module;
import org.snapscript.core.scope.index.ScopeIndex;
import org.snapscript.core.scope.index.ScopeTable;
import org.snapscript.core.type.Type;
import org.snapscript.core.variable.Value;

public interface Scope extends Handle {
   Type getType();
   Scope getStack(); // extend on current scope
   Scope getScope(); // get callers scope  
   ScopeIndex getIndex();
   ScopeTable getTable(); 
   ScopeState getState();   
   Module getModule();
   Value getThis();
}