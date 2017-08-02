package org.snapscript.core;

public interface PathConverter {
   Path createPath(String resource);
   String createModule(String resource);
}