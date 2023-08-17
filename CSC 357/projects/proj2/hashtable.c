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
	//hash_entry** items;
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

linked_list* list_add(hash_table* pTable, int iIndex, hash_entry* pEntry){
	linked_list* head = pTable->collision_lists[iIndex];
	if(head == NULL){
		head = allocate_list();
		head->entry = pEntry;
		head->next = NULL;
		pTable->collision_lists[iIndex] = head;
		return head;
	}
	else if(head->next == NULL){
		linked_list* new_node = allocate_list();
		new_node->entry = pEntry;
		new_node->next = NULL;
		head->next = new_node;
		return head;
	}
	linked_list* temp_list = head;
	while (temp_list->next){
		temp_list = temp_list->next;
	}
	linked_list* new_node = allocate_list();
	new_node->entry = pEntry;
	new_node->next = NULL;
	temp_list->next = new_node;
	return head;
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

	return count % 13;
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
/*
hash_entry* create_entry_num(char* cWord, int iOccurences){
	hash_entry* entry = (hash_entry*) malloc(sizeof(hash_entry));
	entry->word = (char*) malloc(strlen(cWord) + 1);
	entry->occurences = iOccurences;
	strcpy(entry->word, cWord);
	return entry;
}
*/


hash_table* create_table(int iSize){
	hash_table* table = (hash_table*) malloc(sizeof(hash_table));
	table->size = iSize;
	table->count = 0;
	table->collision_lists = (linked_list**) calloc(table->size, sizeof(linked_list));
	for (int i = 0; i < table->size; i++){
		table->collision_lists[i] = NULL;
	}
	return table;
}	

void free_entry(hash_entry* pEntry){
	free((void *) pEntry->word);
	free(pEntry);
}

void free_table(hash_table* pTable){ 
	linked_list* entry_head;
    for(int i = 0; i < pTable->size; i++){
		entry_head = pTable->collision_lists[i];
		if (entry_head != NULL)
			free_entry(entry_head->entry);
	}
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
		pTable->collision_lists[iIndex] = list_add(pTable, iIndex, pEntry);
		return;
	}
}

void insert_entry(hash_table* pTable, hash_entry* new_entry){
	int index = hash(new_entry->word);
	
	linked_list* current_head = pTable->collision_lists[index];
	
	if(current_head == NULL){
		pTable->collision_lists[index] = allocate_list();
		pTable->collision_lists[index]->entry = new_entry;
		pTable->collision_lists[index]->next = NULL;
	}
	else{
		if(strcmp(current_head->entry->word, new_entry->word) == 0) {
			current_head->entry->occurences++;
		}
		else{
			linked_list* temp = current_head;
			current_head = list_add(pTable, index, new_entry);
		}
	}
	pTable->count++;
	return;
}

hash_entry* search_table(hash_table* pTable, char* cWord){
	int index = hash(cWord);
	linked_list* head_at_index = pTable->collision_lists[index];
	if(head_at_index != NULL) {
		if(strcmp(head_at_index->entry->word, cWord) == 0)
			return head_at_index->entry;
		linked_list* temp = head_at_index;
		while(temp->next){
			temp = temp->next;
			if(strcmp(temp->entry->word, cWord) == 0)
				return temp->entry;
		}
	}
	return NULL;
}

void print_table(hash_table* table){
	printf("\nHash Table: \n");
	linked_list* head;
	for (int i = 0; i < table->size; i++) {
		if(table->collision_lists[i]){
			head = table->collision_lists[i];
			printf("Index:%d, Word:%s, Occurences:%d\n", i, head->entry->word, head->entry->occurences);
			while(head->next != NULL){
				head = head->next;
				printf("Index:%d, Word:%s, Occurences:%d\n", i, head->entry->word, head->entry->occurences);
			}
		}
	}
	printf("Table Count: %d\n", table->count);
	printf("-------------------\n\n");
}

hash_entry* table_to_array(hash_table* table){
	hash_entry* entry_arr[table->count];

	linked_list* head;
	for (int i = 0; i < table->size; i++) {
		if(table->collision_lists[i]){
			head = table->collision_lists[i];
			entry_arr[i] = head->entry;
			while(head->next != NULL){
				head = head->next;
				i++;
				entry_arr[i] = head->entry;
			}
		}
	}
	return entry_arr;
}

int main(int argc, char* argv[]){
	
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
	
	if (argc == 2){
		FILE* f = fopen(argv[1], "r");
		while (fscanf(f, "%s", word) != EOF){
			insert_entry(table, create_entry(word));
		}
		hash_entry* array[] = table_to_array(table);
		hash_entry* temp;
		for (int i = 0; i < table->count; i++) {
			
			// iterates the array elements from index 1
			for (int j = i + 1; j < table->count; j++) {
				
				// comparing the array elements, to set array
				// elements in descending order
				if (array[i]->occurences < array[j]->occurences) {
					temp = array[i];
					array[i] = array[j];
					array[j] = temp;
				}
			}
		}
	}
	

	return 0;	
}
