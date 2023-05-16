#include <stdio.h>
#include <stdlib.h>
#include <limits.h>

#define V 6 // number of vertices

typedef struct {
    int edges[V][V]; // adjacency matrix
} Graph;

typedef struct {
    int dist; // shortest distance from source
    int visited; // 1 if visited, 0 otherwise
} Vertex;

void dijkstra(Graph *graph, Vertex *vertices, int source) {
    // initialize all distances as infinity and visited as false
    for (int i = 0; i < V; i++) {
        vertices[i].dist = INT_MAX;
        vertices[i].visited = 0;
    }
    // distance from source to itself is 0
    vertices[source].dist = 0;
    // loop through all vertices
    for (int i = 0; i < V; i++) {
        // find the vertex with the minimum distance from the source
        int u = -1;
        int minDist = INT_MAX;
        for (int j = 0; j < V; j++) {
            if (!vertices[j].visited && vertices[j].dist < minDist) 
            {
                u = j;
                minDist = vertices[j].dist;
            }
        }
        // mark the vertex as visited
        vertices[u].visited = 1;
        // update the distance of all adjacent vertices
        for (int v = 0; v < V; v++) {
            if (graph->edges[u][v] && !vertices[v].visited && vertices[u].dist != INT_MAX && vertices[u].dist + graph->edges[u][v] < vertices[v].dist)
                vertices[v].dist = vertices[u].dist + graph->edges[u][v];                        
        }
    }
}

void main() {
    Graph graph = {.edges = {{0, 1, 4, 0, 0, 0},
                             {1, 0, 2, 5, 7, 0},
                             {4, 2, 0, 0, 1, 0},
                             {0, 5, 0, 0, 3, 8},
                             {0, 7, 1, 3, 0, 0},
                             {0, 0, 0, 8, 0, 0}}}; // the graph as an adjacency matrix

    Vertex vertices[V];
    int source = 0; // source vertex
    
    dijkstra(&graph, vertices, source);
    
    printf("Shortest distances from source vertex %d:\n", source);
    for (int i = 0; i < V; i++) 
        printf("Vertex %d: %d\n", i, vertices[i].dist);
}

