package org.snapscript.core;

public interface Scope extends Handle {
   Type getType();
   Scope getInner(); // extend on current scope
   Scope getOuter(); // get callers scope
   Module getModule();   
   State getState();
   Model getModel();
}