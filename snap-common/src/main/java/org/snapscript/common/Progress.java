package org.snapscript.common;

public interface Progress<T extends Enum> {
   boolean done(T phase);
   boolean wait(T phase);
   boolean wait(T phase, long duration);
}