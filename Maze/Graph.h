#include <string>
#include <vector>
#include <iostream>

struct Node 
{
	int positionx;
	int positiony;
	Node *next;
};

struct List
{
	std::vector<Node> nodes;
};

class Graph
{
	int n;
public:
	std::vector<bool> visited;
	std::vector<Node> adjac;
	Node *start;
	Node *end;
	Graph (int n);

	///////////////////////////////
	Node * generateStart();
	int generateTwoPossibles();
	int generateThreePossibles();
	int generateFourPossible();
	Node * nextAvailableNode(Graph grph,Node &curr);
	Node * generateEnd(Graph grph,Node &start);
};

bool search(Node &head,Node &target);
void printlst(Node &head);
