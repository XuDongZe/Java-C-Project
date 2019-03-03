#include "globals.h"
#include "util.h"
#include "scan.h"
#include "parse.h"
#include "symtable.h"

static TokenEntry * token; /* holds current token */

#define SETERROR (Error=TRUE)
#define ERROR (Error==TRUE)

static TreeNode * Program();
static TreeNode * ProgramHead();
static TreeNode * ProgramBody();
/* 参数声明列表 */
static TreeNode * ParaList();
static TreeNode * ParaListSuf();
/* 声明语句列表 */
static TreeNode * DeclarList();
static TreeNode * DeclarStmt();
static TreeNode * IDList(TokenType type);
static TreeNode * IDListSuf(TokenType type);
/* 过程语句列表 */
static TreeNode * StmtList();
static TreeNode * Stmt();
static TreeNode * AssignStmt();
static TreeNode * IfStmt();
static TreeNode * RepeatStmt();
static TreeNode * Exp();		//布尔表达式链
static TreeNode * Exp01();		//关系表达式链
static TreeNode * Exp02();		//算术表达式: 加减链
static TreeNode * Exp03();		//算术表达式: 乘除链
static TreeNode * Factor();		//表达式->终结符
/* 函数调用 */
static TreeNode * FuncCallExp(char * funcName);
static TreeNode * RParaList();
static TreeNode * RParaListSuf();

typedef struct Code{
	char * op;
	int rs;
	int rd;
	int rt;
}Code;

Code code;	//保持当前要填写的产生式
void printCode()
{
	fprintf(listing, "(%s, %d, %d, %d)\n",
			code.op, code.rs, code.rd, code.rt);
}

static void syntaxError(char * message)
{
	fprintf(listing,">>> ");
	fprintf(listing,"Syntax error at line %d: %s", lineno, message);
	SETERROR;
}

static int match(TokenType expected)
{
	if( token->type==expected )	{ token = getToken(); return TRUE; }

	/*
	* 	当遇到不符合规定的token时, 跳过该token, 知道读到下一个可以接受的token为止
	* */
	syntaxError("unexpected token -> ");
	printToken(*token);
	printf("now the token.type==%d, expected==%d\n", token->type, expected);
	return FALSE;
}

/* Program-> ProgramHead LB ProgramBody RB */
static TreeNode * Program()
{
    TreeNode * t = (TreeNode *) malloc(sizeof(TreeNode));
    t->kind = ProgramK;
    t->comp.program.programHead = ProgramHead();
    match(LB);
    t->comp.program.programBody = ProgramBody();
    match(RB);

    //函数定义完毕
    funcdefsize++;	//这里应该有边界检查的
    return t;
}

/* ProgramHead -> TYPE ID LP ParaList RP */
static TreeNode * ProgramHead()
{
	TreeNode * t = (TreeNode *) malloc(sizeof(TreeNode));
	FuncDefEntry * fp = &FuncDefTable[funcdefsize];

	t->kind = ProgramHeadK;
	t->comp.programHead.defType = token->type;
    match(token->type);
    t->comp.programHead.name = copyString(token->content);
    match(ID);
    match(LP);
    //可选的参数列表
	fp->head = fp->tail = (FParaList *)malloc(sizeof(FParaList));
    if( isType(token->type) ) t->comp.programHead.fparaList = ParaList();
    else t->comp.programHead.fparaList = NULL;
	match(RP);

	//defType, name,填充函数定义表
	fp->name = copyString(t->comp.programHead.name);
	fp->defType = t->comp.programHead.defType;
	fp->head = fp->head->next;	//跳过空的列表头

	return t;
}

/* 	ParaList -> TYPE ID ParaListSuf
 * 	至少一个参变量, 所以参数的可选性要到ProgramHead中进行
 *
 * 	注意定义添加顺序, 先出现的先添加定义
 * */
static TreeNode * ParaList()
{
	TreeNode * t = (TreeNode *)malloc(sizeof(TreeNode));
	VarDefEntry * vp = &VarDefTable[vardefsize];	//保持当前的var

	t->kind = ParaListK;
	t->comp.fparaList.type = token->type;
	match(token->type);
	t->comp.fparaList.name = copyString(token->content);
	match(ID);

	addVarDef(t->comp.fparaList.type, t->comp.fparaList.name, funcdefsize);
	addFPara(funcdefsize, vp);
	t->comp.fparaList.next = ParaListSuf();

	return t;
}

/*  ParaListSuf -> (CMA TYPE ID)*
 * 	可选性, 可以有也可以没有
 * */
static TreeNode * ParaListSuf()
{
	TreeNode * t = (TreeNode *)(malloc(sizeof(TreeNode)));
	TreeNode *head = t, *p;
	VarDefEntry * vp = &VarDefTable[vardefsize];

	//0或者多个
	while(token->type!=RP && token->type!=ENDFILE) {
		match(CMA);
		p = (TreeNode *)malloc(sizeof(TreeNode));
		p->kind = ParaListK;
		p->comp.fparaList.type = token->type;
		match(token->type);
		p->comp.fparaList.name = copyString(token->content);
		match(ID);
		t->comp.fparaList.next = p;
		t = p;

		addVarDef(t->comp.fparaList.type, t->comp.fparaList.name, funcdefsize);

		addFPara(funcdefsize, vp);
	}
	t->comp.fparaList.next = NULL;	//遇到 RP界符 : 空产生式
	head = head->comp.fparaList.next;//将空头跳过

	return head;
}

/* ProgramBody -> TypeDeclar StmtList */
static TreeNode * ProgramBody()
{
    TreeNode * t = (TreeNode *) malloc(sizeof(TreeNode));
    t->kind = ProgramBodyK;
    t->comp.programBody.declarList = DeclarList();
    t->comp.programBody.stmtList = StmtList();

    //match(RETURN);match(ID);match(SEMI);	//这里要改成return语句

    return t;
}

/* 	DeclarList -> DeclarStmt*
 * 	可选性； 0个或者多个语句列表, 0个时返回NULL
 * */
static TreeNode * DeclarList()
{
	TreeNode * t =(TreeNode *) malloc(sizeof(TreeNode));
	TreeNode *head = t, *p;
	while( isType(token->type) )
	{
		p = (TreeNode *) malloc(sizeof(TreeNode));
		p->kind = DeclarListK;
		p->comp.declarList.declarStmt = DeclarStmt();
		t->comp.declarList.next = p;
		t = p;
	}
	t->comp.declarList.next = NULL;

	head = head->comp.declarList.next;//将空头跳过
	return head;
}

/* DeclarStmt -> TYPE IDList SEMI */
static TreeNode * DeclarStmt()
{
	TreeNode * t = (TreeNode *)(malloc(sizeof(TreeNode)));
	t->kind = DeclarStmtK;

	t->comp.declarStmt.type = token->type;
	match(token->type);
	t->comp.declarStmt.idList = IDList(t->comp.declarStmt.type);
	match(SEMI);
	return t;
}

/* IDList-> ID IDListSuf */
static TreeNode * IDList(TokenType type)
{
	TreeNode * t = (TreeNode *)malloc(sizeof(TreeNode));
	t->kind = IDListK;

	t->comp.idList.id = copyString(token->content);
	match(ID);

	addVarDef(type, t->comp.idList.id, funcdefsize);//type, name
	t->comp.idList.next = IDListSuf(type);

	return t;
}

/* 	IDListSuf -> (CMA ID)*
 * 	可选性, 可以有也可以没有
 * */
static TreeNode * IDListSuf(TokenType type)
{
	TreeNode * t = (TreeNode *)(malloc(sizeof(TreeNode)));
	TreeNode *head = t, *p;
	//0或者多个
	while(token->type!=SEMI && token->type!=ENDFILE){
		match(CMA);
		p = (TreeNode *)malloc(sizeof(TreeNode));
		p->kind = IDListK;
		p->comp.idList.id = copyString(token->content);
		t->comp.idList.next = p;
		t = p;
		match(ID);
		addVarDef(type, t->comp.idList.id, funcdefsize);//type, name
	}
	t->comp.idList.next = NULL;	//遇到 SEMI空产生式

	//如果进行到这里没有报错, 那么至少进行了一次, 那么next指针必定不空
	head = head->comp.idList.next;//将空头跳过

	return head;
}

/* 	StmtList -> Stmt*
 * 	可选性； 0个或者多个语句列表, 0个时返回NULL
 * */
static TreeNode * StmtList()
{
	TreeNode * t =(TreeNode *) malloc(sizeof(TreeNode));
	TreeNode *head = t, *p;
	//AssignStmt|IFStmt|RepeatStmt
	while( token->type==ID || token->type==IF || token->type==REPEAT )
	{
		p = (TreeNode *) malloc(sizeof(TreeNode));
		p->kind = StmtListK;
		p->comp.stmtList.stmt = Stmt();
		t->comp.stmtList.next = p;
		t = p;
	}
	t->comp.stmtList.next = NULL;

	head = head->comp.stmtList.next;//将空头跳过
	return head;
}

/* Stmt -> AssignStmt | IfStmt | RepeatStmt */
static TreeNode * Stmt()
{
	switch( token->type ) {
		case ID:
			return AssignStmt(); break;
		case IF:
			return IfStmt(); break;
		case REPEAT:
			return RepeatStmt(); break;
		default:
			fprintf(listing, "no such type statement in proc Stmt() during parsing!\n");
			break;
	}

	return NULL;	//正常情况不会被执行
}

/* AssignStmt -> ID ASSIGN Exp SEMI */
static TreeNode * AssignStmt()
{
	TreeNode * t =(TreeNode *) malloc(sizeof(TreeNode));
	t->kind = AssignStmtK;

	t->comp.assignStmt.id = copyString(token->content);
	match(ID);	match(ASSIGN);

	t->comp.assignStmt.resExp = Exp();
	match(SEMI);

	return t;
}

/* IfStmt -> IF LP Exp Rp LB StmtList RB
 * 			 ELSE LB StmtList RB
 * 	else 分支可选
 * 			 */
static TreeNode * IfStmt()
{
	TreeNode * t =(TreeNode *) malloc(sizeof(TreeNode));
	t->kind = IfStmtK;

	match(IF); match(LP);
	t->comp.ifStmt.boolExp = Exp();	//检查布尔类型
	match(RP); match(LB);
	t->comp.ifStmt.ifList = StmtList();
	match(RB);

	//可选的else分支
	if( token->type == ELSE ) {
		match(token->type);		match(LB);
		t->comp.ifStmt.elseList = StmtList();
		match(RB);
	}else {
		t->comp.ifStmt.elseList = NULL;
	}

	return t;
}

/* RepeatStmt -> REPEAT LB StmtList RB UNTIL LP Exp RP SEMI*/
static TreeNode * RepeatStmt()
{
	TreeNode * t =(TreeNode *) malloc(sizeof(TreeNode));
	t->kind = RepeatStmtK;

	match(REPEAT); match(LB);
	t->comp.repeatStmt.repeatList = StmtList();
	match(RB);	match(UNTIL); match(LP);
	t->comp.repeatStmt.boolExp = Exp();		//检查布尔类型
	match(RP); match(SEMI);

	return t;
}

/* 逻辑表达式链
 * 	Exp ->	 BoolExp OP_BOOL BoolExp
 * 			|BoolExp
 * 	NOT 不知道怎么实现, 这种单目运算符
 *  */
static TreeNode * Exp()
{
	TreeNode * t = Exp01();
	while( isBoolOp(token->type) ) {
		TreeNode * p = (TreeNode *)malloc(sizeof(TreeNode));

		p->kind = E_BOOLK;
		p->comp.exp.op = token->type;
		match(token->type);
		p->comp.exp.lExp = t;
		p->comp.exp.rExp = Exp01();

		t = p;
	}
	return t;
}

/* 	关系表达式链
 * 	Exp01 -> (Exp01 OP_SHIP Exp01)*
 * */
static TreeNode * Exp01()
{
	TreeNode * t = Exp02();
	while( isShipOp(token->type) ) {
		TreeNode * p = (TreeNode *)malloc(sizeof(TreeNode));

		p->kind = E_SHIPK;
		p->comp.exp.op = token->type;
		match(token->type);
		p->comp.exp.lExp = t;
		p->comp.exp.rExp = Exp02();

		t = p;
	}
	return t;
}

/* 算术表达式: 加减链 */
static TreeNode * Exp02()
{
	TreeNode * t = Exp03();
	while( token->type==ADD || token->type==SUB ) {
		TreeNode * p = (TreeNode *)malloc(sizeof(TreeNode));
		p->kind = E_ADDSUBK;

		p->comp.exp.op = token->type;
		match(token->type);
		p->comp.exp.lExp = t;
		p->comp.exp.rExp = Exp03();

		t = p;
	}
	return t;
}

/* 算术表达式: 乘除链 */
static TreeNode * Exp03()
{
	TreeNode * t = Factor();
	while( token->type==MUL || token->type==DIV ) {
		TreeNode * p = (TreeNode *)malloc(sizeof(TreeNode));

		p->kind = E_MULDIVK;
		p->comp.exp.op = token->type;
		match(token->type);
		p->comp.exp.lExp = t;
		p->comp.exp.rExp = Factor();

		t = p;
	}
	return t;
}

/* 表达式 -> 终结符 , 可能出错也就是NULL返回值*/
static TreeNode * Factor()
{
	TreeNode * t =(TreeNode *) malloc(sizeof(TreeNode));
	TokenEntry * lastToken = token;
	token = getToken(); //不用match了

	//switch过程相当于做match
	switch( lastToken->type ) {
	case INUM:
		t->kind = F_INUMK;
		t->comp.factor.fcomp.val.iVal = lastToken->val.iVal;
		break;
	case CNUM:
		t->kind = F_CNUMK;
		t->comp.factor.fcomp.val.cVal = lastToken->val.cVal;
		break;
	case FNUM:
		t->kind = F_FNUMK;
		t->comp.factor.fcomp.val.fVal = lastToken->val.fVal;
		break;
	case ID:
		if( token->type==LP ) {
			t->kind = F_CALLK;	//函数调用
			t->comp.factor.fcomp.func = FuncCallExp(lastToken->content);
		}else {
			t->kind = F_VARK;
			t->comp.factor.fcomp.name = copyString(lastToken->content);
		}
		break;
	case LP:
		t = Exp();
		match(RP);
		break;
	default:
		fprintf(listing, "error token->%s at factor during parsing\n",token->content);
		t = NULL;
		break;
	}
	return t;
}

/* 	FunCallExp -> ID LP RParaList RP
 * 	由于funcName在factor中match, 所以需要记录然后传参
 *  */
static TreeNode * FuncCallExp(char * funcName)
{
	TreeNode * t =(TreeNode *) malloc(sizeof(TreeNode));
	t->kind = E_FUNCCALLK;
	t->comp.funcCallExp.funcName = copyString(funcName);
	//不用match(ID), 因为lastToken在Factor()中已经match了ID, LP
	match(token->type);
	//可选实参列表
	t->comp.funcCallExp.rparaList = RParaList();
	match(RP);

	return t;
}

/* 	RParaList -> (Exp)*
 * 	可选的参数列表
 * */
static TreeNode * RParaList()
{
	if( token->type==RP || token->type==ENDFILE )	return NULL;

	TreeNode * t =(TreeNode *) malloc(sizeof(TreeNode));
	t->kind = RParaListK;
	t->comp.rparaList.paraExp = Exp();
	t->comp.rparaList.next = RParaListSuf();

	return t;
}
static TreeNode * RParaListSuf()
{
	TreeNode * t =(TreeNode *) malloc(sizeof(TreeNode));
	TreeNode * head=t, *p;
	while(token->type!=RP &&token->type!=ENDFILE){
		match(CMA);
		p = (TreeNode *) malloc(sizeof(TreeNode));
		p->kind = RParaListK;
		p->comp.rparaList.paraExp = Exp();
		t->comp.rparaList.next = p;
		t = p;
	}
	t->comp.rparaList.next = NULL;
	head = head->comp.rparaList.next;

	return t;
}

TreeNode * parse(void)
{
	TreeNode * t;
	token = getToken();

	t = Program();
	//t = ProgramBody();
	if (token->type != ENDFILE)	syntaxError("Code ends before file\n");
	else if( Error ) 	syntaxError("some errors occur! parse failed\n");
	else	{ 	fprintf(listing, "parse successful!\n");
				printf("parse successful!\n");}

	return t;
}
