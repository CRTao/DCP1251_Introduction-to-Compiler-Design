/* Generated By:JJTree: Do not edit this line. ASTFalseNode.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=true,TRACK_TOKENS=false,NODE_PREFIX=AST,NODE_EXTENDS=MyNode,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
public
class ASTFalseNode extends SimpleNode {
  public ASTFalseNode(int id) {
    super(id);
  }

  public ASTFalseNode(MiniJava p, int id) {
    super(p, id);
  }


  /** Accept the visitor. **/
  public Object jjtAccept(MiniJavaVisitor visitor, Object data) {
    return visitor.visit(this, data);
  }
}
/* JavaCC - OriginalChecksum=d1b2e56ad3974494a81717a1a52a6e8b (do not edit this line) */
