
#include "list.h"

List listSetDifference(List l1, List l2) {
    List diff = newList();
    Node new = l2->head;
    Node new2 = l1->head;
    
    Node tmp = newNode(0);
    diff->head = tmp;
    Node temp = tmp; 
    int flag;
    while (new2 != NULL){
        flag = true;
        new = l2->head;
        while (new != NULL){
            if (new2->value == new->value){
                flag = false;
                break;
            }
            new = new->next;
        }
        if (flag == true){  
            tmp->next = newNode(new2->value);
            tmp = tmp->next;
        }
        new2 = new2->next;
        
    }
    diff->head = diff->head->next;
    free(temp);
    

	return diff;
}

