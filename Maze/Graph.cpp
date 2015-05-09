#include "Graph.h"
#include <cmath>
#include <time.h>
#include <tgmath.h>

Graph::Graph (int k){
	n=k;
	
	int x = 0;
	int y = 0;
	while (y < k){
		while (x < k){
			Node a;
			a.positionx = y;
			a.positiony = x;
			a.next = NULL;
			visited.push_back(false);
			adjac.push_back(a);
			x++;
		}
		x=0;
		y++;
	}
}


bool search(Node &head,Node &target){
	Node *temp = &head;
	while (temp != NULL){
		//std::cout << "temp " << temp->positionx << temp->positiony << "\n";
		//std::cout << "target" << target.positionx << target.positiony;
		if (temp->positionx == target.positionx && temp->positiony == target.positiony){
			return true;
		}
		temp=temp->next;
	}
	return false;
}

void printlst(Node &head){
	Node *temp = &head;
	while (temp != NULL){
		std::cout << temp->positionx << "," << temp->positiony;
		temp=temp->next;
	}

}

//graph here

Node * Graph::generateStart(){
	Graph * grph = this;
	srand(time(NULL));
	int posx = rand() % (int)sqrt(grph->adjac.size());
	int posy = rand() % (int)sqrt(grph->adjac.size());
	
	if (posx == 0 || (posx == sqrt(grph->adjac.size()) - 1)){
		posy = rand() % (int)sqrt(grph->adjac.size());
	} else {
		while (posy != 0 || posy == (grph->adjac.size() - 1)){
			posy = rand() % (int)sqrt(grph->adjac.size());
		}
	}	

	Node * temp = new Node();
	temp->positionx = posx;
	temp->positiony = posy;
	temp->next = NULL;
	return temp;
}

int Graph::generateTwoPossibles()
{
	int a = rand() % 2;
	return a;
}

int Graph::generateThreePossibles()
{
	int a = rand() % 3;
	return a;
}

int Graph::generateFourPossible()
{
	int a = rand() % 4;
	return a;
}

Node * Graph::nextAvailableNode(Graph grph,Node &curr)
{
	int width = sqrt(grph.adjac.size());
	int pos = curr.positiony*width + curr.positionx;
	int result = 0;
	grph.visited[pos] = true;

	if (pos < width)
	{
		//in the top row
		if (pos == 0)
		{
			//generate for pos + 1 and pos + grph.visited.size()
			int gend = generateTwoPossibles();
			if (gend == 0)
			{
				result = pos+1;
			}
			else
			{
				result = pos + width;
			}
		} 
		else if (pos == width)
		{
			//top right corner
			int gend = generateTwoPossibles();
			if (gend == 0)
			{
				result = pos-1;
			} 
			else 
			{
				result = pos + width;
			}
		} 
		else 
		{
			//generate pos +-1, and pos + width
			int gend = generateThreePossibles();
			if (gend == 0)
			{
				result = pos + 1;
			} 
			else if (gend == 1)
			{
				result = pos - 1;
			} 
			else
			{
				result = pos + width;
			}

		}
	} 
	else if (pos >= (pow(width,2)-(width + 1)))
	{
		//bottom row
		if (pos == (pow(width,2) - (width + 1)))
		{
			//generate pos + 1, pos-width
			int gend = generateTwoPossibles();
			if (gend == 0)
			{
				result = pos + 1;
			}
			else
			{
				result = pos - width;
			}
		} 
		else if (pos == (pow(width,2) -1))
		{
			//generate pos - 1, pos - width
			int gend = generateTwoPossibles();
			if (gend == 0)
			{
				result = pos - 1;
			}
			else
			{
				result = pos - width;
			}
		} 
		else 
		{
			//generate pos +-1, pos - width
			int gend = generateThreePossibles();
			if (gend == 0)
			{
				result = pos + 1;
			}
			else if (gend == 1)
			{
				result = pos - 1;
			}
			else
			{
				result = pos - width;
			}
		}
	} 
	else 
	{
		//anywhere in the middle
		//generate for pos +-1,pos +- width
		int gend = generateFourPossible();
		if (gend == 0)
		{
			result = pos + 1;
		} 
		else if (gend == 1)
		{
			result = pos - 1;
		} 
		else if (gend == 2)
		{
			result = pos - width;
		} 
		else 
		{
			result = pos + width;
		}
	}
    //place this in each occurrence?
	//on the recursive call through
	if (grph.visited.at(result) == true)
	{
		//regenerate
	} 
	else 
	{
		//return  node positions
	}
	return &grph.adjac.at(result);
}

Node * Graph::generateEnd(Graph grph,Node &start){
	srand(time(NULL));
	int posx = (rand() + 23) % (int)sqrt(grph.adjac.size());
	int posy = (rand() + 23) % (int)sqrt(grph.adjac.size());
	
	if (posx == 0 || (posx == sqrt(grph.adjac.size()) - 1)){
		posy = rand() % (int)sqrt(grph.adjac.size());
	} else {
		while (posy != 0 || posy == (grph.adjac.size() - 1)){
			posy = rand() % (int)sqrt(grph.adjac.size());
		}
	}

	//if ((posx == start.positionx) && (posy == start.positiony)){

	//	return generateEnd(grph,start);
	//} else {
		Node * temp = new Node();
		temp->positionx = posx;
		temp->positiony = posy;
		temp->next = NULL;
		return temp;
	//}
}
