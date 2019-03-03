#include "globals.h"
#include "scan.h"
#include "parse.h"
#include "util.h"

/*公共数据
 * */
int lineno = 0;
FILE * source;
FILE * listing;
char * fileName;

int vardefsize = 0;
int funcdefsize = 0;
VarDefEntry VarDefTable[VARDEFTABLELEN+1];
FuncDefEntry FuncDefTable[FUNCDEFTABLELEN+1];

/* 调试控制 */
int EchoSource = TRUE;
int TraceScan = TRUE;
int TraceParse = FALSE;
int TraceAnalyze = FALSE;
int TraceCode = FALSE;
int Error = FALSE;

int main()
{
	fileName = "test.txt";
	source = fopen(fileName, "r");
	if( source==NULL ) {
		printf("open source file Failed!\n");
		system("pause");
		exit(1);
	} else {
		listing = fopen("scanRes.txt", "w");
		if( listing == NULL ) {
			printf("open output file Failed!\n");
			system("pause");
			exit(1);
		}
	}

	printf("size_Node=%dByteS\n", sizeof(TreeNode));
//    while( getToken()->type != ENDFILE )
//    	;
//    printf("scan over\n");

    TreeNode * tree = parse();

	printTree(tree);
    printSymTable();
    printVarDefTable();
    printFuncDefTable();

    system("pause");
    return 0;
}
