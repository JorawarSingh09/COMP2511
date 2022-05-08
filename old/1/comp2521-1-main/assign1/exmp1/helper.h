#include <stdbool.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <ctype.h>


void TreeFree(InvertedIndexBST t);
void TreeFreeFileList(FileList n);
void FreeTfList(TfIdfList n);

void TreeFree(InvertedIndexBST t){
    if (t != NULL) {
        TreeFree(t->left);
        TreeFree(t->right);
        TreeFreeFileList(t->fileList);
        free(t->word);
        free(t);
    }
}

void TreeFreeFileList(FileList n) {
	FileList curr = n;
	while (curr != NULL) {
		FileList next = curr->next;
		free(curr->filename);
		free(curr);
		curr = next;
	}

}

void FreeTfList(TfIdfList n){
	TfIdfList curr = n;
	while (curr != NULL) {
		TfIdfList next = curr->next;
		free(curr->filename);
		free(curr);
		curr = next;
	}

}
