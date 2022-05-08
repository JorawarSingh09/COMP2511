#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <math.h>
#define MAX_STR 1000

int main(int argc, char *argv[]) {
    if (argc < 3) {
        fprintf(stderr, "Usage: %s value list-elements\n", argv[0]);
        return 1;
    }
    char *str[MAX_STR];
    FILE *fp = fopen("invertedIndex.txt", "r");
    while (fgets(str, 1000, fp) != NULL){
        while ((token = strtok_r(rest, " ", &rest))) {
            printf("%s\n", token); 
        }
    }

    FILE *file = fopen("pagerankList.txt", "r");
    while (fgets(str, 1000, fp) != NULL){
    
    
    }    
    fclose(file);
    fclose(fp);
}
