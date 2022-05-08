
#include <stdlib.h>

#include "tree.h"
int BSTreeNumNodes (Tree t);

bool TreeIsPerfectlyBalanced(Tree t) {
	if (t == NULL){
		return true;
	}
	int l = BSTreeNumNodes(t->left);
	int r = BSTreeNumNodes(t->right);
	int diff = abs(l - r);
	if (diff <= 1 && TreeIsPerfectlyBalanced(t->left) && TreeIsPerfectlyBalanced(t->right)){
		return true;
	}
	return false;
}

int BSTreeNumNodes (Tree t)
{
	if (t == NULL)
		return 0;
	else
		return 1 + BSTreeNumNodes (t->left) + BSTreeNumNodes (t->right);
}