package org.snapscript.core;

public interface Compilation {
   Object compile(Module module, Path path, int line) throws Exception;
}