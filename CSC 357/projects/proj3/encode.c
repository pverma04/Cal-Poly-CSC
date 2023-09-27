#include <stdio.h>
#include <string.h>
#include <ctype.h>
#include <stdlib.h>


typedef struct h_node{ //defining a node
    //child nodes
    node *left; 
    node *right;
    
    //data
    char c;
    int occurences;
}node;

typedef struct h_tree{ //huffman tree
    unsigned curr_size;
    unsigned max_cap;
    struct node** array;
}tree;

node* new_node(char cC, int iOccur){
    node *temp_node = (node*) malloc(sizeof(node));
    temp_node->c = cC;
    temp_node->right = NULL;
    temp_node->left = NULL;
    temp_node->occurences = iOccur;

    return temp_node;
}

tree* create_tree(unsigned iCap){
    tree* temp_tree = (tree*) malloc(sizeof(tree));
    temp_tree->curr_size = 0;
    temp_tree->max_cap = iCap;
    temp_tree->array = (node**) malloc((temp_tree->max_cap) * sizeof(node*));
    return temp_tree;
}

tree* array_to_tree(node** pNodeArray) {
    
}

void insert_node(node** pTree, node* pNode) {
if(!(*pTree)) {
    *pTree = pNode;
    return;
}
if(value < (*binary_tree)->data) {
insert(&(*binary_tree)->left, value);
} else if(value > (*binary_tree)->data) {
insert(&(*binary_tree)->right, value);
}
}

