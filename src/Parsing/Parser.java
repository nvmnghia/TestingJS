package Parsing;

import org.mozilla.javascript.CompilerEnvirons;
import org.mozilla.javascript.IRFactory;
import org.mozilla.javascript.ast.AstRoot;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class Parser {
    public static AstRoot parse(File JSFile) throws IOException {
        String fileContent = new String(Files.readAllBytes(JSFile.toPath()));
        return parse(fileContent);
    }

    public static AstRoot parse(String JScode) {
        IRFactory factory = new IRFactory();
        return factory.parse(JScode, null, 0);
    }
}

