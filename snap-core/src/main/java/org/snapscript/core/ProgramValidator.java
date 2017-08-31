package org.snapscript.core;

public interface ProgramValidator {
   void validate(Context context) throws Exception;
   void validate(Type type) throws Exception;
   void validate(Module module) throws Exception;
}