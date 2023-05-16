#include <stdio.h>  
#include <stdlib.h>
#include <limits.h>

#define MAX_VERTICES 100
#define MAX_EDGES 100

typedef struct {
    int source;
    int destination;
    int weight;
} Edge;

typedef struct {
    int numVertices;
    int numEdges;
    Edge edges[MAX_EDGES];
} Graph;

void bellmanFord(Graph* graph, int source) {
    int distances[MAX_VERTICES];
    int i, j;

    // Initialize distances to infinity
    for (i = 0; i < graph->numVertices; i++) {
        distances[i] = INT_MAX;
    }
    distances[source] = 0;

    // Relax all edges repeatedly
    for (i = 0; i < graph->numVertices - 1; i++) {
        for (j = 0; j < graph->numEdges; j++) {
            int u = graph->edges[j].source;
            int v = graph->edges[j].destination;
            int weight = graph->edges[j].weight;
            if (distances[u] != INT_MAX && distances[u] + weight < distances[v]) {
                distances[v] = distances[u] + weight;
            }
        }
    }

    // Check for negative-weight cycles
    for (i = 0; i < graph->numEdges; i++) {
        int u = graph->edges[i].source;
        int v = graph->edges[i].destination;
        int weight = graph->edges[i].weight;
        if (distances[u] != INT_MAX && distances[u] + weight < distances[v]) {
            printf("Graph contains negative-weight cycle.\n");
            return;
        }
    }

    // Print distances
    printf("Vertex\tDistance from source\n");
    for (i = 0; i < graph->numVertices; i++) {
        printf("%d\t%d\n", i, distances[i]);
    }
}

void main() {
    Graph graph = {
        .numVertices = 5,
        .numEdges = 8,
        .edges = {
            {0, 1, -1},
            {0, 2, 4},
            {1, 2, 3},
            {1, 3, 2},
            {1, 4, 2},
            {3, 1, 1},
            {4, 3, -3},
            {2, 4, 5}
        }
    };

    bellmanFord(&graph, 0);    
}

