package org.snapscript.core.platform;

public interface Bridge {
   <T> T getInstance();
   void setInstance(Object object);
}