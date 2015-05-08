#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <string.h>
//#include <math>

struct Symbol
{
	int val;
	char symbol;
};

struct Node
{
	int value;
	struct Node * next;
};

struct Operation
{
	char * string;
	struct Operation * next;
};

struct Symbol* _AND(int a,int b,struct Symbol *c)
{
	if (a == 1 && b == 1)
	{
		c->val = 1;
		return c;
	}
	else
	{
		c->val = 0;
		return c;
	}
}

struct Symbol* _OR(int a,int b,struct Symbol *c)
{
	if (a == 1 || b == 1)
	{
		c->val = 1;
		return c;
	}
	c->val = 0;
	return c;
}

struct Symbol * _NOT(int a)
{
	if (a == 0)
	{
		return 1;
	}
	return 0;
}

struct Symbol * buildSymbolValues(struct Symbol * inputlist, FILE *inputfile, int currentCycle,int length)
{
	char line[1000];
	int currentLine = 1;
	int currentCol = 0;
	while (fgets(line, sizeof(line),inputfile))
	{
		char * token = strtok(line,"\n");
		//printf("%s\n", token);
	
		while (token != NULL)
		{
			char * tok2 = strtok(token," ");
			while (tok2 != NULL)
			{
				if (currentCol == length)
				{
					return inputlist;
				}
				char * in = tok2;
				inputlist[currentCol].val = in[0] - '0'; 
				currentCol++;
				tok2 = strtok(NULL, " ");
			}
			return inputlist;
		}
	}
	return inputlist;
}

struct Operation * buildSymbolValues2(struct Symbol * inputlist, int numberOfInputs, int length, int * numberOfCycles, FILE *inputfile)
{
	char lne[100];
	int currentline = 1;
	int currentcol = 0;
	int numberofCycless = 0;
	struct Operation * head = (struct Operation *)malloc(sizeof(struct Operation));

	head->string = "";
	head->next = NULL;

	while (fgets(lne,sizeof(lne),inputfile))
	{
		char * token = strtok(lne, "\n");
		//printf("%s\n", token);
		if (numberofCycless == 0)
		{
			head->string = strdup(token);
			head->next = NULL;
			numberofCycless++;

			numberOfCycles = token[3] - '0';
		}
		else
		{
			pushNode(head,strdup(token));
			numberofCycless++;
		}
	}

	return head;
}

struct Operation * buildOutputText(struct Operation * head,struct Symbol * outputList, int numberOfOutputs)
{

	int i = 0;

	head->string = "C:";
	head->next = NULL;
	while (i < numberOfOutputs)
	{
		char strng [255];
		strng[0] = outputList[i].symbol;
		strng[1] = ':';
		pushNode(head,strdup(strng));
		//printf("%s\n", strng);
		
		i++;
	}
	return head;
}

struct Operation * addToOutputText(struct Operation * head, struct Symbol * outputList, int numberOfOutputs, int currentCLK)
{
	struct Operation * temp = head;
	char * asd = (char *)malloc(3);
	char * holder = (char *)malloc(sizeof(char *) * 100);
	int i = 0;
	holder = strdup(temp->string);	
	int o = strlen(holder);
	holder[o] = ' ';
	if (currentCLK == 0)
	{
		holder[o + 1] = '0';
	}
	else
	{
		holder[o + 1] = '1';
	}
	holder[o + 2] = '\0'; 
	temp->string = strdup(holder);
	//printf("%s\n", temp->string);
	temp = temp->next;
	free(holder);
	while (temp != NULL)
	{
		char * holder2 = (char *)malloc(sizeof(char *) * 100);
		if (temp->string != NULL)
		{
			holder2 = strdup(temp->string);	
		}
		else
		{
			//printf("%s\n", "someting wong");
		}
		int u = strlen(holder2);
		holder2[u] = ' ';
		if (outputList[i].val == 0)
		{
			holder2[u + 1] = '0';
		}
		else
		{
			holder2[u + 1] = '1';
		}
		holder2[u + 2] = '\0'; 
		temp->string = strdup(holder2);
		//printf("%s\n", temp->string);
		temp = temp->next;
		i++;
		free(holder2);
		
	}
	return head;
}

void printALL(struct Operation * head)
{
	struct Operation * temp = head;
	while (temp != NULL)
	{
		printf("%s\n", temp->string);
		temp = temp->next;
	}
}

struct Symbol * parseSequetionalList(struct Operation * head,int currentcyc,struct Symbol * inputList,int numberOfInputs, int * currentCLK)
{
	struct Operation * temp = head;
	int i = 0;
	while (temp != NULL)
	{
		inputList[i].val = temp->string[2*currentcyc + 5] - '0';

		i++;
		temp = temp->next;
	}

	currentCLK = inputList[i].val;

	return inputList;
}


int findSymbol(struct Symbol * list,char c,int size)
{
	int i = 0;
	if (c == '0')
	{
		return -3;
	}
	if (c == '1')
	{
		return -2;
	}
	while (i < size)
	{
		if (list[i].symbol == c)
		{
			return i;
		}
		i++;
	}
	return -1;
}

int numberOfInputLines(FILE *inputfile)
{
	char line[1000];
	int numToks = 0;
	while (fgets(line, sizeof(line),inputfile))
	{
		char * token = strtok(line,"\n");
		numToks = token[3] - '0';

	}
	fclose(inputfile);
	return numToks;
}

void push(struct Node * head,int n)
{
	struct Node * tempor = head;

	while (tempor->next != NULL)
	{
		tempor = tempor->next;
	}

	tempor->next = (struct Node *)malloc(sizeof(struct Node));
	tempor->next->value = n;
	tempor->next->next = NULL;
}

void printOutputValues(struct Symbol * head,int numberOfOutputs)
{
	int i = 0;
	if (i == 1)
	{
		printf("%d\n", head[i].val);
	}
	else
	{
		while (i < numberOfOutputs)
		{
			if (i > 0)
			{
				printf(" ");
				printf("%d", head[i].val);
			}
			else
			{
				printf("%d", head[i].val);
			}
			
			i++;
		}
		printf("\n");
	}
}

void pushNode(struct Operation * head, char * string)
{
	struct Operation * tem = head;

	while (tem->next != NULL)
	{
		tem = tem->next;
	}


	tem->next = (struct Operation *)malloc(sizeof(struct Operation));
	tem->next->string = (char *)malloc(255);
	tem->next->string = string;
	tem->next->next = NULL;

}

struct Node * getNode(struct Node * head,int index)
{
	int i = 0;
	struct Node * tmp = head;
	while (tmp != NULL)
	{
		tmp = tmp->next;
		i++;
		if (i == index)
		{
			return tmp;
		}
	}
	return head;
}

void buildAllOutputs(struct Symbol * outputList,FILE *circuitfile,int lineCnt)
{
	char line[100];
	char hLine[100];
	int curline = 1;
	while (fgets(line, sizeof(line),circuitfile))
	{
		memset(hLine,0,100);
		int len1 = strlen(line);
		int tmp = 0;
		while (tmp < len1)
		{
			hLine[tmp] = line[tmp];
			tmp++;
		}
		if (curline == lineCnt)
		{
			//printf("%c\n", hLine[len1-1]);
		}
		else
		{
			//printf("%c\n", hLine[len1-2]);	
		}
		curline++;
		char * token = strtok(line," \n");
		//printf("%s\n", token);

	}
	fclose(circuitfile);
}

int parseAND(struct Symbol * inputList,
			  struct Symbol * outputList,
			  struct Symbol interList[],
			  int * interListSize2,
			  char * strs[],
			  int strsSize,
			  char * token, 
			  int numberOfInputs, 
			  int numberOfOutputs)
{
	//printf("%s\n", token);
	//
	int interListSize = *interListSize2;
	//
					
	char andStrng[15];
	int andIDX = 0;
	andStrng[andIDX] = 'A';
	andIDX++;
	andStrng[andIDX] = 'N';
	andIDX++;
	andStrng[andIDX] = 'D';
	andIDX++;
	
	
	char * i = strtok(NULL," ");
	char c = i[0];


	andStrng[andIDX] = ' ';
	andIDX++;
	andStrng[andIDX] = c;
	andIDX++;
	
	int andInput1 = findSymbol(inputList,c,numberOfInputs);
	if (andInput1 == -3)
	{
		//printf("%s\n", "andInput = 0");
		andInput1 = 0;
	}
	else if (andInput1 == -2)
	{
		//printf("%s\n", "andInput1 = 1");
		andInput1 = 1;
	}
	else if (andInput1 == -1)
	{
		andInput1 = findSymbol(interList,c,interListSize);
		if (andInput1 == -1)
		{
			andInput1 = findSymbol(outputList,c,numberOfOutputs);
							andInput1 = outputList[andInput1].val;
		}
		else
		{
			
			andInput1 = interList[andInput1].val;
		}
	}
	else
	{
		andInput1 = inputList[andInput1].val;
	}
	char * i2 = strtok(NULL," ");
	char c2 = i2[0];
	andStrng[andIDX] = ' ';
	andIDX++;
	andStrng[andIDX] = c2;
	andIDX++;
	int andInput2 = findSymbol(inputList,c2,numberOfInputs);
	int In2Selector = 1;
	if (andInput2 == -3)
	{
		//printf("%s\n", "andOutput2 = 0");
		andInput2 = 0;
	}
	else if (andInput2 == -2)
	{
		//printf("%s\n", "andOutput2 = 1");
		andInput2 = 1;
	}
	else if (andInput2 == -1)
	{
		andInput2 = findSymbol(interList,c2,26);
		In2Selector = 3;
		if (andInput2 == -1)
		{
			andInput2 = findSymbol(outputList,c,numberOfOutputs);
							andInput2 = outputList[andInput2].val;
		}
		else
		{
			andInput2 = interList[andInput2].val;
		}
	}
	else
	{
		andInput2 = inputList[andInput2].val;
	}

	char * i3 = strtok(NULL," ");
	char c3 = i3[0];
	andStrng[andIDX] = ' ';
	andIDX++;
	andStrng[andIDX] = c3;
	andIDX++;
	andStrng[andIDX] = '\0';
	
	int whichList = 1;
	int andOutput = findSymbol(outputList,c3,numberOfOutputs);
	if (andOutput == -1)
	{
		andOutput = findSymbol(interList,c3,26);
		whichList = -1;
		if (andOutput == -1)
		{
			//create
			
			interList[interListSize].symbol = c3;
			andOutput = interListSize;
			interListSize++;
		}
	}
	if (whichList > 0)
	{
		outputList[andOutput] = *_AND(andInput1,andInput2,&outputList[andOutput]);
		//printf("%c\n", outputList[andOutput].symbol);
		//printf(" = %d\n", outputList[andOutput].val);
	}
	else
	{
		interList[andOutput] = *_AND(andInput1,andInput2,&interList[andOutput]);
		//printf("%c\n", interList[andOutput].symbol);
		//printf("%d\n", interList[andOutput].val);
	}

	strs[strsSize] = strdup(andStrng);
	strsSize++;

	*interListSize2 = interListSize;

	return strsSize;
}

int parseOR(struct Symbol * inputList,
			 struct Symbol * outputList,
			 struct Symbol interList[],
			 int * interListSize2,
			 char * strs[],
			 int strsSize,
			 char * token, 
			 int numberOfInputs, 
			 int numberOfOutputs)
{
	//printf("%s\n", token);

	int interListSize = *interListSize2;
	
	char orStrng[15];
	int orStrngIndex = 0;
	orStrng[orStrngIndex] = 'O';
	orStrngIndex++;
	orStrng[orStrngIndex] = 'R';
	orStrngIndex++;
	
	char * i = strtok(NULL," ");
	char c = i[0];
	int orInput1 = findSymbol(inputList,c,numberOfInputs);
	if (orInput1 == -3)
	{
		orInput1 = 0;
	}
	else if (orInput1 == -2)
	{
		orInput1 = 1;
	}
	else if (orInput1 == -1)
	{
		orInput1 = findSymbol(interList,c,26);
		if (orInput1 == -1)
		{
			orInput1 = findSymbol(outputList,c,numberOfOutputs);
			orInput1 = outputList[orInput1].val;
		}
		else
		{
			orInput1 = interList[orInput1].val;
		}
	}
	else
	{
		orInput1 = inputList[orInput1].val;
	}

	orStrng[orStrngIndex] = ' ';
	orStrngIndex++;
	orStrng[orStrngIndex] = c;
	orStrngIndex++;
	
	char * i2 = strtok(NULL," ");
	char c2 = i2[0];
	int orInput2 = findSymbol(inputList,c2,numberOfInputs);
	if (orInput2 == -3)
	{
		
		orInput2 = 0;
	}
	else if (orInput2 == -2)
	{
		
		orInput2 = 1;
	}
	else if (orInput2 == -1)
	{
		orInput2 = findSymbol(interList,c2,26);
		if (orInput2 == -1)
		{
			orInput2 = findSymbol(outputList,c2,numberOfOutputs);
			orInput2 = outputList[orInput2].val;
		}
		else
		{
			orInput2 = interList[orInput2].val;

			orStrng[orStrngIndex] = ' ';
			orStrngIndex++;
			orStrng[orStrngIndex] = c2;
			orStrngIndex++;
		}
	}
	else
	{
		orInput2 = inputList[orInput2].val;

		orStrng[orStrngIndex] = ' ';
		orStrngIndex++;
		orStrng[orStrngIndex] = c2;
		orStrngIndex++;
	}
	char * i3 = strtok(NULL," ");
	char c3 = i3[0];
					
	int orOutput = findSymbol(outputList,c3,numberOfOutputs);
	int whichList = 1;
	if (orOutput == -1)
	{
		orOutput = findSymbol(interList,c3,interListSize);
		whichList = -1;
		if (orOutput == -1)
		{
			interList[interListSize].symbol = c3;
			orOutput = interListSize;
			interListSize++;
		}
	}

	
	if (whichList > 0)
	{
		outputList[orOutput] = *_OR(orInput1,orInput2,&outputList[orOutput]);
		//printf("%d\n", outputList[orOutput].val);
	}
	else
	{
		interList[orOutput] = *_OR(orInput1,orInput2,&interList[orOutput]);
		//printf("%d\n", interList[orOutput].val);
	}
	orStrng[orStrngIndex] = ' ';
	orStrngIndex++;
	orStrng[orStrngIndex] = c3;
	orStrngIndex++;
	orStrng[orStrngIndex] = '\0';

	strs[strsSize] = strdup(orStrng);
	strsSize++;

	*interListSize2 = interListSize;

	return strsSize;
}

int parseNOT(struct Symbol * inputList,
			  struct Symbol * outputList,
			  struct Symbol interList[],
			  int * interListSize2,
			  char * strs[],
			  int strsSize,
			  char * token, 
			  int numberOfInputs, 
			  int numberOfOutputs)
{
	//
	//printf("%s\n", token);	

	int interListSize = *interListSize2;

	char notStrng[15];
	int notStrngIndex = 0;

	notStrng[notStrngIndex] = 'N';
	notStrngIndex++;
	notStrng[notStrngIndex] = 'O';
	notStrngIndex++;
	notStrng[notStrngIndex] = 'T';
	notStrngIndex++;
	

	char * i = strtok(NULL," ");
	char c = i[0];

	int notInput = findSymbol(inputList,c,numberOfInputs);
	if (notInput == -1)
	{
		notInput = findSymbol(interList,c,interListSize);
		if (notInput == -1)
		{
			notInput = findSymbol(outputList,c,numberOfOutputs);
			notInput = outputList[notInput].val;
		}
		else
		{
			notInput = interList[notInput].val;
		}
	}
	else if (notInput == -2)
	{
		notInput = 1;
	}
	else if (notInput == -3)
	{
		notInput = 0;
	}
	else
	{
		notInput = inputList[notInput].val;
	}

	notStrng[notStrngIndex] = ' ';
	notStrngIndex++;
	notStrng[notStrngIndex] = c;
	notStrngIndex++;


	char * i2 = strtok(NULL, " ");
	char c2 = i2[0];

	int notOutput = findSymbol(outputList,c2,numberOfOutputs);
	int whichList = 1;
	if (notOutput == -1)
	{
		notOutput = findSymbol(interList,c2,interListSize);
		whichList = -1;
		if (notOutput == -1)
		{
			interList[interListSize].symbol = c2;
			notOutput = interListSize;
			interListSize++;
		}
	}

	notStrng[notStrngIndex] = ' ';
	notStrngIndex++;
	notStrng[notStrngIndex] = c2;			
	notStrngIndex++;
	notStrng[notStrngIndex] = '\0';
	

	if (whichList > 0)
	{
		outputList[notOutput].val = _NOT(notInput);
		//printf("%d\n", outputList[notOutput].val);
	}
	else
	{
		interList[notOutput].val = _NOT(notInput);
		//printf("%d\n", interList[notOutput].val);
	}

	strs[strsSize] = strdup(notStrng);
	strsSize++;

	*interListSize2 = interListSize;

	return strsSize;
}

int parseDECODER(struct Symbol * inputList,
			 	  struct Symbol * outputList,
			  	  struct Symbol interList[],
			 	  int * interListSize2,
			 	  char * strs[],
			  	  int strsSize,
			      char * token, 
			  	  int numberOfInputs, 
			  	  int numberOfOutputs)
{

	int interListSize = *interListSize2;
	char decodeStrng[70];
	int decodeStrngIndex = 0;

	decodeStrng[decodeStrngIndex] = 'D';
	decodeStrngIndex++;
	decodeStrng[decodeStrngIndex] = 'E';
	decodeStrngIndex++;
	decodeStrng[decodeStrngIndex] = 'C';
	decodeStrngIndex++;
	decodeStrng[decodeStrngIndex] = 'O';
	decodeStrngIndex++;
	decodeStrng[decodeStrngIndex] = 'D';
	decodeStrngIndex++;
	decodeStrng[decodeStrngIndex] = 'E';
	decodeStrngIndex++;
	decodeStrng[decodeStrngIndex] = 'R';
	decodeStrngIndex++;
	decodeStrng[decodeStrngIndex] = ' ';
	decodeStrngIndex++;

	//printf("%s\n", token);
	char * i = strtok(NULL," \n");
	int j = atoi(i);
	int ii = 0;
	int j2 = pow(2,j);
	int init = 0;

	int run = 0;
	while (run < strlen(i))
	{
		decodeStrng[decodeStrngIndex] = i[run];
		decodeStrngIndex++;
		run++;
	}

	//
	struct Node * InHead = (struct Node *)malloc(sizeof(struct Node));
	struct Node * OutHead = (struct Node *)malloc(sizeof(struct Node));
	InHead->value = 0;
	InHead->next = NULL;
	struct Node * temp = InHead;
	while (ii < j)
	{
		char * ayylmao = strtok(NULL," ");
		char c = ayylmao[0];

		decodeStrng[decodeStrngIndex] = ' ';
		decodeStrngIndex++;
		decodeStrng[decodeStrngIndex] = c;
		decodeStrngIndex++;
		
		int byy = findSymbol(inputList,c,numberOfInputs);
		if (byy == -1)
		{
			byy = findSymbol(interList,c,interListSize);
			if (byy == -1)
			{
				//printf("%s\n", "not found");
			}
			else
			{
				if (init == 0)
				{
					InHead->value = interList[byy].val;
					InHead->next = NULL;
					init = 1;
				}
				else
				{
					push(InHead,interList[byy].val);
				}
			}
		}
		else if (byy == -2)
		{
			byy = 1;
			if (init == 0)
			{
				InHead->value = 1;
				InHead->next = NULL;
				init = 1;
			}
			else
			{
				push(InHead,1);
			}
		}
		else if (byy == -3)
		{
			byy = 0;
			if (init == 0)
			{
				InHead->value = 0;
				InHead->next = NULL;
				init = 1;
			}
			else
			{
				push(InHead,0);
			}
		}
		else
		{
			if (init == 0)
			{
				//printf("%c\n", c);
				InHead->value = inputList[byy].val;
				InHead->next = NULL;
				init = 1;
			}
			else
			{
				push(InHead,inputList[byy].val);
			}
		}
		ii++;
	}
	ii = 0;
	

	
	struct Node * t = InHead;
	while (t != NULL)
	{
		//printf("Node %d\n", t->value);
		t = t->next;
	}
	

	while (ii < j2)
	{
		//build outputs and assign values
		char * fug = strtok(NULL," ");
		char c = fug[0];

		decodeStrng[decodeStrngIndex] = ' ';
		decodeStrngIndex++;
		decodeStrng[decodeStrngIndex] = c;
		decodeStrngIndex++;

		int whichList = 1;
		int madeNew = 0;
		int a = findSymbol(outputList,c,numberOfOutputs);
		int res = 0;

		if (a == -1)
		{
			a = findSymbol(interList,c,interListSize);
			whichList = -1;
			if ( a == -1)
			{
				//make new
			
				interList[interListSize].symbol = c;
				interList[interListSize].val = 0;
				interListSize++;
				madeNew = 1;
			}
			else
			{
				res = interList[a].val;
			}
		}
		else
		{
			res = outputList[a].val;
		}
		
		
		if (j == 1)
		{
			//decode for 1
			if (ii == 0)
			{
				// 0
				if (InHead->value == 0)
				{
					res = 1;
				}
				else
				{
					res = 0;
				}
			}
			else
			{
				if (InHead->value == 1)
				{
					res = 1;
				}
				else
				{
					res = 0;
				}
			}
		}
		else if (j == 2)
		{
			if (ii == 0)
			{
				//0 0
				if (InHead->value == 0 && InHead->next->value == 0)
				{
					res = 1;
				}
				else
				{
					res = 0;
				}
			}
			else if (ii == 1)
			{
				//0 1
				if (InHead->value == 0 && InHead->next->value == 1)
				{
					res = 1;
				}
				else
				{
					res = 0;
				}
			}
			else if (ii == 2)
			{
				//1 1
				if (InHead->value == 1 && InHead->next->value == 1)
				{
					res = 1;
				}
				else
				{
					res = 0;
				}
			}
			else 
			{
				// 1 0
				if (InHead->value == 1 && InHead->next->value == 0)
				{
					res = 1;
				}
				else
				{
					res = 0;
				}
			}
		}
		else if (j == 3)
		{
			//decode for 3
		}
		else if (j == 4)
		{
			//decode for 4
		}

		//assignment
		if (whichList > 0)
		{
			outputList[a].val = res;
			/*
			printf("%s", "assigned value ");
			printf("%d", res);
			printf("%s", " to symbol: ");
			printf("%c\n", outputList[a].symbol);
			*/
		}
		else
		{
			if (madeNew == 1)
			{
				interList[interListSize-1].val = res;
				/*
				printf("%s", "assigned value ");
				printf("%d", res);
				printf("%s", " to symbol: ");
				printf("%c\n", interList[interListSize-1].symbol);
				*/
			}
			else
			{
				interList[a].val = res;
				/*
				printf("%s", "assigned value ");
				printf("%d", res);
				printf("%s", " to symbol: ");
				printf("%c\n", interList[a].symbol);
				*/
			}
		}
		//
		ii++;
	} ///////////////////////

	strs[strsSize] = strdup(decodeStrng);
	strsSize++;

	*interListSize2 = interListSize;

	return strsSize;
}

int parseMULTIPLEXER(struct Symbol * inputList,
			 	  	  struct Symbol * outputList,
			  	 	  struct Symbol interList[],
			 	 	  int * interListSize2,
			 	 	  char * strs[],
			  		  int strsSize,
			    	  char * token, 
			  		  int numberOfInputs, 
			  		  int numberOfOutputs)
{

	int interListSize = *interListSize2;
	char multiStrng[70];
	int multiStrngIndex = 0;

	multiStrng[multiStrngIndex] = 'M';
	multiStrngIndex++;
	multiStrng[multiStrngIndex] = 'U';
	multiStrngIndex++;
	multiStrng[multiStrngIndex] = 'L';
	multiStrngIndex++;
	multiStrng[multiStrngIndex] = 'T';
	multiStrngIndex++;
	multiStrng[multiStrngIndex] = 'I';
	multiStrngIndex++;
	multiStrng[multiStrngIndex] = 'P';
	multiStrngIndex++;
	multiStrng[multiStrngIndex] = 'L';
	multiStrngIndex++;
	multiStrng[multiStrngIndex] = 'E';
	multiStrngIndex++;
	multiStrng[multiStrngIndex] = 'X';
	multiStrngIndex++;
	multiStrng[multiStrngIndex] = 'E';
	multiStrngIndex++;
	multiStrng[multiStrngIndex] = 'R';
	multiStrngIndex++;
	multiStrng[multiStrngIndex] = ' ';
	multiStrngIndex++;

	//printf("%s\n", token);
	char * i = strtok(NULL," \n");
	int j = atoi(i);
	int ii = 0;
	int j2 = log2(j);
	int init = 0;

	int run = 0;
	while (run < strlen(i))
	{
		multiStrng[multiStrngIndex] = i[run];
		multiStrngIndex++;
		run++;
	}

	struct Node * InHead = (struct Node *)malloc(sizeof(struct Node));
	struct Node * SelectHead = (struct Node *)malloc(sizeof(struct Node));

	InHead->value = 0;
	InHead->next = NULL;
	SelectHead->value = 0;
	SelectHead->next = NULL;

	while (ii < j)
	{
		char * ayylmao = strtok(NULL," ");
		char c = ayylmao[0];
		
		multiStrng[multiStrngIndex] = ' ';
		multiStrngIndex++;
		multiStrng[multiStrngIndex] = c;
		multiStrngIndex++;

		int byy = findSymbol(inputList,c,numberOfInputs);
		if (byy == -1)
		{
			byy = findSymbol(interList,c,interListSize);
			if (byy == -1)
			{
				//printf("%s\n", "not found");
			}
			else
			{
				if (init == 0)
				{
					InHead->value = interList[byy].val;
					InHead->next = NULL;
					init = 1;
				}
				else
				{
					push(InHead,interList[byy].val);
				}
			}
		}
		else if (byy == -2)
		{
			byy = 1;
			if (init == 0)
			{
				InHead->value = 1;
				InHead->next = NULL;
				init = 1;
			}
			else
			{
				push(InHead,1);
			}
		}
		else if (byy == -3)
		{
			byy = 0;
			if (init == 0)
			{
				InHead->value = 0;
				InHead->next = NULL;
				init = 1;
			}
			else
			{
				push(InHead,0);
			}
		}
		else
		{
			if (init == 0)
			{
				//printf("%c\n", c);
				InHead->value = inputList[byy].val;
				InHead->next = NULL;
				init = 1;
			}
			else
			{
				push(InHead,inputList[byy].val);
			}
		}
		ii++;
	}
	ii = 0;


	struct Node * t = InHead;
	while (t != NULL)
	{
		//printf("Node %d\n", t->value);
		t = t->next;
	}

	//reselt initializer 
	init = 0;
	//

	//build second list
	while (ii < j2)
	{
		//build outputs and assign values
		char * ayylmao = strtok(NULL," ");
		char c = ayylmao[0];

		multiStrng[multiStrngIndex] = ' ';
		multiStrngIndex++;
		multiStrng[multiStrngIndex] = c;
		multiStrngIndex++;
		
		int byy = findSymbol(inputList,c,numberOfInputs);
		if (byy == -1)
		{
			byy = findSymbol(interList,c,interListSize);
			if (byy == -1)
			{
				//printf("%s\n", "not found");
			}
			else
			{
				if (init == 0)
				{
					SelectHead->value = interList[byy].val;
					SelectHead->next = NULL;
					init = 1;
				}
				else
				{
					push(SelectHead,interList[byy].val);
				}
			}
		}
		else if (byy == -2)
		{
			byy = 1;
			if (init == 0)
			{
				SelectHead->value = 1;
				SelectHead->next = NULL;
				init = 1;
			}
			else
			{
				push(SelectHead,1);
			}
		}
		else if (byy == -3)
		{
			byy = 0;
			if (init == 0)
			{
				SelectHead->value = 0;
				SelectHead->next = NULL;
				init = 1;
			}
			else
			{
				push(SelectHead,0);
			}
		}
		else
		{
			if (init == 0)
			{
				//printf("%c\n", c);
				SelectHead->value = inputList[byy].val;
				SelectHead->next = NULL;
				init = 1;
			}
			else
			{
				push(SelectHead,inputList[byy].val);
			}
		}
		ii++;
	}
	//
	//printf("%s\n", "selector nodes");
	t = SelectHead;
	while (t != NULL)
	{
		//printf("Node %d\n", t->value);
		t = t->next;
	}

	int res = 0;

	//set output, based on number of nodes in list 2, reverse of decoder
	if (j2 == 1)
	{
		if (SelectHead->value == 0)
		{
			res = InHead->value;
		}
		else
		{
			res = InHead->next->value;
		}
	}
	else if (j2 == 2)
	{
		if (SelectHead->value == 0 && SelectHead->next->value == 0)
		{
			res = InHead->value;
		}
		else if (SelectHead->value == 0 && SelectHead->next->value == 1)
		{
			res = InHead->next->value;
		}
		else if (SelectHead->value == 1 && SelectHead->next->value == 1)
		{
			res = InHead->next->next->value;
		}
		else
		{
			res = getNode(InHead,3)->value;
		}
	}
	else if (j2 == 3)
	{
		if (SelectHead->value == 0 && SelectHead->next->value == 0 && SelectHead->next->next->value == 0)
		{
			res = getNode(InHead,0)->value;
		}
		else if (SelectHead->value == 0 && SelectHead->next->value == 0 && SelectHead->next->next->value == 1)
		{
			res = getNode(InHead,1)->value;
		}
		else if (SelectHead->value == 0 && SelectHead->next->value == 1 && SelectHead->next->next->value == 1)
		{
			res = getNode(InHead,2)->value;
		}
		else if (SelectHead->value == 0 && SelectHead->next->value == 1 && SelectHead->next->next->value == 0)
		{
			res = getNode(InHead,3)->value;
		}
		else if (SelectHead->value == 1 && SelectHead->next->value == 1 && SelectHead->next->next->value == 0)
		{
			res = getNode(InHead,4)->value;
		}
		else if (SelectHead->value == 1 && SelectHead->next->value == 1 && SelectHead->next->next->value == 1)
		{
			res = getNode(InHead,5)->value;
		}
		else if (SelectHead->value == 1 && SelectHead->next->value == 0 && SelectHead->next->next->value == 1)
		{
			res = getNode(InHead,6)->value;
		}
		else
		{
			res = getNode(InHead,7)->value;
		}
	}
	else if (j2 == 4)
	{
		if (SelectHead->value == 0 && getNode(SelectHead,1)->value == 0 && getNode(SelectHead,2)->value == 0 && getNode(SelectHead,3)->value == 0)
		{
			res = getNode(InHead,0)->value;
		}
		else if (SelectHead->value == 0 && getNode(SelectHead,1)->value == 0 && getNode(SelectHead,2)->value == 0 && getNode(SelectHead,3)->value == 1)
		{
			res = getNode(InHead,1)->value;
		}
		else if (SelectHead->value == 0 && getNode(SelectHead,1)->value == 0 && getNode(SelectHead,2)->value == 1 && getNode(SelectHead,3)->value == 1)
		{
			res = getNode(InHead,2)->value;
		}
		else if (SelectHead->value == 0 && getNode(SelectHead,1)->value == 0 && getNode(SelectHead,2)->value == 1 && getNode(SelectHead,3)->value == 0)
		{
			res = getNode(InHead,3)->value;
		}
		else if (SelectHead->value == 0 && getNode(SelectHead,1)->value == 1 && getNode(SelectHead,2)->value == 1 && getNode(SelectHead,3)->value == 0)
		{
			res = getNode(InHead,4)->value;
		}
		else if (SelectHead->value == 0 && getNode(SelectHead,1)->value == 1 && getNode(SelectHead,2)->value == 1 && getNode(SelectHead,3)->value == 1)
		{
			res = getNode(InHead,5)->value;
		}
		else if (SelectHead->value == 0 && getNode(SelectHead,1)->value == 1 && getNode(SelectHead,2)->value == 0 && getNode(SelectHead,3)->value == 1)
		{
			res = getNode(InHead,6)->value;
		}
		else if (SelectHead->value == 0 && getNode(SelectHead,1)->value == 1 && getNode(SelectHead,2)->value == 0 && getNode(SelectHead,3)->value == 0)
		{
			res = getNode(InHead,7)->value;
		}
		else if (SelectHead->value == 1 && getNode(SelectHead,1)->value == 1 && getNode(SelectHead,2)->value == 0 && getNode(SelectHead,3)->value == 0)
		{
			res = getNode(InHead,8)->value;
		}
		else if (SelectHead->value == 1 && getNode(SelectHead,1)->value == 1 && getNode(SelectHead,2)->value == 0 && getNode(SelectHead,3)->value == 1)
		{
			res = getNode(InHead,9)->value;
		}
		else if (SelectHead->value == 1 && getNode(SelectHead,1)->value == 1 && getNode(SelectHead,2)->value == 1 && getNode(SelectHead,3)->value == 1)
		{
			res = getNode(InHead,10)->value;
		}
		else if (SelectHead->value == 1 && getNode(SelectHead,1)->value == 1 && getNode(SelectHead,2)->value == 1 && getNode(SelectHead,3)->value == 0)
		{
			res = getNode(InHead,11)->value;
		}
		else if (SelectHead->value == 1 && getNode(SelectHead,1)->value == 0 && getNode(SelectHead,2)->value == 1 && getNode(SelectHead,3)->value == 0)
		{
			res = getNode(InHead,12)->value;
		}
		else if (SelectHead->value == 1 && getNode(SelectHead,1)->value == 0 && getNode(SelectHead,2)->value == 1 && getNode(SelectHead,3)->value == 1)
		{
			res = getNode(InHead,13)->value;
		}
		else if (SelectHead->value == 1 && getNode(SelectHead,1)->value == 0 && getNode(SelectHead,2)->value == 0 && getNode(SelectHead,3)->value == 1)
		{
			res = getNode(InHead,14)->value;
		}
		else
		{
			res = getNode(InHead,15)->value;
		}
	}
	//

	//find output
	char * i2 = strtok(NULL, " ");
	char c2 = i2[0];

	multiStrng[multiStrngIndex] = ' ';
	multiStrngIndex++;
	multiStrng[multiStrngIndex] = c2;
	multiStrngIndex++;
	multiStrng[multiStrngIndex] = '\0';
	
	int index = findSymbol(outputList,c2,numberOfOutputs);
	int whichList = 1;
	if (index == -1)
	{
		index = findSymbol(interList,c2,interListSize);
		whichList = -1;
		if (index == -1)
		{
			interList[interListSize].symbol = c2;
			index = interListSize;
			interListSize++;
		}
	}

	if (whichList > 0)
	{
		outputList[index].val = res;
		//printf("%d\n", outputList[index].val);
	}
	else
	{
		interList[index].val = res;
		//printf("%d\n", interList[index].val);
	}

	//asssign value = result

	strs[strsSize] = strdup(multiStrng);
	strsSize++;	

	*interListSize2 = interListSize;

	return strsSize;
}

int parseDFLIPFLOP(struct Symbol * inputList,
			 	   struct Symbol * outputList,
			  	   struct Symbol interList[],
			 	   int * interListSize2,
			 	   char * strs[],
			  	   int strsSize,
			       char * token, 
			   	   int numberOfInputs, 
			  	   int numberOfOutputs,
			  	   int previousCLK,
			  	   int currentCLK)
{
	//asd
	char flipflopStrng[70];
	int flipflopIndex = 0;

	
	char * i = strtok(NULL," \n");
	int j = atoi(i);


	int initialval = j;

	char * inputc = strtok(NULL," \n");
	char c = inputc[0];


	int inputval = 0;
	int dubs = findSymbol(outputList,c,numberOfInputs);
	if (dubs == -1)
	{
		dubs = findSymbol(interList,c,interListSize2);
		if (dubs == -1)
		{
			printf("%s\n", "not found");
		}
		else
		{
			inputval = interList[dubs].val;
		}
	}
	else
	{
		inputval = inputList[dubs].val;
	}

	char * clockc = strtok(NULL," \n");
	char clk = clockc[0];


	char * outc = strtok(NULL," \n");
	char oc = outc[0];


	int outttt = 0;

	if (currentCLK == 1)
	{
		outttt = inputval;
	}
	else
	{
		outttt = initialval;
	}
	
	int T = findSymbol(outputList,oc,numberOfInputs);
	if (T == -1)
	{
		T = findSymbol(interList,oc,26);
		if (T == -1)
		{
			//make new
		}
		else
		{
			interList[T].val = outttt;
		}
	}
	else
	{
		outputList[T].val = outttt;
	}

	flipflopStrng[flipflopIndex] = 'D';
						flipflopIndex++;
						flipflopStrng[flipflopIndex] = 'F';
						flipflopIndex++;
						flipflopStrng[flipflopIndex] = 'L';
						flipflopIndex++;
						flipflopStrng[flipflopIndex] = 'I';
						flipflopIndex++;
						flipflopStrng[flipflopIndex] = 'P';
						flipflopIndex++;
						flipflopStrng[flipflopIndex] = 'F';
						flipflopIndex++;
						flipflopStrng[flipflopIndex] = 'L';
						flipflopIndex++;
						flipflopStrng[flipflopIndex] = 'O';
						flipflopIndex++;
						flipflopStrng[flipflopIndex] = 'P';
						flipflopIndex++;
						flipflopStrng[flipflopIndex] = ' ';
						flipflopIndex++;

						if (outttt > 0)
						{
							flipflopStrng[flipflopIndex] = '1';
							flipflopIndex++;
							flipflopStrng[flipflopIndex] = ' ';
							flipflopIndex++;
						}
						else
						{
							flipflopStrng[flipflopIndex] = '0';
							flipflopIndex++;
							flipflopStrng[flipflopIndex] = ' ';
							flipflopIndex++;
						}
					


					flipflopStrng[flipflopIndex] = c;
						flipflopIndex++;
						flipflopStrng[flipflopIndex] = ' ';
						flipflopIndex++;
					flipflopStrng[flipflopIndex] = 'C';
						flipflopIndex++;
						flipflopStrng[flipflopIndex] = ' ';
						flipflopIndex++;
					flipflopStrng[flipflopIndex] = oc;
						flipflopIndex++;
						flipflopStrng[flipflopIndex] = ' ';
						flipflopIndex++;
						flipflopStrng[flipflopIndex] = '\n';


	strs[strsSize] = strdup(flipflopStrng);
	strsSize++;	

	return strsSize;
}

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////////////////////////MAIN MAIN MAIN MAIN MAIN MAIN //////////////////////////////////////////////
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


int main(int argc, char **argv[])
{
	if (argc != 3)
	{
		printf("error");
		return 0;
	} 
	else 
	{
		char * circuit = argv[1];
		char * input = argv[2];
		FILE *circuitfl = fopen(circuit,"rt");
		FILE *inputfl = fopen(input,"rt");
		char INPUTVAR[] = "INPUTVAR";
		char OUTPUTVAR[] = "OUTPUTVAR";
		char AND[] = "AND";
		char OR[] = "OR";
		char NOT[] = "NOT";
		char DECODER[] = "DECODER";
		char MULTIPLEXER[] = "MULTIPLEXER";
		char CLOCK[] = "CLOCK";
		char DFLIPFLOP[] = "DFLIPFLOP";


		if (circuitfl == NULL)
		{
			printf("%s\n", "error");
			return 1;
		}
		if (inputfl == NULL)
		{
			printf("%s\n", "error");
			return 1;	
		}

		int numberOfCycles = numberOfInputLines(inputfl);
		int currentCycle = 1;
		int numberOfInputs = 0;
		int numberOfOutputs = 0;
		int currentCLK = 0;
		int previousCLK = 0;
		
		char line[1000];
		struct Symbol * inputList;
		struct Symbol * outputList;
		struct Symbol interList[26];
		int interListSize = 0;

		struct Operation * opList = (struct Operation *)malloc(sizeof(struct Operation));
		struct Operation * inputText = (struct Operation *)malloc(sizeof(struct Operation));
		struct Operation * outputText = (struct Operation *)malloc(sizeof(struct Operation));
		outputText->string = (char *)malloc(255);
		int isOpListInitialized = 0;
		//
		char *strs[30];
		int strsSize = 0;
		//

		while (fgets(line, sizeof(line),circuitfl))
		{

			char * token = strtok(line," \n");
			int numToks = 0;

			while (token != NULL)
			{
				//printf("%d\n",inputList[0].symbol - '0');
				//printf("%s\n", token);
				//printf("%d\n",inputList[0].symbol);
				if (strcmp(token,INPUTVAR)==0)  ///////////////////////////////////////////////////////////////////////////////////////
				{
					numToks++;
					//printf("%s\n", token);
					char * i = strtok(NULL," \n");
					int j = atoi(i);
					int ii = 0;

					numberOfInputs = j;					
					inputList = (struct Symbol *)calloc(j,sizeof(struct Symbol));
		
					//build input symbols
					//printf("%d\n", j);
					while (ii < j)
					{
						char * s = strtok(NULL, " \n");	

						inputList[ii].symbol = s[0];
						ii++;
					}
					//fclose(inputfl);
					inputfl = fopen(input,"rt");
					//build table
					inputText = buildSymbolValues2(inputList,numberOfInputs,20,numberOfCycles,inputfl);

					previousCLK = currentCLK;
					inputList = parseSequetionalList(inputText,currentCycle-1,inputList,numberOfInputs,&currentCLK);

					//inputList = buildSymbolValues(inputList,inputfl,currentCycle,numberOfInputs);
				}
				else if (strcmp(token,OUTPUTVAR) == 0) ////////////////////////////////////////////////////////////////////////////////////
				{
					numToks++;
					//printf("%s\n", token);
					char * i = strtok(NULL," \n");
					int j = atoi(i);
					int ii = 0;

					outputList = (struct Symbol *)calloc(j,sizeof(struct Symbol));
					numberOfOutputs = j;

					//build output symbols
					while (ii < j)
					{
						char * s = strtok(NULL, " ");						
						//printf("%c\n", s[0]);
						outputList[ii].symbol = s[0];
						outputList[ii].val = 0;
						ii++;
					}
				}
				else if (strcmp(token,CLOCK)==0)
				{
					//do things
					
				}
				else if (strcmp(token,AND) == 0)  ////////////////////////////////////////////////////////////////////////////////////////
				{
					strsSize = parseAND(inputList,outputList,interList,&interListSize,strs,strsSize,token,numberOfInputs,numberOfOutputs);
				}
				else if (strcmp(token,OR) == 0) //////////////////////////////////////////////////////////////////////////////////////////
				{
					strsSize = parseOR(inputList,outputList,interList,&interListSize,strs,strsSize,token,numberOfInputs,numberOfOutputs);
				}
				else if (strcmp(token,NOT) == 0)  ///////////////////////////////////////////////////////////////////////////////////
				{
					strsSize = parseNOT(inputList,outputList,interList,&interListSize,strs,strsSize,token,numberOfInputs,numberOfOutputs);								
				}
				else if (strcmp(token,DECODER)==0)   /////////////////////////////////////////////////////////////////////////////////////
				{
					strsSize = parseDECODER(inputList,outputList,interList,&interListSize,strs,strsSize,&token,numberOfInputs,numberOfOutputs);				
				} 
				else if (strcmp(token,MULTIPLEXER) == 0) //////////////////////////////////////////////////////////////////////////////////////////
				{
					strsSize = parseMULTIPLEXER(inputList,outputList,interList,&interListSize,strs,strsSize,token,numberOfInputs,numberOfOutputs);	
				} 
				else if (strcmp(token,DFLIPFLOP) == 0)
				{
					strsSize = parseDFLIPFLOP(inputList,outputList,interList,&interListSize,strs,strsSize,token,numberOfInputs,numberOfOutputs,currentCLK,previousCLK);
				}

				token = strtok(NULL, " \n");
			}
		}
		int z = 0;
		while (z < strsSize)
		{
			//create new operation
			if (isOpListInitialized == 0)
			{
				opList->string = strs[z];
				opList->next = NULL;
				isOpListInitialized = -1;
			}
			else
			{
				pushNode(opList,strs[z]);
			}
			z++;
		}

		struct Operation * tmpp = opList;
		while (tmpp != NULL)
		{
			//printf("%s\n", tmpp->string);
			tmpp = tmpp->next;
		}

		//printOutputValues(outputList,numberOfOutputs);
		outputText = buildOutputText(outputText,outputList,numberOfOutputs);
		outputText = addToOutputText(outputText,outputList,numberOfOutputs,currentCLK);

		//loop though cycle inputs.
		////////////////////////////////////////////////////////////////////////////////////////////////////////
		currentCycle = 2;
		while (currentCycle <= numberOfCycles)
		{
			//inputText = buildSymbolValues2(inputList,numberOfInputs,20,numberOfCycles,inputfl);
			previousCLK = currentCLK;
			inputList = parseSequetionalList(inputText,currentCycle-1,inputList,numberOfInputs,inputList[numberOfInputs-1].val);

			if (previousCLK == 0)
			{
				currentCLK = 1;
				previousCLK = 0;
			}
			else
			{
				currentCLK = 0;
				previousCLK = 1;
			}

			struct Symbol interList[26];
			int interListSize = 0;

			int k = 0;
			while (k < 25)
			{
				interList[k].symbol = ']';
				k++;
			}

			int z = 0;
			while (z < strsSize)
			{
				int randomholder;
				char * token = strtok(strs[z]," \n");

				if (strcmp(token,AND)==0)
				{
					//printf("%s\n", token);
									
					char andStrng[15];
					int andIDX = 0;

					char * i = strtok(NULL," ");
				
					char c = i[0];

					int andInput1 = findSymbol(inputList,c,numberOfInputs);
					if (andInput1 == -3)
					{
						//printf("%s\n", "andInput = 0");
						andInput1 = 0;
					}
					else if (andInput1 == -2)
					{
						//printf("%s\n", "andInput1 = 1");
						andInput1 = 1;
					}
					else if (andInput1 == -1)
					{
						andInput1 = findSymbol(interList,c,interListSize);
						if (andInput1 == -1)
						{
							andInput1 = findSymbol(outputList,c,numberOfOutputs);
							andInput1 = outputList[andInput1].val;
						}
						else
						{
							
							andInput1 = interList[andInput1].val;
						}
					}
					else
					{
						
						andInput1 = inputList[andInput1].val;
					}
					char * i2 = strtok(NULL," ");
					char c2 = i2[0];

					int andInput2 = findSymbol(inputList,c2,numberOfInputs);
					int In2Selector = 1;
					if (andInput2 == -3)
					{
						//printf("%s\n", "andOutput2 = 0");
						andInput2 = 0;
					}
					else if (andInput2 == -2)
					{
						//printf("%s\n", "andOutput2 = 1");
						andInput2 = 1;
					}
					else if (andInput2 == -1)
					{
						andInput2 = findSymbol(interList,c2,26);
						In2Selector = 3;
						if (andInput2 == -1)
						{
							andInput2 = findSymbol(outputList,c2,numberOfOutputs);
							andInput2 = outputList[andInput2].val;
						}
						else
						{
							andInput2 = interList[andInput2].val;
						}
					}
					else
					{
						
						andInput2 = inputList[andInput2].val;
					}

					char * i3 = strtok(NULL," ");
					char c3 = i3[0];
	
					
					int whichList = 1;
					int andOutput = findSymbol(outputList,c3,numberOfOutputs);
					if (andOutput == -1)
					{
						andOutput = findSymbol(interList,c3,26);
						whichList = -1;
						if (andOutput == -1)
						{
							//create
							//printf("created new symbol %c\n", c3);
							interList[interListSize].symbol = c3;
							andOutput = interListSize;
							interListSize++;
						}
					}
					if (whichList > 0)
					{
						outputList[andOutput] = *_AND(andInput1,andInput2,&outputList[andOutput]);
						//printf("%c", outputList[andOutput].symbol);
						//printf(" = %d\n", outputList[andOutput].val);
					}
					else
					{
						interList[andOutput] = *_AND(andInput1,andInput2,&interList[andOutput]);
						//printf("%c", interList[andOutput].symbol);
						//printf(" = %d\n", interList[andOutput].val);
					}

					andStrng[andIDX] = 'A';
					andIDX++;
					andStrng[andIDX] = 'N';
					andIDX++;
					andStrng[andIDX] = 'D';
					andIDX++;
					andStrng[andIDX] = ' ';
					andIDX++;
					andStrng[andIDX] = c;
					andIDX++;
					andStrng[andIDX] = ' ';
					andIDX++;
					andStrng[andIDX] = c2;
					andIDX++;
					andStrng[andIDX] = ' ';
					andIDX++;
					andStrng[andIDX] = c3;
					andIDX++;
					andStrng[andIDX] = '\0';

					strs[z] = strdup(andStrng);
				} //////////////////////////////////////////////////////////////////////////////////////////////////////
				else if (strcmp(token,OR)==0)
				{
					//printf("%s\n", token);

							
					char orStrng[15];
					int orStrngIndex = 0;
					orStrng[orStrngIndex] = 'O';
					orStrngIndex++;
					orStrng[orStrngIndex] = 'R';
					orStrngIndex++;
					
					char * i = strtok(NULL," ");
					char c = i[0];
					int orInput1 = findSymbol(inputList,c,numberOfInputs);
					if (orInput1 == -3)
					{
						orInput1 = 0;
					}
					else if (orInput1 == -2)
					{
						orInput1 = 1;
					}
					else if (orInput1 == -1)
					{
						orInput1 = findSymbol(interList,c,26);
						if (orInput1 == -1)
						{
							orInput1 = findSymbol(outputList,c,numberOfOutputs);
							orInput1 = outputList[orInput1].val;
						}
						else
						{
							orInput1 = interList[orInput1].val;
						}
					}
					else
					{
						orInput1 = inputList[orInput1].val;
					}

					orStrng[orStrngIndex] = ' ';
					orStrngIndex++;
					orStrng[orStrngIndex] = c;
					orStrngIndex++;
					
					char * i2 = strtok(NULL," ");
					char c2 = i2[0];
					int orInput2 = findSymbol(inputList,c2,numberOfInputs);
					if (orInput2 == -3)
					{
						
						orInput2 = 0;
					}
					else if (orInput2 == -2)
					{
						
						orInput2 = 1;
					}
					else if (orInput2 == -1)
					{
						orInput2 = findSymbol(interList,c2,26);
						if (orInput2 == -1)
						{
							orInput2 = findSymbol(outputList,c2,numberOfOutputs);
							orInput2 = outputList[orInput2].val;
						}
						else
						{
							orInput2 = interList[orInput2].val;

							orStrng[orStrngIndex] = ' ';
							orStrngIndex++;
							orStrng[orStrngIndex] = c2;
							orStrngIndex++;
						}
					}
					else
					{
						orInput2 = inputList[orInput2].val;

						orStrng[orStrngIndex] = ' ';
						orStrngIndex++;
						orStrng[orStrngIndex] = c2;
						orStrngIndex++;
					}
					char * i3 = strtok(NULL," ");
					char c3 = i3[0];
									
					int orOutput = findSymbol(outputList,c3,numberOfOutputs);
					int whichList = 1;
					if (orOutput == -1)
					{
						orOutput = findSymbol(interList,c3,interListSize);
						whichList = -1;
						if (orOutput == -1)
						{
							interList[interListSize].symbol = c3;
							orOutput = interListSize;
							interListSize++;
						}
					}

					
					if (whichList > 0)
					{
						outputList[orOutput] = *_OR(orInput1,orInput2,&outputList[orOutput]);
						//printf("%d\n", outputList[orOutput].val);
					}
					else
					{
						interList[orOutput] = *_OR(orInput1,orInput2,&interList[orOutput]);
						//printf("%d\n", interList[orOutput].val);
					}
					orStrng[orStrngIndex] = ' ';
					orStrngIndex++;
					orStrng[orStrngIndex] = c3;
					orStrngIndex++;
					orStrng[orStrngIndex] = '\0';

					strs[z] = strdup(orStrng);
				} //////////////////////////////////////////////////////////////////////////
				else if (strcmp(token,NOT)==0)
				{
					//printf("%s\n", token);	

					char notStrng[15];
					int notStrngIndex = 0;

					notStrng[notStrngIndex] = 'N';
					notStrngIndex++;
					notStrng[notStrngIndex] = 'O';
					notStrngIndex++;
					notStrng[notStrngIndex] = 'T';
					notStrngIndex++;

					char * i = strtok(NULL," ");
					char c = i[0];

					int notInput = findSymbol(inputList,c,numberOfInputs);
					if (notInput == -1)
					{
						notInput = findSymbol(interList,c,interListSize);
						if (notInput == -1)
						{
							notInput = findSymbol(outputList,c,numberOfOutputs);
							notInput = outputList[notInput].val;
						}
						else
						{
							notInput = interList[notInput].val;
						}
					}
					else if (notInput == -2)
					{
						notInput = 1;
					}
					else if (notInput == -3)
					{
						notInput = 0;
					}
					else
					{
						notInput = inputList[notInput].val;
					}

					notStrng[notStrngIndex] = ' ';
					notStrngIndex++;
					notStrng[notStrngIndex] = c;
					notStrngIndex++;


					char * i2 = strtok(NULL, " ");
					char c2 = i2[0];

					int notOutput = findSymbol(outputList,c2,numberOfOutputs);
					int whichList = 1;
					if (notOutput == -1)
					{
						notOutput = findSymbol(interList,c2,interListSize);
						whichList = -1;
						if (notOutput == -1)
						{
							interList[interListSize].symbol = c2;
							notOutput = interListSize;
							interListSize++;
						}
					}

					notStrng[notStrngIndex] = ' ';
					notStrngIndex++;
					notStrng[notStrngIndex] = c2;			
					notStrngIndex++;
					notStrng[notStrngIndex] = '\0';
					

					if (whichList > 0)
					{
						outputList[notOutput].val = _NOT(notInput);
						//printf("out = %d\n", outputList[notOutput].val);
					}
					else
					{
						interList[notOutput].val = _NOT(notInput);
						//printf("out = %d\n", interList[notOutput].val);
					}

					strs[z] = strdup(notStrng);
				} ////////////////////////////////////////////////////////////////////////////////////////////////////////
				else if (strcmp(token,DECODER)==0)
				{
					char decodeStrng[70];
					int decodeStrngIndex = 0;

					decodeStrng[decodeStrngIndex] = 'D';
					decodeStrngIndex++;
					decodeStrng[decodeStrngIndex] = 'E';
					decodeStrngIndex++;
					decodeStrng[decodeStrngIndex] = 'C';
					decodeStrngIndex++;
					decodeStrng[decodeStrngIndex] = 'O';
					decodeStrngIndex++;
					decodeStrng[decodeStrngIndex] = 'D';
					decodeStrngIndex++;
					decodeStrng[decodeStrngIndex] = 'E';
					decodeStrngIndex++;
					decodeStrng[decodeStrngIndex] = 'R';
					decodeStrngIndex++;
					decodeStrng[decodeStrngIndex] = ' ';
					decodeStrngIndex++;

					//printf("%s\n", token);
					char * i = strtok(NULL," \n");
					int j = atoi(i);
					int ii = 0;
					int j2 = pow(2,j);
					int init = 0;

					int run = 0;
					while (run < strlen(i))
					{
						decodeStrng[decodeStrngIndex] = i[run];
						decodeStrngIndex++;
						run++;
					}

					//
					struct Node * InHead = (struct Node *)malloc(sizeof(struct Node));
					struct Node * OutHead = (struct Node *)malloc(sizeof(struct Node));
					InHead->value = 0;
					InHead->next = NULL;
					struct Node * temp = InHead;
					while (ii < j)
					{
						char * ayylmao = strtok(NULL," ");
						char c = ayylmao[0];

						decodeStrng[decodeStrngIndex] = ' ';
						decodeStrngIndex++;
						decodeStrng[decodeStrngIndex] = c;
						decodeStrngIndex++;
						
						int byy = findSymbol(inputList,c,numberOfInputs);
						if (byy == -1)
						{
							byy = findSymbol(interList,c,interListSize);
							if (byy == -1)
							{
								//printf("%s\n", "not found");
							}
							else
							{
								if (init == 0)
								{
									InHead->value = interList[byy].val;
									InHead->next = NULL;
									init = 1;
								}
								else
								{
									push(InHead,interList[byy].val);
								}
							}
						}
						else if (byy == -2)
						{
							byy = 1;
							if (init == 0)
							{
								InHead->value = 1;
								InHead->next = NULL;
								init = 1;
							}
							else
							{
								push(InHead,1);
							}
						}
						else if (byy == -3)
						{
							byy = 0;
							if (init == 0)
							{
								InHead->value = 0;
								InHead->next = NULL;
								init = 1;
							}
							else
							{
								push(InHead,0);
							}
						}
						else
						{
							if (init == 0)
							{
								InHead->value = inputList[byy].val;
								InHead->next = NULL;
								init = 1;
							}
							else
							{
								push(InHead,inputList[byy].val);
							}
						}
						ii++;
					}
					ii = 0;
					
					struct Node * t = InHead;
					while (t != NULL)
					{
						//printf("Node %d\n", t->value);
						t = t->next;
					}
					

					while (ii < j2)
					{
						//build outputs and assign values
						char * fug = strtok(NULL," ");
						char c = fug[0];

						decodeStrng[decodeStrngIndex] = ' ';
						decodeStrngIndex++;
						decodeStrng[decodeStrngIndex] = c;
						decodeStrngIndex++;

					
						int whichList = 1;
						int madeNew = 0;
						int a = findSymbol(outputList,c,numberOfOutputs);
						int res = 0;

						if (a == -1)
						{
							a = findSymbol(interList,c,interListSize);
							whichList = -1;
							if ( a == -1)
							{
								//make new
				
								interList[interListSize].symbol = c;
								interList[interListSize].val = 0;
								interListSize++;
								madeNew = 1;
							}
							else
							{
								res = interList[a].val;
							}
						}
						else
						{
							res = outputList[a].val;
						}
						
						
						if (j == 1)
						{
							//decode for 1
							if (ii == 0)
							{
								// 0
								if (InHead->value == 0)
								{
									res = 1;
								}
								else
								{
									res = 0;
								}
							}
							else
							{
								if (InHead->value == 1)
								{
									res = 1;
								}
								else
								{
									res = 0;
								}
							}
						}
						else if (j == 2)
						{
							if (ii == 0)
							{
								//0 0
								if (InHead->value == 0 && InHead->next->value == 0)
								{
									res = 1;
								}
								else
								{
									res = 0;
								}
							}
							else if (ii == 1)
							{
								//0 1
								if (InHead->value == 0 && InHead->next->value == 1)
								{
									res = 1;
								}
								else
								{
									res = 0;
								}
							}
							else if (ii == 2)
							{
								//1 1
								if (InHead->value == 1 && InHead->next->value == 1)
								{
									res = 1;
								}
								else
								{
									res = 0;
								}
							}
							else 
							{
								// 1 0
								if (InHead->value == 1 && InHead->next->value == 0)
								{
									res = 1;
								}
								else
								{
									res = 0;
								}
							}
						}
						else if (j == 3)
						{
							//decode for 3
							if (ii == 0)
							{
								if (InHead->value == 0 && InHead->next->value == 0 && InHead->next->next->value == 0)
								{
									res = 1;
								}	
								else
								{
									res = 0;
								}
							}
							else if (ii == 1)
							{
								if (InHead->value == 0 && InHead->next->value == 0 && InHead->next->next->value == 1)
								{
									res = 1;
								}	
								else
								{
									res = 0;
								}
							}
							else if (ii == 2)
							{
								if (InHead->value == 0 && InHead->next->value == 1 && InHead->next->next->value == 1)
								{
									res = 1;
								}	
								else
								{
									res = 0;
								}
							}
							else if (ii == 3)
							{
								if (InHead->value == 0 && InHead->next->value == 1 && InHead->next->next->value == 0)
								{
									res = 1;
								}	
								else
								{
									res = 0;
								}
							}
							else if (ii == 4)
							{
								if (InHead->value == 1 && InHead->next->value == 1 && InHead->next->next->value == 0)
								{
									res = 1;
								}	
								else
								{
									res = 0;
								}
							}
							else if (ii == 5)
							{
								if (InHead->value == 1 && InHead->next->value == 1 && InHead->next->next->value == 1)
								{
									res = 1;
								}	
								else
								{
									res = 0;
								}
							}
							else if (ii = 6)
							{
								if (InHead->value == 1 && InHead->next->value == 0 && InHead->next->next->value == 1)
								{
									res = 1;
								}	
								else
								{
									res = 0;
								}
							}
							else if (ii = 7)
							{
								if (InHead->value == 1 && InHead->next->value == 0 && InHead->next->next->value == 0)
								{
									res = 1;
								}	
								else
								{
									res = 0;
								}
							}
						}
						else if (j == 4)
						{
							//decode for 4
							if (ii == 0)
							{
								if (InHead->value == 0 && InHead->next->value == 0 && InHead->next->next->value == 0 && InHead->next->next->next->value == 0)
								{
									res = 1;
								}
								else
								{
									res = 0;
								}
							}
							else if (ii == 1)
							{
								if (InHead->value == 0 && InHead->next->value == 0 && InHead->next->next->value == 0 && InHead->next->next->next->value == 1)
								{
									res = 1;
								}
								else
								{
									res = 0;
								}
							}
							else if (ii == 2)
							{
								if (InHead->value == 0 && InHead->next->value == 0 && InHead->next->next->value == 1 && InHead->next->next->next->value == 1)
								{
									res = 1;
								}
								else
								{
									res = 0;
								}
							}
							else if (ii == 3)
							{
								if (InHead->value == 0 && InHead->next->value == 0 && InHead->next->next->value == 1 && InHead->next->next->next->value == 0)
								{
									res = 1;
								}
								else
								{
									res = 0;
								}
							}
							else if (ii == 4)
							{
								if (InHead->value == 0 && InHead->next->value == 1 && InHead->next->next->value == 1 && InHead->next->next->next->value == 0)
								{
									res = 1;
								}
								else
								{
									res = 0;
								}
							}
							else if (ii == 5)
							{
								if (InHead->value == 0 && InHead->next->value == 1 && InHead->next->next->value == 1 && InHead->next->next->next->value == 1)
								{
									res = 1;
								}
								else
								{
									res = 0;
								}
							}
							else if (ii == 6)
							{
								if (InHead->value == 0 && InHead->next->value == 1 && InHead->next->next->value == 0 && InHead->next->next->next->value == 1)
								{
									res = 1;
								}
								else
								{
									res = 0;
								}
							}
							else if (ii == 7)
							{
								if (InHead->value == 0 && InHead->next->value == 1 && InHead->next->next->value == 0 && InHead->next->next->next->value == 0)
								{
									res = 1;
								}
								else
								{
									res = 0;
								}
							}
							else if (ii == 8)
							{
								if (InHead->value == 1 && InHead->next->value == 1 && InHead->next->next->value == 0 && InHead->next->next->next->value == 0)
								{
									res = 1;
								}
								else
								{
									res = 0;
								}
							}
							else if (ii == 9)
							{
								if (InHead->value == 1 && InHead->next->value == 1 && InHead->next->next->value == 0 && InHead->next->next->next->value == 1)
								{
									res = 1;
								}
								else
								{
									res = 0;
								}
							}
							else if (ii == 10)
							{
								if (InHead->value == 1 && InHead->next->value == 1 && InHead->next->next->value == 1 && InHead->next->next->next->value == 1)
								{
									res = 1;
								}
								else
								{
									res = 0;
								}
							}
							else if (ii == 11)
							{
								if (InHead->value == 1 && InHead->next->value == 1 && InHead->next->next->value == 1 && InHead->next->next->next->value == 0)
								{
									res = 1;
								}
								else
								{
									res = 0;
								}
							}
							else if (ii == 12)
							{
								if (InHead->value == 1 && InHead->next->value == 0 && InHead->next->next->value == 1 && InHead->next->next->next->value == 0)
								{
									res = 1;
								}
								else
								{
									res = 0;
								}
							}
							else if (ii == 13)
							{
								if (InHead->value == 1 && InHead->next->value == 0 && InHead->next->next->value == 1 && InHead->next->next->next->value == 1)
								{
									res = 1;
								}
								else
								{
									res = 0;
								}
							}
							else if (ii == 14)
							{
								if (InHead->value == 1 && InHead->next->value == 0 && InHead->next->next->value == 0 && InHead->next->next->next->value == 1)
								{
									res = 1;
								}
								else
								{
									res = 0;
								}
							}
							else if (ii == 15)
							{
								if (InHead->value == 1 && InHead->next->value == 0 && InHead->next->next->value == 0 && InHead->next->next->next->value == 0)
								{
									res = 1;
								}
								else
								{
									res = 0;
								}
							}
						}

						//assignment
						if (whichList > 0)
						{
							outputList[a].val = res;
							/*
							printf("%s", "assigned value ");
							printf("%d", res);
							printf("%s", " to symbol: ");
							printf("%c\n", outputList[a].symbol);
							*/
						}
						else
						{
							if (madeNew == 1)
							{
								interList[interListSize-1].val = res;
								/*
								printf("%s", "assigned value ");
								printf("%d", res);
								printf("%s", " to symbol: ");
								printf("%c\n", interList[interListSize-1].symbol);
								*/
							}
							else
							{
								interList[a].val = res;
								/*
								printf("%s", "assigned value ");
								printf("%d", res);
								printf("%s", " to symbol: ");
								printf("%c\n", interList[a].symbol);
								*/
							}
						}
						//
						ii++;
					} ///////////////////////

					strs[z] = strdup(decodeStrng);
				}///////////////////////////////////////////////////////////////////////////////////////////////////////////
				else if (strcmp(token,MULTIPLEXER)==0)
				{
					char multiStrng[70];
					int multiStrngIndex = 0;

					multiStrng[multiStrngIndex] = 'M';
					multiStrngIndex++;
					multiStrng[multiStrngIndex] = 'U';
					multiStrngIndex++;
					multiStrng[multiStrngIndex] = 'L';
					multiStrngIndex++;
					multiStrng[multiStrngIndex] = 'T';
					multiStrngIndex++;
					multiStrng[multiStrngIndex] = 'I';
					multiStrngIndex++;
					multiStrng[multiStrngIndex] = 'P';
					multiStrngIndex++;
					multiStrng[multiStrngIndex] = 'L';
					multiStrngIndex++;
					multiStrng[multiStrngIndex] = 'E';
					multiStrngIndex++;
					multiStrng[multiStrngIndex] = 'X';
					multiStrngIndex++;
					multiStrng[multiStrngIndex] = 'E';
					multiStrngIndex++;
					multiStrng[multiStrngIndex] = 'R';
					multiStrngIndex++;
					multiStrng[multiStrngIndex] = ' ';
					multiStrngIndex++;

					//printf("%s\n", token);
					char * i = strtok(NULL," \n");
					int j = atoi(i);
					int ii = 0;
					int j2 = log2(j);
					int init = 0;

					int run = 0;
					while (run < strlen(i))
					{
						multiStrng[multiStrngIndex] = i[run];
						multiStrngIndex++;
						run++;
					}

					struct Node * InHead = (struct Node *)malloc(sizeof(struct Node));
					struct Node * SelectHead = (struct Node *)malloc(sizeof(struct Node));

					InHead->value = 0;
					InHead->next = NULL;
					SelectHead->value = 0;
					SelectHead->next = NULL;

					while (ii < j)
					{
						char * ayylmao = strtok(NULL," ");
						char c = ayylmao[0];
						
						multiStrng[multiStrngIndex] = ' ';
						multiStrngIndex++;
						multiStrng[multiStrngIndex] = c;
						multiStrngIndex++;

						int byy = findSymbol(inputList,c,numberOfInputs);
						if (byy == -1)
						{
							byy = findSymbol(interList,c,interListSize);
							if (byy == -1)
							{
								//printf("%s\n", "not found");
							}
							else
							{
								if (init == 0)
								{
									InHead->value = interList[byy].val;
									InHead->next = NULL;
									init = 1;
								}
								else
								{
									push(InHead,interList[byy].val);
								}
							}
						}
						else if (byy == -2)
						{
							byy = 1;
							if (init == 0)
							{
								InHead->value = 1;
								InHead->next = NULL;
								init = 1;
							}
							else
							{
								push(InHead,1);
							}
						}
						else if (byy == -3)
						{
							byy = 0;
							if (init == 0)
							{
								InHead->value = 0;
								InHead->next = NULL;
								init = 1;
							}
							else
							{
								push(InHead,0);
							}
						}
						else
						{
							if (init == 0)
							{
								InHead->value = inputList[byy].val;
								InHead->next = NULL;
								init = 1;
							}
							else
							{
								push(InHead,inputList[byy].val);
							}
						}
						ii++;
					}
					ii = 0;

					struct Node * t = InHead;
					while (t != NULL)
					{
						//printf("Node %d\n", t->value);
						t = t->next;
					}

					//reselt initializer 
					init = 0;
					//

					//build second list
					while (ii < j2)
					{
						//build outputs and assign values
						char * ayylmao = strtok(NULL," ");
						char c = ayylmao[0];

						multiStrng[multiStrngIndex] = ' ';
						multiStrngIndex++;
						multiStrng[multiStrngIndex] = c;
						multiStrngIndex++;
						
						int byy = findSymbol(inputList,c,numberOfInputs);
						if (byy == -1)
						{
							byy = findSymbol(interList,c,interListSize);
							if (byy == -1)
							{
								//printf("%s\n", "not found");
							}
							else
							{
								if (init == 0)
								{
									SelectHead->value = interList[byy].val;
									SelectHead->next = NULL;
									init = 1;
								}
								else
								{
									push(SelectHead,interList[byy].val);
								}
							}
						}
						else if (byy == -2)
						{
							byy = 1;
							if (init == 0)
							{
								SelectHead->value = 1;
								SelectHead->next = NULL;
								init = 1;
							}
							else
							{
								push(SelectHead,1);
							}
						}
						else if (byy == -3)
						{
							byy = 0;
							if (init == 0)
							{
								SelectHead->value = 0;
								SelectHead->next = NULL;
								init = 1;
							}
							else
							{
								push(SelectHead,0);
							}
						}
						else
						{
							if (init == 0)
							{
								SelectHead->value = inputList[byy].val;
								SelectHead->next = NULL;
								init = 1;
							}
							else
							{
								push(SelectHead,inputList[byy].val);
							}
						}
						ii++;
					}
					//
					//printf("%s\n", "selector nodes");
					t = SelectHead;
					while (t != NULL)
					{
						//printf("Node %d\n", t->value);
						t = t->next;
					}

					int res = 0;

					//set output, based on number of nodes in list 2, reverse of decoder
					if (j2 == 1)
					{
						if (SelectHead->value == 0)
						{
							res = InHead->value;
						}
						else
						{
							res = InHead->next->value;
						}
					}
					else if (j2 == 2)
					{
						if (SelectHead->value == 0 && SelectHead->next->value == 0)
						{
							res = InHead->value;
						}
						else if (SelectHead->value == 0 && SelectHead->next->value == 1)
						{
							res = InHead->next->value;
						}
						else if (SelectHead->value == 1 && SelectHead->next->value == 1)
						{
							res = InHead->next->next->value;
						}
						else
						{
							res = getNode(InHead,3)->value;
						}
					}
					else if (j2 == 3)
					{
						if (SelectHead->value == 0 && SelectHead->next->value == 0 && SelectHead->next->next->value == 0)
						{
							res = getNode(InHead,0)->value;
						}
						else if (SelectHead->value == 0 && SelectHead->next->value == 0 && SelectHead->next->next->value == 1)
						{
							res = getNode(InHead,1)->value;
						}
						else if (SelectHead->value == 0 && SelectHead->next->value == 1 && SelectHead->next->next->value == 1)
						{
							res = getNode(InHead,2)->value;
						}
						else if (SelectHead->value == 0 && SelectHead->next->value == 1 && SelectHead->next->next->value == 0)
						{
							res = getNode(InHead,3)->value;
						}
						else if (SelectHead->value == 1 && SelectHead->next->value == 1 && SelectHead->next->next->value == 0)
						{
							res = getNode(InHead,4)->value;
						}
						else if (SelectHead->value == 1 && SelectHead->next->value == 1 && SelectHead->next->next->value == 1)
						{
							res = getNode(InHead,5)->value;
						}
						else if (SelectHead->value == 1 && SelectHead->next->value == 0 && SelectHead->next->next->value == 1)
						{
							res = getNode(InHead,6)->value;
						}
						else
						{
							res = getNode(InHead,7)->value;
						}
					}
					else if (j2 == 4)
					{
						if (SelectHead->value == 0 && getNode(SelectHead,1)->value == 0 && getNode(SelectHead,2)->value == 0 && getNode(SelectHead,3)->value == 0)
						{
							res = getNode(InHead,0)->value;
						}
						else if (SelectHead->value == 0 && getNode(SelectHead,1)->value == 0 && getNode(SelectHead,2)->value == 0 && getNode(SelectHead,3)->value == 1)
						{
							res = getNode(InHead,1)->value;
						}
						else if (SelectHead->value == 0 && getNode(SelectHead,1)->value == 0 && getNode(SelectHead,2)->value == 1 && getNode(SelectHead,3)->value == 1)
						{
							res = getNode(InHead,2)->value;
						}
						else if (SelectHead->value == 0 && getNode(SelectHead,1)->value == 0 && getNode(SelectHead,2)->value == 1 && getNode(SelectHead,3)->value == 0)
						{
							res = getNode(InHead,3)->value;
						}
						else if (SelectHead->value == 0 && getNode(SelectHead,1)->value == 1 && getNode(SelectHead,2)->value == 1 && getNode(SelectHead,3)->value == 0)
						{
							res = getNode(InHead,4)->value;
						}
						else if (SelectHead->value == 0 && getNode(SelectHead,1)->value == 1 && getNode(SelectHead,2)->value == 1 && getNode(SelectHead,3)->value == 1)
						{
							res = getNode(InHead,5)->value;
						}
						else if (SelectHead->value == 0 && getNode(SelectHead,1)->value == 1 && getNode(SelectHead,2)->value == 0 && getNode(SelectHead,3)->value == 1)
						{
							res = getNode(InHead,6)->value;
						}
						else if (SelectHead->value == 0 && getNode(SelectHead,1)->value == 1 && getNode(SelectHead,2)->value == 0 && getNode(SelectHead,3)->value == 0)
						{
							res = getNode(InHead,7)->value;
						}
						else if (SelectHead->value == 1 && getNode(SelectHead,1)->value == 1 && getNode(SelectHead,2)->value == 0 && getNode(SelectHead,3)->value == 0)
						{
							res = getNode(InHead,8)->value;
						}
						else if (SelectHead->value == 1 && getNode(SelectHead,1)->value == 1 && getNode(SelectHead,2)->value == 0 && getNode(SelectHead,3)->value == 1)
						{
							res = getNode(InHead,9)->value;
						}
						else if (SelectHead->value == 1 && getNode(SelectHead,1)->value == 1 && getNode(SelectHead,2)->value == 1 && getNode(SelectHead,3)->value == 1)
						{
							res = getNode(InHead,10)->value;
						}
						else if (SelectHead->value == 1 && getNode(SelectHead,1)->value == 1 && getNode(SelectHead,2)->value == 1 && getNode(SelectHead,3)->value == 0)
						{
							res = getNode(InHead,11)->value;
						}
						else if (SelectHead->value == 1 && getNode(SelectHead,1)->value == 0 && getNode(SelectHead,2)->value == 1 && getNode(SelectHead,3)->value == 0)
						{
							res = getNode(InHead,12)->value;
						}
						else if (SelectHead->value == 1 && getNode(SelectHead,1)->value == 0 && getNode(SelectHead,2)->value == 1 && getNode(SelectHead,3)->value == 1)
						{
							res = getNode(InHead,13)->value;
						}
						else if (SelectHead->value == 1 && getNode(SelectHead,1)->value == 0 && getNode(SelectHead,2)->value == 0 && getNode(SelectHead,3)->value == 1)
						{
							res = getNode(InHead,14)->value;
						}
						else
						{
							res = getNode(InHead,15)->value;
						}
					}
					//

					//find output
					char * i2 = strtok(NULL, " ");
					char c2 = i2[0];

					multiStrng[multiStrngIndex] = ' ';
					multiStrngIndex++;
					multiStrng[multiStrngIndex] = c2;
					multiStrngIndex++;
					multiStrng[multiStrngIndex] = '\0';
					
					int index = findSymbol(outputList,c2,numberOfOutputs);
					int whichList = 1;
					if (index == -1)
					{
						index = findSymbol(interList,c2,interListSize);
						whichList = -1;
						if (index == -1)
						{
							interList[interListSize].symbol = c2;
							index = interListSize;
							interListSize++;
						}
					}

					if (whichList > 0)
					{
						outputList[index].val = res;
						//printf("%d\n", outputList[index].val);
					}
					else
					{
						interList[index].val = res;
						//printf("%d\n", interList[index].val);
					}

					strs[z] = strdup(multiStrng);
				} ///////////////////////////////////////////////////////////////////////////////////////////////////////////
				else if (strcmp(token,DFLIPFLOP)==0)
				{
					char flipflopStrng[70];
					int flipflopIndex = 0;
					
					char * i = strtok(NULL," \n");
					int j = atoi(i);

					int initialval = j;

					char * inputc = strtok(NULL," \n");
					char c = inputc[0];

					int inputval = 0;
					int dubs = findSymbol(inputList,c,numberOfInputs);
					if (dubs == -1)
					{
						dubs = findSymbol(interList,c,interListSize);
						if (dubs == -1)
						{

						}
						else
						{
							inputval = interList[dubs].val;
						}
					}
					else
					{
						inputval = inputList[dubs].val;
					}

					char * clockc = strtok(NULL," \n");
					char clk = clockc[0];

					char * outc = strtok(NULL," \n");
					char oc = outc[0];

					int outttt = 0;
					if ((currentCLK == 1))
					{
						outttt = inputval;
					}
					else
					{
						outttt = initialval;
					}

					int whichList = -1;
					int zzz = findSymbol(outputList,oc,numberOfInputs);
					if (zzz == -1)
					{
						whichList = 1;
						zzz = findSymbol(interList,oc,interListSize);
						if (zzz == -1)
						{
							//make new
						}
						else
						{
							interList[zzz].val = outttt;
							if (interList[zzz].val > 0)
							{
								interList[zzz].val = 1;
							}
						}
					}
					else
					{
						outputList[zzz].val = outttt;
						if (outputList[zzz].val > 0)
						{
							outputList[zzz].val = 1;
						}
					}

					flipflopStrng[flipflopIndex] = 'D';
						flipflopIndex++;
						flipflopStrng[flipflopIndex] = 'F';
						flipflopIndex++;
						flipflopStrng[flipflopIndex] = 'L';
						flipflopIndex++;
						flipflopStrng[flipflopIndex] = 'I';
						flipflopIndex++;
						flipflopStrng[flipflopIndex] = 'P';
						flipflopIndex++;
						flipflopStrng[flipflopIndex] = 'F';
						flipflopIndex++;
						flipflopStrng[flipflopIndex] = 'L';
						flipflopIndex++;
						flipflopStrng[flipflopIndex] = 'O';
						flipflopIndex++;
						flipflopStrng[flipflopIndex] = 'P';
						flipflopIndex++;
						flipflopStrng[flipflopIndex] = ' ';
						flipflopIndex++;

						if (outttt > 0)
						{
							flipflopStrng[flipflopIndex] = '1';
							flipflopIndex++;
							flipflopStrng[flipflopIndex] = ' ';
							flipflopIndex++;
						}
						else
						{
							flipflopStrng[flipflopIndex] = '0';
							flipflopIndex++;
							flipflopStrng[flipflopIndex] = ' ';
							flipflopIndex++;
						}
					


					flipflopStrng[flipflopIndex] = c;
						flipflopIndex++;
						flipflopStrng[flipflopIndex] = ' ';
						flipflopIndex++;
					flipflopStrng[flipflopIndex] = 'C';
						flipflopIndex++;
						flipflopStrng[flipflopIndex] = ' ';
						flipflopIndex++;
					flipflopStrng[flipflopIndex] = oc;
						flipflopIndex++;
						flipflopStrng[flipflopIndex] = ' ';
						flipflopIndex++;
						flipflopStrng[flipflopIndex] = '\n';

						strs[z] = strdup(flipflopStrng);

				} ////////////////////////////////////////////////////////////////////////////////////////////////////////////
			z++;
			}
		currentCycle++;
		//printOutputValues(outputList,numberOfOutputs);
		outputText = addToOutputText(outputText,outputList,numberOfOutputs,currentCLK);
		}
		printALL(outputText);
	}
}