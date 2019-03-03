
void printToken( TokenEntry );
void printTree( TreeNode * );

char * copyString(char * s);
void copyToken(TokenEntry * st, TokenEntry * dt);	//st <- dt

int isType(TokenType);
int isConst(TokenType);
int isSingleOp(TokenType type);
int isBoolOp(TokenType type);
int isShipOp(TokenType type);

