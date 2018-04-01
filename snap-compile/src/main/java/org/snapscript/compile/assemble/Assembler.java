package org.snapscript.compile.assemble;

import org.snapscript.core.module.Path;
import org.snapscript.parse.SyntaxNode;

public interface Assembler {
   <T> T assemble(SyntaxNode token, Path path) throws Exception;
}