/* Generated By:JavaCC: Do not edit this line. MiniJavaVisitor.java Version 5.0 */
public interface MiniJavaVisitor
{
  public Object visit(SimpleNode node, Object data);
  public Object visit(ASTStart node, Object data);
  public Object visit(ASTMainClass node, Object data);
  public Object visit(ASTClassDeclaration node, Object data);
  public Object visit(ASTVarDeclaration node, Object data);
  public Object visit(ASTMethodDeclaration node, Object data);
  public Object visit(ASTReturnExpression node, Object data);
  public Object visit(ASTType node, Object data);
  public Object visit(ASTAssignment node, Object data);
  public Object visit(ASTArrayAssignment node, Object data);
  public Object visit(ASTAssignmentOperator node, Object data);
  public Object visit(ASTIfStatement node, Object data);
  public Object visit(ASTWhileStatement node, Object data);
  public Object visit(ASTPrintStatement node, Object data);
  public Object visit(ASTRelationalExpression node, Object data);
  public Object visit(ASTRelationalOperator node, Object data);
  public Object visit(ASTArrayExpression node, Object data);
  public Object visit(ASTCallNode node, Object data);
  public Object visit(ASTAddNode node, Object data);
  public Object visit(ASTMultiNode node, Object data);
  public Object visit(ASTLengthNode node, Object data);
  public Object visit(ASTNewClassNode node, Object data);
  public Object visit(ASTNewIntArrayNode node, Object data);
  public Object visit(ASTNegNode node, Object data);
  public Object visit(ASTThisNode node, Object data);
  public Object visit(ASTIntConstNode node, Object data);
  public Object visit(ASTTrueNode node, Object data);
  public Object visit(ASTFalseNode node, Object data);
  public Object visit(ASTExpList node, Object data);
  public Object visit(ASTIdentifier node, Object data);
}
/* JavaCC - OriginalChecksum=28d5bada048faba9fa784ed4a991abe5 (do not edit this line) */