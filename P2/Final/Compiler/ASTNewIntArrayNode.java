/* Generated By:JJTree: Do not edit this line. ASTNewIntArrayNode.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=true,TRACK_TOKENS=false,NODE_PREFIX=AST,NODE_EXTENDS=MyNode,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
public
class ASTNewIntArrayNode extends SimpleNode {
  public ASTNewIntArrayNode(int id) {
    super(id);
  }

  public ASTNewIntArrayNode(MiniJava p, int id) {
    super(p, id);
  }


  /** Accept the visitor. **/
  public Object jjtAccept(MiniJavaVisitor visitor, Object data) {
    return visitor.visit(this, data);
  }
}
/* JavaCC - OriginalChecksum=7dc77eb1c9187ce6f6df23fbeec17343 (do not edit this line) */
