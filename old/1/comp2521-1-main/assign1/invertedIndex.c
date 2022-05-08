#include <stdbool.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <ctype.h>
#include <math.h>
#include "invertedIndex.h"
/**
 * Normalises a given string. See the spec for details. Note: you should
 * modify the given string - do not create a copy of it.
 */
char *my_strdup(const char *s);
void Remove(char *str, int num);
FileList newFileList(char *filename);
InvertedIndexBST newInvertedIndexBST(char *s, char *n);
InvertedIndexBST BSTreeInsert (InvertedIndexBST t, char *v, char *n);
void BSTreeInfix(InvertedIndexBST t, FILE *fp);
void showBSTreeNode(InvertedIndexBST t, FILE *fp);
void TreeFree(InvertedIndexBST t);
void TreeFreeFileList(FileList n);
FileList findSearchword(InvertedIndexBST tree, char *searchWord);
TfIdfList newTfList(char *word);
TfIdfList insertTfList(TfIdfList tmp, FileList qwe, double g);
TfIdfList sortTfList(TfIdfList tmp);
TfIdfList GetSumofTf(TfIdfList new, TfIdfList tmp);
int check(char *v,TfIdfList new );

char *normaliseWord(char *str){
    int len = strlen(str);
    int i = 0;
    //remove leading spaces
    while(str[i] == ' '){
        Remove(str, i);  //since every time remove an item, next item goes to that position
        len--;
    }
    //remove specfic punctuation appear after a word  
    i = 0;  
    while(i < len){
        if (str[i+1] == ' ' || str[i+1] == '\0' || str[i+1] == '\t' || str[i+1] == '\n'){
            if (str[i] == '.' || str[i] == ',' || str[i] == ';' || str[i] == '?'){
                Remove(str, i);
            }
        }
        i++;
    }
    // remove trailing spaces
    i = len - 1;
    while(str[i] == ' ' && i >= 0){
        Remove(str, i);  
        i--;      
    }
    //converting all characters to lowercase
    i = 0;
    while(i < len){
        str[i] = tolower(str[i]);
        i++;
    }
    
    return str;
}

void Remove(char *str, int num){
    while(str[num]){
        str[num] = str[num+1];
        num++;
    }
    //strcpy(&str[num], &str[num +1]);
}
/**
 * This function opens the collection file with the given name, and then
 * generates an inverted index from those files listed in the collection
 * file,  as  discussed  in  the spec. It returns the generated inverted
 * index.
 */
InvertedIndexBST generateInvertedIndex(char *collectionFilename){
    FILE *fp = fopen(collectionFilename, "r");
    char *filename = malloc(sizeof(char) * 100); //
    InvertedIndexBST tree = NULL;
    while(fscanf(fp, "%s", filename) != EOF){//tmp = filename
        FILE *fpr = fopen(filename, "r");
        char *word = malloc(sizeof(char) * 100);
        //int Numword = 0;
        while (fscanf(fpr, "%s", word) != EOF){ //temp = word
            //Numword++;
            word = normaliseWord(word);
            if (tree == NULL){
                tree = newInvertedIndexBST(word, filename);
            } else{
                tree = BSTreeInsert(tree, word, filename);
            }
            
        }
        //tree->fileList->tf = tree->fileList->tf/Numword;
        free(word);
        fclose(fpr);
    }
    free(filename);
    fclose(fp);
    return tree;
}
InvertedIndexBST BSTreeInsert (InvertedIndexBST t, char *v, char *n)//v = word, n = filename
{
	if (t == NULL){
        return newInvertedIndexBST(v, n);
    } else if (strcmp(v, t->word) < 0){
		t->left = BSTreeInsert(t->left, v, n);
    } else if (strcmp(v, t->word) > 0){
		t->right = BSTreeInsert(t->right, v, n);
    } else{ // (v == t->word)
		// check if n appear before and insert filename alphabetically
		// check if n < first filename  
        if(strcmp(n, t->fileList->filename) < 0){
            FileList x = newFileList(n);
            x->next = t->fileList;
            t->fileList = x;
            return t;
        }
        FileList new = t->fileList;
        FileList tmp = t->fileList;
        // check if n appeared before
        while(new != NULL){
            if(strcmp(new->filename, n) == 0){
                //new->tf += 1;
                return t;
                
            }
            new = new->next;
        }
        // insert filename alphabetically
        while(tmp->next != NULL){
            if(strcmp(n, tmp->next->filename) < 0){
                FileList temp = newFileList(n);
                temp->next = tmp->next;
                tmp->next = temp;
                return t;
            }
            tmp = tmp->next;
        }
        tmp->next = newFileList(n);
        return t;
    }
	return t;
}

char *my_strdup(const char *word){
    char *n = malloc(strlen (word) + 1);  
    if (n == NULL) {
        fprintf(stderr, "Insufficient memory!\n");
        exit(EXIT_FAILURE);
    }
    strcpy(n, word);                      
    return n;       
}

InvertedIndexBST newInvertedIndexBST(char *word, char *filename){
	InvertedIndexBST new = malloc(sizeof(*new));
    if (new == NULL) {
        fprintf(stderr, "Insufficient memory!\n");
        exit(EXIT_FAILURE);
    }
	new->word = my_strdup(word);
	new->fileList= newFileList(filename);
	new->left = new->right = NULL;
	return new;
}

FileList newFileList(char *filename){
    FileList new = malloc(sizeof(*new));
    if (new == NULL) {
        fprintf(stderr, "Insufficient memory!\n");
        exit(EXIT_FAILURE);
    }
    new->filename = my_strdup(filename);
    new->tf = 0;
    new->next = NULL;
    return new;
}

/**
 * Outputs  the  given inverted index to a file named invertedIndex.txt.
 * The output should contain one line per word, with the  words  ordered
 * alphabetically  in ascending order. Each list of filenames for a word
 * should be ordered alphabetically in ascending order.
*/
void printInvertedIndex(InvertedIndexBST tree){
    FILE *fp = fopen("invertedIndex.txt", "w");
    if(fp == NULL) { 
		printf("Error opening file : invertedIndex.txt \n");
		return;
	}
    BSTreeInfix(tree, fp);
	fclose(fp);
}

void BSTreeInfix(InvertedIndexBST t, FILE *fp){
	if (t == NULL){
        return;
    }
	BSTreeInfix(t->left, fp);
	showBSTreeNode(t, fp);
	BSTreeInfix(t->right, fp);
}

void showBSTreeNode(InvertedIndexBST t, FILE *fp){
    fprintf(fp, "%s ", t->word);
    FileList new = t->fileList;
    while(new != NULL){
        fprintf(fp, "%s ", new->filename);
        //fprintf(fp, "%f ", new->tf);
        new = new->next;
    }
    fprintf(fp, "\n");
}





// Functions for Part-2

/**
 * Returns  an  ordered list where each node contains a filename and the 
 * corresponding tf-idf value for a given searchWord. You only  need  to
 * include documents (files) that contain the given searchWord. The list
 * must  be  in  descending order of tf-idf value. If there are multiple
 * files with same  tf-idf  value,  order  them  by  their  filename  in
 * ascending order.
*/
TfIdfList calculateTfIdf(InvertedIndexBST tree, char *searchWord, int D){
    FileList new = findSearchword(tree, searchWord);
    FileList qwe = findSearchword(tree, searchWord);
    TfIdfList tmp = NULL;
    double i = 0, w =0, f = 0;
    while (new != NULL){
        FILE *fpr = fopen(new->filename, "r");
        char *word = malloc(sizeof(char) * 100);
        i = 0, w =0;
        while (fscanf(fpr, "%s", word) != EOF){
            word = normaliseWord(word);
            if(strcmp(word, searchWord) == 0){
                i++;
            }
            w++;
        }
        fclose(fpr);
        new->tf = i/w;
        f++;
        free(word);
        //printf("%s\n",new->filename);
        new = new->next;
    }
    double g = 0;
    g = log10(D/f);
    tmp = insertTfList(tmp, qwe, g);
    TfIdfList temp = tmp;
    int a = 0;
    while(a < f){
        temp = sortTfList(temp);
        a++;
    }
    //printf("%f \n",temp->tfIdfSum); 
    //*tmp = *temp;
    return tmp;
    //return NULL;
}
TfIdfList sortTfList(TfIdfList tmp){
    TfIdfList node=NULL, temp = NULL;
    double value = 0;//temp variable to store node data
    char *fn = tmp->filename;
    node = tmp;
    //temp = node;//temp node to hold node data and next link
    while(node != NULL){
        temp=node; 
        while (temp->next !=NULL){
            if(temp->tfIdfSum < temp->next->tfIdfSum){
                value = temp->tfIdfSum;
                temp->tfIdfSum = temp->next->tfIdfSum;// swap the data
                temp->next->tfIdfSum = value;
                fn = temp->filename;
                temp->filename = temp->next->filename;// swap the data
                temp->next->filename = fn;
            } else if (temp->tfIdfSum == temp->next->tfIdfSum){
                if(strcmp(temp->filename, temp->next->filename) > 0){
                    value = temp->tfIdfSum;
                    temp->tfIdfSum = temp->next->tfIdfSum;// swap the data
                    temp->next->tfIdfSum = value;
                    fn = temp->filename;
                    temp->filename = temp->next->filename;// swap the data
                    temp->next->filename = fn;
                }
            }
        temp = temp->next;    // move to the next element 
        }
        node = node->next;    // move to the next node
    
    }
    return tmp;
}

TfIdfList insertTfList(TfIdfList tmp, FileList qwe, double g){
    //TfIdfList temp = tmp;
    tmp = newTfList(qwe->filename);
    tmp->tfIdfSum = qwe->tf * g; 
    TfIdfList temp = tmp;
    while(qwe != NULL){
        tmp->next = newTfList(qwe->filename);
        tmp = tmp->next;
        tmp->tfIdfSum = qwe->tf * g; 
        qwe = qwe->next;
        
    }
    TfIdfList x = temp->next;
    free(temp->filename);
    free(temp);
    return x;
}

FileList findSearchword(InvertedIndexBST tree, char *searchWord){
    InvertedIndexBST t = tree;
    while(t != NULL){
        if (strcmp(searchWord, t->word) < 0){
            t = t->left;
        } else if (strcmp(searchWord, t->word) > 0){
            t = t->right;
        
        } else {//strcmp(searchWord, tree->word) == 0
        
            return t->fileList;
        }
    }
    fprintf(stderr, "Find no word!\n");
    exit(EXIT_FAILURE);

}

TfIdfList newTfList(char *word){
	TfIdfList new = malloc(sizeof(*new));
    if (new == NULL) {
        fprintf(stderr, "Insufficient memory!\n");
        exit(EXIT_FAILURE);
    }
	new->filename = my_strdup(word);
	new->tfIdfSum= 0;
	new->next = NULL;
	return new;
}
/**
 * Returns  an  ordered list where each node contains a filename and the
 * summation of tf-idf values of all the matching search words for  that
 * file.  You only need to include documents (files) that contain one or
 * more of the given search words. The list must be in descending  order
 * of summation of tf-idf values (tfIdfSum). If there are multiple files
 * with  the  same tf-idf sum, order them by their filename in ascending
 * order.
 */
TfIdfList retrieve(InvertedIndexBST tree, char *searchWords[], int D){
/*    int i = 0;
    TfIdfList new = NULL;
    TfIdfList tmp = NULL;
    while (searchWords[i] != NULL){
        if (new == NULL){
            new = calculateTfIdf(tree, searchWords[i], D);
        } else {
            tmp = calculateTfIdf(tree, searchWords[i], D);
            new = GetSumofTf(new, tmp);
        }
        i++;
    }
    


    return new;*/
    return NULL;
}

TfIdfList GetSumofTf(TfIdfList new, TfIdfList tmp){
    TfIdfList newnode = new;
    TfIdfList start_tmp = tmp;
    int i = 1;
    //int a = 0;
    while(new != NULL){
        tmp = start_tmp;
        i = 1;
        while(tmp != NULL){
            if (new->filename == tmp->filename){
                new->tfIdfSum = new->tfIdfSum + tmp->tfIdfSum;
            
            } else if (check(tmp->filename, new) == 0){
                TfIdfList temp = new->next;
                new->next = tmp;
                new->next->next = temp;
                i++;
            }
            tmp = tmp->next;
        }
        /*while(a < i){
            new = new->next;
            a++;
        }*/
        new = new->next;
    }
    
    
    return newnode;
    //return NULL;
}

int check(char *v,TfIdfList new ){
    TfIdfList newnode = new;
    int i = 0;
    while(newnode != NULL){
        if(v == new->filename){
            i = 1;
        }
        newnode = newnode->next;
    }
    return i;
}
