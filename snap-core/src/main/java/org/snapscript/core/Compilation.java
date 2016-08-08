package org.snapscript.core;

public interface Compilation {
   Object compile(Module module, int line) throws Exception;
}
