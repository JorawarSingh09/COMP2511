// testList.c - testing DLList data type
// Written by John Shepherd, March 2013

#include <assert.h>
#include <stdio.h>
#include <stdlib.h>

#include "DLList.h"

void printstate(DLList L);

int main (void)
{
	DLList myList = getDLList (stdin);
	putDLList (stdout, myList);
	assert (validDLList (myList));

	// TODO: more tests neede
    
    printstate(myList);
    printf("======\nempty list, insert before current\n");
    DLListBefore(myList, ">first");
    printstate(myList);
    assert(validDLList(myList));
    
    printstate(myList);
    printf("======\none node list, insert before first\n");
    DLListBefore(myList, ">second");
    printstate(myList);
    assert(validDLList(myList));
    
    DLListMoveTo(myList, 2);
    printstate(myList);
    printf("======\nusual list, insert before current\n");
    DLListBefore(myList, ">third");
    printstate(myList);
    assert(validDLList(myList));
    
    DLListDelete(myList);
    DLListDelete(myList);
    DLListDelete(myList);
    printstate(myList);
    printf("======\nempty list, insert after current\n");
    DLListAfter(myList, ">1");
    printstate(myList);
    assert(validDLList(myList));
    
    printstate(myList);
    printf("======\none node list, insert after last\n");
    DLListAfter(myList, ">2");
    printstate(myList);
    assert(validDLList(myList));
    
    DLListMoveTo(myList, 1);
    printstate(myList);
    printf("======\nusual list, insert after current\n");
    DLListAfter(myList, ">3");
    printstate(myList);
    assert(validDLList(myList));
    
    printstate(myList);
    printf("======\nusual list, delete last\n");
    DLListDelete(myList);
    printstate(myList);
    assert(validDLList(myList));
    
    DLListAfter(myList, ">3");
    DLListMoveTo(myList,2);
    printstate(myList);
    printf("======\nusual list, delete middle\n");
    DLListDelete(myList);
    printstate(myList);
    assert(validDLList(myList));
    
    DLListBefore(myList, ">2");
    DLListMoveTo(myList,1);
    printstate(myList);
    printf("======\nusual list, delete first\n");
    DLListDelete(myList);
    printstate(myList);
    assert(validDLList(myList));
    
    DLListDelete(myList);
    printstate(myList);
    printf("======\none node list, delete first\n");
    DLListDelete(myList);
    printstate(myList);
    assert(validDLList(myList));
    
    printstate(myList);
    printf("======\nempty list, delete node\n");
    DLListDelete(myList);
    printstate(myList);
    assert(validDLList(myList));
    
    
    
    
    
	freeDLList (myList);
	return EXIT_SUCCESS;
}

void printstate(DLList L){
    printf("current:%s  length :%zu\n",DLListCurrent(L),DLListLength(L));
    putDLList (stdout, L);

}
