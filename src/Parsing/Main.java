package Parsing;

import com.google.gson.Gson;
import org.mozilla.javascript.Function;
import org.mozilla.javascript.Node;
import org.mozilla.javascript.ast.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        AstRoot root = Parser.parse(new File("javascript.js"));
        System.out.println(root.toSource());

        System.out.println("__________________________________________________________________________");

//        String[] paramAndVar = root.getParamAndVarNames();
//        for (String str : paramAndVar) {
//            System.out.print(str + " ");
//        }

        List<Symbol> listSymbols = root.getSymbols();
        System.out.println(listSymbols.size());
        for (Symbol symbol : listSymbols) {
            System.out.println(symbol.getDeclTypeName() + " " + symbol.getName());
        }

        List<FunctionNode> listFunctions = root.getFunctions();
        System.out.println(listFunctions.size());
        for (FunctionNode func : listFunctions) {
            System.out.println(func.getName() + "    " + func.toString());
        }

//        Node firstNodeBody = ((FunctionNode) root.getFirstChild()).getBody();
//        traverse(firstNodeBody);

        simpleTraverse(root.getFirstChild());

//        root.visitAll(new NodeVisitor() {
//            @Override
//            public boolean visit(AstNode node) {
//                System.out.println(String.format("%1$3d", node.getLineno()) + "  " + node.getClass());
//                return true;
//            }
//        });

//        root.visit(new NodeVisitor() {
//            @Override
//            public boolean visit(AstNode node) {
//                System.out.println(String.format("%1$3d", node.getLineno()) + "  " + node.getClass());
//
//                return true;
//            }
//        });
    }

    public static void traverse(Node node) {
        while (node != null) {
            System.out.println(node.getType() + "    " + node.getLineno());

            if (node instanceof FunctionNode) {
                FunctionNode thisNode = (FunctionNode) node;

                System.out.println("FunctionNode: " + thisNode.getFunctionName() + " " + thisNode.getFunctionType());
                AstNode body = thisNode.getBody();
                System.out.println("Traversing into body of " + thisNode.getFunctionName());
                traverse(body);
            } else if (node instanceof VariableDeclaration) {
                System.out.println("VariableDeclaration: " + ((VariableDeclaration) node).getVariables());
            } else {
                System.out.println("WHAT THE FUCK IS " + node.getClass());
            }
            System.out.println("");

            System.out.println("Start traverse child");
            Node childNode = node.getFirstChild();
            traverse(childNode);
            System.out.println("Stop traverse child");

            System.out.println("Move to next");
            node = node.getNext();
        }
        System.out.println("End traverse");
    }

    private static int depth = 0;    // For pretty printing
    public static void simpleTraverse(Node node) {
        StringBuilder builder = new StringBuilder("");
        for (int i = 0; i < depth; ++i) {
            builder.append('\t');
        }
        String tabs = builder.toString();

        while (node != null) {
            System.out.println(node.getClass() + " " + node.getLineno());

//            Node childNode = node.getFirstChild();
            for (Node childNode : node) {
                if (childNode != null) {
                    ++depth;
                    System.out.println(tabs + "Start traversing child");

                    simpleTraverse(childNode);

                    --depth;
                    System.out.println(tabs + "Stop traversing child");
                }
            }

            System.out.println("Move to next");
            node = node.getNext();
        }

        System.out.println(tabs + "End traversing");

//        if (node != null) {
//            AstNode thisNode = (AstNode) node;
//            for (Node sib : thisNode) {
//                System.out.println(sib.getClass().getName().substring(33) + " " + sib.getLineno());
//                simpleTraverse(sib);
//            }
//
//            simpleTraverse(thisNode.getNext());
//        }
    }
}
