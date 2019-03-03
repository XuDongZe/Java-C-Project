/*
 * symtable.c
 *
 *  Created on: 2018年6月26日
 *      Author: xudongze
 */
#include "globals.h"
#include "symtable.h"

/*
 * 	函数定义表
 * */
void printFuncDefTable()
{
	int i;
	fprintf(listing, "\n函数定义表:\n");
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
 * 	添加形参, 维护尾指针, 尾插法
 * 	最后得到的形参列表有一个空头, 注意即可
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
 * 	变量定义表
 * */
void printVarDefTable()
{
	int i;
	fprintf(listing, "\n变量定义表:\n");
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
	/* 找到了已经存在的项 */
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

/* 调试使用符号表系统 */
/* 求一个字符串(词)的哈希值
 * 输入: tokenEntry 的 content
 * */
static unsigned symhash(const char * content)
{
	unsigned int hashno = 0;
	unsigned char ch;

	/* ++操作符的优先级比*高 */
	while( (ch = *content++) != '\0' )
		hashno += hashno*9 ^ ch;
	return hashno;
}

/* 根据content内容在哈希表中查找
 * 线性扫描的哈希函数 */
TokenEntry * lookup(char * content)
{
	TokenEntry *sp = &SymTable[symhash(content) % SYMTABLELEN];
	int scount = SYMTABLELEN;

	/* 使用scount控制扫描的次数,所以最多次数的扫描是从
	 * 哈希计算的下标开始一周,这样最多哈希函数退化成线性数组
	 */
	while(--scount) {
		/* 先考察按哈希函数计算的下标是否被占用了 即是否之前已经定义了 */
		if(sp->content && !strcmp(sp->content, content)){
			return sp;
		}
		/*
		 * 	没有找到时两种情况
		 * 		关键字sp->content空:		也就是该token类型第一次加入到符号表
		 * 			为该tokenEntry创建内存实体
		 *		关键字sp->content非空:	哈希冲突, 线性扫描解决冲突
		*/
		if(!sp->content){
			//填充 sp
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
	/* 头插法将ref插入到该sym对应的哈希表项值中
     * 这样TokenEntry的pos指针指向最后一个插入的token的位置
     * 也就是栈式的存储
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
		 * 注意这里，我们想要将所有的空项放到表的最后，
		 * 这样，我们就可以在遍历表时，使用如下规则判断表中数据已经遍历完毕，
		 * 而不用真正的遍历SIZE_SYMTAB个元素。
		 * 规则：以空元素为结束。
		 * 而一般的散列表是不可以这样判断的，因为元素是散列放置的。
		 */
		return 1;
	}
	if(!sp2->content)	return -1;
	return strcmp(sp1->content, sp2->content);
}

//这个print之后符号表的hash顺序会被打乱, 所以只能用来调试, 可以设计另一个更简单的print
void printSymTable(void)
{
	TokenEntry *sp;
	TokenEntry *end = SymTable + SYMTABLELEN;

	/* 对连续数组symtab使用大小sizeof()分组的组分，
	 * 应用方法symcmp()进行排序
	 */
	qsort(SymTable, SYMTABLELEN, sizeof(TokenEntry), tokencmp);
	fprintf(listing, "\n建立符号表:\n");
	for(sp=SymTable; sp->content&&sp<end; sp++){

		/* 左对齐 */
		fprintf(listing, "%10s:", sp->content);

		char *prefn = "";
		TokenPos * pp = sp->pos;
		/* 由于是栈式的存储
		 * 所以我们按照人们的习惯使用队列的方式将其显示
		 * 也就是最先出现的引用我们将会先输出
		 * 需要引用列表的逆序
		 */
		TokenPos * pppre = NULL;
		while(pp) {
			TokenPos * next = pp->next;
			pp->next = pppre;
			pppre = pp;
			pp = next;
		}
		/* 注意此时pp==NULL,而接下来还要使用pp遍历链表，所以要调整pp为原链表尾
		 * 也就是逆序后的链表头
		 */
		pp = sp->pos = pppre;
		for( ; pp; pp = pp->next ){
			/* 同一文件引用只打印一次，那么我们需要记录上一个引用的文件是哪个
 			 * 当本次的文件引用和上次的一样时，我们不显示文件名称即可。
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

