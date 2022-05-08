// Queue.c ... implementation of Queue ADT
// Written by John Shepherd, March 2013

#include <assert.h>
#include <stdio.h>
#include <stdlib.h>

#include "Queue.h"
#include "Item.h"

typedef struct QueueNode {
	Item value;
	struct QueueNode *next;
} QueueNode;

typedef struct QueueRep {
	QueueNode *head; // ptr to first node
	QueueNode *tail; // ptr to last node
} QueueRep;

// create new empty Queue
Queue newQueue (void)
{
	QueueRep *new = malloc (sizeof *new);
	*new = (QueueRep){ .head = NULL, .tail = NULL };
	return new;
}

// free memory used by Queue
void dropQueue (Queue Q)
{
	assert (Q != NULL);
	for (QueueNode *curr = Q->head, *next; curr != NULL; curr = next) {
		next = curr->next;
		free (curr);
	}
	free (Q);
}

// display as 3 > 5 > 4 > ...
void showQueue (Queue Q)
{
	assert (Q != NULL);

	for (QueueNode *curr = Q->head; curr != NULL; curr = curr->next) {
		ItemShow (curr->value);
		if (curr->next != NULL)
			printf (">");
	}
	printf ("\n");
}

// add item at end of Queue
void QueueJoin (Queue Q, Item it)
{
	assert (Q != NULL);

	QueueNode *new = malloc (sizeof *new);
	assert (new != NULL);
	*new = (QueueNode){ .value = ItemCopy (it), .next = NULL };

	if (Q->head == NULL)
		Q->head = new;
	if (Q->tail != NULL)
		Q->tail->next = new;
	Q->tail = new;
}

// remove item from front of Queue
Item QueueLeave (Queue Q)
{
	assert (Q != NULL);
	assert (Q->head != NULL);
	Item it = ItemCopy (Q->head->value);
	QueueNode *old = Q->head;
	Q->head = old->next;
	if (Q->head == NULL)
		Q->tail = NULL;
	free (old);
	return it;
}

// check for no items
int QueueIsEmpty (Queue Q)
{
	return (Q->head == NULL);
}

int find_from_queue(Queue q, int *dist){
    QueueNode *tmp = q->head;
    int min = dist[0];
    while(tmp != NULL){
        if (dist[tmp->value] < min){
            min = dist[tmp->value];
        }
        tmp = tmp->next;
    }


	// find where min occurs in Queue
	QueueNode *prev = NULL;
	QueueNode *curr = q->head;
	while (curr != NULL && dist[curr->value] != min) {
		prev = curr;
		curr = curr->next;
	}
	if (curr == NULL){
		assert (curr != NULL);
    }

	if (prev == NULL){
		q->head = curr->next;
	} else {
		prev->next = curr->next;
	}
	if (q->tail == curr) {
		q->tail = prev;
    }
    int a = curr->value;
	free (curr);
	return a;
}
