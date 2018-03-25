package org.snapscript.core;

public interface Allocator {
   <T extends Value> T compile(Scope scope, String name, int modifiers) throws Exception;
   <T extends Value> T allocate(Scope scope, String name, int modifiers) throws Exception;
}
