#include "globals.h"
#include "scan.h"
#include "util.h"
#include <math.h>

/*
    当前token 字符表示
*/
#define BUFLEN 256
static char lineBuf[BUFLEN];
static int linepos = 0;
static int bufsize = 0;
static int EOF_flag = FALSE;
char tokenString[MAXTOKENLEN+1];

/*
    关键字表
*/
static struct {
    char * str;
    TokenType tokType;
} keyWordTable[MAXRESERVED] = {
    {"int", INT}, {"float", FLOAT}, {"char", CHAR}, {"void", VOID},
    {"if", IF}, {"then", THEN}, {"else", ELSE}, {"fi", FI},
    {"repeat", REPEAT}, {"until", UNTIL},
    {"begin", BEGIN}, {"end", END},
    {"read", READ}, {"write", WRITE}, {"return", RETURN},
};

/*
    状态机
*/
typedef enum StateType {
    S_START,
    S_NUM_1, S_NUM_2, S_NUM_3, S_NUM_4, S_NUM_5, S_NUM_6,
    S_ID_KEY, S_AND, S_OR,
    S_ASSIGN_EQ, S_GT_GE, S_LT_LE, S_NOT_NE,
    S_COMMENT, S_END, S_ERROR = -1
} StateType;

int numVal;  //若当前是数字, 那么 d = ch - '0';

enum { _DIGIT, _LETTER, _POINT, _POWER,  /*1.23e+2*/ _BLANK };

int currentChar;    //全局变量记录字符
int TraceNoShow;    //默认显示,

//getNextChar返回字符类型
static int getNextChar( void)
{
    int ch;
    if( !(linepos < bufsize) ) {
        lineno++;
        if( fgets(lineBuf, BUFLEN-1, source) ) {
            bufsize = strlen(lineBuf);
            linepos = 0;
        } else {
            EOF_flag = TRUE;
            return EOF;
        }
    }
    //现在lineBuf里面有内容
    ch = lineBuf[linepos++];
    currentChar = ch;
    if( isdigit(ch) ) { numVal = ch - '0'; return _DIGIT; }
    if( ch == 'e' || ch == 'E' ) return _POWER;
    if( isalpha(ch) )  return _LETTER;
    if( ch == ' ' || ch=='\t' || ch=='\n')  return _BLANK;

    return ch;
}

static void ungetNextChar(void)
{
    if( !EOF_flag ) linepos--;
}

/*
    传入一个 标识符类型
    查找一个关键字是否存在, 若存在那么返回关键字类型
    不存在则返回 ID标识符类型
*/
static TokenType keyWordLookUp(char * s)
{
    int i;
    for(i=0; i < MAXRESERVED; i++) {
        if( !strcmp(s, keyWordTable[i].str) ) {
            return keyWordTable[i].tokType;
        }
    }
    return ID;
}

static void handleError(void)
{
    fprintf(listing, "scan for error:\n");
}

TokenEntry * getToken( void)
{
    int w;  //整数累加器
    int p;  //指数累加器
    int n;  //小数位数累加器
    int e;  //指数符号
    int iVal;
    float fVal;

    int tokenIndex = 0;
    TokenType currentToken;
    StateType state = S_START;

    int save;   //该char是否保存到tokenString[]

    while ( state!=S_END && state!=S_ERROR ) {
    	TraceNoShow = FALSE; // 默认显示
        save = TRUE;
        int charType = getNextChar();
        switch(state) {
        case S_START:
            switch(charType) {
            case _DIGIT:
                w = numVal;
                n = 0;
                p = 0;
                e = 1;
                state = S_NUM_1;
                break;
            case _POINT:
                w = 0;
                n = 0;
                p = 0;
                e = 1;
                state = S_NUM_2;
                break;
            case _LETTER:
            case _POWER:
                state = S_ID_KEY;
                break;
            case '!':
            	state = S_NOT_NE;
            	break;
            case '&': state = S_AND; break;
            case '|': state = S_OR; break;
            case '=':
                state = S_ASSIGN_EQ;
                break;
            case '<':
                state = S_LT_LE;
                break;
            case '>':
                state = S_GT_GE;
                break;
            case '+':
                currentToken = ADD;
                state = S_END;
                break;
            case '-':
                currentToken = SUB;
                state = S_END;
                break;
            case '*':
                currentToken = MUL;
                state = S_END;
                break;
            case '/':
                currentToken = DIV;
                state = S_END;
                break;
            case '(':
                currentToken = LP;
                state = S_END;
                break;
            case ')':
                currentToken = RP;
                state = S_END;
                break;
            case _BLANK:
                save = FALSE;
                TraceNoShow = TRUE;
                break;
            case ';':
                currentToken = SEMI;
                state = S_END;
                break;
            case ',':
                currentToken = CMA;
                state = S_END;
                break;
            case '{':
			   currentToken = LB;
			   state = S_END;
			   break;
            case '}':
			   currentToken = RB;
			   state = S_END;
			   break;
            case '%':
				save = FALSE;
				state = S_COMMENT;
				break;
            case EOF:
                currentToken = ENDFILE;
                state = S_END;
                break;
            default:
                state = S_ERROR;
                break;
            }
            break;  //S_START
        case S_NUM_1:
            switch( charType ) {
            case _DIGIT:
                w = w * 10 + numVal;
                break;
            case _POINT:
                state = S_NUM_3;
                break;
            case _POWER:
                state = S_NUM_4;
                break;
            default:
                ungetNextChar();//回退字符
                save = FALSE;
                iVal = w;
                //没有小数部分的数字识别结束
                currentToken = INUM;
                state = S_END;
                break;
            }
            break;  //S_NUM_1
        case S_NUM_2:
            switch( charType ) {
            case _DIGIT:
                n++;
                w = w * 10 + numVal;
                state = S_NUM_3;
                break;
            default:
                state = S_ERROR;
                break;
            }
            break;  //S_NUM_2
        case S_NUM_3:
            switch( charType ) {
            case _DIGIT:
                n++;
                w = w * 10 + numVal;
                break;
            case _POWER:
                state = S_NUM_4;
                break;
            default:
                ungetNextChar();
                save = FALSE;
                fVal = w * pow(10, e*p-n);
                //没有科学计数法的浮点数识别结束
                currentToken = FNUM;
                state = S_END;
                break;
            }
            break; //S_NUM_3
        case S_NUM_4:
            switch(charType) {
            case '-':
                e = -1;
                break;
            case '+':
                state = S_NUM_5;
                break;
            case _DIGIT:
                p = p * 10 + numVal;    //指数部分累加
                state = S_NUM_6;
                break;
            }
            break;  // S_NUM_4
        case S_NUM_5:
            switch(charType) {
            case _DIGIT:
                p = p * 10 + numVal;
                state = S_NUM_6;
                break;
            default:
                state = S_ERROR;
                break;
            }
            break;  //S_NUM_5
        case S_NUM_6:
            switch(charType) {
            case _DIGIT:
                p = p * 10 + numVal;
                break;
            default:
                ungetNextChar();
                save = FALSE;
                fVal = w * pow(10, e*p - n);
                //带科学计数法的数字识别结束
                currentToken = FNUM;
                state = S_END;
                break;
            }
            break;  //S_NUM_6
        case S_ID_KEY:
            switch(charType) {
            case _LETTER:
            case _POWER:
                break;
            default:
                ungetNextChar();    //回退字符
                save = FALSE;
                currentToken = ID;
                state = S_END;
                break;
            }
            break; //S_ID_KEY
		case S_NOT_NE:
			 switch(charType) {
				case '=':
					currentToken = NE;
					state = S_END;
					break;
				default:
					ungetNextChar();
					save = FALSE;
					currentToken = NOT;
					state = S_END;
					break;
				}
			break; //S_NOT_NE
		 case S_AND:
			 switch(charType) {
				case '&':
					currentToken = AND;
					state = S_END;
					break;
				default:
					ungetNextChar();
					save = FALSE;
					state = S_ERROR;
					break;
				}
			break; //S_AND
		 case S_OR:
			 switch(charType) {
				case '|':
					currentToken = OR;
					state = S_END;
					break;
				default:
					ungetNextChar();
					save = FALSE;
					state = S_ERROR;
					break;
				}
			break; //S_OR
        case S_ASSIGN_EQ:
            switch(charType) {
            case '=':
                currentToken = EQ;
                state = S_END;
                break;
            default:
                ungetNextChar();
                save = FALSE;
                currentToken = ASSIGN;
                state = S_END;
                break;
            }
            break; //S_ASSIGN_EQ
        case S_GT_GE:
            switch(charType) {
            case '=':
                currentToken = GE;
                state = S_END;
                break;
            default:
                ungetNextChar();
                save = FALSE;
                currentToken = GT;
                state = S_END;
                break;
            }
            break; //S_GT_GE
        case S_LT_LE:
            switch(charType) {
            case '=':
                currentToken = LE;
                state = S_END;
                break;
            default:
                ungetNextChar();
                save = FALSE;
                currentToken = LT;
                state = S_END;
                break;
            }
            break; //S_LT_LE
        case S_COMMENT:
            save = FALSE;
            switch(charType) {
            case '%':
                state = S_START;
                break;  //注释结束, 重新开始分析
            case EOF:
                currentToken = ENDFILE;
                state = S_END;
                break;
            default:
                break;
            }
            break;
        case S_ERROR:
            handleError();
            break;  //S_ERROR
        case S_END:
            break;
        }//end of switch(state)

        //记录得到的tokenString <- currentChar, 多出的不记录
        if( save == TRUE && tokenIndex <= MAXTOKENLEN ) {
            tokenString[tokenIndex++] = (char)currentChar;
        }
        if( state==S_END ) {
			if( currentToken==ENDFILE )
				  {tokenString[0]='E';tokenString[1]='O';tokenString[2]='F';tokenIndex=3;}
			//按字符串形式添加'\0'到字符数组
			if(tokenIndex<=MAXTOKENLEN)	tokenString[tokenIndex++] = '\0';
			else tokenString[MAXTOKENLEN] = '\0';
			if( currentToken==ID )	currentToken = keyWordLookUp(tokenString);
	    }
	}//end of while( state != S_END )

	//得到一个token, 填充结构体内容 tokenType, content, valType, val
    TokenEntry * pt = addToken(currentToken, tokenString);
    if(currentToken == INUM)  pt->val.iVal = iVal;
    else  if(currentToken == FNUM )  pt->val.fVal = fVal;

    if ( TraceNoShow == FALSE && TraceScan ) {
        fprintf(listing,"\t%d: ",lineno);
        printToken(*pt);
    }

    return pt;
}
