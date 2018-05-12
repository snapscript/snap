package org.snapscript.compile;

import org.snapscript.core.Any;
import org.snapscript.core.scope.Model;

public interface Executable extends Any {   
   void execute() throws Exception;
   void execute(Model model) throws Exception;
   void execute(Model model, boolean test) throws Exception;
}