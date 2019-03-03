/*
 * symtable.h
 *
 *  Created on: 2018��6��26��
 *      Author: xudongze
 */

#ifndef SYMTABLE_H_
#define SYMTABLE_H_

/*
 * token���ű�
 * */
#define SYMTABLELEN 9997
TokenEntry SymTable[SYMTABLELEN];

void addFPara(int procIndex, VarDefEntry * para);
FuncDefEntry * findFunc(char * name);	//�Ҳ�������NULL
int findVar(char * name, int proc);
void addVarDef(ValType type, char * name, int proc);

TokenEntry * addToken(TokenType type, char * content);
TokenEntry * lookup(char * content);

void printFuncDefTable();
void printVarDefTable();
void printSymTable(void);
#endif /* SYMTABLE_H_ */
