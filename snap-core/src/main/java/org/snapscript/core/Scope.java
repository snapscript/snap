package org.snapscript.core;

public interface Scope extends Handle {
   Type getType();
   Scope getStack(); // extend on current scope
   Scope getScope(); // get callers scope
   Module getModule();  
   Counter getCounter();
   Table getTable(); 
   State getState();
}