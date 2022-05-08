#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <math.h>
//use Graph.h from lab08
#include "Graph.c"
// Graph.h ... interface to Graph ADT
// Written by John Shepherd, March 2013

#define MAX_URL 100
struct UrlListNode {
	char *filename;
	struct UrlListNode *next;
};
typedef struct UrlListNode *UrlList;

//functions defined below
UrlList newUrlList(char *filename);
UrlList getCollotion(void);
char *my_strdup(const char *word);
UrlList newUrlList(char *filename);
Graph getGraph(UrlList url, int nV);
char *add_string(const char *s, const char *t);
double *calculatePageRank(Graph g, double d, double diffPR, int maxIteration);
double sum_of_pj(Graph g, int num, double *pre_PR);
double win(Graph g, int pj, int pi);
double wout(Graph g, int pj, int pi);
double find_out_degree(Graph g, int p);
double find_in_degree(Graph g, int p);
void sort_array(double *array, int size);
void FreeUrlList(UrlList n);

//global variable that contain max 100 url string 
char *url_to_number[MAX_URL];
double abc[7];

int main(int argc, char *argv[]) {
    if (argc < 4) {
        fprintf(stderr, "Usage: %s value list-elements\n", argv[0]);
        return 1;
    }
    UrlList file = getCollotion();
    UrlList tmp = file;
    int i = 0;
    while(i <= MAX_URL){
        url_to_number[i] = "0"; //reset global string
        i++;
    }
    i = 0;
    while(tmp != NULL){
        url_to_number[i] = tmp->filename;//find nV and add url to global variable
        i++;
        tmp = tmp->next;
    }
    Graph g = getGraph(file, i);
    double d;
    double diffPR;
    int maxIteration;
    sscanf(argv[1], "%lf", &d);
    sscanf(argv[2], "%lf", &diffPR);
    sscanf(argv[3], "%d", &maxIteration);
    
    double *li = calculatePageRank(g, d, diffPR, maxIteration);
    double convert[g->nV];
    FILE *fp = fopen("pagerankList1.txt", "w+");
    int a =0;
    i = 0;
    while(i < g->nV){
        convert[i] = li[i];
        i++;
    }
    sort_array(li, g->nV);
    i = 0;
    while(i < g->nV){
        a = 0;
        while(a < g->nV){
            if(convert[a] == li[i]){
                break;
            }
            a++;
        }
        fprintf(fp, "%s, %.0f, %.7f\n",url_to_number[a], find_out_degree(g, a) , li[i]);
        i++;
    }
    fclose(fp);
    FreeUrlList(file);
    dropGraph(g);
}

void FreeUrlList(UrlList n){
	UrlList curr = n;
	while (curr != NULL) {
		UrlList next = curr->next;
		free(curr->filename);
		free(curr);
		curr = next;
	}

}
//functions below is used to
//read data and creat graph
UrlList getCollotion(void){
    FILE *fp = fopen("collection.txt", "r");
    char *filename = malloc(sizeof(char) * 100);
    UrlList new = newUrlList(filename);
    UrlList tmp = new;
    while(fscanf(fp, "%s", filename) != EOF){
        new->next = newUrlList(filename);
        new = new->next;
    }
    UrlList x = tmp->next;
    free(tmp->filename);
    free(tmp);
    free(filename);
    fclose(fp);
    return x;
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

UrlList newUrlList(char *filename){
    UrlList new = malloc(sizeof(*new));
    if (new == NULL) {
        fprintf(stderr, "Insufficient memory!\n");
        exit(EXIT_FAILURE);
    }
    new->filename = my_strdup(filename);
    new->next = NULL;
    return new;
}

int convert_url_to_number(char* url){
    int i = 0;
    while (i < MAX_URL){
        if (strcmp(url_to_number[i], url) == 0 ) {
            return i;
        }
        i++;
    }
    fprintf(stderr, "Not found url!\n");
    exit(EXIT_FAILURE);
}

char *add_string(const char *s, const char *t){
    char *result = malloc(strlen(s) + strlen(t) + 1); 
    strcpy(result, s);
    strcat(result, t);
    return result;
}

Graph getGraph(UrlList urlList, int nV){
    Graph g = newGraph(nV);
    int i = 0;
    while(urlList != NULL){
        char *filename = add_string(urlList->filename, ".txt");
        FILE *fp = fopen(filename, "r");
        char *url = malloc(sizeof(char) * 100); //read word form file cotained in collection.txt
        while(fscanf(fp, "%s", url) != EOF){
            i = 0;
            while(i < nV){
                if (strcmp(url_to_number[i], url) == 0){ //find the word that in #section1
                    insertEdge(g, convert_url_to_number(urlList->filename), convert_url_to_number(url), 1);
                }
                i++;
            }
        }
        free(url);
        free(filename);
        urlList = urlList->next;
    }
    return g;
}

//functions below is used to
//calculatePageRank
double *calculatePageRank(Graph g, double d, double diffPR, int maxIteration){
    int number_of_urls = g->nV;
    int iteration = 0;
    double diff = diffPR;

    double tmp = 0;
    double pre_PR[number_of_urls];
    double new_PR[number_of_urls];
    int i =0;
    while (i< number_of_urls){
        new_PR[i] = 1.0/number_of_urls;
        i++;
    }
    while(iteration < maxIteration && diff >= diffPR){
        memcpy(pre_PR, new_PR, number_of_urls * sizeof(double));
        i = 0;
        while(i < number_of_urls){
            tmp = sum_of_pj(g, i, pre_PR);
            new_PR[i] = (1.0 - d)/number_of_urls + d * tmp;
        
            i++;
        }
        i = 0;
        diff = 0;
        while(i < number_of_urls){
            diff += fabs(new_PR[i] - pre_PR[i]);
            //printf("%lf  ", diff);
            i++;
        }
        //printf("%lf", diff);
        iteration++;
    }
    //printf("   %d iteration\n", iteration);
    memcpy(abc, new_PR, number_of_urls * sizeof(double));
    return abc;
}

double sum_of_pj(Graph g, int num, double *pre_PR){
    int i = 0;
    double result = 0.0;
    while(i < g->nV){
        if (g->edges[i][num] == 1 && i != num){ //find i = pj and num = pi
            result += (pre_PR[i] * win(g, i, num) * wout(g, i, num));
        }
        i++;
    }
    //printf("%lf  \n", wout(g, 2, 6));
    return result;
}

double wout(Graph g, int pj, int pi){
    double out_degree_pi = find_out_degree(g, pi);
    double out_degree_pj = 0;
    double res = 0.0;
    int i =0;
    while(i < g->nV){
        if (g->edges[pj][i] == 1 && i != pj){ //find out_degree_pj
            if(find_out_degree(g, i) != 0){
                out_degree_pj += find_out_degree(g, i);
            }
        }
        i++;
    }
    if(out_degree_pj == 0){
        return out_degree_pi/0.5;
    }
    /*if(out_degree_pi == 0){
        return 0;
    }*/
    res = out_degree_pi / out_degree_pj;
    //printf("%lf  ", res);
    return res;
}

double find_out_degree(Graph g, int p){
    int i =0;
    double degree = 0.0;
    while(i < g->nV){
        if (g->edges[p][i] == 1 && i != p){
            degree += 1;
        }
        i++;
    }
    if (degree == 0){
        return 0.5;
    }
    return degree;
}

double win(Graph g, int pj, int pi){
    double in_degree_pi = find_in_degree(g, pi);
    double in_degree_pj = 0;
    double res = 0.0;
    int i =0;
    while(i < g->nV){
        if (g->edges[pj][i] == 1 && i != pj){ //find out_degree_pj
            if(find_in_degree(g, i) != 0){
                in_degree_pj += find_in_degree(g, i);
            }
        }
        i++;
    }
    if(in_degree_pj == 0){
        return in_degree_pi/0.5;
    }
    res = in_degree_pi / in_degree_pj;
    //printf("%lf  ", res);
    return res;

}

double find_in_degree(Graph g, int p){
    int i =0;
    double degree = 0.0;
    while(i < g->nV){
        if (g->edges[i][p] == 1 && i != p){
            degree += 1;
        }
        i++;
    }
    if (degree == 0){
        return 0.5;
    }
    //printf("%lf  ", degree);
    return degree;
}

void sort_array(double *array, int size){
    int i, j;
    double temp;
    i = 0;
    while(i < size){
        j = i + 1;
        while(j < size){
            if (array[i] < array[j]){
                temp = array[i];
                array[i] = array[j];
                array[j] = temp;
            }
            j++;
        }
        i++;
    }
}
