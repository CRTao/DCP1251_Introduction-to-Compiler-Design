/* Generated By:JJTree: Do not edit this line. ASTLengthNode.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=true,TRACK_TOKENS=false,NODE_PREFIX=AST,NODE_EXTENDS=MyNode,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
public
class ASTLengthNode extends SimpleNode {
  public ASTLengthNode(int id) {
    super(id);
  }

  public ASTLengthNode(MiniJava p, int id) {
    super(p, id);
  }


  /** Accept the visitor. **/
  public Object jjtAccept(MiniJavaVisitor visitor, Object data) {
    return visitor.visit(this, data);
  }
}
/* JavaCC - OriginalChecksum=1a6df133e13055f17ca3c9014d36836c (do not edit this line) */
