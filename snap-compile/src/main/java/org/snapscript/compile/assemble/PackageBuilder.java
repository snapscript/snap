package org.snapscript.compile.assemble;

import static org.snapscript.core.Reserved.GRAMMAR_FILE;

import java.util.concurrent.Executor;

import org.snapscript.core.Context;
import org.snapscript.core.Statement;
import org.snapscript.core.link.Package;
import org.snapscript.core.link.StatementPackage;
import org.snapscript.core.module.FilePathConverter;
import org.snapscript.core.module.Path;
import org.snapscript.core.module.PathConverter;
import org.snapscript.parse.SyntaxCompiler;
import org.snapscript.parse.SyntaxNode;
import org.snapscript.parse.SyntaxParser;

public class PackageBuilder {

   private final SyntaxCompiler compiler;
   private final PathConverter converter;
   private final Assembler assembler;   
   
   public PackageBuilder(Context context, Executor executor) {
      this.compiler = new SyntaxCompiler(GRAMMAR_FILE);
      this.assembler = new OperationAssembler(context, executor);
      this.converter = new FilePathConverter();
   }

   public Package create(Path path, String source, String grammar) throws Exception {
      String resource = path.getPath();
      SyntaxParser parser = compiler.compile();
      SyntaxNode node = parser.parse(resource, source, grammar); // source could be split here!
      Statement statement = assembler.assemble(node, path);
      String module = converter.createModule(resource);

      return new StatementPackage(statement, path, module);
   } 
}