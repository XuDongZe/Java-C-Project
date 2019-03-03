/*
 * symtable.c
 *
 *  Created on: 2018��6��26��
 *      Author: xudongze
 */
#include "globals.h"
#include "symtable.h"

/*
 * 	���������
 * */
void printFuncDefTable()
{
	int i;
	fprintf(listing, "\n���������:\n");
	fprintf(listing, "type\t\t name\t\t fparas\t\t\n");
	for(i=0; i<funcdefsize; i++) {
		FuncDefEntry * fp = &FuncDefTable[i];
		fprintf(listing, "%d\t\t %s\t\t", fp->defType, fp->name);
		FParaList * p = fp->head;
		while( p ) {
			fprintf(listing, "%d,%d:%s -> ", p->para->procIndex, p->para->type,
					p->para->name);
			p = p->next;
		}
		fprintf(listing, "\n");
	}
}
/*
 * 	����β�, ά��βָ��, β�巨
 * 	���õ����β��б���һ����ͷ, ע�⼴��
 * */
void addFPara(int procIndex, VarDefEntry * para)
{
	FParaList * p = (FParaList *)malloc(sizeof(FParaList));
	FuncDefEntry * fp = &FuncDefTable[procIndex];

	p->para = para;
	p->next = NULL;
	fp->tail->next = p;
	fp->tail = p;
}

FuncDefEntry * findFunc(char * name)
{
	int i;
	for(i=0; i<funcdefsize; i++) {
		char * funcName = FuncDefTable[i].name;
		if( funcName && !strcmp(funcName, name) )	return &FuncDefTable[i];
	}
	return NULL;
}


/*
 * 	���������
 * */
void printVarDefTable()
{
	int i;
	fprintf(listing, "\n���������:\n");
	fprintf(listing, "name\t\t type\t\t proc\t\t\n");
	for(i=0; i<vardefsize; i++) {
		VarDefEntry * vp = &VarDefTable[i];
		fprintf(listing, "%s\t\t %d\t\t %d\n", vp->name, vp->type, vp->procIndex);
	}
}

int findVar(char * name, int proc)
{
	int i;
	for(i=0; i<vardefsize; i++) {
		char * varName = VarDefTable[i].name;
		int faproc = VarDefTable[i].procIndex;
		if( faproc==proc && varName && !strcmp(varName, name) )	return TRUE;
	}
	return FALSE;
}
void addVarDef(ValType type, char * name, int proc)
{
	/* �ҵ����Ѿ����ڵ��� */
	if( findVar(name, proc) ) {
		fprintf(listing, "var %s already defined\n", name);
		return;
	}
	if( !(vardefsize <= VARDEFTABLELEN) ){
		fprintf(listing, "VarDefTable overflow\n");
		exit(1);
	}
	VarDefEntry * sp = &VarDefTable[vardefsize++];
	sp->name = copyString(name);
	sp->procIndex = proc;
	sp->type = type;
}

/* ����ʹ�÷��ű�ϵͳ */
/* ��һ���ַ���(��)�Ĺ�ϣֵ
 * ����: tokenEntry �� content
 * */
static unsigned symhash(const char * content)
{
	unsigned int hashno = 0;
	unsigned char ch;

	/* ++�����������ȼ���*�� */
	while( (ch = *content++) != '\0' )
		hashno += hashno*9 ^ ch;
	return hashno;
}

/* ����content�����ڹ�ϣ���в���
 * ����ɨ��Ĺ�ϣ���� */
TokenEntry * lookup(char * content)
{
	TokenEntry *sp = &SymTable[symhash(content) % SYMTABLELEN];
	int scount = SYMTABLELEN;

	/* ʹ��scount����ɨ��Ĵ���,������������ɨ���Ǵ�
	 * ��ϣ������±꿪ʼһ��,��������ϣ�����˻�����������
	 */
	while(--scount) {
		/* �ȿ��찴��ϣ����������±��Ƿ�ռ���� ���Ƿ�֮ǰ�Ѿ������� */
		if(sp->content && !strcmp(sp->content, content)){
			return sp;
		}
		/*
		 * 	û���ҵ�ʱ�������
		 * 		�ؼ���sp->content��:		Ҳ���Ǹ�token���͵�һ�μ��뵽���ű�
		 * 			Ϊ��tokenEntry�����ڴ�ʵ��
		 *		�ؼ���sp->content�ǿ�:	��ϣ��ͻ, ����ɨ������ͻ
		*/
		if(!sp->content){
			//��� sp
			sp->content = copyString(content);
			sp->pos = NULL;
			return sp;
		}
		if(++sp >= SymTable+SYMTABLELEN)
			sp = SymTable;
	}
	fprintf(listing, "symtab overflow\n");
	abort();
}

TokenEntry * addToken(TokenType type, char * content)
{
	TokenEntry *sp = lookup(content);
	sp->type = type;

	if( sp->pos &&
		sp->pos->line == lineno &&
		!strcmp(sp->pos->fileName, fileName) )
		return sp;

	TokenPos * ppos = (TokenPos *) malloc(sizeof(TokenPos));
	if(!ppos)
		perror("malloc ref in addref\n");
	ppos->fileName = copyString(fileName);
	ppos->line = lineno;
	/* ͷ�巨��ref���뵽��sym��Ӧ�Ĺ�ϣ����ֵ��
     * ����TokenEntry��posָ��ָ�����һ�������token��λ��
     * Ҳ����ջʽ�Ĵ洢
	*/
	ppos->next = sp->pos;
	sp->pos = ppos;

	return sp;
}

static int tokencmp(const void *ap, const void *bp)
{
	const struct TokenEntry *sp1 = ap;
	const struct TokenEntry *sp2 = bp;
	if(!sp1->content) {
		if(!sp2->content)	return 0;
		/*
		 * ע�����������Ҫ�����еĿ���ŵ�������
		 * ���������ǾͿ����ڱ�����ʱ��ʹ�����¹����жϱ��������Ѿ�������ϣ�
		 * �����������ı���SIZE_SYMTAB��Ԫ�ء�
		 * �����Կ�Ԫ��Ϊ������
		 * ��һ���ɢ�б��ǲ����������жϵģ���ΪԪ����ɢ�з��õġ�
		 */
		return 1;
	}
	if(!sp2->content)	return -1;
	return strcmp(sp1->content, sp2->content);
}

//���print֮����ű��hash˳��ᱻ����, ����ֻ����������, ���������һ�����򵥵�print
void printSymTable(void)
{
	TokenEntry *sp;
	TokenEntry *end = SymTable + SYMTABLELEN;

	/* ����������symtabʹ�ô�Сsizeof()�������֣�
	 * Ӧ�÷���symcmp()��������
	 */
	qsort(SymTable, SYMTABLELEN, sizeof(TokenEntry), tokencmp);
	fprintf(listing, "\n�������ű�:\n");
	for(sp=SymTable; sp->content&&sp<end; sp++){

		/* ����� */
		fprintf(listing, "%10s:", sp->content);

		char *prefn = "";
		TokenPos * pp = sp->pos;
		/* ������ջʽ�Ĵ洢
		 * �������ǰ������ǵ�ϰ��ʹ�ö��еķ�ʽ������ʾ
		 * Ҳ�������ȳ��ֵ��������ǽ��������
		 * ��Ҫ�����б������
		 */
		TokenPos * pppre = NULL;
		while(pp) {
			TokenPos * next = pp->next;
			pp->next = pppre;
			pppre = pp;
			pp = next;
		}
		/* ע���ʱpp==NULL,����������Ҫʹ��pp������������Ҫ����ppΪԭ����β
		 * Ҳ��������������ͷ
		 */
		pp = sp->pos = pppre;
		for( ; pp; pp = pp->next ){
			/* ͬһ�ļ�����ֻ��ӡһ�Σ���ô������Ҫ��¼��һ�����õ��ļ����ĸ�
 			 * �����ε��ļ����ú��ϴε�һ��ʱ�����ǲ���ʾ�ļ����Ƽ��ɡ�
			 */
			if(!strcmp(pp->fileName, prefn)){
				fprintf(listing, " %d", pp->line);
			}else {
				fprintf(listing, " %s:%d", pp->fileName, pp->line );
				prefn = pp->fileName;
			}
		}
		fprintf(listing, "\n");
	}
}

