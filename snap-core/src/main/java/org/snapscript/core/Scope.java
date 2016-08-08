package org.snapscript.core;

public interface Scope extends Any {
   Type getType();
   Scope getInner();
   Scope getOuter();
   Module getModule();   
   State getState();
   Model getModel();
}
