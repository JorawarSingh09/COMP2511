
#include <stdlib.h>

#include "BSTree.h"
int BSTreeNumNodes (BSTree t);

int BSTreeGetKth(BSTree t, int k) {
	BSTree tmp = t;
	int m = BSTreeNumNodes(tmp->left);
	if(k < m){
		tmp = tmp->left;
		return BSTreeGetKth(tmp, k);
	} else if(k > m){
		tmp = tmp->right;
		return BSTreeGetKth(tmp, k-m-1);
	} 
	return tmp->value;
}

int BSTreeNumNodes (BSTree t)
{
	if (t == NULL)
		return 0;
	else
		return 1 + BSTreeNumNodes (t->left) + BSTreeNumNodes (t->right);
}
