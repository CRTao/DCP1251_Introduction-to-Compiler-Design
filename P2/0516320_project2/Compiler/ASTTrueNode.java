/* Generated By:JJTree: Do not edit this line. ASTTrueNode.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=true,TRACK_TOKENS=false,NODE_PREFIX=AST,NODE_EXTENDS=MyNode,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
public
class ASTTrueNode extends SimpleNode {
  public ASTTrueNode(int id) {
    super(id);
  }

  public ASTTrueNode(MiniJava p, int id) {
    super(p, id);
  }


  /** Accept the visitor. **/
  public Object jjtAccept(MiniJavaVisitor visitor, Object data) {
    return visitor.visit(this, data);
  }
}
/* JavaCC - OriginalChecksum=95a78b4d7b13da8cb057e708adb6e903 (do not edit this line) */