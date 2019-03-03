/*
 * symtable.h
 *
 *  Created on: 2018年6月26日
 *      Author: xudongze
 */

#ifndef SYMTABLE_H_
#define SYMTABLE_H_

/*
 * token符号表
 * */
#define SYMTABLELEN 9997
TokenEntry SymTable[SYMTABLELEN];

void addFPara(int procIndex, VarDefEntry * para);
FuncDefEntry * findFunc(char * name);	//找不到返回NULL
int findVar(char * name, int proc);
void addVarDef(ValType type, char * name, int proc);

TokenEntry * addToken(TokenType type, char * content);
TokenEntry * lookup(char * content);

void printFuncDefTable();
void printVarDefTable();
void printSymTable(void);
#endif /* SYMTABLE_H_ */
