#include "globals.h"
#include "util.h"

static int space = 0;
#define TABLEN 2
#define INCSPACE (space+=TABLEN)
#define DECSPACE (space-=TABLEN)
static void printSpace(void )
{
	int i;
	for(i=0; i<space; i++)
		fprintf(listing, " ");
}

/* 聚合类型  */
int isType(TokenType type)
{
	return ( type==INT || type==FLOAT || type==CHAR || type==VOID );
}
int isConst(TokenType type)
{
	return ( type==INUM || type==FNUM || type==CNUM );
}
int isSingleOp(TokenType type)
{
	return ( type==NOT );
}
int isBoolOp(TokenType type)
{
	return ( /*type==NOT ||*/ type==AND || type==OR );
}
int isShipOp(TokenType type)
{
	return ( type==LT || type==GT || type==EQ
		   ||type==LE || type==GE || type==NE );
}

char * copyString(char * s)
{
    int n;
    char * t;
    if (s==NULL) return NULL;
    n = strlen(s) + 1;
    t = malloc(n);
    if (t==NULL)
        fprintf(listing,"Out of memory error at line %d\n",lineno);
    else strcpy(t, s);
    return t;
}

void printToken( TokenEntry tokenEntry )
{
    TokenType token = tokenEntry.type;

    switch (token) {
    case IF:case THEN:case ELSE:case FI:case REPEAT:case UNTIL:case READ:
    case WRITE:case BEGIN:case END:case INT:case FLOAT:case BOOL:case CHAR:
    case VOID:case PTR:case RETURN:
    	fprintf(listing, "reserved word: %s\n",tokenEntry.content);
        break;
    case AND: 	fprintf(listing, "&&\n"); break;
    case OR:	fprintf(listing, "||\n"); break;
    case NOT:	fprintf(listing, "!\n"); break;
    case ASSIGN:fprintf(listing,"=\n");	break;
    case LT: 	fprintf(listing,"<\n"); break;
    case GT: 	fprintf(listing, ">\n"); break;
    case EQ:
        fprintf(listing,"==\n");
        break;
    case NE:
		fprintf(listing,"!=\n");
		break;
    case LE:
        fprintf(listing,"<=\n");
        break;
    case GE:
        fprintf(listing,">=\n");
        break;
    case LP:
        fprintf(listing,"(\n");
        break;
    case RP:
        fprintf(listing,")\n");
        break;
    case SEMI:
        fprintf(listing,";\n");
        break;
    case CMA:
        fprintf(listing,",\n");
        break;
    case LB:
		fprintf(listing,"{\n");
		break;
    case RB:
		fprintf(listing,"}\n");
		break;
    case ADD:
        fprintf(listing,"+\n");
        break;
    case SUB:
        fprintf(listing,"-\n");
        break;
    case MUL:
        fprintf(listing,"*\n");
        break;
    case DIV:
        fprintf(listing,"/\n");
        break;
    case ENDFILE:
        fprintf(listing,"EOF\n");
        break;
    case INUM:
        fprintf(listing,"INUM, val= %d\n", tokenEntry.val.iVal);
        break;
    case FNUM:
         fprintf(listing,"FNUM, val= %f\n", tokenEntry.val.fVal);
         break;
    case CNUM:
    	 fprintf(listing,"CNUM, val= %c\n", tokenEntry.val.cVal);
    	 break;
    case ID:
        fprintf(listing,
                "ID, name= %s\n",tokenEntry.content);
        break;
    case ERROR:
        fprintf(listing,
                "ERROR: %s\n",tokenEntry.content);
        break;
    default: /* should never happen */
        fprintf(listing,"Unknown token: %d\n",token);
        break;
    }
}

TokenEntry t;//%-2d, 左对齐
void printTree(TreeNode * root)
{
	if( !root )	return;

	printSpace();
	INCSPACE;
	NodeKind kind = root->kind;
	switch(kind)
	{
	case ProgramK:
		fprintf(listing, "Program:\n");
		printTree(root->comp.program.programHead);
		printTree(root->comp.program.programBody);
		break;
	case ProgramHeadK:
		fprintf(listing, "ProgramHead: %-2d %s\n", root->comp.programHead.defType,
				root->comp.programHead.name);
		printTree(root->comp.programHead.fparaList);
		break;
	case ParaListK:
		fprintf(listing, "FParaList:\t");
		while(root){
			fprintf(listing, "%d:%s -> ", root->comp.fparaList.type,
					root->comp.fparaList.name);
			root = root->comp.fparaList.next;
		}
		fprintf(listing, "\n");
		break;
	case ProgramBodyK:
		fprintf(listing, "ProgramBody:\n");
		printTree(root->comp.programBody.declarList);
		printTree(root->comp.programBody.stmtList);
		break;
	case DeclarListK:
		fprintf(listing, "DeclarList:\n");
		while( root ) {
			printTree(root->comp.declarList.declarStmt);
			root = root->comp.declarList.next;
		}
		break;
	case DeclarStmtK:
		fprintf(listing, "%-2d:\t", root->comp.declarStmt.type);
		printTree(root->comp.declarStmt.idList);
		break;
	case IDListK:
		while(root) {
			fprintf(listing, "%s -> ", root->comp.idList.id);
			root = root->comp.idList.next;
		}
		fprintf(listing, "\n");
		break;
	case StmtListK:
		fprintf(listing, "StmtList:\n");
		while( root ) {
			printTree(root->comp.stmtList.stmt);
			root = root->comp.stmtList.next;
		}
		break;
	case AssignStmtK:
		fprintf(listing, "Assign to %s\n", root->comp.assignStmt.id);
		printTree(root->comp.assignStmt.resExp);
		break;
	case IfStmtK:
		fprintf(listing, "IfBoolExp:\n");	printTree(root->comp.ifStmt.boolExp);
		printSpace(); fprintf(listing, "If:"); printTree(root->comp.ifStmt.ifList);
		printSpace(); fprintf(listing, "Else:"); printTree(root->comp.ifStmt.elseList);
		break;
	case RepeatStmtK:
		fprintf(listing, "Repeat:"); printTree(root->comp.repeatStmt.repeatList);
		printSpace(); fprintf(listing, "Until:\t"); printTree(root->comp.repeatStmt.boolExp);
		break;
	case E_BOOLK: case E_SHIPK:case E_ADDSUBK:case E_MULDIVK:
		t.type = root->comp.exp.op;
		printToken(t);	//op类型, tokenType不需要其他操作
		printTree(root->comp.exp.lExp);
		printTree(root->comp.exp.rExp);
		break;
	case F_INUMK: fprintf(listing, "%-2d\n", root->comp.factor.fcomp.val.iVal); break;
	case F_FNUMK: fprintf(listing, "%f\n", root->comp.factor.fcomp.val.fVal); break;
	case F_CNUMK: fprintf(listing, "%c\n", root->comp.factor.fcomp.val.cVal); break;
	case F_VARK:  fprintf(listing, "%s\n", root->comp.factor.fcomp.name); break;
	case F_CALLK: printTree(root->comp.factor.fcomp.func); 	break;
	case E_FUNCCALLK:
		fprintf(listing, "function: %s\n", root->comp.funcCallExp.funcName);
		printTree(root->comp.funcCallExp.rparaList);
		break;
	case RParaListK:
		while( root ) {
			printTree(root->comp.rparaList.paraExp);
			root = root->comp.rparaList.next;
		}
		break;
	default:
		break;
	}
	DECSPACE;
}

void copyToken(TokenEntry * st, TokenEntry * dt)
{
	st->content = copyString(dt->content);
	st->type = dt->type;
	if( isConst(dt->type) )	{
		switch(dt->type){
			case INUM: st->val.iVal = dt->val.iVal; break;
			case FNUM: st->val.fVal = dt->val.fVal; break;
			case CNUM: st->val.cVal = dt->val.cVal; break;
			default:  break;
		}
	}
	if( dt->pos ) {
		st->pos = (TokenPos *) malloc(sizeof(TokenPos));
		st->pos->fileName = copyString(dt->pos->fileName);
		st->pos->line = dt->pos->line;
		st->pos->next = NULL;	//一定是NULL
	}
}
