package org.snapscript.core;

public interface Scope extends Handle {
   Type getType();
   Scope getInner(); // extend on current scope
   Scope getOuter(); // get callers scope
   Counter getCounter();
   Module getModule();   
   State getState();
   Model getModel();
}