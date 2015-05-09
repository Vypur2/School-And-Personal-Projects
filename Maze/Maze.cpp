#include <stdlib.h>
#include <stdio.h>
#include "conio.h"
#include "Graph.cpp"

void printMaze(Graph g2)
{
	int i = 0;
	int pos;
	int lineNums = (sqrt(g2.visited.size()));
	int verticles = lineNums - 1;
	int k = 0;
	int p = 0;
	int j = 0;
	int count = 0;
	bool ln = true;
	bool ranthrough = false;	
	
	while (p < g2.adjac.size()){	
		k=0;
		j=1;
		ranthrough = false;
		if (p >= lineNums && p <lineNums*(lineNums-1)+1 && count == lineNums){
			std::cout << "\n";
			while (j < lineNums){
				if (search(g2.adjac.at((p+k)-lineNums),g2.adjac.at(p+k))){
					std::cout << "|       ";
				} else {
					std::cout << "\t";
				}
				j++;
				k++;
			}
			std::cout << "\n";
			ln = false;
			count = 0;

		}

		while (count < lineNums){
			if ((g2.adjac.at(p).positionx == g2.start->positionx) && (g2.adjac.at(p).positiony == g2.start->positiony)){
				std::cout << "s";
			} else if ((g2.adjac.at(p).positionx == g2.end->positionx) && (g2.adjac.at(p).positiony == g2.end->positiony)){
				std::cout << "e";
			} else {
				std::cout << "+";
			}
			
			
			if (p < g2.adjac.size()-1){
				if (search(g2.adjac.at(p),g2.adjac.at(p+1))){
				//if (g2.adjac.at(p).next == &g2.adjac.at(p+1)){
					std::cout << "  ---  ";
				} else if ((p+1)%lineNums == 0){
					;
				} else {
					std::cout << "\t";
				}
			}
			count++;
			p++;
			ranthrough = true;
		}
		ln = true;
		if (ranthrough == false){
			p++;
		} else {
			ranthrough = true;
		}
	}
		
	/*
	while (i < (sqrt(g2.visited.size()))){
		int y = (sqrt(g2.visited.size()));
		k = 0;

		while (k < y-1){
			std::cout << "+" << "\t";
			k++;
		}
		std::cout << "+";
		std::cout << "+" << "\t" << "+" << "\t" << "+";
		std::cout << "\n"; 
		i++;
	}
	*/
}

int main()
{
	std::cout << "Enter the size of the maze" << "\n";
	int i;
	std::cin >> i;
	Graph g (i);

	Node *test = new Node();
	test = g.generateStart();
	g.start = test;
	test = g.generateEnd(g,*test);
	g.end = test;	
	test = g.nextAvailableNode(g,*g.start);
	g.start->next = test;
	std::cout << test->positionx << " , " << test->positiony << "\n";

	printMaze(g);
	getch();

	return 0;
}

