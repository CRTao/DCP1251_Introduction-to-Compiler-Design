import java.util.Map.Entry;

import exception.MismatchException;
import exception.NotDefineException;

/*	類型步驟化確認	*/
/*	檢查各Node類型是否有錯誤	*/

public class TypeCheckVisitor implements MiniJavaVisitor {

	TypeCheckVisitor(SymbolTable t){
		table=t;
	}
	public SymbolTable table;
	
	public Object visit(SimpleNode node, Object data) {
		throw new RuntimeException("not unimplemented in subclass?");
	}

	/*	Start 接一節點	*/
	public Object visit(ASTStart node, Object data) {
		node.childrenAccept(this, data);
		return data;
	}
	/*	MainClass 接一節點	*/
	public Object visit(ASTMainClass node, Object data) {
		node.childrenAccept(this, data);
		return data;
	}

	/*	Class 接 Class名稱 ＋ 名稱	*/
	public Object visit(ASTClassDeclaration node, Object data) {
		if(node.children.length>1 && node.children[1] instanceof ASTIdentifier){
			String clazz=Util.getClassName(node);
			ASTIdentifier c2=(ASTIdentifier) node.children[1];
			String parentClazz=c2.token.image;
			SymbolTable.ClassSymbolTable cst=table.classes.get(parentClazz);
			do{
				if(clazz.equals(parentClazz)) throw new RuntimeException("circular inherit:"+clazz);
				if(cst==null) throw new NotDefineException(parentClazz+" not defined.");
				if(cst.parent==null) break;
				cst=table.classes.get(cst.parent);
				parentClazz=cst.parent;
			}while(true);
		}
		node.childrenAccept(this, data);
		return data;
	}
	/*	Var 為括號，進下一層	*/
	public Object visit(ASTVarDeclaration node, Object data) {
		return node.children[0];
	}
	/*	Method 為括號，進下一層	*/
	public Object visit(ASTMethodDeclaration node, Object data) {
		node.childrenAccept(this, data);
		return node.children[0];
	}
	/*	Return，確認回傳值，或因為void宣告的錯誤	*/
	public Object visit(ASTReturnExpression node, Object data) {
		if(node.children==null || node.children.length==0){
			ASTMethodDeclaration m=(ASTMethodDeclaration) node.parent;
			ASTType t=(ASTType) m.children[0];
			Util.typeEqual(t, "void");
			return voidType();
		}else{
			SimpleNode n=(SimpleNode) node.children[0];
			Object t= n.jjtAccept(this, data);
			ASTMethodDeclaration m=(ASTMethodDeclaration) node.parent;
			ASTType mt=(ASTType) m.children[0];
			Util.typeEqual(getType(t), mt);
			return t;
		}
	}
	/*	Type類型，單節點	*/
	public Object visit(ASTType node, Object data) {
		return node;
	}
	/*	宣告，類型+名稱		*/
	public Object visit(ASTAssignment node, Object data) {
		SimpleNode n=(SimpleNode) node.children[2];
		Object t= n.jjtAccept(this, data);
		ASTIdentifier i=(ASTIdentifier) node.children[0];
		ASTType t1=getType(i);
		Util.typeEqual(t1,getType(t));
		return t;
	}
	/*	Array宣告，終點值	*/
	public Object visit(ASTArrayAssignment node, Object data) {
		return null;
	}
	/*	運算符號宣告	*/
	public Object visit(ASTAssignmentOperator node, Object data) {
		return data;
	}
	/*	If，檢查是否為Boolean後將expr內容推進	*/
	public Object visit(ASTIfStatement node, Object data) {
		SimpleNode n=(SimpleNode) node.children[0];
		Object t=n.jjtAccept(this, data);
		Util.typeEqual(getType(t), "boolean");
		for(int i=1;i<node.children.length;i++)
			node.children[i].jjtAccept(this, data);
		return null;
	}
	/*	While，檢查是否為Boolean後將expr內容推進	*/
	public Object visit(ASTWhileStatement node, Object data) {
		SimpleNode n=(SimpleNode) node.children[0];
		Object t=n.jjtAccept(this, data);
		Util.typeEqual(getType(t), "boolean");
		node.children[1].jjtAccept(this, data);
		return null;
	}
	/*	印刷，終點值	*/
	public Object visit(ASTPrintStatement node, Object data) {
		return null;
	}
	/*	關係expr，確認是否有兩個比較數	*/
	public Object visit(ASTRelationalExpression node, Object data) {
		SimpleNode n1=(SimpleNode) node.children[0];
		Object t1=n1.jjtAccept(this, data);
		Util.typeEqual(getType(t1),"int");
		SimpleNode n2=(SimpleNode) node.children[2];
		Object t2=n2.jjtAccept(this, data);
		Util.typeEqual(getType(t2),"int");
		return booleanType();
	}
	/*	符號，終點值	*/
	public Object visit(ASTRelationalOperator node, Object data) {
		return null;
	}
	/*	存位置與名稱	*/
	public Object visit(ASTArrayExpression node, Object data) {
		Object ret=node.children[0].jjtAccept(this, data);
		node.children[1].jjtAccept(this, data);
		return ret;
	}
	/*	呼叫函數，給其他Visitor檢查用	*/
	public Object visit(ASTCallNode node, Object data) {
		ASTType clazz=(ASTType)node.children[0].jjtAccept(this, data);
		ASTIdentifier method=(ASTIdentifier) node.children[1];
		ASTExpList expList=(ASTExpList) node.children[2];
		SymbolTable.MethodSymbolTable mt=table.findMethod(Util.typeString(clazz),method.tokenImage());
		if(mt.params.size()==0){
			if(expList.children!=null && expList.children.length>0){
				 throw new MismatchException("method "+method.tokenImage()+" argument number mismatch");
			}
		}else{
			if(expList.children==null || mt.params.size()!=expList.children.length) throw new MismatchException("method "+method.tokenImage()+" argument number mismatch");
			int i=0;
			for(Entry<String, ASTType> param : mt.params.entrySet()){
				Node exp=expList.children[i++];
				Object real=exp.jjtAccept(this, data);
				ASTType formula=param.getValue();
				Util.typeEqual(getType(real), formula);
			}
		}
		return mt.returnType;
	}
	
	/*	加法	*/
	public Object visit(ASTAddNode node, Object data) {
		SimpleNode n1=(SimpleNode) node.children[0];
		Object t1=n1.jjtAccept(this, data);
		Util.typeEqual(getType(t1),"int");
		SimpleNode n2=(SimpleNode) node.children[1];
		Object t2=n2.jjtAccept(this, data);
		Util.typeEqual(getType(t2),"int");
		return t1;
	}
	/*	乘法	*/
	public Object visit(ASTMultiNode node, Object data) {
		SimpleNode n1=(SimpleNode) node.children[0];
		Object t1=n1.jjtAccept(this, data);
		Util.typeEqual(getType(t1),"int");
		SimpleNode n2=(SimpleNode) node.children[1];
		Object t2=n2.jjtAccept(this, data);
		Util.typeEqual(getType(t2),"int");
		return t1;
	}
	/*	.length		*/
	public Object visit(ASTLengthNode node, Object data) {
		return null;
	}
	/*	new + class		*/
	public Object visit(ASTNewClassNode node, Object data) {
		SimpleNode n1=(SimpleNode) node.children[0];
		SymbolTable.ClassSymbolTable clazz= table.findClass(n1.token.image);
		return n1;
	}
	/*	new + array		*/
	public Object visit(ASTNewIntArrayNode node, Object data) {
		return null;
	}
	/*	負數	*/
	public Object visit(ASTNegNode node, Object data) {
		SimpleNode n1=(SimpleNode) node.children[0];
		ASTType t1=(ASTType) n1.jjtAccept(this, data);
		Util.typeEqual(t1, "boolean");
		return t1;
	}

	/*	回傳上層Node類型	*/
	public Object visit(ASTThisNode node, Object data) {
		ASTMethodDeclaration m = getEncloseingMethod(node);
		ASTClassDeclaration clazz = (ASTClassDeclaration) m.parent;
		return objectType(((ASTIdentifier)clazz.children[0]).tokenImage());
	}
	/*	int const	*/
	public Object visit(ASTIntConstNode node, Object data) {
		ASTType i=new ASTType(Integer.parseInt(node.token.image));
		i.token=new Token();
		i.token.image="int";
		return i;
	}
	/*	True	*/
	public Object visit(ASTTrueNode node, Object data) {
		return booleanType();
	}
	/*	False	*/
	public Object visit(ASTFalseNode node, Object data) {
		return booleanType();
	}

	/*	expList，可無限延伸		*/
	public Object visit(ASTExpList node, Object data) {
		if(node.children.length==0){
			ASTType i=new ASTType(-1);
			i.token=new Token();
			i.token.image="void";
			return i;
		}
        for (int i = 1; i < node.children.length; ++i) {
	          node.children[i].jjtAccept(this, data);
	        }
		return node.children[0].jjtAccept(this, data);
	}
	/*	宣告	*/
	public Object visit(ASTIdentifier node, Object data) {
		return node;
	}
	/*	該Node的類型	*/
	private ASTType getType(Object o){
		if(o instanceof ASTType) return (ASTType) o;
		if(o instanceof ASTIdentifier){
			ASTIdentifier i=(ASTIdentifier) o;
			ASTType t1 = getType(i);
			return t1;
		}
		throw new RuntimeException("impossible.");
	}
	/*	該類型的名稱	*/
	private ASTType getType(ASTIdentifier i) {
		try{
			table.findClass(i.tokenImage());
			return objectType(i.tokenImage());
		}catch(NotDefineException e){}
		ASTMethodDeclaration m = getEncloseingMethod(i);
		ASTClassDeclaration clazz = (ASTClassDeclaration) m.parent;
		ASTType t1 = table.findLocalOrParam(i.tokenImage(), Util.getMethodName(m),
				Util.getClassName(clazz));
		return t1;
	}
	/*	內外括號是否吻合	*/
	private ASTMethodDeclaration getEncloseingMethod(SimpleNode i){
		SimpleNode p = (SimpleNode) i.parent;
		while (!(p instanceof ASTMethodDeclaration)) {
			p = (SimpleNode) p.parent;
		}
		return (ASTMethodDeclaration) p;
	}
	/*	該Node的名稱	*/
	private ASTType objectType(String name){
		ASTType i=new ASTType(0);
		i.token=new Token();
		i.token.image=name;
		return i;
	}
	/*	boolean		*/
	private ASTType booleanType() {
		ASTType i=new ASTType(0);
		i.token=new Token();
		i.token.image="boolean";
		return i;
	}
	/*	void		*/
	private ASTType voidType() {
		ASTType i=new ASTType(0);
		i.token=new Token();
		i.token.image="void";
		return i;
	}
	
}
