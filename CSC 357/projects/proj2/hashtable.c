#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include <stdint.h>
#include <stdbool.h>

#define TABLE_SIZE 100

typedef struct hash_entry {
	char* word;
	int occurences;
} hash_entry;

typedef struct linked_list{
	hash_entry* entry;
    struct linked_list* next;
} linked_list;;

typedef struct hash_table{
	int count;
	int size;
	hash_entry** items;
	linked_list** collision_lists;
} hash_table;


/*
---------------------
LINKEDLIST
---------------------
*/


linked_list* allocate_list(){
	linked_list* list = (linked_list*) malloc(sizeof(linked_list));
	return list;
}

linked_list* list_add(linked_list* pList, hash_entry* pEntry){
	if(pList == NULL){
		linked_list* head = allocate_list();
		head->entry = pEntry;
		head->next = NULL;
		pList = head;
		return pList;
	}
	else if(pList->next == NULL){
		linked_list* new_node = allocate_list();
		new_node->entry = pEntry;
		new_node->next = NULL;
		pList->next = new_node;
		return pList;
	}
	linked_list* temp_list = pList;
	while (temp_list->next){
		temp_list = temp_list->next;
	}
	linked_list* new_node = allocate_list();
	new_node->entry = pEntry;
	new_node->next = NULL;
	temp_list->next = new_node;
	return pList;
}

void free_list(linked_list* pList){
	linked_list* temp_list = pList;
	while(pList) {
		temp_list = pList;
		pList = pList->next;
		free(&(temp_list->entry->occurences));
		free(temp_list->entry->word);
		free(temp_list->entry);
		free(temp_list);
	}
}

linked_list** create_collision_lists(hash_table* pTable){
	linked_list** lists = (linked_list**) calloc(pTable->size, sizeof(linked_list));
	for(int i = 0; i < pTable->size; i++){
		lists[i] = NULL;
	}
	return lists;
}


void free_collision_lists(hash_table* pTable){
	linked_list** lists = pTable->collision_lists;
	for(int i = 0; i < pTable->size; i++){
		free_list(lists[i]);
	}
	free(lists);
}


/*
---------------------
HASHTABLE
---------------------
*/

unsigned int hash(char* cWord){
	int count = 0;
	for(int i = 0; i < strlen(cWord); i++){
		count += cWord[i];
	}

	return count;
}
/*
TODO: combine these two - make a search
*/
hash_entry* create_entry(char* cWord){
	hash_entry* entry = (hash_entry*) malloc(sizeof(hash_entry));
	entry->word = (char*) malloc(strlen(cWord) + 1);
	entry->occurences = 1;
	strcpy(entry->word, cWord);
	return entry;
}

hash_entry* create_entry_num(char* cWord, int iOccurences){
	hash_entry* entry = (hash_entry*) malloc(sizeof(hash_entry));
	entry->word = (char*) malloc(strlen(cWord) + 1);
	entry->occurences = iOccurences;
	strcpy(entry->word, cWord);
	return entry;
}

hash_table* create_table(int iSize){
	hash_table* table = (hash_table*) malloc(sizeof(hash_table));
	table->size = iSize;
	table->count = 0;
	table->items = (hash_entry**) calloc(table->size, sizeof(hash_entry));
	for (int i = 0; i < table->size; i++){
		table->items[i] = NULL;
	}
	table->collision_lists = create_collision_lists(table);
	return table;
}	

void free_entry(hash_entry* pEntry){
	free((void *) pEntry->word);
	free(pEntry);
}

void free_table(hash_table* pTable){ 
    for(int i = 0; i < pTable->size; i++){
		hash_entry* entry = pTable->items[i];
		if (entry != NULL)
			free_entry(entry);
	}
	free(pTable->items);
	free_collision_lists(pTable);
	free(pTable);
}

void handle_collisions(hash_table* pTable, int iIndex, hash_entry* pEntry) {
	linked_list* head = pTable->collision_lists[iIndex];
	if(head == NULL){
		head = allocate_list();
		head->entry = pEntry;
		head->next = NULL;
		pTable->collision_lists[iIndex] = head;
		return;
	}
	else{
		pTable->collision_lists[iIndex] = list_add(head, pEntry);
		return;
	}
}

void insert_entry(hash_table* pTable, hash_entry* new_entry){
	int index = hash(new_entry->word);
	
	hash_entry* current_entry = pTable->items[index];
	
	if(current_entry == NULL){
		pTable->items[index] = new_entry;
		pTable->count++;
	}
	else{
		if(strcmp(current_entry->word, new_entry->word) == 0) {
			pTable->items[index]->occurences++;
		}
		else{
			handle_collisions(pTable, index, new_entry);
			return;
		}
	}
}

hash_entry* search_table(hash_table* pTable, char* cWord){
	int index = hash(cWord);
	hash_entry* item_at_index = pTable->items[index];
	linked_list* head_at_index = pTable->collision_lists[index];
	if(item_at_index != NULL) {
		if(strcmp(item_at_index->word, cWord) == 0)
			return item_at_index;
		if(head_at_index == NULL)
			return NULL;
		while(head_at_index->next != NULL){
			head_at_index = head_at_index->next;
			if(strcmp(head_at_index->entry->word, cWord) == 0)
				return head_at_index->entry;
		}
	}
	return NULL;
}

void print_table(hash_table* table){
	printf("\nHash Table: \n");
	linked_list* head;
	for (int i = 0; i < table->size; i++) {
		if(table->items[i]){
			printf("Index:%d, Word:%s, Occurences:%d\n", i, table->items[i]->word, table->items[i]->occurences);
		}
		else if(table->collision_lists[i]){
			head = table->collision_lists[i];
			while(head->next){
				printf("Index:%d, Word:%s, Occurences:%d\n", i, head->entry->word, head->entry->occurences);
			}
		}
	}
	printf("-------------------\n\n");
}

/*hash_entry* table_to_array(hash_table* table){
	hash_entry entry_arr[table->count];
	
}*/

int main(){
	
	char word[100]; //no more than 100 characters in the word
	hash_table* table = create_table(1000);
	hash_entry* new_entry;
	
	
	insert_entry(table, create_entry("abc"));
	printf("%u\n", hash("abc"));
	
	insert_entry(table, create_entry("def"));
	printf("%u\n", hash("def"));
	insert_entry(table, create_entry("ghi"));
	printf("%u\n", hash("ghi"));
	insert_entry(table, create_entry("jkl"));
	printf("%u\n", hash("jkl"));
	insert_entry(table, create_entry("mno"));
	printf("%u\n", hash("mno"));
	insert_entry(table, create_entry("pqr"));
	printf("%u\n", hash("pqr"));
	
	insert_entry(table, create_entry("cba"));
	printf("%u\n", hash("cba"));
	print_table(table);

	/*if (argc == 2){
		FILE* f = fopen(argv[1], "r");
		while (fscanf(f, "%s", word) != EOF){
            create_entry(word);
			new_entry = create_entry(word);
			insert_entry(table, new_entry);
		}
	}*/

	return 0;	
}
