#include <stdio.h>
#include <stdlib.h>

#define FALSE 0
#define TRUE 1
#define NOEXIST -1

#define NOTOKEN = -1; //�ýڵ���token����û�ж�Ӧ����Ŀ, Ҳ���Ǹýڵ㲻����token
#define MAXRESERVED 15

extern FILE* source;    //source code text file
extern char * fileName; //���ֵ�ǰ�Ĵ��ļ���
extern FILE* listing;   //output text file
extern int lineno;      //source line number for listing
extern int funcdefsize;
extern int vardefsize;

//INT��ʾ"int"��tokenType, �����ǳ�����valType
//ע�����Ĭ��ֵ��0, Ҫ����
typedef enum {
	ERROR=-2, ENDFILE=-3,
/* �ؼ��� */
    INT=1, FLOAT, BOOL, CHAR, VOID, PTR,	/* 1~6 */
    IF, THEN, ELSE, FI, REPEAT, UNTIL, READ, WRITE, BEGIN, END, RETURN,	/* 7~17 */
/* ���� */
    ASSIGN, ADD, SUB, MUL, DIV, EQ, NE, GE, LE, GT, LT, LP, RP,	/* 18~30 */
    AND, OR, NOT,	/* 31~33 */
    SEMI,/* ; */ COMMENT /* // */, CMA/* , */, LB/* { */, RB/* } */, /* 34~38 */

    ID, INUM, FNUM, CNUM,	/* 39~42 */
} TokenType;

typedef enum {
    ProgramK, ProgramHeadK, ProgramBodyK, ParaListK,
/* �����б��� */
    DeclarListK, DeclarStmtK, IDListK,
/* ����б��� */
    StmtListK, IfStmtK, RepeatStmtK, AssignStmtK,
    ExpK, E_BOOLK, E_SHIPK, E_ADDSUBK, E_MULDIVK, E_FUNCCALLK, E_SINGLE/* ������� */,
    RParaListK /* �����������  */,
    FactorK, F_CALLK, F_EXPK, F_INUMK, F_CNUMK, F_FNUMK, F_VARK
}NodeKind;

//��TokenTypeֵ��ͬ, ���Կ���==�ж��Ƿ����, Ҳ�����໥��ֵ
typedef enum { T_VOID=VOID, T_INT=INT, T_FLOAT=FLOAT, T_BOOL=BOOL, T_CHAR=CHAR, T_PTR=PTR} ValType;

//Integer Float Char Pointer
typedef union{
    int iVal; 	float fVal;
    char cVal;	void * pVal;
}Val;

typedef struct TokenPos {
	char * fileName;
	int line;
	struct TokenPos * next;
}TokenPos;

typedef struct TokenEntry {
    TokenType type;     //�ʷ���������� token���
    char * content;     //�ַ�����ʽ������Token��˵��, ��Ϊ���ű������, ��ͬ��������ϲ�
    Val val;
    //��¼�õ��ʵ�λ��: �ļ�+�к�
    TokenPos * pos;
} TokenEntry;

//���������

/* ��С:
 *  */
typedef struct treeNode {
    //�ڵ�����
    NodeKind kind;
    union {
        //�������ṹ
        struct Program {
            struct treeNode * programBody;
            struct treeNode * programHead;
        }program;
        //�����ײ�
        struct ProgramHead {
        	ValType defType;	//������������
        	char * name;
        	struct treeNode * fparaList;
        }programHead;
        //�����б�
        struct FParaList {
        	ValType type;
        	char * name;
        	struct treeNode * next;
        }fparaList;
        //������
        struct ProgramBody {
            struct treeNode * declarList;
            struct treeNode * stmtList;
            ValType retType;	//����ֵ����
            Val 	retVal;		//����ֵ
        }programBody;
/* �����б��� */
        //���������б�
        struct DeclarList {
            struct treeNode * declarStmt;
            struct treeNode * next;
        }declarList;
        //�����������
        struct DeclarStmt {
        	TokenType type;
            //int DeclarTBIndex;//�ý綨��Ӧ��DecLarTBIndex���±�
        	struct treeNode * idList;
        }declarStmt;
        //��������еı����б�, ��֧������ʱ��ֵ
        struct IdList {
            char * id;
            struct treeNode * next;
        }idList;
/* ����б�*/
        struct StmtList {
        	struct treeNode * stmt;
        	struct treeNode * next;
        }stmtList;
/* ��� */
        struct AssignStmt {
        	char * id;
        	struct treeNode * resExp;
        }assignStmt;
        struct IFStmt {
        	struct treeNode * boolExp;
        	struct treeNode * ifList;
        	struct treeNode * elseList;
        }ifStmt;
        struct RepeatStmt {
        	struct treeNode * repeatList;
        	struct treeNode * boolExp;
        }repeatStmt;
/* ���ʽ */
        struct Exp { 	//��С�ݹ����,
        	TokenType op;					//�������ʽ, Ҳ������һ����ͨ���ʽ
        	struct treeNode * lExp;
        	struct treeNode * rExp;
        	struct treeNode * next;
        }exp;
        struct Factor {
        	union{
        		char * name;	//���� ��������
        		Val val;
        		struct treeNode * exp;	//���ű��ʽ
        		struct treeNode * func; //�������ñ��ʽ
        	}fcomp;
        }factor;
/* ����������� */
        struct FuncCallExp {	//�������ñ��ʽ
        	char * funcName;
        	struct treeNode * rparaList;	//ʵ���б�
        }funcCallExp;
        struct RparaList {
        	struct treeNode * paraExp;	//exp
        	struct treeNode * next;
        }rparaList;
    }comp ;
} TreeNode;

extern int EchoSource;
extern int TraceScan;
extern int TraceParse;
extern int TraceAnalyze;
extern int TraceCode;
extern int Error;
/*
 * ���������, name��Ϊ�ռ�Ϊ�Ѷ���
 * */
#define VARDEFTABLELEN 1000
typedef struct VarDefEntry {
	char * name;
	ValType type;
	int procIndex;	//ָ��ñ����ĸ�������, Ҳ����������ϵͳ, FuncDefTable�ж�Ӧ�±�
}VarDefEntry;

//�����β��б� formal paramList
typedef struct FormalParaList {
	VarDefEntry * para;
	struct FormalParaList * next;
}FParaList;

//����(����)�����
#define FUNCDEFTABLELEN 10
typedef struct FuncDefEntry {
	char * name;
	ValType retType;
	ValType defType;
	//int paraNums;	//�β�����
	FParaList * head;	//���֧��10���β�, intָ��VarDefTable�ж�Ӧ�±�
	FParaList * tail;		//���һ���β�λ��, ����β�
}FuncDefEntry;

extern VarDefEntry VarDefTable[VARDEFTABLELEN+1];
extern int vardefsize;
extern FuncDefEntry FuncDefTable[FUNCDEFTABLELEN+1];
extern int funcdefsize;


