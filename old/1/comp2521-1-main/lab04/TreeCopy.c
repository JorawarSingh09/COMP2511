
#include "tree.h"
int TreeHight(Tree t);
int nodeDepth (Tree t, int key);
Tree TreeCopy(Tree t, int depth) {
	if (depth < 0){
		return NULL;
	}
	if (depth >= TreeHight(t)){
		return t;
	}

    if (t == NULL) {
        return NULL;
	}
    Tree new = malloc(sizeof(*t));
    new->value = t->value;

    new->left = TreeCopy(t->left, depth - 1);
    new->right = TreeCopy(t->right, depth - 1);
	if (depth == 0){
		return new;
	} 
	return new;
}

int TreeHight(Tree t)
{
	if (t == NULL){
		return 0;
	}
	int lc = TreeHight(t->left);
	int rc = TreeHight(t->right);

	return lc >rc ? lc + 1 : rc +1 ;
}

int nodeDepth (Tree t, int key)
{
	if (t == NULL) return -1;
	if (t->value == key) {
		return 0;
	} else if (key < t->value) {
		int ndl = nodeDepth (t->left, key);
		if (ndl == -1) return -1;
		return ndl + 1;
	} else {
		int ndr = nodeDepth (t->right, key);
		if (ndr == -1) return -1;
		return ndr + 1;
	}
}