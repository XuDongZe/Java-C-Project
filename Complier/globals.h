#include <stdio.h>
#include <stdlib.h>

#define FALSE 0
#define TRUE 1
#define NOEXIST -1

#define NOTOKEN = -1; //该节点在token表中没有对应的项目, 也就是该节点不消耗token
#define MAXRESERVED 15

extern FILE* source;    //source code text file
extern char * fileName; //保持当前的打开文件名
extern FILE* listing;   //output text file
extern int lineno;      //source line number for listing
extern int funcdefsize;
extern int vardefsize;

//INT表示"int"的tokenType, 而不是常量的valType
//注意许多默认值是0, 要错开来
typedef enum {
	ERROR=-2, ENDFILE=-3,
/* 关键字 */
    INT=1, FLOAT, BOOL, CHAR, VOID, PTR,	/* 1~6 */
    IF, THEN, ELSE, FI, REPEAT, UNTIL, READ, WRITE, BEGIN, END, RETURN,	/* 7~17 */
/* 符号 */
    ASSIGN, ADD, SUB, MUL, DIV, EQ, NE, GE, LE, GT, LT, LP, RP,	/* 18~30 */
    AND, OR, NOT,	/* 31~33 */
    SEMI,/* ; */ COMMENT /* // */, CMA/* , */, LB/* { */, RB/* } */, /* 34~38 */

    ID, INUM, FNUM, CNUM,	/* 39~42 */
} TokenType;

typedef enum {
    ProgramK, ProgramHeadK, ProgramBodyK, ParaListK,
/* 声明列表部分 */
    DeclarListK, DeclarStmtK, IDListK,
/* 语句列表部分 */
    StmtListK, IfStmtK, RepeatStmtK, AssignStmtK,
    ExpK, E_BOOLK, E_SHIPK, E_ADDSUBK, E_MULDIVK, E_FUNCCALLK, E_SINGLE/* 单运算符 */,
    RParaListK /* 函数调用语句  */,
    FactorK, F_CALLK, F_EXPK, F_INUMK, F_CNUMK, F_FNUMK, F_VARK
}NodeKind;

//与TokenType值相同, 所以可以==判断是否相等, 也可以相互赋值
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
    TokenType type;     //词法分析定义的 token类别
    char * content;     //字符串形式给出的Token的说明, 作为符号表的主键, 相同的名字则合并
    Val val;
    //记录该单词的位置: 文件+行号
    TokenPos * pos;
} TokenEntry;

//函数定义表

/* 大小:
 *  */
typedef struct treeNode {
    //节点类型
    NodeKind kind;
    union {
        //程序主结构
        struct Program {
            struct treeNode * programBody;
            struct treeNode * programHead;
        }program;
        //程序首部
        struct ProgramHead {
        	ValType defType;	//函数定义类型
        	char * name;
        	struct treeNode * fparaList;
        }programHead;
        //参数列表
        struct FParaList {
        	ValType type;
        	char * name;
        	struct treeNode * next;
        }fparaList;
        //程序体
        struct ProgramBody {
            struct treeNode * declarList;
            struct treeNode * stmtList;
            ValType retType;	//返回值类型
            Val 	retVal;		//返回值
        }programBody;
/* 声明列表部分 */
        //变量声明列表
        struct DeclarList {
            struct treeNode * declarStmt;
            struct treeNode * next;
        }declarList;
        //变量声明语句
        struct DeclarStmt {
        	TokenType type;
            //int DeclarTBIndex;//该界定对应的DecLarTBIndex的下标
        	struct treeNode * idList;
        }declarStmt;
        //声明语句中的变量列表, 不支持声明时赋值
        struct IdList {
            char * id;
            struct treeNode * next;
        }idList;
/* 语句列表*/
        struct StmtList {
        	struct treeNode * stmt;
        	struct treeNode * next;
        }stmtList;
/* 语句 */
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
/* 表达式 */
        struct Exp { 	//减小递归深度,
        	TokenType op;					//布尔表达式, 也可以是一个普通表达式
        	struct treeNode * lExp;
        	struct treeNode * rExp;
        	struct treeNode * next;
        }exp;
        struct Factor {
        	union{
        		char * name;	//变量 函数调用
        		Val val;
        		struct treeNode * exp;	//括号表达式
        		struct treeNode * func; //函数调用表达式
        	}fcomp;
        }factor;
/* 函数调用语句 */
        struct FuncCallExp {	//函数调用表达式
        	char * funcName;
        	struct treeNode * rparaList;	//实参列表
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
 * 变量定义表, name不为空即为已定义
 * */
#define VARDEFTABLELEN 1000
typedef struct VarDefEntry {
	char * name;
	ValType type;
	int procIndex;	//指向该变量的附属过程, 也就是作用域系统, FuncDefTable中对应下标
}VarDefEntry;

//函数形参列表 formal paramList
typedef struct FormalParaList {
	VarDefEntry * para;
	struct FormalParaList * next;
}FParaList;

//过程(函数)定义表
#define FUNCDEFTABLELEN 10
typedef struct FuncDefEntry {
	char * name;
	ValType retType;
	ValType defType;
	//int paraNums;	//形参数量
	FParaList * head;	//最多支持10个形参, int指向VarDefTable中对应下标
	FParaList * tail;		//最后一个形参位置, 添加形参
}FuncDefEntry;

extern VarDefEntry VarDefTable[VARDEFTABLELEN+1];
extern int vardefsize;
extern FuncDefEntry FuncDefTable[FUNCDEFTABLELEN+1];
extern int funcdefsize;


