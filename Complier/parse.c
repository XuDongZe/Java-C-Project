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
/* ���������б� */
static TreeNode * ParaList();
static TreeNode * ParaListSuf();
/* ��������б� */
static TreeNode * DeclarList();
static TreeNode * DeclarStmt();
static TreeNode * IDList(TokenType type);
static TreeNode * IDListSuf(TokenType type);
/* ��������б� */
static TreeNode * StmtList();
static TreeNode * Stmt();
static TreeNode * AssignStmt();
static TreeNode * IfStmt();
static TreeNode * RepeatStmt();
static TreeNode * Exp();		//�������ʽ��
static TreeNode * Exp01();		//��ϵ���ʽ��
static TreeNode * Exp02();		//�������ʽ: �Ӽ���
static TreeNode * Exp03();		//�������ʽ: �˳���
static TreeNode * Factor();		//���ʽ->�ս��
/* �������� */
static TreeNode * FuncCallExp(char * funcName);
static TreeNode * RParaList();
static TreeNode * RParaListSuf();

typedef struct Code{
	char * op;
	int rs;
	int rd;
	int rt;
}Code;

Code code;	//���ֵ�ǰҪ��д�Ĳ���ʽ
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
	* 	�����������Ϲ涨��tokenʱ, ������token, ֪��������һ�����Խ��ܵ�tokenΪֹ
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

    //�����������
    funcdefsize++;	//����Ӧ���б߽����
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
    //��ѡ�Ĳ����б�
	fp->head = fp->tail = (FParaList *)malloc(sizeof(FParaList));
    if( isType(token->type) ) t->comp.programHead.fparaList = ParaList();
    else t->comp.programHead.fparaList = NULL;
	match(RP);

	//defType, name,��亯�������
	fp->name = copyString(t->comp.programHead.name);
	fp->defType = t->comp.programHead.defType;
	fp->head = fp->head->next;	//�����յ��б�ͷ

	return t;
}

/* 	ParaList -> TYPE ID ParaListSuf
 * 	����һ���α���, ���Բ����Ŀ�ѡ��Ҫ��ProgramHead�н���
 *
 * 	ע�ⶨ�����˳��, �ȳ��ֵ�����Ӷ���
 * */
static TreeNode * ParaList()
{
	TreeNode * t = (TreeNode *)malloc(sizeof(TreeNode));
	VarDefEntry * vp = &VarDefTable[vardefsize];	//���ֵ�ǰ��var

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
 * 	��ѡ��, ������Ҳ����û��
 * */
static TreeNode * ParaListSuf()
{
	TreeNode * t = (TreeNode *)(malloc(sizeof(TreeNode)));
	TreeNode *head = t, *p;
	VarDefEntry * vp = &VarDefTable[vardefsize];

	//0���߶��
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
	t->comp.fparaList.next = NULL;	//���� RP��� : �ղ���ʽ
	head = head->comp.fparaList.next;//����ͷ����

	return head;
}

/* ProgramBody -> TypeDeclar StmtList */
static TreeNode * ProgramBody()
{
    TreeNode * t = (TreeNode *) malloc(sizeof(TreeNode));
    t->kind = ProgramBodyK;
    t->comp.programBody.declarList = DeclarList();
    t->comp.programBody.stmtList = StmtList();

    //match(RETURN);match(ID);match(SEMI);	//����Ҫ�ĳ�return���

    return t;
}

/* 	DeclarList -> DeclarStmt*
 * 	��ѡ�ԣ� 0�����߶������б�, 0��ʱ����NULL
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

	head = head->comp.declarList.next;//����ͷ����
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
 * 	��ѡ��, ������Ҳ����û��
 * */
static TreeNode * IDListSuf(TokenType type)
{
	TreeNode * t = (TreeNode *)(malloc(sizeof(TreeNode)));
	TreeNode *head = t, *p;
	//0���߶��
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
	t->comp.idList.next = NULL;	//���� SEMI�ղ���ʽ

	//������е�����û�б���, ��ô���ٽ�����һ��, ��ônextָ��ض�����
	head = head->comp.idList.next;//����ͷ����

	return head;
}

/* 	StmtList -> Stmt*
 * 	��ѡ�ԣ� 0�����߶������б�, 0��ʱ����NULL
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

	head = head->comp.stmtList.next;//����ͷ����
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

	return NULL;	//����������ᱻִ��
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
 * 	else ��֧��ѡ
 * 			 */
static TreeNode * IfStmt()
{
	TreeNode * t =(TreeNode *) malloc(sizeof(TreeNode));
	t->kind = IfStmtK;

	match(IF); match(LP);
	t->comp.ifStmt.boolExp = Exp();	//��鲼������
	match(RP); match(LB);
	t->comp.ifStmt.ifList = StmtList();
	match(RB);

	//��ѡ��else��֧
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
	t->comp.repeatStmt.boolExp = Exp();		//��鲼������
	match(RP); match(SEMI);

	return t;
}

/* �߼����ʽ��
 * 	Exp ->	 BoolExp OP_BOOL BoolExp
 * 			|BoolExp
 * 	NOT ��֪����ôʵ��, ���ֵ�Ŀ�����
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

/* 	��ϵ���ʽ��
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

/* �������ʽ: �Ӽ��� */
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

/* �������ʽ: �˳��� */
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

/* ���ʽ -> �ս�� , ���ܳ���Ҳ����NULL����ֵ*/
static TreeNode * Factor()
{
	TreeNode * t =(TreeNode *) malloc(sizeof(TreeNode));
	TokenEntry * lastToken = token;
	token = getToken(); //����match��

	//switch�����൱����match
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
			t->kind = F_CALLK;	//��������
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
 * 	����funcName��factor��match, ������Ҫ��¼Ȼ�󴫲�
 *  */
static TreeNode * FuncCallExp(char * funcName)
{
	TreeNode * t =(TreeNode *) malloc(sizeof(TreeNode));
	t->kind = E_FUNCCALLK;
	t->comp.funcCallExp.funcName = copyString(funcName);
	//����match(ID), ��ΪlastToken��Factor()���Ѿ�match��ID, LP
	match(token->type);
	//��ѡʵ���б�
	t->comp.funcCallExp.rparaList = RParaList();
	match(RP);

	return t;
}

/* 	RParaList -> (Exp)*
 * 	��ѡ�Ĳ����б�
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
